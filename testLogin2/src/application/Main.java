package application;
	
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class Main extends Application {

	// thread가 동시다발성을 생겨나는 경우가 없기때문에 threadPool이 필요X
	Socket socket;
	TextArea textArea; //메시지 주고 받을 때 출력되는 공간
	
	
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
		while(true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if(length == -1) throw new IOException();
				String message = new String(buffer,0, length, "UTF-8");
				
				System.out.println("수신 : "+message);
				
				Connection connection = null;
				
				String url = "jdbc:mariadb://localhost:3307/javatest";
				String user = "root";
				String pwd = "1234";
				try {
					connection = DriverManager.getConnection(url, user, pwd);					
				} catch (SQLException e) {
					System.out.println("DB 연결 실패 : "+e.getMessage());
				}
				
				Statement stmt = null;
				
				String sql = "INSERT INTO chat(message)"+" VALUES('"+message+"');";
				try {
					stmt = connection.createStatement();
					stmt.execute(sql);
				} catch (SQLException e) {
					System.out.println("DB에 입력 실패 : "+e.getMessage());
				}finally {
					try {
						stmt.close();
						connection.close();
					} catch (SQLException e) {
						System.out.println("DB 닫기 실패 : "+e.getMessage());
					}			
				}
				Platform.runLater(()->{
					textArea.appendText(message);
				});	
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
	
	//실제로 프로그램을 작동시키는 메소드
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));
		
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		
		TextField userName = new TextField();
		userName.setPrefWidth(150);
		userName.setPromptText("닉네임을 입력하세요.");
		HBox.setHgrow(userName, Priority.ALWAYS);
		
		TextField IPText = new TextField("127.0.0.1");
		TextField portText = new TextField("9876");
		portText.setPrefWidth(80); //포트번호 픽셀너비 80
		
		hbox.getChildren().addAll(userName, IPText, portText); //hbox내부에 세개의 내용이 입력 될 수 있도록
		root.setTop(hbox); //hbox를 위쪽에다 배치
		
		textArea = new TextArea();
		textArea.setEditable(false);
		root.setCenter(textArea);
		
		TextField input = new TextField();
		input.setPrefWidth(Double.MAX_VALUE);
		input.setDisable(true);
		
		input.setOnAction(event->{
			send(userName.getText()+": "+input.getText()+"\n");
			input.setText(""); //메시지를 전송했으니까 전송칸 비우기
			input.requestFocus();//다시 메시지를 전송할 수 있도록 focus를 설정
		});
		Button sendButton = new Button("전송");
		sendButton.setDisable(true); //접속하기 이전에는 이용할 수 없도록 Disable처리
		
		sendButton.setOnAction(event -> {
			send(userName.getText()+": "+input.getText()+"\n");
			input.setText("");
			input.requestFocus();
		});
		
		Button connectionButton = new Button("접속하기");
		connectionButton.setOnAction(event->{
			if(connectionButton.getText().equals("접속하기")) {
				int port = 9876;
				try {
					port = Integer.parseInt(portText.getText());
				}catch(Exception e) {
					e.printStackTrace();
				}
				startClient(IPText.getText(),port);
				Platform.runLater(()-> {
					textArea.appendText("[채팅방 접속]\n");
				});
				connectionButton.setText("종료하기");
				input.setDisable(false);
				sendButton.setDisable(false);
				input.requestFocus();
			}else {
				stopClient();
				Platform.runLater(()->{
					textArea.appendText("[채팅방 퇴장]\n");
				});
				connectionButton.setText("[접속하기]");
				input.setDisable(false);
				sendButton.setDisable(false);
				
			}
		});
		BorderPane pane = new BorderPane();
		pane.setLeft(connectionButton);
		pane.setCenter(input);
		pane.setRight(sendButton);
		
		root.setBottom(pane);
		Scene chattingroom = new Scene(root, 400, 400);
		primaryStage.setTitle("[채팅 클라이언트]");
		primaryStage.setScene(chattingroom);
		primaryStage.setOnCloseRequest(event->stopClient());
		primaryStage.show();
		
		connectionButton.requestFocus();
 	}
	
	//프로그램 진입점
	public static void main(String[] args) {
		launch(args);
		
		Main callMethod = new Main();//객체화
		

		
		//사용자에게 입력 받기 : 메시지
		
//		Scanner sc = new Scanner(System.in);
//
//		System.out.println("메시지를 입력하세요 : ");
//		String message = sc.next();

		
		
		
		
//		String sql = "INSERT INTO chat(message)"+" VALUES('"+msg+"');";
		
		
		
	}
}
