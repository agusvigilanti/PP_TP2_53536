

public class CupoExcedidoExcepcion extends Exception {
    private static final long serialVersionUID = 1L;

    public CupoExcedidoExcepcion(String mensaje) {
        super(mensaje);
        System.out.println("[ERROR] Cupo excedido: " + mensaje);
    }

    public CupoExcedidoExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
        System.out.println("[ERROR] Cupo excedido: " + mensaje + " | Causa: " + causa.getMessage());
    }
}