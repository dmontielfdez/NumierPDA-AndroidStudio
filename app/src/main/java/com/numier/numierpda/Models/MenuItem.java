package com.numier.numierpda.Models;

import java.util.ArrayList;

public class MenuItem {
	
	String title;
	ArrayList<MenuDet> listMenuDet;
	
	public MenuItem(String title, ArrayList<MenuDet> listMenuDet) {
		super();
		this.title = title;
		this.listMenuDet = listMenuDet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<MenuDet> getListMenuDet() {
		return listMenuDet;
	}

	public void setListMenuDet(ArrayList<MenuDet> listMenuDet) {
		this.listMenuDet = listMenuDet;
	}
	

}
