package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CSVTableView extends TableView<String>{
	public CSVTableView() {
		
	}
	public void inputCSV(String delimiter, String file) throws IOException {
		

	      // Get CSV file lines as List
	      List<String> lines = Files.readAllLines(Paths.get(file));

	      // Get the header row
	      String[] firstRow = lines.get(0).split(delimiter);

	      // For each header/column, create TableColumn
	      for (String columnName : firstRow) {
	          TableColumn<String, String> column = new TableColumn<>(columnName);
	          this.getColumns().add(column);

	          column.setCellValueFactory(cellDataFeatures -> {
	              String values = cellDataFeatures.getValue();
	              String[] cells = values.split(delimiter);
	              int columnIndex = cellDataFeatures.getTableView().getColumns().indexOf(cellDataFeatures.getTableColumn());
	              if (columnIndex >= cells.length) {
	                  return new SimpleStringProperty("");
	              } else {
	                  return new SimpleStringProperty(cells[columnIndex]);
	              }
	          });

	          this.setItems(FXCollections.observableArrayList(lines));
	          // Remove header row, as it will be added to the data at this point
	          // this only works if we're sure that our CSV file has a header,
	          // otherwise, we're just deleting data at this point.
	          this.getItems().remove(0);
	        }
	    }
}
