package com.example.democrud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nombre;
	
	@Column
	private String descripcion;
	
	@Column
	private double precio;
	
	@Column
	private double porcentaje_descuento;

	@Column
	private String pais_venta; 
	
	@Column
	private String imagen_frontal; 
	
	@Column
	private String imagen_trasera;
	
	@Column
	private int orden_buscado;
	
	public Producto() {
		super();
	}

	public Producto(Long id,String nombre, String descripcion, double precio, double porcentaje_descuento,
			String pais_venta, int orden_buscado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.porcentaje_descuento = porcentaje_descuento;
		this.pais_venta = pais_venta;
		this.orden_buscado = orden_buscado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getPorcentaje_descuento() {
		return porcentaje_descuento;
	}

	public void setPorcentaje_descuento(double porcentaje_descuento) {
		this.porcentaje_descuento = porcentaje_descuento;
	}

	public String getPais_venta() {
		return pais_venta;
	}

	public void setPais_venta(String pais_venta) {
		this.pais_venta = pais_venta;
	}

	public String getImagen_frontal() {
		return imagen_frontal;
	}

	public void setImagen_frontal(String imagen_frontal) {
		this.imagen_frontal = imagen_frontal;
	}

	public String getImagen_trasera() {
		return imagen_trasera;
	}

	public void setImagen_trasera(String imagen_trasera) {
		this.imagen_trasera = imagen_trasera;
	}

	public int getOrden_buscado() {
		return orden_buscado;
	}

	public void setOrden_buscado(int orden_buscado) {
		this.orden_buscado = orden_buscado;
	}

	
	
	
	
}
