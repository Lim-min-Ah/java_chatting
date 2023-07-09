package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import Service.CommonService;
import Service.RegistService;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.RegistServiceImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegistController implements Initializable{
	private Parent root;
	private RegistService registService;//회원가입 컨트롤러에 공통메서드를 객체로 할당
	private CommonService comService;

	public void setRoot(Parent root){
		this.root = root;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//initialize 메서드에 객체를 할당받는 코드
		comService = new CommonServiceImpl();
		registService = new RegistServiceImpl();
	}

	@FXML private TextField R_PW = new TextField(); 
	@FXML private TextField R_PW2 = new TextField(); 
	public void registBtnAction() { //sign_up버튼
		TextField R_ID = (TextField)root.lookup("#R_ID"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_PW = (TextField)root.lookup("#R_PW"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_PW2 = (TextField)root.lookup("#R_PW2"); //lookup 은  Parent 가 가지고 있는 메소드

		//정규식 (영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리)
		String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
		
		//Pattern 클래스의 compile(), matcher() 함수를 활용하여 Matcher 클래스 생성
		Matcher matcher = Pattern.compile(pwPattern).matcher(R_PW.getText());
		
		//정규식 (같은 문자 4개 이상 사용 불가)
		pwPattern = "(.)\\1\\1\\1";
		Matcher matcher2 = Pattern.compile(pwPattern).matcher(R_PW.getText()); //Pattern 클래스의 compile(), matcher() 함수를 활용하여 Matcher 클래스 생성
		 	

		//비번 정규식 검사 & 비번 일치 검사
		if(!matcher.matches()){//Matcher 클래스의 matches() 함수를 활용하여 체크, true 일 경우 정규식을 만족
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("비밀번호 오류!");
        	alert.setHeaderText("(영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();
			R_PW.setText("");
			R_PW2.setText("");
			System.out.println("1 만족 X : (영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리");
		}
		 
		else if(matcher2.find()){//Matcher 클래스의 find() 함수를 활용하여 체크, true 일 경우 정규식을 만족함
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("비밀번호 오류!");
        	alert.setHeaderText("같은 문자 4개 이상 사용 불가");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();
			R_PW.setText("");
			R_PW2.setText("");
			System.out.println("2 만족 X : 같은 문자 4개 이상 사용 불가");

		}
		 
		else if(R_PW.getText().contains(R_ID.getText())){//String 클래스의 contains() 함수를 활용하여 Id가 비밀번호 문자열에 있는지 체크함
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("비밀번호 오류!");
        	alert.setHeaderText("Id를 비밀번호 문자열에 사용불가");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();
			R_PW.setText("");
			R_PW2.setText("");
			System.out.println("3 만족 X : Id가 비밀번호 문자열에 있음");

		}
		 
		else if(R_PW.getText().contains(" ")){//String 클래스의 contains() 함수를 활용하여 공백문자가 비밀번호 문자열에 있는지 체크함
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("비밀번호 오류!");
        	alert.setHeaderText("문자열에 공백문자 사용 불가");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();
			R_PW.setText("");
			R_PW2.setText("");
			System.out.println("4 만족 X : 공백문자가 비밀번호 문자열에 있음");

		}
		else if(!R_PW.getText().equals(R_PW2.getText())) {
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("회원가입 오류!");
        	alert.setHeaderText("비밀번호가 일치하지 않습니다.");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();
			System.out.println("일치하는 비번이 아님!");
			
			}
		else if (R_PW.getText().equals(R_PW2.getText())){
			registService.RegistProc(root);
			
//			Platform.exit();
			String fxmlURL1 = "/application/LoginPage.fxml";
			comService.showLoginPage(fxmlURL1);
		}

	}
	
	@FXML private TextField R_ID = new TextField(); 
	public void chkID(ActionEvent event) {//ID중복 체크 버튼
		switch(	registService.RegistChkID(root)) {
		case 0://중복되지 않은 아이디
			Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("성공!!");
        	alert.setHeaderText("적정한 아이디입니다");        	
        	alert.showAndWait();
			break;
		case 1:
			// 아이디가 중복되는 경우
        	Alert alert1 = new Alert(AlertType.INFORMATION);
        	alert1.setTitle("로그인 오류!");
        	alert1.setHeaderText("중복된 아이디입니다");
        	alert1.setContentText("다시 시도해주세요!");
        	
        	alert1.showAndWait();
			System.out.println("중복된 아이디 ID 없음!");
			R_ID.setText("");
			break;
	}
	}
	
	public void addBtnAction(ActionEvent event) { //우편번호 버튼
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		try {
		    Object result = engine.eval("Math.min(2, 3)");

		    if (result instanceof Integer) {
		        System.out.println(result);
		    }
		} catch (ScriptException e) {
		    System.err.println(e);
		}
		
//		registService.RegistAddProc(root);
	}
}
