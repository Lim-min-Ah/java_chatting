package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import Service.ChatRoomService;
import Service.CommonService;
import Service.LoginService;
import Service.friendService;
import ServiceImpl.ChatRoomServiceImpl;
import ServiceImpl.CommonServiceImpl;
import ServiceImpl.LoginServiceImpl;
import ServiceImpl.friendServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class friendController implements Initializable{

	private Parent root;
	private CommonService comService;
	private friendService friendService;
	private MyDB mydb = new MyDB();
	
	@FXML private TableColumn<members, String> tbFriend;
	@FXML private TableView<members> tableViewFriend;
	@FXML private Text MyName;
	@FXML private Text numFriend;
	
	ObservableList<members> listview;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		comService = new CommonServiceImpl();
		friendService = new friendServiceImpl();
		MyName.setText(MyDB.getUserName);
		numFriend.setText(String.valueOf(getNumF()));
		
		try {
			setTableView();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
	
	public int getNumF() {// 친구목록에 있는 친구 수 세기
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT ID, NAME from members");

			while (rs.next()) {				
				String getID = rs.getString("ID");
				String getNAME = rs.getString("NAME");
				
//					data.setmem(getNAME);
				System.out.println("ID/PW 정보 : ("+getID+","+getNAME+")");//잘 가지고 왔나 확인
				
				result += 1; //반복문이 한번씩 돌 때마다 +1, 친구 수를 세기 위함.
				
				}

		}catch (Exception e) {
			System.out.println("select 오류1 : "+e.getMessage());
		}

		
		return result;
		
	}
	
	@FXML
    public void GoChatRoom(ActionEvent event) {
    	if (tableViewFriend.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("오류!");
        	alert.setHeaderText("대화할 친구를 선택한 후 사용하세요.");
        	alert.setContentText("다시 시도해주세요!");
        	alert.showAndWait();

			return;
		}

		//선택한 데이터의 회원ID값 가져오기
		//방법1)
		String getUName = tableViewFriend.getSelectionModel().getSelectedItem().toString();

//		String Port = mydb.getIpPort(getInfo); //mydb에서 port번호 얻기
//		MyDB.PortNUM = Port;
		
		getUName = tableViewFriend.getSelectionModel().getSelectedItem().getNAME();
		
		mydb.inputYOUChatRoom("개인채팅방", "2", getUName, "속성", "127.0.0.1", "8888");
		    		
		String fxmlURL4 = "/application/Client.fxml";
		comService.showChatPage(fxmlURL4);
    		    		
    }
	
	//테이블 초기화
	public void setTableView() throws SQLException {
		
		DBconn.connect();
		listview = FXCollections.observableArrayList();
		
    	tbFriend.setCellValueFactory(new PropertyValueFactory<>("NAME"));

    	    	
    	try {
    		DBconn.connect();
    		
    		String sql = "SELECT NAME FROM members";
    		Statement st = DBconn.connect().createStatement();
    		ResultSet rs = st.executeQuery(sql);
    		
    		while(rs.next()) {
    			listview.add(new members(
    					rs.getString("NAME")
    					));
    		}
    		    		
    		tableViewFriend.setItems(listview);

    	}catch(SQLException e) {
    		System.out.println(e);
    	}

    }
	
	
	
}
