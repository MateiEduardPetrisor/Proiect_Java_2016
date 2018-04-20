package database;

import java.sql.SQLException;
import java.util.List;
import exceptions.ValidateException;
import models.Company;
import models.Department;
import models.Employee;

public interface DatabaseInterface {

	public void createTables() throws SQLException;

	public void deleteTables() throws SQLException;

	public void insertCompany(Company company) throws SQLException;

	public void updateCompany(int companyId, Company company) throws SQLException;

	public void deleteCompany(int companyId) throws SQLException;

	public List<Company> readCompanies() throws SQLException, ValidateException;

	public void insertDepartment(int companyId, Department departament) throws SQLException;

	public void updateDepartment(int companyId, Department department) throws SQLException;

	public void deleteDepartment(int companyId, int departmentId) throws SQLException;

	public List<Department> readDepartments(int companyId) throws SQLException, ValidateException;

	public void insertEmployee(int companyId, int departmentId, Employee employee) throws SQLException;

	public void updateEmployee(int companyId, int departmentId, Employee employee) throws SQLException;

	public void deleteEmployee(int companyId, int departmentId, int employeeId) throws SQLException;

	public List<Employee> readEmployees(int companyId, int departmentId) throws SQLException, ValidateException;
}