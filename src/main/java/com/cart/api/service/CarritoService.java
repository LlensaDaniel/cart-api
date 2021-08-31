package com.cart.api.service;



import com.cart.api.model.Carrito;
import com.cart.api.model.Producto;
import com.cart.api.util.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class CarritoService {

	private static Carrito carrito = new Carrito();
	private static final String PRODUCTO_INEXISTENTE = "El producto informado no existe. Por favor validar.";

	public ResponseEntity agregarProducto(ProductoService productoService, UUID idProducto, Integer cantidad) {

		HttpStatus status = HttpStatus.OK;
		String message = "Producto agregado al carrito de forma satisfactoria.";

		try {
			Producto producto = productoService.getProducto(idProducto);
			producto.setCantidad(cantidad);
			carrito.agregarProducto(producto);
		}
		catch (RuntimeException e) {
			status = HttpStatus.NOT_FOUND;
			message = "Producto inexistente para ser agregado al carrito. Por favor validar.";
		}
		return new ResponseEntity(message, status);
	}

	public ResponseEntity<List<Producto>> obtenerProductosCarrito(){
		return new ResponseEntity(carrito.getProductos(), HttpStatus.OK);
	}

	public ResponseEntity eliminarProducto(ProductoService productoService, UUID idProducto) {

		HttpStatus status = HttpStatus.OK;
		String message = "Producto eliminado del carrito de forma satisfactoria.";

		try {

			carrito.eliminarProducto(productoService.getProducto(idProducto));
		}

		catch (RuntimeException e) {
			status = HttpStatus.NOT_FOUND;
			message = "Producto inexistente para ser eliminado del carrito. Por favor validar.";
		}
		return new ResponseEntity(message, status);
	}


	public ResponseEntity modificarProductoCarrito(UUID idProducto, Integer cantidad) {

		HttpStatus status = HttpStatus.OK;
		String message = "Producto modificado del carrito de forma satisfactoria.";

		try {
			carrito.getProductos()
					.stream()
					.filter(p -> p.getId().equals(idProducto))
					.findFirst().get().setCantidad(cantidad);
		}
		catch (RuntimeException e) {
			status = HttpStatus.NOT_FOUND;
			message = "Producto inexistente para ser eliminado del carrito. Por favor validar.";
		}
		return new ResponseEntity(message, status);
	}


	public ResponseEntity checkout() {
		double costoFinal = carrito.getProductos()
				.stream()
				.map(p -> p.calcularCostoFinal()).mapToDouble(Double::doubleValue).sum();

		carrito.getProductos()
				.stream()
				.forEach(p -> p.setEstado(Estado.COMPLETO));

		carrito.getProductos().stream().forEach(System.out::println);

		return new ResponseEntity("Checkout realizado. El costo final de su pedido es: " + costoFinal, HttpStatus.OK);
	}
}
