package com.numier.numierpda.Controllers;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.numier.numierpda.Activities.Main;
import com.numier.numierpda.DB.CategoryCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.MenuCrud;
import com.numier.numierpda.DB.MenuDetCrud;
import com.numier.numierpda.DB.ModifierCrud;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.DB.ProductSubproductCrud;
import com.numier.numierpda.DB.SubproductCrud;
import com.numier.numierpda.DB.WorkerCrud;
import com.numier.numierpda.Models.Category;
import com.numier.numierpda.Models.Menu;
import com.numier.numierpda.Models.MenuDet;
import com.numier.numierpda.Models.Modifier;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.Models.ProductSubproduct;
import com.numier.numierpda.Models.Subproduct;
import com.numier.numierpda.Models.Worker;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.PreferencesTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NumierApi extends AsyncTask<Void, Void, Void> {

    // WORKERS
    public static final int __WORKER_ID = 0;
    public static final int __WORKER_PASS = 1;
    public static final int __WORKER_NAME = 2;

    // PRODUCTS
    public static final int __PRODUCT_ID = 0;
    public static final int __PRODUCT_NAME = 1;
    public static final int __PRODUCT_COLOR = 2;
    public static final int __PRODUCT_OPTION1 = 3;
    public static final int __PRODUCT_OPTION2 = 4;
    public static final int __PRODUCT_OPTION3 = 5;
    public static final int __PRODUCT_OPTION4 = 6;
    public static final int __PRODUCT_OPTION5 = 7;
    public static final int __PRODUCT_RATE1 = 8;
    public static final int __PRODUCT_RATE2 = 9;
    public static final int __PRODUCT_RATE3 = 10;
    public static final int __PRODUCT_RATE4 = 11;
    public static final int __PRODUCT_RATE1_OPTION = 12;
    public static final int __PRODUCT_RATE2_OPTION = 13;
    public static final int __PRODUCT_RATE3_OPTION = 14;
    public static final int __PRODUCT_RATE4_OPTION = 15;
    public static final int __PRODUCT_VALUE_RATE1 = 16;
    public static final int __PRODUCT_VALUE_RATE2 = 17;
    public static final int __PRODUCT_VALUE_RATE3 = 18;
    public static final int __PRODUCT_VALUE_RATE4 = 19;
    public static final int __PRODUCT_ASK_PRICE = 20;
    public static final int __PRODUCT_ASK_WEIGHT = 21;
    public static final int __PRODUCT_NUMBER_SUBPRODUCTS = 22;
    public static final int __PRODUCT_DISCOUNT = 23;
    public static final int __PRODUCT_SUBPRODUCTS = 24;
    public static final int __PRODUCT_ABSOLUT_PRICE = 25;
    public static final int __PRODUCT_NUM_PLATO = 26;
    public static final int __PRODUCT_IS_MENU = 27;
    public static final int __PRODUCT_MENU = 28;
    public static final int __PRODUCT_SELING = 29;
    public static final int __PRODUCT_DESCRI = 30;

    public static final int __SUBPRODUCT_ID = 0;
    public static final int __SUBPRODUCT_NAME = 1;
    public static final int __SUBBPRODUCT_PRICE = 2;

    public static final int __MODIFIER_ID = 0;
    public static final int __MODIFIER_NAME = 1;
    public static final int __MODIFIER_PRICE = 2;
    public static final int __MODIFIER_GRUPO = 3;

    public static final int __PRODUCT_MENU_TITLE_1 = 0;
    public static final int __PRODUCT_MENU_TITLE_2 = 1;
    public static final int __PRODUCT_MENU_TITLE_3 = 2;
    public static final int __PRODUCT_MENU_TITLE_4 = 3;
    public static final int __PRODUCT_MENU_TITLE_5 = 4;
    public static final int __PRODUCT_MENU_TITLE_6 = 5;

    public static final int __PRODUCT_SUBPRODUCT_ID_SUBPRODUCT = 0;
    public static final int __PRODUCT_SUBPRODUCT_INCLUDED = 1;
    public static final int __PRODUCT_SUBPRODUCT_PRICE = 2;
    public static final int __PRODUCT_SUBPRODUCT_ORDEN = 3;
    public static final int __PRODUCT_SUBPRODUCT_TYPE = 4;

    public ProgressDialog progress;

    String json;
    Activity activity;
    Database db;
    int numProducts;
    List<Category> listCategories;
    List<Subproduct> listSubProducts;
    List<ProductSubproduct> listProductsSubproducts;
    ArrayList<Modifier> listModifiers;
    String result;

    public NumierApi(Activity activity, String json) {
        this.activity = activity;
        this.json = json;
        db = new Database(activity);
        numProducts = 0;
        listCategories = new ArrayList<Category>();
        listSubProducts = new ArrayList<Subproduct>();
        listProductsSubproducts = new ArrayList<ProductSubproduct>();
        listModifiers = new ArrayList<Modifier>();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progress = new ProgressDialog(activity);

        progress.setTitle(activity.getString(R.string.analyzing));

        progress.setMessage(activity.getString(R.string.splash_info_analyzing));

        progress.setCancelable(false);
        progress.show();

        db.getWritableDatabase().execSQL("DELETE FROM WORKER");
        db.getWritableDatabase().execSQL("DELETE FROM PRODUCTSUBPRODUCT");
        db.getWritableDatabase().execSQL("DELETE FROM PRODUCT");
        db.getWritableDatabase().execSQL("DELETE FROM SUBPRODUCT");
        db.getWritableDatabase().execSQL("DELETE FROM CATEGORY");
        db.getWritableDatabase().execSQL("DELETE FROM MENU");
        db.getWritableDatabase().execSQL("DELETE FROM MENU_DET");
        db.getWritableDatabase().execSQL("DELETE FROM MODIFIER");

    }

    @Override
    protected Void doInBackground(Void... voids) {
        JSONObject responseJSON = null;
        try {
            responseJSON = new JSONObject(json);

            // PARAMETROS GENERALES

            String displayCash = "N";
            if (responseJSON.has("cashButton")) {
                displayCash = responseJSON.getString("cashButton");
            }
            PreferencesTools.savePreferences(activity, "displayCash", displayCash);

            String voidButton = "N";
            if (responseJSON.has("voidButton")) {
                voidButton = responseJSON.getString("voidButton");
            }
            PreferencesTools.savePreferences(activity, "voidButton", voidButton);

            String printerButton = "N";
            if (responseJSON.has("printerButton")) {
                printerButton = responseJSON.getString("printerButton");
            }
            PreferencesTools.savePreferences(activity, "printerButton", printerButton);

            String messages = "";
            if (responseJSON.has("messages")) {
                messages = responseJSON.getString("messages");
            }
            PreferencesTools.savePreferences(activity, "messages", messages);

            String workerFilter = "N";
            if (responseJSON.has("workerFilter")) {
                workerFilter = responseJSON.getString("workerFilter");
            }
            PreferencesTools.savePreferences(activity, "workerFilter", workerFilter);

            String supergrupos = "N";
            if (responseJSON.has("supergrupos")) {
                supergrupos = responseJSON.getString("supergrupos");
            }
            PreferencesTools.savePreferences(activity, "supergrupos", supergrupos);

            String numMenus = "3";
            if (responseJSON.has("numMenus")) {
                numMenus = responseJSON.getString("numMenus");
            }
            PreferencesTools.savePreferences(activity, "numMenus", numMenus);

            String forceDinners = "N";
            if (responseJSON.has("forceDinners")) {
                forceDinners = responseJSON.getString("forceDinners");
            }
            PreferencesTools.savePreferences(activity, "forceDinners", forceDinners);

            String dinnerProduct = "";
            if (responseJSON.has("dinnerProduct")) {
                dinnerProduct = responseJSON.getString("dinnerProduct");
            }
            PreferencesTools.savePreferences(activity, "dinnerProduct", dinnerProduct);

            String elegirMesa = "N";
            if (responseJSON.has("elegirMesa")) {
                elegirMesa = responseJSON.getString("elegirMesa");
            }
            PreferencesTools.savePreferences(activity, "elegirMesa", elegirMesa);

            String nombreWifi = "";
            if (responseJSON.has("nombreWifi")) {
                nombreWifi = responseJSON.getString("nombreWifi");
            }
            PreferencesTools.savePreferences(activity, "nombreWifi", nombreWifi);

            String permitirCambios = "S";
            if (responseJSON.has("permitirCambios")) {
                permitirCambios = responseJSON.getString("permitirCambios");
            }
            PreferencesTools.savePreferences(activity, "permitirCambios", permitirCambios);

            String serialTPV = "";
            if (responseJSON.has("serial")) {
                serialTPV = responseJSON.getString("serial");
            }
            PreferencesTools.savePreferences(activity, "serialTPV", serialTPV);

            String sinWifi = "";
            if (responseJSON.has("sinWifi")) {
                sinWifi = responseJSON.getString("sinWifi");
            }
            PreferencesTools.savePreferences(activity, "sinWifi", sinWifi);

            // WORKERS
            JSONArray workers = responseJSON.getJSONArray("workers");

            List<Worker> listWorkers = new ArrayList<Worker>();

            for (int i = 0; i < workers.length(); i++) {

                JSONArray jsonSubArray = workers.getJSONArray(i);

                listWorkers.add(new Worker(jsonSubArray.getString(__WORKER_ID),
                        jsonSubArray.getString(__WORKER_PASS),
                        jsonSubArray.getString(__WORKER_NAME)));
            }
            new WorkerCrud(db).insert(listWorkers);


            // SUBPRODUCTS
            JSONArray subProducts = responseJSON.getJSONArray("subproducts");


            for (int i = 0; i < subProducts.length(); i++) {
                JSONArray elementSubProducts = subProducts.getJSONArray(i);
                listSubProducts.add(new Subproduct(elementSubProducts
                        .getInt(__SUBPRODUCT_ID), elementSubProducts
                        .getString(__SUBPRODUCT_NAME), elementSubProducts
                        .getDouble(__SUBBPRODUCT_PRICE)));
            }


            // CATEGORIES
            JSONArray categories = responseJSON.getJSONArray("categories");

            for (int i = 0; i < categories.length(); i++) {

                JSONObject jsonSubObjet = categories.getJSONObject(i);

                String name = jsonSubObjet.getString("name");
                String code = jsonSubObjet.getString("code");

                JSONArray products = jsonSubObjet.getJSONArray("products");
                List<Product> listProducts = new ArrayList<Product>();

                for (int j = 0; j < products.length(); j++) {

                    // Relacion Producto-Subproducto
                    JSONArray jsonSubArray = products.getJSONArray(j);

                    int haveSubProducts = 0;

                    if (jsonSubArray.getJSONArray(__PRODUCT_SUBPRODUCTS).length() > 0) {

                        haveSubProducts = 1;

                        JSONArray subproductsParent = jsonSubArray.getJSONArray(__PRODUCT_SUBPRODUCTS);

                        for (int k = 0; k < subproductsParent.length(); k++) {
                            JSONArray subproducts = subproductsParent.getJSONArray(k);

                            listProductsSubproducts.add(new ProductSubproduct(
                                    jsonSubArray.getString(__PRODUCT_ID),
                                    subproducts.getInt(__PRODUCT_SUBPRODUCT_ID_SUBPRODUCT),
                                    subproducts.getInt(__PRODUCT_SUBPRODUCT_INCLUDED),
                                    subproducts.getDouble(__PRODUCT_SUBPRODUCT_PRICE),
                                    subproducts.getInt(__PRODUCT_SUBPRODUCT_ORDEN),
                                    subproducts.getString(__PRODUCT_SUBPRODUCT_TYPE)));

                        }
                    }


                    Product prod = new Product(
                            jsonSubArray.getString(__PRODUCT_ID),
                            jsonSubArray.getString(__PRODUCT_NAME),
                            code,
                            jsonSubArray.getString(__PRODUCT_COLOR),
                            jsonSubArray.getString(__PRODUCT_OPTION1),
                            jsonSubArray.getString(__PRODUCT_OPTION2),
                            jsonSubArray.getString(__PRODUCT_OPTION3),
                            jsonSubArray.getString(__PRODUCT_OPTION4),
                            jsonSubArray.getString(__PRODUCT_OPTION5),
                            jsonSubArray.getDouble(__PRODUCT_RATE1),
                            jsonSubArray.getDouble(__PRODUCT_RATE2),
                            jsonSubArray.getDouble(__PRODUCT_RATE3),
                            jsonSubArray.getDouble(__PRODUCT_RATE4),
                            jsonSubArray.getInt(__PRODUCT_ASK_PRICE),
                            jsonSubArray.getInt(__PRODUCT_ASK_WEIGHT),
                            haveSubProducts,
                            jsonSubArray.getInt(__PRODUCT_NUMBER_SUBPRODUCTS),
                            jsonSubArray.getDouble(__PRODUCT_DISCOUNT),
                            jsonSubArray.getInt(__PRODUCT_ABSOLUT_PRICE),
                            jsonSubArray.getInt(__PRODUCT_NUM_PLATO),
                            jsonSubArray.getInt(__PRODUCT_IS_MENU),
                            jsonSubArray.getInt(__PRODUCT_SELING),
                            jsonSubArray.getString(__PRODUCT_DESCRI)
                    );
                    // RATE 1
                    if (jsonSubArray.getInt(__PRODUCT_RATE1_OPTION) == 0) {
                        prod.setRateOption1(0);
                        prod.setRateName1("");

                    } else {
                        prod.setRateOption1(jsonSubArray.getInt(__PRODUCT_RATE1_OPTION));
                        prod.setRateName1(jsonSubArray.getString(__PRODUCT_VALUE_RATE1));
                    }

                    // RATE 2
                    if (jsonSubArray.getInt(__PRODUCT_RATE2_OPTION) == 0) {
                        prod.setRateOption2(0);
                        prod.setRateName2("");

                    } else {
                        prod.setRateOption2(jsonSubArray.getInt(__PRODUCT_RATE2_OPTION));
                        prod.setRateName2(jsonSubArray.getString(__PRODUCT_VALUE_RATE2));
                    }

                    // RATE 3
                    if (jsonSubArray.getInt(__PRODUCT_RATE3_OPTION) == 0) {
                        prod.setRateOption3(0);
                        prod.setRateName3("");

                    } else {
                        prod.setRateOption3(jsonSubArray.getInt(__PRODUCT_RATE3_OPTION));
                        prod.setRateName3(jsonSubArray.getString(__PRODUCT_VALUE_RATE3));
                    }

                    if (jsonSubArray.getInt(__PRODUCT_RATE4_OPTION) == 0) {
                        prod.setRateOption4(0);
                        prod.setRateName4("");

                    } else {
                        prod.setRateOption4(jsonSubArray.getInt(__PRODUCT_RATE4_OPTION));
                        prod.setRateName4(jsonSubArray.getString(__PRODUCT_VALUE_RATE4));
                    }

                    listProducts.add(prod);

                    // MENU

                    if (jsonSubArray.getInt(__PRODUCT_IS_MENU) != 0) {
                        JSONArray menuArray = jsonSubArray.getJSONArray(__PRODUCT_MENU);

                        if (menuArray.length() != 0) {
                            Menu menu = new Menu(0,
                                    prod.getId(),
                                    menuArray.getString(__PRODUCT_MENU_TITLE_1),
                                    menuArray.getString(__PRODUCT_MENU_TITLE_2),
                                    menuArray.getString(__PRODUCT_MENU_TITLE_3),
                                    menuArray.getString(__PRODUCT_MENU_TITLE_4),
                                    menuArray.getString(__PRODUCT_MENU_TITLE_5),
                                    menuArray.getString(__PRODUCT_MENU_TITLE_6));

                            new MenuCrud(db).insert(menu);

                            ArrayList<MenuDet> menuDets = new ArrayList<MenuDet>();

                            for (int k = 6; k < menuArray.length(); k++) {
                                JSONArray mDet = menuArray.getJSONArray(k);
                                menuDets.add(new MenuDet(0, prod.getId(), mDet.getString(0), mDet.getInt(1), 0));
                            }

                            new MenuDetCrud(db).insert(menuDets);
                        }
                    }

                    numProducts++;

                }

                listCategories.add(new Category(code, name, code, listProducts));

            }


            // MODIFICADORES

            if (responseJSON.has("modifiers")) {
                JSONArray modifiers = responseJSON.getJSONArray("modifiers");

                for (int i = 0; i < modifiers.length(); i++) {

                    JSONArray jsonSubArray = modifiers.getJSONArray(i);

                    listModifiers.add(new Modifier(jsonSubArray.getInt(__MODIFIER_ID),
                            jsonSubArray.getString(__MODIFIER_NAME),
                            jsonSubArray.getDouble(__MODIFIER_PRICE),
                            false,
                            jsonSubArray.getString(__MODIFIER_GRUPO).trim()));
                }
            }

        } catch (JSONException e) {
            Log.e("fallo", "FAILS" + e.toString());
            Log.e("fallo", responseJSON.toString());
            result = "FAILS" + e.toString();
        }
        progress.dismiss();
        result = "OK";
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progress.dismiss();

        if (result.equals("OK")) {
            new InsertDataDB().execute();
        } else {
            DialogsTools.launchCustomDialog(activity, result);
        }

    }

    public class InsertDataDB extends AsyncTask<Void, Integer, Void> {

        ProgressDialog p;
        SQLiteDatabase sqlDB;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(activity);
            p.setProgressStyle(progress.STYLE_HORIZONTAL);
            p.setMessage(activity.getString(R.string.inserting_products));
            p.setProgress(0);
            p.setMax(numProducts);
            p.show();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso = values[0].intValue();

            p.setProgress(progreso);
        }

        @Override
        protected Void doInBackground(Void... params) {

            sqlDB = db.getWritableDatabase();
            sqlDB.beginTransaction();

            // INSERTO CATEGORIAS Y PRODUCTOS
            new CategoryCrud(db).insert(listCategories);

            for (Category c : listCategories) {
                for (Product p : c.getProducts()) {
                    int idProduct = new ProductCrud(db).insert(p);
                    publishProgress(idProduct);
                }
            }

            //INSERTO SUBPRODUCTOS
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    p.setMessage(activity.getString(R.string.inserting_subproducts));
                    p.setProgress(0);
                    p.setMax(listSubProducts.size());
                }
            });


            for (Subproduct s : listSubProducts) {
                int idSubproduct = new SubproductCrud(db).insert(s);
                publishProgress(idSubproduct);
            }


            //INSERTO RELACION SUBPRODUCTOS
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    p.setMessage(activity.getString(R.string.inserting_products_subproducts));
                    p.setProgress(0);
                    p.setMax(listProductsSubproducts.size());
                }
            });


            for (ProductSubproduct ps : listProductsSubproducts) {
                int idProductSubproduct = new ProductSubproductCrud(db).insert(ps);
                publishProgress(idProductSubproduct);
            }

            // INSERTO MODIFICADORES
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    p.setMessage(activity.getString(R.string.inserting_modifiers));
                    p.setProgress(0);
                    p.setMax(listModifiers.size());
                }
            });

            for (Modifier m : listModifiers) {
                int idModifier = new ModifierCrud(db).insert(m);
                publishProgress(idModifier);
            }

            sqlDB.setTransactionSuccessful();
            sqlDB.endTransaction();

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("terminado", "terminado");
            p.dismiss();

            if (result.equals("OK")) {
                PreferencesTools.savePreferences(activity, "configured", "true");
                Intent intent = new Intent(activity, Main.class);
                activity.startActivity(intent);
                activity.finish();

            } else {
                DialogsTools.launchCustomDialog(activity, result);
            }


        }
    }
}

