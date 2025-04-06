// src/dto/CitaDTO.java
package dto;

import java.time.LocalDateTime;
import java.util.Date;

public class CitaDTO {
    private DoctorDTO doctor;
    private PacienteDTO paciente;
    private String especialidad;
    private LocalDateTime fecha;
    private boolean atendido;
    private String informacionCompletaPaciente; // Nueva variable

    // Getters y Setters
    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public String getInformacionCompletaPaciente() {
        return informacionCompletaPaciente;
    }

    public void setInformacionCompletaPaciente(String informacionCompletaPaciente) {
        this.informacionCompletaPaciente = informacionCompletaPaciente;
    }
}