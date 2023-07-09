package Service;

import javafx.scene.Parent;

public interface ChatService {
	public void Chatsend(Parent root); //DB에 메시지 넣기
	public int showMsg(Parent root); //창에 내 메시지 띄우기

}
