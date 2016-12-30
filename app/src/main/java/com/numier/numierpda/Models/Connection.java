package com.numier.numierpda.Models;

public class Connection {

	// Atributos
	//private String name, id;
	
	//Atributos v2.4
	private String name, url, key, operario, rate, terminal;
	private int id;

	
	//constructor v2.4
	public Connection(int id, String name, String url, String key, String operario, String rate, String terminal) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.key = key;
		this.operario = operario;
		this.rate = rate;
		this.terminal = terminal;
	}
	
	

	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}





	public String getOperario() {
		return operario;
	}


	public void setOperario(String operario) {
		this.operario = operario;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
		@Override
	public String toString() {
		return "Connection [id=" + this.id 
				+", name=" + this.name + "]";
	}

}
