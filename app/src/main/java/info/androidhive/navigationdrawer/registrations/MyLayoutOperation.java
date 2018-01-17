package info.androidhive.navigationdrawer.registrations;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.R;

public class MyLayoutOperation {

	public static void display(final Activity activity, Button btn)
	{
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
				
				java.util.ArrayList<String> msg = new ArrayList<String>();

				for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
				{
					LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
					EditText edit = (EditText) innerLayout.findViewById(R.id.editDescricao);
					EditText edit1 = (EditText) innerLayout.findViewById(R.id.editDescricao1);

					msg.add(edit.getText().toString()+'/'+edit1.getText().toString());

				}

				Toast t = Toast.makeText(activity.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT);
				t.show();
			}
		});
	}

	public static void add(final Activity activity, ImageButton btn)
	{
		final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);;

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.rowdetail, null);

				newView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

				ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
				btnRemove.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						linearLayoutForm.removeView(newView);
					}
				});

				linearLayoutForm.addView(newView);
			}
		});
	}


	public static void add1(final Activity activity, ImageButton btn)
	{
		final LinearLayout linearLayoutForm1 = (LinearLayout) activity.findViewById(R.id.linearLayoutForm1);;

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.rowdetails1, null);

				newView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

				ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
				btnRemove.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						linearLayoutForm1.removeView(newView);
					}
				});

				linearLayoutForm1.addView(newView);
			}
		});
	}
}
