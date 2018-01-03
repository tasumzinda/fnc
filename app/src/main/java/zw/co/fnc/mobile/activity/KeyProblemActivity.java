package zw.co.fnc.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;

import java.util.ArrayList;

/**
 * Created by User on 3/8/2017.
 */
public class KeyProblemActivity extends BaseActivity implements View.OnClickListener{

    private Spinner driverOfStunting;
    private ListView indicator;
    private ListView interventionCategory;
    private Button save;
    ArrayAdapter<Indicator> indicatorArrayAdapter;
    ArrayAdapter<InterventionCategory> interventionCategoryArrayAdapter;
    private Long district;
    private Long ward;
    private Long period;
    private Long microPlan;
    private QuarterlyMicroPlan holder;
    private KeyProblem driver;
    private ArrayList<Long> departmentCategories;
    private ArrayList<Long> resourcesNeeded;
    private ArrayList<Long> strategyCategories;
    private ArrayList<Long> potentialChallenges;
    private String expectedDateOfCompletion;
    private String actualDateOfCompletion;
    private String percentageDone;
    private Long actionRequired;
    private ArrayList<InterventionCategory> intervention;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.driver_of_stunting);
        driverOfStunting = (Spinner) findViewById(R.id.driverOfStunting);
        indicator = (ListView) findViewById(R.id.indicator);
        interventionCategory = (ListView) findViewById(R.id.intervention);
        save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        indicatorArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Indicator.getAll());
        indicator.setAdapter(indicatorArrayAdapter);
        interventionCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, InterventionCategory.getAll());
        interventionCategory.setAdapter(interventionCategoryArrayAdapter);
        ArrayAdapter<KeyProblemCategory> driverOfStuntingCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, KeyProblemCategory.getAll());
        driverOfStuntingCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverOfStunting.setAdapter(driverOfStuntingCategoryArrayAdapter);
        indicator.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        indicator.setItemsCanFocus(false);
        interventionCategory.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        interventionCategory.setItemsCanFocus(false);
        Intent intent = getIntent();
        holder = (QuarterlyMicroPlan) intent.getSerializableExtra("plan");
        driver = (KeyProblem) intent.getSerializableExtra("driver");
        district = intent.getLongExtra("district", 0L);
        ward = intent.getLongExtra("ward", 0L);
        period = intent.getLongExtra("period", 0L);
        microPlan = intent.getLongExtra("microPlan", 0L);
        expectedDateOfCompletion = intent.getStringExtra("expectedDateOfCompletion");
        actualDateOfCompletion = intent.getStringExtra("actualDateOfCompletion");
        percentageDone = intent.getStringExtra("percentageDone");
        actionRequired = intent.getLongExtra("actionRequired", 0L);
        intervention = (ArrayList<InterventionCategory>) intent.getSerializableExtra("intervention");
        strategyCategories = (ArrayList<Long>) intent.getSerializableExtra("strategyCategories");
        potentialChallenges = (ArrayList<Long>) intent.getSerializableExtra("potentialChallenges");
        resourcesNeeded = (ArrayList<Long>) intent.getSerializableExtra("resourcesNeeded");
        departmentCategories = (ArrayList<Long>) intent.getSerializableExtra("departmentCategories");
        driverOfStunting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                interventionCategoryArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.check_box_item, InterventionCategory.findByKeyProblemCategory((KeyProblemCategory) driverOfStunting.getSelectedItem()));
                interventionCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                interventionCategory.setAdapter(interventionCategoryArrayAdapter);
                interventionCategoryArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Log.d("Test", String.valueOf(microPlan != null));
        if(driver != null){
            int i = 0;
            for(KeyProblemCategory d : KeyProblemCategory.getAll()){
                if(driver.keyProblemCategory.name.equals(((KeyProblemCategory) driverOfStunting.getItemAtPosition(i)).name)){
                    driverOfStunting.setSelection(i);
                    break;
                }
                i++;
            }
            ArrayList<Indicator> indicators = (ArrayList<Indicator>) driver.indicators;
            ArrayList<String> list = new ArrayList<>();
            for(Indicator s : indicators){
                list.add(s.name);
            }
            int indicatorCount = indicatorArrayAdapter.getCount();
            for(i = 0; i < indicatorCount; i++){
                Indicator current = indicatorArrayAdapter.getItem(i);
                if(list.contains(current.name)){
                    indicator.setItemChecked(i, true);
                }
            }
            ArrayList<InterventionCategory> interventions = (ArrayList<InterventionCategory>) driver.interventions;
            ArrayList<String> list1 = new ArrayList<>();
            for(InterventionCategory s : interventions){
                list1.add(s.name);
            }
            int interventionCount = indicatorArrayAdapter.getCount();
            for(i = 0; i < interventionCount; i++){
                InterventionCategory current = interventionCategoryArrayAdapter.getItem(i);
                if(list1.contains(current.name)){
                    interventionCategory.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("FNC Mobile:: Create/Edit Driver Of Stunting For " + District.findById(district).name + " " + Ward.findById(ward).name + " " + Period.findById(period).name + " Action Plan"));
        }else if(microPlan != 0L){
            driver = new KeyProblem();
            QuarterlyMicroPlan m = QuarterlyMicroPlan.findById(microPlan);
            Log.d("Plan", AppUtil.createGson().toJson(m));
            setSupportActionBar(createToolBar("FNC Mobile:: Create/Edit Driver Of Stunting For " + m.ward.district.name + " " + m.ward.name + " " + m.period.name + " Action Plan"));
        }else{
            driver = new KeyProblem();
            setSupportActionBar(createToolBar("FNC Mobile:: Create/Edit Driver Of Stunting For " + District.findById(district).name + " " + Ward.findById(ward).name + " " + Period.findById(period).name + " Action Plan"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private ArrayList<Indicator> getIndicators(){
        ArrayList<Indicator> a = new ArrayList<>();
        for(int i = 0; i < indicator.getCount(); i++){
            if(indicator.isItemChecked(i)){
                a.add(indicatorArrayAdapter.getItem(i));
            }else{
                a.remove(indicatorArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private ArrayList<InterventionCategory> getInterventions(){
        ArrayList<InterventionCategory> a = new ArrayList<>();
        for(int i = 0; i < interventionCategory.getCount(); i++){
            if(interventionCategory.isItemChecked(i)){
                a.add(interventionCategoryArrayAdapter.getItem(i));
            }else{
                a.remove(interventionCategoryArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    public void onBackPressed(){
        Intent intent = new Intent(KeyProblemActivity.this, MainActivity.class);
        driver.keyProblemCategory = (KeyProblemCategory) driverOfStunting.getSelectedItem();
        driver.interventions = getInterventions();
        driver.indicators = getIndicators();
        intent.putExtra("driver", driver);
        intent.putExtra("plan", holder);
        intent.putExtra("departmentCategories", departmentCategories);
        intent.putExtra("resourcesNeeded", resourcesNeeded);
        intent.putExtra("potentialChallenges", potentialChallenges);
        intent.putExtra("strategyCategories", strategyCategories);
        intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
        intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
        intent.putExtra("percentageDone", percentageDone);
        intent.putExtra("actionRequired", actionRequired);
        intent.putExtra("intervention", intervention);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onClick(View view) {
        if(validate()){
            Intent intent = new Intent(KeyProblemActivity.this, ActionPlanItemActivity1.class);
            intent.putExtra("driverOfStuntingCategory", ((KeyProblemCategory) driverOfStunting.getSelectedItem()).getId());
            if(microPlan == 0L){
                intent.putExtra("district", district);
                intent.putExtra("ward", ward);
                intent.putExtra("period", period);
            }else{
                intent.putExtra("microPlan", microPlan);
            }
            intent.putExtra("indicators", getIndicators());
            intent.putExtra("interventions", getInterventions());
            intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
            intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
            intent.putExtra("percentageDone", percentageDone);
            intent.putExtra("actionRequired", actionRequired);
            intent.putExtra("departmentCategories", departmentCategories);
            intent.putExtra("resourcesNeeded", resourcesNeeded);
            intent.putExtra("potentialChallenges", potentialChallenges);
            intent.putExtra("strategyCategories", strategyCategories);
            driver.keyProblemCategory = (KeyProblemCategory) driverOfStunting.getSelectedItem();
            driver.interventions = getInterventions();
            driver.indicators = getIndicators();
            intent.putExtra("driver", driver);
            if(intervention != null){
                intent.putExtra("intervention", intervention);
            }else{
                intent.putExtra("intervention", getInterventions());
            }
            startActivity(intent);
            finish();
        }

    }

    public boolean validate(){
        boolean isValid = true;
        if(driverOfStunting.getSelectedItem() == null){
            AppUtil.createShortNotification(this, "Please select a driver of stunting");
            isValid = false;
        }

        if(getIndicators().size() == 0){
            AppUtil.createShortNotification(this, "Please select at least one indicator");
            isValid = false;
        }

        if(getInterventions().size() == 0){
            AppUtil.createShortNotification(this, "Please select at least one intervention category");
            isValid = false;
        }
        return isValid;
    }
}
