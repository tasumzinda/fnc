package zw.co.fnc.mobile.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;

import java.util.ArrayList;

/**
 * Created by User on 3/9/2017.
 */
public class ActionPlanItemActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //setContentView(R.layout.generic_list_view);
        setContentView(R.layout.action_plan_item);
        final Intent intent = getIntent();
        microPlan = intent.getLongExtra("microPlan", 0L);
        district = intent.getLongExtra("district", 0L);
        ward = intent.getLongExtra("ward", 0L);
        period = intent.getLongExtra("period", 0L);
        driverOfStuntingCategory = intent.getLongExtra("driverOfStuntingCategory", 0L);
        indicators = (ArrayList<Indicator>) intent.getSerializableExtra("indicators");
        interventions = (ArrayList<InterventionCategory>) intent.getSerializableExtra("interventions");
        setSupportActionBar(createToolBar("FNC Mobile::" + District.findById(district).name + " " + Ward.findById(ward).name + " :" + Period.findById(period).name + " Action Plan"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedIndicators = new ArrayList<>();
        selectedInterventions = new ArrayList<>();
        for(int i = 0; i < indicators.size(); i++){
            Indicator ind = indicators.get(i);
            selectedIndicators.add(ind);
        }
        for(int i = 0; i < interventions.size(); i++){
            InterventionCategory inter = interventions.get(i);
            selectedInterventions.add(inter);
        }
        selectedDrivers = new ArrayList<>();
        driver = new KeyProblem();
        driver.keyProblemCategory = (KeyProblemCategory.findById(driverOfStuntingCategory));
        driver.indicators = selectedIndicators;
        driver.interventions = selectedInterventions;
        selectedDrivers.add(driver);
        init();
    }

    public void init(){
        mainHeader = new TableLayout(this);
        TableRow headerRow = new TableRow(this);
        TextView driverHeading = new TextView(this);
        driverHeading.setTextSize(18);
        driverHeading.setTextColor(getResources().getColor(R.color.white));
        driverHeading.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        driverHeading.setTypeface(Typeface.DEFAULT_BOLD);
        driverHeading.setMinWidth(300);
        driverHeading.setText("DRIVER OF STUNTING");
        driverHeading.setPadding(10, 10, 10, 5);
        headerRow.setBackgroundResource(R.drawable.drawable_row_border);
        headerRow.addView(driverHeading);
        headerRow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView problemHeading = new TextView(this);
        problemHeading.setText("PROBLEM INDICATOR");
        problemHeading.setTextSize(18);
        problemHeading.setPadding(10, 10, 10, 5);
        problemHeading.setTextColor(getResources().getColor(R.color.white));
        problemHeading.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        problemHeading.setTypeface(Typeface.DEFAULT_BOLD);
        headerRow.addView(problemHeading);
        TextView interventionHeading = new TextView(this);
        interventionHeading.setText("INTERVENTION TO ADDRESS PROBLEM");
        interventionHeading.setTextColor(getResources().getColor(R.color.white));
        interventionHeading.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        interventionHeading.setTypeface(Typeface.DEFAULT_BOLD);
        interventionHeading.setTextSize(18);
        interventionHeading.setPadding(10, 10, 10, 5);
        headerRow.addView(interventionHeading);
        headerRow.setMinimumHeight(60);
        mainHeader.addView(headerRow);
        mainHeader.setColumnStretchable(0, true);
        for(KeyProblem d : selectedDrivers){
            TableRow mainRow = new TableRow(this);
            TextView driverOfStunting = new TextView(this);
            driverOfStunting.setText(d.keyProblemCategory.name);
            driverOfStunting.setTextSize(16);
            driverOfStunting.setPadding(10, 10, 10, 5);
            driverOfStunting.setTextColor(getResources().getColor(R.color.colorPrimary));
            driverOfStunting.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mainRow.addView(driverOfStunting);
            mainRow.setBackgroundResource(R.drawable.drawable_row_border);
            mainRow.setMinimumHeight(60);
            TableLayout  indicatorTable = new TableLayout(this);
            for(Indicator indicator : d.indicators){
                TableRow indicatorRow = new TableRow(this);
                TextView problemIndicator = new TextView(this);
                problemIndicator.setTextSize(16);
                problemIndicator.setPadding(10, 10, 10, 5);
                problemIndicator.setText(indicator.name);
                indicatorRow.addView(problemIndicator);
                indicatorRow.setBackgroundResource(R.drawable.drawable_row_border);
                indicatorRow.setMinimumHeight(60);
                indicatorTable.addView(indicatorRow);
            }
            mainRow.addView(indicatorTable);
            TableLayout interventionTable = new TableLayout(this);
            for(InterventionCategory intervention : d.interventions){
                TableRow interventionRow = new TableRow(this);
                TextView problemIntervention = new TextView(this);
                problemIntervention.setText(intervention.name + ": Add Action Req");
                problemIntervention.setTextSize(16);
                problemIntervention.setPadding(10, 10, 10, 5);
                interventionRow.addView(problemIntervention);
                interventionRow.setBackgroundResource(R.drawable.drawable_row_border);
                interventionRow.setMinimumHeight(60);
                interventionTable.addView(interventionRow);
                TableLayout actionRequiredTable = new TableLayout(this);
                TableRow actionHeaderRow = new TableRow(this);
                actionHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
                actionHeaderRow.setMinimumHeight(60);
                TextView placeHolder1 = new TextView(this);
                placeHolder1.setMinWidth(200);
                actionHeaderRow.addView(placeHolder1);
                TextView actionHeader = new TextView(this);
                actionHeader.setText("Action Required");
                actionHeaderRow.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                actionHeader.setTextSize(16);
                actionHeader.setPadding(10, 10, 10, 5);
                actionHeader.setTextColor(getResources().getColor(R.color.white));
                actionHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                actionHeader.setTypeface(Typeface.DEFAULT_BOLD);
                actionHeaderRow.addView(actionHeader);
                TextView placeHolder2 = new TextView(this);
                placeHolder1.setMinWidth(200);
                actionHeaderRow.addView(placeHolder2);
                actionRequiredTable.addView(actionHeaderRow);
                for(ActionRequired action : ActionRequired.findByInterventionCategory(intervention)){
                    TableRow actionRequiredDataRow = new TableRow(this);
                    actionRequiredDataRow.setBackgroundResource(R.drawable.drawable_row_border);
                    actionRequiredDataRow.setMinimumHeight(60);
                    TextView actionTaken = new TextView(this);
                    actionTaken.setTextSize(16);
                    actionTaken.setText(action.actionCategory.name);
                    actionTaken.setPadding(10, 10, 10, 5);
                    actionRequiredDataRow.addView(actionTaken);
                    actionRequiredTable.addView(actionRequiredDataRow);
                    TableLayout actionRequiredDataTable = new TableLayout(this);
                    TableRow actionItemsHeaderRow = new TableRow(this);
                    actionItemsHeaderRow.setBackgroundResource(R.drawable.drawable_row_border);
                    actionItemsHeaderRow.setMinimumHeight(60);
                    actionItemsHeaderRow.setBackgroundColor(getResources().getColor(R.color.yellow));
                    TextView resourcesHeader = new TextView(this);
                    resourcesHeader.setTextSize(16);
                    resourcesHeader.setText("Resources Needed");
                    resourcesHeader.setTextColor(getResources().getColor(R.color.white));
                    resourcesHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    resourcesHeader.setTypeface(Typeface.DEFAULT_BOLD);
                    resourcesHeader.setMinWidth(250);
                    resourcesHeader.setPadding(10, 10, 10, 5);
                    actionItemsHeaderRow.addView(resourcesHeader);
                    TextView departmentHeader = new TextView(this);
                    departmentHeader.setTextSize(16);
                    departmentHeader.setText("Department");
                    departmentHeader.setTextColor(getResources().getColor(R.color.white));
                    departmentHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    departmentHeader.setTypeface(Typeface.DEFAULT_BOLD);
                    departmentHeader.setMinWidth(250);
                    departmentHeader.setPadding(10, 10, 10, 5);
                    actionItemsHeaderRow.addView(departmentHeader);
                    TextView challengesHeader = new TextView(this);
                    challengesHeader.setTextSize(16);
                    challengesHeader.setText("Potential Challenges");
                    challengesHeader.setTextColor(getResources().getColor(R.color.white));
                    challengesHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    challengesHeader.setTypeface(Typeface.DEFAULT_BOLD);
                    challengesHeader.setMinWidth(250);
                    challengesHeader.setPadding(10, 10, 10, 5);
                    actionItemsHeaderRow.addView(challengesHeader);
                    TextView strategyHeader = new TextView(this);
                    strategyHeader.setTextSize(16);
                    strategyHeader.setText("Strategy To Overcome Challenges");
                    strategyHeader.setTextColor(getResources().getColor(R.color.white));
                    strategyHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    strategyHeader.setTypeface(Typeface.DEFAULT_BOLD);
                    strategyHeader.setMinWidth(250);
                    strategyHeader.setPadding(10, 10, 10, 5);
                    actionItemsHeaderRow.addView(strategyHeader);
                    actionRequiredDataTable.addView(actionItemsHeaderRow);
                    actionRequiredDataRow.addView(actionRequiredDataTable);
                    TableRow actionItemsRow = new TableRow(this);
                    actionItemsRow.setBackgroundResource(R.drawable.drawable_row_border);
                    actionItemsRow.setMinimumHeight(60);
                    TableLayout resourcesTable = new TableLayout(this);
                    for(ResourcesNeededCategory resource : ResourcesNeededCategory.findByActionRequired(action)){
                        TableRow resourcesRow = new TableRow(this);
                        resourcesRow.setBackgroundResource(R.drawable.drawable_row_border);
                        resourcesRow.setMinimumHeight(60);
                        TextView resources = new TextView(this);
                        resources.setText(resource.name);
                        resources.setPadding(10, 10, 10, 5);
                        resourcesRow.addView(resources);
                        resourcesTable.addView(resourcesRow);
                    }
                    actionItemsRow.addView(resourcesTable);
                    TableLayout departmentTable = new TableLayout(this);
                    for(DepartmentCategory department : DepartmentCategory.findByActionRequired(action)){
                        TableRow departmentRow = new TableRow(this);
                        departmentRow.setBackgroundResource(R.drawable.drawable_row_border);
                        departmentRow.setMinimumHeight(60);
                        TextView departments = new TextView(this);
                        departments.setText(department.name);
                        departments.setPadding(10, 10, 10, 5);
                        departmentRow.addView(departments);
                        departmentTable.addView(departmentRow);
                    }
                    actionItemsRow.addView(departmentTable);
                    TableLayout challengesTable = new TableLayout(this);
                    for(PotentialChallengesCategory challenge : PotentialChallengesCategory.findByActionRequired(action)){
                        TableRow challengesRow = new TableRow(this);
                        challengesRow.setBackgroundResource(R.drawable.drawable_row_border);
                        challengesRow.setMinimumHeight(60);
                        TextView challenges = new TextView(this);
                        challenges.setText(challenge.name);
                        challenges.setPadding(10, 10, 10, 5);
                        challengesRow.addView(challenges);
                        challengesTable.addView(challengesRow);
                    }
                    actionItemsRow.addView(challengesTable);
                    TableLayout strategyTable = new TableLayout(this);
                    for(StrategyCategory strategy : StrategyCategory.findByActionRequired(action)){
                        TableRow strategyRow = new TableRow(this);
                        strategyRow.setBackgroundResource(R.drawable.drawable_row_border);
                        strategyRow.setMinimumHeight(60);
                        TextView strategys = new TextView(this);
                        strategys.setText(strategy.name);
                        strategys.setPadding(10, 10, 10, 5);
                        strategyRow.addView(strategys);
                        strategyTable.addView(strategyRow);
                    }
                    actionItemsRow.addView(strategyTable);
                    actionRequiredDataTable.addView(actionItemsRow);

                }
                interventionRow.addView(actionRequiredTable);
            }
            mainRow.addView(interventionTable);
            mainHeader.addView(mainRow);

        }
        TableLayout mainTable = (TableLayout) findViewById(R.id.table_main);
        mainTable.addView(mainHeader);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ActionPlanItemActivity.this, ActionRequiredStep1Activity.class);
        intent.putExtra("district", district);
        intent.putExtra("ward", ward);
        intent.putExtra("period", period);
        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
        intent.putExtra("indicators", indicators);
        intent.putExtra("interventions", interventions);
        startActivity(intent);
        finish();
    }
}
