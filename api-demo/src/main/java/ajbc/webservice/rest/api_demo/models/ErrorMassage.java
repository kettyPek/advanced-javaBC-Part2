package ajbc.webservice.rest.api_demo.models;

public class ErrorMassage {
	
	private String massage;
	private String linkToDocks;
	private InternalErrorCode errorCode;
	
	public ErrorMassage() {
		
	}
	
	public ErrorMassage(String massage, String linkToDocks, InternalErrorCode errorCode) {
		this.massage = massage;
		this.linkToDocks = linkToDocks;
		this.errorCode = errorCode;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public String getLinkToDocks() {
		return linkToDocks;
	}

	public void setLinkToDocks(String linkToDocks) {
		this.linkToDocks = linkToDocks;
	}

	public InternalErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(InternalErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
	

}
