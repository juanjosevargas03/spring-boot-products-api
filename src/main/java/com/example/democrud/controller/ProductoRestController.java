package com.example.democrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.democrud.commons.Response;
import com.example.democrud.model.Producto;
import com.example.democrud.service.api.FileServiceAPI;
import com.example.democrud.service.api.ProductoServiceAPI;

@RestController
@RequestMapping(value = "/api/v1/products")
@CrossOrigin("*")
public class ProductoRestController {

	
	@Autowired
	private ProductoServiceAPI productoServiceAPI;
	
	@Autowired
	private FileServiceAPI fileServiceAPI;

	@GetMapping(value = "/all")
	public List<Producto> getAll() {
		return productoServiceAPI.getAll();
	}
	
	@GetMapping(value = "/mostWanted")
	public List<Producto> getMostWanted() {
		return productoServiceAPI.getMostWanted();
	}
	
	@GetMapping(value = "/find/{id}")
	public ResponseEntity<Producto> find(@PathVariable Long id) {		
		Producto producto = productoServiceAPI.get(id);
		if (producto == null) {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
		} 
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}

	@GetMapping(value = "/findByName/{nombre}")
	public ResponseEntity<List<Producto>> find(@PathVariable String nombre) {				
		List<Producto> productos =  productoServiceAPI.findByName(nombre);
		
		for(Producto p: productos) {
			p.setOrden_buscado(p.getOrden_buscado() + 1);
			productoServiceAPI.save(p);
		}
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity save(
			@RequestParam(value="id", required=false) String id ,
			@RequestParam(value="nombre",required=false) String nombre,
			@RequestParam(value="descripcion",required=false) String descripcion,
			@RequestParam(value="precio",required=false) String precio,
			@RequestParam(value="porcentaje_descuento",required=false) String porcentaje_descuento,
			@RequestParam(value="pais_venta",required=false) String pais_venta,
            @RequestParam(value="imagen_frontal", required=false) MultipartFile frontal,
            @RequestParam(value="imagen_trasera", required=false) MultipartFile trasera 
			) {
		
		Producto productoAux = null;
		int orden_buscado = 0;
		if(id!=null) {
			productoAux = productoServiceAPI.get(Long.parseLong(id));
			orden_buscado = productoAux.getOrden_buscado();
		}
		
		Producto obj = new Producto(
				id != null ? Long.parseLong(id) : null
				,nombre != null ? nombre : productoAux.getNombre()
				,descripcion != null ? descripcion : productoAux.getDescripcion()
				,precio != null ? Double.parseDouble(precio) : productoAux.getPrecio()
				,porcentaje_descuento != null ? Double.parseDouble(porcentaje_descuento) : productoAux.getPorcentaje_descuento()
				,pais_venta != null ? pais_venta : productoAux.getPais_venta() 
				,orden_buscado
				);
		
		
		Producto producto = productoServiceAPI.validateProducto(obj);
		if (producto == null) {
			return new ResponseEntity<Response>(new Response("Porcentaje de descuento invalido"), HttpStatus.BAD_REQUEST) ;
		} 
		
		String path = "";
		if(frontal==null){
			producto.setImagen_frontal(productoAux.getImagen_frontal());
		}else {
			try {
				path = fileServiceAPI.save(frontal,producto.getNombre());
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Response>(new Response("Error al guardar imagen"), HttpStatus.INTERNAL_SERVER_ERROR) ;		
			}
			producto.setImagen_frontal(path);
		}
		
		if(trasera==null){
			producto.setImagen_trasera(productoAux.getImagen_trasera());
		}else {
			try {
				path = fileServiceAPI.save(trasera,producto.getNombre());

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Response>(new Response("Error al guardar imagen"), HttpStatus.INTERNAL_SERVER_ERROR) ;		
			}
			producto.setImagen_trasera(path);
		}
		
		productoServiceAPI.save(producto);
		return new ResponseEntity<Producto>(producto, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Producto> delete(@PathVariable Long id) {
		Producto producto = productoServiceAPI.get(id);
		if (producto != null) {
			productoServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	

	
}