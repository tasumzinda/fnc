package zw.co.fnc.mobile.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;

import java.util.ArrayList;

/**
 * Created by User on 3/18/2017.
 */
public class ActionPlanItemActivity1 extends BaseActivity{

    private Long district;
    private Long ward;
    private Long period;
    private Long driverOfStuntingCategory;
    private ArrayList<Indicator> indicators;
    private ArrayList<InterventionCategory> interventions;
    private ArrayList<Indicator> selectedIndicators;
    private ArrayList<InterventionCategory> selectedInterventions;
    private KeyProblem driver;
    private ArrayList<KeyProblem> selectedDrivers;
    private Button addDriverOfStunting;
    private Long microPlan;
    TableLayout mainHeader;
    Button save;
    private QuarterlyMicroPlan holder;
    private KeyProblem driver1;
    private ArrayList<Long> departmentCategories;
    private ArrayList<Long> resourcesNeeded;
    private ArrayList<Long> strategyCategories;
    private ArrayList<Long> potentialChallenges;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.action_plan_item);
        final Intent intent = getIntent();
        holder = (QuarterlyMicroPlan) intent.getSerializableExtra("plan");
        driver1 = (KeyProblem) intent.getSerializableExtra("driver");
        microPlan = intent.getLongExtra("microPlan", 0L);
        district = intent.getLongExtra("district", 0L);
        ward = intent.getLongExtra("ward", 0L);
        period = intent.getLongExtra("period", 0L);
        strategyCategories = (ArrayList<Long>) intent.getSerializableExtra("strategyCategories");
        potentialChallenges = (ArrayList<Long>) intent.getSerializableExtra("potentialChallenges");
        resourcesNeeded = (ArrayList<Long>) intent.getSerializableExtra("resourcesNeeded");
        departmentCategories = (ArrayList<Long>) intent.getSerializableExtra("departmentCategories");
        driverOfStuntingCategory = intent.getLongExtra("driverOfStuntingCategory", 0L);
        indicators = (ArrayList<Indicator>) intent.getSerializableExtra("indicators");
        interventions = (ArrayList<InterventionCategory>) intent.getSerializableExtra("interventions");
        if(microPlan == 0L){
            setSupportActionBar(createToolBar("FNC Mobile:: " + District.findById(district).name + " " + Ward.findById(ward).name + " " + Period.findById(period).name + " Action Plan"));
        }else{
            setSupportActionBar(createToolBar("FNC Mobile:: " + QuarterlyMicroPlan.findById(microPlan).ward.district.name + " " + QuarterlyMicroPlan.findById(microPlan).ward.name + " " + QuarterlyMicroPlan.findById(microPlan).period.name + " Action Plan"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedIndicators = new ArrayList<>();
        selectedInterventions = new ArrayList<>();
        /*for(int i = 0; i < driver1.indicators.size(); i++){
            Indicator ind = Indicator.findById(indicators.get(i));
            selectedIndicators.add(ind);
        }
        for(int i = 0; i < interventions.size(); i++){
            InterventionCategory inter = InterventionCategory.findById(interventions.get(i));
            selectedInterventions.add(inter);
        }*/
        Log.d("Driver", "D: " + driver1.getId());
        selectedDrivers = new ArrayList<>();
        driver = new KeyProblem();
        driver.keyProblemCategory = (KeyProblemCategory.findById(driverOfStuntingCategory));
        driver.indicators = selectedIndicators;
        driver.interventions = selectedInterventions;
        selectedDrivers.add(driver1);
        init();
    }

    public void init(){
        for(KeyProblem d : selectedDrivers){
            TableLayout mainTable = new TableLayout(this);
            LinearLayout.LayoutParams tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mainTable.setLayoutParams(tableParams);
            mainTable.setBackgroundResource(R.drawable.drawable_row_border);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            TableRow driverHeaderRow = new TableRow(this);
            TableRow.LayoutParams textParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            driverHeaderRow.setLayoutParams(rowParams);
            TextView driverHeading = new TextView(this);
            driverHeading.setLayoutParams(textParams);
            driverHeading.setTextSize(18);
            driverHeading.setTextColor(getResources().getColor(R.color.white));
            driverHeading.setTypeface(Typeface.DEFAULT_BOLD);
            driverHeading.setText("DRIVER OF STUNTING");
            driverHeading.setPadding(10, 10, 10, 5);
            driverHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            driverHeaderRow.addView(driverHeading);
            driverHeaderRow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainTable.addView(driverHeaderRow);
            mainTable.setStretchAllColumns(true);
            TableRow mainRow = new TableRow(this);
            mainRow.setLayoutParams(rowParams);
            TextView driverOfStunting = new TextView(this);
            driverOfStunting.setText(driver1.keyProblemCategory.name);
            driverOfStunting.setTextSize(18);
            driverOfStunting.setTextColor(getResources().getColor(R.color.colorPrimary));
            driverOfStunting.setPadding(10, 10, 10, 5);
            mainRow.addView(driverOfStunting);
            mainRow.setBackgroundResource(R.drawable.drawable_row_border);
            mainRow.setMinimumHeight(60);
            mainTable.addView(mainRow);
            TableLayout  indicatorTable = new TableLayout(this);
            indicatorTable.setLayoutParams(tableParams);
            indicatorTable.setShrinkAllColumns(true);
            indicatorTable.setPadding(10, 10, 10, 10);
            TableRow indicatorHeaderRow = new TableRow(this);
            TextView indicatorHeading = new TextView(this);
            indicatorHeading.setLayoutParams(textParams);
            indicatorHeading.setTextSize(18);
            indicatorHeading.setTextColor(getResources().getColor(R.color.white));
            indicatorHeading.setTypeface(Typeface.DEFAULT_BOLD);
            indicatorHeading.setText("PROBLEM INDICATOR");
            indicatorHeading.setPadding(10, 10, 10, 5);
            indicatorHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            indicatorHeaderRow.addView(indicatorHeading);
            indicatorHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
            indicatorTable.addView(indicatorHeaderRow);
            for(Indicator indicator : driver1.indicators){
                TableRow indicatorRow = new TableRow(this);
                indicatorRow.setLayoutParams(rowParams);
                TextView problemIndicator = new TextView(this);
                problemIndicator.setTextSize(16);
                problemIndicator.setLayoutParams(textParams);
                problemIndicator.setPadding(10, 10, 10, 5);
                problemIndicator.setText(indicator.name);
                indicatorRow.addView(problemIndicator);
                indicatorRow.setBackgroundResource(R.drawable.indicator_background);
                indicatorRow.setMinimumHeight(60);
                indicatorTable.addView(indicatorRow);
            }
            mainTable.addView(indicatorTable);
            TableLayout  interventionTable = new TableLayout(this);
            interventionTable.setLayoutParams(tableParams);
            interventionTable.setShrinkAllColumns(true);
            interventionTable.setPadding(10, 10, 10, 10);
            interventionTable.setBackgroundResource(R.drawable.indicator_background);
            TableRow interventionHeaderRow = new TableRow(this);
            TextView interventionHeading = new TextView(this);
            interventionHeading.setLayoutParams(textParams);
            interventionHeading.setTextSize(18);
            interventionHeading.setTextColor(getResources().getColor(R.color.white));
            interventionHeading.setTypeface(Typeface.DEFAULT_BOLD);
            interventionHeading.setText("INTERVENTION TO ADDRESS PROBLEM");
            interventionHeading.setPadding(10, 10, 10, 5);
            interventionHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            interventionHeaderRow.addView(interventionHeading);
            interventionHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
            interventionTable.addView(interventionHeaderRow);
            for(InterventionCategory intervention : driver1.interventions){
                TableRow interventionRow = new TableRow(this);
                interventionRow.setLayoutParams(rowParams);
                TextView problemIntervention = new TextView(this);
                problemIntervention.setTextSize(16);
                problemIntervention.setLayoutParams(textParams);
                problemIntervention.setPadding(10, 10, 10, 5);
                problemIntervention.setText(intervention.name);
                Button addAction = new Button(this);
                addAction.setText("Add Action Required");
                addAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActionPlanItemActivity1.this, ActionRequiredStep1Activity.class);
                        intent.putExtra("microPlan", microPlan);
                        intent.putExtra("ward", ward);
                        intent.putExtra("district", district);
                        intent.putExtra("period", period);
                        intent.putExtra("driver", driver1);
                        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
                        intent.putExtra("indicators", indicators);
                        intent.putExtra("interventions", interventions);
                        intent.putExtra("departmentCategories", departmentCategories);
                        intent.putExtra("resourcesNeeded", resourcesNeeded);
                        intent.putExtra("potentialChallenges", potentialChallenges);
                        intent.putExtra("strategyCategories", strategyCategories);
                        startActivity(intent);
                        finish();
                    }
                });
                interventionRow.addView(problemIntervention);
                interventionRow.addView(addAction);
                interventionRow.setBackgroundResource(R.drawable.indicator_background);
                interventionRow.setMinimumHeight(60);
                interventionTable.addView(interventionRow);
            }
            mainTable.addView(interventionTable);
            mainHeader = (TableLayout) findViewById(R.id.table_main);
            mainHeader.addView(mainTable);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ActionPlanItemActivity1.this, KeyProblemActivity.class);
        intent.putExtra("district", district);
        intent.putExtra("ward", ward);
        intent.putExtra("period", period);
        intent.putExtra("plan", holder);
        intent.putExtra("driver", driver1);
        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
        intent.putExtra("indicators", indicators);
        intent.putExtra("interventions", interventions);
        intent.putExtra("departmentCategories", departmentCategories);
        intent.putExtra("resourcesNeeded", resourcesNeeded);
        intent.putExtra("potentialChallenges", potentialChallenges);
        intent.putExtra("strategyCategories", strategyCategories);
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
}
