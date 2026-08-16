package system.loja.exceptions.pagamento;

public class PagamentoNotFoundException extends RuntimeException {
    public PagamentoNotFoundException(String message) {
        super(message);
    }
}
