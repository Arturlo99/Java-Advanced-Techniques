package serviceloader.client;

public class Record {
	private String recordId;
	private String name;
	private String lastName;
	private String weight;
	private String height;
	private String categoryId;
	
	public Record(String recordId, String name, String lastName, String weight, String height) {
		this.recordId = recordId;
		this.name = name;
		this.lastName = lastName;
		this.weight = weight;
		this.height = height;
	}
	public Record(String recordId, String name, String lastName, String weight, String height, String categoryId) {
		this.recordId = recordId;
		this.name = name;
		this.lastName = lastName;
		this.weight = weight;
		this.height = height;
		this.categoryId = categoryId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
