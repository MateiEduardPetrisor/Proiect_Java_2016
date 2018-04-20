package models;

import exceptions.ValidateException;;

public class Department {
	private int departmentId;
	private String departmentName;

	public int getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(int departmentId) throws ValidateException {
		if (departmentId > 0) {
			this.departmentId = departmentId;
		} else {
			throw new ValidateException("Id Must Be Greather Than 0");
		}
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) throws ValidateException {
		if (departmentName.length() >= 2) {
			this.departmentName = departmentName;
		} else {
			throw new ValidateException("Department Name Must Be Longer Than 2");
		}
	}

	public Department(String departmentName) throws ValidateException {
		super();
		this.setDepartmentName(departmentName);
	}

	@Override
	public String toString() {
		return this.departmentName;
	}
}