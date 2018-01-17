package info.androidhive.navigationdrawer.other;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.fragment.PhotosFragment;

/**
 * Created by hp on 12-12-2017.
 */
public class pdf_main extends ArrayAdapter<String> {
    String[] Name;
    LayoutInflater inflater;
    Context c;

    public pdf_main(Context context, int resource, List<String> names, String namesk[], PhotosFragment fragk) {
        super(context, resource, names);
        this.Name = namesk;
        this.c = context;

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
            convertView = inflater.inflate(R.layout.fragment_pdf, null);
            hold.name = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(hold);


            hold.name.setText(Name[position]);

            System.out.println("jhg " + Name[position]);


        }
        return convertView;


    }


}