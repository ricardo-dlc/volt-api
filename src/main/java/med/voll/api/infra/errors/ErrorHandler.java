package med.voll.api.infra.errors;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ErrorHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> handle404Error() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<validationErrorData>> handle400Error(MethodArgumentNotValidException e) {
		var errors = e.getFieldErrors().stream().map(validationErrorData::new).toList();
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler({ DataValidationException.class, ValidationException.class })
	public ResponseEntity<String> handleDataValidations(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	private record validationErrorData(String field, String error) {
		public validationErrorData(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}
