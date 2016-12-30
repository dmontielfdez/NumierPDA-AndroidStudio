package com.numier.numierpda.Models;

public class MenuDet {
	
	int idMenuDet, numPlato, counter;
	String idMenu, code;

	public MenuDet(int idMenuDet, String idMenu, String code, int numPlato, int counter) {
		super();
		this.idMenuDet = idMenuDet;
		this.idMenu = idMenu;
		this.code = code;
		this.numPlato = numPlato;
		this.counter = counter;
	}


	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}


	public int getIdMenuDet() {
		return idMenuDet;
	}

	public void setIdMenuDet(int idMenuDet) {
		this.idMenuDet = idMenuDet;
	}

	public String getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getNumPlato() {
		return numPlato;
	}

	public void setNumPlato(int numPlato) {
		this.numPlato = numPlato;
	}
	
	

}
