package com.cart.api.dto;

import com.cart.api.util.Estado;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class ProductoDto {

    private String nombre;
    private String sku;
    private String descripcion;
    private Double precio;
    private Boolean esOferta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getEsOferta() {
        return esOferta;
    }

    public void setEsOferta(Boolean esOferta) {
        this.esOferta = esOferta;
    }
}
