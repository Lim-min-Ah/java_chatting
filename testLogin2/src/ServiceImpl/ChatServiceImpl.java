package ServiceImpl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Service.ChatService;
import application.MyDB;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class ChatServiceImpl implements ChatService{
	private MyDB mydb = new MyDB();

	@Override
	public void Chatsend(Parent root) {
		TextField MessageGo = (TextField)root.lookup("#MessageGo"); //lookup 은  Parent 가 가지고 있는 메소드
        // 현재 시간
        LocalTime now = LocalTime.now();
 
        // 포맷 정의하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
 
        // 포맷 적용하기
        String formatedNow = now.format(formatter);
		mydb.goMessage(MessageGo.getText(), formatedNow);
		
	}

	@Override
	public int showMsg(Parent root) {
		// TODO Auto-generated method stub
		return 0;
	}

}
