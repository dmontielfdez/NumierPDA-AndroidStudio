package com.numier.numierpda.Models;

public class Worker {

	// Atributos
	//private String name, id;
	
	//Atributos v2.4
	private String name, id, operarioPassword;
	

	// Constructor
//	public Worker(String id, String name) {
//		this.id = id;
//		this.name = name;
//	}
	
	//constructor v2.4
	public Worker(String id, String pass, String name) {
		this.id = id;
		this.operarioPassword = pass;
		this.name = name;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//v2.4 getter setter del nuevo atributo
	public String getOperarioPassword() {
		return operarioPassword;
	}

	public void setOperarioPassword(String operarioPassword) {
		this.operarioPassword = operarioPassword;
	}

//	@Override
//	public String toString() {
//		return "Worker [id=" + id + ", name=" + name + "]";
//	}
	
	//v2.4
	@Override
	public String toString() {
		return "Worker [id=" + this.id
				+", password="+this.operarioPassword 
				+", name=" + this.name + "]";
	}

}
