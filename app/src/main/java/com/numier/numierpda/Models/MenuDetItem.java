package com.numier.numierpda.Models;

public class MenuDetItem {
	
	String idProduct;
	int numProducts;
	
	public MenuDetItem(String idProduct, int numProducts) {
		super();
		this.idProduct = idProduct;
		this.numProducts = numProducts;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public int getNumProducts() {
		return numProducts;
	}

	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}
	
	
	
	

}
