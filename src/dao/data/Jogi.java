package dao.data;

import java.util.Date;

public class Jogi extends Ugyfel {

	private String name;
	private String shortName;
	private Date establishmentDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@Override
	public String toString() {
		return "Jogi [name=" + name + ", shortName=" + shortName
				+ ", establishmentDate=" + establishmentDate + ", getId()="
				+ getId() + ", isCustomer()=" + isCustomer()
				+ ", getLastIdentified()=" + getLastIdentified()
				+ ", getDocuments()=" + getDocuments() + "]";
	}

}
