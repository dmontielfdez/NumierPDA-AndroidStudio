package com.numier.numierpda.Models;

public class Product {

	// Atributos
	private String id, name, category, color, rateName1, rateName2, rateName3, rateName4, option1, option2, option3, option4, option5, descri;
	private double rate1, rate2, rate3, rate4, discount;
	private int rateOption1, rateOption2, rateOption3, rateOption4, askWeight, askPrice, numberSubproducts, absolutPrice, numPlato, isMenu, seling, haveSubproducts;

	// Constructores
	public Product(String id, String name, String category, String color,
				   String option1, String option2, String option3, String option4,
				   String option5, double rate1, double rate2, double rate3,
				   double rate4, int askWeight, int askPrice, int haveSubproducts,
				   int numberSubproducts, double discount, int absolutPrice, int numPlato, int isMenu, int seling, String descri) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.color = color;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.option5 = option5;
		this.rate1 = rate1;
		this.rate2 = rate2;
		this.rate3 = rate3;
		this.rate4 = rate4;
		this.askWeight = askWeight;
		this.askPrice = askPrice;
		this.haveSubproducts = haveSubproducts;
		this.discount = discount;
		this.numberSubproducts = numberSubproducts;
		this.absolutPrice = absolutPrice;
		this.numPlato = numPlato;
		this.isMenu = isMenu;
		this.seling = seling;
		this.descri = descri;
	}

	// Constructores
	public Product(String id, String name) {
		this.id = id;
		this.name = name;
		this.category = "";
		this.color = "";
		this.option1 = "";
		this.option2 = "";
		this.option3 = "";
		this.option4 = "";
		this.option5 = "";
		this.rate1 = 0;
		this.rate2 = 0;
		this.rate3 = 0;
		this.rate4 = 0;
		this.askWeight = 0;
		this.askPrice = 0;
		this.haveSubproducts = 0;
		this.discount = 0;
		this.numberSubproducts = 0;
		this.absolutPrice = 0;
		this.numPlato = 0;
		this.isMenu = 0;
		this.seling = 0;
		this.descri = "";
	}





	// Getters y Setters
	
	

	public int getSeling() {
		return seling;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

	public void setSeling(int seling) {
		this.seling = seling;
	}

	public int getAbsolutPrice() {
		return absolutPrice;
	}





	public void setAbsolutPrice(int absolutPrice) {
		this.absolutPrice = absolutPrice;
	}





	public int getNumPlato() {
		return numPlato;
	}





	public void setNumPlato(int numPlato) {
		this.numPlato = numPlato;
	}





	public int getIsMenu() {
		return isMenu;
	}





	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}





	public String getRateName4() {
		return rateName4;
	}



	public void setRateName4(String rateName4) {
		this.rateName4 = rateName4;
	}



	public int getRateOption4() {
		return rateOption4;
	}



	public void setRateOption4(int rateOption4) {
		this.rateOption4 = rateOption4;
	}



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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRateName1() {
		return rateName1;
	}

	public void setRateName1(String rateName1) {
		this.rateName1 = rateName1;
	}

	public String getRateName2() {
		return rateName2;
	}

	public void setRateName2(String rateName2) {
		this.rateName2 = rateName2;
	}

	public String getRateName3() {
		return rateName3;
	}

	public void setRateName3(String rateName3) {
		this.rateName3 = rateName3;
	}

	public double getRate1() {
		return rate1;
	}

	public void setRate1(double rate1) {
		this.rate1 = rate1;
	}

	public double getRate2() {
		return rate2;
	}

	public void setRate2(double rate2) {
		this.rate2 = rate2;
	}

	public double getRate3() {
		return rate3;
	}

	public void setRate3(double rate3) {
		this.rate3 = rate3;
	}

	public double getRate4() {
		return rate4;
	}

	public void setRate4(double rate4) {
		this.rate4 = rate4;
	}

	public int getRateOption1() {
		return rateOption1;
	}

	public void setRateOption1(int rateOption1) {
		this.rateOption1 = rateOption1;
	}

	public int getRateOption2() {
		return rateOption2;
	}

	public void setRateOption2(int rateOption2) {
		this.rateOption2 = rateOption2;
	}

	public int getRateOption3() {
		return rateOption3;
	}

	public void setRateOption3(int rateOption3) {
		this.rateOption3 = rateOption3;
	}

	public int getAskWeight() {
		return askWeight;
	}

	public void setAskWeight(int askWeight) {
		this.askWeight = askWeight;
	}

	public int getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(int askPrice) {
		this.askPrice = askPrice;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSubproducts() {
		return haveSubproducts;
	}

	public void setSubproducts(int haveSubproducts) {
		this.haveSubproducts = haveSubproducts;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getNumberSubproducts() {
		return numberSubproducts;
	}

	public void setNumberSubproducts(int numberSubproducts) {
		this.numberSubproducts = numberSubproducts;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category="
				+ category + ", color=" + color + ", rateName1=" + rateName1
				+ ", rateName2=" + rateName2 + ", rateName3=" + rateName3+",rateName4="+rateName4
				+ ", option1=" + option1 + ", option2=" + option2
				+ ", option3=" + option3 + ", option4=" + option4
				+ ", option5=" + option5 + ", rate1=" + rate1 + ", rate2="
				+ rate2 + ", rate3=" + rate3 + ", rate4=" + rate4
				+ ", discount=" + discount + ", rateOption1=" + rateOption1
				+ ", rateOption2=" + rateOption2 + ", rateOption3="
				+ rateOption3 + ",rateOption4="+rateOption4+", askWeight=" + askWeight + ", askPrice="
				+ askPrice + ", haveSubproducts=" + haveSubproducts + ",seling=" + seling + ", numberSubproducts="
				+ numberSubproducts + "]";
	}

}
