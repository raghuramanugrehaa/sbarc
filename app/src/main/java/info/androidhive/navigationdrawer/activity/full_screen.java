package info.androidhive.navigationdrawer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;

import info.androidhive.navigationdrawer.R;
public class full_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        Intent i=getIntent();
        String path=i.getStringExtra("image");
        String name=i.getStringExtra("title");
        String loc=i.getStringExtra("loc");
        TextView n=(TextView) findViewById(R.id.name);
        TextView l=(TextView) findViewById(R.id.location);
n.setText(name);
        l.setText(loc);
        System.out.println("full_check "+path);
        String replaceString=path.replace("file://","");
        String replaceString1=replaceString.replace("%20"," ");
        System.out.println("ghj "+replaceString1);
        File imgFile = new File(replaceString1);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        ImageView k=(ImageView)findViewById(R.id.test_image);
        k.setImageBitmap(myBitmap);

    }
}
