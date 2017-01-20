package com.numier.numierpda.Tools;


import android.app.Activity;
import android.util.Log;

import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductSubproductCrud;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.Models.ProductSubproduct;
import com.numier.numierpda.Models.Subproduct;

import java.text.DecimalFormat;
import java.util.List;

public class ConversionTools {

    public static String getFormatPrice(double price, boolean showLabel) {

        DecimalFormat df = new DecimalFormat("0.00");

        String formatPrice;

        if (price < -1.00) {
            if (showLabel)
                formatPrice = "Precio: " + df.format(price) + " €";

            else
                formatPrice = df.format(price) + "€";
        } else if (price > -1.00 && price < 0) {

            if (showLabel)
                formatPrice = "Precio: " + price + " €";

            else
                formatPrice = df.format(price) + "€";

        } else if (price < 1.00) {

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

    public static String getFormatUds(double quantity, boolean decimal) {

        DecimalFormat df = new DecimalFormat("#.000");

        String formatQuantity;


        if (decimal) {
            if (quantity < 1.00) {
                formatQuantity = "0" + df.format(quantity) + "x";
            } else {
                formatQuantity = df.format(quantity) + "x";
            }
        } else {
            int quantityInteger = (int) quantity;
            if (quantityInteger > 1) {
                formatQuantity = Integer.toString(quantityInteger) + "x";
            } else if (quantityInteger < 1) {
                formatQuantity = "0" + df.format(quantity) + "x";
            } else {
                formatQuantity = Integer.toString(quantityInteger) + "x";
            }

        }

        return formatQuantity;
    }

    public static String getFormatKg(double quantity, boolean decimal) {

        DecimalFormat df = new DecimalFormat("#.000");

        String formatQuantity;

        if (decimal) {
            if (quantity < 1.00) {
                formatQuantity = "0" + df.format(quantity) + " Kg";
            } else {
                formatQuantity = df.format(quantity) + " Kg";
            }
        } else {
            int quantityInteger = (int) quantity;
            if (quantityInteger > 1) {
                formatQuantity = Integer.toString(quantityInteger) + " Kg";
            } else if (quantityInteger < 1) {
                formatQuantity = "0" + df.format(quantity) + " Kg";
            } else {
                formatQuantity = Integer.toString(quantityInteger) + " Kg";
            }

        }

        return formatQuantity;
    }

    public static String getNameRate(Activity activity, Detalle detail) {
        String nameRate = "";
        if (detail.getRate() != null) {
            if (detail.getRate().length() > 0) {
                nameRate = detail.getRate();
            }
        }
        return nameRate;
    }

    public static String getNameSubproducts(Activity activity, List<Subproduct> listSubproducts, String idProduct) {
        Database db = new Database(activity);
        StringBuilder subProductsName = new StringBuilder("");
        // Compruebo si tiene subproductos para generar el titulo
        if (listSubproducts != null) {

            if (listSubproducts.size() > 0) {

                for (int i = 0; i < listSubproducts.size(); i++) {

                    if (listSubproducts.get(i) != null) {
                        ProductSubproduct bp = new ProductSubproductCrud(db).findSubProductByid(Integer.toString(listSubproducts.get(i).getId()), idProduct);

                        // Si esta incluido lo añado
                        if (bp.getOpcional() == 1) {
                            subProductsName.append(listSubproducts.get(i).getName());
                        }

                        if (i < listSubproducts.size() - 1) {

                            if (listSubproducts.get(i + 1) != null) {

                                ProductSubproduct bpNext = new ProductSubproductCrud(db).findSubProductByid(Integer.toString(listSubproducts.get(i + 1).getId()), idProduct);

                                if (bpNext != null && bpNext.getOpcional() == 1) {
                                    if (subProductsName.length() > 1) {
                                        subProductsName.append(", ");
                                    }
                                }
                            }
                        }
                    }
                }
                subProductsName.append("");
            }
        }

        return subProductsName.toString();
    }
}
