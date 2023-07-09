package ServiceImpl;

import java.io.IOException;

import Service.RegistService;
import application.MyDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistServiceImpl implements RegistService{

	private MyDB mydb = new MyDB();

	public RegistServiceImpl() { //서비스에서는 생성자를 이용하여 DB객체를 할당
				
	}
	@FXML
	private Button btn_signUp;
//	@FXML
//    private RadioButton R_male = new RadioButton();
//    @FXML
//    private RadioButton R_female = new RadioButton();
	

    
    static private String genderText ="";

	@Override
	public void RegistProc(Parent root) {
	    

		
		TextField R_ID = (TextField)root.lookup("#R_ID"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_name = (TextField)root.lookup("#R_name"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_PW = (TextField)root.lookup("#R_PW"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_PW2 = (TextField)root.lookup("#R_PW2"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_email = (TextField)root.lookup("#R_email"); //lookup 은  Parent 가 가지고 있는 메소드
		
		RadioButton R_male = (RadioButton)root.lookup("#R_male"); //lookup 은  Parent 가 가지고 있는 메소드
		RadioButton R_female = (RadioButton)root.lookup("#R_female"); //lookup 은  Parent 가 가지고 있는 메소드


		if(R_male.isSelected()) {
			//남자 선택 되었을 때의 처리부분
			genderText = R_male.getText(); 
			
		} else if(R_female.isSelected()) {
    		//여자 선택 되었을 때의 처리부분
			genderText = R_female.getText();     		
		}

		
		mydb.registUser(R_ID.getText(), R_name.getText(), R_PW.getText(), R_PW2.getText(), R_email.getText(), genderText);

	}
//	private registADD add = new registADD();
	@Override
	public void RegistAddProc(Parent root) {
//		https://lkt01010.tistory.com/113 이거보고하고있음(우편번호)
		//작성 예정(우편번호 관련)
		
	}
	
	@Override
	public int RegistChkID(Parent root) {//아이디 중복확인
		TextField R_ID = (TextField)root.lookup("#R_ID"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField R_PW = (TextField)root.lookup("#R_PW"); //lookup 은  Parent 가 가지고 있는 메소드

		return mydb.R_chkId(R_ID.getText(), R_PW.getText()); //관리자가 로그인했을 때도 봐야 하기 때문에 PW도 인자값으로 넘겨줌.
	}

}
