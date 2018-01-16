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
import java.util.List;

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
    private KeyProblem driver;
    private Long selectedIntervention;
    private ArrayList<InterventionCategory> intervention;
    private Long actionReq;

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
        driverOfStuntingCategory = intent.getLongExtra("driverOfStuntingCategory", 0L);
        indicators = (ArrayList<Indicator>) intent.getSerializableExtra("indicators");
        interventions = (ArrayList<InterventionCategory>) intent.getSerializableExtra("interventions");
        actionReq = intent.getLongExtra("actionReq", 0L);
        selectedIntervention = intent.getLongExtra("selectedIntervention", 0L);
        intervention = (ArrayList<InterventionCategory>) intent.getSerializableExtra("intervention");
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
        if(actionReq != 0L){
            ActionRequired item = ActionRequired.findById(actionReq);
            int i = 0;
            ActionCategory actionCategory = item.actionCategory;
            for(ActionCategory category : ActionCategory.getAll()){
                if(actionCategory != null && actionCategory.equals(actionRequired.getItemAtPosition(i))){
                    actionRequired.setSelection(i, true);
                    break;
                }
                i++;
            }
            if(item.expectedDateOfCompletion != null){
                updateLabel(item.expectedDateOfCompletion, expectedDateOfCompletion);
            }
            if(item.actualDateOfCompletion != null){
                updateLabel(item.actualDateOfCompletion, actualDateOfCompletion);
            }
            if(item.percentageDone != null){
                percentageDone.setText(String.valueOf(item.percentageDone));
            }
            int count = resourcesNeededCategoryArrayAdapter.getCount();
            List<ResourcesNeededCategory> resources = ResourcesNeededCategory.findByActionRequired(item);
            for(int k = 0; k < count; k++){
                ResourcesNeededCategory current = resourcesNeededCategoryArrayAdapter.getItem(k);
                if(resources.contains(current)){
                    resourcesNeeded.setItemChecked(k, true);
                }
            }
        }else if(intervention != null){
            int i = 0;
            for(InterventionCategory item : intervention){
                if(item.serverId.equals(selectedIntervention)){
                    for(ActionRequired a : item.actionRequireds){
                        for(ActionCategory category : ActionCategory.getAll()){
                            if(a.actionCategory != null && a.actionCategory.serverId.equals(((ActionCategory) actionRequired.getItemAtPosition(i)).serverId)){
                                actionRequired.setSelection(i, true);
                                break;
                            }
                            i++;
                        }
                        if(a.actualDateOfCompletion != null){
                            updateLabel(a.actualDateOfCompletion, actualDateOfCompletion);
                        }
                        if(a.expectedDateOfCompletion != null){
                            updateLabel(a.expectedDateOfCompletion, expectedDateOfCompletion);
                        }
                        if(a.percentageDone != null){
                            percentageDone.setText(String.valueOf(a.percentageDone));
                        }
                        ArrayList<Long> resourcesNeededCategories = new ArrayList<>();
                        for(ResourcesNeededCategory resource : a.resourcesNeededCategorys){
                            resourcesNeededCategories.add(resource.serverId);
                        }
                        int count = resourcesNeededCategoryArrayAdapter.getCount();
                        for(int k = 0; k < count; k++){
                            ResourcesNeededCategory current = resourcesNeededCategoryArrayAdapter.getItem(k);
                            if(resourcesNeededCategories.contains(current.serverId)){
                                resourcesNeeded.setItemChecked(k, true);
                            }
                        }
                    }
                }
            }
        }
        setSupportActionBar(createToolBar("FNC Mobile::Create/ Edit Ward Intervention Action-Step 1"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void save(){
        if(validate()){
            Intent intent = new Intent(this, ActionRequiredStep2Activity.class);
            intent.putExtra("district", district);
            intent.putExtra("ward", ward);
            intent.putExtra("period", period);
            intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
            intent.putExtra("indicators", indicators);
            intent.putExtra("interventions", interventions);
            intent.putExtra("microPlan", microPlan);
            intent.putExtra("driverId", driverId);
            intent.putExtra("selectedIntervention", selectedIntervention);
            intent.putExtra("driver", driver);
            intent.putExtra("actionReq", actionReq);
            ActionRequired action = new ActionRequired();
            action.actionCategory = (ActionCategory) actionRequired.getSelectedItem();
            if(! actualDateOfCompletion.getText().toString().isEmpty()){
                action.actualDateOfCompletion = DateUtil.getDateFromString(actualDateOfCompletion.getText().toString());
            }
            if( ! expectedDateOfCompletion.getText().toString().isEmpty()){
                action.expectedDateOfCompletion = DateUtil.getDateFromString(expectedDateOfCompletion.getText().toString());
            }
             if(! percentageDone.getText().toString().isEmpty()){
                action.percentageDone = Integer.parseInt(percentageDone.getText().toString().trim());
            }
            action.resourcesNeededCategorys = getResource();
            action.interventionCategory = InterventionCategory.findByServerId(selectedIntervention);
            action.keyProblem = driver;
            ArrayList<ActionRequired> list = new ArrayList<>();
            list.add(action);
            ArrayList<InterventionCategory> items = new ArrayList<>();
            if(driverId == 0L){
                for(InterventionCategory m : intervention){
                    if(m.serverId.equals(selectedIntervention)){
                        m.actionRequireds = list;
                    }
                    items.add(m);
                }
            }else{
                ActionRequired item = ActionRequired.findById(actionReq);

                InterventionCategory interventionCategory = item.interventionCategory;
                interventionCategory.actionRequireds = list;
                items.add(interventionCategory);
            }

            intent.putExtra("intervention", items);
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
        if(getResource().isEmpty()){
            AppUtil.createShortNotification(this, "Please select ressources needed");
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

    private ArrayList<ResourcesNeededCategory> getResource(){
        ArrayList<ResourcesNeededCategory> a = new ArrayList<>();
        for(int i = 0; i < resourcesNeeded.getCount(); i++){
            if(resourcesNeeded.isItemChecked(i)){
                a.add(resourcesNeededCategoryArrayAdapter.getItem(i));
            }else{
                a.remove(resourcesNeededCategoryArrayAdapter.getItem(i));
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
        if(driverId == 0L){
            Intent intent = new Intent(ActionRequiredStep1Activity.this, ActionPlanItemActivity1.class);
            intent.putExtra("microPlan", microPlan);
            intent.putExtra("ward", ward);
            intent.putExtra("district", district);
            intent.putExtra("period", period);
            intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
            intent.putExtra("indicators", indicators);
            intent.putExtra("interventions", interventions);
            intent.putExtra("driver", driver);
            intent.putExtra("driverId", driverId);
            intent.putExtra("selectedIntervention", selectedIntervention);
            ActionRequired action = new ActionRequired();
            action.actionCategory = (ActionCategory) actionRequired.getSelectedItem();
            if(! actualDateOfCompletion.getText().toString().isEmpty()){
                action.actualDateOfCompletion = DateUtil.getDateFromString(actualDateOfCompletion.getText().toString());
            }
            if( ! expectedDateOfCompletion.getText().toString().isEmpty()){
                action.expectedDateOfCompletion = DateUtil.getDateFromString(expectedDateOfCompletion.getText().toString());
            }
            if(! percentageDone.getText().toString().isEmpty()){
                action.percentageDone = Integer.parseInt(percentageDone.getText().toString().trim());
            }
            action.resourcesNeededCategorys = getResource();
            action.interventionCategory = InterventionCategory.findByServerId(selectedIntervention);
            action.keyProblem = driver;
            ArrayList<ActionRequired> list = new ArrayList<>();
            list.add(action);
            ArrayList<InterventionCategory> items = new ArrayList<>();
            for(InterventionCategory m : interventions){
                if(m.serverId.equals(selectedIntervention)){
                    m.actionRequireds = list;
                }
                items.add(m);
            }
            intent.putExtra("intervention", items);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, LoadActionPlanActivity.class);
            intent.putExtra("microPlan", microPlan);
            startActivity(intent);
            finish();
        }

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
