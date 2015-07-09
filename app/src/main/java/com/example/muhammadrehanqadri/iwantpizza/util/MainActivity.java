package com.example.muhammadrehanqadri.iwantpizza.util;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.muhammadrehanqadri.iwantpizza.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.concurrent.Executor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import util.ConnManager;
import util.HttpManager;
import util.RequestPackage;

public class MainActivity extends ActionBarActivity {


    SQLiteDatabase db;
    Button Signup;
    EditText phone;
    EditText password;
    private ProgressBar pb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!ConnManager.isConnectedOrConnecting(MainActivity.this)){
            Toast.makeText(MainActivity.this,"You must be connected to the internet",Toast.LENGTH_SHORT).show();
        }
        pb = (ProgressBar) findViewById(R.id.pb);
       // db = openOrCreateDatabase("CustomerDB", Context.MODE_PRIVATE, null);
     //   db.execSQL("CREATE TABLE IF NOT EXISTS Customer(name VARCHAR,phone VARCHAR PRIMARY KEY,Email VARCHAR,Address VARCHAR,Password VARCHAR);");
         Signup=(Button)findViewById(R.id.button2);
        Button signin=(Button)findViewById(R.id.button);
        phone=(EditText)findViewById(R.id.editText);
         password=(EditText)findViewById(R.id.editText2);
        signin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                String phoneEntered = phone.getText().toString();
                String passwordEntered = password.getText().toString();
//                if( (phoneEntered == "admin" || phoneEntered == "Admin") && (passwordEntered=="iwantpizza")){
//                    startActivity(new Intent(MainActivity.this, AdminActivity.class));
//                }
              //  else {
                if (ConnManager.isConnected(MainActivity.this)){
                    if (phoneEntered=="123" && passwordEntered=="iwantpizza")
                        startActivity(new Intent(MainActivity.this,AdminActivity.class));
                    boolean login = LoginAsync(phoneEntered, passwordEntered);
                }
                else{
                    Toast.makeText(MainActivity.this,"You must be connected to the internet",Toast.LENGTH_SHORT).show();
                }
            //    }
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




        public boolean LoginAsync(String phoneEntered, String passwordEntered) {
            RequestPackage requestPackage=new RequestPackage();
            requestPackage.setUri("http://pizzaapp.apphb.com/Service1.svc/Login");
            requestPackage.addParams("phoneNo", phoneEntered);
            requestPackage.addParams("password", passwordEntered);
            (new LoginTask()).execute(requestPackage);
            return false;
        }

        private class LoginTask extends AsyncTask<RequestPackage,String,Boolean> {
            @Override
            protected void onPreExecute() {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(RequestPackage... params) {
                String uri = params[0].getUriWithParamsSlashSeperated();
                Log.d("imaq","uri-> "+uri);
                try{
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(uri);
                    httpGet.setHeader("Accept","application/json");
                    httpGet.setHeader("Content-type", "application/json");

                    HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
                    HttpEntity responseEntity = httpResponse.getEntity();

                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.parse(responseEntity.getContent());
                    String docStr = getStringFromDoc(doc);
                    Log.d("imaq","result-> " + docStr);

                    //Read Response data into buffer
                    /*char[] buffer = new char[(int)responseEntity.getContentLength()];
                    InputStreamReader inputStreamReader = new InputStreamReader(responseEntity.getContent());
                    inputStreamReader.read(buffer);
                    inputStreamReader.close();
                    Log.d("imaq", "buffer-> " + buffer.toString());
                    JSONArray jsonArray = new JSONArray(new String (buffer));
                    String s = jsonArray.get(0).toString();
                    Log.d("imaq","result-> "+s);*/
                    return  true;
                }
                catch (Exception e){
                    Log.d("imaq","Exception mainactivity-->"+e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }

            private String getStringFromDoc(Document doc) throws TransformerException {
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                StringWriter sw = new StringWriter();
                t.transform(new DOMSource(doc), new StreamResult(sw));
                return sw.toString();
            }

            @Override
            protected void onPostExecute(Boolean logggedIn) {
                pb.setVisibility(View.INVISIBLE);
                if (logggedIn) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ViewPizzasActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "invalid Phone number and/or Password",
                            Toast.LENGTH_LONG).show();
                }
                phone.setText(null);
                password.setText(null);
            }
        }

}
