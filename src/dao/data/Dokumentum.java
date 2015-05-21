package dao.data;

import java.util.Date;

public class Dokumentum {

	private Type type;
	private String number;
	private Date issueDate;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Override
	public String toString() {
		return "Dokumentum [type=" + type + ", number=" + number
				+ ", issueDate=" + issueDate + "]";
	}

}
