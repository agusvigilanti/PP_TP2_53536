
import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;

    public Inscripcion(String estado, Estudiante estudiante) {
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.estudiante = estudiante;
        System.out.println("Inscripcion creada correctamente: " + this.fecha + " - " + this.estado);
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    @Override
    public String toString() {
        return "Fecha: " + this.fecha + " - Estado: " + this.estado + " - Alumno: " + this.estudiante.getNombre();
    }
}