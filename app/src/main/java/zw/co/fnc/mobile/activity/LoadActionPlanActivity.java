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

/**
 * Created by User on 3/12/2017.
 */
public class LoadActionPlanActivity extends BaseActivity {

    private Long microPlan;
    TableLayout mainHeader;
    TableRow.LayoutParams textParams;
    TableLayout.LayoutParams rowParams;
    LinearLayout.LayoutParams tableParams;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.action_plan_item);
        final Intent intent = getIntent();
        microPlan = intent.getLongExtra("microPlan", 0L);
        setSupportActionBar(createToolBar(QuarterlyMicroPlan.findById(microPlan).ward.district.name + " " + QuarterlyMicroPlan.findById(microPlan).ward.name + ":: " + QuarterlyMicroPlan.findById(microPlan).period.name + " Action Plan"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        rowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init();
    }

    public void init(){
        for(final KeyProblem d : KeyProblem.findByMicroPlan(QuarterlyMicroPlan.findById(microPlan))){
            TableLayout mainTable = new TableLayout(this);
            mainTable.setLayoutParams(tableParams);
            mainTable.setBackgroundResource(R.drawable.drawable_row_border);
            TableRow driverHeaderRow = new TableRow(this);
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
            driverOfStunting.setText(d.keyProblemCategory.name);
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
            for(Indicator indicator : Indicator.findByKeyProblem(d)){
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
            for(InterventionCategory intervention : InterventionCategory.findByKeyProblem(d)){
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
                addAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoadActionPlanActivity.this, ActionRequiredStep1Activity.class);
                        intent.putExtra("driverId", d.getId());
                        intent.putExtra("microPlan", microPlan);
                        startActivity(intent);
                        finish();
                    }
                });
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
                for(ActionRequired action : ActionRequired.findByInterventionCategory(intervention)){
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
                    for(ActionRequiredResourcesNeededContract resource : ActionRequiredResourcesNeededContract.findByActionRequired(action)){
                        TableRow resourcesRow = new TableRow(this);
                        resourcesRow.setBackgroundResource(R.drawable.indicator_background);
                        resourcesRow.setLayoutParams(rowParams);
                        resourcesRow.setMinimumHeight(60);
                        TextView resources = new TextView(this);
                        resources.setText(resource.resourcesNeededCategory.name);
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
                    for(ActionRequiredDepartmentCategoryContract department : ActionRequiredDepartmentCategoryContract.findByActionRequired(action)){
                        TableRow departmentRow = new TableRow(this);
                        departmentRow.setBackgroundResource(R.drawable.indicator_background);
                        departmentRow.setLayoutParams(rowParams);
                        departmentRow.setMinimumHeight(60);
                        TextView departments = new TextView(this);
                        departments.setText(department.departmentCategory.name);
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
                    for(ActionRequiredStrategyCategoryContract strategy : ActionRequiredStrategyCategoryContract.findByActionRequired(action)){
                        TableRow strategyRow = new TableRow(this);
                        strategyRow.setBackgroundResource(R.drawable.indicator_background);
                        strategyRow.setLayoutParams(rowParams);
                        strategyRow.setMinimumHeight(60);
                        TextView strategys = new TextView(this);
                        strategys.setText(strategy.strategyCategory.name);
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
                    for(ActionRequiredPotentialChallengesCategoryContract challenge : ActionRequiredPotentialChallengesCategoryContract.findByActionRequired(action)){
                        TableRow challengesRow = new TableRow(this);
                        challengesRow.setBackgroundResource(R.drawable.indicator_background);
                        challengesRow.setLayoutParams(rowParams);
                        challengesRow.setMinimumHeight(60);
                        TextView challenges = new TextView(this);
                        challenges.setText(challenge.potentialChallengesCategory.name);
                        challenges.setPadding(10, 10, 10, 5);
                        challenges.setLayoutParams(textParams);
                        challengesRow.addView(challenges);
                        challengesTable.addView(challengesRow);
                    }
                    actionRequiredTable.addView(challengesTable);

                }
                interventionTable.addView(actionRequiredTable);
            }
            mainTable.addView(interventionTable);
            mainHeader = (TableLayout) findViewById(R.id.table_main);
            mainHeader.addView(mainTable);
        }
        TableLayout addDriverTable = new TableLayout(this);
        addDriverTable.setLayoutParams(tableParams);
        addDriverTable.setStretchAllColumns(true);
        TableRow addDriverRow = new TableRow(this);
        addDriverRow.setLayoutParams(rowParams);
        Button addDriver = new Button(this);
        addDriver.setText("Add New Driver Of Stunting");
        addDriver.setTypeface(Typeface.DEFAULT_BOLD);
        addDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadActionPlanActivity.this, KeyProblemActivity.class);
                intent.putExtra("microPlan", microPlan);
                startActivity(intent);
                finish();
            }
        });
        addDriverRow.addView(addDriver);
        addDriverTable.addView(addDriverRow);
        mainHeader.addView(addDriverTable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(LoadActionPlanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
