package adoptask.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import adoptask.dto.ErrorDto;
import adoptask.servicio.EntityNotFoundException;
import adoptask.servicio.ServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ EntityNotFoundException.class, UsernameNotFoundException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto handleEntityNotFoundException(Exception ex) {
		return new ErrorDto("Not Found", ex.getMessage());
	}

	@ExceptionHandler({ IllegalArgumentException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto handleIllegalArgumentException(Exception ex) {
		return new ErrorDto("Bad Request", ex.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorDto handleBadCredentialsException(BadCredentialsException ex) {
		return new ErrorDto("Unauthorized", ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorDto handleAccessDeniedException(AccessDeniedException ex) {
		return new ErrorDto("Forbidden", ex.getMessage());
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorDto handleServiceException(ServiceException ex) {
		return new ErrorDto("Unprocessable Entity", ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDto handleGlobalException(Exception ex) {
		return new ErrorDto("Internal Server Error", ex.getMessage());
	}
}
