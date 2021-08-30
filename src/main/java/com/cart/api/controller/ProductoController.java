package com.cart.api.controller;


import com.cart.api.dto.ProductoDto;
import com.cart.api.model.Producto;
import com.cart.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("productos")
public class ProductoController {

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity crearProducto(@Autowired ProductoService service, @RequestBody ProductoDto producto) {
		ResponseEntity response = null;

		if(productoValido(producto)) {
			response = service.crearProducto(producto);
		} else {
			response = new ResponseEntity("La información del producto no es correcta. Por favor validar.", HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	private boolean productoValido(ProductoDto producto) {
		return !StringUtils.isEmpty(producto.getNombre()) &&
				!StringUtils.isEmpty(producto.getDescripcion()) &&
				producto.getPrecio() != null &&
				producto.getEsOferta() != null;
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity modificarProducto(@Autowired ProductoService service, @RequestBody ProductoDto producto, @PathVariable UUID id) {
		ResponseEntity response = null;

		if(productoValido(producto)) {
			response = service.modificarProducto(producto, id);
		} else {
			response = new ResponseEntity("La información del producto no es correcta. Por favor validar.", HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity eliminarProducto(@Autowired ProductoService service, @PathVariable UUID id) {
		return service.eliminarProducto(id);
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Producto>> obtenerProductos(@Autowired ProductoService service) {
		return service.obtenerProductos();
	}



}
