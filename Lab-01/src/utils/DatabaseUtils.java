// src/utils/DatabaseUtils.java
package utils;

import dto.DoctorDTO;
import dto.PacienteDTO;
import dto.CitaDTO;
import entity.Doctor;
import entity.Paciente;
import entity.Cita;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    private static final String DOCTORES_FILE = "doctores.txt";
    private static final String PACIENTES_FILE = "pacientes.txt";
    private static final String CITAS_FILE = "citas.txt";

    public static List<DoctorDTO> leerDoctores() {
        List<DoctorDTO> doctores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 7) {
                    continue; // Saltar esta línea si no tiene el formato correcto
                }
                DoctorDTO doctor = new DoctorDTO();
                doctor.setNombre(data[0]);
                doctor.setApellido(data[1]);
                doctor.setDui(data[2]);
                doctor.setFechaNacimiento(DateUtils.parseDate(data[3]));
                doctor.setFechaReclutamiento(DateUtils.parseDate(data[4]));
                doctor.setEspecialidad(data[5]);
                doctor.setCodigo(data[6]);
                doctores.add(doctor);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return doctores;
    }

    public static List<PacienteDTO> leerPacientes() {
        List<PacienteDTO> pacientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PACIENTES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                PacienteDTO paciente = new PacienteDTO();
                paciente.setNombre(data[0]);
                paciente.setApellido(data[1]);
                paciente.setDui(data[2]);
                paciente.setFechaNacimiento(DateUtils.parseDate(data[3]));
                pacientes.add(paciente);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public static List<CitaDTO> leerCitas() {
        List<CitaDTO> citas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CITAS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                CitaDTO cita = new CitaDTO();
                DoctorDTO doctor = buscarDoctorPorCodigo(data[0]);
                if (doctor == null) {
                    System.err.println("Doctor no encontrado: " + data[0]);
                    continue; // Saltar esta cita si el doctor no se encuentra
                }
                cita.setDoctor(doctor);
                PacienteDTO paciente = buscarPacientePorDui(data[1]);
                if (paciente == null) {
                    System.err.println("Paciente no encontrado: " + data[1]);
                    continue; // Saltar esta cita si el paciente no se encuentra
                }
                cita.setPaciente(paciente);
                cita.setEspecialidad(data[2]);
                cita.setFecha(DateUtils.parseDate(data[3]));
                cita.setAtendido(Boolean.parseBoolean(data[4]));
                citas.add(cita);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public static DoctorDTO buscarDoctorPorCodigo(String codigo) {
        List<DoctorDTO> doctores = leerDoctores();
        for (DoctorDTO doctor : doctores) {
            if (doctor.getCodigo().equals(codigo)) {
                return doctor;
            }
        }
        return null;
    }

    public static PacienteDTO buscarPacientePorDui(String dui) {
        List<PacienteDTO> pacientes = leerPacientes();
        for (PacienteDTO paciente : pacientes) {
            if (paciente.getDui().equals(dui)) {
                return paciente;
            }
        }
        return null;
    }

    public static void guardarDoctor(DoctorDTO doctor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORES_FILE, true))) {
            bw.write(doctor.getNombre() + "," + doctor.getApellido() + "," + doctor.getDui() + "," +
                    DateUtils.formatDate(doctor.getFechaNacimiento()) + "," +
                    DateUtils.formatDate(doctor.getFechaReclutamiento()) + "," +
                    doctor.getEspecialidad() + "," + doctor.getCodigo());
            bw.newLine(); // Asegura que cada doctor se escriba en una nueva línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarPaciente(PacienteDTO paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PACIENTES_FILE, true))) {
            bw.write(paciente.getNombre() + "," + paciente.getApellido() + "," + paciente.getDui() + "," +
                    DateUtils.formatDate(paciente.getFechaNacimiento()));
            bw.newLine(); // Asegura que cada paciente se escriba en una nueva línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarCita(CitaDTO cita) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CITAS_FILE, true))) {
            bw.write(cita.getDoctor().getCodigo() + "," + cita.getPaciente().getDui() + "," +
                    cita.getEspecialidad() + "," + DateUtils.formatDate(cita.getFecha()) + "," +
                    cita.isAtendido());
            bw.newLine(); // Asegura que cada cita se escriba en una nueva línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}