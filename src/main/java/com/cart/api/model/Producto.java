package com.cart.api.model;

import com.cart.api.util.Estado;

import java.util.UUID;

public class Producto {

    private UUID id;
    private String nombre;
    private String sku;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    private Boolean esOferta;
    private Estado estado;

    public Producto(String nombre, String descripcion, String sku, Double precio, Boolean esOferta) {
        this.id = UUID.randomUUID();
        this.estado = Estado.PENDIENTE;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sku = sku;
        this.precio = precio;
        this.esOferta = esOferta;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
        return this.esOferta ? precio/2 : precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    private Boolean getEsOferta() {
        return esOferta;
    }

    public Double calcularCostoFinal(){
        return getPrecio()*getCantidad();
    }

}
