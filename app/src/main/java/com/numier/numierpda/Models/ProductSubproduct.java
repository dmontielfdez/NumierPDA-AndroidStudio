package com.numier.numierpda.Models;

public class ProductSubproduct {

	// Atributos
	private int idSubproduct, included, orden;
	private String idProduct, type;
	private double price;

	// Constructores
	public ProductSubproduct(String idProduct, int idSubproduct, int included, double price, int orden, String type) {
		this.idProduct = idProduct;
		this.idSubproduct = idSubproduct;
		this.included = included;
		this.price = price;
		this.orden = orden;
		this.type = type;
	}
	
	
	

	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public int getOrden() {
		return orden;
	}


	public void setOrden(int orden) {
		this.orden = orden;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	public ProductSubproduct() {
	}

	// Getters y Setters
	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public int getIdSubproduct() {
		return idSubproduct;
	}

	public void setIdSubproduct(int idSubproduct) {
		this.idSubproduct = idSubproduct;
	}

	public int getIncluded() {
		return included;
	}

	public void setIncluded(int included) {
		this.included = included;
	}

	@Override
	public String toString() {
		return "ProductSubproduct [idProduct=" + idProduct + ", idSubproduct="
				+ idSubproduct + ", included=" + included + "]";
	}

}
