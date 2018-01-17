package info.androidhive.navigationdrawer.registrations;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.R;


public class supplier extends AppCompatActivity {
    Button next;
    ImageButton btnAdd;
    public String cah[]=new String[100];
    ImageButton btnAdd1;
    EditText mobile,company,marks,phone1,phone2,pan,gst;
    EditText bankname,account,ifsc;
    JSONArray list = new JSONArray();
    JSONArray list1 = new JSONArray();
    Spinner category, paytems,shipmenttype,shipdays,rate;
    EditText mcg_a,mcg_y,mcg_w;
    JSONObject jhg=new JSONObject();
    JSONObject jhg1=new JSONObject();
    String cahe;
    String cahe1;
    EditText mcw_a,mcw_y,mcw_w;
    EditText rcl_a,rcl_y,rcl_w;
    JSONObject obj;
    JSONObject mcw;
    JSONObject rcl;
    JSONObject mcg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        obj=new JSONObject();
        mcw=new JSONObject();
        mcg=new JSONObject();
        rcl=new JSONObject();
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnAdd1 = (ImageButton) findViewById(R.id.btnAdd1);
        next=(Button) findViewById(R.id.bt_submitform);
        //form assign
        // mobile=(EditText) findViewById(R.id.et_mobileno);
        final MultiSelectionSpinner spinner=(MultiSelectionSpinner)findViewById(R.id.input1);

        List<String> listl = new ArrayList<String>();
        listl.add("Select Days");
        listl.add("Sunnday");
        listl.add("Monday");
        listl.add("Tuesday");
        listl.add("Wednesday");
        listl.add("Thursday");
        listl.add("Friday");
        listl.add("Saturday");
        spinner.setItems(listl);


        category=(Spinner) findViewById(R.id.cat);
        company=(EditText) findViewById(R.id.et_company_name);
        marks=(EditText) findViewById(R.id.marks);
        phone1=(EditText) findViewById(R.id.phone_1);
        phone2=(EditText) findViewById(R.id.phone_2);
        pan=(EditText) findViewById(R.id.pan);
        gst=(EditText) findViewById(R.id.gst);
        paytems=(Spinner) findViewById(R.id.pay_terms);

        bankname=(EditText) findViewById(R.id.bank_name);
        account=(EditText) findViewById(R.id.account_name);
        ifsc =(EditText) findViewById(R.id.ifsc);


        shipmenttype=(Spinner) findViewById(R.id.shipment_type);
        //  shipdays=(Spinner) findViewById(R.id.shipment_days);

        rate=(Spinner) findViewById(R.id.Ratecard);


        mcg_a=(EditText) findViewById(R.id.et_fc_acres);
        mcg_y=(EditText) findViewById(R.id.et_fc_years);
        mcg_w=(EditText) findViewById(R.id.et_fc_week);

        mcw_a=(EditText) findViewById(R.id.et_md_acres);
        mcw_y=(EditText) findViewById(R.id.et_md_years);
        mcw_w=(EditText) findViewById(R.id.et_md_per_week);

        rcl_a=(EditText) findViewById(R.id.et_sh_acres);
        rcl_y=(EditText) findViewById(R.id.et_sh_years);
        rcl_w=(EditText) findViewById(R.id.et_sh_week);
        // btnDisplay = (Button) findViewById(R.id.btnDisplay);

