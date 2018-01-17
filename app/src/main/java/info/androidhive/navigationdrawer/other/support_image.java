package info.androidhive.navigationdrawer.other;

/**
 * Created by hp on 28-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.full_screen;
import info.androidhive.navigationdrawer.fragment.HomeFragment;
import info.androidhive.navigationdrawer.fragment.PhotosFragment;
import info.androidhive.navigationdrawer.fragment.open_pdf;

/**
 * Created by hp on 12-12-2017.
 */
public class support_image extends ArrayAdapter<String> {
    String[] Name;
    String[] de;
    String[] pa;
    LayoutInflater inflater;
    Context c;
DBManager db;
    private List<String> friendList;
    private List<String> searchList;
    public support_image(Context context, int resource, List<String> names, String t[], String d[], String p[], HomeFragment fragk) {
        super(context, resource, names);
        this.Name = t;
        this.de=d;
        this.pa=p;
        this.c = context;
        this.friendList=names;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(friendList);

    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    public class viewholder {

        TextView name;
        TextView loc;
        ImageView img;
        ImageView del;



    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // SharedPreferences myprefs= getContext().getSharedPreferences("user", 0);
        // textView = (TextView) rootView.findViewById(R.id.count);

        viewholder hold = new viewholder();
        final viewholder hold1 = new viewholder();
        //  View view = ...;


        if (convertView == null) {
//

            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_records, null);
            hold.name = (TextView) convertView.findViewById(R.id.title);
            hold.img = (ImageView) convertView.findViewById(R.id.list_image);
            hold.loc = (TextView) convertView.findViewById(R.id.loc);
            hold.del=(ImageView) convertView.findViewById(R.id.deletes);

            convertView.setTag(hold);


            hold.name.setText(Name[position]);
            hold.loc.setText(de[position]);
            String path = pa[position];
            String replaceString = path.replace("file://", "");
            String replaceString1 = replaceString.replace("%20", " ");
            System.out.println("ghj " + replaceString1);
            File imgFile = new File(replaceString1);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            hold.img.setImageBitmap(myBitmap);
            System.out.println("jhg " + Name[position]);
        }
        else
            hold= (viewholder)convertView.getTag();
        //hold.img.setImageResource(R.drawable.delete);
        db = new DBManager(c);
        db.open();
        hold.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id[]=getItem(position).split(",");
                    db.delete(Long.parseLong(id[1]));
                    //Intent myIntent = new Intent(c, support_image.class);
                    Intent intent= new Intent(c, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    c.startActivity(intent);
                }
            });



        return convertView;


    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        friendList.clear();
        if (charText.length() == 0) {
            friendList.addAll(searchList);
        } else {
            for (String s : searchList) {
                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    friendList.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }
}