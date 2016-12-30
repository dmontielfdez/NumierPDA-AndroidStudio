package com.numier.numierpda.Models;


public class Menu {

	int idMenu;
	String idProduct, title1, title2, title3, title4, title5, title6;

	public Menu(int idMenu, String idProduct, String title1, String title2, String title3,
			String title4, String title5, String title6) {
		super();
		this.idMenu = idMenu;
		this.idProduct = idProduct;
		this.title1 = title1;
		this.title2 = title2;
		this.title3 = title3;
		this.title4 = title4;
		this.title5 = title5;
		this.title6 = title6;
	}
	
	

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public int getIdMenu() {
		return idMenu;
	}
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getTitle3() {
		return title3;
	}
	public void setTitle3(String title3) {
		this.title3 = title3;
	}
	public String getTitle4() {
		return title4;
	}
	public void setTitle4(String title4) {
		this.title4 = title4;
	}
	public String getTitle5() {
		return title5;
	}
	public void setTitle5(String title5) {
		this.title5 = title5;
	}
	public String getTitle6() {
		return title6;
	}
	public void setTitle6(String title6) {
		this.title6 = title6;
	}




}
