package com.numier.numierpda.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Activities.Menu;
import com.numier.numierpda.Activities.Seling;
import com.numier.numierpda.Activities.Welcome;
import com.numier.numierpda.Adapters.AdapterCategoryGridCash;
import com.numier.numierpda.Adapters.AdapterListTicketFragment;
import com.numier.numierpda.Adapters.AdapterProductGridCash;
import com.numier.numierpda.Controllers.GetAccountList;
import com.numier.numierpda.DB.CategoryCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Dialogs.DialogAlertExit;
import com.numier.numierpda.Dialogs.DialogAlertGetBill;
import com.numier.numierpda.Dialogs.DialogDeleteDiscount;
import com.numier.numierpda.Dialogs.DialogDinners;
import com.numier.numierpda.Dialogs.DialogFilterSearch;
import com.numier.numierpda.Dialogs.DialogDiscount;
import com.numier.numierpda.Dialogs.DialogModifiers;
import com.numier.numierpda.Dialogs.DialogOptionItem;
import com.numier.numierpda.Dialogs.DialogProductNotDefined;
import com.numier.numierpda.Models.Category;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Header;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.IntakeTools;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.ArrayList;
import java.util.List;


public class CashFragment extends Fragment implements OnClickListener, OnItemClickListener, OnCheckedChangeListener {

    static Activity activity;
    private Database db;

    int idHeader, numBill;
    String nameBill;

    int quantity;
    static boolean inCategory;
    boolean isChanged;
    static List<Category> categories;
    List<Product> products;
    Header header;
    Vibrator vi;
    double cambio;

    private static ImageButton buttonBack;
    private ImageButton buttonSeparator;
    private ImageButton getBill;
    private ImageButton saveBill;
    private ImageButton pointBill;
    private ImageButton buttonOptions;
    private ImageButton printerBill;
    private ImageButton buttonSearch;
    private ImageButton buttonModifier;
    public Button buttonMenus;
    private static ToggleButton buttonX2;
    private static ToggleButton buttonX3;
    private static GridView gridView;
    private TextView billNumber;
    public static TextView lastItem;
    public static TextView numDinners;
    private ProgressDialog progress;
    private LinearLayout buttonDinners;

    // Supergrupos
    public LinearLayout supergroups;
    public Button super1;
    public Button super2;
    public Button super3;
    public Button super4;
    public static int superGrupoSelect = 1;
    static String displaySupergroups;

    private long mLastClickTime = 0;


    public CashFragment(int idHeader, int numBill, String nameBill) {
        this.idHeader = idHeader;
        this.numBill = numBill;
        this.nameBill = nameBill;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash, container, false);

        activity = getActivity();
        quantity = 1;
        header = new Header();
        isChanged = false;

        db = new Database(activity);

        this.buttonBack = (ImageButton) view.findViewById(R.id.exitCash);
        this.buttonSearch = (ImageButton) view.findViewById(R.id.buttonSearch);
        this.buttonModifier = (ImageButton) view.findViewById(R.id.buttonModifier);
        this.buttonMenus = (Button) view.findViewById(R.id.buttonMenus);
        this.buttonOptions = (ImageButton) view.findViewById(R.id.buttonOptions);
        this.buttonX2 = (ToggleButton) view.findViewById(R.id.buttonX2);
        this.buttonX3 = (ToggleButton) view.findViewById(R.id.buttonX3);
        this.buttonSeparator = (ImageButton) view.findViewById(R.id.lineSeparator);
        this.saveBill = (ImageButton) view.findViewById(R.id.saveBill);
        this.pointBill = (ImageButton) view.findViewById(R.id.pointBill);
        this.buttonDinners = (LinearLayout) view.findViewById(R.id.buttonDinners);
        this.numDinners = (TextView) view.findViewById(R.id.numDinners);
        this.printerBill = (ImageButton) view.findViewById(R.id.printer);
        this.getBill = (ImageButton) view.findViewById(R.id.getBill);
        this.gridView = (GridView) view.findViewById(R.id.gridviewItems);
        this.billNumber = (TextView) view.findViewById(R.id.billNumber);
        this.lastItem = (TextView) view.findViewById(R.id.lastDetailData);

