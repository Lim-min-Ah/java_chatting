package Service;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface AdminService {

//	int updateMember(ObservableList buildData);

	int updateMember(Parent root);

	int insertMember(Parent root);

	int deleteMember(Parent root);
	
	void inputCSVfile(Parent root) throws IOException;

//	ArrayList<String> getAllMemberList(Parent root);

}
