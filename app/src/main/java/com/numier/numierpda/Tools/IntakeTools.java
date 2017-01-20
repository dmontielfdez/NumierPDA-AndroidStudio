package com.numier.numierpda.Tools;

import android.support.v4.app.FragmentActivity;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductSubproductCrud;
import com.numier.numierpda.DB.SubproductCrud;
import com.numier.numierpda.Dialogs.DialogAskPrice;
import com.numier.numierpda.Dialogs.DialogAskWeight;
import com.numier.numierpda.Dialogs.DialogAskMessage;
import com.numier.numierpda.Dialogs.DialogRates;
import com.numier.numierpda.Dialogs.DialogSubproducts;
import com.numier.numierpda.Fragments.TicketFragment;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.Models.ProductSubproduct;
import com.numier.numierpda.Models.Subproduct;

import java.util.ArrayList;
import java.util.List;

public class IntakeTools {

    public static FragmentActivity activity;
    public static Detalle detail;

    public static void generateIntake(FragmentActivity activity, Product product, int quantity, String numOrden) {

        IntakeTools.activity = activity;

        detail = new Detalle();

        detail.setIdProduct(product.getId());
        detail.setProductName(product.getName());
        detail.setQuantity(quantity);
        detail.setPrinted(0);


        if (product.getNumPlato() == 0) {
            if (numOrden.equals("Nº Ord.")) {
                detail.setNumOrden("");
            } else {
                detail.setNumOrden(numOrden);
            }
        } else {
            switch (product.getNumPlato()) {
                case 1:
                    detail.setNumOrden("1º");
                    break;
                case 2:
                    detail.setNumOrden("2º");
                    break;
                case 3:
                    detail.setNumOrden("3º");
                    break;
                default:
                    detail.setNumOrden("");
                    break;
            }
        }

        if(product.getId().equals("DDDDD")){
            detail.setDiscount(product.getDiscount());
        }

        launchDialogSubProduct(product, detail);

    }

    public static void launchDialogSubProduct(Product product, Detalle detail) {

        // Compruebo en primer lugar si tiene subprodutos
        if (product.haveSubproducts() == 1) {

            Database db = new Database(activity);

            List<ProductSubproduct> listProductSubProducts = new ProductSubproductCrud(db).findSubproduct(product.getId());
            db.close();

            if (listProductSubProducts.size() > 0) {

                List<Subproduct> subProducts = new ArrayList<Subproduct>();
                List<Subproduct> subProductsRequired = new ArrayList<Subproduct>();

                for (int i = 0; i < listProductSubProducts.size(); i++) {

                    if (listProductSubProducts.get(i).getOpcional() == 1) {
                        Subproduct bp = new SubproductCrud(db).findById(listProductSubProducts.get(i).getIdSubproduct());

                        if (bp != null) {
                            bp.setPrice(listProductSubProducts.get(i).getPrice());
                            subProducts.add(bp);
                        }


                    } else {
                        Subproduct bp = new SubproductCrud(db).findById(listProductSubProducts.get(i).getIdSubproduct());

                        if (bp != null) {
                            subProductsRequired.add(bp);
                            detail.setPriceExtraSubproduct(listProductSubProducts.get(i).getPrice());
                        }

                    }
                }

                if (subProducts.size() > 0 && product.getNumberSubproducts() != 0) {
                    new DialogSubproducts(subProducts, product, detail, subProductsRequired).show(activity.getSupportFragmentManager(), "dialogSubproducts");
                } else {
                    detail.setListSubProducts((subProductsRequired));

                    launchDialogPrice(product, detail);
                }
            } else {
                launchDialogPrice(product, detail);
            }
        } else {
            launchDialogPrice(product, detail);
        }

    }

    public static void launchDialogPrice(Product product, Detalle detail) {

        if (product.getAskPrice() == 1) {
            new DialogAskPrice(product, detail).show(activity.getSupportFragmentManager(), "dialogAskPrice");
        } else {
            launchDialogWeight(product, detail);
        }
    }

