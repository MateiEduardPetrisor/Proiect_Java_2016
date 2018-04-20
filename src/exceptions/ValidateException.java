package exceptions;

public class ValidateException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidateException(String exceptionMessage) {
		super(exceptionMessage);
	}
}