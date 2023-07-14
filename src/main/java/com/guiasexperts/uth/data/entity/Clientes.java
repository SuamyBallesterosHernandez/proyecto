package com.guiasexperts.uth.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Clientes extends AbstractEntity {

    private String nombre;
    private String  edad;
    private String telefono;
    private String direccion;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String  getEdad() {
        return edad;
    }
    public void setEdad(String  edad) {
        this.edad = edad;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String  telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
