package ServiceImpl;

import Service.ChatRoomService;
import Service.LoginService;
import application.Data;
import application.MyDB;
import application.members;
import application.rooms;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class ChatRoomServiceImpl implements ChatRoomService {

	public ChatRoomServiceImpl() { //서비스에서는 생성자를 이용하여 DB객체를 할당
		
	}

	
	private MyDB mydb = new MyDB();
	
	@FXML
	static TextField Input_id;
	

//	@FXML static TextField 
//	private members mem;
//	@Override
//	public void makeChatRoom(Parent root, members mem) {
//		rooms room = new rooms();
//
//		TextField input_chatName = (TextField)root.lookup("#input_chatName"); //채팅방 이름
//		TextField input_chatNum = (TextField)root.lookup("#input_chatNum"); //최대 인원수
//
//		TextField input_IP = (TextField)root.lookup("#input_IP"); //ip번호
//		TextField input_port = (TextField)root.lookup("#input_port"); //port번호
//
////		room.setMAIN(mem.getNAME());
//
////		loginService.LoginUSer(root);
//		//여기에 로그인 정보가 들어가야함.
////		this.mem.getNAME()s
//
//		mydb.inputChatRoom(input_chatName.getText(), input_chatNum.getText(), room.getMAIN(), "속성", input_IP.getText(), input_port.getText());		
//	}
	
	private members mem;
	@Override
	public void makeChatRoom(Parent root) {
		Data data = new Data();
		
		rooms room = new rooms();
		TextField input_chatName = (TextField)root.lookup("#input_chatName"); //채팅방 이름
		TextField input_chatNum = (TextField)root.lookup("#input_chatNum"); //최대 인원수

		TextField input_IP = (TextField)root.lookup("#input_IP"); //ip번호
		TextField input_port = (TextField)root.lookup("#input_port"); //port번호

		

		mydb.inputChatRoom(input_chatName.getText(), input_chatNum.getText(), data.getmem(), "속성", input_IP.getText(), input_port.getText());		
		
	}
	
	@Override
	public void makeChatRoom(Parent root, String a) {
		Data data = new Data();
		
		rooms room = new rooms();
		TextField input_chatName = (TextField)root.lookup("#input_chatName"); //채팅방 이름
		TextField input_chatNum = (TextField)root.lookup("#input_chatNum"); //최대 인원수

		TextField input_IP = (TextField)root.lookup("#input_IP"); //ip번호
		TextField input_port = (TextField)root.lookup("#input_port"); //port번호

		

		mydb.inputChatRoom(input_chatName.getText(), input_chatNum.getText(), a, "속성", input_IP.getText(), input_port.getText());		
		
	}



}
