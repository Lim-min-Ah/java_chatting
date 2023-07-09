package application;


import java.io.File;
import java.net.URL;

import Service.CommonService;
import ServiceImpl.CommonServiceImpl;
import javafx.application.Application;
import javafx.stage.Stage;


public class LoginPage extends Application {
	
	CommonService comService; //인터페이스 선언
	@Override
	public void start(Stage primaryStage) throws Exception{
		comService = new CommonServiceImpl(); //인터페이스 인스턴스에 인터페이스 구현체를 객체로 할당
				
		String fxmlURL = "/application/LoginPage.fxml"; // fxml 파일이 저장되어 있는 경로를 문자열로 넘겨줌
		comService.showLoginPage(fxmlURL); //회원가입 comService.showRegisterPage(fxmlURL);은 로그인컨트롤러에서 볼 수 있음.
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
