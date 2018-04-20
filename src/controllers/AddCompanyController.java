package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import database.DatabaseHelper;
import exceptions.ValidateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.Logger;
import models.Company;

public class AddCompanyController implements Initializable {

	private LoadWindow loadWindow = new LoadWindow();
	private DatabaseHelper databaseHelper;
	private Company company;

	public void setCompany(Company company) {
		this.company = company;
	}

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField textFieldCompanyId;

	@FXML
	private TextField textFiedlCompanyName;

	@FXML
	private Label labelCompanyId;

	@FXML
	private Label labelCompanyName;

	@FXML
	private Button buttonAdd;

	@FXML
	private Button buttonCancel;

	@FXML
	void addCompanyEvent(ActionEvent event) {
		try {
			if (this.company != null) {
				this.updateCompany();
			} else {
				Company company = createCompany();
				this.databaseHelper.insertCompany(company);
			}
			this.loadWindow.closeWindow(this.anchorPane);
			Stage stage = this.loadWindow.loadMainForm();
			stage.show();
		} catch (ValidateException | NumberFormatException | SQLException | IOException e) {
			Logger.saveException(e);
			this.clearFormFields();
			this.loadWindow.displayMessage(AlertType.ERROR, e.getClass().getName().toString() + " " + e.getMessage(),
					ButtonType.OK);
		}
	}

	@FXML
	void cancelAddCompanyEvent(ActionEvent event) throws IOException {
		this.loadWindow.closeWindow(this.anchorPane);
		Stage mainMenu = this.loadWindow.loadMainForm();
		mainMenu.show();
	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert textFieldCompanyId != null : "fx:id=\"textFieldCompanyId\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert textFiedlCompanyName != null : "fx:id=\"textFiedlCompanyName\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert labelCompanyId != null : "fx:id=\"labelCompanyId\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert labelCompanyName != null : "fx:id=\"labelCompanyName\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert buttonAdd != null : "fx:id=\"buttonAdd\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'AddCompany.fxml'.";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.databaseHelper = DatabaseHelper.getInstance();
			if (this.company != null) {
				this.fillFormWithCompanyDetails();
				this.buttonAdd.setText("Update");
			} else {
				this.textFieldCompanyId.setVisible(false);
				this.labelCompanyId.setVisible(false);
			}
		} catch (SQLException e) {
			Logger.saveException(e);
		}
	}

	private Company createCompany() throws ValidateException {
		String companyName = this.textFiedlCompanyName.getText();
		Company company = new Company(companyName);
		return company;
	}

	private void updateCompany() throws NumberFormatException, ValidateException, SQLException {
		int companyId = Integer.parseInt(this.textFieldCompanyId.getText());
		String companyName = this.textFiedlCompanyName.getText();
		this.company.setCompanyId(companyId);
		this.company.setCompanyName(companyName);
		this.databaseHelper.updateCompany(companyId, this.company);
	}

	private void clearFormFields() {
		if (this.company != null) {
			this.textFieldCompanyId.setText(this.company.getCompanyId() + "");
			this.textFiedlCompanyName.setText(this.company.getCompanyName());
		} else {
			this.textFieldCompanyId.setText("");
			this.textFiedlCompanyName.setText("");
		}
	}

	private void fillFormWithCompanyDetails() {
		this.textFieldCompanyId.setEditable(false);
		this.textFieldCompanyId.setText(this.company.getCompanyId() + "");
		this.textFiedlCompanyName.setText(this.company.getCompanyName());
	}
}