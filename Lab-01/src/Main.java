import dto.DoctorDTO;
import dto.PacienteDTO;
import dto.CitaDTO;
import utils.DatabaseUtils;
import utils.DateUtils;

import java.sql.Time;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Integer n = 9;

        while (true) {
            System.out.println("Sistema de Citas Médicas");
            System.out.println("1. Listar todas las citas");
            System.out.println("2. Agendar nueva cita");
            System.out.println("3. Agregar nuevo paciente");
            System.out.println("4. Agregar nuevo doctor");
            System.out.println("5. Listar citas por doctor");
            System.out.println("6. Mundo Salva vidas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    listarCitas();
                    break;
                case 2:
                    agendarCita(scanner);
                    break;
                case 3:
                    agregarPaciente(scanner);
                    break;
                case 4:
                    agregarDoctor(scanner, n);
                    n++;
                    break;
                case 5:
                    listarCitasPorDoctor(scanner);
                    break;
                case 6:
                    System.out.println("Este botón se ve bonito pero no hace nada. Bienvenido a la Grieta del Invocador");
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void listarCitas() {
        List<CitaDTO> citas = DatabaseUtils.leerCitas();
        for (CitaDTO cita : citas) {
            System.out.println("Doctor: " + cita.getDoctor().getCodigo() +
                    ", Paciente: " + cita.getInformacionCompletaPaciente() +
                    ", Especialidad: " + cita.getEspecialidad() +
                    ", Fecha: " + cita.getFecha() +
                    ", Atendido: " + cita.isAtendido());
        }
    }

    private static void agendarCita(Scanner scanner) {
        // 2 Casos al crear cita
        System.out.println("1. Agregar cita ahora        2. Agregar cita a partir de mañana");
        int tipo = scanner.nextInt();
        if (tipo == 1) {citaEnMomento(scanner);}
        else if (tipo == 2) {citaAFuturo(scanner);}
        else {System.out.println("Opcion no valida. Intente de nuevo.");}
    }

    private static void citaEnMomento(Scanner scanner) {
        String a = scanner.nextLine();
        System.out.print("DUI del Paciente: ");
        String duiPaciente = scanner.nextLine();
        PacienteDTO paciente = DatabaseUtils.buscarPacientePorDui(duiPaciente);
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        // Submenú para seleccionar especialidad
        System.out.println("Seleccione una especialidad:");
        for (int i = 0; i < ESPECIALIDADES.length; i++) {
            System.out.println((i + 1) + ". " + ESPECIALIDADES[i]);
        }
        System.out.print("Opción: ");
        int opcionEspecialidad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        String especialidad = ESPECIALIDADES[opcionEspecialidad - 1];

        // Buscar un doctor de la especialidad seleccionada
        DoctorDTO doctor = DatabaseUtils.buscarDoctorPorEspecialidad(especialidad);
        if (doctor == null) {
            System.out.println("No se encontró un doctor con la especialidad seleccionada.");
            return;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime fecha = LocalDateTime.parse(LocalDateTime.now().format(formato), formato);

        CitaDTO cita = new CitaDTO();
        cita.setDoctor(doctor);
        cita.setPaciente(paciente);
        cita.setEspecialidad(especialidad);
        cita.setFecha(fecha);
        //Asumiendo que el paciente esta presente al momento de realzar la cita
        cita.setAtendido(true);

        DatabaseUtils.guardarCita(cita);
        System.out.println("Cita agendada exitosamente.");
    }

    private static void citaAFuturo(Scanner scanner) {
        String a = scanner.nextLine();
        System.out.print("DUI del Paciente: ");
        String duiPaciente = scanner.nextLine();
        PacienteDTO paciente = DatabaseUtils.buscarPacientePorDui(duiPaciente);
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        // Submenú para seleccionar especialidad
        System.out.println("Seleccione una especialidad:");
        for (int i = 0; i < ESPECIALIDADES.length; i++) {
            System.out.println((i + 1) + ". " + ESPECIALIDADES[i]);
        }
        System.out.print("Opción: ");
        int opcionEspecialidad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        String especialidad = ESPECIALIDADES[opcionEspecialidad - 1];

        // Buscar un doctor de la especialidad seleccionada
        DoctorDTO doctor = DatabaseUtils.buscarDoctorPorEspecialidad(especialidad);
        if (doctor == null) {
            System.out.println("No se encontró un doctor con la especialidad seleccionada.");
            return;
        }

        System.out.print("Fecha de la Cita (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        System.out.println("1. 8am   2. 9am   3. 10am   4. 11am   5. 12am   6. 1pm   7. 2pm   8. 3pm    9. 4pm");
        System.out.print("Hora de la Cita: ");
        String valHora = scanner.nextLine();
        String horaStr = String.valueOf(Integer.parseInt(valHora)+7) + ":00:00";
        horaStr = Time.valueOf(horaStr).toString();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss");
        LocalDateTime fecha = LocalDateTime.parse(fechaStr + " " + horaStr, formato);


        CitaDTO cita = new CitaDTO();
        cita.setDoctor(doctor);
        cita.setPaciente(paciente);
        cita.setEspecialidad(especialidad);
        cita.setFecha(fecha);
        cita.setAtendido(false);

        DatabaseUtils.guardarCita(cita);
        System.out.println("Cita agendada exitosamente.");
    }

    private static void agregarPaciente(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Fecha de Nacimiento (dd/MM/yyyy): ");
        String fechaNacimientoStr = scanner.nextLine();
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = DateUtils.parseDate(fechaNacimientoStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dui;
        if (esMenorDeEdad(fechaNacimiento)) {
            dui = generarDuiCorrelativo();
        } else {
            System.out.print("DUI: ");
            dui = scanner.nextLine();
        }

        // Verificar si el DUI ya existe
        if (DatabaseUtils.buscarPacientePorDui(dui) != null) {
            System.out.println("Error: El paciente con DUI " + dui + " ya existe.");
            return;
        }

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDui(dui);
        paciente.setFechaNacimiento(fechaNacimiento);

        DatabaseUtils.guardarPaciente(paciente);
        System.out.println("Paciente agregado exitosamente.");
    }

    private static boolean esMenorDeEdad(Date fechaNacimiento) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);
        Date fechaLimite = cal.getTime();
        return fechaNacimiento.after(fechaLimite);
    }

    private static String generarDuiCorrelativo() {
        List<PacienteDTO> pacientes = DatabaseUtils.leerPacientes();
        int maxDui = 0;
        for (PacienteDTO paciente : pacientes) {
            if (paciente.getDui().matches("000000\\d{2}-0")) {
                int numero = Integer.parseInt(paciente.getDui().substring(6, 8));
                if (numero > maxDui) {
                    maxDui = numero;
                }
            }
        }
        return String.format("000000%02d-0", maxDui + 1);
    }


    private static final String[] ESPECIALIDADES = {
            "Cardiología", "Neurología", "Pediatría", "Oncología", "Ortopedia",
            "Endocrinología", "Reumatología", "Neumología", "Infectología", "General"
    };


    private static void agregarDoctor(Scanner scanner, Integer n) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DUI: ");
        String dui = scanner.nextLine();
        System.out.print("Fecha de Nacimiento (dd/MM/yyyy): ");
        String fechaNacimientoStr = scanner.nextLine();
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = DateUtils.parseDate(fechaNacimientoStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.print("Fecha de Reclutamiento (dd/MM/yyyy): ");
        String fechaReclutamientoStr = scanner.nextLine();
        Date fechaReclutamiento = null;
        try {
            fechaReclutamiento = DateUtils.parseDate(fechaReclutamientoStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Submenú para seleccionar especialidad
        System.out.println("Seleccione una especialidad:");
        for (int i = 0; i < ESPECIALIDADES.length; i++) {
            System.out.println((i + 1) + ". " + ESPECIALIDADES[i]);
        }
        System.out.print("Opción: ");
        int opcionEspecialidad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        String especialidad = ESPECIALIDADES[opcionEspecialidad - 1];

        System.out.print("Código: ");
        String codigo = generarCodigoDoctor(n);
        //Feedback del codigo de doctoir asignado
        System.out.println(codigo);

        DoctorDTO doctor = new DoctorDTO();
        doctor.setNombre(nombre);
        doctor.setApellido(apellido);
        doctor.setDui(dui);
        doctor.setFechaNacimiento(fechaNacimiento);
        doctor.setFechaReclutamiento(fechaReclutamiento);
        doctor.setEspecialidad(especialidad);
        doctor.setCodigo(codigo);

        DatabaseUtils.guardarDoctor(doctor);
        System.out.println("Doctor agregado exitosamente.");
    }

    public static String generarCodigoDoctor(Integer count){
        //Creamos dos arreglos unicamente para almacenar los valores a utilizar
        Integer[] n = new Integer[5];
        Character[] s = new Character[5];
        for (int i = 0; i < 5; i++) {
            n[i] = Math.floorDiv(count, (int) Math.pow(10,4-i));
            count = (int) (count - n[i]*Math.pow(10,4-i));
            //Si el valor esta en una posicion par en la cuenta, convertir a caracter.
            if(i%2 != 0){
                s[i] = (char) ('A' + n[i]);
            }
        }
        //Devolver una string en base al format dado anteroirmente
        return String.format("ZNH-%d%s%d-MD-%s%d", n[0], s[1], n[2], s[3], n[4]);
    }

    private static void listarCitasPorDoctor(Scanner scanner) {
        System.out.print("Código del Doctor: ");
        String codigoDoctor = scanner.nextLine();
        List<CitaDTO> citas = DatabaseUtils.leerCitas();
        boolean citasEncontradas = false;
        for (CitaDTO cita : citas) {
            if (cita.getDoctor().getCodigo().equals(codigoDoctor)) {
                System.out.println("Paciente: " + cita.getPaciente().getDui() + ", Especialidad: " + cita.getEspecialidad() +
                        ", Fecha: " + cita.getFecha() + ", Atendido: " + cita.isAtendido());
                citasEncontradas = true;
            }
        }
        if (!citasEncontradas) {
            System.out.println("No se encontraron citas para el doctor con código: " + codigoDoctor);
        }
    }
}