package com.example.democrud.dao.api;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.democrud.model.Producto;

public interface ProductoDaoAPI extends CrudRepository<Producto, Long> {

	List<Producto> findByNombreLikeIgnoreCase(String string);
	
}
