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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.Logger;
import models.Company;
import models.Department;
import models.Employee;
import models.EmployeeType;

public class AddEmployeeController implements Initializable {

	private LoadWindow loadWindow = new LoadWindow();
	private DatabaseHelper databaseHelper;
	private Company company;
	private Department department;
	private Employee employee;

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button buttonAdd;

	@FXML
	private Button buttonCancel;

	@FXML
	private TextField textFieldEmployeeFirstName;

	@FXML
	private TextField textFieldEmployeeLasrName;

	@FXML
	private TextField textFieldEmployeeAge;

	@FXML
	private TextField textFieldEmployeeNIN;

	@FXML
	private TextField textFieldEmployeeId;

	@FXML
	private TextField textFieldEmployeeSalary;

	@FXML
	private ComboBox<EmployeeType> comboBoxEmployeeType;

	@FXML
	private Label labelEmployeeFirstName;

	@FXML
	private Label labelEmployeeLastName;

	@FXML
	private Label labelEmployeeAge;

	@FXML
	private Label labelEmployeeNIN;

	@FXML
	private Label labelEmployeeId;

	@FXML
	private Label labelEmployeeType;

	@FXML
	private Label labelEmployeeSalary;

	@FXML
	void addEmployeeEvent(ActionEvent event) throws SQLException, IOException {
		try {
			if (this.company != null && this.department != null && this.employee != null) {
				this.updateEmployee();
			} else {
				Employee employee = createEmplyee();
				int companyId = this.company.getCompanyId();
				int departmentId = this.department.getDepartmentId();
				this.databaseHelper.insertEmployee(companyId, departmentId, employee);
			}
			this.loadWindow.closeWindow(this.anchorPane);
			Stage stage = this.loadWindow.loadMainForm();
			stage.show();
		} catch (NumberFormatException | ValidateException e) {
			Logger.saveException(e);
			this.clearFormFields();
			this.loadWindow.displayMessage(AlertType.ERROR, e.getClass().getName().toString() + " " + e.getMessage(),
					ButtonType.OK);
		}
	}

