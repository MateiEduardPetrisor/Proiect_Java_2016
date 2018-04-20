package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import database.DatabaseHelper;
import exceptions.ValidateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.Logger;
import models.Company;
import models.Department;
import models.Employee;

public class MainFormController implements Initializable {

	private DatabaseHelper databaseHelper;
	private LoadWindow loadWindow = new LoadWindow();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ListView<Company> listViewCompanies;

	@FXML
	private ContextMenu contextMenuCompanies;

	@FXML
	private MenuItem addCompanyMenuItem;

	@FXML
	private MenuItem updateCompanyMenuItem;

	@FXML
	private MenuItem deleteCompanyMenuItem;

	@FXML
	private ListView<Employee> listViewEmployees;

	@FXML
	private ContextMenu contextMenuEmployees;

	@FXML
	private MenuItem addEmployeeMenuItem;

	@FXML
	private MenuItem updateEmployeeMenuItem;

	@FXML
	private MenuItem deleteEmployeeMenuItem;

	@FXML
	private ListView<Department> listViewDepartments;

	@FXML
	private ContextMenu ContextMenudepartments;

	@FXML
	private MenuItem addDepartmentMenuItem;

	@FXML
	private MenuItem updateDepartmentMenuItem;

	@FXML
	private MenuItem deleteDepartmentMenuItem;

	@FXML
	void listViewCompaniesClickEvent(MouseEvent event) {
		try {
			this.fillListViewDepartments();
		} catch (SQLException | ValidateException e) {
			Logger.saveException(e);
			e.printStackTrace();
		}
	}

	@FXML
	void listViewDepartmentClickEvent(MouseEvent event) {
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		if (department != null) {
			try {
				this.fillListViewEmployees();
			} catch (SQLException | ValidateException e) {
				Logger.saveException(e);
				e.printStackTrace();
			}
		}
	}

	@FXML
	void addCompanyOnAction(ActionEvent event) throws IOException {
		this.loadWindow.closeWindow(this.anchorPane);
		Stage addCompanyStage = this.loadWindow.loadAddCompany(null, false);
		addCompanyStage.show();
	}

