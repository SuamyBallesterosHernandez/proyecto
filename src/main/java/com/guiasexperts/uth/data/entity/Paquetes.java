package com.guiasexperts.uth.data.entity;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Paquetes extends AbstractEntity {

    private String destino;
    private String duracion;
    private String alojamiento;
    private Integer precio;

    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public String getDuracion() {
        return duracion;
    }
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public String getAlojamiento() {
        return alojamiento;
    }
    public void setAlojamiento(String alojamiento) {
        this.alojamiento = alojamiento;
    }
    public Integer getPrecio() {
        return precio;
    }
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

}
