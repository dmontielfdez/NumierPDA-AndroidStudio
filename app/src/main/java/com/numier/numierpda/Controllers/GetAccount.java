package com.numier.numierpda.Controllers;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ModifierCrud;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.DB.SubproductCrud;
import com.numier.numierpda.Dialogs.DialogGetAccountList;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Header;
import com.numier.numierpda.Models.Modifier;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.Models.Subproduct;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.OkHttpTools;
import com.numier.numierpda.Tools.PreferencesTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.numier.numierpda.Controllers.NumierApi.__BILL_AMOUNT;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_DINNER;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_ID;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_LOCKED;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_NAME;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_NUMBER;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_PRINTED;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_PRINTERS;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_AMOUNT;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_MODIFIERS;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_NUM_ORDEN;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_OPTION;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_PRICE;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_PRODUCT_ID;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_QUANTITY;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_RATE;
import static com.numier.numierpda.Controllers.NumierApi.__DETAIL_SUBPRODUCTS;

public class GetAccount extends AsyncTask<Header, Void, String> {

    FragmentActivity activity;
    String key, url;
    ProgressDialog progressDialog;
    private int forceAccount;
    Header header;

    // Se le pasa si queremos forzar a recuperar la cuenta, 0 no 1 si
    public GetAccount(FragmentActivity activity, int forceAccount) {
        this.activity = activity;
        this.forceAccount = forceAccount;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(activity.getString(R.string.wait_moment));
        progressDialog.setMessage(activity.getString(R.string.getting_account));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Header... params) {
        if (OkHttpTools.ping(activity)) {
            header = params[0];

            String actual = "";
            if (Cash.getCashFragment().getHeader().getNumBill() != 0) {
                actual = "?actualId=" + Cash.getCashFragment().getHeader().getIdHeader();
            }

            return OkHttpTools.get("getAccount.htm?key=" + key + "&Id=" + header.getIdHeader() + actual, activity);
        } else {
            return "";
        }
    }

