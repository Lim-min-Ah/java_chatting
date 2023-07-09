package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.scene.layout.HBox;


import Service.ChatService;
import Service.CommonService;
import Service.LoginService;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.LoginServiceImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Chatcontroller implements Initializable {

	// thread가 동시다발성을 생겨나는 경우가 없기때문에 threadPool이 필요X
	Socket socket;
	
	@FXML TextField IPnum;
	@FXML TextField PortNum;
	@FXML TextField MessageGo;
	@FXML VBox textarea_msg;
	@FXML Button send_btn;
	@FXML Button connectionButton;
	@FXML Text MyMsg;
	
	private Parent root;
	private LoginService loginService; //로그인 컨트롤러에 공통메서드를 객체로 할당
	private CommonService comService;
	private ChatService chatService;
	private MyDB mydb = new MyDB();
	private CreateMsg cm;

	private Stage clientStage;
	
	public void setRoot(Parent root){
		this.root = root;
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {//initialize 메서드에 객체를 할당받는 코드
		loginService = new LoginServiceImpl();
		comService = new CommonServiceImpl();
		
		cm = new CreateMsg(this.clientStage);
//		getMsgIndex();
	}
	
	//클라이언트 프로그램 동작 메소드
	public void startClient(String IP, int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP, port); //소켓 초기화
					receive(); //초기화 이후 서버에서 메시지를 전달받을 수 있도록 불러옴
				}catch(Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[서버 접속 실패]");
						Platform.exit();
					}
				}
				
			}
		};
		thread.start();
	}
	
	//클라이언트 프로그램 종료 메소드
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	//서버로부터 메시지를 전달받는 메소드
	public void receive() { 
		ArrayList<String> listMsg = mydb.getMsgs();//과거 메시지를 띄우기
        // 첫번째, 마지막 index
        int firstIndex = 0;
        int lastIndex = mydb.getMsgs().size() - 1;
        
        for(int i = firstIndex; i<=lastIndex; i++) {
        	System.out.println(listMsg.get(i));        	
        	String msgIndex = listMsg.get(i);
        	
			String[] a = msgIndex.split(":");
			System.out.println(a[0]+"!!!!"+a[1]+"!!!!"+a[2]);
			if (MyDB.getUserName.equals(a[0])) {
				HBox hbox = cm.makeMsg(a[0], a[1], true, a[2]);
				Platform.runLater( () -> this.textarea_msg.getChildren().add(hbox));				
				}
			else {
				HBox hbox = cm.makeMsg(a[0], a[1], false, a[2]);
				Platform.runLater( () -> this.textarea_msg.getChildren().add(hbox));			
				System.out.println("통과");
			}
        }
	
		while(true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if(length == -1) throw new IOException();
				String message = new String(buffer,0, length, "UTF-8");
				
				System.out.println("수신 : "+message);
				String[] a = message.split(":");
				System.out.println(a[0]+"그리고"+a[1]);

				if (MyDB.getUserName.equals(a[0])) {
					HBox hbox = cm.makeMsg(a[0], a[1], true, getTime());
					Platform.runLater( () -> this.textarea_msg.getChildren().add(hbox));				
					}
				else {
					HBox hbox = cm.makeMsg(a[0], a[1], false, getTime());
					Platform.runLater( () -> this.textarea_msg.getChildren().add(hbox));			
					System.out.println("통과");
				}
				
				
			}catch(Exception e) {
				stopClient();
				break;
			}
		}
	}

	
	//서버로 메시지를 전송하는 메소드
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				}catch(Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
		
	}
	
		
	public void SendBtnPressed(ActionEvent event) { //전송버튼 누를시

		TextField MessageGo = (TextField)root.lookup("#MessageGo"); //lookup 은  Parent 가 가지고 있는 메소드
		
        // 현재 시간
        LocalTime now = LocalTime.now();
  
        // 포맷 정의하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
 
        // 포맷 적용하기
        String formatedNow = now.format(formatter);
		mydb.goMessage(MessageGo.getText(), formatedNow);
		
		
		Platform.runLater(()-> {			
			send(MyDB.getUserName+":"+ MessageGo.getText()+"\n");
//			textarea_msg.appendText(MessageGo.getText()+"\n"); //비상용 나중에 없애고 제대로 작동되게 하기.
			MessageGo.setText(""); //메시지를 전송했으니까 전송칸 비우기
			MessageGo.requestFocus();//다시 메시지를 전송할 수 있도록 focus를 설정	
			
		});
		
	}
	static int result = 9876;
	public int chkport(String Port) { //중복 검사
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
		
		Data data = new Data();
		rooms room = new rooms();

		try {
			result = 9876;
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT PK, PORTnum from rooms");

			while (rs.next()) {
				String getPk = rs.getString("PK");
				String getPort = rs.getString("PORTnum");

				System.out.println("port 정보 : ("+getPort+")");//잘 가지고 왔나 확인
											
				if(getPk.equals(MyDB.infoPK)) {
						result = Integer.parseInt(getPort); //멤버리스트에 ID가 있고 해당 멤버객체에 저장된 PW와  입력한PW가 같을경우 결과값에 +1
					break;
				}
				

			}
		}catch (Exception e) {
			System.out.println("select 오류1 : "+e.getMessage());
		}finally {
			try {
				rs.close();
			}catch (SQLException e) {
				System.out.println("select 오류2 : "+e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println(result);
		return result;
		// 반복문이 끝나면 결과값을 반환
		// 즉 , 결과값 0: ID가 존재하지않음, 1: ID는 존재하지만 PW 미일치, 2: ID/PW 모두 만족

	} 
	
	static int port;
	public void connectionPressed(ActionEvent event) {
		PortNum.setText(Integer.toString(result));
		port = result; //포트 기본값 항상 9876으로 설정
		Platform.runLater(()-> {
			if(connectionButton.getText().equals("접속!")) {				
//				Portnum.setText(MyDB.PortNUM);
				try {
					port = Integer.parseInt(PortNum.getText());
				}catch(Exception e) {
					e.printStackTrace();
				}
				startClient(IPnum.getText(),port);
				Platform.runLater(()-> {
//					textarea_msg.appendText("[채팅방 접속]\n");
				});
				connectionButton.setText("종료하기");
				MessageGo.setDisable(false);
				send_btn.setDisable(false);
				MessageGo.requestFocus();
			}else {
				stopClient();
				Platform.runLater(()->{
//					textarea_msg.appendText("[채팅방 퇴장]\n");
				});
				connectionButton.setText("[접속하기]");
				MessageGo.setDisable(false);
				send_btn.setDisable(false);
				
			}
		});
	}
	

	public String getTime() { //중복 검사
        // 현재 시간
        LocalTime now = LocalTime.now();
 
        // 포맷 정의하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
 
        // 포맷 적용하기
        String formatedNow = now.format(formatter);
		return formatedNow;
		}
	
	public void start(Stage arg0) throws Exception {
		
		// TODO Auto-generated method stub
//		launch(arg0);
//		MessageGo.setOnAction(event->{
//			send("로그인 대상"+": "+MessageGo.getText()+"\n");
//			MessageGo.setText(""); //메시지를 전송했으니까 전송칸 비우기
//			MessageGo.requestFocus();//다시 메시지를 전송할 수 있도록 focus를 설정
//		});
//		send_btn.setOnAction(event -> {
//			send("로그인 대상"+": "+MessageGo.getText()+"\n");
//			MessageGo.setText("");
//			MessageGo.requestFocus();
//		});
		
		
		Main callMethod = new Main();//객체화
		
	}
}

