package models;

import exceptions.ValidateException;

public class Employee extends Person {
	private int employeeId;
	private EmployeeType employeeType;
	private float employeeSalary;

	public int getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(int employeeId) throws ValidateException {
		if (employeeId > 0) {
			this.employeeId = employeeId;
		} else {
			throw new ValidateException("Id Of An Employee Must Be Positive!");
		}
	}

	public EmployeeType getEmployeeType() {
		return this.employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public float getEmployeeSalary() {
		return this.employeeSalary;
	}

	public void setEmployeeSalary(float employeeSalary) throws ValidateException {
		if (employeeSalary > 1450) {
			this.employeeSalary = employeeSalary;
		} else {
			throw new ValidateException("Any Salary Must Be Grather Than 1450");
		}
	}

	public Employee(String personFirstName, String personLastName, int personAge, String personCNP,
			EmployeeType employeeType, float employeeSalary) throws ValidateException {
		super(personFirstName, personLastName, personAge, personCNP);
		this.setEmployeeSalary(employeeSalary);
		this.setEmployeeType(employeeType);
	}

	@Override
	public String toString() {
		return super.toString() + this.employeeType + " " + this.employeeSalary;
	}
}