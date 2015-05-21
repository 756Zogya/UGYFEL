package dao.data;

public class Type {

	private String code;
	private boolean isUnique;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	@Override
	public String toString() {
		return "Type [code=" + code + ", isUnique=" + isUnique + "]";
	}

}