        // Oculto el boton de imprimir si viene en el pdaserver.ini
        String displayPrinter = PreferencesTools.getValueOfPreferences(getActivity(), "printerButton");

        if (displayPrinter.equals("N")) {
            printerBill.setVisibility(View.INVISIBLE);
        }

        // Pongo las columnas que esta definido en los settings
        String numberColumns = PreferencesTools.getValueOfPreferences(getActivity(), "numberColumns");
        if (!numberColumns.equals("")) {
            gridView.setNumColumns(Integer.parseInt(numberColumns));
        }

        // Supergrupos

        this.supergroups = (LinearLayout) view.findViewById(R.id.supergroups);
        this.super1 = (Button) view.findViewById(R.id.super1);
        this.super2 = (Button) view.findViewById(R.id.super2);
        this.super3 = (Button) view.findViewById(R.id.super3);
        this.super4 = (Button) view.findViewById(R.id.super4);

        displaySupergroups = PreferencesTools.getValueOfPreferences(getActivity(), "supergrupos");

        if (displaySupergroups.equals("S")) {
            super1.setBackgroundResource(R.drawable.style_buttons_bill_pressed);
            super1.setTextColor(Color.parseColor("#eeeeee"));
            supergroups.setVisibility(View.VISIBLE);

            this.super1.setOnClickListener(this);
            this.super2.setOnClickListener(this);
            this.super3.setOnClickListener(this);
            this.super4.setOnClickListener(this);
        }

        // Asigno los eventos
        this.buttonBack.setOnClickListener(this);
        this.buttonSeparator.setOnClickListener(this);
        this.buttonMenus.setOnClickListener(this);
        this.getBill.setOnClickListener(this);
        this.gridView.setOnItemClickListener(this);
        this.buttonX2.setOnCheckedChangeListener(this);
        this.buttonX3.setOnCheckedChangeListener(this);
        this.saveBill.setOnClickListener(this);
        this.printerBill.setOnClickListener(this);
        this.buttonDinners.setOnClickListener(this);
        this.buttonOptions.setOnClickListener(this);
        this.buttonSearch.setOnClickListener(this);
        this.buttonModifier.setOnClickListener(this);
        this.pointBill.setOnClickListener(this);
        this.lastItem.setOnClickListener(this);

        vi = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // Si esta la opcion de elegir mesa cambio el icono de apuntar por uno de mover
        String elegirMesa = PreferencesTools.getValueOfPreferences(getActivity(), "elegirMesa");
        if (elegirMesa.equals("S")) {
            saveBill.setImageResource(R.mipmap.ic_move);
        }

        // Si el idHeader es diferente de 0 la recuperar ya que viene de elegir mesa
        if (idHeader != 0) {
            progress = ProgressDialog.show(getActivity(), "Espere", "Recuperando los datos de la cuenta");
//            new GetAccount(0, nameBill).execute(new Header(idHeader));
        } else {
            if (numBill != 0) {
                this.billNumber.setText(numBill);
                this.header.setNumBill(numBill);
            }
        }

        // Obtengo las categorias para mostrarlas
        getCategories(superGrupoSelect);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.exitCash:

                vi.vibrate(50);

