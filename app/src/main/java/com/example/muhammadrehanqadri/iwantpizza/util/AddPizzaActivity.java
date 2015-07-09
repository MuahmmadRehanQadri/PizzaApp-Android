package com.example.muhammadrehanqadri.iwantpizza.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.muhammadrehanqadri.iwantpizza.R;

import java.io.ByteArrayOutputStream;

import DB.DBHandler;


public class AddPizzaActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private  String location;
    private int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pizza);

//        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(100000);

        Button browseButton = (Button) findViewById(R.id.buttonBrowse);
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button addButton = (Button) findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextName = ((EditText) findViewById(R.id.editTextName)).getText().toString();
                String editTextType = ((EditText) findViewById(R.id.editTextType)).getText().toString();
                String editTextPrice = ((EditText) findViewById(R.id.editTextPrice)).getText().toString();
                ImageView imageView = (ImageView) findViewById(R.id.image);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                //DBHandler dbHandler = new DBHandler(getApplicationContext());
                AddNewPizzaAsync(editTextName, editTextType, editTextPrice, stream.toByteArray());
                Toast.makeText(AddPizzaActivity.this,"Added!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddNewPizzaAsync(String editTextName, String editTextType, String editTextPrice, byte[] imageBytes) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_pizza, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String locationOption = parent.getItemAtPosition(position).toString();
        if (locationOption.equals("Current")){
            location = "Current";
            //TODO: IRFAN's CODE
        }
        else if (locationOption.equals("Manual")){
            location = "Manual";
        }
        else{
            location="Default";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
}
