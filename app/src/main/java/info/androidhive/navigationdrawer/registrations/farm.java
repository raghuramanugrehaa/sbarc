package info.androidhive.navigationdrawer.registrations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.navigationdrawer.R;


public class farm extends AppCompatActivity {

    JSONObject obj;
    TextView name, email, phn;
    EditText mobile_num,occupation,capacity;
    EditText fis_acres, fish_years, mudcrab_acres, mudcrab_years, shrimp_acres, shrimp_years;
    EditText total_farming_area, ownd_in_acres, leased_in_acres;
    EditText total_farming_num, owned_seed_num, sponcerd_seed_num;
    EditText village, mandal, district, state, pincode;
    EditText min_salinity, max_salinity;

    Spinner select_state, select_water_type, select_moter_type, select_occupation, farming_capcity;
    Button submit;
    String st;
    ImageView user_img;
    RadioGroup radioGroup;
    RadioButton seeds;
    RadioButton bags;
    String tempVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        obj=new JSONObject();
        mobile_num= (EditText)findViewById(R.id.et_mobileno);
        occupation=(EditText) findViewById(R.id.occupation) ;
        capacity=(EditText) findViewById(R.id.farming_capacity);
        //   org_ocupation= (EditText)findViewById(R.id.et_occupation);
        select_state=(Spinner) findViewById(R.id.state1);
        fis_acres= (EditText)findViewById(R.id.et_fc_acres);
        fish_years= (EditText)findViewById(R.id.et_fc_years);
        mudcrab_acres= (EditText)findViewById(R.id.et_md_acres);
        mudcrab_years= (EditText)findViewById(R.id.et_md_years);
        shrimp_acres= (EditText)findViewById(R.id.et_sh_acres);
        shrimp_years= (EditText)findViewById(R.id.et_sh_years);
        total_farming_area= (EditText)findViewById(R.id.et_total_farmingacres);
        ownd_in_acres= (EditText)findViewById(R.id.et_ownedacres);
        leased_in_acres= (EditText)findViewById(R.id.et_leasedacres);
        total_farming_num= (EditText)findViewById(R.id.et_total_farmingnumber);
        total_farming_num.setFocusable(false);
        total_farming_num.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        total_farming_num.setClickable(false);


