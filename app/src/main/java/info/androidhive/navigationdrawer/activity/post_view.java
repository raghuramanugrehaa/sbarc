package info.androidhive.navigationdrawer.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.farm_visiting.declaration;
import info.androidhive.navigationdrawer.fragment.PhotosFragment;
import info.androidhive.navigationdrawer.fragment.view_comments;
import info.androidhive.navigationdrawer.other.community_home_helper;
import info.androidhive.navigationdrawer.other.pdf_main;

public class post_view extends AppCompatActivity {
String title,desc,time,photo,user,po;
    view_comments vm;
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    private CoordinatorLayout coordinatorLayout;
    EditText lo;
    JSONObject dummy;
    ImageView ol;
    ListView lv;
    private Exception exceptionToBeThrown;
TextView t,d,tm,u;
    ImageView ph;
    String [] name,com,tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        lv=(ListView) findViewById(R.id.list);
        t=(TextView) findViewById(R.id.second_title);
        d=(TextView) findViewById(R.id.desc);
        tm=(TextView) findViewById(R.id.time);
        u=(TextView) findViewById(R.id.title);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
ph=(ImageView) findViewById(R.id.imageView1);
        Intent iip=getIntent();
         po=iip.getStringExtra("id");

ph.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
// Changing message text color
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Hey Whats Up", Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);

// Changing action button text color
        View sbView = snackbar.getView();
      //  EditText textView = (EditText) sbView.findViewById(android.support.design.R.id.snackbar_text);
       // textView.setTextColor(Color.YELLOW);
        LayoutInflater objLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.sample, null); // custom_snac_layout is your custom xml
        Snackbar.SnackbarLayout sLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        lo=(EditText)snackView.findViewById(R.id.text);
   ol=(ImageView)snackView.findViewById(R.id.Saves);

        sLayout.addView(snackView, 0);
        sLayout.setBackgroundColor(Color.WHITE);
        snackbar.show();

        ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     System.out.println("poil "+lo.getText().toString());
                dummy=new JSONObject();
                try {
                    dummy.put("Description",lo.getText().toString());
                    dummy.put("id",po);
                    dummy.put("user","com_user");
                    HttpPostRequest tm=new HttpPostRequest();
                    tm.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
});

        AsyncTaskRunner1 rt=new AsyncTaskRunner1();
        rt.execute(po);
    }

    private class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            String jsonString = callURL("http://54.68.72.28:3001/comments/view/"+params[0]);
            try {
               // JSONArray jsonArray = new JSONArray(jsonString);
                //int  count=0;
                //count = jsonArray.length(); // get totalCount of all jsonObjects

                //System.out.println("open_pdf"+count);
                JSONObject qw=new JSONObject(jsonString);

                JSONArray jsonArray = qw.getJSONArray("posts");

                JSONArray jsonArray1 = qw.getJSONArray("comments");

                               System.out.println("coln "+jsonArray.getJSONObject(0));

                //   System.out.println("cdmc "+qw.getJSONArray("posts"));

               // JSONArray jsonArray1 = new JSONArray(qw.getJSONArray("posts"));
//                System.out.println("coln "+jsonArray1.getJSONObject(0));

            //   JSONObject jsonObject = jsonArray1.getJSONObject(0);  // get jsonObject @ i position
///                System.out.println("cdmc "+jsonObject.get("Name"));

JSONObject jsonObject=jsonArray.getJSONObject(0);

                title= (String) jsonObject.get("Name");
                desc= (String) jsonObject.get("Description");
                photo= (String) jsonObject.get("photo");
                time= (String) jsonObject.get("timestamp");
                user=(String) jsonObject.get("user");
name=new String[jsonArray1.length()];
                tt=new String[jsonArray1.length()];
                com=new String[jsonArray1.length()];


                for(int i=0;i<jsonArray1.length();i++){
JSONObject obj1=jsonArray1.getJSONObject(0);
name[i]=obj1.getString("user");
                    tt[i]=obj1.getString("timestamp");
                    com[i]=obj1.getString("Description");

                }
                // pp.get(count);



               vm =new view_comments(post_view.this,R.layout.activity_view_comments,name,com,tt,post_view.this);

            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("trte "+e);

            }
            catch(Exception e){
                exceptionToBeThrown=e;
            }

            //  System.out.println("respoi "+resp);
            return resp;

        }





        @Override
        protected void onPreExecute() {
               progressDialog = ProgressDialog.show(post_view.this,"Please Wait...","Loading data");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //  finalResult.setText(text[0]);





        }
        protected  void onPostExecute(String a) {
             progressDialog.dismiss();
            //
            if (exceptionToBeThrown != null) {
                System.out.println("poop "+exceptionToBeThrown);
                AlertDialog alertDialog = new AlertDialog.Builder(post_view.this).create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                // Setting Dialog Title
                alertDialog.setTitle("Slug");

                // Setting Dialog Message
                alertDialog.setMessage("An Error Accured");

                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.tick);

                // Setting OK Button
                alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //  open_video.AsyncTaskRunner tr=new open_video.AsyncTaskRunner();
                        //tr.execute();

                    }
                });
                alertDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        edit.setText("An Error Accured...!");
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else {
                t.setText(title);
                u.setText(user);
                d.setText(desc);
                tm.setText(time);
System.out.println("fsd "+photo);
                ph.setImageBitmap(base64ToBitmap(photo));
                lv.setAdapter(vm);
               /* Intent myIntent = new Intent(getActivity(), youtube.class);
                myIntent.putExtra("video",a);
                getActivity().startActivity(myIntent);*/




            }
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    public static String callURL(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null){
                urlConn.setReadTimeout(15 * 1000);
                urlConn.setConnectTimeout(15*1000);}
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }
    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = "http://54.68.72.28:3001/comments/cmpost";
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
            dialog=new ProgressDialog(post_view.this);
            dialog.setMessage("Loading...Please Wait");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String result){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            AlertDialog alertDialog = new AlertDialog.Builder(
                    post_view.this).create();
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

                      Intent tr=new Intent(post_view.this,MainActivity.class);
                        startActivity(tr);
                        // Setting Dialog Message

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                super.onPostExecute(result);
            }
        }
    }
}
