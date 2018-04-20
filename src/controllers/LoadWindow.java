package controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Company;
import models.Department;
import models.Employee;

public class LoadWindow {
	public Stage loadMainForm() throws IOException {
		Stage stage = null;
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MainForm.fxml"));
		stage = new Stage();
		Scene scene = new Scene(parent);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Main Form");
		return stage;
	}

	public Stage loadAddCompany(Company company, boolean editState) throws IOException {
		Stage stage = null;
		FXMLLoader fxmlLoader = null;
		Parent parent = null;
		Scene scene = null;
		if (company != null && editState) {
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddCompany.fxml"));
			fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> param) {
					if (param == AddCompanyController.class) {
						AddCompanyController addCompanyController = new AddCompanyController();
						addCompanyController.setCompany(company);
						return addCompanyController;
					} else {
						try {
							param.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return param;
				}
			});
			parent = fxmlLoader.load();
			stage = new Stage();
			scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Edit Company");
		} else {
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddCompany.fxml"));
			parent = fxmlLoader.load();
			stage = new Stage();
			scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Add Company");
		}
		return stage;
	}

	public Stage loadAddDepartment(Company company, Department department, boolean editState) throws IOException {
		Stage stage = null;
		FXMLLoader fxmlLoader = null;
		Parent parent = null;
		Scene scene = null;
		if (department != null && editState) {
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddDepartment.fxml"));
			fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> param) {
					if (param == AddDepartmentController.class) {
						AddDepartmentController addDepartmentController = new AddDepartmentController();
						addDepartmentController.setCompany(company);
						addDepartmentController.setDepartment(department);
						return addDepartmentController;
					} else {
						try {
							param.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return param;
				}
			});
			parent = fxmlLoader.load();
			stage = new Stage();
			scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Edit Department");
		} else {
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddDepartment.fxml"));
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddDepartment.fxml"));
			fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> param) {
					if (param == AddDepartmentController.class) {
						AddDepartmentController addDepartmentController = new AddDepartmentController();
						addDepartmentController.setCompany(company);
						return addDepartmentController;
					} else {
						try {
							param.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return param;
				}
			});
			parent = fxmlLoader.load();
			stage = new Stage();
			scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Add Department");
		}
		return stage;
	}

	public void closeWindow(AnchorPane pane) {
		Stage stage = (Stage) (pane.getScene().getWindow());
		stage.close();
	}

	public Stage loadAddEmployee(Company company, Department department, Employee employee, boolean editState)
			throws IOException {
		FXMLLoader fxmlLoader = null;
		Stage stage = null;
		Parent parent = null;
		Scene scene = null;
		if (company != null && department != null && employee != null && editState) {
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddEmployee.fxml"));
			fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> param) {
					if (param == AddEmployeeController.class) {
						AddEmployeeController addEmployeeController = new AddEmployeeController();
						addEmployeeController.setCompany(company);
						addEmployeeController.setDepartment(department);
						addEmployeeController.setEmployee(employee);
						return addEmployeeController;
					} else {
						try {
							param.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return param;
				}
			});
			parent = fxmlLoader.load();
			stage = new Stage();
			scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Edit Employee");
		} else {
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddEmployee.fxml"));
			fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> param) {
					if (param == AddEmployeeController.class) {
						AddEmployeeController addEmployeeController = new AddEmployeeController();
						addEmployeeController.setCompany(company);
						addEmployeeController.setDepartment(department);
						return addEmployeeController;
					} else {
						try {
							param.newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return param;
				}
			});
			parent = fxmlLoader.load();
			stage = new Stage();
			scene = new Scene(parent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Add Employee");
		}
		return stage;
	}

	public void displayMessage(AlertType alertType, String message, ButtonType buttonType) {
		Alert alert = new Alert(alertType, message, buttonType);
		alert.show();
	}
}