    public static void launchDialogWeight(Product product, Detalle detail) {

        if (product.getAskWeight() == 1) {
            new DialogAskWeight(product, detail).show(activity.getSupportFragmentManager(), "dialogAskWeight");
        } else {
            launchDialogRate(product, detail);
        }
    }

    public static void launchDialogRate(Product product, Detalle detail) {

        if (product.getAskPrice() != 1) {
            List<String> rates = new ArrayList<String>();

            if (product.getRateOption1() == 1) {
                rates.add(product.getRateName1() + " - " + ConversionTools.getFormatPrice(product.getRate1(), false));
            }
            if (product.getRateOption2() == 1) {
                rates.add(product.getRateName2() + " - " + ConversionTools.getFormatPrice(product.getRate2(), false));
            }
            if (product.getRateOption3() == 1) {
                rates.add(product.getRateName3() + " - " + ConversionTools.getFormatPrice(product.getRate3(), false));
            }
            if (product.getRateOption4() == 1) {
                rates.add(product.getRateName4() + " - " + ConversionTools.getFormatPrice(product.getRate4(), false));
            }

            // Si he encontrado alguna tarifa por la que preguntar sacaré el diálogo
            if (rates.size() > 0) {
                new DialogRates(rates.toArray(new String[rates.size()]), product, detail).show(activity.getSupportFragmentManager(), "dialogRates");
            } else {
                // Si no encuentro ninguna tarifa por la que preguntar el valor tarifa lo envio blanco
                detail.setRate("");

                // Como precio buscaré en preferencias la tarifa por defecto y esa enviaré
                int ra = Integer.parseInt(PreferencesTools.getValueOfPreferences(activity, "rate"));

                switch (ra) {
                    case 1:
                        detail.setPrice(product.getRate1());
                        break;
                    case 2:
                        detail.setPrice(product.getRate2());
                        break;
                    case 3:
                        detail.setPrice(product.getRate3());
                        break;
                    case 4:
                        detail.setPrice(product.getRate4());
                        break;
                }
                launchDialogMessage(product, detail);
            }
        } else {
            launchDialogMessage(product, detail);
        }
    }

    public static void launchDialogMessage(Product product, Detalle detail) {

        List<String> messages = new ArrayList<String>();

        if (product.getOption1().length() > 0) {
            messages.add(product.getOption1());
        }
        if (product.getOption2().length() > 0) {
            messages.add(product.getOption2());
        }
        if (product.getOption3().length() > 0) {
            messages.add(product.getOption3());
        }
        if (product.getOption4().length() > 0) {
            messages.add(product.getOption4());
        }
        if (product.getOption5().length() > 0) {
            messages.add(product.getOption5());
        }

        if (messages.size() > 0) {
            DialogAskMessage fragment1 = new DialogAskMessage(messages.toArray(new String[messages.size()]), detail);
            fragment1.show(activity.getSupportFragmentManager(), "");
        } else {
            detail.setOption("");

            addDetailToList(detail);
        }

    }

    public static void addDetailToList(Detalle detail){
        // Creo el detalle y lo añado a la lista

        String title = detail.getProductName();

        String nameRate = ConversionTools.getNameRate(activity, detail);
        String nameSubproducts = ConversionTools.getNameSubproducts(activity, detail.getListSubProducts(), detail.getIdProduct());

        if (nameRate != "") {
            title += " " + nameRate;
        }

        if (nameSubproducts != "") {
            title += " " + nameSubproducts;
        }

//        if (detail.getOption() != null && !detail.getOption().equals("")) {
//            title += " - " + detail.getOption();
//        }


        detail.setTitle(title);

        detail.setPrice(detail.getPrice() + detail.getPriceExtraSubproduct());
        detail.setAmount(detail.getQuantity() * (detail.getPrice()));

        TicketFragment ticketFragment = Cash.getTicketFragment();
        if (ticketFragment != null) {
            ticketFragment.addAndUpdate(detail);
        }
    }

}


