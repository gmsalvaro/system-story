package system.loja.exceptions.cliente;

public class ClienteValidationException extends RuntimeException {
    public ClienteValidationException(String message) {
        super(message);
    }
}
