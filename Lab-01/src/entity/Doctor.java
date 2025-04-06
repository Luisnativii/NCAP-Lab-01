package entity;

import java.util.Date;

public class Doctor {
    private String nombre;
    private String apellido;
    private String dui;
    private Date fechaNacimiento;
    private Date fechaReclutamiento;
    private String especialidad;
    private String codigo;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaReclutamiento() {
        return fechaReclutamiento;
    }

    public void setFechaReclutamiento(Date fechaReclutamiento) {
        this.fechaReclutamiento = fechaReclutamiento;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}