    @Override
    protected void onPostExecute(String resp) {
        super.onPostExecute(resp);
        progressDialog.dismiss();

        if (!resp.equals("")) {
            try {
                JSONObject json = new JSONObject(resp);

                if (json.getString("status").equals("OK")) {
                    parseJson(json.toString());
                } else if (json.getString("status").equals("error")) {
                    DialogsTools.launchCustomDialog(activity, json.getString("message"));
                } else {
                    DialogsTools.launchServerDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                DialogsTools.launchCustomDialog(activity, resp);
            }
        } else {
            DialogsTools.launchServerDialog(activity);
        }


    }

    public void parseJson(String json) {
        List<Detalle> listDetails = new ArrayList<>();
        String[] listPrinters;
        Database db = new Database(activity);

        JSONObject responseJSON = null;

        try {
            responseJSON = new JSONObject(json);
            String status = responseJSON.getString("status");

            if (status.equals("OK")) {
                JSONArray details = responseJSON.getJSONArray("details");

                // Si la cuenta no tiene detalles notificamos del error
                if (details.length() > 0) {

                    for (int i = 0; i < details.length(); i++) {

                        JSONArray detail = details.getJSONArray(i);

                        //	 Obtengo primero el string donde vienen los id de los SubProductos
                        String subProductsId = detail.getString(__DETAIL_SUBPRODUCTS);

                        List<Subproduct> subProducts = new ArrayList<Subproduct>();
                        if (subProductsId.length() > 0) {
                            String[] ids = subProductsId.split(",");

                            for (int k = 0; k < ids.length; k++) {
                                subProducts.add(new SubproductCrud(db).findById(Integer.parseInt(ids[k])));
                            }
                        }

                        //	 Obtengo el string donde vienen los modifiers
                        List<Modifier> mods = new ArrayList<Modifier>();
                        String modifiers = detail.getString(__DETAIL_MODIFIERS);

                        if (modifiers.length() > 0) {
                            String[] ids = modifiers.split(",");

                            for (int k = 0; k < ids.length; k++) {
                                mods.add(new ModifierCrud(db).findById(Integer.parseInt(ids[k])));
                            }
                        }

                        // Articulo no definido
                        if (detail.getString(__DETAIL_PRODUCT_ID).equals("ZZZZZ")) {

                            listDetails.add(new Detalle("ZZZZZ", "Artículo no definido",
                                    detail.getDouble(__DETAIL_QUANTITY),
                                    detail.getDouble(__DETAIL_PRICE),
                                    detail.getDouble(__DETAIL_AMOUNT),
                                    detail.getString(__DETAIL_RATE),
                                    detail.getString(__DETAIL_OPTION), i, 1, subProducts, ""));

                        } else if (detail.getString(__DETAIL_PRODUCT_ID).equals("DDDDD")) {

                            subProducts = new ArrayList<Subproduct>();

                            //línea nueva añadida para obtener un subproducto
                            subProducts.add(new Subproduct(detail.getInt(5), detail.getString(5), 0.0));

                            listDetails.add(new Detalle("DDDDD", "Descuento " + detail.getString(5) + "%",
                                    detail.getDouble(__DETAIL_QUANTITY),
                                    detail.getDouble(__DETAIL_PRICE),
                                    detail.getDouble(5),
                                    detail.getString(__DETAIL_RATE),
                                    detail.getString(__DETAIL_OPTION),
                                    i,
                                    1,
                                    subProducts,
                                    "")
                            );

                        } else {
                            Product p = new ProductCrud(db).findById(detail.getString(__DETAIL_PRODUCT_ID));

                            Log.d("ID", detail.getString(__DETAIL_PRODUCT_ID));

                            if (p == null) {

                            }

                            String numOrden = "";

                            if (!detail.isNull(__DETAIL_NUM_ORDEN)) {
                                if (!detail.getString(__DETAIL_NUM_ORDEN).equals("0")) {
                                    numOrden = detail.getString(__DETAIL_NUM_ORDEN) + "º";
                                }
                            }

                            Log.d("p", p.getName());

                            Detalle d = new Detalle(p.getId(),
                                    p.getName(),
                                    detail.getDouble(__DETAIL_QUANTITY),
                                    detail.getDouble(__DETAIL_PRICE),
                                    detail.getDouble(__DETAIL_AMOUNT),
                                    detail.getString(__DETAIL_RATE),
                                    detail.getString(__DETAIL_OPTION),
                                    i,
                                    1,
                                    subProducts,
                                    numOrden);

                            if (d.getQuantity() % 1 != 0) {
                                d.setDecimalQuantity(true);
                            }

                            d.setListModifier(mods);

                            String title = d.getProductName();

                            String nameRate = ConversionTools.getNameRate(activity, d);
                            String nameSubproducts = ConversionTools.getNameSubproducts(activity, d.getListSubProducts(), d.getIdProduct());

                            if (nameRate != "") {
                                title += " " + nameRate;
                            }

                            if (nameSubproducts != "") {
                                title += " " + nameSubproducts;
                            }

                            d.setTitle(title);

                            listDetails.add(d);
                        }


                    }

                    header.setNumBill(Integer.parseInt(responseJSON.getString("account")));
                    header.setRate(responseJSON.getInt("rate"));

                    JSONArray jsonPrinters = responseJSON.getJSONArray("printers");

                    String[] printers = null;
                    printers = new String[jsonPrinters.length()];
                    for (int j = 0; j < jsonPrinters.length(); j++) {
                        printers[j] = jsonPrinters.getString(j);
                    }

                    header.setDetails(listDetails);
                    header.setPrinters(printers);
                    db.close();

                    Cash.getCashFragment().setHeaderRefreshAll(header);
                } else {

                }

            } else {

                String message = responseJSON.getString("message");
                Log.e("message", message);

                header.setBillName(message);

            }

        } catch (Exception e) {
            e.printStackTrace();
            DialogsTools.launchCustomDialog(activity, json);
        }

    }
}


