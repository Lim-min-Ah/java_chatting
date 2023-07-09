package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Service.AdminService;
import Service.CommonService;
import Service.LoginService;
import Service.RegistService;
import ServiceImpl.AdminServiceImpl;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.LoginServiceImpl;
import ServiceImpl.RegistServiceImpl;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class AdminController implements Initializable{
	private static final String String = null;
	private Parent root;
	private AdminService adminService; //로그인 컨트롤러에 공통메서드를 객체로 할당
	private CommonService comService;
	private RegistService registService;
	private MyDB mydb = new MyDB();
	
	public void setRoot(Parent root){
		this.root = root;
	}
	
	static String work ="";
	//TABLE VIEW AND DATA

    @FXML private TableColumn<members, String> tbMemId;
    @FXML private TableColumn<members, String> tbMemName;
    @FXML private TableColumn<members, String> tbMemPw;
    @FXML private TableColumn<members, String> tbMemEmail;
    @FXML private TableColumn<members, String> tbMemGender;


		
//	@FXML private ResourceBundle resources;
//    @FXML private URL location;
    @FXML private TextField memId;
    @FXML private TextField memName;
    @FXML private TextField memPw;
    @FXML private TextField memEmail;
    @FXML private TextField memGender;
    @FXML private TextField IDsearch;

    @FXML private Button addbtn;
    @FXML private Button modbtn;
    @FXML private Button delbtn;
    @FXML private Button okbtn;
    @FXML private Button cancelbtn;
    
	@FXML private TableView<members> tableview;
	
	@FXML private ComboBox<String> comboBox = new ComboBox<>();
	
	ObservableList<String> list; //콤보박스
	ObservableList<members> listview; //테이블 뷰
	
	@FXML 
	void Select(ActionEvent event) {
		String s = comboBox.getSelectionModel().getSelectedItem().toString();
		IDsearch.setPromptText(s+" 검색");
	}

	

    
    //추가버튼을 클릭했을 때 처리
    @FXML
    void addClick(ActionEvent event) {
    	//각 TextField들을 편집 가능한 상태로 만든다.
    	memId.setEditable(true);
    	memName.setEditable(true);
    	memPw.setEditable(true);
    	memEmail.setEditable(true);
    	memGender.setEditable(true);

    	
    	okbtn.setDisable(false); //활성화
    	cancelbtn.setDisable(false);
    	
    	addbtn.setDisable(true);//비활성화
    	modbtn.setDisable(true);
    	delbtn.setDisable(true);
    	
    	tableview.setDisable(true); //테이블뷰 비활성화
    	
    	//TextField의 내용을 모두 지운다.
    	memId.clear();
    	memName.clear();
    	memPw.clear();
    	memEmail.clear();
    	memGender.clear();

    	
    	memId.requestFocus(); //ID에 포커스 주기
    	work = "Insert";
    }
    //취소 버튼을 클릭했을 때 처리(추가버튼 클릭했을 시와 반대)
    @FXML
    void cancelClick(ActionEvent event) {
    	memId.setEditable(false); //편집 불가 상태
    	memName.setEditable(false);
    	memPw.setEditable(false);
    	memEmail.setEditable(false);
    	memGender.setEditable(false);

    	
    	okbtn.setDisable(true); //비활성화
    	cancelbtn.setDisable(true);
    	
    	addbtn.setDisable(false);//활성화
    	modbtn.setDisable(false);
    	delbtn.setDisable(false);
    	
    	tableview.setDisable(false); //테이블뷰 활성화
    	work = "";
    }

    //삭제 버튼을 클릭했을 때 처리
    @FXML
    void delClick(ActionEvent event) {
    	if (tableview.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("삭제작업오류!");
        	alert.setHeaderText("삭제할 데이터를 선택한 후 사용하세요.");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();

			return;
		}
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("삭제확인");
    	alert.setContentText("정말로 삭제하시겠습니까?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		//선택한 데이터의 회원ID값 가져오기
    		//방법1)String memId = table.getSelectionModel().getSelectedItem().getMem_id();
    		
    		//방법2) 텍스트 필드에서 가져오기
//    		String memberId = memId.getText();
    		
    		int cnt = adminService.deleteMember(root); //DB에서 삭제하기
    		if (cnt>0) { 
				//삭제성공을 했다면 현재 테이블 뷰에서도 삭제돼야함.
    			//테이블 뷰의 인덱스번호 구하기
    			
    			int index = tableview.getSelectionModel().getSelectedIndex();
    			listview.remove(index);
    			
    			//마지막작업에서 만든 것을 써봄
    			//setTableView();
    			
    			Alert infoMsg = new Alert(AlertType.INFORMATION);
    			infoMsg.setTitle("Information MessageBox");
    			infoMsg.setHeaderText("삭제성공");
    			infoMsg.setContentText("회원 정보를 삭제했습니다.");

    			infoMsg.showAndWait();
    			memId.clear();
    			memName.clear();
    			memPw.clear();
    			memEmail.clear();
    			memGender.clear();
			}
    	} else {
    		//삭제실패
    		Alert infoMsg2 = new Alert(AlertType.INFORMATION);
    		infoMsg2.setTitle("Information MessageBox");
    		infoMsg2.setHeaderText("작업실패");
    		infoMsg2.setContentText("삭제 작업 실패!!");

    		infoMsg2.showAndWait();
    	}

    }
    
    //수정 버튼을 클릭했을 때 처리
    //(1)선택이 되어져있어야 함 --> 비어있는지 아닌지 확인
    @FXML
    void modClick(ActionEvent event) {
    	if (tableview.getSelectionModel().isEmpty()) {
    		Alert infoMsg = new Alert(AlertType.INFORMATION);
    		infoMsg.setTitle("Information MessageBox");
    		infoMsg.setHeaderText("수정작업오류");
    		infoMsg.setContentText("수정할 데이터를 선택한 후 사용하세요.");

    		infoMsg.showAndWait();
			return;
		}
    	
    	memId.setEditable(false); //ID는 수정불가
    	memName.setEditable(true); //나머지 데이터는 편집 가능 상태로 만든다.
    	memPw.setEditable(true);
    	memEmail.setEditable(true);
    	memGender.setEditable(true);
    	
    	okbtn.setDisable(false); //활성화
    	cancelbtn.setDisable(false);
    	
    	addbtn.setDisable(true);//비활성화
    	modbtn.setDisable(true);
    	delbtn.setDisable(true);
    	
    	tableview.setDisable(true); //테이블뷰 비활성화
    	
    	memName.requestFocus(); //포커스(커서위치)는 이름
    	work = "Update";
    }
//확인 버튼을 클릭했을 때 처리
    @FXML
    void okClick(ActionEvent event) {
    	//추가버튼 -> 확인 
    	//수정버튼 -> 확인인지를 구분해줘햐 함. 
    	//work라는 변수 선언후 각각의 메서드에 work추가
    	
    	//현재 TextField에 입력한 내용을 가져온다.
    	String mId = memId.getText();
    	if (mId.isEmpty()) {
    		Alert errMsg = new Alert(AlertType.INFORMATION);
    		errMsg.setTitle("Information MessageBox");
    		errMsg.setHeaderText("입력오류");
    		errMsg.setContentText("회원 ID를 입력하세요");

    		errMsg.showAndWait();
			memId.requestFocus();
			return;
		}
    	
    	String mName = memName.getText();
    	if (mName.isEmpty()) {
    		Alert errMsg = new Alert(AlertType.INFORMATION);
    		errMsg.setTitle("Information MessageBox");
    		errMsg.setHeaderText("입력오류");
    		errMsg.setContentText("회원이름을 입력하세요.");

    		errMsg.showAndWait();
			memName.requestFocus();
			return;
		}
    	String mPw = memPw.getText();
    	if (mPw.isEmpty()) {
    		Alert errMsg = new Alert(AlertType.INFORMATION);
    		errMsg.setTitle("Information MessageBox");
    		errMsg.setHeaderText("입력오류");
    		errMsg.setContentText("비밀번호를 입력하세요.");

    		errMsg.showAndWait();

    		memPw.requestFocus();
			return;
		}
    	
    	String mEmail = memEmail.getText();
    	if (mEmail.isEmpty()) {
    		Alert errMsg = new Alert(AlertType.INFORMATION);
    		errMsg.setTitle("Information MessageBox");
    		errMsg.setHeaderText("입력오류");
    		errMsg.setContentText("회원이메일을 입력하세요.");

    		errMsg.showAndWait();
    		memEmail.requestFocus();
			return;
		}
    	String mGender = memGender.getText();
    	if (mGender.isEmpty()) {
    		Alert errMsg = new Alert(AlertType.INFORMATION);
    		errMsg.setTitle("Information MessageBox");
    		errMsg.setHeaderText("입력오류");
    		errMsg.setContentText("회원성별을 입력하세요.");

    		errMsg.showAndWait();
    		memGender.requestFocus();
			return;
		}
    	
    	//입력 받은 데이터를 MemberVO에 담는다.
    	mydb.registUser(mId, mName, mPw, mPw, mEmail, mGender);
    	
    	
    	//추가 작업인지 수정작업인지 구분해서 처리한다.
    	if ("Insert".equals(work)) {
    		//DB추가
			int cnt = adminService.insertMember(root);
			if(cnt>0) {
				//TableView에 추가
				
//				MembersList.add
	    		Alert infoMsg = new Alert(AlertType.INFORMATION);
	    		infoMsg.setTitle("Information MessageBox");
	    		infoMsg.setHeaderText("작업성공");
	    		infoMsg.setContentText("회원 정보를 추가했습니다.");

	    		infoMsg.showAndWait();
				//입력했던 TextField자료 삭제
				memId.clear();
				memName.clear();
				memPw.clear();
				memEmail.clear();
				memGender.clear();
			}else {
	    		Alert infoMsg = new Alert(AlertType.INFORMATION);
	    		infoMsg.setTitle("Information MessageBox");
	    		infoMsg.setHeaderText("작업실패");
	    		infoMsg.setContentText(mId+" 회원의 추가 작업 실패!!");

	    		infoMsg.showAndWait();
			}
		}else if ("Update".equals(work)) { 
			//DB수정
			int cnt = adminService.updateMember(root);
			//작업 성공
			if (cnt > 0 ) {
				
//				int index =tableview.getSelectionModel().getSelectedIndex();
				//TableView데이터 수정작업
//				data.set(index, buildData());
				Alert infoMsg = new Alert(AlertType.INFORMATION);
	    		infoMsg.setTitle("Information MessageBox");
	    		infoMsg.setHeaderText("작업성공");
	    		infoMsg.setContentText(mId+" 회원의 정보가 수정되었습니다.");

	    		infoMsg.showAndWait();
			} else {
				Alert infoMsg = new Alert(AlertType.INFORMATION);
	    		infoMsg.setTitle("Information MessageBox");
	    		infoMsg.setHeaderText("작업실패");
	    		infoMsg.setContentText(mId+" 회원의 정보 수정 실패!!");

	    		infoMsg.showAndWait();
			}
		}
    	//추가 또는 수정 작업 완료 후에 원래의 상태로 되돌린다.
    	//(활성화/비활성화 처음상태로 돌려놓기)
    	memId.setEditable(false); //편집 불가 상태
    	memName.setEditable(false);
    	memPw.setEditable(false);
    	memEmail.setEditable(false);
    	memGender.setEditable(false);
    	
    	okbtn.setDisable(true); //비활성화
    	cancelbtn.setDisable(true);
    	
    	addbtn.setDisable(false);//활성화
    	modbtn.setDisable(false);
    	delbtn.setDisable(false);
    	
    	tableview.setDisable(false); //테이블뷰 활성화
    	work = "";
    	
    }
//    //========================================TableView클릭시=========================================

	public void resetTB(ActionEvent event) {
		try {
			setTableView();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    // TableView를 클릭했을 때 처리
    @FXML
    void tableClick(ActionEvent event) throws SQLException {
    	setTableView();
    }		

    //초기화되는 부분
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//initialize 메서드에 객체를 할당받는 코드
		comboBox.getItems().addAll("ID", "NAME", "PW", "EMAIL", "GENDER");
//		list = FXCollections.observableArrayList("ID", "NAME", "PW", "EMAIL");

		comboBox.setValue("ID");
		
		adminService = new AdminServiceImpl();
		comService = new CommonServiceImpl();
		registService = new RegistServiceImpl();

		try {
			setTableView();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
//    
    //DB에서 전체 회원 정보를 가져와 TebleView에 셋팅
	public void setTableView() throws SQLException {
		
		DBconn.connect();
		listview = FXCollections.observableArrayList();
		
		tbMemId.setCellValueFactory(new PropertyValueFactory<>("ID")); //오류
    	tbMemName.setCellValueFactory(new PropertyValueFactory<>("NAME"));
    	tbMemPw.setCellValueFactory(new PropertyValueFactory<>("PW"));
    	tbMemEmail.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
    	tbMemGender.setCellValueFactory(new PropertyValueFactory<>("GENDER"));

    	    	
    	try {
    		DBconn.connect();
    		
    		String sql = "SELECT ID, NAME, PW, EMAIL, GENDER FROM members";
    		Statement st = DBconn.connect().createStatement();
    		ResultSet rs = st.executeQuery(sql);
    		
    		while(rs.next()) {
    			listview.add(new members(
    					rs.getString("ID"),
    					rs.getString("NAME"),
    					rs.getString("PW"),
    					rs.getString("EMAIL"),
    					rs.getString("GENDER")
    					));
    		}
    		    		
    		tableview.setItems(listview);

    	}catch(SQLException e) {
    		System.out.println(e);
    	}

    	//tableview.setItems(data);
    }
	
	
	
	public void SearchID() throws SQLException {
		
		DBconn.connect();
		listview = FXCollections.observableArrayList();
		
		String comboGet = comboBox.getSelectionModel().getSelectedItem().toString(); //ex) ID, PW, NAME, EMAIL, Gender추출됨.

    	TextField IDsearch = (TextField)root.lookup("#IDsearch");
    	String getCol = IDsearch.getText();
		
    	try {
    		DBconn.connect();
    		
    		String sql = "SELECT ID, NAME, PW, EMAIL, GENDER FROM members";
    		Statement st = DBconn.connect().createStatement();
    		ResultSet rs = st.executeQuery(sql);
    		

    		System.out.println(getCol);
    		
    		while(rs.next()) {        	    			
    			tbMemId.setCellValueFactory(new PropertyValueFactory<>("ID")); 
    	    	tbMemName.setCellValueFactory(new PropertyValueFactory<>("NAME"));
    	    	tbMemPw.setCellValueFactory(new PropertyValueFactory<>("PW"));
    	    	tbMemEmail.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
    	    	tbMemGender.setCellValueFactory(new PropertyValueFactory<>("GENDER"));

    	    	
    			String getID = rs.getString("ID");
    			String getName = rs.getString("NAME");
    			String getPw = rs.getString("PW");
    			String getEmail = rs.getString("EMAIL");
    			String getGender = rs.getString("GENDER");

    			
//    			TextField IDsearch = (TextField)root.lookup("#IDsearch");
    			if (comboGet.equals("ID")){ //만약 comboGet의 값이 ID라면 ID로 검색가능
	    			if(getID.equals(getCol)) {
	        			listview.add(new members(
	        					rs.getString("ID"),
	        					rs.getString("NAME"),
	        					rs.getString("PW"),
	        					rs.getString("EMAIL"),
	        					rs.getString("GENDER")
	        					));
	        		}
    			}
    			else if(comboGet.equals("NAME")){ //만약 comboGet의 값이 ID라면 ID로 검색가능
	    			if(getName.equals(getCol)) {
	        			listview.add(new members(
	        					rs.getString("ID"),
	        					rs.getString("NAME"),
	        					rs.getString("PW"),
	        					rs.getString("EMAIL"),
	        					rs.getString("GENDER")
	        					));
	        		}
    			}
    			else if(comboGet.equals("PW")){ //만약 comboGet의 값이 ID라면 ID로 검색가능
	    			if(getPw.equals(getCol)) {
	        			listview.add(new members(
	        					rs.getString("ID"),
	        					rs.getString("NAME"),
	        					rs.getString("PW"),
	        					rs.getString("EMAIL"),
	        					rs.getString("GENDER")
	        					));
	        		}
    			}
    			else if(comboGet.equals("EMAIL")){ //만약 comboGet의 값이 ID라면 ID로 검색가능
	    			if(getEmail.equals(getCol)) {
	        			listview.add(new members(
	        					rs.getString("ID"),
	        					rs.getString("NAME"),
	        					rs.getString("PW"),
	        					rs.getString("EMAIL"),
	        					rs.getString("GENDER")
	        					));
	        		}
    			}
    			else if(comboGet.equals("GENDER")){ //만약 comboGet의 값이 ID라면 ID로 검색가능
	    			if(getGender.equals(getCol)) {
	        			listview.add(new members(
	        					rs.getString("ID"),
	        					rs.getString("NAME"),
	        					rs.getString("PW"),
	        					rs.getString("EMAIL"),
	        					rs.getString("GENDER")
	        					));
	        		}
    			}
    		}

    		tableview.setItems(listview);

    	}catch(SQLException e) {
    		System.out.println(e);
    	}

    	//tableview.setItems(data);
    }

	@FXML
	void searchClick(ActionEvent event) throws SQLException {
		SearchID();
	}	

    public void alert(String head, String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("경고");
		alert.setHeaderText(head);
		alert.setContentText(msg);
		
		alert.showAndWait(); //Alert창 보여주기
	}
	
	public void infoMsg(String head, String msg) {
		Alert info = new Alert(AlertType.INFORMATION);
		
		info.setTitle("정보");
		info.setHeaderText(head);
		info.setContentText(msg);
		
		info.showAndWait(); //Alert창 보여주기
	}

	public ButtonType confirm(String head, String msg) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("확인");
		confirm.setHeaderText(head);
		confirm.setContentText(msg);
		return confirm.showAndWait().get();

	}

}
