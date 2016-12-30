package com.numier.numierpda.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Detalle {

	// Atributos
	private int order, printed, isSeling;
	private double price, amount, quantity, priceExtra;
	private String rate, option, productName, idProduct, title, numOrden;
	private boolean decimalQuantity, noImprimir;
	private List<Subproduct> listSubProducts;
	private List<Subproduct> listSubProductsExtra;
	private List<Subproduct> listSubProductsOpcional;
	private int[] idSubProducts;
	private List<Modifier> listModifier;

	// Constructores
	public Detalle(String idProduct, String productName, double quantity,
			double price, double amount, String rate, String option, int order,
			int printed, List<Subproduct> subProducts, String numOrden) {

		this.idProduct = idProduct;
		this.productName = productName;
		this.quantity = quantity;
		this.order = order;
		this.printed = printed;
		this.price = price;
		this.amount = amount;
		this.rate = rate;
		this.option = option;
		this.decimalQuantity = false;
		this.listSubProducts = subProducts;
		this.listSubProductsExtra = new ArrayList<Subproduct>();
		this.listSubProductsOpcional = new ArrayList<Subproduct>();
		this.numOrden = numOrden;
		this.listModifier = new ArrayList<Modifier>();
		this.isSeling = 0;
	}

	public Detalle() {
		this.idProduct = "";
		this.productName = "";
		this.quantity = 0;
		this.order = 0;
		this.printed = 0;
		this.price = 0;
		this.amount = 0;
		this.rate = "";
		this.title = "";
		this.option = "";
		this.decimalQuantity = false;
		this.listSubProducts = new ArrayList<Subproduct>();
		this.listSubProductsExtra = new ArrayList<Subproduct>();
		this.listSubProductsOpcional = new ArrayList<Subproduct>();
		this.numOrden = "";
		this.listModifier = new ArrayList<Modifier>();
		this.isSeling = 0;
	}
	
	
	
	

	// Getters y Setters
	
	

	public int getIsSeling() {
		return isSeling;
	}

	public void setIsSeling(int isSeling) {
		this.isSeling = isSeling;
	}

	public List<Subproduct> getListSubProductsExtra() {
		return listSubProductsExtra;
	}

	public void setListSubProductsExtra(List<Subproduct> listSubProductsExtra) {
		this.listSubProductsExtra = listSubProductsExtra;
	}

	public List<Subproduct> getListSubProductsOpcional() {
		return listSubProductsOpcional;
	}

	public void setListSubProductsOpcional(List<Subproduct> listSubProductsOpcional) {
		this.listSubProductsOpcional = listSubProductsOpcional;
	}

	public boolean isNoImprimir() {
		return noImprimir;
	}

	public List<Modifier> getListModifier() {
		return listModifier;
	}

	public void setListModifier(List<Modifier> listModifier) {
		this.listModifier = listModifier;
	}

	public void setNoImprimir(boolean noImprimir) {
		this.noImprimir = noImprimir;
	}

	public String getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getPrinted() {
		return printed;
	}

	public void setPrinted(int printed) {
		this.printed = printed;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public boolean isDecimalQuantity() {
		return decimalQuantity;
	}

	public void setDecimalQuantity(boolean decimalQuantity) {
		this.decimalQuantity = decimalQuantity;
	}

	public List<Subproduct> getListSubProducts() {
		return listSubProducts;
	}

	public void setListSubProducts(List<Subproduct> listSubProducts) {
		this.listSubProducts = listSubProducts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPriceExtra() {
		return priceExtra;
	}

	public void setPriceExtra(double priceExtra) {
		this.priceExtra = priceExtra;
	}

	public int[] getIdSubProducts() {
		return idSubProducts;
	}

	public void setIdSubProducts(int[] idSubProducts) {
		this.idSubProducts = idSubProducts;
	}

	@Override
	public String toString() {
		return "Detalle [order=" + order + ", printed=" + printed + ", price="
				+ price + ", amount=" + amount + ", quantity=" + quantity
				+ ", priceExtra=" + priceExtra + ", rate=" + rate + ", option="
				+ option + ", productName=" + productName + ", idProduct="
				+ idProduct + ", title=" + title + ", decimalQuantity="
				+ decimalQuantity + ", listSubProducts=" + listSubProducts
				+ ", idSubProducts=" + Arrays.toString(idSubProducts) + "]";
	}

}
