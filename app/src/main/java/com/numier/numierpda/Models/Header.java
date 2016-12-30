package com.numier.numierpda.Models;

import java.util.ArrayList;
import java.util.List;

public class Header {

	// ATRIBUTOS
	private int idHeader, numBill, printed, locked, diners, option1, option2,
			option3, function, rate;
	private double amount;
	private String idEmployee, pdaName, billName, tipoCobro;
	private List<Detalle> details;
	private String[] printers;

	// nuevo atributo v2.4
	private String textBill;

	
	
	
	// CONSTRUCTORES
	public Header() {
		this.details = new ArrayList<Detalle>();
	}
	
	public Header(int idHeader) {
		this.idHeader = idHeader;

	}

	public Header(int idHeader, int numBill, String billName, int numDiners,
			double amount, int printed, int locked, int option1, int option2,
			int option3, int function, int rate, String[] printers) {
		this.idHeader = idHeader;
		this.numBill = numBill;
		this.billName = billName;
		this.amount = amount;
		this.diners = numDiners;
		this.printed = printed;
		this.locked = locked;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.function = function;
		this.rate = rate;
		this.printers = printers;
	}

	public Header(int idHeader, int numBill, double amount, int printed,
			int locked, int option1, int option2, int option3,
			String id_employee, String pda_name, int diners,
			List<Detalle> details) {

		this.idHeader = idHeader;
		this.numBill = numBill;
		this.amount = amount;
		this.printed = printed;
		this.locked = locked;
		this.idEmployee = id_employee;
		this.pdaName = pda_name;
		this.diners = diners;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;

		if (details == null)
			this.details = new ArrayList<Detalle>();
		else
			this.details = details;

	}

	// Constructor v2.4 (�NUEVO!)
	// contiene todos los atributos excepto el "int numBill", que ha sido
	// sustituido por "String textBill"
	public Header(int idHeader, int printed, int locked, int diners,
			int option1, int option2, int option3, int function, int rate,
			double amount, String idEmployee, String pdaName, String billName,
			String tipoCobro, List<Detalle> details, String[] printers,
			String textBill) {
		super();
		this.idHeader = idHeader;
		this.printed = printed;
		this.locked = locked;
		this.diners = diners;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.function = function;
		this.rate = rate;
		this.amount = amount;
		this.idEmployee = idEmployee;
		this.pdaName = pdaName;
		this.billName = billName;
		this.tipoCobro = tipoCobro;
		this.details = details;
		this.printers = printers;
		this.textBill = textBill;
	}

	
	
	
	// GETTERS AND SETTERS
	public String getTipoCobro() {
		return tipoCobro;
	}

	public void setTipoCobro(String tipoCobro) {
		this.tipoCobro = tipoCobro;
	}

	public String[] getPrinters() {
		return printers;
	}

	public void setPrinters(String[] printers) {
		this.printers = printers;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	public int getOption1() {
		return option1;
	}

	public void setOption1(int option1) {
		this.option1 = option1;
	}

	public int getOption2() {
		return option2;
	}

	public void setOption2(int option2) {
		this.option2 = option2;
	}

	public int getOption3() {
		return option3;
	}

	public void setOption3(int option3) {
		this.option3 = option3;
	}

	public int getPrinted() {
		return printed;
	}

	public void setPrinted(int printed) {
		this.printed = printed;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getDiners() {
		return diners;
	}

	public void setDiners(int diners) {
		this.diners = diners;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getIdHeader() {
		return idHeader;
	}

	public void setIdHeader(int idHeader) {
		this.idHeader = idHeader;
	}

	public int getNumBill() {
		return numBill;
	}

	public void setNumBill(int numBill) {
		this.numBill = numBill;
	}

	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getPdaName() {
		return pdaName;
	}

	public void setPdaName(String pdaName) {
		this.pdaName = pdaName;
	}

	public List<Detalle> getDetails() {
		return details;
	}

	public void setDetails(List<Detalle> details) {
		this.details = details;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}
	
	// v2.4 nuevo getter y setter (�NUEVO!) 
	public String getTextBill() {
		return textBill;
	}

	public void setTextBill(String textBill) {
		this.textBill = textBill;
	}
	
	

	public void refreshAmount(){
		
		double cantidadTemporal = 0.0;
		double importeSemiFinal = 0.0;	
		
		for (Detalle detalle : details){
			cantidadTemporal = detalle.getQuantity()*detalle.getPrice();
			detalle.setAmount(cantidadTemporal);
			importeSemiFinal = importeSemiFinal+detalle.getAmount();
		}		
		
		this.amount = importeSemiFinal;		
	}
	
	

	@Override
	public String toString() {
		
		if (true){
			return "Header [idHeader=" + idHeader + ", numBill=" + numBill
					+ ", printed=" + printed + ", locked=" + locked + ", diners="
					+ diners + ", amount=" + amount + ", idEmployee=" + idEmployee
					+ ", pdaName=" + pdaName + ", billName=" + billName
					+ ", details=" + details + "]";
		}else{
			return "Header [idHeader=" + idHeader + ", numBill=" + textBill
					+ ", printed=" + printed + ", locked=" + locked + ", diners="
					+ diners + ", amount=" + amount + ", idEmployee=" + idEmployee
					+ ", pdaName=" + pdaName + ", billName=" + billName
					+ ", details=" + details + "]";
		}
		

	}

}
