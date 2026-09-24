public class Curso extends Actividad implements Certificable {
    private static final long serialVersionUID = 1L;

    private String docente;
    private int cantidadClases;

    public Curso(int id, String titulo, int cupoMaximo, String docente, int cantidadClases) {
        super(id, titulo, cupoMaximo);
        this.docente = docente;
        this.cantidadClases = cantidadClases;
        
    }

    @Override
    public double calcularCostoMateriales() {
        return cantidadClases * 1000.0;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        
        return ENTIDAD_EMISORA + "\n\nSe certifica que el estudiante " + estudiante.getNombre() + " ha completado el curso: " + this.getTitulo() + ".";
    }
}
