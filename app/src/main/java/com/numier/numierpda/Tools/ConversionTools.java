package com.numier.numierpda.Tools;


import java.text.DecimalFormat;

public class ConversionTools {

//	public static String getFormatAmount(double amount) {
//
//		DecimalFormat df = new DecimalFormat("0.00");
//
//		String formatAmount;
//
//		// Compruebo el valor del importe y en caso de ser menor a 1 cambio la
//		// manera en la que lo formateo
//		if (amount <= -1.00) {
//			formatAmount = df.format(amount) + "€";
//		} else if (amount > -1.00 && amount < 0) {
//			formatAmount = df.format(amount) + "€";
//		} else if (amount < 1.00) {
//			formatAmount = df.format(amount) + "€";
//		} else {
//			formatAmount = df.format(amount) + "€";
//		}
//		return formatAmount;
//	}
//
	public static String getFormatPrice(double price, boolean showLabel) {

		DecimalFormat df = new DecimalFormat("0.00");

		String formatPrice;

		if (price < -1.00) {
			if (showLabel)
				formatPrice = "Precio: " + df.format(price) + " €";

			else
				formatPrice = df.format(price) + "€";
		}

		else if (price > -1.00 && price < 0) {

			if (showLabel)
				formatPrice = "Precio: " + price + " €";

			else
				formatPrice = df.format(price) + "€";

		}

		else if (price < 1.00) {

			if (showLabel)
				formatPrice = "Precio: 0" + df.format(price) + " €";

			else
				formatPrice = df.format(price) + "€";

		} else {

			if (showLabel)
				formatPrice = "Precio: " + df.format(price) + " €";

			else
				formatPrice = df.format(price) + "€";
		}
		return formatPrice;
	}
//
//	/**
//	 * Versión para tickets
//	 *
//	 *
//	 */
//	public static String getFormatPriceRevised(double price, boolean showLabel) {
//
//		DecimalFormat df = new DecimalFormat("#.00");
//
//		String formatPrice;
//
//		// Compruebo el valor del precio y en caso de ser menor a 1 cambio la
//		// manera en la que lo formateo
//		if (price < -1.00) {
//			if (showLabel)
//
//				formatPrice = df.format(price) + " €";
//			else
//				formatPrice = df.format(price) + " €";
//		}
//
//		else if (price > -1.00 && price < 0) {
//
//			if (showLabel)
//
//				formatPrice = price + " €";
//			else
//				formatPrice = "0" + price + " €";
//
//		}
//
//		else if (price < 1.00) {
//
//			if (showLabel){
//
//				//original de la versión
//				//formatPrice = df.format(price) + " €";
//
//				//v2.4 nuevo se ha hecho esto para evitar que dé valores sin enteros (sólo daba decimales)
//				formatPrice = new DecimalFormat("0.00").format(price);
//
//			}
//			else
//				formatPrice = "0" + df.format(price) + " €";
//
//		} else {
//
//			if (showLabel)
//
//				formatPrice = df.format(price) + " €";
//			else
//				formatPrice = df.format(price) + " €";
//		}
//		return formatPrice;
//	}
//
//	public static double getFormatPricePretty(double price) {
//
//		DecimalFormat df = new DecimalFormat("#.00");
//
//		String formatPrice;
//
//		// Compruebo el valor del precio y en caso de ser menor a 1 cambio la
//		// manera en la que lo formateo
//
//		if (price < 0) {
//			formatPrice = df.format(price);
//		} else if (price < 1.00) {
//			formatPrice = "0" + df.format(price);
//
//		} else {
//			formatPrice = df.format(price);
//		}
//
//		formatPrice = formatPrice.replaceAll(",", ".");
//		return Double.parseDouble(formatPrice);
//	}
//
//	/**
//	 * Versión nueva del método para Ticket.
//	 * @param quantity
//	 * @param decimal
//	 * @return
//	 */
//	public static String getFormatQuantity(double quantity, boolean decimal) {
//
//		DecimalFormat df = new DecimalFormat("#.000");
//
//		String formatQuantity;
//
//		// Compruebo en primer lugar si se trata de una unidad entera o con
//		// decimales
//
//		if (decimal) {
//			if (quantity < 1.00) {
//				formatQuantity = "0" + df.format(quantity) + " x";
//			} else {
//				formatQuantity = df.format(quantity) + " x";
//
//			}
//		} else {
//			int quantityInteger = (int) quantity;
//			if (quantityInteger > 1){
//				formatQuantity = Integer.toString(quantityInteger) + "x";
//			}
//			// formatQuantity = Integer.toString(quantityInteger) + " Uds";
//			// v2.4
//
//			else if(quantityInteger < 1){
//				formatQuantity = "0" + df.format(quantity) + "x";
//			}
//			else {
//				formatQuantity = Integer.toString(quantityInteger) + "x";
//			}
//			// formatQuantity = Integer.toString(quantityInteger) + " Ud";
//			// v2.4d
//
//		}
//
//		return formatQuantity;
//	}
//
//	public static String getFormatQuantityRevised(double quantity, boolean decimal) {
//
//		DecimalFormat df = new DecimalFormat("#.000");
//
//		String formatQuantity;
//
//		// Compruebo en primer lugar si se trata de una unidad entera o con
//		// decimales
//
//		if (decimal) {
//			if (quantity < 1.00) {
//				// formatQuantity = "0" + df.format(quantity) + " Uds";
//				// v2.4
//				formatQuantity = "0" + df.format(quantity) + "x";
//			} else {
//				// formatQuantity = df.format(quantity) + " Uds";
//				// v2.4
//				formatQuantity = df.format(quantity) + "x";
//			}
//		} else {
//			int quantityInteger = (int) quantity;
//			if (quantityInteger > 1){
//				formatQuantity = Integer.toString(quantityInteger) + "x";
//			}
//			// formatQuantity = Integer.toString(quantityInteger) + " Uds";
//			// v2.4
//
//			else if(quantityInteger < 1){
//				formatQuantity = "0" + df.format(quantity) + "x";
//			}
//			else {
//				formatQuantity = Integer.toString(quantityInteger) + "x";
//			}
//			// formatQuantity = Integer.toString(quantityInteger) + " Ud";
//			// v2.4
//
//
//		}
//
//		return formatQuantity;
//	}
//
//	// Metodo que genera los titulos a la lista completa de los detalles
//	public static void generateTitles(final List<Detalle> details, Database db) {
//
//		for (Detalle detail : details) {
//			generateTitle(detail, db);
//		}
//	}
//
//	// Metodo para generar el titulo del detalle
//	public static void generateTitle(final Detalle detail, Database db) {
//
//		detail.setTitle("");
//
//		if (detail.getRate() != null)
//			if (detail.getRate().length() > 0)
//				detail.setTitle(detail.getRate());
//
//		if (detail.getIdProduct().equals("DDDDD")) {
//			// detail.setTitle(" - "+detail.getListSubProducts().get(0).getId()+"%");
//			// TODO: Da un NullPointer cuando se le pasa un descuento. ¿POR QUÉ?
//			detail.setTitle(" - " + "%");
//		} else {
//			if (detail.getListSubProducts() != null)
//				// Compruebo si tengo algun subproducto
//				if (detail.getListSubProducts().size() > 0) {
//
//					StringBuilder subProductsName = new StringBuilder("(");
//
//					// Recorro los subproductos
//					for (int i = 0; i < detail.getListSubProducts().size(); i++) {
//
//						// Saco la relacion productsubproduct para comprobar si
//						// esta
//						// incluido
//
//						if(detail.getListSubProducts().get(i) != null){
//							ProductSubproduct bp = new ProductSubproductCrud(db)
//									.findSubProductByid(Integer.toString(detail.getListSubProducts().get(i).getId()));
//
//							//							Log.d("bp", detail.getListSubProducts().get(i).toString());
//
//							// Si esta incluido lo añado
//							if (bp.getIncluded() == 1) {
//								subProductsName.append(detail.getListSubProducts()
//										.get(i).getName());
//							}
//
//							if (i < detail.getListSubProducts().size() - 1) {
//
//								if(detail.getListSubProducts().get(i + 1) != null){
//
//									ProductSubproduct bpNext = new ProductSubproductCrud(db)
//											.findSubProductByid(Integer.toString(detail.getListSubProducts().get(i + 1).getId()));
//
//									if (bpNext != null && bpNext.getIncluded() == 1)
//
//										if (subProductsName.length() > 1)
//											subProductsName.append(",");
//								}
//
//							}
//						}
//
//
//
//					}
//					subProductsName.append(")");
//
//					if (subProductsName.length() == 2) {
//						subProductsName.delete(0, subProductsName.length());
//					}
//					detail.setTitle(detail.getTitle() + " "
//							+ subProductsName.toString());
//
//					detail.setPrice(detail.getPrice() + detail.getPriceExtra());
//					detail.setAmount(detail.getPrice() * detail.getQuantity());
//				}
//		}
//
////		if (detail.getOption() != null)
////			if (detail.getOption().length() > 0)
////				detail.setTitle(detail.getTitle() + " " + detail.getOption());
//
//		detail.setTitle(detail.getProductName() + " " + detail.getTitle());
//	}
}
