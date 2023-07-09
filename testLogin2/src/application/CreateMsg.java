package application;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CreateMsg {

	private Stage client;
	private Circle circle;
	private Text text;
	private Label label;
	private Label title;
	private Label size;
	private ImageView iv;
	private Text nowTime;
	
	private FileChooser fc;
	
//	EtcFuntion ef = new EtcFuntion();
	
	public CreateMsg(Stage c) {
		this.client = c ;
		fc = new FileChooser();
		
	}
	
	//check = ture else check = false;
	public HBox makeMsg(String user, String msg, boolean check, String time) {
		try {
			HBox hbox = null;
			if(check) {
				hbox = FXMLLoader.load(FXMLflag.class.getResource("/application/msgMy.fxml")); //True이며 내 msg
			}else {
				hbox = FXMLLoader.load(FXMLflag.class.getResource("/application/msgYou.fxml")); //False이면 상대 msg
			}
			
//			circle = (Circle)hbox.lookup("#circle");
			text = (Text)hbox.lookup("#text");
			label = (Label)hbox.lookup("#label");
			nowTime = (Text)hbox.lookup("#nowTime");
			
//			if(user.getImage() != null) {
//				Image img = new Image(new ByteArrayInputStream(user.getImage()));
//				circle.setFill(new ImagePattern(img));
//			}
			
//			circle.setFill(new Image(/imgFile/사용자 아이콘.jpg));
			label.setText(user);
			text.setText(msg);
			nowTime.setText(time);
//			hbox.setId(user.getId());
			
			return hbox;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
