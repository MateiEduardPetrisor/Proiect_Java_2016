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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.Logger;
import models.Company;
import models.Department;

public class AddDepartmentController implements Initializable {

	private DatabaseHelper databaseHelper;
	private LoadWindow loadWindow = new LoadWindow();
	private Company company;
	private Department department;

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Label labelDepartmentId;

	@FXML
	private Label labelDepartmentName;

	@FXML
	private TextField textFieldDepartmentId;

	@FXML
	private TextField textFieldDepartmentName;

	@FXML
	private Button buttonAdd;

	@FXML
	private Button buttonCancel;

	@FXML
	void addDepartmentEvent(ActionEvent event) {
		try {
			if (this.department != null) {
				this.updateDepartment();
			} else {
				Department department = createDepartment();
				int companyId = this.company.getCompanyId();
				this.databaseHelper.insertDepartment(companyId, department);
			}
			this.loadWindow.closeWindow(this.anchorPane);
			Stage mainForm = this.loadWindow.loadMainForm();
			mainForm.show();
		} catch (ValidateException | SQLException | IOException e) {
			Logger.saveException(e);
			this.clearFormFields();
			this.loadWindow.displayMessage(AlertType.ERROR, e.getClass().getName().toString() + " " + e.getMessage(),
					ButtonType.OK);
		}
	}

	@FXML
	void cancelAddDepartmentEvent(ActionEvent event) throws IOException {
		this.loadWindow.closeWindow(this.anchorPane);
		Stage mainForm = this.loadWindow.loadMainForm();
		mainForm.show();
	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'AddDepartment.fxml'.";
		assert labelDepartmentId != null : "fx:id=\"labelDepartmentId\" was not injected: check your FXML file 'AddDepartment.fxml'.";
		assert labelDepartmentName != null : "fx:id=\"labelDepartmentName\" was not injected: check your FXML file 'AddDepartment.fxml'.";
		assert textFieldDepartmentId != null : "fx:id=\"textFieldDepartmentId\" was not injected: check your FXML file 'AddDepartment.fxml'.";
		assert textFieldDepartmentName != null : "fx:id=\"textFieldDepartmentName\" was not injected: check your FXML file 'AddDepartment.fxml'.";
		assert buttonAdd != null : "fx:id=\"buttonAdd\" was not injected: check your FXML file 'AddDepartment.fxml'.";
		assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'AddDepartment.fxml'.";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.databaseHelper = DatabaseHelper.getInstance();
			if (this.department != null) {
				this.fillFormDepartmentsDetails();
				this.buttonAdd.setText("Update");
			} else {
				this.labelDepartmentId.setVisible(false);
				this.textFieldDepartmentId.setVisible(false);
			}
		} catch (SQLException e) {
			Logger.saveException(e);
		}
	}

	private Department createDepartment() throws ValidateException {
		String departmentName = this.textFieldDepartmentName.getText();
		Department department = new Department(departmentName);
		return department;
	}

	private void clearFormFields() {
		if (this.department != null) {
			this.textFieldDepartmentId.setText(this.department.getDepartmentId() + "");
			this.textFieldDepartmentName.setText(this.department.getDepartmentName());
		} else {
			this.textFieldDepartmentId.setText("");
			this.textFieldDepartmentName.setText("");
		}
	}

	private void fillFormDepartmentsDetails() {
		this.textFieldDepartmentId.setEditable(false);
		this.textFieldDepartmentId.setText(this.department.getDepartmentId() + "");
		this.textFieldDepartmentName.setText(this.department.getDepartmentName());
	}

	private void updateDepartment() throws SQLException, ValidateException {
		int departmentId = Integer.parseInt(this.textFieldDepartmentId.getText());
		String departmentName = this.textFieldDepartmentName.getText();
		int companyId = this.company.getCompanyId();
		this.department.setDepartmentId(departmentId);
		this.department.setDepartmentName(departmentName);
		this.databaseHelper.updateDepartment(companyId, this.department);
	}
}