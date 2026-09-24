
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int legajo;
    private String nombre;
    private static List<Estudiante> estudiantes= new ArrayList<>();

    public static List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public String getNombre() {
        return nombre;
    }

    public Estudiante (int legajo, String nombre) {
        
        this.legajo = legajo;
        this.nombre = nombre;
        agregarEstudiante(this);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        Estudiante.estudiantes.add(estudiante);
        System.out.println("Estudiante agregado correctamente: " + estudiante.nombre);
        System.out.println("--------------------------------------------------"); 
        System.out.println("Lista de estudiantes:");
        for (Estudiante e : Estudiante.estudiantes) {
            System.out.println("- Estudiante: " + e.getNombre() + " - Legajo: " + e.legajo);
        }
        System.out.println(""); 
    }
    
}
