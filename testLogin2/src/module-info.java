module testLogin {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.scripting;
	requires org.mariadb.jdbc;
	requires java.base;
	requires javafx.web;

	opens application to javafx.graphics, javafx.fxml, javafx.base;
	
	
}
