package ServiceImpl;
import java.io.IOException;

// 공통서비스 인터페이스의 구현체(이 부분에 로그인 페이지를 띄울 코드 작성)
import Service.CommonService;
import application.AdminController;
import application.ChatRoomController;
import application.Chatcontroller;
import application.LoginController;
import application.MakeRoomController;
import application.MyDB;
import application.RegistController;
import application.friendController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CommonServiceImpl implements CommonService{

	@Override
	public void showLoginPage(String fxmlURL) { //로그인 창 띄우기((fxml파일 불러오기)공통서비스->로그인컨트롤러)
		Parent root;
		Stage stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL));

		LoginController loginCon;
	
			try {
				root = loader.load();
				loginCon = loader.getController();
				loginCon.setRoot(root);
				
				stage.setScene(new Scene(root));
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
	}

	@Override
	public void showRegistPage(String fxmlURL2) {//회원가입창 띄우기(fxml파일 로드(공통서비스->회원가입컨트롤러)
		
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL2));
		
		RegistController registCon;
		
			try {
				root = loader.load(); 
				registCon = loader.getController();
				registCon.setRoot(root);
				
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

	@Override
	public void showAdminPage(String fxmlURL3) {
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL3));
		
		AdminController adminCon;
		
			try {
				root = loader.load(); 
				adminCon = loader.getController();
				adminCon.setRoot(root);
				
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void showChatPage(String fxmlURL4) {
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL4));
		
		Chatcontroller chatCon;
		
			try {
				root = loader.load(); 
				chatCon = loader.getController();
				chatCon.setRoot(root);
				
				stage.setTitle(MyDB.RoomTitle); //채팅방 이름 표시
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	@Override
	public void showChatRoom(String fxmlURL5) {
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL5));
		
		ChatRoomController chatRoomCon;
		
			try {
				root = loader.load(); 
				chatRoomCon = loader.getController();
				chatRoomCon.setRoot(root);
				
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		
	}

	@Override
	public void showMakeRoom(String fxmlURL6) {
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL6));
//		loader.setLocation(MakeRoomController.class.getResource("/application/makeRoom.fxml"));
		MakeRoomController makeRoomCon;
		
			try {
				root = loader.load(); 
				makeRoomCon = loader.getController();
				makeRoomCon.setRoot(root);
				
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	}

	@Override
	public void showFriendRoom(String fxmlURL7) {
		Parent root;
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL7));
		friendController friendCon;
		
			try {
				root = loader.load(); 
				friendCon = loader.getController();
				friendCon.setRoot(root);
				
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
/*
[ 과정 정리
1. fxml 로더에서 fxml을 로딩하면 fxml에 설정해준 로그인 컨트롤러 객체가 생성된다. 
2. 이 생성된 객체를 공통서비스에서 넘겨받고 
3. 공통서비스에서 넘겨받은 로그인 컨트롤러 객체를 이용하여 컨트롤러의 메서드를 호출하여 root를 설정한다.
*/