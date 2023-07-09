package Service;

import javafx.scene.Parent;

public interface RegistService {
	 //int형 반환타입으로 선언한 이유 : 결과를 다르게 출력하기 위함
	public void RegistProc(Parent root);
	public int RegistChkID(Parent root); //아이디 중복 확인
	public void RegistAddProc(Parent root); //우편번호때문에 작성
}
