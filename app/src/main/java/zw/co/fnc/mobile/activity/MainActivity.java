package zw.co.fnc.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Spinner province;
    private Spinner district;
    private Spinner ward;
    private Spinner period;
    private Button search;
    private QuarterlyMicroPlan quarterlyMicroPlan;
    private QuarterlyMicroPlan holder;
    private KeyProblem driver;
    private String expectedDateOfCompletion;
    private String actualDateOfCompletion;
    private String percentageDone;
    private Long actionRequired;
    private ArrayList<Long> strategyCategories;
    private ArrayList<Long> potentialChallenges;
    private ArrayList<Long> departmentCategories;
    private ArrayList<Long> resourcesNeeded;
    private ArrayList<InterventionCategory> intervention;
    ArrayAdapter<Province> provinceArrayAdapter;
    ArrayAdapter<District> districtArrayAdapter;
    ArrayAdapter<Ward> wardArrayAdapter;
    ArrayAdapter<Period> periodArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            syncAppData();
        }
        Intent intent = getIntent();
        holder = (QuarterlyMicroPlan) intent.getSerializableExtra("plan");
        driver = (KeyProblem) intent.getSerializableExtra("driver");
        expectedDateOfCompletion = intent.getStringExtra("expectedDateOfCompletion");
        actualDateOfCompletion = intent.getStringExtra("actualDateOfCompletion");
        percentageDone = intent.getStringExtra("percentageDone");
        actionRequired = intent.getLongExtra("actionRequired", 0L);
        intervention = (ArrayList<InterventionCategory>) intent.getSerializableExtra("intervention");
        resourcesNeeded = (ArrayList<Long>) intent.getSerializableExtra("resourcesNeeded");
        departmentCategories = (ArrayList<Long>) intent.getSerializableExtra("departmentCategories");
        strategyCategories = (ArrayList<Long>) intent.getSerializableExtra("strategyCategories");
        potentialChallenges = (ArrayList<Long>) intent.getSerializableExtra("potentialChallenges");
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        ward = (Spinner) findViewById(R.id.ward);
        period = (Spinner) findViewById(R.id.period);
        search = (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(this);
        provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, District.getByProvince((Province) province.getSelectedItem()));
                districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtArrayAdapter);
                districtArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wardArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, Ward.getByDistrict((District) district.getSelectedItem()));
                wardArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ward.setAdapter(wardArrayAdapter);
                wardArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        periodArrayAdapter = new ArrayAdapter<Period>(this, R.layout.simple_spinner_item, Period.getAll());
        periodArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period.setAdapter(periodArrayAdapter);
        if(holder != null){
            int i = 0;
            Log.d("Period", holder.period.name);
            for(Period p : Period.getAll()){
                if(holder.period.name != null && holder.period.name.equals(((Period) period.getItemAtPosition(i)).name)){
                    period.setSelection(i);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("FNC Mobile:: Search/Create Ward Action Plan"));
        }else{
            holder = new QuarterlyMicroPlan();
            setSupportActionBar(createToolBar("FNC Mobile:: Search/Create Ward Action Plan"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(resourcesNeeded != null){
            for(Long i : resourcesNeeded){
                Log.d("Main", "ID: " + i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                //onExit();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void updateView(){
        provinceArrayAdapter.clear();
        provinceArrayAdapter.addAll(Province.getAll());
        provinceArrayAdapter.notifyDataSetChanged();
        periodArrayAdapter.clear();
        periodArrayAdapter.addAll(Period.getAll());
        periodArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == search.getId()){
            if(validate()){
                quarterlyMicroPlan = QuarterlyMicroPlan.getByPeriodAndWard((Period) period.getSelectedItem(), (Ward) ward.getSelectedItem());
                if(quarterlyMicroPlan == null){
                    Intent intent = new Intent(MainActivity.this, KeyProblemActivity.class);
                    intent.putExtra("district", ((District) district.getSelectedItem()).getId());
                    intent.putExtra("ward", ((Ward) ward.getSelectedItem()).getId());
                    intent.putExtra("period", ((Period) period.getSelectedItem()).getId());
                    holder.period = (Period) period.getSelectedItem();
                    holder.district = (District) district.getSelectedItem();
                    holder.ward = (Ward) ward.getSelectedItem();
                    intent.putExtra("plan", holder);
                    intent.putExtra("driver", driver);
                    intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
                    intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
                    intent.putExtra("percentageDone", percentageDone);
                    intent.putExtra("actionRequired", actionRequired);
                    intent.putExtra("potentialChallenges", potentialChallenges);
                    intent.putExtra("strategyCategories", strategyCategories);
                    intent.putExtra("departmentCategories", departmentCategories);
                    intent.putExtra("resourcesNeeded", resourcesNeeded);
                    intent.putExtra("intervention", intervention);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(MainActivity.this, LoadActionPlanActivity.class);
                    intent.putExtra("microPlan", quarterlyMicroPlan.getId());
                    startActivity(intent);
                    finish();
                }
            }

        }

    }

    private boolean validate(){
        boolean isValid = true;
        if(province.getSelectedItem() == null){
            AppUtil.createShortNotification(this, "Please select province");
            isValid = false;
        }

        if(district.getSelectedItem() == null){
            AppUtil.createShortNotification(this, "Please select district");
            isValid = false;
        }

        if(ward.getSelectedItem() == null){
            AppUtil.createShortNotification(this, "Please select ward");
            isValid = false;
        }

        if(period.getSelectedItem() == null){
            AppUtil.createShortNotification(this, "Please select period");
            isValid = false;
        }
        return isValid;
    }
}
