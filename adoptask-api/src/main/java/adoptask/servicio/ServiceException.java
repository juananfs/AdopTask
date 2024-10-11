package adoptask.servicio;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {
	public ServiceException(String message) {
		super(message);
	}
}
