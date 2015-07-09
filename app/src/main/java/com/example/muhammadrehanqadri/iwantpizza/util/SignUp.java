package com.example.muhammadrehanqadri.iwantpizza.util;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.muhammadrehanqadri.iwantpizza.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONStringer;

import util.RequestPackage;

public class SignUp extends ActionBarActivity {


    Button Signup;
    EditText name,phone,password,address,Email;
    ProgressBar pb = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pb = (ProgressBar) findViewById(R.id.pb);
        Signup=(Button)findViewById(R.id.button3);
        name=(EditText)findViewById(R.id.editText3);
        phone=(EditText)findViewById(R.id.editText4);
        Email=(EditText)findViewById(R.id.editText5);
        address=(EditText)findViewById(R.id.editText6);
        password=(EditText)findViewById(R.id.editText7);
        Signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (name.getText().toString().trim().length() == 0 || phone.getText().toString().trim().length() == 0 ||
                        Email.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0
                        || address.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Enter all values", Toast.LENGTH_LONG).show();

                } else {
                    SignUpAsync();
                }
            }

        });

    }

    private void SignUpAsync() {
        (new SignUpTask()).execute("http://pizzaapp.apphb.com/Service1.svc/SignUp");
        finish();
        Toast.makeText(getApplicationContext(), "Your Acount is Registered", Toast.LENGTH_LONG).show();
    }

    public  class SignUpTask extends AsyncTask<String,String,Boolean>{

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String uri = params[0];
            HttpPost request = new HttpPost(uri);
            request.setHeader("Accept","application/json");
            request.setHeader("Content-type", "application/json");
try {
    //Build json string
    JSONStringer customer = new JSONStringer()
            .object()
            .key("Customer")
            .object()
            .key("PhoneNo").value(phone.getText().toString())
            .key("Name").value(name.getText().toString())
            .key("Address").value(address.getText().toString())
            .key("Email").value(Email.getText().toString())
            .key("Password").value(password.getText().toString())
            .endObject()
            .endObject();
    StringEntity entity = new StringEntity(customer.toString());
    request.setEntity(entity);

    //send request to wcf service
    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpResponse response = httpClient.execute(request);
    Log.d("imaq","response-> "+response.toString() );
}
catch (Exception e){
    e.printStackTrace();
    Log.d("imaq", "Exception signup-->" + e.getMessage());
    return false;
}
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            pb.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
}
