
public class EventoNoEncontradoException extends PersistenciaException {
    private static final long serialVersionUID = 1L;

    public EventoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public EventoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
