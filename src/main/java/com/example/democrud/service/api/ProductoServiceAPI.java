package com.example.democrud.service.api;

import java.util.List;

import com.example.democrud.commons.GenericServiceAPI;
import com.example.democrud.model.Producto;

public interface ProductoServiceAPI extends GenericServiceAPI<Producto, Long>  {
	
	public List<Producto> findByName(String nombre);
	
	public Producto validateProducto(Producto p) ;
	
	public List<Producto> getMostWanted();

}
