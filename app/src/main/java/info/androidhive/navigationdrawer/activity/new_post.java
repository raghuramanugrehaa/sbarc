package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.style.IconMarginSpan;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.farm_visiting.declaration;


public class new_post extends Activity {
ImageView pick,save;

    JSONObject dummy;
    String resp;
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    ImageView show;
    int RESULT_LOAD_IMG=1;

    Bitmap bitmap;
    TextView title,description;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        pick=(ImageView) findViewById(R.id.pick);
        show=(ImageView) findViewById(R.id.show);
        save=(ImageView) findViewById(R.id.save);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.desc);
        sharedpreferences = getSharedPreferences("user",MODE_PRIVATE);

        dummy=new JSONObject();
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dummy.put("Name",title.getText().toString());

                    dummy.put("Description",description.getText().toString());
                    dummy.put("photo",bitmapToBase64(bitmap));
                    dummy.put("user",sharedpreferences.getString("name",null));
                    HttpPostRequest rt=new HttpPostRequest();
                    rt.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
               show.setVisibility(View.VISIBLE);
                // Log.d(TAG, String.valueOf(bitmap));
                show.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = "http://54.68.72.28:3001/comments/save";
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
            dialog=new ProgressDialog(new_post.this);
            dialog.setMessage("Loading...Please Wait");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            AlertDialog alertDialog = new AlertDialog.Builder(
                    new_post.this).create();
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




                                Intent i = new Intent(new_post.this, MainActivity.class);
                                startActivity(i);
                                // Write your code here to execute after dialog closed
                                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();


                        // Showing Alert Message
                        alertDialog.show();
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
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