                if (inCategory) {
                    if (header.getIdHeader() == 0) {
                        if (header.getDetails().size() > 0) {
                            new DialogAlertExit().show(getActivity().getSupportFragmentManager(), "dialog");
                        } else {
                            Intent i = new Intent(getActivity(), Welcome.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    } else {
                        if (!isChanged) {
                            Intent i = new Intent(getActivity(), Welcome.class);
                            startActivity(i);
                            getActivity().finish();
                        } else
                            new DialogAlertExit().show(getActivity().getSupportFragmentManager(), "dialog");

                    }

                } else
                    this.getCategories(superGrupoSelect);

                break;

            case R.id.buttonOptions:
                vi.vibrate(50);
                showOptions();
                break;

            case R.id.buttonSearch:
                vi.vibrate(50);
                launchingFilterDialog();
                break;

            case R.id.lastDetailData:

                vi.vibrate(50);

                if (this.header.getDetails().size() > 0) {
                    if (this.header.getDetails().get(0).getIdProduct().equals("DDDDD") == true) {
                        new DialogDeleteDiscount(this.header.getDetails(), 0).show(getActivity().getSupportFragmentManager(), "dialog");
                    } else {
                        DialogOptionItem dialog = new DialogOptionItem(this.header.getDetails(), 0);

                        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                    }
                }

                break;

            case R.id.buttonMenus:

                vi.vibrate(50);

                int num = Integer.parseInt(PreferencesTools.getValueOfPreferences(activity, "numMenus"));

                String numSelect = buttonMenus.getText().toString();
                numSelect = numSelect.substring(0, numSelect.indexOf("º"));

                int numS = 0;
                try {
                    numS = Integer.parseInt(numSelect);
                } catch (Exception e) {

                }

                if (numS < num) {
                    buttonMenus.setText((numS + 1) + "º");
                    buttonMenus.setTextSize(getResources().getDimension(R.dimen.textMenuGrande));
                    buttonMenus.setBackgroundResource(R.drawable.selector_button_menus_selected);
                } else {
                    buttonMenus.setText("Nº Ord.");
                    buttonMenus.setTextSize(getResources().getDimension(R.dimen.textMenuPeque));
                    buttonMenus.setBackgroundResource(R.drawable.selector_button_menus);
                }

                break;

            case R.id.getBill:

                vi.vibrate(50);

                if (getHeader().getIdHeader() == 0) {

                    if (header.getDetails().size() > 0) {
                        new DialogAlertGetBill().show(getActivity().getSupportFragmentManager(), "dial");

                    } else {
                        launchGetBill(1);
                    }

                } else {

                    if(header.getDetails().isEmpty()){
                        DialogsTools.launchCustomDialog(getActivity(), getString(R.string.fail_header_without_details));
                        break;
                    }

                    // Aqui siempre tendre que pasar el id para desbloquear
                    if (isChanged) {
                        new DialogAlertGetBill().show(getActivity().getSupportFragmentManager(), "dial");
                    } else {
                        launchGetBill(1);
                    }

                }
                break;

            case R.id.lineSeparator:

                vi.vibrate(50);

                Product separator = new Product("*****", "-------------------------------------");

                IntakeTools.generateIntake(getActivity(), separator, 1, "");

                break;

            case R.id.buttonDinners:

                // Previene el doble click
                if (SystemClock.elapsedRealtime() - mLastClickTime < 200) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                vi.vibrate(50);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogDinners frag = new DialogDinners(Integer.parseInt(numDinners.getText().toString()));
                frag.show(ft, "dialogDinners");
                break;

            case R.id.buttonModifier:

                // Previene el doble click
                if (SystemClock.elapsedRealtime() - mLastClickTime < 200) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                vi.vibrate(50);

                if (this.header.getDetails().size() != 0) {
                    if (!this.header.getDetails().get(0).getIdProduct().equals("*****")) {
                        if (this.header.getDetails().get(0).getIdProduct() != "ZZZZZ") {
                            new DialogModifiers(getActivity(), this.header.getDetails(), 0).show(getActivity().getSupportFragmentManager(), "dialogModifier");
                        }

                    } else {
                        Toast.makeText(getActivity(), R.string.info_select_product_before_modifer, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.info_select_product_before_modifer, Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.super1:
                this.getCategories(1);
                superGrupoSelect = 1;
                changeBackgroundsSuper();
                break;

            case R.id.super2:
                this.getCategories(2);
                superGrupoSelect = 2;
                changeBackgroundsSuper();
                break;

            case R.id.super3:
                this.getCategories(3);
                superGrupoSelect = 3;
                changeBackgroundsSuper();
                break;

            case R.id.super4:
                this.getCategories(4);
                superGrupoSelect = 4;
                changeBackgroundsSuper();
                break;
        }
    }

    public void launchGetBill(int option) {
        new GetAccountList(getActivity(), option).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Previene el doble click
        if (SystemClock.elapsedRealtime() - mLastClickTime < 200) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        vi.vibrate(50);

        if (inCategory) {
            getProducts(categories.get(position).getId());
        } else {

            Product p = products.get(position);

            if (products.get(position).getIsMenu() == 1) {

                List<String> rates = new ArrayList<String>();

                // Compruebo si alguna tarifa debe de ser preguntada, en caso de serlo lanzaré un diálogo
                if (p.getRateOption1() == 1) {
                    rates.add(p.getRateName1() + " - " + ConversionTools.getFormatPrice(p.getRate1(), false));
                }
                if (p.getRateOption2() == 1) {
                    rates.add(p.getRateName2() + " - " + ConversionTools.getFormatPrice(p.getRate2(), false));
                }
                if (p.getRateOption3() == 1) {
                    rates.add(p.getRateName3() + " - " + ConversionTools.getFormatPrice(p.getRate3(), false));
                }
                if (p.getRateOption4() == 1) {
                    rates.add(p.getRateName4() + " - " + ConversionTools.getFormatPrice(p.getRate4(), false));
                }

                // Si he encontrado alguna tarifa por la que preguntar sacaré el diálogo
                if (rates.size() > 0) {
                    getRateofMenu(rates.toArray(new String[rates.size()]), position);
                } else {
                    Intent intent = new Intent(getActivity(), Menu.class);
                    intent.putExtra("idProduct", products.get(position).getId());
                    intent.putExtra("haveRate", false);
                    startActivity(intent);
                }


            } else if (products.get(position).getSeling() == 1) {
                Intent intent = new Intent(getActivity(), Seling.class);
                intent.putExtra("idProduct", products.get(position).getId());
                intent.putExtra("haveRate", false);
                startActivity(intent);
            } else {
                IntakeTools.generateIntake(getActivity(), products.get(position), quantity, buttonMenus.getText().toString());
            }

        }
    }

    public String getRateofMenu(final String[] rates, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_rate));

        builder.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, rates),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String rate = rates[which];
                        rate = rate.substring(0, rate.lastIndexOf("-") - 1);

                        Intent intent = new Intent(getActivity(), Menu.class);
                        intent.putExtra("idProduct", products.get(position).getId());
                        intent.putExtra("rate", rate);
                        intent.putExtra("haveRate", true);
                        startActivity(intent);

                    }
                });

        builder.show();

        return "";
    }

    public void setDetailsToHeader(List<Detalle> details, Detalle lastDetail) {

        // Hilo que cambia el color de fondo del last item
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {
                        lastItem.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_last_item_cash_pressed));
                    }
                });

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {
                        lastItem.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_last_item_cash));
                    }
                });
            }
        }).start();

        // Guardo los detalles en el header
        header.setDetails(details);

        // Devuelve el precio sumado de un conjunto de Details
        double amount = calculatePrice(details);

        // Fijo el precio que acabo de obtener al HEADER ()
        header.setAmount(amount);

        // Asigno a la vista los datos
        if (details.size() == 0) {
            Cash.getTicketFragment().setAmount(ConversionTools.getFormatPrice(0, false));
            billNumber.setText("");
            lastItem.setText("");
        } else {
            Cash.getTicketFragment().setAmount(ConversionTools.getFormatPrice(header.getAmount(), false));

            if (header.getBillName() != null && !header.getBillName().equalsIgnoreCase("")) {
                if (header.getBillName().length() < 10) {
                    billNumber.setText(header.getBillName());
                } else {
                    billNumber.setText(header.getBillName().substring(0, 10));
                }
            } else {
                billNumber.setText(Integer.toString(header.getNumBill()));
            }

            if (lastDetail != null) {
                String numOrden = "";
                if (!lastDetail.getNumOrden().equals("")) {
                    numOrden = lastDetail.getNumOrden() + " - ";
                }

                if (lastDetail.getIdProduct().equals("*****")) {
                    lastItem.setText("-----------");
                } else if (lastDetail.getIdProduct().equals("ZZZZZ")) {
                    lastItem.setText(ConversionTools.getFormatUds(lastDetail.getQuantity(), false)
                            + " "
                            + getString(R.string.product_not_defined)
                            + " "
                            + ConversionTools.getFormatPrice(lastDetail.getPrice(), false)
                            + "\n"
                            + getString(R.string.total)
                            + " " + ConversionTools.getFormatPrice(lastDetail.getAmount(), false));
                } else if (lastDetail.getIdProduct().equals("DDDDD")) {
                    lastItem.setText(lastDetail.getProductName()
                            + "\n"
                            + getString(R.string.total)
                            + " " + ConversionTools.getFormatPrice(lastDetail.getAmount(), false));
                } else {
                    String title = lastDetail.getTitle();
                    if (lastDetail.getProductName().length() > 30) {
                        title = lastDetail.getProductName().substring(0, 25) + "...";
                    }

                    String uds = "";
                    Product p = new ProductCrud(db).findById(lastDetail.getIdProduct());
                    if (p.getAskWeight() == 0) {
                        uds = ConversionTools.getFormatUds(lastDetail.getQuantity(), lastDetail.isDecimalQuantity());
                    } else {
                        uds = ConversionTools.getFormatKg(lastDetail.getQuantity(), true);
                    }

                    this.lastItem.setText(uds
                            + " " + numOrden
                            + title
                            + " "
                            + ConversionTools.getFormatPrice(lastDetail.getPrice(), false)
                            + "\n"
                            + getString(R.string.total)
                            + " " + ConversionTools.getFormatPrice(lastDetail.getAmount(), false));
                }
            }
        }
        isChanged = true;
    }

    public double calculatePrice(List<Detalle> details) {

        if (quantity > 1) {
            this.quantity = 1;
            this.buttonX2.setChecked(false);
            this.buttonX3.setChecked(false);
        }

        boolean hayDescuento = false;
        int posDescuento = 0;

        int index = 0;
        for (Detalle detail : details) {
            if (detail.getIdProduct().equals("DDDDD")) {
                hayDescuento = true;
                posDescuento = index;
            }
            index++;
        }

        double amount = 0.0;

        for (Detalle detail : details) {
            amount = amount + detail.getAmount();
        }

        double discountAmount = 0;
        if (hayDescuento) {
            amount = amount + Math.abs(getHeader().getDetails().get(posDescuento).getAmount());
            discountAmount = (amount * getHeader().getDetails().get(posDescuento).getDiscount()) / 100;
            discountAmount = -discountAmount;
            getHeader().getDetails().get(posDescuento).setAmount(discountAmount);
            Cash.getTicketFragment().getAdapterList().notifyDataSetChanged();
        }

        return amount + discountAmount;
    }


    public void getProducts(String idCategory) {
        Database db = new Database(activity);

        this.products = new ProductCrud(db).findByCategory(idCategory);
        db.close();

        gridView.setAdapter(new AdapterProductGridCash(activity, products));
        inCategory = false;

        buttonX2.setVisibility(View.VISIBLE);
        buttonX3.setVisibility(View.VISIBLE);

        buttonBack.setImageResource(R.mipmap.ic_back);

    }

    public void changeBackgroundsSuper() {
        super1.setBackgroundResource(R.drawable.selector_buttons_super);
        super2.setBackgroundResource(R.drawable.selector_buttons_super);
        super3.setBackgroundResource(R.drawable.selector_buttons_super);
        super4.setBackgroundResource(R.drawable.selector_buttons_super);
        super1.setTextColor(Color.parseColor("#000000"));
        super2.setTextColor(Color.parseColor("#000000"));
        super3.setTextColor(Color.parseColor("#000000"));
        super4.setTextColor(Color.parseColor("#000000"));

        switch (superGrupoSelect) {
            case 1:
                super1.setBackgroundResource(R.drawable.style_buttons_super_pressed);
                super1.setTextColor(Color.parseColor("#eeeeee"));
                break;
            case 2:
                super2.setBackgroundResource(R.drawable.style_buttons_super_pressed);
                super2.setTextColor(Color.parseColor("#eeeeee"));
                break;
            case 3:
                super3.setBackgroundResource(R.drawable.style_buttons_super_pressed);
                super3.setTextColor(Color.parseColor("#eeeeee"));
                break;
            case 4:
                super4.setBackgroundResource(R.drawable.style_buttons_super_pressed);
                super4.setTextColor(Color.parseColor("#eeeeee"));
                break;

            default:
                break;
        }
    }

    public static void getCategories(int supergroup) {

        if (displaySupergroups.equals("S")) {
            Database db = new Database(activity);

            categories = new CategoryCrud(db).getSuperGrupo(supergroup);
            db.close();
        } else {
            Database db = new Database(activity);

            categories = new CategoryCrud(db).getAll();
            db.close();
        }

        gridView.setAdapter(new AdapterCategoryGridCash(activity, categories));
        inCategory = true;

        buttonX2.setVisibility(View.INVISIBLE);
        buttonX3.setVisibility(View.INVISIBLE);

        buttonBack.setImageResource(R.mipmap.ic_close);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Log.d("checked", isChecked+"");
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.buttonX2:
                    this.quantity = 2;

                    buttonX3.setChecked(false);
                    break;

                case R.id.buttonX3:
                    this.quantity = 3;

                    buttonX2.setChecked(false);
                    break;
            }

        } else {
            this.quantity = 1;
        }
    }

    public void showOptions() {

        String[] options = getResources().getStringArray(R.array.options_cash);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.select_option).setItems(options, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        if (header.getIdHeader() == 0) {
//                            launchGetBill(2);
                        } else {
//                            showPrinters(header);
                        }

                        break;
                    case 1:
                        new DialogDiscount().show(getActivity().getSupportFragmentManager(), "dialogDiscount");
                        break;
                    case 2:
                        new DialogProductNotDefined().show(getActivity().getSupportFragmentManager(), "Dialog");
                        break;
                    case 3:
                        if (header.getIdHeader() == 0) {
//                            launchGetBill(4);
                        } else {
//                            launchSaveBill(5);
                        }
                        break;

                    default:
                        break;
                }
            }
        });

        final Dialog dialog = builder.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void setHeaderRefreshAll(Header header) {

        this.header = header;
        if (this.header.getDetails().size() > 0) {
            this.setDetailsToHeader(this.header.getDetails(), this.header.getDetails().get(0));
        } else {
            this.setDetailsToHeader(this.header.getDetails(), new Detalle());
        }

        Cash.getTicketFragment().setDetails(this.header.getDetails());
        Cash.getTicketFragment().setAdapterList(new AdapterListTicketFragment(getActivity(), Cash.getTicketFragment().getDetails()));
        Cash.getTicketFragment().getListView().setAdapter(((Cash) getActivity()).getTicketFragment().getAdapterList());

        numDinners.setText(header.getDiners() + "");

        isChanged = false;

    }

    public void cancelAccount() {
        if (header.getIdHeader() == 0) {
            setHeaderRefreshAll(new Header());
        } else {

//            final Dialog dialog = new Dialog(context);
//            dialog.setContentView(R.layout.dialog_cancel);
//            dialog.setTitle("Atención");
//
//            TextView text = (TextView) dialog.findViewById(R.id.textViewDialogCancel);
//            text.setText("La cuenta va a ser anulada y no podrá ser recuperada ¿Desea continuar?");
//
//            Button dialogButton = (Button) dialog.findViewById(R.id.buttonCancelDialogCancel);
//            // if button is clicked, close the custom dialog
//            dialogButton.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//
//            Button dialogButtonOk = (Button) dialog.findViewById(R.id.buttonOkDialogCancel);
//            // if button is clicked, close the custom dialog
//            dialogButtonOk.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    CheckBox checkbox = (CheckBox) dialog.findViewById(R.id.checkBoxDialogCancel);
//
//                    if(checkbox.isChecked()){
//                        ((Cash) getActivity()).getCashFragment().launchSaveBill(3);
//
//                        ((Cash) getActivity()).getCashFragment()
//                                .setHeaderRefreshAll(new Header());
//
//                        dialog.cancel();
//                    }
//
//
//                }
//            });
//
//            dialog.show();

        }

    }

    /**
     * Crea y abre un diálogo para buscar un producto escribiendo en una lista.
     */
    public void launchingFilterDialog() {

        Database dbTemp = new Database(activity);
        ArrayList<Product> listaProductosTemporales = new ArrayList<Product>(new ProductCrud(dbTemp).getTodosLosProductos());
        dbTemp.close();

        new DialogFilterSearch(listaProductosTemporales, buttonMenus.getText().toString(), getActivity()).show(getActivity().getSupportFragmentManager(), "dialogFiltro");
    }

    public Header getHeader() {
        return header;
    }

    public TextView getLastItem() {
        return lastItem;
    }


    public void setTextLastItem(String text) {
        getLastItem().setText(text);
    }

    public Button getButtonMenus() {
        return buttonMenus;
    }

    public void setButtonMenus(Button buttonMenus) {
        this.buttonMenus = buttonMenus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
