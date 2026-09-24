
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    public static void main(String[] args) {
        
        System.out.println("=== EJERCICIO 1 ===");

        ejecutarCasoExitoso();
        ejecutarCasoFallido();

        System.out.println("=== FIN ===");

        System.out.println("\n=== EJERCICIO 2 ===");

        ejecutarEjercicio2();

        System.out.println("=== FIN ===");

        System.out.println("\n=== EJERCICIO 3 ===");

        ejecutarEjercicio3();

        System.out.println("=== FIN ===");
    }

    // EJERCICIO 1 IMPLEMENTADO

    private static void ejecutarCasoExitoso() {

        System.out.println("\n--- CASO EXITOSO ---");

        String idEvento = "EV-OK-" + System.currentTimeMillis();

        try {
            System.out.println("1) Creacion de evento:");
            EventoUniversitario evento = new EventoUniversitario(idEvento, "Seminario de Programacion", 1200.0, false);
            evento.crearActividad(1, "Charla introductoria", 2, "Charla", "Lic. Morales", false);

            System.out.println("2) Inscribiendo estudiantes...");
            Estudiante estudiante1 = new Estudiante(201, "Lucia Perez");
            Estudiante estudiante2 = new Estudiante(202, "Pedro Ruiz");
            evento.getActividades().get(0).inscribir(estudiante1);
            evento.getActividades().get(0).inscribir(estudiante2);

            System.out.println("3) Guardando evento...");
            evento.persistirEvento();

            System.out.println("4) Leyendo evento desde disco:");
            EventoUniversitario eventoLeido = EventoUniversitario.recuperaraEvento(idEvento);

            System.out.println("Caso exitoso: el evento se persistio y se leyo correctamente -> " + eventoLeido.getTitulo());

        } catch (CupoExcedidoExcepcion e) {
            System.err.println("[CupoExcedidoExcepcion] El caso exitoso falló por cupo: " + e.getMessage());
        } catch (PersistenciaException e) {
            System.err.println("[PersistenciaException] El caso exitoso falló al guardar o leer el evento: " + e.getMessage());
        }
    }

    private static void ejecutarCasoFallido() {
        
        System.out.println("\n--- CASO FALLIDO CONTROLADO ---");

        try {
            System.out.println("1) Creando evento con cupo insuficiente...");
            EventoUniversitario evento = new EventoUniversitario("EV-FAIL", "Evento con exceso de inscriptos", 500.0, false);
            evento.crearActividad(10, "Taller lleno", 1, "Taller", "Prof. Lara", false);

            System.out.println("2) Intentando inscribir dos estudiantes en un cupo de 1...");
            Estudiante estudiante1 = new Estudiante(301, "Ana Lopez");
            Estudiante estudiante2 = new Estudiante(302, "Beto Diaz");
            evento.getActividades().get(0).inscribir(estudiante1);
            evento.getActividades().get(0).inscribir(estudiante2);

            System.out.println("Este mensaje no debería aparecer porque el caso fallido debe disparar la excepción.");

        } catch (CupoExcedidoExcepcion e) {
            System.err.println("[CupoExcedidoExcepcion] Caso fallido controlado: " + e.getMessage());
        }
    }

    // EJERCICIO 2 IMPLEMENTADO

    private static void ejecutarEjercicio2() {

        System.out.println("\n--- CREANDO EVENTOS Y ACTIVIDADES ---");

        Sala[] salas = {
            new Sala(1, "Aula Magna"),
            new Sala(2, "Sala de Conferencias"),
            new Sala(3, "Laboratorio de Informática"),
            new Sala(4, "Auditorio Central")
        };
        Random random = new Random();

        for (EventoUniversitario evento : EventoUniversitario.getListaEventos()) {
            Sala salaAleatoria = salas[random.nextInt(salas.length)];
            evento.asignarSala(salaAleatoria);
        }

        for (EventoUniversitario evento : EventoUniversitario.getListaEventos()) {
            int idActividad = random.nextInt(1000) + 1;
            int cupoMaximo = random.nextInt(10) + 1;
            String[] tipos = { "Charla", "Taller", "Curso" };
            String tipo = tipos[random.nextInt(tipos.length)];
            String[] titulos = {
                "Introduccion a la programacion",
                "Tecnologias emergentes",
                "Desarrollo de proyectos",
                "Buenas practicas profesionales"
            };
            String[] disertantes = {
                "Dra. Garcia",
                "Dr. Fernandez",
                "Ing. Martinez",
                "Lic. Rodriguez"
            };
            String titulo = titulos[random.nextInt(titulos.length)];
            String disertante = disertantes[random.nextInt(disertantes.length)];
            boolean requiereNotebook = random.nextBoolean();
            int cantidadClases = random.nextInt(12) + 1;

            evento.crearActividad(
                idActividad,
                titulo,
                cupoMaximo,
                tipo,
                disertante,
                requiereNotebook,
                cantidadClases
            );
            System.out.println("Actividad de tipo " + tipo + " creada para el evento " + evento.getTitulo());
        }
        System.out.println("");

        EventoUniversitario evento1 = new EventoUniversitario("EV-001", "Evento de Tecnologia", 1000.0, false);
        EventoUniversitario evento2 = new EventoUniversitario("EV-002", "Evento de Innovacion", 800.0, true);
        EventoUniversitario evento3 = new EventoUniversitario("EV-003", "Evento de Emprendimiento", 1200.0, false);
        evento1.crearActividad(101, "Charla de apertura", 50, "Charla", "Dr. Lopez", false);
        evento2.crearActividad(102, "Taller de creatividad", 30, "Taller", "Lic. Gomez", true);
        evento3.crearActividad(103, "Curso de liderazgo", 20, "Curso", "Prof. Sanchez", false, 5);

        System.out.println("\n--- INSCRIBIENDO ESTUDIANTES ---");

        for (int i = 1; i <= 8; i++) {
            new Estudiante(400 + i, "Estudiante " + i);
        }

        ArrayList<EventoUniversitario> eventos = EventoUniversitario.getListaEventos();
        Random randomInscripciones = new Random();
        int cantidadInscripciones = randomInscripciones.nextInt(10) + 5;

        for (int i = 0; i < cantidadInscripciones; i++) {
            EventoUniversitario evento = eventos.get(randomInscripciones.nextInt(eventos.size()));
            Actividad actividad = evento.getActividades().get(
                randomInscripciones.nextInt(evento.getActividades().size())
            );
            Estudiante estudiante = Estudiante.getEstudiantes().get(
                randomInscripciones.nextInt(Estudiante.getEstudiantes().size())
            );

            try {
                actividad.inscribir(estudiante);
            } catch (CupoExcedidoExcepcion e) {
                System.out.println("No se pudo inscribir a " + estudiante.getNombre()
                    + " en " + actividad.getTitulo() + ": " + e.getMessage());
            }
        }

        System.out.println("\n--- CERTIFICADOS EMITIDOS ---");

        boolean seEmitioCertificado = false;
        for (EventoUniversitario evento : eventos) {
            for (Actividad actividad : evento.getActividades()) {
                if (actividad instanceof Certificable) {
                    Certificable certificable = (Certificable) actividad;
                    for (Inscripcion inscripcion : actividad.getInscripciones()) {
                        System.out.println(certificable.generarCertificado(inscripcion.getEstudiante()));
                        System.out.println("--------------------------------------");
                        seEmitioCertificado = true;
                    }
                }
            }
        }

        if (!seEmitioCertificado) {
            System.out.println("No se emitieron certificados porque no hay estudiantes inscriptos en cursos.");
        }

        System.out.println("\n--- MOSTRANDO DATOS DE LOS EVENTOS ---");

        for (EventoUniversitario evento : eventos) {
            evento.mostrarDatos();
        }
    }
    
    // EJERCICIO 3 IMPLEMENTADO

    private static void ejecutarEjercicio3() {
        
        System.out.println("\n--- FILTRADO DE ACTIVIDADES ---");
        
        EventoUniversitario evento = new EventoUniversitario("E1", "Congreso", 1000, false);

        evento.crearActividad(1, "Introducción a Java", 40, "Charla", "Ana", false);
        evento.crearActividad(2, "Spring Boot", 20, "Curso", "Luis", false,5);
        evento.crearActividad(3, "Arduino", 15, "Taller", "", true);

        List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
        List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);
        List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);

        System.out.println("\n--- TAMAÑO DE ACTIVIDADES ---");
        
        System.out.println("Charlas: " + charlas.size());
        System.out.println("Cursos: " + cursos.size());
        System.out.println("Talleres: " + talleres.size());

        System.out.println("\n--- LISTAS FILTRADAS POR TIPO ---");
        System.out.println("Charlas:");
        for (Charla charla : charlas) {
            System.out.println("- " + charla.getTitulo());
        }

        System.out.println("\nCursos:");
        for (Curso curso : cursos) {
            System.out.println("- " + curso.getTitulo());
        }

        System.out.println("\nTalleres:");
        for (Taller taller : talleres) {
            System.out.println("- " + taller.getTitulo());
        }
        
        System.out.println("\n--- COSTO DE MATERIALES POR TIPO ---");
        
        evento.mostrarCostoMaterialesPorTipo();
    }
}