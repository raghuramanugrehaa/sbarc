package info.androidhive.navigationdrawer.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.farm_visiting.declaration;

public class signup extends AppCompatActivity {
    EditText name,password,email,phone;
    Button submit;
    JSONObject jsonObj;
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=(EditText) findViewById(R.id.input_name);
        password=(EditText)findViewById(R.id.input_password);
        phone=(EditText)findViewById(R.id.input_phone);
        email=(EditText)findViewById(R.id.input_email);
        submit=(Button) findViewById(R.id.btn_signup);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObj.put("name",name.getText().toString());
                    jsonObj.put("password",password.getText().toString());
                    jsonObj.put("phone",phone.getText().toString());
                    jsonObj.put("email",email.getText().toString());
                    jsonObj.put("token", FirebaseInstanceId.getInstance().getToken());
HttpPostRequest rt=new HttpPostRequest();
                    rt.execute();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        jsonObj=new JSONObject();
    }


    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = "http://54.68.72.28:3001/registration";
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
            dialog=new ProgressDialog(signup.this);
            dialog.setMessage("Loading...Please Wait");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            AlertDialog alertDialog = new AlertDialog.Builder(
                    signup.this).create();
            if(result==null){
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
            else {
                JSONObject rres;
                System.out.println("goy it" + result);
                try {
                    rres = new JSONObject(result);

                    // Setting Dialog Title
                    alertDialog.setTitle("TR CRABS");
                    if (rres.getBoolean("success")) {





                                Intent i = new Intent(signup.this, login.class);
                                startActivity(i);
                                // Write your code here to execute after dialog closed
                                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

                    } else {


                        // Setting Dialog Title


                        // Setting Dialog Message
                        alertDialog.setMessage(rres.getString("message"));

                        // Setting Icon to Dialog


                        // Setting OK Button
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Write your code here to execute after dialog closed
                                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                super.onPostExecute(result);
            }
        }
    }
}
