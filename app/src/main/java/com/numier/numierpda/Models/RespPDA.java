package com.numier.numierpda.Models;

import java.util.List;

public class RespPDA {
	
	int status;
	List<Header> list;
	String statusText;
	
	
	/*/////////////////////
	 * CONSTRUCTORES
	 */////////////////////
	
	public RespPDA(int status, List<Header> list, String statusText) {
		super();
		this.status = status;
		this.list = list;
		this.statusText = statusText;
	}
	
	public RespPDA() {

	}
	

	public List<Header> getList() {
		return list;
	}


	/*///////////////////////////
	 * GETTERS & SETTERS
	 *//////////////////////////
	 

	public void setList(List<Header> list) {
		this.list = list;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	

}
