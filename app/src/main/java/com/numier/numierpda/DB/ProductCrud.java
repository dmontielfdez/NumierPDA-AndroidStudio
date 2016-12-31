package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Controllers.NumierApi;
import com.numier.numierpda.Models.Product;


public class ProductCrud {

    // Atributos
    private Database db;

    // Constructor
    public ProductCrud(Database db) {
        this.db = db;
    }

    // Inserción de datos
    public int insert(Product p) {
        int c = 0;
        try {


            ContentValues values = new ContentValues();
            values.put("ID_PRODUCT", p.getId());
            values.put("NAME", p.getName());
            values.put("ID_CATEGORY", p.getCategory());
            values.put("COLOR", p.getColor());
            values.put("OPTION1", p.getOption1());
            values.put("OPTION2", p.getOption2());
            values.put("OPTION3", p.getOption3());
            values.put("OPTION4", p.getOption4());
            values.put("OPTION5", p.getOption5());
            values.put("RATE1", p.getRate1());
            values.put("RATE2", p.getRate2());
            values.put("RATE3", p.getRate3());
            values.put("RATE4", p.getRate4());
            values.put("RATE1_OPTION", p.getRateOption1());
            values.put("RATE2_OPTION", p.getRateOption2());
            values.put("RATE3_OPTION", p.getRateOption3());
            values.put("RATE4_OPTION", p.getRateOption4());
            values.put("VALUE_RATE1_OPTION", p.getRateName1());
            values.put("VALUE_RATE2_OPTION", p.getRateName2());
            values.put("VALUE_RATE3_OPTION", p.getRateName3());
            values.put("VALUE_RATE4_OPTION", p.getRateName4());
            values.put("ASK_PRICE", p.getAskPrice());
            values.put("ASK_WEIGHT", p.getAskWeight());
            values.put("SUBPRODUCTS", p.getSubproducts());
            values.put("NUMBER_SUBPRODUCTS", p.getNumberSubproducts());
            values.put("DISCOUNT", p.getDiscount());
            values.put("ABSOLUT_PRICE", p.getAbsolutPrice());
            values.put("NUM_PLATO", p.getNumPlato());
            values.put("IS_MENU", p.getIsMenu());
            values.put("SELING", p.getSeling());
            values.put("DESCRI", p.getDescri());

            long id = db.getWritableDatabase().insert("PRODUCT", null, values);
            c = (int) id;

        } catch (SQLiteException sqlIo) {
            sqlIo.printStackTrace();
            return 0;
        }
        return c;
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();

        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM PRODUCT",
                null);

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {
                // products.add(new Product(c.getString(0), c.getString(1)));
            } while (c.moveToNext());
        }

        return products;
    }

    public List<Product> findByCategory(String idCategory) {
        List<Product> products = new ArrayList<Product>();

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT * FROM PRODUCT WHERE ID_CATEGORY='" + idCategory + "'", null);

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {

                Product product = new Product(
                        c.getString(NumierApi.__PRODUCT_ID),
                        c.getString(NumierApi.__PRODUCT_NAME),
                        c.getString(2),
                        c.getString(NumierApi.__PRODUCT_COLOR + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION1 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION2 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION3 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION4 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION5 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE1 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE2 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE3 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE4 + 1),
                        c.getInt(NumierApi.__PRODUCT_ASK_PRICE + 1),
                        c.getInt(NumierApi.__PRODUCT_ASK_WEIGHT + 1),
                        c.getInt(NumierApi.__PRODUCT_SUBPRODUCTS - 1),
                        c.getInt(NumierApi.__PRODUCT_NUMBER_SUBPRODUCTS + 3),
                        c.getDouble(NumierApi.__PRODUCT_DISCOUNT + 1),
                        c.getInt(NumierApi.__PRODUCT_ABSOLUT_PRICE + 1),
                        c.getInt(NumierApi.__PRODUCT_NUM_PLATO + 1),
                        c.getInt(NumierApi.__PRODUCT_IS_MENU + 1),
                        c.getInt(NumierApi.__PRODUCT_SELING),
                        c.getString(NumierApi.__PRODUCT_DESCRI));

                if (c.getInt(NumierApi.__PRODUCT_RATE1_OPTION + 1) == 0) {
                    product.setRateOption1(0);
                    product.setRateName1("");

                } else {
                    product.setRateOption1(c
                            .getInt(NumierApi.__PRODUCT_RATE1_OPTION + 1));
                    product.setRateName1(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE1 + 1));
                }
                if (c.getInt(NumierApi.__PRODUCT_RATE2_OPTION + 1) == 0) {
                    product.setRateOption2(0);
                    product.setRateName2("");

                } else {
                    product.setRateOption2(c
                            .getInt(NumierApi.__PRODUCT_RATE2_OPTION + 1));
                    product.setRateName2(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE2 + 1));
                }

                if (c.getInt(NumierApi.__PRODUCT_RATE3_OPTION + 1) == 0) {
                    product.setRateOption3(0);
                    product.setRateName3("");

                } else {
                    product.setRateOption3(c
                            .getInt(NumierApi.__PRODUCT_RATE3_OPTION + 1));
                    product.setRateName3(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE3 + 1));
                }

                if (c.getInt(NumierApi.__PRODUCT_RATE4_OPTION + 1) == 0) {
                    product.setRateOption4(0);
                    product.setRateName4("");

                } else {
                    product.setRateOption4(c
                            .getInt(NumierApi.__PRODUCT_RATE4_OPTION + 1));
                    product.setRateName4(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE4 + 1));
                }

                products.add(product);

            } while (c.moveToNext());
            c.close();
        }

        return products;
    }

    public Product findById(String idProduct) {
        Product product = null;

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT * FROM PRODUCT WHERE ID_PRODUCT=\"" + idProduct + "\"",
                null);

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros

            product = new Product(c.getString(NumierApi.__PRODUCT_ID),
                    c.getString(NumierApi.__PRODUCT_NAME), c.getString(2),
                    c.getString(NumierApi.__PRODUCT_COLOR + 1),
                    c.getString(NumierApi.__PRODUCT_OPTION1 + 1),
                    c.getString(NumierApi.__PRODUCT_OPTION2 + 1),
                    c.getString(NumierApi.__PRODUCT_OPTION3 + 1),
                    c.getString(NumierApi.__PRODUCT_OPTION4 + 1),
                    c.getString(NumierApi.__PRODUCT_OPTION5 + 1),
                    c.getDouble(NumierApi.__PRODUCT_RATE1 + 1),
                    c.getDouble(NumierApi.__PRODUCT_RATE2 + 1),
                    c.getDouble(NumierApi.__PRODUCT_RATE3 + 1),
                    c.getDouble(NumierApi.__PRODUCT_RATE4 + 1),
                    c.getInt(NumierApi.__PRODUCT_ASK_PRICE + 1),
                    c.getInt(NumierApi.__PRODUCT_ASK_WEIGHT + 1),
                    c.getInt(NumierApi.__PRODUCT_SUBPRODUCTS - 1),
                    c.getInt(NumierApi.__PRODUCT_NUMBER_SUBPRODUCTS + 3),
                    c.getDouble(NumierApi.__PRODUCT_DISCOUNT + 1),
                    c.getInt(NumierApi.__PRODUCT_ABSOLUT_PRICE + 1),
                    c.getInt(NumierApi.__PRODUCT_NUM_PLATO + 1),
                    c.getInt(NumierApi.__PRODUCT_IS_MENU + 1),
                    c.getInt(NumierApi.__PRODUCT_SELING),
                    c.getString(NumierApi.__PRODUCT_DESCRI));

            if (c.getInt(NumierApi.__PRODUCT_RATE1_OPTION + 1) == 0) {
                product.setRateOption1(0);
                product.setRateName1("");

            } else {
                product.setRateOption1(c
                        .getInt(NumierApi.__PRODUCT_RATE1_OPTION + 1));
                product.setRateName1(c
                        .getString(NumierApi.__PRODUCT_VALUE_RATE1 + 1));
            }
            if (c.getInt(NumierApi.__PRODUCT_RATE2_OPTION + 1) == 0) {
                product.setRateOption2(0);
                product.setRateName2("");

            } else {
                product.setRateOption2(c
                        .getInt(NumierApi.__PRODUCT_RATE2_OPTION + 1));
                product.setRateName2(c
                        .getString(NumierApi.__PRODUCT_VALUE_RATE2 + 1));
            }

            if (c.getInt(NumierApi.__PRODUCT_RATE3_OPTION + 1) == 0) {
                product.setRateOption3(0);
                product.setRateName3("");

            } else {
                product.setRateOption3(c
                        .getInt(NumierApi.__PRODUCT_RATE3_OPTION + 1));
                product.setRateName3(c
                        .getString(NumierApi.__PRODUCT_VALUE_RATE3 + 1));
            }

            if (c.getInt(NumierApi.__PRODUCT_RATE4_OPTION + 1) == 0) {
                product.setRateOption4(0);
                product.setRateName4("");

            } else {
                product.setRateOption4(c
                        .getInt(NumierApi.__PRODUCT_RATE4_OPTION + 1));
                product.setRateName4(c
                        .getString(NumierApi.__PRODUCT_VALUE_RATE4 + 1));
            }

            c.close();
        }

        return product;
    }

    @SuppressLint("NewApi")
    public List<Product> getTodosLosProductos() {
        List<Product> products = new ArrayList<Product>();

        Product product = null;

        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM PRODUCT", null);

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {

            //			Log.d("PRODUCT CRUD", "Nº DE COLUMNAS DE LA TABLA: "+c.getColumnCount());
            //			int i = 0, n = c.getColumnCount();
            //
            //			do {
            //				Log.d("PRODUCT CRUD", "Columna: "+i+" --- nombre: "+c.getColumnName(i)+"--- Tipo:"+c.getType(i));
            //				i++;
            //			}while (i<n);


            // Recorremos el cursor hasta que no haya más registros
            do {
                product = new Product(c.getString(NumierApi.__PRODUCT_ID),
                        c.getString(NumierApi.__PRODUCT_NAME), c.getString(2),
                        c.getString(NumierApi.__PRODUCT_COLOR + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION1 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION2 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION3 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION4 + 1),
                        c.getString(NumierApi.__PRODUCT_OPTION5 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE1 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE2 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE3 + 1),
                        c.getDouble(NumierApi.__PRODUCT_RATE4 + 1),
                        c.getInt(NumierApi.__PRODUCT_ASK_PRICE + 1),
                        c.getInt(NumierApi.__PRODUCT_ASK_WEIGHT + 1),
                        c.getInt(NumierApi.__PRODUCT_SUBPRODUCTS - 1),
                        c.getInt(NumierApi.__PRODUCT_NUMBER_SUBPRODUCTS + 3),
                        c.getDouble(NumierApi.__PRODUCT_DISCOUNT + 1),
                        c.getInt(NumierApi.__PRODUCT_ABSOLUT_PRICE + 1),
                        c.getInt(NumierApi.__PRODUCT_NUM_PLATO + 1),
                        c.getInt(NumierApi.__PRODUCT_IS_MENU + 1),
                        c.getInt(NumierApi.__PRODUCT_SELING),
                        c.getString(NumierApi.__PRODUCT_DESCRI));

                if (c.getInt(NumierApi.__PRODUCT_RATE1_OPTION + 1) == 0) {
                    product.setRateOption1(0);
                    product.setRateName1("");

                } else {
                    product.setRateOption1(c
                            .getInt(NumierApi.__PRODUCT_RATE1_OPTION + 1));
                    product.setRateName1(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE1 + 1));
                }
                if (c.getInt(NumierApi.__PRODUCT_RATE2_OPTION + 1) == 0) {
                    product.setRateOption2(0);
                    product.setRateName2("");

                } else {
                    product.setRateOption2(c
                            .getInt(NumierApi.__PRODUCT_RATE2_OPTION + 1));
                    product.setRateName2(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE2 + 1));
                }

                if (c.getInt(NumierApi.__PRODUCT_RATE3_OPTION + 1) == 0) {
                    product.setRateOption3(0);
                    product.setRateName3("");

                } else {
                    product.setRateOption3(c
                            .getInt(NumierApi.__PRODUCT_RATE3_OPTION + 1));
                    product.setRateName3(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE3 + 1));
                }

                if (c.getInt(NumierApi.__PRODUCT_RATE4_OPTION + 1) == 0) {
                    product.setRateOption4(0);
                    product.setRateName4("");

                } else {
                    product.setRateOption4(c
                            .getInt(NumierApi.__PRODUCT_RATE4_OPTION + 1));
                    product.setRateName4(c
                            .getString(NumierApi.__PRODUCT_VALUE_RATE4 + 1));
                }

                products.add(product);

            } while (c.moveToNext());
        }

        return products;
    }

    public String getNameProduct(String idProduct) {
        String name = "";

        Cursor c = db.getReadableDatabase().rawQuery("SELECT NAME FROM PRODUCT WHERE ID_PRODUCT ='" + idProduct + "'", null);

        try {
            // Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {

                // Recorremos el cursor hasta que no haya más registros
                do {
                    name = c.getString(0);
                } while (c.moveToNext());
            }

            c.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return name;
    }

    public double getPriceProduct(String idProduct, String rate) {
        Double price = 0.00;

        String rateSelect = getRate(idProduct, rate);

        Cursor c = db.getReadableDatabase().rawQuery("SELECT " + rateSelect + " FROM PRODUCT WHERE ID_PRODUCT ='" + idProduct + "'", null);

        try {
            // Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {

                // Recorremos el cursor hasta que no haya más registros
                do {
                    price = c.getDouble(0);
                } while (c.moveToNext());
            }

            c.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return price;
    }

    public String getRate(String idProduct, String rate) {

        String rateSelect = "";

        Cursor c = db.getReadableDatabase().rawQuery("SELECT VALUE_RATE1_OPTION,VALUE_RATE2_OPTION,VALUE_RATE3_OPTION,VALUE_RATE4_OPTION FROM PRODUCT WHERE (VALUE_RATE1_OPTION = '" + rate + "' OR VALUE_RATE2_OPTION = '" + rate + "' OR VALUE_RATE3_OPTION = '" + rate + "' OR VALUE_RATE4_OPTION = '" + rate + "') AND ID_PRODUCT ='" + idProduct + "'", null);

        try {
            // Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {

                // Recorremos el cursor hasta que no haya más registros
                do {
                    if (c.getString(0).equals(rate)) {
                        rateSelect = "RATE1";
                    } else if (c.getString(1).equals(rate)) {
                        rateSelect = "RATE2";
                    } else if (c.getString(2).equals(rate)) {
                        rateSelect = "RATE3";
                    } else if (c.getString(3).equals(rate)) {
                        rateSelect = "RATE4";
                    }
                } while (c.moveToNext());
            }

            c.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return rateSelect;
    }


}
