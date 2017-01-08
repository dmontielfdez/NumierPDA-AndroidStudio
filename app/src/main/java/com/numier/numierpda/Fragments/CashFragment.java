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
import android.widget.ToggleButton;

import com.numier.numierpda.Activities.Menu;
import com.numier.numierpda.Activities.Seling;
import com.numier.numierpda.Activities.Welcome;
import com.numier.numierpda.Adapters.AdapterCategoryGridCash;
import com.numier.numierpda.Adapters.AdapterProductGridCash;
import com.numier.numierpda.DB.CategoryCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Dialogs.DialogAlertExit;
import com.numier.numierpda.Dialogs.DialogDinners;
import com.numier.numierpda.Dialogs.DialogFilterSearch;
import com.numier.numierpda.Models.Category;
import com.numier.numierpda.Models.Header;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.ArrayList;
import java.util.List;


public class CashFragment extends Fragment implements OnClickListener, OnItemClickListener, OnCheckedChangeListener {

    Activity activity;

    int idHeader, numBill;
    String nameBill;

    int quantity;
    boolean inCategory;
    boolean automatic, isChanged;
    List<Category> categories;
    List<Product> products;
    Header header;
    Vibrator vi;
    double cambio;

    private ImageButton buttonBack;
    private ImageButton buttonSeparator;
    private ImageButton getBill;
    private ImageButton saveBill;
    private ImageButton pointBill;
    private ImageButton buttonOptions;
    private ImageButton printerBill;
    private ImageButton buttonSearch;
    private ImageButton buttonModifier;
    private Button buttonMenus;
    private ToggleButton buttonX2;
    private ToggleButton buttonX3;
    private GridView gridView;
    private TextView billNumber;
    private TextView lastItem;
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
    String displaySupergroups;

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
        automatic = true;
        header = new Header();
        isChanged = false;

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
        gridView.setNumColumns(Integer.parseInt(numberColumns));


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

            case R.id.buttonDinners:

                // Previene el doble click
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                vi.vibrate(50);
//                new DialogDinners(Integer.parseInt(numDinners.getText().toString())).show(getActivity().getSupportFragmentManager(), "dialogBillNumber");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Prueba frag = new Prueba(Integer.parseInt(numDinners.getText().toString()));
                frag.show(ft, "dialogDinners");
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        vi.vibrate(50);

        if (inCategory) {
            getProducts(categories.get(position).getId());
        } else {

            Product p = products.get(position);

            if (products.get(position).getIsMenu() == 1) {

                // Instanciamos una lista para guardar las tarifas encontradas
                List<String> rates = new ArrayList<String>();

                // Compruebo si alguna tarifa debe de ser preguntada, en caso de serlo
                // lanzaré un diálogo
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
//                IntakeUtils.generateIntake(getActivity(), products.get(position), quantity, buttonMenus.getText().toString(), this.header.getRate(), true);
            }


            //			IntakeUtils.generateIntake(getActivity(), products.get(position), quantity, buttonMenus.getText().toString(), this.header.getRate());

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

    public void getProducts(String idCategory) {
        Database db = new Database(activity);

        this.products = new ProductCrud(db).findByCategory(idCategory);
        db.close();

        this.gridView.setAdapter(new AdapterProductGridCash(activity, products));
        this.inCategory = false;

        this.buttonX2.setVisibility(View.VISIBLE);
        this.buttonX3.setVisibility(View.VISIBLE);

        this.buttonBack.setImageResource(R.mipmap.ic_back);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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

    public void getCategories(int supergroup) {

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
//                        createDiscount();
                        break;
                    case 2:
//                        new DialogKeyboardSpecific(header.getDetails(), quantity).show(
//                                getActivity().getSupportFragmentManager(), "Dialog");
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

    /**
     * Crea y abre un diálogo para buscar un producto escribiendo en una lista.
     */
    public void launchingFilterDialog() {

        Database dbTemp = new Database(activity);
        ArrayList<Product> listaProductosTemporales = new ArrayList<Product>(new ProductCrud(dbTemp).getTodosLosProductos());
        dbTemp.close();

        new DialogFilterSearch(listaProductosTemporales, buttonMenus.getText().toString(), getActivity()).show(getActivity().getSupportFragmentManager(), "dialogFiltro");
    }
}
