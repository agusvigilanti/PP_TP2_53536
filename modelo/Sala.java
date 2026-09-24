
import java.io.Serializable;

public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public Sala (int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public void mostrarSala () {
        System.out.println("--------------------------------------");
        System.out.println("ID de la sala: " + id);
        System.out.println("Nombre de la sala: " + nombre);
        System.out.println("--------------------------------------");
    }
}
