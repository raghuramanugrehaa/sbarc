package info.androidhive.navigationdrawer.other;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.fragment.PhotosFragment;
import info.androidhive.navigationdrawer.fragment.open_pdf;

/**
 * Created by hp on 12-12-2017.
 */
public class support_open_pdf extends ArrayAdapter<String> {
    String[] Name;
    LayoutInflater inflater;
    Context c;
    private List<String> friendList;
    private List<String> searchList;
    public support_open_pdf(Context context, int resource, List<String> names, String namesk[], open_pdf fragk) {
        super(context, resource, names);
        this.Name = namesk;
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
            convertView = inflater.inflate(R.layout.support_open_pdfs, null);
            hold.name = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(hold);


            hold.name.setText(Name[position]);

            System.out.println("jhg " + Name[position]);


        }
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