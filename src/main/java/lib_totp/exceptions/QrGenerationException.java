package lib_totp.exceptions;

public class QrGenerationException extends Exception {
    public QrGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
