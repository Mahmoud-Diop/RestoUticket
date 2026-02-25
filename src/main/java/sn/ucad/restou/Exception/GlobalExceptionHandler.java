package sn.ucad.restou.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
        // Erreur de validation (400 Bad Request)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex,
                        WebRequest request) {
                List<ErrorResponse.FieldError> details = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new ErrorResponse.FieldError(
                                                error.getField(),
                                                error.getDefaultMessage()))
                                .collect(Collectors.toList());
                ErrorResponse response = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                "Erreur de validation des donnees",
                                request.getDescription(false).replace("uri=", ""),
                                details);
                return ResponseEntity.badRequest().body(response);
        }
        // Ressource non trouvee (404 Not Found)

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
                        ResourceNotFoundException ex,
                        WebRequest request) {
                ErrorResponse response = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                ex.getMessage(),
                                request.getDescription(false).replace("uri=", ""));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // integrity constraint violation (409 Conflict)
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
                        DataIntegrityViolationException ex,
                        WebRequest request) {
                String message = "an integrity constraint was violated";
                if (ex.getMessage() != null) {
                        if (ex.getMessage().contains("EMAIL")) {
                                message = "this email is already in use";
                        } else if (ex.getMessage().contains("NUMERO_CARTE")) {
                                message = "this card number is already in use";
                        } else if (ex.getMessage().contains("CODE_TICKET")) {
                                message = "this ticket code already exists";
                        }
                }
                ErrorResponse response = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                "Conflict",
                                message,
                                request.getDescription(false).replace("uri=", ""));
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        }

        //  500 Internal Server Error
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGlobalException(
                        Exception ex,
                        WebRequest request) {
                ErrorResponse response = new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "An unexpected error occurred",
                                request.getDescription(false).replace("uri=", ""));
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}
