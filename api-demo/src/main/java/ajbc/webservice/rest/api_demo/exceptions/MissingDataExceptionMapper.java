package ajbc.webservice.rest.api_demo.exceptions;

import ajbc.webservice.rest.api_demo.models.ErrorMassage;
import ajbc.webservice.rest.api_demo.models.InternalErrorCode;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider // the server will know to use it as exception handling tool
public class MissingDataExceptionMapper implements ExceptionMapper<MissingDataException>{

	@Override
	public Response toResponse(MissingDataException exception) {
		ErrorMassage errorMassage = new ErrorMassage(exception.getMessage(),"www.ketty.com", InternalErrorCode.INVALID);
		return Response.status(InternalErrorCode.INVALID.getCodeNum()).entity(errorMassage).build();
	}

}