        MyLayoutOperation.add(this, btnAdd);
        MyLayoutOperation.add1(this, btnAdd1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    obj.put("company_name",company.getText().toString());
                    //    obj.put("mobile_company",mobile.getText().toString());
                    obj.put("marks",marks.getText().toString());
                    obj.put("phone_1",phone1.getText().toString());
                    obj.put("phone_2",phone2.getText().toString());
                    obj.put("pan",pan.getText().toString());
                    obj.put("gst",gst.getText().toString());
                    obj.put("occupation",category.getSelectedItem().toString());
                    obj.put("payterms",paytems.getSelectedItem().toString());
                    obj.put("bankname",bankname.getText().toString());
                    obj.put("account_number",account.getText().toString());
                    obj.put("ifsc",ifsc.getText().toString());
                    obj.put("shipment_type",shipmenttype.getSelectedItem().toString());
                    obj.put("shipment_terms",spinner.getSelectedStrings());
                    System.out.println("getting done"+spinner.getSelectedStrings());
                    obj.put("rate_card",rate.getSelectedItem().toString());

                    mcg.put("Turn_over",mcg_a.getText().toString());
                    mcg.put("Trading_from",mcg_y.getText().toString());
                    mcg.put("per_week",mcg_w.getText().toString());

                    mcw.put("Turn_over",mcw_a.getText().toString());
                    mcw.put("Trading_from",mcw_y.getText().toString());
                    mcw.put("per_week",mcw_w.getText().toString());

                    rcl.put("Turn_over",rcl_a.getText().toString());
                    rcl.put("Trading_from",rcl_y.getText().toString());
                    rcl.put("per_week",rcl_w.getText().toString());

                    obj.put("Mud_Crab_GL",mcg);
                    obj.put("Mud_Crab_Waters",mcw);
                    obj.put("Red_Crab_Live",rcl);

                    get(supplier.this);
                    get1(supplier.this);
                    // String replaceString=cahe.replace("null","");

                    //  String replaceString1=cahe1.replace("null","");
                    obj.put("chainers",list);
                    obj.put("sales",list1);
                    SharedPreferences.Editor editor = getSharedPreferences("collect", MODE_PRIVATE).edit();
                    editor.putString("supplier1",obj.toString());
                    editor.commit();
                    SharedPreferences prefs = getSharedPreferences("collect", MODE_PRIVATE);
                    Boolean sup = prefs.getBoolean("buyer",false);

                    if(sup){
                        Intent i=new Intent(supplier.this,buyer.class);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i=new Intent(supplier.this,declaration.class);
                        startActivity(i);
                    }
                    System.out.println("repeated "+obj.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
    public  void get(Activity activity) throws JSONException {

        LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);



        for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
        {
            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
            EditText edit = (EditText) innerLayout.findViewById(R.id.editDescricao);
            EditText edit1 = (EditText) innerLayout.findViewById(R.id.editDescricao1);
            EditText edit2 = (EditText) innerLayout.findViewById(R.id.editmb1);

            jhg.put("person",edit.getText().toString());
            jhg.put("village",edit1.getText().toString());
            jhg.put("Phone",edit2.getText().toString());
            list.put(jhg);

            //       cah[i]='{'+"person:"+edit.getText().toString()+','+"village:"+edit1.getText().toString()+','+"phone:"+edit2.getText().toString()+'}';
            //if(i<scrollViewlinerLayout.getChildCount()-1)
            //cahe+=edit.getText().toString()+"|"+edit1.getText().toString()+"-";
            //else
            // cahe+="person:"+edit.getText().toString()+",village:"+edit1.getText().toString();
            //cahe+=edit.getText().toString()+"|"+edit1.getText().toString();
            //cach.put("village",edit1.getText().toString());


        }



    }
    public  void get1(Activity activity) throws JSONException {

        LinearLayout scrollViewlinerLayout1 = (LinearLayout) activity.findViewById(R.id.linearLayoutForm1);


        cahe1="";
        for (int i = 0; i < scrollViewlinerLayout1.getChildCount(); i++)
        {
            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout1.getChildAt(i);
            EditText edit = (EditText) innerLayout.findViewById(R.id.editDes);
            EditText edit1 = (EditText) innerLayout.findViewById(R.id.editDes1);
            EditText edit2 = (EditText) innerLayout.findViewById(R.id.editmb1);

            jhg.put("person",edit.getText().toString());
            jhg.put("village",edit1.getText().toString());
            jhg.put("Phone",edit2.getText().toString());
            list1.put(jhg);

        }



    }
}
