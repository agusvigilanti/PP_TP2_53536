
public class ArchivoPersistenciaException extends PersistenciaException {
    private static final long serialVersionUID = 1L;

    public ArchivoPersistenciaException(String mensaje) {
        super(mensaje);
    }

    public ArchivoPersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
