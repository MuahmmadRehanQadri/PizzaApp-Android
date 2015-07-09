package com.example.muhammadrehanqadri.iwantpizza.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadrehanqadri.iwantpizza.R;

import java.util.ArrayList;

import Adapter.ViewPizzasAdapter;
import DB.DBHandler;
import Model.Pizza;


public class ViewPizzasActivity extends Activity {

    ArrayList<Pizza> pizzaArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pizzas);

        PopulateGridView();
    }

    final ArrayList<Pizza> selectedPizzaArrayList = new ArrayList<>();

    private void PopulateGridView() {
        //get pizzas
        DBHandler db = new DBHandler(ViewPizzasActivity.this);
        final ArrayList<Pizza> pizzaArrayList = db.getAllPizzas();
        GridView gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new ViewPizzasAdapter(ViewPizzasActivity.this, pizzaArrayList));
        gv.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String pizzaSelectedName = (view.findViewById(R.id.editTextName)).toString();
                double pizzaSelectedPrice = Double.parseDouble(((TextView) (view.findViewById(R.id.editTextPrice))).getText().toString());
                final int[] pizzaSelectedQty = new int[1];
                if (view.getBackground()==null) {
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewPizzasActivity.this);
                        alertDialogBuilder.setTitle("How many?");
                        final NumberPicker numberPicker = new NumberPicker(ViewPizzasActivity.this);
                        numberPicker.setMinValue(1);
                        numberPicker.setMaxValue(10000);
                        alertDialogBuilder.setView(numberPicker);
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pizzaSelectedQty[0] = numberPicker.getValue();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                    Pizza pizzaSelected = new Pizza(pizzaSelectedName, pizzaSelectedQty[0] +"",pizzaSelectedPrice, null);
                    view.setBackground(getResources().getDrawable(R.mipmap.blue));
                    selectedPizzaArrayList.add(pizzaSelected);
                }
                else {
                    view.setBackground(null);
                //  selectedPizzaArrayList.remove(pizzaSelected);
                    for (int i = 0; i<selectedPizzaArrayList.size(); i++){
                        if (selectedPizzaArrayList.get(i).getName()==pizzaSelectedName){
                            selectedPizzaArrayList.remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pizzas, menu);
        return true;
    }
    String address = null;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_order) {
            //select map
            Intent intent = new Intent(ViewPizzasActivity.this, mapaddress.class);
            startActivityForResult(intent,100);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            address = data.getStringExtra("location");

            //place order code
            placeOrder();

            //toast:ORDER placed!
            Toast.makeText(ViewPizzasActivity.this,"Order Placed!", Toast.LENGTH_SHORT).show();
            //redirect to orders

        }
    }

    private void placeOrder() {
        //TODO
        //INSERT INTO ORDER TABLE

    }
}
