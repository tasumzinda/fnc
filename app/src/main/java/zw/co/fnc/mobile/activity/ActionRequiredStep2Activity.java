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
import java.util.List;

public class ActionRequiredStep2Activity extends BaseActivity implements View.OnClickListener {

    private ListView departmentCategory;
    private ArrayAdapter<DepartmentCategory> departmentCategoryArrayAdapter;
    private Long ward;
    private Long period;
    private Long district;
    private Long driverOfStuntingCategory;
    private ArrayList<Indicator> indicators;
    private ArrayList<InterventionCategory> interventions;
    private Button next;
    private  Long microPlan;
    private Long driverId;
    private KeyProblem driver;
    private ArrayList<InterventionCategory> intervention;
    private Long selectedIntervention;
    private Long actionReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_required_step2);
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
        next = (Button) findViewById(R.id.btn_next);
        departmentCategory = (ListView) findViewById(R.id.department_category);
        departmentCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, DepartmentCategory.getAll());
        departmentCategory.setAdapter(departmentCategoryArrayAdapter);
        departmentCategory.setItemsCanFocus(false);
        departmentCategory.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        next.setOnClickListener(this);
        if(actionReq != 0L){
            ActionRequired item = ActionRequired.findById(actionReq);
            List<DepartmentCategory> departmentCategories = DepartmentCategory.findByActionRequired(item);
            int count = departmentCategoryArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                DepartmentCategory current = departmentCategoryArrayAdapter.getItem(i);
                if(departmentCategories.contains(current)){
                    departmentCategory.setItemChecked(i, true);
                }
            }
        }else if(intervention != null){
            for(InterventionCategory item : intervention){
                if(item.serverId.equals(selectedIntervention)){
                    for(ActionRequired a : item.actionRequireds){
                        if(a.departments != null){
                            ArrayList<Long> departments = new ArrayList<>();
                            for(DepartmentCategory department : a.departments){
                                departments.add(department.serverId);
                            }
                            int count = departmentCategoryArrayAdapter.getCount();
                            for(int k = 0; k < count; k++){
                                DepartmentCategory current = departmentCategoryArrayAdapter.getItem(k);
                                if(departments.contains(current.serverId)){
                                    departmentCategory.setItemChecked(k, true);
                                }
                            }
                        }
                    }
                }
            }

        }
        setSupportActionBar(createToolBar("FNC Mobile::Create/ Edit Ward Intervention Action-Step 2"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void save(){
        if(validate()){
            Intent intent = new Intent(this, ActionRequiredFinalActivity.class);
            intent.putExtra("district", district);
            intent.putExtra("ward", ward);
            intent.putExtra("period", period);
            intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
            intent.putExtra("indicators", indicators);
            intent.putExtra("interventions", interventions);
            intent.putExtra("microPlan", microPlan);
            intent.putExtra("driverId", driverId);
            intent.putExtra("driver", driver);
            intent.putExtra("selectedIntervention", selectedIntervention);
            ArrayList<InterventionCategory> items = new ArrayList<>();
            if(driverId == 0L){
                for(InterventionCategory m : intervention){
                    if(m.serverId.equals(selectedIntervention)){
                        for(ActionRequired a : m.actionRequireds){
                            a.departments = getDepartment();
                        }
                    }
                    items.add(m);
                }
            }else{
                ActionRequired item = ActionRequired.findById(actionReq);
                InterventionCategory interventionCategory = item.interventionCategory;
                for(ActionRequired a : interventionCategory.actionRequireds){
                    a.departments = getDepartment();
                }
                items.add(interventionCategory);
            }

            intent.putExtra("intervention", items);
            intent.putExtra("actionReq", actionReq);
            startActivity(intent);
            finish();
        }
    }
    public boolean validate(){
        boolean isValid = true;
        if(getDepartment().isEmpty()){
            AppUtil.createShortNotification(this, "Please select department category");
            isValid = false;
        }
        return isValid;
    }

    private ArrayList<Long> getDepartmentCategory(){
        ArrayList<Long> a = new ArrayList<>();
        for(int i = 0; i < departmentCategory.getCount(); i++){
            if(departmentCategory.isItemChecked(i)){
                a.add(departmentCategoryArrayAdapter.getItem(i).getId());
            }else{
                a.remove(departmentCategoryArrayAdapter.getItem(i).getId());
            }
        }
        return a;
    }

    private ArrayList<DepartmentCategory> getDepartment(){
        ArrayList<DepartmentCategory> a = new ArrayList<>();
        for(int i = 0; i < departmentCategory.getCount(); i++){
            if(departmentCategory.isItemChecked(i)){
                a.add(departmentCategoryArrayAdapter.getItem(i));
            }else{
                a.remove(departmentCategoryArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            save();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ActionRequiredStep2Activity.this, ActionRequiredStep1Activity.class);
        intent.putExtra("microPlan", microPlan);
        intent.putExtra("driverId", driverId);
        intent.putExtra("actionReq", actionReq);
        intent.putExtra("ward", ward);
        intent.putExtra("district", district);
        intent.putExtra("period", period);
        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
        intent.putExtra("indicators", indicators);
        intent.putExtra("interventions", interventions);
        intent.putExtra("driver", driver);
        intent.putExtra("selectedIntervention", selectedIntervention);
        ArrayList<InterventionCategory> items = new ArrayList<>();
        for(InterventionCategory m : intervention){
            if(m.serverId.equals(selectedIntervention)){
                for(ActionRequired a : m.actionRequireds){
                    a.departments = getDepartment();
                }
            }
            items.add(m);
        }
        intent.putExtra("intervention", items);
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
