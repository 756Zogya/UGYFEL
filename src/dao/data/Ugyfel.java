package dao.data;

import java.util.Date;
import java.util.List;

public abstract class Ugyfel {

	private long id;
	private boolean isCustomer;
	private Date lastIdentified;
	private List<Dokumentum> documents;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Date getLastIdentified() {
		return lastIdentified;
	}

	public void setLastIdentified(Date lastIdentified) {
		this.lastIdentified = lastIdentified;
	}

	public List<Dokumentum> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Dokumentum> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return "Ugyfel [id=" + id + ", isCustomer=" + isCustomer
				+ ", lastIdentified=" + lastIdentified + ", documents="
				+ documents + "]";
	}

}
