package application;

import javafx.beans.property.SimpleStringProperty;

public class members {
	
	private SimpleStringProperty  ID;
	private SimpleStringProperty  NAME;
	private SimpleStringProperty  PW;
	private SimpleStringProperty  EMAIL;
	private SimpleStringProperty  GENDER;

		
	public members(String ID, String NAME, String PW, String EMAIL, String GENDER) {
		this.ID = new SimpleStringProperty(ID);
		this.NAME = new SimpleStringProperty(NAME);
		this.PW = new SimpleStringProperty(PW);
		this.EMAIL = new SimpleStringProperty(EMAIL);
		this.GENDER = new SimpleStringProperty(GENDER);
	}


	public members(String NAME) {
		this.NAME = new SimpleStringProperty(NAME);	}

	public String getID() {
		return ID.get();
	}
	public void setID(String ID) {
	       this.ID = new SimpleStringProperty(ID);
	       
	}
	
	public String getNAME() {
		return NAME.get();
	}
	
	public void setNAME(String NAME) {
	       this.ID = new SimpleStringProperty(NAME);
	   }
	
	
	public String getPW() {
		return PW.get();
	}
	
	public void setPW(String PW) {
	       this.ID = new SimpleStringProperty(PW);
	   }
	
	
	public String getEMAIL() {
		return EMAIL.get();
	}
	
	public void setEMAIL(String EMAIL) {
	       this.ID = new SimpleStringProperty(EMAIL);
	   }
	
	public String getGENDER() {
		return GENDER.get();
	}
	public void setGENDER(String GENDER) {
	       this.ID = new SimpleStringProperty(GENDER);
	       
	   }
	@Override
	public String toString() {
		return this.getNAME() + "(" + this.getNAME()+")";
	}
}
