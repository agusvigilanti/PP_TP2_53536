
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class EventoUniversitario implements Serializable {

   private static final long serialVersionUID = 1L;

   private final String id;
   private String titulo;
   private double costoBase;
   private boolean gratuito;
   private static int cantidadEventos = 0;
   private String idParaMostrar;
   private boolean usarIdAlternativo = false; 
   private static ArrayList<EventoUniversitario> listaEventos = new ArrayList<>();
   private Sala sala;
   private List<Actividad> actividades = new ArrayList<>();

   public Sala getSala() {
      return sala;
   }

   public List<Actividad> getActividades() {
      return actividades;
   }

   public String getTitulo() {
      return titulo;
   }

   public String getId() {
      return id;
   }

   public static ArrayList<EventoUniversitario> getListaEventos() {
      return listaEventos;
   }
   
   public static void mostrarListaEventos() {
      System.out.println("===================================================================");
      System.out.println("                       EVENTOS UNIVERSITARIOS");
      System.out.println("===================================================================");
      for (EventoUniversitario evento : EventoUniversitario.getListaEventos()) {
         System.out.printf("Codigo: %-5s Titulo: %-20s Costo: $%.2f%n", 
         evento.id, evento.titulo, evento.costoBase);
      }
      System.out.println("===================================================================");
   }
     
   public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
      this.id = id;
      this.titulo = titulo;
      this.costoBase = costoBase;
      this.gratuito = gratuito;

      if (this.gratuito) {
         this.costoBase = 0.0;
      } else {
         this.costoBase = calcularCostoEstimado(this.costoBase); 
      }

      listaEventos.add(this);
      ++cantidadEventos;

      System.out.println("TICKET DEL EVENTO GENERADO AUTOMATICAMENTE:");
      this.mostrarDatos();

      EventoUniversitario copia = this.copiar();
      System.out.println("COPIA DEL EVENTO GENERADA AUTOMATICAMENTE:");
      copia.mostrarDatos();
   }

   public EventoUniversitario (EventoUniversitario otro) {
      this.id = otro.id + " ------COPIA------ ";
      this.titulo = otro.titulo;
      this.costoBase = otro.costoBase;
      this.gratuito = otro.gratuito;
   }

   public EventoUniversitario copiar() {
      return new EventoUniversitario(this);
   }

   public double calcularCostoEstimado(double costoEstimado) {
      double costoPorPersona = 500.0; 
    
      return costoEstimado * costoPorPersona;
   }

    public void crearActividad(int id, String titulo, int cupoMaximo, String tipo, String disertante, boolean requiereNotebook) {
         crearActividad(id, titulo, cupoMaximo, tipo, disertante, requiereNotebook, 1);
    }

    public void crearActividad(int id, String titulo, int cupoMaximo, String tipo, String disertante, boolean requiereNotebook, int cantidadClases) {
      double costoEstimado = calcularCostoEstimado(cupoMaximo);
      Actividad actividad;

      if (tipo.equalsIgnoreCase("Charla")) {
        actividad = new Charla(id, titulo, cupoMaximo, disertante);
         } else if (tipo.equalsIgnoreCase("Curso")) {
            actividad = new Curso(id, titulo, cupoMaximo, disertante, cantidadClases);
      } else {
        actividad = new Taller(id, titulo, cupoMaximo, requiereNotebook);
      }

      actividades.add(actividad);
      cantidadEventos++;
      
   }

   public void mostrarDatos() {
      
      System.out.println("--------------------------------------------------");
      if (usarIdAlternativo) {
         System.out.println("Codigo del evento = " + idParaMostrar);
      } else {
         System.out.println("Codigo del evento = " + this.id);
      }
      System.out.println("Titulo = " + this.titulo);
      if (this.gratuito) {
         System.out.println("Costo = $ " + String.format("%.1f", this.costoBase));
         
      } else {
         System.out.println("Costo = $ " + String.format("%.1f", this.costoBase) + "   ($500 por persona)");
         
      }
      System.out.println("--------------------------------------------------");
   }
   
   public static int getCantidadEventos() {
      return cantidadEventos;
   }
   
   public void asignarSala(Sala sala) {
      this.sala = sala;
      System.out.println("Sala asignada al evento " + this.titulo + ":");
      this.sala.mostrarSala();
   }

   public boolean persistirEvento() throws PersistenciaException {
      if (this.id == null || this.id.isBlank()) {
         throw new EventoInvalidoException("El evento no tiene un identificador válido para persistir");
      }

      Path carpeta = Paths.get("eventos");
      try {
         Files.createDirectories(carpeta);
      } catch (IOException e) {
         throw new ArchivoPersistenciaException("No se pudo crear la carpeta de persistencia", e);
      }

      Path archivo = carpeta.resolve(this.id + ".ser");

      try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(archivo))) {
         salida.writeObject(this);
         System.out.println("Evento persistido correctamente: " + archivo.toAbsolutePath());
         return true;
      } catch (NotSerializableException e) {
         throw new SerializacionException("El evento no es serializable: " + this.id, e);
      } catch (IOException e) {
         throw new ArchivoPersistenciaException("No se pudo guardar el evento " + this.id + " en disco", e);
      }
   }

   public static EventoUniversitario recuperaraEvento(String id) throws PersistenciaException {
      if (id == null || id.isBlank()) {
         throw new EventoInvalidoException("Debe indicar un id válido para recuperar el evento");
      }

      Path archivo = Paths.get("eventos", id + ".ser");
      try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(archivo))) {
         Object objeto = entrada.readObject();
         if (!(objeto instanceof EventoUniversitario)) {
            throw new SerializacionException("El archivo " + archivo + " no contiene un EventoUniversitario válido");
         }
         EventoUniversitario evento = (EventoUniversitario) objeto;
         System.out.println("Evento recuperado correctamente: " + evento.getTitulo());
         return evento;
      } catch (NoSuchFileException e) {
         throw new EventoNoEncontradoException("No existe un evento persistido con el id " + id, e);
      } catch (FileNotFoundException e) {
         throw new EventoNoEncontradoException("No se encontró el archivo de persistencia del evento " + id, e);
      } catch (ClassNotFoundException e) {
         throw new SerializacionException("La clase del evento persistido no está disponible en la aplicación", e);
      } catch (IOException e) {
         throw new ArchivoPersistenciaException("Error de lectura al recuperar el evento " + id, e);
      }
   }

   public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
      List<T> actividadesFiltradas = new ArrayList<>();
      for (Actividad actividad : actividades) {
         if (tipo.isInstance(actividad)) {
            actividadesFiltradas.add(tipo.cast(actividad));
         }
      }
      return actividadesFiltradas;

   }
   
   public double calcularCostoMateriales(List<? extends Actividad> actividades) {
      double costoTotal = 0.0;

      for (Actividad actividad : actividades) {
        costoTotal += actividad.calcularCostoMateriales();
      }
      return costoTotal;
   }

   public void mostrarCostoMaterialesPorTipo() {
      double costoCharlas = calcularCostoMateriales(filtrarActividadesPorTipo(Charla.class));
      double costoCursos = calcularCostoMateriales(filtrarActividadesPorTipo(Curso.class));
      double costoTalleres = calcularCostoMateriales(filtrarActividadesPorTipo(Taller.class));

      System.out.println("Costo de materiales por tipo:");
      System.out.println("Charlas: $" + costoCharlas);
      System.out.println("Cursos: $" + costoCursos);
      System.out.println("Talleres: $" + costoTalleres);
   }

   static {
      System.out.println("Se ha inicializado el contador de eventos ocurridos...");
   }
}