	@FXML
	void cancelAddEmployeeEvent(ActionEvent event) throws IOException {
		this.loadWindow.closeWindow(this.anchorPane);
		Stage stage = this.loadWindow.loadMainForm();
		stage.show();
	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert buttonAdd != null : "fx:id=\"buttonAdd\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert textFieldEmployeeFirstName != null : "fx:id=\"textFieldEmployeeFirstName\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert textFieldEmployeeLasrName != null : "fx:id=\"textFieldEmployeeLasrName\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert textFieldEmployeeAge != null : "fx:id=\"textFieldEmployeeAge\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert textFieldEmployeeNIN != null : "fx:id=\"textFieldEmployeeNIN\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert textFieldEmployeeId != null : "fx:id=\"textFieldEmployeeId\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert textFieldEmployeeSalary != null : "fx:id=\"textFieldEmployeeSalary\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert comboBoxEmployeeType != null : "fx:id=\"comboBoxEmployeeType\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeFirstName != null : "fx:id=\"labelEmployeeFirstName\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeLastName != null : "fx:id=\"labelEmployeeLastName\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeAge != null : "fx:id=\"labelEmployeeAge\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeNIN != null : "fx:id=\"labelEmployeeNIN\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeId != null : "fx:id=\"labelEmployeeId\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeType != null : "fx:id=\"labelEmployeeType\" was not injected: check your FXML file 'AddEmployee.fxml'.";
		assert labelEmployeeSalary != null : "fx:id=\"labelEmployeeSalary\" was not injected: check your FXML file 'AddEmployee.fxml'.";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			if (this.employee != null) {
				this.textFieldEmployeeId.setEditable(false);
				this.fillFormWithEmployeeDetails();
				this.buttonAdd.setText("Update");
			} else {
				this.labelEmployeeId.setVisible(false);
				this.textFieldEmployeeId.setVisible(false);
			}
			this.databaseHelper = DatabaseHelper.getInstance();
			this.comboBoxEmployeeType.getItems().setAll(EmployeeType.values());
		} catch (SQLException e) {
			Logger.saveException(e);
		}
	}

	private Employee createEmplyee() throws NumberFormatException, ValidateException {
		String personFirstName = this.textFieldEmployeeFirstName.getText();
		String personLastName = this.textFieldEmployeeLasrName.getText();
		int personAge = Integer.parseInt(this.textFieldEmployeeAge.getText());
		String personCNP = this.textFieldEmployeeNIN.getText();
		EmployeeType employeeType = this.comboBoxEmployeeType.getValue();
		float employeeSalary = Float.parseFloat(this.textFieldEmployeeSalary.getText());
		Employee employee = new Employee(personFirstName, personLastName, personAge, personCNP, employeeType,
				employeeSalary);
		return employee;
	}

	private void updateEmployee() throws SQLException, NumberFormatException, ValidateException {
		String personFirstName = this.textFieldEmployeeFirstName.getText();
		String personLastName = this.textFieldEmployeeLasrName.getText();
		int personAge = Integer.parseInt(this.textFieldEmployeeAge.getText());
		EmployeeType employeeType = this.comboBoxEmployeeType.getValue();
		float employeeSalary = Float.parseFloat(this.textFieldEmployeeSalary.getText());
		String personNIN = this.textFieldEmployeeNIN.getText();
		int companyId = this.company.getCompanyId();
		int departmentId = this.department.getDepartmentId();
		int employeeId = this.employee.getEmployeeId();
		this.employee.setEmployeeId(employeeId);
		this.employee.setPersonFirstName(personFirstName);
		this.employee.setPersonLastName(personLastName);
		this.employee.setPersonAge(personAge);
		this.employee.setEmployeeType(employeeType);
		this.employee.setEmployeeSalary(employeeSalary);
		this.employee.setPersonNIN(personNIN);
		this.databaseHelper.updateEmployee(companyId, departmentId, this.employee);
	}

	private void fillFormWithEmployeeDetails() {
		this.textFieldEmployeeAge.setText(this.employee.getPersonAge() + "");
		this.textFieldEmployeeFirstName.setText(this.employee.getPersonFirstName());
		this.textFieldEmployeeLasrName.setText(this.employee.getPersonLastName());
		this.textFieldEmployeeId.setText(this.employee.getEmployeeId() + "");
		this.textFieldEmployeeNIN.setText(this.employee.getPersonNIN());
		this.textFieldEmployeeSalary.setText(this.employee.getEmployeeSalary() + "");
		this.comboBoxEmployeeType.getSelectionModel().select(this.employee.getEmployeeType());
	}

	private void clearFormFields() {
		if (this.department != null && this.employee != null && this.company != null) {
			this.textFieldEmployeeAge.setText(this.employee.getPersonAge() + "");
			this.textFieldEmployeeFirstName.setText(this.employee.getPersonFirstName());
			this.textFieldEmployeeLasrName.setText(this.employee.getPersonLastName());
			this.textFieldEmployeeId.setText(this.employee.getEmployeeId() + "");
			this.textFieldEmployeeNIN.setText(this.employee.getPersonNIN());
			this.textFieldEmployeeSalary.setText(this.employee.getEmployeeSalary() + "");
			this.comboBoxEmployeeType.getSelectionModel().select(this.employee.getEmployeeType());
		} else {
			this.textFieldEmployeeAge.setText("");
			this.textFieldEmployeeFirstName.setText("");
			this.textFieldEmployeeLasrName.setText("");
			this.textFieldEmployeeId.setText("");
			this.textFieldEmployeeNIN.setText("");
			this.textFieldEmployeeSalary.setText("");
			this.comboBoxEmployeeType.getSelectionModel().select(0);
		}
	}
}