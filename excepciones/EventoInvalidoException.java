
public class EventoInvalidoException extends PersistenciaException {
    private static final long serialVersionUID = 1L;

    public EventoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public EventoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
