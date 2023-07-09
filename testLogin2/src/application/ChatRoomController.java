package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import Service.AdminService;
import Service.ChatRoomService;
import Service.CommonService;
import Service.LoginService;
import Service.RegistService;
import ServiceImpl.AdminServiceImpl;
import ServiceImpl.ChatRoomServiceImpl;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.LoginServiceImpl;
import ServiceImpl.RegistServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ChatRoomController implements Initializable{
	private static final String String = null;
	private Parent root;
	private ChatRoomService chatRoomService;
	private CommonService comService;
	private LoginService loginService;
	private MyDB mydb = new MyDB();
		
    @FXML private TableColumn<rooms, String> tbPK;
    @FXML private TableColumn<rooms, String> tbTITLE;
    @FXML private TableColumn<rooms, String> tbNUM;
    @FXML private TableColumn<rooms, String> tbMAIN;
    @FXML private TableColumn<rooms, String> tbATTR;

    
		
//	@FXML private ResourceBundle resources;
//    @FXML private URL location;
    @FXML private Button goChatBtn;
    @FXML private Button makeRoomBtn;
    @FXML private Button resetBtn;
//    @FXML private Label userName;

    
    
	@FXML private TableView<rooms> tableViewRoom;

	ObservableList<rooms> listview;
	

	public void setRoot(Parent root){
		this.root = root;
	}
	
    //초기화되는 부분
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//initialize 메서드에 객체를 할당받는 코드
		comService = new CommonServiceImpl();
		chatRoomService = new ChatRoomServiceImpl();
		loginService = new LoginServiceImpl();
		
//		userName.setText(loginService.LoginUSer(root));


		try {
			setTableView();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	
	public void GoFriend(ActionEvent event) {
		String fxmlURL7 = "/application/friendPage.fxml";
		comService.showFriendRoom(fxmlURL7);
	}
	
	public void makeRoom(ActionEvent event) {
		String fxmlURL6 = "/application/makeRoom.fxml";
		comService.showMakeRoom(fxmlURL6);
//		loader.setLocation(MakeRoomController.class.getResource("/application/makeRoom.fxml"));
	}

	public void resetTB(ActionEvent event) {
		try {
			setTableView();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    //입장 버튼을 클릭했을 때 처리
    @FXML
    public void GoChatRoom(ActionEvent event) {
    	if (tableViewRoom.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("입장 오류!");
        	alert.setHeaderText("입장할 방을 선택한 후 사용하세요.");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();

			return;
		}
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("채팅방 입장");
    	alert.setContentText("채팅방에 입장하시겠습니까?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		//선택한 데이터의 회원ID값 가져오기
    		//방법1)
    		String getInfo = tableViewRoom.getSelectionModel().getSelectedItem().getPK();

    		String Port = mydb.getIpPort(getInfo); //mydb에서 port번호 얻기
    		MyDB.PortNUM = Port;
    		
    		MyDB.infoPK = tableViewRoom.getSelectionModel().getSelectedItem().getPK();
    		    		
    		String fxmlURL4 = "/application/Client.fxml";
    		comService.showChatPage(fxmlURL4);
    		    		
    	} else {
    		//삭제실패
    		Alert infoMsg2 = new Alert(AlertType.INFORMATION);
    		infoMsg2.setTitle("Information MessageBox");
    		infoMsg2.setHeaderText("작업실패");
    		infoMsg2.setContentText("입장 실패!!");

    		infoMsg2.showAndWait();
    	}

    }
	
//	public void SendBtnPressed(ActionEvent event) { //전송버튼 누를시
//
//		TextField MessageGo = (TextField)root.lookup("#MessageGo"); //lookup 은  Parent 가 가지고 있는 메소드
//		
//		mydb.goMessage(MessageGo.getText());
//		
//		Platform.runLater(()-> {			
//			send(loginService.LoginUSer(root)+": "+MessageGo.getText()+"\n");
//			MessageGo.setText(""); //메시지를 전송했으니까 전송칸 비우기
//			MessageGo.requestFocus();//다시 메시지를 전송할 수 있도록 focus를 설정
//		});
//		
//	}
	

	 // TableView를 클릭했을 때 처리
    @FXML
    void tableClick(ActionEvent event) throws SQLException {
    	setTableView();
    }		

  
    //DB에서 전체 회원 정보를 가져와 TebleView에 셋팅
	public void setTableView() throws SQLException {
		
		DBconn.connect();
		listview = FXCollections.observableArrayList();
		
		tbPK.setCellValueFactory(new PropertyValueFactory<>("PK")); 
		tbTITLE.setCellValueFactory(new PropertyValueFactory<>("TITLE"));
		tbNUM.setCellValueFactory(new PropertyValueFactory<>("NUM"));
		tbMAIN.setCellValueFactory(new PropertyValueFactory<>("MAIN"));
		tbATTR.setCellValueFactory(new PropertyValueFactory<>("ATTR"));

    	
    	try {
    		DBconn.connect();
    		
    		String sql = "SELECT PK, TITLE, NUM, MAIN, ATTR FROM rooms";
    		Statement st = DBconn.connect().createStatement();
    		ResultSet rs = st.executeQuery(sql);
    		
    		while(rs.next()) {
    			listview.add(new rooms(
    					rs.getString("PK"),
    					rs.getString("TITLE"),
    					rs.getString("NUM"),
    					rs.getString("MAIN"),
    					rs.getString("ATTR")
    					));
    		}
    		    		
    		tableViewRoom.setItems(listview);

    	}catch(SQLException e) {
    		System.out.println(e);
    	}
    }
	
}
