package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptions.ValidateException;
import models.Company;
import models.Department;
import models.Employee;
import models.EmployeeType;

public class DatabaseHelper implements DatabaseInterface {
	private final static String DATABASE_NAME = "DATABASE.DB";
	private final static String TABLE_COMPANIES = "COMPANIES";
	private final static String TABLE_COMPANIES_ID_PK = "COMPANY_ID_PK";
	private final static String TABLE_COMPANIES_COMPANY_NAME = "COMPANY_NAME";
	private final static String CREATE_TABLE_COMPANIES = "CREATE TABLE " + TABLE_COMPANIES + "(" + TABLE_COMPANIES_ID_PK
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_COMPANIES_COMPANY_NAME + " TEXT NOT NULL" + ")" + ";";
	private final static String INSERT_COMPANY = "INSERT INTO " + TABLE_COMPANIES + "(" + TABLE_COMPANIES_ID_PK + ","
			+ TABLE_COMPANIES_COMPANY_NAME + ")" + " values (?,?)" + ";";
	private final static String UPDATE_COMPANY = "UPDATE " + TABLE_COMPANIES + " SET " + TABLE_COMPANIES_COMPANY_NAME
			+ " = ? " + " WHERE " + TABLE_COMPANIES_ID_PK + " = ? " + ";";
	private final static String DELETE_COMPANY = "DELETE FROM " + TABLE_COMPANIES + " WHERE " + TABLE_COMPANIES_ID_PK
			+ "= ?" + ";";
	private final static String DROP_TABLE_COMPANIES = "DROP TABLE " + TABLE_COMPANIES + ";";
	private final static String TABLE_DEPARTMENTS = "DEPARTMENTS";
	private final static String TABLE_DEPARTMENTS_ID_PK = "ID_DEPARTMENT_PK";
	private final static String TABLE_DEPARTMENTS_COMPANY_ID_FK = "COMPANY_ID";
	private final static String TABLE_DEPARTMENTS_DEPARTMENT_NAME = "DEPARTMENT_NAME";
	private static final String CREATE_TABLE_DEPARTMENTS = "CREATE TABLE " + TABLE_DEPARTMENTS + "( "
			+ TABLE_DEPARTMENTS_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_DEPARTMENTS_COMPANY_ID_FK
			+ " INTEGER REFERENCES " + TABLE_COMPANIES + "(" + TABLE_COMPANIES_ID_PK + ")" + ","
			+ TABLE_DEPARTMENTS_DEPARTMENT_NAME + " TEXT NOT NULL " + ")" + ";";
	private final static String INSERT_DEPARTMENT = "INSERT INTO " + TABLE_DEPARTMENTS + "(" + TABLE_DEPARTMENTS_ID_PK
			+ "," + TABLE_DEPARTMENTS_COMPANY_ID_FK + "," + TABLE_DEPARTMENTS_DEPARTMENT_NAME + ")" + " values (?,?,?)"
			+ ";";
	private final static String UPDATE_DEPARTMENT = "UPDATE " + TABLE_DEPARTMENTS + " SET "
			+ TABLE_DEPARTMENTS_DEPARTMENT_NAME + " = ? " + " WHERE " + TABLE_DEPARTMENTS_ID_PK + " = ? " + " AND "
			+ TABLE_DEPARTMENTS_COMPANY_ID_FK + " = ?" + ";";
	private final static String DELETE_DEPARTMENT = "DELETE FROM " + TABLE_DEPARTMENTS + " WHERE "
			+ TABLE_DEPARTMENTS_ID_PK + "= ?" + ";";
	private final static String DROP_TABLE_DEPARTMENTS = "DROP TABLE " + TABLE_DEPARTMENTS + ";";
	private final static String TABLE_EMPLOYEES = "EMPLOYEES";
	private final static String TABLE_EMPLOYEES_ID_PK = "EMPLOYEE_ID_PK";
	private final static String TABLE_EMPLOYEES_COMPANY_ID_FK = "COMPANY_ID";
	private final static String TABLE_EMPLOYEES_DEPARTMENT_ID_FK = "DEPARTMENT_ID";
	private final static String TABLE_EMPLOYEES_PERSON_FIRST_NAME = "PERSON_FIRST_NAME";
	private final static String TABLE_EMPLOYEES_PERSON_LAST_NAME = "PERSON_LAST_NAME";
	private final static String TABLE_EMPLOYEES_PERSON_AGE = "PERSON_AGE";
	private final static String TABLE_EMPLOYEES_PERSON_CNP = "PERSON_CNP";
	private final static String TABLE_EMPLOYEES_EMPLOYEE_TYPE = "EMPLOYEE_TYPE";
	private final static String TABLE_EMPLOYEES_EMPLOYEE_SALARY = "EMPLOYEE_SALARY";
	private final static String CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_EMPLOYEES + "(" + TABLE_EMPLOYEES_ID_PK
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_EMPLOYEES_COMPANY_ID_FK + " INTEGER REFERENCES "
			+ TABLE_COMPANIES + "(" + TABLE_COMPANIES_ID_PK + ")" + "," + TABLE_EMPLOYEES_DEPARTMENT_ID_FK
			+ " REFERENCES " + TABLE_DEPARTMENTS + "(" + TABLE_DEPARTMENTS_ID_PK + ")" + ","
			+ TABLE_EMPLOYEES_PERSON_FIRST_NAME + " TEXT NOT NULL, " + TABLE_EMPLOYEES_PERSON_LAST_NAME
			+ " TEXT NOT NULL, " + TABLE_EMPLOYEES_PERSON_CNP + " TEXT NOT NULL, " + TABLE_EMPLOYEES_PERSON_AGE
			+ " INTEGR NOT NULL, " + TABLE_EMPLOYEES_EMPLOYEE_TYPE + " TEXT NOT NULL, "
			+ TABLE_EMPLOYEES_EMPLOYEE_SALARY + " REAL NOT NULL" + ")" + ";";
	private final static String INSERT_EMPLOYEE = "INSERT INTO " + TABLE_EMPLOYEES + "(" + TABLE_EMPLOYEES_COMPANY_ID_FK
			+ "," + TABLE_EMPLOYEES_DEPARTMENT_ID_FK + "," + TABLE_EMPLOYEES_PERSON_FIRST_NAME + ","
			+ TABLE_EMPLOYEES_PERSON_LAST_NAME + "," + TABLE_EMPLOYEES_PERSON_CNP + "," + TABLE_EMPLOYEES_PERSON_AGE
			+ "," + TABLE_EMPLOYEES_EMPLOYEE_TYPE + "," + TABLE_EMPLOYEES_EMPLOYEE_SALARY + ")"
			+ "values (?,?,?,?,?,?,?,?)" + ";";
	private final static String DROP_TABLE_EMPLOYEES = "DROP TABLE " + TABLE_EMPLOYEES + ";";
	private final static String DELETE_EMPLOYEE = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " + TABLE_EMPLOYEES_ID_PK
			+ " = ?" + " AND " + TABLE_EMPLOYEES_DEPARTMENT_ID_FK + " = ?" + " AND " + TABLE_EMPLOYEES_COMPANY_ID_FK
			+ " = ?" + ";";
	private final static String GET_DEPARTMENTS_IDS = "SELECT " + TABLE_DEPARTMENTS_ID_PK + " FROM " + TABLE_DEPARTMENTS
			+ " WHERE " + TABLE_DEPARTMENTS_COMPANY_ID_FK + "=?" + ";";
	private final static String GET_EMPLOYEE_IDS = "SELECT " + TABLE_EMPLOYEES_ID_PK + " FROM " + TABLE_EMPLOYEES
			+ " WHERE " + TABLE_EMPLOYEES_COMPANY_ID_FK + " = ?" + " AND " + TABLE_EMPLOYEES_DEPARTMENT_ID_FK + " = ?"
			+ ";";
	private final static String UPDATE_EMPLOYEE = "UPDATE " + TABLE_EMPLOYEES + " SET "
			+ TABLE_EMPLOYEES_PERSON_FIRST_NAME + "= ? " + "," + TABLE_EMPLOYEES_PERSON_LAST_NAME + " = ? " + ","
			+ TABLE_EMPLOYEES_PERSON_CNP + "=?" + "," + TABLE_EMPLOYEES_PERSON_AGE + "=?" + ","
			+ TABLE_EMPLOYEES_EMPLOYEE_TYPE + "=?" + "," + TABLE_EMPLOYEES_EMPLOYEE_SALARY + "=?" + " WHERE "
			+ TABLE_EMPLOYEES_ID_PK + " = ? " + " AND " + TABLE_EMPLOYEES_DEPARTMENT_ID_FK + " = ? " + " AND "
			+ TABLE_EMPLOYEES_COMPANY_ID_FK + " = ?" + ";";
	private final static String SELECT_COMPANIES = "SELECT * FROM " + TABLE_COMPANIES + ";";
	private final static String SELECT_DEPARTMENTS = "SELECT * FROM " + TABLE_DEPARTMENTS + " WHERE "
			+ TABLE_DEPARTMENTS_COMPANY_ID_FK + " =?" + ";";
	private final static String SELECT_EMPLOYESS = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE "
			+ TABLE_EMPLOYEES_COMPANY_ID_FK + " = ? " + " AND " + TABLE_EMPLOYEES_DEPARTMENT_ID_FK + " = ?" + ";";

