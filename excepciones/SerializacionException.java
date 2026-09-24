
public class SerializacionException extends PersistenciaException {
    private static final long serialVersionUID = 1L;

    public SerializacionException(String mensaje) {
        super(mensaje);
    }

    public SerializacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
