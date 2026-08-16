package system.loja.exceptions.venda;

public class VendaNotFoundException extends RuntimeException {
    public VendaNotFoundException(String message) {
        super(message);
    }
}
