package ui.mvc;

public class View {
	private String name;
	private Object data;
	private String beanId;
	
	public View(String name) {
		this.name = name;
	}

	public View(String name, Object data) {
		this.name = name;
		this.data = data;
	}

	public View(String name, String beanId,Object data) {
		this.name = name;
		this.data = data;
		this.beanId = beanId;
	}

	public String getName() {
		return name;
	}

	public Object getData() {
		return data;
	}
	
	public String getBeanId() {
		return beanId;
	}
}