	@FXML
	void updateCompanyOnAction(ActionEvent event) throws IOException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		if (company != null) {
			this.loadWindow.closeWindow(anchorPane);
			Stage updateCompanyStage = this.loadWindow.loadAddCompany(company, true);
			updateCompanyStage.show();
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select A Company!", ButtonType.OK);
		}
	}

	@FXML
	void deleteCompanyOnAction(ActionEvent event) {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		if (company != null) {
			try {
				int companyId = company.getCompanyId();
				this.databaseHelper.deleteCompany(companyId);
				this.listViewCompanies.getItems().remove(company);
				this.listViewDepartments.getItems().clear();
				this.listViewEmployees.getItems().clear();
			} catch (SQLException e) {
				Logger.saveException(e);
			}
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select A Company First!", ButtonType.OK);
		}
	}

	@FXML
	void addDepartmentOnAction(ActionEvent event) throws IOException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		if (company != null) {
			this.loadWindow.closeWindow(this.anchorPane);
			Stage addDepartment = this.loadWindow.loadAddDepartment(company, null, false);
			addDepartment.show();
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select A Company First!", ButtonType.OK);
		}
	}

	@FXML
	void updateDepartmentOnAction(ActionEvent event) throws IOException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		if (company != null && department != null) {
			this.loadWindow.closeWindow(anchorPane);
			Stage stage = this.loadWindow.loadAddDepartment(company, department, true);
			stage.show();
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select A Company & Department", ButtonType.OK);
		}
	}

	@FXML
	void deleteDepartmentOnAction(ActionEvent event) throws SQLException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		if (company != null && department != null) {
			int companyId = company.getCompanyId();
			int departmentId = department.getDepartmentId();
			this.databaseHelper.deleteDepartment(companyId, departmentId);
			this.listViewDepartments.getItems().remove(department);
			this.listViewEmployees.getItems().clear();
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select A Company & Department", ButtonType.OK);
		}
	}

	@FXML
	void addEmployeeOnAction(ActionEvent event) throws IOException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		if (company != null && department != null) {
			this.loadWindow.closeWindow(anchorPane);
			Stage stage = this.loadWindow.loadAddEmployee(company, department, null, false);
			stage.show();
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select A Company & Department", ButtonType.OK);
		}
	}

	@FXML
	void updateEmployeeOnAction(ActionEvent event) throws IOException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		Employee employee = this.listViewEmployees.getSelectionModel().getSelectedItem();
		if (company != null && department != null && employee != null) {
			this.loadWindow.closeWindow(anchorPane);
			Stage stage = this.loadWindow.loadAddEmployee(company, department, employee, true);
			stage.show();
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Select Company & Departament", ButtonType.OK);
		}
	}

	@FXML
	void deleteEmployeeOnAction(ActionEvent event) throws SQLException {
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		Employee employee = this.listViewEmployees.getSelectionModel().getSelectedItem();
		if (company != null && department != null && employee != null) {
			int companyId = company.getCompanyId();
			int departmentId = department.getDepartmentId();
			int employeeId = employee.getEmployeeId();
			this.databaseHelper.deleteEmployee(companyId, departmentId, employeeId);
			this.listViewEmployees.getItems().remove(employee);
		} else {
			this.loadWindow.displayMessage(AlertType.ERROR, "Please Select Company, Department & Employee",
					ButtonType.OK);
		}
	}

	@FXML
	void initialize() {
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert listViewCompanies != null : "fx:id=\"listViewCompanies\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert contextMenuCompanies != null : "fx:id=\"contextMenuCompanies\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert addCompanyMenuItem != null : "fx:id=\"addCompanyMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert updateCompanyMenuItem != null : "fx:id=\"updateCompanyMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert deleteCompanyMenuItem != null : "fx:id=\"deleteCompanyMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert addDepartmentMenuItem != null : "fx:id=\"addDepartmentMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert listViewEmployees != null : "fx:id=\"listViewEmployees\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert contextMenuEmployees != null : "fx:id=\"contextMenuEmployees\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert updateEmployeeMenuItem != null : "fx:id=\"updateEmployeeMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert deleteEmployeeMenuItem != null : "fx:id=\"deleteEmployeeMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert listViewDepartments != null : "fx:id=\"listViewDepartments\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert ContextMenudepartments != null : "fx:id=\"ContextMenudepartments\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert updateDepartmentMenuItem != null : "fx:id=\"updateDepartmentMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert deleteDepartmentMenuItem != null : "fx:id=\"deleteDepartmentMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
		assert addEmployeeMenuItem != null : "fx:id=\"addEmployeeMenuItem\" was not injected: check your FXML file 'MainForm.fxml'.";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.databaseHelper = DatabaseHelper.getInstance();
			this.databaseHelper.createTables();
			this.fillListViewCompanies();
		} catch (SQLException | ValidateException e) {
			Logger.saveException(e);
		}
	}

	private void fillListViewCompanies() throws SQLException, ValidateException {
		this.listViewDepartments.getItems().clear();
		this.listViewEmployees.getItems().clear();
		List<Company> lst = this.databaseHelper.readCompanies();
		for (Company c : lst) {
			this.listViewCompanies.getItems().add(c);
		}
	}

	private void fillListViewDepartments() throws SQLException, ValidateException {
		this.listViewDepartments.getItems().clear();
		this.listViewEmployees.getItems().clear();
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		if (company != null) {
			int companyId = company.getCompanyId();
			List<Department> lst = this.databaseHelper.readDepartments(companyId);
			for (Department d : lst) {
				this.listViewDepartments.getItems().add(d);
			}
		}
	}

	private void fillListViewEmployees() throws SQLException, ValidateException {
		this.listViewEmployees.getItems().clear();
		Company company = this.listViewCompanies.getSelectionModel().getSelectedItem();
		Department department = this.listViewDepartments.getSelectionModel().getSelectedItem();
		if (department != null) {
			int companyId = company.getCompanyId();
			int departmentId = department.getDepartmentId();
			List<Employee> lst = this.databaseHelper.readEmployees(companyId, departmentId);
			for (Employee e : lst) {
				this.listViewEmployees.getItems().add(e);
			}
		}
	}
}