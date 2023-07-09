package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Service.ChatRoomService;
import Service.CommonService;
import Service.LoginService;
import Service.RegistService;
import ServiceImpl.ChatRoomServiceImpl;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.LoginServiceImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
// 이곳에 로그인창에서 발생하는 이벤트(버튼 등)가 가능하도록 컨트롤하는 클래스
public class LoginController implements Initializable{
    Stage window;
    Scene chattingroom;
	
	private Parent root;
	private LoginService loginService; //로그인 컨트롤러에 공통메서드를 객체로 할당
	private CommonService comService;
	private ChatRoomService makeRoomService;

	
	@FXML Button btnLogin;
	@FXML TextField input_id;
	
	public void setRoot(Parent root){
		this.root = root;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//initialize 메서드에 객체를 할당받는 코드
		loginService = new LoginServiceImpl();
		comService = new CommonServiceImpl();
		makeRoomService = new ChatRoomServiceImpl();
	}
	
	public void RegistBtnPressed(ActionEvent event) {
		String fxmlURL2 = "/application/RegisterPage.fxml";
		comService.showRegistPage(fxmlURL2);
	}
	

	Data data = new Data();
	//1. 사용자가 "Login"버튼을 누를 경우 이벤트 처리 메소드 만들기
	//2. fxml파일가서 onAction으로 아래 함수 연결
	public void LoginBtnPressed(ActionEvent event) {
		switch(loginService.LoginProc(root)) {
		
        case 0:
			// 아이디가 없는경우
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("로그인 오류!");
        	alert.setHeaderText("잘못된 아이디입니다.");
        	alert.setContentText("다시 시도하거나 아이디 찾기를 클릭하여 재설정하세요.");

        	alert.showAndWait();
			System.out.println("일치하는 ID 없음!");
			break;
		case 1:
			//아이디가 있지만 비밀번호가 일치하지 않는 경우
			Alert alert1 = new Alert(AlertType.INFORMATION);
        	alert1.setTitle("로그인 오류!");
        	alert1.setHeaderText("잘못된 비밀번호입니다.");
        	alert1.setContentText("다시 시도하거나 비밀번호 찾기를 클릭하여 재설정하세요.");
        	alert1.showAndWait();
			System.out.println("PW 미일치!");
			break;
		case 2:
			//로그인에 성공
			System.out.println("로그인 성공!");
			String fxmlURL5 = "/application/ChatRoomList.fxml"; //채팅 대기방
//			String fxmlURL4 = "/application/Client.fxml"; // 채팅창
//			String fxmlURL4 = "/application/listUnit.fxml";
			String a = loginService.LoginProc1(root);
			System.out.println(a);
			MyDB.getUserName = a;
			comService.showChatRoom(fxmlURL5);
//			Platform.exit();

			break; 
		case 3:
			String fxmlURL3 = "/application/testAdminPage.fxml";
			comService.showAdminPage(fxmlURL3);
			
		}
	}

	
}
