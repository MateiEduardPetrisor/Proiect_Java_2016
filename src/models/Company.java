package models;

import exceptions.ValidateException;

public class Company {
	private int companyId;
	private String companyName;

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) throws ValidateException {
		if (companyId > 0) {
			this.companyId = companyId;
		} else {
			throw new ValidateException("Id Of Company Must Be Positive!");
		}
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) throws ValidateException {
		if (companyName.length() >= 3) {
			this.companyName = companyName;
		} else {
			throw new ValidateException("Company Name Must Be At Least 3 Letters Long!");
		}
	}

	public Company(String companyName) throws ValidateException {
		super();
		this.setCompanyName(companyName);
	}

	@Override
	public String toString() {
		return this.companyName;
	}
}