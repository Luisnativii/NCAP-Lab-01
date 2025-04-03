import dto.DoctorDTO;
import dto.PacienteDTO;
import dto.CitaDTO;
import utils.DatabaseUtils;
import utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Sistema de Citas Médicas");
            System.out.println("1. Listar todas las citas");
            System.out.println("2. Agendar nueva cita");
            System.out.println("3. Agregar nuevo paciente");
            System.out.println("4. Agregar nuevo doctor");
            System.out.println("5. Listar citas por doctor");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

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
                    agregarDoctor(scanner);
                    break;
                case 5:
                    listarCitasPorDoctor(scanner);
                    break;
                case 6:
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
            System.out.println("Doctor: " + cita.getDoctor().getCodigo() + ", Paciente: " + cita.getPaciente().getDui() +
                    ", Especialidad: " + cita.getEspecialidad() + ", Fecha: " + DateUtils.formatDate(cita.getFecha()) +
                    ", Atendido: " + cita.isAtendido());
        }
    }

    private static void agendarCita(Scanner scanner) {
        System.out.print("Código del Doctor: ");
        String codigoDoctor = scanner.nextLine();
        DoctorDTO doctor = DatabaseUtils.buscarDoctorPorCodigo(codigoDoctor);
        if (doctor == null) {
            System.out.println("Doctor no encontrado.");
            return;
        }

        System.out.print("DUI del Paciente: ");
        String duiPaciente = scanner.nextLine();
        PacienteDTO paciente = DatabaseUtils.buscarPacientePorDui(duiPaciente);
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();
        System.out.print("Fecha de la Cita (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        Date fecha = null;
        try {
            fecha = DateUtils.parseDate(fechaStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDui(dui);
        paciente.setFechaNacimiento(fechaNacimiento);

        DatabaseUtils.guardarPaciente(paciente);
        System.out.println("Paciente agregado exitosamente.");
    }

    private static void agregarDoctor(Scanner scanner) {
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
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();
        System.out.print("Código: ");
        String codigo = scanner.nextLine();

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

    private static void listarCitasPorDoctor(Scanner scanner) {
        System.out.print("Código del Doctor: ");
        String codigoDoctor = scanner.nextLine();
        List<CitaDTO> citas = DatabaseUtils.leerCitas();
        boolean citasEncontradas = false;
        for (CitaDTO cita : citas) {
            if (cita.getDoctor().getCodigo().equals(codigoDoctor)) {
                System.out.println("Paciente: " + cita.getPaciente().getDui() + ", Especialidad: " + cita.getEspecialidad() +
                        ", Fecha: " + DateUtils.formatDate(cita.getFecha()) + ", Atendido: " + cita.isAtendido());
                citasEncontradas = true;
            }
        }
        if (!citasEncontradas) {
            System.out.println("No se encontraron citas para el doctor con código: " + codigoDoctor);
        }
    }
}