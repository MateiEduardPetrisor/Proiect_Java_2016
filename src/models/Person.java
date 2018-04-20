package models;

import exceptions.ValidateException;

public abstract class Person {
	private String personFirstName;
	private String personLastName;
	private int personAge;
	private String personNIN;

	public String getPersonFirstName() {
		return this.personFirstName;
	}

	public void setPersonFirstName(String personFirstName) throws ValidateException {
		if ((personFirstName.length() >= 5) & (personFirstName != null)) {
			this.personFirstName = personFirstName;
		} else {
			throw new ValidateException("First Name Length Must Be Greather Or Equal Than 5!");
		}
	}

	public String getPersonLastName() {
		return this.personLastName;
	}

	public void setPersonLastName(String personLastName) throws ValidateException {
		if ((personLastName.length() > 5) & (personLastName != null)) {
			this.personLastName = personLastName;
		} else {
			throw new ValidateException("Last Name Length Must Be Greather Than 5!");
		}
	}

	public int getPersonAge() {
		return this.personAge;
	}

	public void setPersonAge(int personAge) throws ValidateException {
		if ((personAge > 0) && (personAge < 125)) {
			this.personAge = personAge;
		} else {
			throw new ValidateException("A Person Age Must Be Between 0 And 125!");
		}
	}

	public String getPersonNIN() {
		return this.personNIN;
	}

	public void setPersonNIN(String personNIN) throws ValidateException {
		if ((personNIN.length() == 13) & (personNIN != null)) {
			this.personNIN = personNIN;
		} else {
			throw new ValidateException("Length Of A NIN Must Be 13!");
		}
	}

	public Person(String personFirstName, String personLastName, int personAge, String personNIN)
			throws ValidateException {
		super();
		this.setPersonFirstName(personFirstName);
		this.setPersonLastName(personLastName);
		this.setPersonAge(personAge);
		this.setPersonNIN(personNIN);
	}

	@Override
	public String toString() {
		return this.personFirstName + " " + this.personLastName + " " + this.personAge + " " + this.personNIN + " ";
	}
}