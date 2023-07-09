package Service;

import java.net.URL;

//service 패키지에는 인터페이스가 들어가며, impl 패키지에는 service 패키지에 선언된 인터페이스의 구현체들이 들어가게 될 것
import javafx.scene.Parent;

public interface CommonService {
	//서비스 인터페이스 부분 : 가장 먼저 로그인 페이지를 보여줄 메소드를 선언하고 구현체로 이동
	public void showLoginPage(String fxmlURL);
	public void showRegistPage(String fxmlURL2);
	public void showAdminPage(String fxmlURL3);
	public void showChatPage(String fxmlURL4); //채팅방
	public void showChatRoom(String fxmlURL5); //채팅 대기방
	public void showMakeRoom(String fxmlURL6); //채팅을 만들기 위해 '방만들기'창
	public void showFriendRoom(String fxmlURL7); // 친구 목록창
	

}
