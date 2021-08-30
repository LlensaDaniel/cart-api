package com.cart.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Carrito {

    private List<Producto> productos = new ArrayList<>();

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        this.productos.removeIf(p -> p.getId().equals(producto.getId()));
    }

    public void modificarProducto(Producto producto) {
        this.productos = this.productos.stream()
                .map(p -> p.getId().equals(producto.getId()) ? producto : p)
                .collect(Collectors.toList());
    }

}
