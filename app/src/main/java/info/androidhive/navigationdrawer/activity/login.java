package info.androidhive.navigationdrawer.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import info.androidhive.navigationdrawer.R;

public class login extends AppCompatActivity {
    private static final String TAG ="storage" ;
    SharedPreferences sharedpreferences;

    TextView signup;
    EditText name,password,email,phone;
    Button submit;
    JSONObject jsonObj;
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences("user",MODE_PRIVATE);
        signup=(TextView)findViewById(R.id.link_signup);
submit=(Button) findViewById(R.id.btn_login);

        name=(EditText)findViewById(R.id.input_email);
        password=(EditText) findViewById(R.id.input_password);
       String value = sharedpreferences.getString("token", null);

        if(value!=null&&isStoragePermissionGranted()){
            Intent iu=new Intent(login.this,MainActivity.class);
            startActivity(iu);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonObj=new JSONObject();
                try {
                    jsonObj.put("username",name.getText().toString());
                    jsonObj.put("password",password.getText().toString());
                    HttpPostRequest rt=new HttpPostRequest();
                    rt.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent r=new Intent(login.this,youtube.class);
        //startActivity(r);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(login.this,signup.class);
                startActivity(i);
            }
        });
    }
    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = "http://54.68.72.28:3001/auth";
            String result = null;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestProperty("Content-Type", "application/json");

                if (jsonObj!= null) {
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(jsonObj.toString());
                    writer.flush();
                }

                int statusCode = connection.getResponseCode();
                System.out.println("status code"+statusCode);
                if (statusCode ==  200) {
                    //Create a new InputStreamReader
                    InputStreamReader streamReader = new
                            InputStreamReader(connection.getInputStream());
                    //Create a new buffered reader and String Builder
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    //Check if the line we are reading is not null
                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }
                    //Close our InputStream and Buffered reader
                    reader.close();
                    streamReader.close();
                    //Set our result equal to our stringBuilder
                    result = stringBuilder.toString();
                    System.out.println("goy "+result);
                }
                else{

                    return null;
                }
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;

            //return null;
        }
        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(login.this);
            dialog.setMessage("Loading...Please Wait");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            JSONObject js = null;
            String sd="false";
            String msg="";
            try {
                js=new JSONObject(result);
                sd=js.getString("success");
                msg=js.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AlertDialog alertDialog = new AlertDialog.Builder(
                    login.this).create();

            if(result==null||result==sd){
                alertDialog.setTitle("TR CRABS");
                alertDialog.setMessage("An Error Occured Please try Again");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        // Write your code here to execute after dialog closed
                        //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
            else if(sd=="false"){
                alertDialog.setTitle("TR CRABS");

                alertDialog.setMessage(msg);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        // Write your code here to execute after dialog closed
                        //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
            else {

                String tok="";
                String name="";
                String phone="";
                String email="";
                String photo="";
                String id="";
                try {
                    tok=js.getString("token");
                    name=js.getString("Name");
                    phone=js.getString("Phone");
                    email=js.getString("email");
                    photo=js.getString("photo");
                    photo=js.getString("id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("token", tok);
                editor.putString("name", name);
                editor.putString("phone", phone);
                editor.putString("mail", email);
                editor.putString("photo", photo);
                editor.putString("id", id);

                editor.commit();
                    Intent main=new Intent(login.this,MainActivity.class);
                startActivity(main);
                }


                super.onPostExecute(result);
            }
        }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    }