        owned_seed_num= (EditText)findViewById(R.id.et_ownseednumbner);
        owned_seed_num.setFocusable(false);
        owned_seed_num.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        owned_seed_num.setClickable(false);
        sponcerd_seed_num= (EditText)findViewById(R.id.et_sponseredseednumber);
        sponcerd_seed_num.setFocusable(false);
        sponcerd_seed_num.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        sponcerd_seed_num.setClickable(false);
        village= (EditText)findViewById(R.id.et_village);
        mandal= (EditText)findViewById(R.id.et_mandal);
        district= (EditText)findViewById(R.id.et_district);
        //  state= (EditText)findViewById(R.id.et_state);
        pincode= (EditText)findViewById(R.id.et_pincode);
        min_salinity= (EditText)findViewById(R.id.et_minsalinity);
        max_salinity= (EditText)findViewById(R.id.et_maxsalinity);
        select_water_type= (Spinner)findViewById(R.id.sp_water_types);
        select_moter_type= (Spinner)findViewById(R.id.sp_motor_types);
        //select_occupation = (Spinner)findViewById(R.id.et_occupation);
        //take_farmer_photo = (Button)findViewById(R.id.bt_farmerphoto);
        // take_id = (Button)findViewById(R.id.bt_uploadgovid);
        submit = (Button)findViewById(R.id.bt_submitform);
        radioGroup =(RadioGroup)findViewById(R.id.id_seed_bag);
        seeds =(RadioButton)findViewById(R.id.id_num_seeds);
        bags = (RadioButton)findViewById(R.id.id_num_bags);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId) {
                    case R.id.id_num_seeds:
                        total_farming_num.setHint("Total Number Of Seeds");
                        owned_seed_num.setHint("Own Invested Seed Num");
                        sponcerd_seed_num.setHint("Sponcerd Seed Num");
                        total_farming_num.setFocusable(true);
                        total_farming_num.setFocusableInTouchMode(true);
                        total_farming_num.setClickable(true);
                        owned_seed_num.setFocusable(true);
                        owned_seed_num.setFocusableInTouchMode(true);
                        owned_seed_num.setClickable(true);
                        sponcerd_seed_num.setFocusable(true);
                        sponcerd_seed_num.setFocusableInTouchMode(true);
                        sponcerd_seed_num.setClickable(true);
                        tempVal = "seeds";
                        Log.d("madhuval", tempVal);

                        break;
                    case R.id.id_num_bags:
                        total_farming_num.setHint("Toatal Number od Bags");
                        owned_seed_num.setHint("Own Invested Bag Num");
                        sponcerd_seed_num.setHint("Sponcerd Seed Num");
                        total_farming_num.setFocusable(true);
                        total_farming_num.setFocusableInTouchMode(true);
                        total_farming_num.setClickable(true);
                        owned_seed_num.setFocusable(true);
                        owned_seed_num.setFocusableInTouchMode(true);
                        owned_seed_num.setClickable(true);
                        sponcerd_seed_num.setFocusable(true);
                        sponcerd_seed_num.setFocusableInTouchMode(true);
                        sponcerd_seed_num.setClickable(true);
                        tempVal= "bags";
                        Log.d("madhuval", tempVal);
                        break;
//                    case R.id.radio3:
//                        RadioButton value = Integer.parseInt(((RadioButton) findViewById(R.id.radio3).getText()) * 3);
//                        break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // st =select_state.getSelectedItem().toString();
//                  int pos =s.getSelectedItemPosition()
                if(mobile_num.getText().toString().isEmpty()){
                    mobile_num.setError("This Field Required");
                    showToast("Some fields are incomplete");
//                  }else if(org_ocupation.getText().toString().isEmpty()){
//                      org_ocupation.setError("This Field Required");
//                      showToast("Some fields are incomplete");
                }else if(fis_acres.getText().toString().isEmpty()){
                    fis_acres.setError("This Field Required");
                    showToast("Some fields are incomplete");
                } else if(fish_years.getText().toString().isEmpty()){
                    fish_years.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(mudcrab_acres.getText().toString().isEmpty()){
                    mudcrab_acres.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(mudcrab_years.getText().toString().isEmpty()){
                    mudcrab_years.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(shrimp_acres.getText().toString().isEmpty()){
                    shrimp_acres.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(shrimp_years.getText().toString().isEmpty()){
                    shrimp_years.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(total_farming_area.getText().toString().isEmpty()){
                    total_farming_area.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(ownd_in_acres.getText().toString().isEmpty()){
                    ownd_in_acres.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(leased_in_acres.getText().toString().isEmpty()){
                    leased_in_acres.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(total_farming_num.getText().toString().isEmpty()){
                    total_farming_num.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(owned_seed_num.getText().toString().isEmpty()){
                    owned_seed_num.setError("This Field Required");
                    showToast("Some fields are incomplete");
                } else if(sponcerd_seed_num.getText().toString().isEmpty()){
                    sponcerd_seed_num.setError("This Field Required");
                    showToast("Some fields are incomplete");
                } else if(village.getText().toString().isEmpty()){
                    village.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(mandal.getText().toString().isEmpty()){
                    mandal.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(district.getText().toString().isEmpty()){
                    district.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(pincode.getText().toString().isEmpty()){
                    pincode.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(min_salinity.getText().toString().isEmpty()){
                    min_salinity.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(max_salinity.getText().toString().isEmpty()) {
                    max_salinity.setError("This Field Required");
                    showToast("Some fields are incomplete");
                }else if(select_water_type.getSelectedItemPosition()==0) {
                    showToast("Select water type");
                }else if(select_moter_type.getSelectedItemPosition()==0) {
                    showToast("Select moter type");
                }else if(select_state.getItemAtPosition(0).toString().equals("Select Water Type")) {
                    showToast("Select water type");
//                  }else if(select_state.getItemAtPosition(0).toString().equals("Select Motor Type")) {
//                      showToast("Select moter type");


                } else{


                    try {
                        obj.put("alt_mobile",mobile_num.getText().toString());


                        obj.put("occupation",occupation.getText().toString());
                        obj.put("farm_capacity", capacity.getText().toString());
                        obj.put("fish_acreas",fis_acres.getText().toString());
                        obj.put("fish_years", fish_years.getText().toString());
                        obj.put("mudcrab_acres", mudcrab_acres.getText().toString());
                        obj.put("mudcrab_years" , mudcrab_years.getText().toString());
                        obj.put("shr_acrs",  shrimp_acres.getText().toString());
                        obj.put("shr_years",  shrimp_years.getText().toString());
                        obj.put("total_farming_area",   total_farming_area.getText().toString());
                        obj.put("own_acres", ownd_in_acres.getText().toString());
                        obj.put("leased_area",  leased_in_acres.getText().toString());
                        obj.put("total_famrming_bags",  total_farming_num.getText().toString());
                        obj.put("own_seed_num", owned_seed_num.getText().toString());//add bags for above and beloew
                        obj.put("sponsered_seed_num" , sponcerd_seed_num.getText().toString());
                        obj.put("village", village.getText().toString());
                        obj.put("mandal",mandal.getText().toString());
                        obj.put("district",district.getText().toString());
                        obj.put("state", select_state.getSelectedItem().toString());
                        obj.put("pincode", pincode.getText().toString());
                        obj.put("min_salinity", min_salinity.getText().toString());
                        obj.put("max_salinity" ,max_salinity.getText().toString());
                        obj.put("selected_water_type",select_water_type.getSelectedItem().toString());
                        obj.put("select_motor_type",  select_moter_type.getSelectedItem().toString());
                        System.out.println("checking data"+obj.toString());
                        SharedPreferences.Editor editor = getSharedPreferences("collect", MODE_PRIVATE).edit();
                        editor.putString("farmer1",obj.toString());
                        editor.commit();
                        SharedPreferences prefs = getSharedPreferences("collect", MODE_PRIVATE);
                        Boolean sup = prefs.getBoolean("supplier",false);
                        if(sup){
                            Intent i=new Intent(farm.this,supplier.class);
                            startActivity(i);
                        }
                        else
                        {
                            Intent i=new Intent(farm.this,declaration.class);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }




            }

        });
    }
    public void showToast(String message) {
        Toast.makeText(farm.this, message, Toast.LENGTH_LONG).show();
    }


}
