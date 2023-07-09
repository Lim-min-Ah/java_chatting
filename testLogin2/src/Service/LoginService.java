package Service;

import javafx.scene.Parent;
import javafx.scene.control.TextField;

public interface LoginService {
 //int형 반환타입으로 선언한 이유 : 결과를 다르게 출력하기 위함
	public int LoginProc(Parent root);

	public String LoginUSer(Parent root);

	public String LoginProc1(Parent root);



}
