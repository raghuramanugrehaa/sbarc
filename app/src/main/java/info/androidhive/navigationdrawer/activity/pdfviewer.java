package info.androidhive.navigationdrawer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import info.androidhive.navigationdrawer.R;

import  com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;


public class pdfviewer extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        Intent r = getIntent();
        System.out.println("pfd " + r.getStringExtra("imageUri"));

        Uri myUri = Uri.parse(r.getStringExtra("imageUri"));
        String ffff = "/temp.pdf";
        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + ffff);

        System.out.println("file.exists() = " + file.exists());

        pdfView.fromFile(file)   .enableAnnotationRendering(true)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .password(null)
                .scrollHandle(null)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pdfView.enableAnnotationRendering(true);
                        pdfView.zoomTo(1);
                        pdfView.setPositionOffset(0);
                        pdfView.loadPages();
                    }
                })
                .load();



    }

}
