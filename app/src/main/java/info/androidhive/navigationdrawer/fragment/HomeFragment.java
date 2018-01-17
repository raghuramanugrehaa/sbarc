package info.androidhive.navigationdrawer.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Hashtable;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.full_screen;
import info.androidhive.navigationdrawer.other.DBManager;
import info.androidhive.navigationdrawer.other.DatabaseHelper;
import info.androidhive.navigationdrawer.other.support_image;


public class HomeFragment extends Fragment {

String p[];
support_image sum;
    String d[];
    String t[];
    Hashtable<String,String> hm=new Hashtable<String,String>();
    Hashtable<String,String> hm1=new Hashtable<String,String>();

    private ArrayList<String> stringArrayList;
    public HomeFragment() {
    }

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.Location, DatabaseHelper.DESC,DatabaseHelper.Path };

   // final int[] to = new int[] { R.id.id, R.id.title, R.id.desc,R.id.path };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        stringArrayList = new ArrayList<>();
        ImageView km=(ImageView)  rootView.findViewById(R.id.imageView1);
TextView txt=(TextView) rootView.findViewById(R.id.title);
        dbManager = new DBManager(getActivity());
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        int y=cursor.getCount();
        if(y!=0) {
            km.setVisibility(View.GONE);
            txt.setVisibility(View.GONE);
        }
p=new String[cursor.getCount()];
        d=new String[cursor.getCount()];
        t=new String[cursor.getCount()];
        listView = (ListView) rootView.findViewById(R.id.listView2);
      //  listView.setEmptyView(rootView.findViewById(R.id.empty));
        int g=0;
        if (cursor.moveToFirst()) {
            do {
                System.out.println("formings "+cursor.getString(3));
                t[g]=cursor.getString(1).toString();
                System.out.println("collected "+t[g]);
                stringArrayList.add(t[g]+","+cursor.getString(0));
                d[g]=cursor.getString(2);
                p[g]=cursor.getString(3);
                hm.put(t[g],p[g]);
                hm1.put(t[g],d[g]);
g++;
                // get the data into array, or class variable
            } while (cursor.moveToNext());
        }
        sum=new support_image(getActivity(),R.layout.view_records,stringArrayList,t,d,p,HomeFragment.this);
        //adapter = new SimpleCursorAdapter(getActivity(), R.layout.view_records, cursor, from, to, 0);
        sum.notifyDataSetChanged();
        listView.setAdapter(sum);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = listView.getItemAtPosition(i).toString().trim();
                System.out.println("clickef "+   text);
                String r[]=text.split(",");
              /*  TextView idTextView = (TextView) view.findViewById(R.id.path);
                TextView name = (TextView) view.findViewById(R.id.title);
                TextView location = (TextView) view.findViewById(R.id.desc);
                String text = idTextView.getText().toString();
                String text1 = name.getText().toString();
                String text2 = location.getText().toString();
                System.out.println("totkm "+text1);
*/
                Intent myIntent = new Intent(getActivity(), full_screen.class);
                myIntent.putExtra("image",hm.get(r[0]));
                myIntent.putExtra("title",r[0]);
               myIntent.putExtra("loc",hm1.get(r[0]));
                getActivity().startActivity(myIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // menu.findItem(R.id.action_search).setVisible(false);
        //  getActivity().invalidateOptionsMenu();

        super.onCreateOptionsMenu(menu, inflater);
    }






}
