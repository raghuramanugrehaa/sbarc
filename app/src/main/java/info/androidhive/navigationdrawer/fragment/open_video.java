package info.androidhive.navigationdrawer.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.youtube;
import info.androidhive.navigationdrawer.other.support_open_pdf;
import info.androidhive.navigationdrawer.other.support_open_video;

import static android.provider.UserDictionary.AUTHORITY;


public class open_video extends Fragment implements SearchView.OnQueryTextListener {
    public static int  count =0;
    private Exception exceptionToBeThrown;
    TextView ui_hot;
    private ArrayList<String> stringArrayList;
    private ArrayList<String> stringArrayimage;
    TextView edit;
    support_open_video p;
    private TextView mCounter;
    private int count1=0;
    Context cont;
    public open_video() {
    }
    String[] name;
    String[] price;
    String[] TR;
    String[] qauntity;
    String[] img;
    ListView lv;
    TextView textView;
    ImageView imh;
    //private int count = 2;
    String i1;
    //   listView.setAdapter(p);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final   View rootView = inflater.inflate(R.layout.fragment_open_video, container, false);

        final SharedPreferences frag= getActivity().getSharedPreferences("value", 0);

        //setHasOptionsMenu(false);
        setHasOptionsMenu(true);
        //edit.setVisibility(View.INVISIBLE);
        //edit.setText("");


        this.cont=this.getActivity();
        Bundle bundle = this.getArguments();
        String myValue = frag.getString("item",null);
        this.i1=myValue;
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.mobileArray, android.R.layout.simple_list_item_1);
        getActivity().setTitle(myValue);
//        SearchView toolbarTextView  = (SearchView) ((MainActivity) this.getActivity()).findViewById(R.id.action_search);
        //toolbarTextView.setText("Hello");



        lv = (ListView) rootView.findViewById  (R.id.listView2);
        if(isOnline()) {
            try {
                AsyncTaskRunner myTask = new AsyncTaskRunner();
                myTask.execute();
            }
            catch (Exception e){}
        }
        else{
            edit.setText("No..! Internet Connection");
        }

        System.out.println("yooo "+count);



        lv.setAdapter(p);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = lv.getItemAtPosition(i).toString().trim();
                System.out.println("clickef "+text);

                AsyncTaskRunner1 tr=new AsyncTaskRunner1();
                tr.execute(text);
            }
        });


        // listView.setAdapter(new ListviewContactAdapter(getActivity(), listContact));

        return rootView;
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getActivity(), "No Internet connection!", Toast.LENGTH_LONG).show();
            edit.setText("No..! Internet Connection");
            return false;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("tap");
        String text = newText.toString().toLowerCase(Locale.getDefault());
        //p.filter(text);

        if (TextUtils.isEmpty(text)) {
            p.filter("");
            lv.clearTextFilter();
        } else {
            // lv.setFilterText(text);
            p.filter(newText);
            //  lv.setAdapter(p);
        }
        return true;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            String jsonString = callURL("http://54.68.72.28:3001/identity/videonames/"+i1);
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                count=0;
                count = jsonArray.length(); // get totalCount of all jsonObjects

                System.out.println("open_pdf"+count);
                // pp.get(count);
                stringArrayList = new ArrayList<>();
                stringArrayimage = new ArrayList<>();
                name=new String[count];


                for(int i=0 ; i< count; i++){   // iterate through jsonArray
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    //  System.out.println("jsonObject " + i + ": " + jsonObject.getString("Name"));
                    name[i]=jsonObject.getString("Name");
                    stringArrayList.add(jsonObject.getString("Name"));
                    System.out.println("inside_open"+name[i]);
                }

                p=new support_open_video(getActivity(),R.layout.support_open_video,stringArrayList,name,open_video.this);

//
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch(Exception e){
                exceptionToBeThrown=e;
            }

            //  System.out.println("respoi "+resp);
            return resp;

        }





        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),"Please Wait","loading data");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //  finalResult.setText(text[0]);


        }
        protected  void onPostExecute(String a) {
            progressDialog.dismiss();
            //
            if (exceptionToBeThrown != null) {

                AlertDialog alertDialog = new AlertDialog.Builder(cont).create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                // Setting Dialog Title
                alertDialog.setTitle("SRT Crabs");

                // Setting Dialog Message
                alertDialog.setMessage("An Error Accured");

                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.tick);

                // Setting OK Button
                alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTaskRunner tr=new AsyncTaskRunner();
                        tr.execute();

                    }
                });
                alertDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        edit.setText("An Error Accured...!");
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else {
                System.out.println("mein " + count);
//int l=Integer.parseInt(a);
                String kl = count + "";

                //  if(count.equ)
                //    edit.setText("Sorry..! we dont have any item presently to serve you.. :-(");

                lv.setAdapter(p);
                //kl= lv.getAdapter().getCount()+"";

                if (count == 0)
//                    edit.setText("Sorry..! we dont have any item presently to serve you.. :-(");

                    count = 0;
            }
        }
    }



    private class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            String jsonString = callURL("http://54.68.72.28:3001/identity/video/"+params[0]);
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                count=0;
                JSONObject ju=jsonArray.getJSONObject(0);
                String m=ju.getString("video_id");


resp=m;

System.out.println("Video_id "+m);


              //SYs  String ff=ju.getString("file");
                // p=new support_open_pdf(getActivity(),R.layout.support_open_pdfs,stringArrayList,name,open_pdf.this);

//
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch(Exception e){
                exceptionToBeThrown=e;
                System.out.println("trt "+e);
            }

            //  System.out.println("respoi "+resp);
            return resp;

        }





        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),"Please Wait...","Loading data");
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
                AlertDialog alertDialog = new AlertDialog.Builder(cont).create();
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
                        AsyncTaskRunner tr=new AsyncTaskRunner();
                        tr.execute();

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
                Intent myIntent = new Intent(getActivity(), youtube.class);
                myIntent.putExtra("video",a);
                getActivity().startActivity(myIntent);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // menu.findItem(R.id.action_search).setVisible(false);
        //  getActivity().invalidateOptionsMenu();

        super.onCreateOptionsMenu(menu, inflater);
    }


      /* SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // System.out.println("search query submit");
                if (TextUtils.isEmpty(query)) {
                    lv.clearTextFilter();
                } else {
                    lv.setFilterText(query.toString().trim());

                }
                return true;
               // return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                String text = newText.toString().toLowerCase(Locale.getDefault());
                //p.filter(text);

               if (TextUtils.isEmpty(text)) {
                   p.filter("");
                   lv.clearTextFilter();
                } else {
                   // lv.setFilterText(text);
                   p.filter(newText);
                 //  lv.setAdapter(p);
                }
                return true;
            }

        });

return ;*/




}
