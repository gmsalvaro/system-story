package system.loja.exceptions.produto;

public class ProdutoValidationException extends RuntimeException {
    public ProdutoValidationException(String message) {
        super(message);
    }
}
