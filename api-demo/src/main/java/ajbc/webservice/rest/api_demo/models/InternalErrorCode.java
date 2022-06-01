package ajbc.webservice.rest.api_demo.models;

public enum InternalErrorCode {
	
	INVALID(123);
	
	private int codeNum;
	
	private InternalErrorCode(int codeNum) {
		this.codeNum = codeNum;
	}
	
	public int getCodeNum() {
		return codeNum;
	}
	
	
}
