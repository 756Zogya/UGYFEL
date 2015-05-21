package dao.data;

import java.util.Date;

public class Termeszetes extends Ugyfel {

	private String lastName;
	private String firstName;
	private Date birthDate;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Termeszetes [lastName=" + lastName + ", firstName=" + firstName
				+ ", birthDate=" + birthDate + ", getId()=" + getId()
				+ ", isCustomer()=" + isCustomer() + ", getLastIdentified()="
				+ getLastIdentified() + ", getDocuments()=" + getDocuments()
				+ "]";
	}

}
