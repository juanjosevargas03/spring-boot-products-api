package com.example.democrud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.democrud.commons.GenericServiceImpl;
import com.example.democrud.dao.api.ProductoDaoAPI;
import com.example.democrud.model.Producto;
import com.example.democrud.service.api.ProductoServiceAPI;

@Service
public class ProductoServiceImpl extends GenericServiceImpl<Producto, Long> implements ProductoServiceAPI {

	@Autowired
	private ProductoDaoAPI productoDaoAPI;
	
	@Override
	public CrudRepository<Producto, Long> getDao() {
		return productoDaoAPI;
	}

	@Override
	public List<Producto> findByName(String nombre) {
		return productoDaoAPI.findByNombreLikeIgnoreCase("%"+nombre+"%");
	}
	
	public Producto validateProducto(Producto p) {
		
		String pais = p.getPais_venta();
		if(pais.equalsIgnoreCase("Colombia") || pais.equalsIgnoreCase("Mexico")) {
			if(p.getPorcentaje_descuento() > 50) {
				return null;
			}		
		}
		if(pais.equalsIgnoreCase("Chile") || pais.equalsIgnoreCase("Peru")) {
			if(p.getPorcentaje_descuento() > 30) {
				return null;
			}
		}
		
		return p;	
	}

	public List<Producto> getMostWanted() {
		List<Producto> productos = getAll();
		productos.removeIf(p -> (p.getOrden_buscado() == 0));
		productos.sort(( p2,p1) -> new Integer(p1.getOrden_buscado()).compareTo(p2.getOrden_buscado()) );
		
		return productos;
	}
	
	
}
