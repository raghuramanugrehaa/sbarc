package info.androidhive.navigationdrawer.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.new_post;
import info.androidhive.navigationdrawer.activity.youtube;
import info.androidhive.navigationdrawer.other.OnLoadMoreListener;
import info.androidhive.navigationdrawer.other.community_home_adapter;
import info.androidhive.navigationdrawer.other.community_home_helper;


public class NotificationsFragment extends Fragment {
    private List<community_home_helper> contacts;
    private community_home_adapter contactAdapter;
    private Random random;
    private Exception exceptionToBeThrown;
    Context cont;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myIntent = new Intent(getActivity(),new_post.class);
        getActivity().startActivity(myIntent);
    }
});
        contacts = new ArrayList<>();
        random = new Random();
        AsyncTaskRunner1 rt=new AsyncTaskRunner1();
        rt.execute();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter = new community_home_adapter(recyclerView, contacts, getActivity());
      //set dummy data
  /*      for (int i = 0; i < 10; i++) {
            community_home_helper contact = new community_home_helper();
            contact.setTitle(phoneNumberGenerating());
            contact.setUser("DevExchanges" + i + "@gmail.com");
            contacts.add(contact);
        }*/
System.out.println("full che"+contacts.toString());
        //find view by id and attaching adapter for the RecyclerView




        //set load more listener for the RecyclerView adapter
        contactAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (contacts.size() <= 20) {
                    contacts.add(null);
                    contactAdapter.notifyItemInserted(contacts.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            contacts.remove(contacts.size() - 1);
                            contactAdapter.notifyItemRemoved(contacts.size());

                            //Generating more data
                            int index = contacts.size();
                            int end = index + 10;
                            for (int i = index; i < end; i++) {
                                community_home_helper contact = new community_home_helper();
                             /*   contact.setPhone(phoneNumberGenerating());
                                contact.setEmail("DevExchanges" + i + "@gmail.com");
                                contacts.add(contact);*/
                            }
                            contactAdapter.notifyDataSetChanged();
                            contactAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;

    }

    private String phoneNumberGenerating() {
        int low = 100000000;
        int high = 999999999;
        int randomNumber = random.nextInt(high - low) + low;

        return "0" + randomNumber;
    }


    private class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            String jsonString = callURL("http://54.68.72.28:3001/comments/posts");
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
               int  count=0;
                count = jsonArray.length(); // get totalCount of all jsonObjects

                System.out.println("open_pdf"+count);
                // pp.get(count);



                for(int i=0 ; i< count; i++){   // iterate through jsonArray
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                    ////  System.out.println("jsonObject " + i + ": " + jsonObject.getString("Name"));
                    community_home_helper contact = new community_home_helper();
                 //   System.out.println("minp "+jsonObject.getString("user"));
                    contact.setTitle(jsonObject.getString("Name"));
                    contact.setDesc(jsonObject.getString("Description"));
                    contact.setUser(jsonObject.getString("user"));
                    contact.setimage(jsonObject.getString("photo"));
                    contact.setId(jsonObject.getString("_id"));

                    contacts.add(contact);
                }


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
         //   progressDialog = ProgressDialog.show(getActivity(),"Please Wait...","Loading data");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //  finalResult.setText(text[0]);


        }
        protected  void onPostExecute(String a) {
          //  progressDialog.dismiss();
            //
            if (exceptionToBeThrown != null) {
                System.out.println("poop "+exceptionToBeThrown);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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
               /* Intent myIntent = new Intent(getActivity(), youtube.class);
                myIntent.putExtra("video",a);
                getActivity().startActivity(myIntent);*/



                recyclerView.setAdapter(contactAdapter);
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
    }




