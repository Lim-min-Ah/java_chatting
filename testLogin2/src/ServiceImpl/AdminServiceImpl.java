package ServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Service.AdminService;
import application.CSVTableView;
import application.MyDB;
//import application.csvTable;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminServiceImpl implements AdminService{

	private MyDB mydb = new MyDB();
	public AdminServiceImpl() { //서비스에서는 생성자를 이용하여 DB객체를 할당
		
	}
	
	@Override
	public int updateMember(Parent root) { //수정
		TextField memId = (TextField)root.lookup("#memId"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField memName = (TextField)root.lookup("#memName"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField memPw = (TextField)root.lookup("#memPw"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField memEmail = (TextField)root.lookup("#memEmail"); //lookup 은  Parent 가 가지고 있는 메소드

		
		return mydb.updateMember(memId.getText(), memName.getText(), memPw.getText(), memEmail.getText());
	}

	@Override
	public int insertMember(Parent root) { //추가
		TextField memId = (TextField)root.lookup("#memId"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField memName = (TextField)root.lookup("#memName"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField memPw = (TextField)root.lookup("#memPw"); //lookup 은  Parent 가 가지고 있는 메소드
		TextField memEmail = (TextField)root.lookup("#memEmail"); //lookup 은  Parent 가 가지고 있는 메소드
		
		return mydb.insertUser(memId.getText(), memName.getText(), memPw.getText(), null, memEmail.getText(), null);
	}

	@Override
	public int deleteMember(Parent root) { //삭제
		TextField memId = (TextField)root.lookup("#memId"); //lookup 은  Parent 가 가지고 있는 메소드
		
		return mydb.delUser(memId.getText());
	}
	
	@FXML
    private TableView tableview;
	@FXML
    private TextField memId;
    @FXML
    private TextField memName;
    @FXML
    private TextField memPw;
    @FXML
    private TextField memEmail;
	
	private CSVTableView csvTable = new CSVTableView();
	@Override
	public void inputCSVfile(Parent root) throws IOException {
		csvTable.inputCSV(",", "C:/Users/Windows/Desktop/members_info.csv");
		CSVTableView mVo = (CSVTableView) tableview.getSelectionModel().getSelectedItem();
//		
//		memId.setText(mVo.tbMemId());
//		memName.setText(mVo.getMem_name());
//		memPw.setText(mVo.getMem_tel());
//		memEmail.setText(mVo.getMem_addr());
		
	}

//	@FXML
//	private TableView table;
//	List<List<Type>> myList = new ArrayList<>();
//	@Override
//	public ArrayList<String> getAllMemberList(Parent root) {
//		
//		mydb.list();
//		ArrayList<String> a = mydb.ListID();
//		ArrayList<String> b =mydb.ListNAME();
//		ArrayList<String> c =mydb.ListPW();
//		ArrayList<String> d =mydb.ListEMAIL();
//		
//		myList.add(mydb.ListID());
//		
//		return null;
//	}
}
