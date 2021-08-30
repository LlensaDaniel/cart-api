package com.cart.api.service;



import com.cart.api.dto.ProductoDto;
import com.cart.api.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class ProductoService {

	private static List<Producto> productos = new ArrayList<>();

	private static final String PRODUCTO_INEXISTENTE = "El producto informado no existe. Por favor validar.";

	public ResponseEntity crearProducto(ProductoDto requestDto) {

		HttpStatus status = HttpStatus.OK;
		Producto producto = new Producto(requestDto.getNombre(), requestDto.getDescripcion(), requestDto.getSku(), requestDto.getPrecio(), requestDto.getEsOferta());

		productos.add(producto);

		return new ResponseEntity<Producto>(producto, status);
	}

	public ResponseEntity modificarProducto(ProductoDto requestDto, UUID id) {
		ResponseEntity response = null;
		HttpStatus status = HttpStatus.OK;

		if(productoExistente(id)) {
			Producto productoParaActualizar = productos.stream()
														.filter(producto -> producto.getId().equals(id))
														.findFirst().get();
			reemplazarInformacionProducto(productoParaActualizar, requestDto);
			actualizarProducto(productoParaActualizar);
			response = new ResponseEntity(productoParaActualizar, HttpStatus.OK);
		} else {
			response = new ResponseEntity(PRODUCTO_INEXISTENTE, HttpStatus.NOT_FOUND);
		}

		return response;
	}

	public ResponseEntity eliminarProducto(UUID id) {
		ResponseEntity response = null;
		HttpStatus status = HttpStatus.OK;

		if(productoExistente(id)) {
			quitarProducto(id);
		} else {
			response = new ResponseEntity(PRODUCTO_INEXISTENTE, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	private void actualizarProducto(Producto producto) {
		productos = productos.stream()
				.map(p -> p.getId().toString().equals(producto.getId().toString())? producto : p)
				.collect(Collectors.toList());
	}

	private void reemplazarInformacionProducto(Producto productoParaActualizar, ProductoDto requestDto) {
		productoParaActualizar.setNombre(requestDto.getNombre());
		productoParaActualizar.setDescripcion(requestDto.getDescripcion());
		productoParaActualizar.setSku(requestDto.getSku());
		productoParaActualizar.setPrecio(requestDto.getPrecio());
	}

	private boolean productoExistente(UUID id) {
		return productos.stream().anyMatch(producto -> producto.getId().equals(id));
	}

	public void quitarProducto(UUID id) {
		productos.removeIf(p -> p.getId().equals(id));
	}

	public ResponseEntity<List<Producto>> obtenerProductos() {
		return new ResponseEntity(productos, HttpStatus.OK);
	}

	public Producto getProducto(UUID idProducto) {
		if(productoExistente(idProducto)) {
			System.out.println("$$$$$$$$$$$ Existe el producto que quiero agregar al carrito.");
			return productos.stream().filter(producto -> producto.getId().equals(idProducto)).findFirst().get();
		} else {
			throw new RuntimeException("Producto inexistente.");
		}

	}
}