	private static Connection connectionInstance = null;
	private static DatabaseHelper databaseInstance = null;

	public static synchronized DatabaseHelper getInstance() throws SQLException {
		if (DatabaseHelper.databaseInstance == null) {
			DatabaseHelper.databaseInstance = new DatabaseHelper();
		}
		return DatabaseHelper.databaseInstance;
	}

	private DatabaseHelper() throws SQLException {
		DatabaseHelper.connectionInstance = DriverManager.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
	}

	@Override
	public void createTables() throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.CREATE_TABLE_COMPANIES);
		System.out.println(DatabaseHelper.CREATE_TABLE_DEPARTMENTS);
		System.out.println(DatabaseHelper.CREATE_TABLE_EMPLOYEES);
		boolean isCompanies = checkIfTableExists(DatabaseHelper.TABLE_COMPANIES);
		boolean isDepartments = checkIfTableExists(DatabaseHelper.TABLE_DEPARTMENTS);
		boolean isEmployees = checkIfTableExists(DatabaseHelper.TABLE_EMPLOYEES);
		PreparedStatement preparedStatementCreateDatabase = null;
		if (!isCompanies) {
			preparedStatementCreateDatabase = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.CREATE_TABLE_COMPANIES);
			preparedStatementCreateDatabase.executeUpdate();
			preparedStatementCreateDatabase.close();
		}
		if (!isDepartments) {
			preparedStatementCreateDatabase = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.CREATE_TABLE_DEPARTMENTS);
			preparedStatementCreateDatabase.executeUpdate();
			preparedStatementCreateDatabase.close();
		}
		if (!isEmployees) {
			preparedStatementCreateDatabase = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.CREATE_TABLE_EMPLOYEES);
			preparedStatementCreateDatabase.executeUpdate();
			preparedStatementCreateDatabase.close();
		}
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void deleteTables() throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.DROP_TABLE_COMPANIES);
		System.out.println(DatabaseHelper.DROP_TABLE_DEPARTMENTS);
		System.out.println(DatabaseHelper.DROP_TABLE_EMPLOYEES);
		boolean isCompanies = checkIfTableExists(DatabaseHelper.TABLE_COMPANIES);
		boolean isDepartments = checkIfTableExists(DatabaseHelper.TABLE_DEPARTMENTS);
		boolean isEmployees = checkIfTableExists(DatabaseHelper.TABLE_EMPLOYEES);
		PreparedStatement preparedStatementDeleteDatabase = null;
		if (isCompanies) {
			preparedStatementDeleteDatabase = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.DROP_TABLE_COMPANIES);
			preparedStatementDeleteDatabase.executeUpdate();
			preparedStatementDeleteDatabase.close();
		}
		if (isDepartments) {
			preparedStatementDeleteDatabase = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.DROP_TABLE_DEPARTMENTS);
			preparedStatementDeleteDatabase.executeUpdate();
			preparedStatementDeleteDatabase.close();
		}
		if (isEmployees) {
			preparedStatementDeleteDatabase = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.DROP_TABLE_EMPLOYEES);
			preparedStatementDeleteDatabase.executeUpdate();
			preparedStatementDeleteDatabase.close();
		}
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void insertCompany(Company company) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.INSERT_COMPANY);
		PreparedStatement preparedStatementInsertCompany = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.INSERT_COMPANY);
		preparedStatementInsertCompany.setString(2, company.getCompanyName());
		preparedStatementInsertCompany.execute();
		preparedStatementInsertCompany.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void updateCompany(int companyId, Company company) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.UPDATE_COMPANY);
		PreparedStatement preparedStatementUpdateCompany = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.UPDATE_COMPANY);
		preparedStatementUpdateCompany.setString(1, company.getCompanyName());
		preparedStatementUpdateCompany.setInt(2, companyId);
		preparedStatementUpdateCompany.executeUpdate();
		preparedStatementUpdateCompany.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void deleteCompany(int companyId) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.GET_DEPARTMENTS_IDS);
		PreparedStatement preparedStatementGetDepartmentsIds = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.GET_DEPARTMENTS_IDS);
		preparedStatementGetDepartmentsIds.setInt(1, companyId);
		ResultSet departmentIds = preparedStatementGetDepartmentsIds.executeQuery();
		while (departmentIds.next()) {
			int departmentId = departmentIds.getInt(1);
			System.out.println(DatabaseHelper.GET_DEPARTMENTS_IDS);
			PreparedStatement preparedStatementGetEmployeesIds = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.GET_EMPLOYEE_IDS);
			preparedStatementGetEmployeesIds.setInt(1, companyId);
			preparedStatementGetEmployeesIds.setInt(2, departmentId);
			ResultSet employeesIds = preparedStatementGetEmployeesIds.executeQuery();
			while (employeesIds.next()) {
				System.out.println(DatabaseHelper.DELETE_EMPLOYEE);
				int employeeId = employeesIds.getInt(1);
				PreparedStatement preparedStatementDeleteDepartmentEmployees = DatabaseHelper.connectionInstance
						.prepareStatement(DatabaseHelper.DELETE_EMPLOYEE);
				preparedStatementDeleteDepartmentEmployees.setInt(1, employeeId);
				preparedStatementDeleteDepartmentEmployees.setInt(2, departmentId);
				preparedStatementDeleteDepartmentEmployees.setInt(3, companyId);
				preparedStatementDeleteDepartmentEmployees.executeUpdate();
				preparedStatementDeleteDepartmentEmployees.close();
			}
			PreparedStatement preparedStatementDeleteDepartments = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.DELETE_DEPARTMENT);
			preparedStatementDeleteDepartments.setInt(1, departmentId);
			preparedStatementDeleteDepartments.executeUpdate();
			preparedStatementDeleteDepartments.close();
			employeesIds.close();
		}
		preparedStatementGetDepartmentsIds.close();
		departmentIds.close();
		System.out.println(DatabaseHelper.DELETE_COMPANY);
		PreparedStatement preparedStatementDeleteCompany = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.DELETE_COMPANY);
		preparedStatementDeleteCompany.setInt(1, companyId);
		preparedStatementDeleteCompany.executeUpdate();
		preparedStatementDeleteCompany.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public List<Company> readCompanies() throws SQLException, ValidateException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		List<Company> companiesList = new ArrayList<Company>();
		System.out.println(DatabaseHelper.SELECT_COMPANIES);
		PreparedStatement preparedStatement = connectionInstance.prepareStatement(DatabaseHelper.SELECT_COMPANIES);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int companyId = resultSet.getInt(1);
			String companyName = resultSet.getString(2);
			Company company = new Company(companyName);
			company.setCompanyId(companyId);
			companiesList.add(company);
		}
		resultSet.close();
		preparedStatement.close();
		DatabaseHelper.connectionInstance.close();
		return companiesList;
	}

	@Override
	public void insertDepartment(int companyId, Department departament) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.INSERT_DEPARTMENT);
		PreparedStatement preparedStatementInsertDepartment = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.INSERT_DEPARTMENT);
		preparedStatementInsertDepartment.setInt(2, companyId);
		preparedStatementInsertDepartment.setString(3, departament.getDepartmentName());
		preparedStatementInsertDepartment.execute();
		preparedStatementInsertDepartment.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void updateDepartment(int companyId, Department department) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.UPDATE_DEPARTMENT);
		PreparedStatement preparedStatementUpdateDepartment = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.UPDATE_DEPARTMENT);
		preparedStatementUpdateDepartment.setString(1, department.getDepartmentName());
		preparedStatementUpdateDepartment.setInt(2, department.getDepartmentId());
		preparedStatementUpdateDepartment.setInt(3, companyId);
		preparedStatementUpdateDepartment.executeUpdate();
		preparedStatementUpdateDepartment.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void deleteDepartment(int companyId, int departmentId) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.GET_EMPLOYEE_IDS);
		PreparedStatement preparedStatementGetEmployeesIds = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.GET_EMPLOYEE_IDS);
		preparedStatementGetEmployeesIds.setInt(1, companyId);
		preparedStatementGetEmployeesIds.setInt(2, departmentId);
		ResultSet employeesIds = preparedStatementGetEmployeesIds.executeQuery();
		while (employeesIds.next()) {
			System.out.println(DatabaseHelper.DELETE_EMPLOYEE);
			int employeeId = employeesIds.getInt(1);
			PreparedStatement preparedStatementDeleteDepartmentEmployees = DatabaseHelper.connectionInstance
					.prepareStatement(DatabaseHelper.DELETE_EMPLOYEE);
			preparedStatementDeleteDepartmentEmployees.setInt(1, employeeId);
			preparedStatementDeleteDepartmentEmployees.setInt(2, departmentId);
			preparedStatementDeleteDepartmentEmployees.setInt(3, companyId);
			preparedStatementDeleteDepartmentEmployees.executeUpdate();
			preparedStatementDeleteDepartmentEmployees.close();
		}
		preparedStatementGetEmployeesIds.close();
		employeesIds.close();
		PreparedStatement preparedStatementDeleteDepartment = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.DELETE_DEPARTMENT);
		preparedStatementDeleteDepartment.setInt(1, departmentId);
		preparedStatementDeleteDepartment.executeUpdate();
		preparedStatementDeleteDepartment.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public List<Department> readDepartments(int companyId) throws SQLException, ValidateException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.SELECT_DEPARTMENTS);
		List<Department> departmentsList = new ArrayList<Department>();
		PreparedStatement preparedStatement = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.SELECT_DEPARTMENTS);
		preparedStatement.setInt(1, companyId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int departmentId = resultSet.getInt(1);
			String departmentName = resultSet.getString(3);
			Department department = new Department(departmentName);
			department.setDepartmentId(departmentId);
			departmentsList.add(department);
		}
		resultSet.close();
		preparedStatement.close();
		DatabaseHelper.connectionInstance.close();
		return departmentsList;
	}

	@Override
	public void insertEmployee(int companyId, int departmentId, Employee employee) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.INSERT_EMPLOYEE);
		PreparedStatement preparedStatementInsertEmployee = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.INSERT_EMPLOYEE);
		preparedStatementInsertEmployee.setInt(1, companyId);
		preparedStatementInsertEmployee.setInt(2, departmentId);
		preparedStatementInsertEmployee.setString(3, employee.getPersonFirstName());
		preparedStatementInsertEmployee.setString(4, employee.getPersonLastName());
		preparedStatementInsertEmployee.setString(5, employee.getPersonNIN());
		preparedStatementInsertEmployee.setInt(6, employee.getPersonAge());
		preparedStatementInsertEmployee.setString(7, employee.getEmployeeType().toString());
		preparedStatementInsertEmployee.setFloat(8, employee.getEmployeeSalary());
		preparedStatementInsertEmployee.execute();
		preparedStatementInsertEmployee.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void updateEmployee(int companyId, int departmentId, Employee employee) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.UPDATE_EMPLOYEE);
		PreparedStatement preparedStatementUpdateEmployee = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.UPDATE_EMPLOYEE);
		preparedStatementUpdateEmployee.setString(1, employee.getPersonFirstName());
		preparedStatementUpdateEmployee.setString(2, employee.getPersonLastName());
		preparedStatementUpdateEmployee.setString(3, employee.getPersonNIN());
		preparedStatementUpdateEmployee.setInt(4, employee.getPersonAge());
		preparedStatementUpdateEmployee.setString(5, employee.getEmployeeType().toString());
		preparedStatementUpdateEmployee.setFloat(6, employee.getEmployeeSalary());
		preparedStatementUpdateEmployee.setInt(7, employee.getEmployeeId());
		preparedStatementUpdateEmployee.setInt(8, departmentId);
		preparedStatementUpdateEmployee.setInt(9, companyId);
		preparedStatementUpdateEmployee.executeUpdate();
		preparedStatementUpdateEmployee.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public void deleteEmployee(int companyId, int departmentId, int employeeId) throws SQLException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.DELETE_EMPLOYEE);
		PreparedStatement preparedStatementDeleteEmployee = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.DELETE_EMPLOYEE);
		preparedStatementDeleteEmployee.setInt(1, employeeId);
		preparedStatementDeleteEmployee.setInt(2, departmentId);
		preparedStatementDeleteEmployee.setInt(3, companyId);
		preparedStatementDeleteEmployee.executeUpdate();
		preparedStatementDeleteEmployee.close();
		DatabaseHelper.connectionInstance.close();
	}

	@Override
	public List<Employee> readEmployees(int companyId, int departmentId) throws SQLException, ValidateException {
		if (DatabaseHelper.connectionInstance.isClosed()) {
			DatabaseHelper.connectionInstance = DriverManager
					.getConnection("JDBC:sqlite:" + DatabaseHelper.DATABASE_NAME);
		}
		System.out.println(DatabaseHelper.SELECT_EMPLOYESS);
		PreparedStatement preparedStatement = DatabaseHelper.connectionInstance
				.prepareStatement(DatabaseHelper.SELECT_EMPLOYESS);
		preparedStatement.setInt(1, companyId);
		preparedStatement.setInt(2, departmentId);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Employee> employeeList = new ArrayList<Employee>();
		while (resultSet.next()) {
			int employeeId = resultSet.getInt(1);
			String employeeFirstName = resultSet.getString(4);
			String employeeLastName = resultSet.getString(5);
			String employeeNIN = resultSet.getString(6);
			int employeeAge = resultSet.getInt(7);
			EmployeeType employyeType = EmployeeType.valueOf(resultSet.getString(8));
			float employeeSalary = resultSet.getFloat(9);
			Employee employee = new Employee(employeeFirstName, employeeLastName, employeeAge, employeeNIN,
					employyeType, employeeSalary);
			employee.setEmployeeId(employeeId);
			employeeList.add(employee);
		}
		resultSet.close();
		preparedStatement.close();
		DatabaseHelper.connectionInstance.close();
		return employeeList;
	}

	private boolean checkIfTableExists(String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = DatabaseHelper.connectionInstance.getMetaData();
		ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
		if (resultSet.next()) {
			resultSet.close();
			return true;
		} else {
			resultSet.close();
			return false;
		}
	}
}