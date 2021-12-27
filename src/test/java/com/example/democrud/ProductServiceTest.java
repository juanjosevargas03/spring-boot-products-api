package com.example.democrud;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.democrud.model.Producto;
import com.example.democrud.service.api.ProductoServiceAPI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
	
	@Mock
	private ProductoServiceAPI productoServiceAPI; 
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Producto producto =  new Producto((long) 1,"producto1","producto1",200.0,30.0,"chile",0);
		Producto producto2 =  new Producto((long) 2,"producto2","producto2",200.0,40.0,"mexico",0);
		List<Producto> productos = new ArrayList<Producto>();
		productos.add(producto);
		productos.add(producto2);

		Mockito.when(productoServiceAPI.get((long) 1)).thenReturn(producto);
		Mockito.when(productoServiceAPI.save(producto)).thenReturn(producto);
		Mockito.when(productoServiceAPI.findByName("producto")).thenReturn(productos);

	}
	
	   @Test
	   public void whenValidGetID_ThenReturnProduct(){
		   Producto producto = productoServiceAPI.get((long) 1);
	       Assertions.assertThat(producto.getNombre()).isEqualTo("producto1");
	   }
	
	   @Test
	   public void whenValidUpdate_ThenReturnProduct(){
		   Producto producto = productoServiceAPI.get((long) 1);
		   producto.setPorcentaje_descuento(20);
		   Producto productoRes = productoServiceAPI.save(producto);
	       Assertions.assertThat(productoRes.getPorcentaje_descuento()).isEqualTo(20);
	   }
	   
	   @Test
	   public void findByName_Test(){
		   List<Producto> productos = productoServiceAPI.findByName("producto");
	       Assertions.assertThat(productos.size()).isEqualTo(2);
	   }
	
	  
	
}
