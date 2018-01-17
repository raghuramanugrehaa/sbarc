package info.androidhive.navigationdrawer.registrations;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;


public class declaration extends AppCompatActivity {

    Button submit;
    CheckBox c;
    JSONObject dummy;
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);
        submit=(Button) findViewById(R.id.bt_submitform1);
        c=(CheckBox)findViewById(R.id.accept);
        dummy=new JSONObject();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c.isChecked()){
                    SharedPreferences prefs = getSharedPreferences("collect", MODE_PRIVATE);
                    SharedPreferences prefs1 = getSharedPreferences("token", MODE_PRIVATE);
                    Boolean far = prefs.getBoolean("farmer",false);
                    Boolean sup = prefs.getBoolean("supplier",false);
                    Boolean buy = prefs.getBoolean("buyer",false);
                    try {

                        dummy.put("farmer_b",far);
                        dummy.put("supplier_b",sup);
                        dummy.put("buyer_b",buy);
                        dummy.put("completed_by",prefs1.getString("user","no"));
                        dummy.put("token",prefs1.getString("token","no"));
                        dummy.put("profile",new JSONObject(prefs.getString("profile","no")));
                        if(far)
                            dummy.put("farmer",new JSONObject(prefs.getString("farmer1","no")));
                        if(sup)
                            dummy.put("supplier",new JSONObject(prefs.getString("supplier1","no")));
                        if(buy)
                            dummy.put("buyer",new JSONObject(prefs.getString("buyer1","no")));
                        System.out.println("pop"+dummy.toString());
                        HttpPostRequest rt=new HttpPostRequest();
                        rt.execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please agree with the terms and conditions.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = "http://35.163.113.249:3001/setup";
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

                if (dummy != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(dummy.toString());
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
            dialog=new ProgressDialog(declaration.this);
            dialog.setMessage("Loading...Please Wait");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            JSONObject rres;
            System.out.println("goy it"+result);
            try {
                rres=new JSONObject(result);
                AlertDialog alertDialog = new AlertDialog.Builder(
                        declaration.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("TR CRABS");
                if(rres.getBoolean("success")) {


                    // Setting Dialog Message
                    alertDialog.setMessage(rres.getString("message"));

                    // Setting Icon to Dialog


                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences preferences = getSharedPreferences("collect", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.commit();

                            Intent i=new Intent(declaration.this,MainActivity.class);
                            startActivity(i);
                            // Write your code here to execute after dialog closed
                            //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                else{



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
