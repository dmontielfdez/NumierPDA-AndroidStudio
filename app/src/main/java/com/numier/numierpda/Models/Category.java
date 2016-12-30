package com.numier.numierpda.Models;

import java.util.List;

public class Category {

	// Atributos
	private String id, name, code;
	private List<Product> products;

	// Constructores
	public Category(String id, String name, String code, List<Product> products) {
		this.name = name;
		this.id = id;
		this.code = code;
		this.products = products;
	}

	public Category(String name, String code, List<Product> products) {
		this.name = name;
		this.code = code;
		this.products = products;
	}
	
	

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Category(String id, String name) {
		this.name = name;
		this.id = id;
	}

	// Getters y Setters
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	// Metodo toString
	@Override
	public String toString() {
		return "Category [name=" + name + ", id=" + id + ", products="
				+ products + "]";
	}

}
