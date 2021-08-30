package com.cart.api.controller;


import com.cart.api.dto.CarritoDto;
import com.cart.api.model.Producto;
import com.cart.api.service.CarritoService;
import com.cart.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("carrito")
public class CarritoController {

	private static final String INFORMACION_INVALIDA = "El id de producto o la cantidad no son correctos. Por favor validar.";
	@PostMapping(value = "/productos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity agregarProducto(@Autowired CarritoService service, @Autowired ProductoService productoService,
										  @RequestBody CarritoDto carritoDto, @PathVariable UUID id) {
		ResponseEntity response = null;
		if(validarInformacionNecesaria(carritoDto, id)) {
			response = service.agregarProducto(productoService, id, carritoDto.getCantidad());
		} else {
			response = new ResponseEntity(INFORMACION_INVALIDA, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PutMapping(value = "/productos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity modificarProducto(@Autowired CarritoService service, @RequestBody CarritoDto carritoDto, @PathVariable UUID id) {
		ResponseEntity response = null;

		if(validarInformacionNecesaria(carritoDto, id)) {
			response = service.modificarProductoCarrito(id, carritoDto.getCantidad());
		} else {
			response = new ResponseEntity(INFORMACION_INVALIDA, HttpStatus.BAD_REQUEST);
		}
		return response;
	}



	@DeleteMapping(value = "/productos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity eliminarProductoCarrito(@Autowired CarritoService service, @Autowired ProductoService productoService,
												  @PathVariable UUID id) {
		ResponseEntity response = null;
		if(validarId(id)) {
			response = service.eliminarProducto(productoService, id);
		} else {
			response = new ResponseEntity("id de producto es requerido para eliminar del carrito", HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@GetMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Producto>> obtenerProductosCarrito(@Autowired CarritoService service) {
		return service.obtenerProductosCarrito();
	}

	@PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity checkoutCarrito(@Autowired CarritoService service) {
		return service.checkout();
	}

	private boolean validarInformacionNecesaria(CarritoDto carritoDto, UUID id) {
		Integer cantidad = carritoDto.getCantidad();
		return validarId(id) && cantidad != null && cantidad.doubleValue() > 0;
	}

	private boolean validarId(UUID id) {
		return id != null;
	}

}
