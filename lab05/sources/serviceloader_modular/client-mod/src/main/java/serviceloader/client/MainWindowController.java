package serviceloader.client;

import java.util.ArrayList;
import java.util.ServiceLoader;
import ex.api.ClusterAnalysisService;
import ex.api.ClusteringException;
import ex.api.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class MainWindowController {
	@FXML
	private TableView<Record> inputTable;

	@FXML
	private TableColumn<Record, String> recordIdColInput;

	@FXML
	private TableColumn<Record, String> nameColInput;

	@FXML
	private TableColumn<Record, String> lastNameColInput;

	@FXML
	private TableColumn<Record, String> weightColInput;

	@FXML
	private TableColumn<Record, String> heightColInput;

	@FXML
	private ComboBox<String> algorithmComboBox;

	@FXML
	private TableView<Record> outputTable;

	@FXML
	private TableColumn<Record, String> recordIdColOutput;

	@FXML
	private TableColumn<Record, String> nameColOutput;

	@FXML
	private TableColumn<Record, String> lastNameColOutput;

	@FXML
	private TableColumn<Record, String> weightColOutput;

	@FXML
	private TableColumn<Record, String> heightColOutput;

	@FXML
	private TableColumn<Record, String> categoryColOutput;

	@FXML
	private Button submitBtn;
	
	DataSet inputdataSet;
	String[][] inputData;
	String[] headers = { "RecordId", "Name", "Last name", "Weight", "Height", "CategoryId" };

	@FXML
	public void initialize() throws ClusteringException, InterruptedException {
		
		inputTable.setEditable(true);

		Callback<TableColumn<Record, String>, TableCell<Record, String>> cellFactory = new Callback<TableColumn<Record, String>, TableCell<Record, String>>() {
			public TableCell call(TableColumn p) {
				return new EditingCell();
			}
		};
		setProperties(cellFactory);
		String[][] inputData = setUpInitialData();
		ArrayList<Record> inputRecords = new ArrayList<>();
		for (int i = 0; i < inputData.length; i++) {
			inputRecords.add(
					new Record(inputData[i][0], inputData[i][1], inputData[i][2], inputData[i][3], inputData[i][4]));
		}
		ObservableList<Record> inputDataOL = FXCollections.<Record>observableArrayList(inputRecords);
		inputTable.getItems().addAll(inputDataOL);


	}

	private String[][] setUpInitialData() {
		inputdataSet = new DataSet();
		inputdataSet.setHeader(headers);
		String[][] inputData = { { "0", "Micha³", "Cieœlak", "91", "190", "" },
				{ "1", "Tomasz", "Adamek", "75", "175", "" },
				{ "2", "Krzysztof", "W³odarczyk", "79", "185", "" },
				{ "3", "Daniel", "Adamiec", "83", "174", "" },
				{ "4", "Zbigniew", "Cebulak", "79", "158", "" },
				{ "5", "Andrzej", "Go³ota", "39", "182", "" }, 
				{ "6", "Rafa³", "Jackiewicz", "69", "167", "" },
				{ "7", "Jerzy", "Kaczmarek", "71", "169", "" },
				{ "8", "Zbigniew", "Olech", "44", "172", "" },
				{ "9", "Ludwik", "Ochman", "59", "168", "" },
				{ "10", "Arkadiusz", "Ma³ek", "100", "162", "" },
				{ "11", "Henryk", "Kozie³", "52", "144", "" },};
		inputdataSet.setData(inputData);
		return inputData;
	}

	@FXML
	void submitBtnHandler(ActionEvent event) throws ClusteringException, InterruptedException {
		ServiceLoader<ClusterAnalysisService> loader = ServiceLoader.load(ClusterAnalysisService.class);
		DataSet outDataSet = new DataSet();
		String[][] tableDataString = new String[inputTable.getItems().size()][6];
		ObservableList<Record> tableDataOL =  inputTable.getItems();
		
		for (int i = 0; i < inputTable.getItems().size(); i++) {
			tableDataString[i][0] = tableDataOL.get(i).getRecordId();
			tableDataString[i][1] = tableDataOL.get(i).getName();
			tableDataString[i][2] = tableDataOL.get(i).getLastName();
			tableDataString[i][3] = tableDataOL.get(i).getWeight();
			tableDataString[i][4] = tableDataOL.get(i).getHeight();
			tableDataString[i][5] = tableDataOL.get(i).getCategoryId();
		}
		
		inputdataSet.setData(tableDataString);

		String[] options = { Integer.toString(algorithmComboBox.getSelectionModel().getSelectedIndex()) };
		for (ClusterAnalysisService service : loader) {
			service.submit(inputdataSet);
			service.setOptions(options);
			outDataSet = service.retrieve(false);
		}
		Thread.sleep(1500);
		ArrayList<Record> outputRecords = new ArrayList<>();
		String[][] outputData = outDataSet.getData();
		for (int i = 0; i < outputData.length; i++) {
			outputRecords.add(new Record(outputData[i][0], outputData[i][1], outputData[i][2], outputData[i][3],
					outputData[i][4], outputData[i][5]));
		}
		ObservableList<Record> outputDataOL = FXCollections.<Record>observableArrayList(outputRecords);
		outputTable.getItems().setAll(outputDataOL);

	}

	private void setProperties(Callback<TableColumn<Record, String>, TableCell<Record, String>> cellFactory) {
		recordIdColInput.setText(headers[0]);
		nameColInput.setText(headers[1]);
		lastNameColInput.setText(headers[2]);
		weightColInput.setText(headers[3]);
		heightColInput.setText(headers[4]);
		
		recordIdColOutput.setText(headers[0]);
		nameColOutput.setText(headers[1]);
		lastNameColOutput.setText(headers[2]);
		weightColOutput.setText(headers[3]);
		heightColOutput.setText(headers[4]);
		categoryColOutput.setText(headers[5]);
		
		
		algorithmComboBox.getItems().addAll("Group by weight category", "Group by BMI");
		algorithmComboBox.getSelectionModel().selectFirst();
		recordIdColInput.setCellValueFactory(new PropertyValueFactory<>("recordId"));
//		recordIdColInput.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
		nameColInput.setCellValueFactory(new PropertyValueFactory<>("name"));
		lastNameColInput.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		weightColInput.setCellValueFactory(new PropertyValueFactory<>("weight"));
		heightColInput.setCellValueFactory(new PropertyValueFactory<>("height"));

		recordIdColOutput.setCellValueFactory(new PropertyValueFactory<>("recordId"));
		nameColOutput.setCellValueFactory(new PropertyValueFactory<>("name"));
		lastNameColOutput.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		weightColOutput.setCellValueFactory(new PropertyValueFactory<>("weight"));
		heightColOutput.setCellValueFactory(new PropertyValueFactory<>("height"));
		categoryColOutput.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
		
		nameColInput.setCellFactory(cellFactory);
		nameColInput.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Record, String> t) {
				((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
			}
		});
		
		lastNameColInput.setCellFactory(cellFactory);
		lastNameColInput.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Record, String> t) {
				((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
			}
		});
		
		weightColInput.setCellFactory(cellFactory);
		weightColInput.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Record, String> t) {
				((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWeight(t.getNewValue());
			}
		});
		heightColInput.setCellFactory(cellFactory);
		heightColInput.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Record, String> t) {
				((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHeight(t.getNewValue());
			}
		});

	}

	class EditingCell extends TableCell<Record, String> {

		private TextField textField;

		public EditingCell() {
		}

		@Override
		public void startEdit() {
			super.startEdit();

			if (textField == null) {
				createTextField();
			}

			setGraphic(textField);
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText(String.valueOf(getItem()));
			setContentDisplay(ContentDisplay.TEXT_ONLY);
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setGraphic(textField);
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				} else {
					setText(getString());
					setContentDisplay(ContentDisplay.TEXT_ONLY);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						commitEdit(textField.getText());
						cancelEdit();
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}

}
