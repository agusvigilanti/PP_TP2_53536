
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private int cupoMaximo;
    private static final int cupoMinimo = 0;
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public Inscripcion inscribir (Estudiante estudiante) throws CupoExcedidoExcepcion {
        if (this.cupoMaximo <= cupoMinimo) {
            throw new CupoExcedidoExcepcion(
                "No hay cupos disponibles para la actividad '" + this.titulo + "'"
            );
        }

        Inscripcion inscripcion = new Inscripcion("Inscripto a la actividad: " + this.titulo, estudiante);
        this.inscripciones.add(inscripcion);
        this.cupoMaximo--;
        this.mostrarInscripciones(estudiante);
        return inscripcion;
    }

    public void mostrarInscripciones(Estudiante estudiante) {
        System.out.println("--------------------------------------------------");
        System.out.println("Inscriptos a " + getTipo().toLowerCase() + " " + this.titulo + ":");
    
        boolean estaInscripto = false;

        for (Inscripcion i : inscripciones) {
            System.out.println("- Alumno: " + i.getEstudiante().getNombre());
            if (i.getEstudiante().equals(estudiante)) {
                estaInscripto = true;
            }
        }
    
        System.out.println("Cupos restantes: " + this.cupoMaximo);
        System.out.println(estudiante.getNombre() + (estaInscripto ? " está inscripto." : " no está inscripto."));
        System.out.println("--------------------------------------------------");
    }
    
    
    public Actividad (int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
       
    }

    public final void mostrarIdentificacion () {
        System.out.println("[" + getTipo() + "] " + titulo + " (id: " + id + ") - Costo materiales: $" + calcularCostoMateriales());
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    
    public int getId() { 
        return id; 
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public int getCupoMaximo() { 
        return cupoMaximo; 
    }
}