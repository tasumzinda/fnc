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
import zw.co.fnc.mobile.util.AppUtil;

import java.util.ArrayList;

/**
 * Created by User on 3/9/2017.
 */
public class ActionPlanItemActivity extends BaseActivity {

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
    private String expectedDateOfCompletion;
    private String actualDateOfCompletion;
    private String percentageDone;
    private Long actionRequired;
    private Long driverId;
    private ArrayList<InterventionCategory> intervention;
    TableRow.LayoutParams textParams;
    TableLayout.LayoutParams rowParams;
    LinearLayout.LayoutParams tableParams;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.action_plan_item);
        textParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        rowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final Intent intent = getIntent();
        holder = (QuarterlyMicroPlan) intent.getSerializableExtra("plan");
        driver1 = (KeyProblem) intent.getSerializableExtra("driver");
        microPlan = intent.getLongExtra("microPlan", 0L);
        district = intent.getLongExtra("district", 0L);
        ward = intent.getLongExtra("ward", 0L);
        period = intent.getLongExtra("period", 0L);
        expectedDateOfCompletion = intent.getStringExtra("expectedDateOfCompletion");
        actualDateOfCompletion = intent.getStringExtra("actualDateOfCompletion");
        percentageDone = intent.getStringExtra("percentageDone");
        actionRequired = intent.getLongExtra("actionRequired", 0L);
        intervention = (ArrayList<InterventionCategory>) intent.getSerializableExtra("intervention");
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
        selectedDrivers = new ArrayList<>();
        /*driver = new KeyProblem();
        driver.keyProblemCategory = (KeyProblemCategory.findById(driverOfStuntingCategory));
        driver.indicators = selectedIndicators;
        driver.interventions = selectedInterventions;*/
        selectedDrivers.add(driver1);
        for(InterventionCategory m : intervention){
            Log.d("Item", AppUtil.createGson().toJson(m));
        }
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
            for(final InterventionCategory item : intervention){
                if(item.actionRequireds.size() > 0){
                    createInterventionTable(interventionTable, item);
                }else{
                    TableRow interventionRow = new TableRow(this);
                    interventionRow.setLayoutParams(rowParams);
                    TextView problemIntervention = new TextView(this);
                    problemIntervention.setTextSize(16);
                    problemIntervention.setLayoutParams(textParams);
                    problemIntervention.setPadding(10, 10, 10, 5);
                    problemIntervention.setText(item.name);
                    Button addAction = new Button(this);
                    addAction.setText("Add Action Required");
                    addAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ActionPlanItemActivity.this, ActionRequiredStep1Activity.class);
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
                            intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
                            intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
                            intent.putExtra("percentageDone", percentageDone);
                            intent.putExtra("actionRequired", actionRequired);
                            intent.putExtra("selectedIntervention", item.serverId);
                            intent.putExtra("intervention", intervention);
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

            }
            mainTable.addView(interventionTable);
            mainHeader = (TableLayout) findViewById(R.id.table_main);
            mainHeader.addView(mainTable);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ActionPlanItemActivity.this, KeyProblemActivity.class);
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

    public void createInterventionTable(TableLayout interventionTable, InterventionCategory intervention){
        TableRow interventionRow = new TableRow(this);
        interventionRow.setLayoutParams(rowParams);
        TextView problemIntervention = new TextView(this);
        problemIntervention.setTextSize(16);
        problemIntervention.setLayoutParams(textParams);
        problemIntervention.setPadding(10, 10, 10, 5);
        problemIntervention.setText(intervention.name);
        Button addAction = new Button(this);
        addAction.setText("Review Action");
        addAction.setTypeface(Typeface.DEFAULT_BOLD);
            /*addAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoadActionPlanActivity.this, ActionRequiredStep1Activity.class);
                    intent.putExtra("driverId", d.getId());
                    intent.putExtra("microPlan", microPlan);
                    startActivity(intent);
                    finish();
                }
            });*/
        interventionRow.addView(problemIntervention);
        interventionRow.addView(addAction);
        interventionRow.setBackgroundResource(R.drawable.indicator_background);
        interventionRow.setMinimumHeight(60);
        interventionTable.addView(interventionRow);
        TableLayout  actionRequiredTable = new TableLayout(this);
        actionRequiredTable.setBackgroundResource(R.drawable.indicator_background);
        actionRequiredTable.setLayoutParams(tableParams);
        actionRequiredTable.setShrinkAllColumns(true);
        actionRequiredTable.setPadding(15, 10, 15, 10);
        TableRow actionHeaderRow = new TableRow(this);
        TextView actionHeading = new TextView(this);
        actionHeading.setLayoutParams(textParams);
        actionHeading.setTextSize(18);
        actionHeading.setTextColor(getResources().getColor(R.color.white));
        actionHeading.setTypeface(Typeface.DEFAULT_BOLD);
        actionHeading.setText("ACTION REQUIRED");
        actionHeading.setPadding(10, 10, 10, 5);
        actionHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
        actionHeaderRow.addView(actionHeading);
        actionHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
        actionRequiredTable.addView(actionHeaderRow);
        for(ActionRequired action : intervention.actionRequireds){
            TableRow actionRequiredDataRow = new TableRow(this);
            actionRequiredDataRow.setLayoutParams(rowParams);
            actionRequiredDataRow.setBackgroundResource(R.drawable.indicator_background);
            actionRequiredDataRow.setMinimumHeight(60);
            TextView actionTaken = new TextView(this);
            actionTaken.setTextSize(16);
            actionTaken.setLayoutParams(textParams);
            actionTaken.setText(action.actionCategory.name);
            actionTaken.setPadding(10, 10, 10, 5);
            actionRequiredDataRow.addView(actionTaken);
            actionRequiredTable.addView(actionRequiredDataRow);
            TableLayout  resourcesTable = new TableLayout(this);
            resourcesTable.setLayoutParams(tableParams);
            resourcesTable.setShrinkAllColumns(true);
            resourcesTable.setPadding(15, 10, 15, 10);
            TableRow resourcesHeaderRow = new TableRow(this);
            TextView resourcesHeading = new TextView(this);
            resourcesHeading.setLayoutParams(textParams);
            resourcesHeading.setTextSize(18);
            resourcesHeading.setTextColor(getResources().getColor(R.color.white));
            resourcesHeading.setTypeface(Typeface.DEFAULT_BOLD);
            resourcesHeading.setText("RESOURCES NEEDED");
            resourcesHeading.setPadding(10, 10, 10, 5);
            resourcesHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            resourcesHeaderRow.addView(resourcesHeading);
            resourcesHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
            resourcesTable.addView(resourcesHeaderRow);
            for(ResourcesNeededCategory resource : action.resourcesNeededCategorys){
                TableRow resourcesRow = new TableRow(this);
                resourcesRow.setBackgroundResource(R.drawable.indicator_background);
                resourcesRow.setLayoutParams(rowParams);
                resourcesRow.setMinimumHeight(60);
                TextView resources = new TextView(this);
                resources.setText(resource.name);
                resources.setPadding(10, 10, 10, 5);
                resources.setLayoutParams(textParams);
                resourcesRow.addView(resources);
                resourcesTable.addView(resourcesRow);
            }
            actionRequiredTable.addView(resourcesTable);

            TableLayout  departmentTable = new TableLayout(this);
            departmentTable.setLayoutParams(tableParams);
            departmentTable.setShrinkAllColumns(true);
            departmentTable.setPadding(15, 10, 15, 10);
            TableRow departmentHeaderRow = new TableRow(this);
            TextView departmentHeading = new TextView(this);
            departmentHeading.setLayoutParams(textParams);
            departmentHeading.setTextSize(18);
            departmentHeading.setTextColor(getResources().getColor(R.color.white));
            departmentHeading.setTypeface(Typeface.DEFAULT_BOLD);
            departmentHeading.setText("DEPARTMENT");
            departmentHeading.setPadding(10, 10, 10, 5);
            departmentHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            departmentHeaderRow.addView(departmentHeading);
            departmentHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
            departmentTable.addView(departmentHeaderRow);
            for(DepartmentCategory department : action.departments){
                TableRow departmentRow = new TableRow(this);
                departmentRow.setBackgroundResource(R.drawable.indicator_background);
                departmentRow.setLayoutParams(rowParams);
                departmentRow.setMinimumHeight(60);
                TextView departments = new TextView(this);
                departments.setText(department.name);
                departments.setPadding(10, 10, 10, 5);
                departments.setLayoutParams(textParams);
                departmentRow.addView(departments);
                departmentTable.addView(departmentRow);
            }
            actionRequiredTable.addView(departmentTable);
            TableLayout  strategyTable = new TableLayout(this);
            strategyTable.setLayoutParams(tableParams);
            strategyTable.setShrinkAllColumns(true);
            strategyTable.setPadding(15, 10, 15, 10);
            TableRow strategyHeaderRow = new TableRow(this);
            TextView strategyHeading = new TextView(this);
            strategyHeading.setLayoutParams(textParams);
            strategyHeading.setTextSize(18);
            strategyHeading.setTextColor(getResources().getColor(R.color.white));
            strategyHeading.setTypeface(Typeface.DEFAULT_BOLD);
            strategyHeading.setText("STRATEGY TO OVERCOME CHALLENGES");
            strategyHeading.setPadding(10, 10, 10, 5);
            strategyHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            strategyHeaderRow.addView(strategyHeading);
            strategyHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
            strategyTable.addView(strategyHeaderRow);
            for(StrategyCategory strategy : action.strategyCategorys){
                TableRow strategyRow = new TableRow(this);
                strategyRow.setBackgroundResource(R.drawable.indicator_background);
                strategyRow.setLayoutParams(rowParams);
                strategyRow.setMinimumHeight(60);
                TextView strategys = new TextView(this);
                strategys.setText(strategy.name);
                strategys.setPadding(10, 10, 10, 5);
                strategys.setLayoutParams(textParams);
                strategyRow.addView(strategys);
                strategyTable.addView(strategyRow);
            }
            actionRequiredTable.addView(strategyTable);

            TableLayout  challengesTable = new TableLayout(this);
            challengesTable.setLayoutParams(tableParams);
            challengesTable.setShrinkAllColumns(true);
            challengesTable.setPadding(15, 10, 15, 10);
            TableRow challengesHeaderRow = new TableRow(this);
            TextView challengesHeading = new TextView(this);
            challengesHeading.setLayoutParams(textParams);
            challengesHeading.setTextSize(18);
            challengesHeading.setTextColor(getResources().getColor(R.color.white));
            challengesHeading.setTypeface(Typeface.DEFAULT_BOLD);
            challengesHeading.setText("POTENTIAL CHALLENGES");
            challengesHeading.setPadding(10, 10, 10, 5);
            challengesHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
            challengesHeaderRow.addView(challengesHeading);
            challengesHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
            challengesTable.addView(challengesHeaderRow);
            for(PotentialChallengesCategory challenge : action.potentialChallengesCategorys){
                TableRow challengesRow = new TableRow(this);
                challengesRow.setBackgroundResource(R.drawable.indicator_background);
                challengesRow.setLayoutParams(rowParams);
                challengesRow.setMinimumHeight(60);
                TextView challenges = new TextView(this);
                challenges.setText(challenge.name);
                challenges.setPadding(10, 10, 10, 5);
                challenges.setLayoutParams(textParams);
                challengesRow.addView(challenges);
                challengesTable.addView(challengesRow);
            }
            actionRequiredTable.addView(challengesTable);

        }
        interventionTable.addView(actionRequiredTable);
    }
}
