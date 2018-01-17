package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.other.DBManager;


public class add_record extends Activity {
    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
String url="";
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Intent ty=getIntent();
        url=ty.getStringExtra("url");
        System.out.println("kana "+url);

        subjectEditText = (EditText) findViewById(R.id.subject_edittext);
        descEditText = (EditText) findViewById(R.id.description_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = subjectEditText.getText().toString();
                final String desc = descEditText.getText().toString();
                dbManager.insert(name, desc, url);

                Intent main = new Intent(add_record.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
               // break;*/
            }
        });
    }


}
