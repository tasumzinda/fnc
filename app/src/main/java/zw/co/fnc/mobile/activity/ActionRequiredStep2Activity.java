package zw.co.fnc.mobile.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;
import java.util.ArrayList;

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
    private String expectedDateOfCompletion;
    private String actualDateOfCompletion;
    private String percentageDone;
    private Long actionRequired;
    private ArrayList<Long> resourcesNeeded;
    private KeyProblem driver;
    private ArrayList<Long> departments;
    private ArrayList<Long> strategyCategories;
    private ArrayList<Long> potentialChallenges;

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
        strategyCategories = (ArrayList<Long>) intent.getSerializableExtra("strategyCategories");
        potentialChallenges = (ArrayList<Long>) intent.getSerializableExtra("potentialChallenges");
        driverOfStuntingCategory = intent.getLongExtra("driverOfStuntingCategory", 0L);
        indicators = (ArrayList<Indicator>) intent.getSerializableExtra("indicators");
        interventions = (ArrayList<InterventionCategory>) intent.getSerializableExtra("interventions");
        expectedDateOfCompletion = intent.getStringExtra("expectedDateOfCompletion");
        actualDateOfCompletion = intent.getStringExtra("actualDateOfCompletion");
        percentageDone = intent.getStringExtra("percentageDone");
        actionRequired = intent.getLongExtra("actionRequired", 0L);
        resourcesNeeded = (ArrayList<Long>) intent.getSerializableExtra("resourcesNeeded");
        departments = (ArrayList<Long>) intent.getSerializableExtra("departmentCategories");
        next = (Button) findViewById(R.id.btn_next);
        departmentCategory = (ListView) findViewById(R.id.department_category);
        departmentCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, DepartmentCategory.getAll());
        departmentCategory.setAdapter(departmentCategoryArrayAdapter);
        departmentCategory.setItemsCanFocus(false);
        departmentCategory.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        next.setOnClickListener(this);
        if(driverId != 0L){
            KeyProblem d = KeyProblem.findById(driverId);
            int i = 0;
            for(ActionRequired action : ActionRequired.findByKeyProblem(d)){
                ArrayList<DepartmentCategory> resources = (ArrayList<DepartmentCategory>) DepartmentCategory.findByActionRequired(action);
                int count = departmentCategoryArrayAdapter.getCount();
                for(int k = 0; k < count; k++){
                    DepartmentCategory current = departmentCategoryArrayAdapter.getItem(k);
                    if(resources.contains(current)){
                        departmentCategory.setItemChecked(i, true);
                    }
                }
            }
        }else if(departments != null){
            ArrayList<Long> list = departments;
            int count = departmentCategoryArrayAdapter.getCount();
            for(int k = 0; k < count; k++){
                DepartmentCategory current = departmentCategoryArrayAdapter.getItem(k);
                if(list.contains(current.getId())){
                    departmentCategory.setItemChecked(k, true);
                }
            }
        }
        setSupportActionBar(createToolBar("FNC Mobile::Create/ Edit Ward Intervention Action-Step 2"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void save(){
        if(validate()){
            Intent intent = new Intent(this, ActionRequiredFinalActivity.class);
            intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
            intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
            intent.putExtra("percentageDone", percentageDone);
            intent.putExtra("actionRequired", actionRequired);
            intent.putExtra("departmentCategories", getDepartmentCategory());
            intent.putExtra("district", district);
            intent.putExtra("ward", ward);
            intent.putExtra("period", period);
            intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
            intent.putExtra("indicators", indicators);
            intent.putExtra("interventions", interventions);
            intent.putExtra("microPlan", microPlan);
            intent.putExtra("driverId", driverId);
            intent.putExtra("resourcesNeeded", resourcesNeeded);
            intent.putExtra("driver", driver);
            intent.putExtra("strategyCategories", strategyCategories);
            intent.putExtra("potentialChallenges", potentialChallenges);
            startActivity(intent);
            finish();
        }
    }
    public boolean validate(){
        boolean isValid = true;
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

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            save();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ActionRequiredStep2Activity.this, ActionRequiredStep1Activity.class);
        intent.putExtra("microPlan", microPlan);
        intent.putExtra("ward", ward);
        intent.putExtra("district", district);
        intent.putExtra("period", period);
        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
        intent.putExtra("indicators", indicators);
        intent.putExtra("interventions", interventions);
        intent.putExtra("departmentCategories", getDepartmentCategory());
        intent.putExtra("resourcesNeeded", resourcesNeeded);
        intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
        intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
        intent.putExtra("percentageDone", percentageDone);
        intent.putExtra("actionRequired", actionRequired);
        intent.putExtra("driver", driver);
        intent.putExtra("strategyCategories", strategyCategories);
        intent.putExtra("potentialChallenges", potentialChallenges);
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
