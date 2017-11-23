package zw.co.fnc.mobile.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;
import zw.co.fnc.mobile.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class ActionRequiredStep1Activity extends BaseActivity implements View.OnClickListener{

    private Spinner actionRequired;
    private EditText expectedDateOfCompletion;
    private EditText actualDateOfCompletion;
    private EditText percentageDone;
    private ListView resourcesNeeded;
    private ArrayAdapter<ResourcesNeededCategory> resourcesNeededCategoryArrayAdapter;
    private Long ward;
    private Long period;
    private Long district;
    private Long driverOfStuntingCategory;
    private ArrayList<Indicator> indicators;
    private ArrayList<InterventionCategory> interventions;
    private Button next;
    private  Long microPlan;
    private DatePickerDialog expectedDateOfCompletionDialog;
    private DatePickerDialog actualDateOfCompletionDialog;
    private Long driverId;
    private ArrayList<Long> departmentCategories;
    private ArrayList<Long> strategyCategories;
    private ArrayList<Long> potentialChallenges;
    private KeyProblem driver;
    private Long action;
    private String expectedDate;
    private String actualDate;
    private String percentage;
    private ArrayList<Long> resources;
    private Long selectedIntervention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_required_step1);
        Intent intent = getIntent();
        driverId = intent.getLongExtra("driverId", 0L);
        district = intent.getLongExtra("district", 0L);
        ward = intent.getLongExtra("ward", 0L);
        period = intent.getLongExtra("period", 0L);
        microPlan = intent.getLongExtra("microPlan", 0L);
        driver = (KeyProblem) intent.getSerializableExtra("driver");
        expectedDate = intent.getStringExtra("expectedDateOfCompletion");
        actualDate = intent.getStringExtra("actualDateOfCompletion");
        percentage = intent.getStringExtra("percentageDone");
        resources = (ArrayList<Long>) intent.getSerializableExtra("resourcesNeeded");
        strategyCategories = (ArrayList<Long>) intent.getSerializableExtra("strategyCategories");
        potentialChallenges = (ArrayList<Long>) intent.getSerializableExtra("potentialChallenges");
        departmentCategories = (ArrayList<Long>) intent.getSerializableExtra("departmentCategories");
        driverOfStuntingCategory = intent.getLongExtra("driverOfStuntingCategory", 0L);
        indicators = (ArrayList<Indicator>) intent.getSerializableExtra("indicators");
        interventions = (ArrayList<InterventionCategory>) intent.getSerializableExtra("interventions");
        action =  intent.getLongExtra("actionRequired", 0L);
        selectedIntervention = intent.getLongExtra("selectedIntervention", 0L);
        Log.d("Intervention", InterventionCategory.findByServerId(selectedIntervention).name);
        actionRequired = (Spinner) findViewById(R.id.action_required);
        expectedDateOfCompletion = (EditText) findViewById(R.id.exp_date_of_completion);
        actualDateOfCompletion = (EditText) findViewById(R.id.actual_date_of_completion);
        percentageDone = (EditText) findViewById(R.id.percentage_done);
        resourcesNeeded = (ListView) findViewById(R.id.resources_needed);
        next = (Button) findViewById(R.id.btn_next);
        ArrayAdapter<ActionCategory> actionCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ActionCategory.getAll());
        actionCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionRequired.setAdapter(actionCategoryArrayAdapter);
        resourcesNeededCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ResourcesNeededCategory.getAll());
        resourcesNeeded.setAdapter(resourcesNeededCategoryArrayAdapter);
        resourcesNeeded.setItemsCanFocus(false);
        resourcesNeeded.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        next.setOnClickListener(this);
        expectedDateOfCompletionDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), expectedDateOfCompletion);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        actualDateOfCompletionDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), actualDateOfCompletion);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        expectedDateOfCompletion.setOnClickListener(this);
        actualDateOfCompletion.setOnClickListener(this);
        if(driverId != 0L){
            /*KeyProblem d = KeyProblem.findById(driverId);
            int i = 0;
            for(ActionRequired action : ActionRequired.findByKeyProblem(d)){
                for(ActionCategory category : ActionCategory.getAll()){
                    if(action.actionCategory != null && action.actionCategory.equals(actionRequired.getItemAtPosition(i))){
                        actionRequired.setSelection(i);
                        break;
                    }
                    i++;
                }
                if(action.actualDateOfCompletion != null){
                    updateLabel(action.actualDateOfCompletion , actualDateOfCompletion);
                }
                updateLabel(action.expectedDateOfCompletion, expectedDateOfCompletion);
                if(action.percentageDone != null){
                    percentageDone.setText(action.percentageDone.toString());
                }
                ArrayList<ResourcesNeededCategory> resources = (ArrayList<ResourcesNeededCategory>) ResourcesNeededCategory.findByActionRequired(action);
                int count = resourcesNeededCategoryArrayAdapter.getCount();
                for(int k = 0; k < count; k++){
                    ResourcesNeededCategory current = resourcesNeededCategoryArrayAdapter.getItem(k);
                    if(resources.contains(current)){
                        resourcesNeeded.setItemChecked(i, true);
                    }
                }
            }*/
        }else if(action != 0L){
            int i = 0;
            for(ActionCategory category : ActionCategory.getAll()){
                if (action != null && action.equals(((ActionCategory) actionRequired.getItemAtPosition(i)).getId())) {
                    actionRequired.setSelection(i, true);
                    break;
                }
                i++;
            }
            if(actualDate != null && ! actualDate.isEmpty()){
                updateLabel(DateUtil.getDateFromString(actualDate), actualDateOfCompletion);
            }
            updateLabel(DateUtil.getDateFromString(expectedDate), expectedDateOfCompletion);
            if(percentage != null){
                percentageDone.setText(percentage);
            }
            ArrayList<Long> list = resources;
            int count = resourcesNeededCategoryArrayAdapter.getCount();
            for(int k = 0; k < count; k++){
                ResourcesNeededCategory current = resourcesNeededCategoryArrayAdapter.getItem(k);
                if(list.contains(current.getId())){
                    resourcesNeeded.setItemChecked(k, true);
                }
            }
        }
        setSupportActionBar(createToolBar("FNC Mobile::Create/ Edit Ward Intervention Action-Step 1"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void save(){
        if(validate()){
            Intent intent = new Intent(this, ActionRequiredStep2Activity.class);
            intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion.getText().toString());
            intent.putExtra("actualDateOfCompletion", actualDateOfCompletion.getText().toString());
            intent.putExtra("percentageDone", percentageDone.getText().toString());
            intent.putExtra("actionRequired", ((ActionCategory) actionRequired.getSelectedItem()).getId());
            intent.putExtra("resourcesNeeded", getResourcesNeeded());
            intent.putExtra("district", district);
            intent.putExtra("ward", ward);
            intent.putExtra("period", period);
            intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
            intent.putExtra("indicators", indicators);
            intent.putExtra("interventions", interventions);
            intent.putExtra("microPlan", microPlan);
            intent.putExtra("driverId", driverId);
            intent.putExtra("departmentCategories",departmentCategories);
            intent.putExtra("potentialChallenges", potentialChallenges);
            intent.putExtra("strategyCategories", strategyCategories);
            intent.putExtra("driver", driver);
            ActionRequired action = new ActionRequired();
            action.actionCategory = (ActionCategory) actionRequired.getSelectedItem();
            action.actualDateOfCompletion = DateUtil.getDateFromString(actualDateOfCompletion.getText().toString());
            action.expectedDateOfCompletion = DateUtil.getDateFromString(expectedDateOfCompletion.getText().toString());
            action.percentageDone = Integer.parseInt(percentageDone.getText().toString());
            action.resourceIds = getResourcesNeeded();
            action.interventionCategory = InterventionCategory.findByServerId(selectedIntervention);
            action.keyProblem = driver;
            ArrayList<ActionRequired> list = new ArrayList<>();
            list.add(action);
            for(InterventionCategory m : interventions){
                if(m.serverId.equals(selectedIntervention)){
                    m.actionRequireds = list;
                }
            }
            startActivity(intent);
            finish();
        }
    }
    public boolean validate(){
        boolean isValid = true;
        if(expectedDateOfCompletion.getText().toString().isEmpty()){
            expectedDateOfCompletion.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            expectedDateOfCompletion.setError(null);
        }

        if(actionRequired.getSelectedItem() == null){
            AppUtil.createShortNotification(this, "Please select action required");
            isValid = false;
        }
        return isValid;
    }

    private ArrayList<Long> getResourcesNeeded(){
        ArrayList<Long> a = new ArrayList<>();
        for(int i = 0; i < resourcesNeeded.getCount(); i++){
            if(resourcesNeeded.isItemChecked(i)){
                a.add(resourcesNeededCategoryArrayAdapter.getItem(i).getId());
            }else{
                a.remove(resourcesNeededCategoryArrayAdapter.getItem(i).getId());
            }
        }
        return a;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            save();
        }
        if(view.getId() == expectedDateOfCompletion.getId()){
            expectedDateOfCompletionDialog.show();
        }
        if(view.getId() == actualDateOfCompletion.getId()){
            actualDateOfCompletionDialog.show();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ActionRequiredStep1Activity.this, ActionPlanItemActivity1.class);
        intent.putExtra("microPlan", microPlan);
        intent.putExtra("ward", ward);
        intent.putExtra("district", district);
        intent.putExtra("period", period);
        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
        intent.putExtra("indicators", indicators);
        intent.putExtra("interventions", interventions);
        intent.putExtra("departmentCategories",departmentCategories);
        intent.putExtra("resourcesNeeded", getResourcesNeeded());
        intent.putExtra("driver", driver);
        intent.putExtra("driverId", driverId);
        intent.putExtra("potentialChallenges", potentialChallenges);
        intent.putExtra("strategyCategories", strategyCategories);
        intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion.getText().toString());
        intent.putExtra("actualDateOfCompletion", actualDateOfCompletion.getText().toString());
        intent.putExtra("percentageDone", percentageDone.getText().toString());
        intent.putExtra("actionRequired", ((ActionCategory) actionRequired.getSelectedItem()).getId());
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
