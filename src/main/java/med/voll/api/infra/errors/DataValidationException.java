package med.voll.api.infra.errors;

public class DataValidationException extends RuntimeException {

	public DataValidationException(String string) {
		super(string);
	}

}
