package testserver.server.support;

public class GenericResponseObject {
	String message;
	int status;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public GenericResponseObject(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	
}
