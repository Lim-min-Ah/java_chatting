package ServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Service.LoginService;
import application.DBconn;
import application.Data;
import application.MyDB;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class LoginServiceImpl implements LoginService{

	private MyDB mydb = new MyDB();
	public LoginServiceImpl() { //서비스에서는 생성자를 이용하여 DB객체를 할당
				
	}
		
	@Override
	public int LoginProc(Parent root) {
		TextField Input_id = (TextField)root.lookup("#Input_id"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField Input_pw = (TextField)root.lookup("#Input_pw"); //FXMLLoader를 통해 생성한뒤 이 메서드를 사용하면 CSS에서 객체를 찾는 방식으로 이름, 즉 아이디가 일치하는 컴포넌트를 찾는 방법
		
		Data data = new Data();
		data.setmem(mydb.getName(Input_id.getText()));

		return mydb.chkIdPw(Input_id.getText(), Input_pw.getText());
	}
	
	public String LoginProc1(Parent root) {
		TextField Input_id = (TextField)root.lookup("#Input_id"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField Input_pw = (TextField)root.lookup("#Input_pw"); //FXMLLoader를 통해 생성한뒤 이 메서드를 사용하면 CSS에서 객체를 찾는 방식으로 이름, 즉 아이디가 일치하는 컴포넌트를 찾는 방법
//		
//		Data data = new Data();
//		data.setmem(mydb.getName(Input_id.getText()));

		return mydb.getName(Input_id.getText());
	}
	
	
	static private String userNAME;

	public String LoginUSer(Parent root) {
		
		TextField Input_id = (TextField)root.lookup("#Input_id"); //lookup 은  Parent 가 가지고 있는 메소드

		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT ID, NAME from members");

			while (rs.next()) {
				String getID = rs.getString("ID");
				String getNAME = rs.getString("NAME");
				
				System.out.println("ID/NAME 정보 : ("+getID+","+getNAME+")");//잘 가지고 왔나 확인
				
				if(getID.equals(Input_id.getText())) {
					userNAME = getNAME;
					break;
				}
			}
		}catch (Exception e) {
			System.out.println("select 오류1 : "+e.getMessage());
			}
		return userNAME;
	}
	

}




