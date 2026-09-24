
public class PersistenciaException extends Exception {
    private static final long serialVersionUID = 1L;

    public PersistenciaException(String mensaje) {
        super(mensaje);
        System.out.println("[ERROR PERSISTENCIA] " + mensaje);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        System.out.println("[ERROR PERSISTENCIA] " + mensaje + " | Causa: " + causa.getMessage());
    }
}
