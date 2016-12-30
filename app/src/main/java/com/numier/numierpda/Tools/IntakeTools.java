package com.numier.numierpda.Tools;

public class IntakeTools {

//	public static void generateIntake(FragmentActivity fragmentActivity, Product product, int quantity, String numOrden, int rate, boolean sumar) {
//
//		// Variables del metodo
//		Detalle detail;
//
//		// Instanciacion de las variables
//		detail = new Detalle();
//
//		// Le asigno valores que deben de asignarse
//		detail.setIdProduct(product.getId());
//		detail.setProductName(product.getName());
//		detail.setQuantity(quantity);
//		detail.setPrinted(0);
//
//
//		if(product.getNumPlato() == 0){
//			// Asigno el orden de platos
//			if(numOrden.equals("Nº Ord.")){
//				detail.setNumOrden("");
//			} else{
//				detail.setNumOrden(numOrden);
//			}
//		} else{
//			switch (product.getNumPlato()) {
//			case 1:
//				detail.setNumOrden("1º");
//				break;
//			case 2:
//				detail.setNumOrden("2º");
//				break;
//			case 3:
//				detail.setNumOrden("3º");
//				break;
//
//			default:
//				detail.setNumOrden("");
//				break;
//			}
//		}
//
//		// Conjunto de condiciones necesarias para poder generar una
//		// generateIntake
//		if (launchDialogSubProduct(fragmentActivity, product, detail, rate, sumar)) {
//
//		} else if (launchDialogPrice(fragmentActivity, product, detail, sumar)) {
//
//		} else if (launchDialogRate(fragmentActivity, product, detail, rate, sumar)) {
//
//		} else if (launchDialogWeight(fragmentActivity, product, detail, sumar)) {
//
//		} else
//			launchDialogMessage(fragmentActivity, product, detail, sumar);
//
//	}
//
//
//	public static void generateIntakeMenu(FragmentActivity fragmentActivity, Product product, int quantity, String numOrden, int rate, boolean det, int numPlato, boolean haveRate, String rateFinal, boolean sumar) {
//
//		// Variables del metodo
//		Detalle detail;
//
//		// Instanciacion de las variables
//		detail = new Detalle();
//
//		// Le asigno valores que deben de asignarse
//		detail.setIdProduct(product.getId());
//		detail.setProductName(product.getName());
//		detail.setQuantity(quantity);
//		detail.setPrinted(0);
//		detail.setNumOrden("");
//		detail.setRate("");
//		detail.setOption("");
//
//
//		if(numPlato == 0){
//			// Asigno el orden de platos
//			if(numOrden.equals("Nº Ord.")){
//				detail.setNumOrden("");
//			} else{
//				detail.setNumOrden(numOrden);
//			}
//		} else{
//			switch (numPlato) {
//			case 1:
//				detail.setNumOrden("1º");
//				break;
//			case 2:
//				detail.setNumOrden("2º");
//				break;
//			case 3:
//				detail.setNumOrden("3º");
//				break;
//			case 4:
//				detail.setNumOrden("4º");
//				break;
//			case 5:
//				detail.setNumOrden("5º");
//				break;
//			case 6:
//				detail.setNumOrden("6º");
//				break;
//
//			default:
//				detail.setNumOrden("");
//				break;
//			}
//		}
//
//
//		if(det){
//			detail.setNoImprimir(true);
//			detail.setAmount(0);
//			detail.setPrice(0);
//
//		} else{
//			detail.setNoImprimir(false);
//
//			if(haveRate){
//
//				// Le asigno la tarifa a detalle
//				detail.setRate(rateFinal);
//
//				// Le asigno el precio a detalle
//				detail.setPrice(getPriceOfValue(product, rateFinal));
//			} else{
//				// Como precio buscaré en preferencias la tarifa por defecto y esa
//				// enviaré
//				int ra = 1;
//				if(rate == 0){
//					if (!PreferencesTools.getValueOfPreferences(fragmentActivity,"NumierPreferences", "rate").equals("")){
//						ra = Integer.parseInt(PreferencesTools.getValueOfPreferences(fragmentActivity, "NumierPreferences", "rate"));
//					}
//				} else{
//					ra = rate;
//				}
//
//				switch (ra) {
//				case 1:
//					detail.setPrice(product.getRate1());
//					break;
//				case 2:
//					detail.setPrice(product.getRate2());
//					break;
//				case 3:
//					detail.setPrice(product.getRate3());
//					break;
//				case 4:
//					detail.setPrice(product.getRate4());
//					break;
//
//				}
//			}
//
//
//
//			detail.setAmount(detail.getQuantity() * detail.getPrice());
//		}
//
//		((Cash) fragmentActivity).getTicketFragment().addAndUpdate(detail, sumar);
//
//
//	}
//
//	public static void generateIntakeSeling(FragmentActivity fragmentActivity, Product product, int quantity, String numOrden, int rate, boolean sumar) {
//
//		// Variables del metodo
//		Detalle detail;
//
//		// Instanciacion de las variables
//		detail = new Detalle();
//
//		// Le asigno valores que deben de asignarse
//		detail.setIdProduct(product.getId());
//		detail.setProductName(product.getName());
//		detail.setQuantity(quantity);
//		detail.setPrinted(0);
//		detail.setNumOrden("");
//		detail.setRate("");
//		detail.setOption("");
//		detail.setIsSeling(1);
//
//
//
//		if(product.getNumPlato() == 0){
//			// Asigno el orden de platos
//			if(numOrden.equals("Nº Ord.")){
//				detail.setNumOrden("");
//			} else{
//				detail.setNumOrden(numOrden);
//			}
//		} else{
//			switch (product.getNumPlato()) {
//			case 1:
//				detail.setNumOrden("1º");
//				break;
//			case 2:
//				detail.setNumOrden("2º");
//				break;
//			case 3:
//				detail.setNumOrden("3º");
//				break;
//
//			default:
//				detail.setNumOrden("");
//				break;
//			}
//		}
//
//
//		int ra = 1;
//		if(rate == 0){
//			if (!PreferencesTools.getValueOfPreferences(fragmentActivity,"NumierPreferences", "rate").equals("")){
//				ra = Integer.parseInt(PreferencesTools.getValueOfPreferences(fragmentActivity, "NumierPreferences", "rate"));
//			}
//		} else{
//			ra = rate;
//		}
//
//		switch (ra) {
//		case 1:
//			detail.setPrice(product.getRate1());
//			break;
//		case 2:
//			detail.setPrice(product.getRate2());
//			break;
//		case 3:
//			detail.setPrice(product.getRate3());
//			break;
//		case 4:
//			detail.setPrice(product.getRate4());
//			break;
//
//		}
//
//		// Busco los seling y los añado
//
//		Database db = new Database(fragmentActivity, "NUMIER", null, Database.versionDB);
//
//		ArrayList<SubproductSeling> listSubproducts = (ArrayList<SubproductSeling>) new SelingSubproductCrud(db).getAllSeling(product.getId());
//
//		ArrayList<Subproduct> listSubproductsBase = new ArrayList<Subproduct>();
//		ArrayList<Subproduct> listSubproductsExtras = new ArrayList<Subproduct>();
//		ArrayList<Subproduct> listSubproductsOpcional = new ArrayList<Subproduct>();
//
//		double amount = detail.getPrice();
//
//		for (SubproductSeling sub : listSubproducts) {
//			if(sub.getType().equals("B") && sub.isChecked()){
//				amount += sub.getPrice();
//				listSubproductsBase.add(new Subproduct(Integer.parseInt(sub.getIdSubproduct()), sub.getName(), sub.getPrice()));
//			}
//
//			if(sub.getType().equals("E") && sub.getCounter() > 0){
//				for (int i = 0; i < sub.getCounter(); i++) {
//					amount += sub.getPrice();
//					listSubproductsExtras.add(new Subproduct(Integer.parseInt(sub.getIdSubproduct()), sub.getName(), sub.getPrice()));
//				}
//			}
//
//			if(sub.getType().equals("O") && sub.isChecked()){
//				amount += sub.getPrice();
//				listSubproductsOpcional.add(new Subproduct(Integer.parseInt(sub.getIdSubproduct()), sub.getName(), sub.getPrice()));
//			}
//
//		}
//
//		detail.setListSubProducts(listSubproductsBase);
//		detail.setListSubProductsExtra(listSubproductsExtras);
//		detail.setListSubProductsOpcional(listSubproductsOpcional);
//
//		Log.d("amount", amount+"€");
//
//		detail.setPrice(amount);
//
//		detail.setAmount(detail.getQuantity() * detail.getPrice());
//
//		((Cash) fragmentActivity).getTicketFragment().addAndUpdate(detail, sumar);
//
//		new SelingSubproductCrud(db).deleteSeling(product.getId());
//
//	}
//
//	// Fragmento emergente que pregunta por las tarifas
//	@SuppressLint("ValidFragment")
//	public static class DialogRate extends DialogFragment {
//
//		// Atributos
//		private Product product;
//		private FragmentActivity context;
//		private String[] rates;
//		private Detalle detail;
//		private int rate;
//		private boolean sumar;
//
//		public DialogRate(String[] rates, Product product,
//				FragmentActivity fragmentActivity, Detalle detail, int rate, boolean sumar) {
//			this.rates = rates;
//			this.context = fragmentActivity;
//			this.product = product;
//			this.detail = detail;
//			this.rate = rate;
//			this.sumar = sumar;
//
//		}
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//			builder.setTitle("Seleccione una tarifa");
//
//			builder.setAdapter(new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_list_item_1, rates),
//					new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//
//					String rate = rates[which];
//					rate = rate.substring(0, rate.lastIndexOf("-")-1);
//
//					// Le asigno la tarifa a detalle
//					detail.setRate(rate);
//
//					// Le asigno el precio a detalle
//					detail.setPrice(getPriceOfValue(product, rate));
//
//					// Realizo las comprobaciones necesarias para seguir
//					// con el proceso
//					if (launchDialogWeight(context, product, detail, sumar)) {
//
//					} else if (launchDialogMessage(context, product,
//							detail, sumar)) {
//
//					}
//				}
//			});
//
//			return builder.create();
//		}
//
//
//	}
//
//	// Metodo para obtener el precio que tiene asignado una tarifa
//	public static double getPriceOfValue(Product product, String value) {
//
//		if (value.equals(product.getRateName1())) {
//			return product.getRate1();
//		} else if (value.equals(product.getRateName2())) {
//			return product.getRate2();
//		} else if (value.equals(product.getRateName3())) {
//			return product.getRate3();
//		}  else if (value.equals(product.getRateName4())) {
//			return product.getRate4();
//		}else {
//			return product.getRate1();
//		}
//	}
//
//	// Fragmento emergente que pregunta por los subproductos
//	@SuppressLint("ValidFragment")
//	public static class DialogSubProduct extends DialogFragment {
//
//		// Atributos
//		private List<Subproduct> subProducts;
//		private Product product;
//		private Detalle detail;
//		private String[] subProductsName;
//		List<Subproduct> subProductsRequired;
//		private int rate;
//		private boolean sumar;
//
//		public DialogSubProduct(List<Subproduct> subProducts, Product product,
//				Detalle detail, List<Subproduct> subProductsRequired, int rate, boolean sumar) {
//			this.subProducts = subProducts;
//			this.product = product;
//			this.detail = detail;
//			this.subProductsRequired = subProductsRequired;
//			subProductsName = new String[subProducts.size()];
//			this.rate = rate;
//			this.sumar = sumar;
//
//			// Recorremos los subproductos para sacar los nombres de estos
//			// subproductos
//			for (int i = 0; i < subProducts.size(); i++) {
//				subProductsName[i] = subProducts.get(i).getName();
//			}
//
//		}
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//			builder.setTitle("Seleccione subartículo nº "+ (detail.getListSubProducts().size() + 1))
//			.setItems(subProductsName, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//
//					detail.getListSubProducts().add(subProducts.get(which));
//
//					// Compruebo si el producto tiene precio absoluto
//					Double versionControlador = Double.parseDouble(PreferencesTools.getValueOfPreferences(getActivity(), "NumierPreferences", "versionControlador"));
//
//					if(versionControlador >= 2.5){
//						if(product.getAbsolutPrice() == 0){
//							detail.setPriceExtra(detail.getPriceExtra() + subProducts.get(which).getPrice());
//						}
//					} else{
//						detail.setPriceExtra(detail.getPriceExtra() + subProducts.get(which).getPrice());
//					}
//
//					// Compruebo si tengo tantos subproductos
//					// como
//					// repeticiones me exige el producto
//					if (detail.getListSubProducts().size() == product.getNumberSubproducts()) {
//
//						// Recorro los requeridos para en caso
//						// de haber
//						// algunos introducirlos
//						for (Subproduct value : subProductsRequired) {
//							detail.getListSubProducts().add(
//									value);
//						}
//
//						// Realizamos las comprobacines
//						// necesarias para
//						// seguir con el proceso
//						if (launchDialogPrice(getActivity(),
//								product, detail, sumar)) {
//
//						} else if (launchDialogRate(
//								getActivity(), product, detail, rate, sumar)) {
//
//						} else if (launchDialogWeight(
//								getActivity(), product, detail, sumar)) {
//
//						} else if (launchDialogMessage(
//								getActivity(), product, detail, sumar)) {
//
//						}
//					} else {
//						new DialogSubProduct(subProducts,
//								product, detail,
//								subProductsRequired, rate, sumar)
//						.show(getActivity()
//								.getSupportFragmentManager(),
//								"dial");
//
//					}
//				}
//			});
//			builder.setCancelable(true);
//			final Dialog dialog = builder.create();
//
//			dialog.setCanceledOnTouchOutside(false);
//			return dialog;
//		}
//	}
//
//	@SuppressLint("ValidFragment")
//	public static class DialogMessage extends DialogFragment implements
//	OnClickListener, OnItemClickListener {
//
//		// Atributos
//		private String[] listMessages;
//		private Detalle detail;
//
//		// View
//		private GridView gridview;
//		private TextView textview;
//		private Button accept, back;
//		private boolean sumar;
//
//		public DialogMessage(String[] messages, Detalle detail, boolean sumar) {
//			this.listMessages = messages;
//			this.detail = detail;
//			this.sumar = sumar;
//		}
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//
//
//			final Dialog dialog = new Dialog(getActivity());
//
//			dialog.setCanceledOnTouchOutside(false);
//
//			dialog.setTitle("Comentario");
//
//			dialog.getWindow().setGravity(Gravity.CENTER);
//			View v = getActivity().getLayoutInflater().inflate(
//					R.layout.dialog_product_message_cash, null);
//
//			// Rescato la vista
//			gridview = (GridView) v.findViewById(R.id.gridViewDialogMessage);
//			accept = (Button) v.findViewById(R.id.buttonAcceptMessageDialog);
//			back = (Button) v.findViewById(R.id.buttonBackMessageDialog);
//			textview = (TextView) v
//					.findViewById(R.id.inputMessageDialogMessage);
//
//			// Asigno los eventos
//			this.accept.setOnClickListener(this);
//			this.back.setOnClickListener(this);
//			this.gridview.setOnItemClickListener(this);
//
//			gridview.setAdapter(new AdapterGridDialogMessage(getActivity(),
//					listMessages));
//
//			dialog.setContentView(v);
//
//			dialog.getWindow().setBackgroundDrawable(
//					new ColorDrawable(Color.WHITE));
//
//			dialog.show();
//
//			return dialog;
//
//		}
//
//		@Override
//		public void onClick(View v) {
//
//			switch (v.getId()) {
//
//			case R.id.buttonBackMessageDialog:
//				this.dismiss();
//				break;
//
//			case R.id.buttonAcceptMessageDialog:
//
//				// Le asigno el mensaje
//				this.detail.setOption(this.textview.getText().toString());
//
//				((Cash) getActivity()).getTicketFragment().addAndUpdate(detail, sumar);
//
//				this.dismiss();
//
//				break;
//
//			}
//
//		}
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//
//			textview.setText(listMessages[arg2]);
//			// Le asigno el mensaje
//			this.detail.setOption(this.textview.getText().toString());
//
//			((Cash) getActivity()).getTicketFragment().addAndUpdate(detail, sumar);
//
//			this.dismiss();
//		}
//
//	}
//
//	public static boolean launchDialogSubProduct(
//			FragmentActivity fragmentActivity, Product product, Detalle detail, int rate, boolean sumar) {
//
//
//
//		// Compruebo en primer lugar si tengo subprodutos
//		if (product.getSubproducts() == 1) {
//
//			// En caso de tenerlos busco en la base de datos los subproductos
//			// relacionados con el id
//			Database db = new Database(fragmentActivity, "NUMIER", null, Database.versionDB);
//
//			List<ProductSubproduct> productSubProducts = new ProductSubproductCrud(db).findSubproduct(product.getId());
//
//			// Instancio otra lista para guardar los subproductos que estén
//			// incluidos
//			List<Subproduct> subProducts = new ArrayList<Subproduct>();
//			List<Subproduct> subProductsRequired = new ArrayList<Subproduct>();
//			db.close();
//			// Si se han encontrado algún subproducto sigo con el proceso
//			if (productSubProducts.size() > 0) {
//
//				// Recorremos lo subproductos encontrados
//				for (int i = 0; i < productSubProducts.size(); i++) {
//
//					// Comprobamos si está incluido para guardarlo en la lista a
//					// imprimir en pantalla, en caso de no estarlo lo
//					// incluimos en el detalle pero sin necesidad de sacarlo en
//					// la ventana de diálogo
//					if (productSubProducts.get(i).getIncluded() == 1) {
//
//						Subproduct bp = new SubproductCrud(db).findById(productSubProducts.get(i).getIdSubproduct());
//
//						if(bp != null){
//							bp.setPrice(productSubProducts.get(i).getPrice());
//
//							subProducts.add(bp);
//						}
//
//
//
//					} else {
//						Subproduct bp = new SubproductCrud(db).findById(productSubProducts.get(i).getIdSubproduct());
//
//						if(bp != null){
//							subProductsRequired.add(bp);
//
//							detail.setPriceExtra(productSubProducts.get(i).getPrice());
//						}
//
//					}
//				}
//
//				// Comprobamos si hay algún subproducto por el que preguntar, en
//				// caso de encontrarlo lo sacamos por pantalla, en caso
//				// contrario le indico q los id a mostrar son solo los
//				// requeridos
//				if (subProducts.size() > 0 && product.getNumberSubproducts() != 0) {
//
//					new DialogSubProduct(subProducts, product, detail,
//							subProductsRequired, rate, sumar).show(
//									fragmentActivity.getSupportFragmentManager(),
//									"dialogSubproducts");
//
//
//					return true;
//				} else {
//					detail.setListSubProducts((subProductsRequired));
//					return false;
//				}
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
//
//	}
//
//	public static boolean launchDialogRate(FragmentActivity fragmentActivity,
//			Product product, Detalle detail, int rate, boolean sumar) {
//
//
//		// Instanciamos una lista para guardar las tarifas encontradas
//		List<String> rates = new ArrayList<String>();
//
//		// Compruebo si alguna tarifa debe de ser preguntada, en caso de serlo
//		// lanzaré un diálogo
//		if (product.getRateOption1() == 1) {
//			rates.add(product.getRateName1() + " - " +ConversionTools.getFormatPrice(product.getRate1(), false));
//		}
//		if (product.getRateOption2() == 1) {
//			rates.add(product.getRateName2() + " - " +ConversionTools.getFormatPrice(product.getRate2(), false));
//		}
//		if (product.getRateOption3() == 1) {
//			rates.add(product.getRateName3() + " - " +ConversionTools.getFormatPrice(product.getRate3(), false));
//		}
//		if (product.getRateOption4() == 1) {
//			rates.add(product.getRateName4() + " - " +ConversionTools.getFormatPrice(product.getRate4(), false));
//		}
//
//		// Si he encontrado alguna tarifa por la que preguntar sacaré el diálogo
//		if (rates.size() > 0) {
//
//			new DialogRate(rates.toArray(new String[rates.size()]), product,
//					fragmentActivity, detail, rate, sumar)
//			.show(fragmentActivity.getSupportFragmentManager(),
//					"dialogRates");
//			return true;
//
//		} else {
//			// Si no encuentro ninguna tarifa por la que preguntar el valor
//			// tarifa lo envio blanco
//			detail.setRate("");
//
//			// Como precio buscaré en preferencias la tarifa por defecto y esa
//			// enviaré
//			int ra = 1;
//			if(rate == 0){
//				if (!PreferencesTools.getValueOfPreferences(fragmentActivity,"NumierPreferences", "rate").equals("")){
//					ra = Integer.parseInt(PreferencesTools.getValueOfPreferences(fragmentActivity, "NumierPreferences", "rate"));
//				}
//			} else{
//				ra = rate;
//			}
//
//			switch (ra) {
//			case 1:
//				detail.setPrice(product.getRate1());
//				break;
//			case 2:
//				detail.setPrice(product.getRate2());
//				break;
//			case 3:
//				detail.setPrice(product.getRate3());
//				break;
//			case 4:
//				detail.setPrice(product.getRate4());
//				break;
//
//			}
//
//			return false;
//		}
//
//	}
//
//	public static boolean launchDialogMessage(
//			FragmentActivity fragmentActivity, Product product, Detalle detail, boolean sumar) {
//
//
//
//		// Variables
//		List<String> messages = new ArrayList<String>();
//
//		if (product.getOption1().length() > 0) {
//			messages.add(product.getOption1());
//		}
//		if (product.getOption2().length() > 0) {
//			messages.add(product.getOption2());
//		}
//		if (product.getOption3().length() > 0) {
//			messages.add(product.getOption3());
//		}
//		if (product.getOption4().length() > 0) {
//			messages.add(product.getOption4());
//		}
//		if (product.getOption5().length() > 0) {
//			messages.add(product.getOption5());
//		}
//
//		if (messages.size() > 0) {
//			DialogMessage fragment1 = new DialogMessage(
//					messages.toArray(new String[messages.size()]), detail, sumar);
//			fragment1.setCancelable(false);
//			fragment1.show(fragmentActivity.getSupportFragmentManager(), "");
//
//			return true;
//
//		} else {
//
//			// Le asigno el mensaje
//			detail.setOption("");
//
//			((Cash) fragmentActivity).getTicketFragment().addAndUpdate(detail, sumar);
//
//			return false;
//		}
//
//	}
//
//	public static boolean launchDialogPrice(FragmentActivity fragmentActivity,
//			Product product, Detalle detail, boolean sumar) {
//
//
//
//		// Comprobamos si es necesario preguntar por el precio
//		if (product.getAskPrice() == 1) {
//
//			new DialogKeyboard("Indique el precio del artículo", false, detail,
//					product).show(fragmentActivity.getSupportFragmentManager(),
//							"Dialog Keyboard");
//
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public static boolean launchDialogWeight(FragmentActivity fragmentActivity,
//			Product product, Detalle detail, boolean sumar) {
//		// Comprobamos si es necesario preguntar por el peso
//		if (product.getAskWeight() == 1) {
//
//			detail.setDecimalQuantity(true);
//
//			new DialogKeyboard("Indique el peso del artículo", true, detail,
//					product).show(fragmentActivity.getSupportFragmentManager(),
//							"Dialog Keyboard");
//
//			return true;
//		} else {
//
//			detail.setAmount(detail.getQuantity() * detail.getPrice());
//
//			return false;
//		}
//	}
}
