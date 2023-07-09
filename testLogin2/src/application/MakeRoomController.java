package application;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import Service.ChatRoomService;
import Service.CommonService;
import Service.LoginService;
import ServiceImpl.ChatRoomServiceImpl;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.LoginServiceImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MakeRoomController implements Initializable{

	
	// thread가 동시다발성을 생겨나는 경우가 없기때문에 threadPool이 필요X
	Socket socket;
	
	private Parent root;
	private CommonService comService;
	private ChatRoomService makeRoomService;
	private LoginService loginService; //로그인 컨트롤러에 공통메서드를 객체로 할당


	private MyDB mydb = new MyDB();

	
	@FXML TextField input_chatName;
	@FXML TextField input_chatNum;
	@FXML TextField input_IP;
	@FXML TextField input_port;
	@FXML Button MakeBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		comService = new CommonServiceImpl();
		makeRoomService = new ChatRoomServiceImpl();
		loginService = new LoginServiceImpl();
	}

	public void setRoot(Parent root) {
		this.root = root;
//		loginService = new LoginServiceImpl();
		
	}
	

	public void MakeBtnPressed(ActionEvent event) {
//		members mem = new members();
//		String a = loginService.LoginProc1(root);
		
		makeRoomService.makeChatRoom(root);
		
		
		String fxmlURL4 = "/application/Client.fxml";
		comService.showChatPage(fxmlURL4);
	}
	
	public void startClient(String IP, int port) { //IP, port입력란
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP, port); //소켓 초기화
//					receive(); //초기화 이후 서버에서 메시지를 전달받을 수 있도록 불러옴
				}catch(Exception e) {
					if(!socket.isClosed()) {
//						stopClient();
						Alert alert = new Alert(AlertType.INFORMATION);
			        	alert.setTitle("서버 오류!");
			        	alert.setHeaderText("불특정한 이유 : "+e.getMessage());
			        	alert.setContentText("불특정한 이유로 서버 접속이 어렵습니다.");

			        	alert.showAndWait();
						System.out.println("[서버 접속 실패]");
						Platform.exit();
					}
				}
				
			}
		};
		thread.start();
	}
	
	
//	public void makeRoom(ActionEvent event) { //방 만들기 버튼 눌렀을 시
//		String fxmlURL6 = "/application/makeRoom.fxml";
//		comService.showMakeRoom(fxmlURL6); //지금ChatRoom이 가리키는 건 여기임.새로 만들어서 오류 해결하기
//	}
//	


}
