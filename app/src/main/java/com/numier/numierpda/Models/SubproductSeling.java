package com.numier.numierpda.Models;

public class SubproductSeling {
	
	private  String id, idProduct, idSubproduct, type, name;
	private boolean checked;
	private int counter;
	private double price;
	

	public SubproductSeling(String idProduct, String idSubproduct, String name, String type, boolean checked, int counter, double price) {
		super();
		this.idProduct = idProduct;
		this.idSubproduct = idSubproduct;
		this.name = name;
		this.type = type;
		this.checked = checked;
		this.counter = counter;
		this.price = price;
	}
	
	
	
	

	public double getPrice() {
		return price;
	}





	public void setPrice(double price) {
		this.price = price;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public String getIdProduct() {
		return idProduct;
	}





	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}





	public int getCounter() {
		return counter;
	}





	public void setCounter(int counter) {
		this.counter = counter;
	}





	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIdSubproduct() {
		return idSubproduct;
	}

	public void setIdSubproduct(String idSubproduct) {
		this.idSubproduct = idSubproduct;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
