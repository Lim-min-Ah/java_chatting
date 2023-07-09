package application;

import javafx.beans.property.SimpleStringProperty;

public class rooms {
	private SimpleStringProperty PK;
	private SimpleStringProperty TITLE;
	private SimpleStringProperty NUM;
	private SimpleStringProperty MAIN;
	private SimpleStringProperty ATTR;
	
	public rooms() {
		
	}
		
	public rooms(String PK, String TITLE, String NUM, String MAIN, String ATTR) {
		this.PK = new SimpleStringProperty(PK);
		this.TITLE = new SimpleStringProperty(TITLE);
		this.NUM = new SimpleStringProperty(NUM);
		this.MAIN = new SimpleStringProperty(MAIN);
		this.ATTR = new SimpleStringProperty(ATTR);
	}
	
	public String getPK() {
		return PK.get();
	}
	
	public void setPK(String PK) {
	       this.PK = new SimpleStringProperty(PK);
	       
	}
	
	public String getTITLE() {
		return TITLE.get();
	}
	
	public void setTITLE(String TITLE) {
	       this.PK = new SimpleStringProperty(TITLE);
	}
	
	public String getNUM() {
		return NUM.get();
	}
	
	public void setNUM(String NUM) {
	       this.PK = new SimpleStringProperty(NUM);
	   }
	
	public String getMAIN() {
		return MAIN.get();
	}
	
	public void setMAIN(String MAIN) {
	       this.PK = new SimpleStringProperty(MAIN);
	   }
	
	public String getATTR() {
		return ATTR.get();
	}
	
	public void setATTR(String ATTR) {
	       this.PK = new SimpleStringProperty(ATTR);
	   }
//	@Override
//	public String toString() {
//		return this.getNAME() + "(" + this.getID()+")";
//	}
}
