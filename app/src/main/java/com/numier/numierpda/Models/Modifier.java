package com.numier.numierpda.Models;

public class Modifier {

	// Atributos
	private String name;
	private int id;
	private double price;
	private boolean checked;
	private String grupo;

	// Constructores
	public Modifier() {
	}

	public Modifier(int id, String name, double price, boolean checked, String grupo) {
		this.name = name;
		this.id = id;
		this.price = price;
		this.checked = checked;
		this.grupo = grupo;
	}
	
	

	// Getters y Setters

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	// To String
	@Override
	public String toString() {
		return "Modifier [name=" + name + ", id=" + id + ", price=" + price+ ", grupo=" + grupo+ "]";
	}

}
