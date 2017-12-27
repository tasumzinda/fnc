package zw.co.fnc.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;
import zw.co.fnc.mobile.util.DateUtil;

import java.util.ArrayList;

/**
 * Created by User on 3/11/2017.
 */
public class ActionRequiredFinalActivity extends BaseActivity implements View.OnClickListener {

    private ListView strategys;
    private ListView challenges;
    private Button save;
    private Long ward;
    private Long period;
    private Long district;
    private Long driverOfStuntingCategory;
    private ArrayList<Indicator> indicators;
    private ArrayList<InterventionCategory> interventions;
    private String expectedDateOfCompletion;
    private String actualDateOfCompletion;
    private String percentageDone;
    private Long actionRequired;
    private ArrayList<Long> resourcesNeeded;
    private ArrayList<Long> departmentCategories;
    Long microPlan;
    private ArrayAdapter<StrategyCategory> strategyCategoryArrayAdapter;
    private ArrayAdapter<PotentialChallengesCategory> potentialChallengesCategoryArrayAdapter;
    QuarterlyMicroPlan item;
    private Long driverId;
    KeyProblem driver;
    private KeyProblem driver1;
    private ArrayList<Long> strategyCategories;
    private ArrayList<Long> potentialChallenges;
    private ArrayList<InterventionCategory> intervention;
    private Long selectedIntervention;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_action_required_final);
        strategys = (ListView) findViewById(R.id.strategy);
        challenges = (ListView) findViewById(R.id.challenges);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        driverId = intent.getLongExtra("driverId", 0L);
        district = intent.getLongExtra("district", 0L);
        ward = intent.getLongExtra("ward", 0L);
        microPlan = intent.getLongExtra("microPlan", 0L);
        period = intent.getLongExtra("period", 0L);
        driver1 = (KeyProblem) intent.getSerializableExtra("driver");
        driverOfStuntingCategory = intent.getLongExtra("driverOfStuntingCategory", 0L);
        indicators = (ArrayList<Indicator>) intent.getSerializableExtra("indicators");
        interventions = (ArrayList<InterventionCategory>) intent.getSerializableExtra("interventions");
        expectedDateOfCompletion = intent.getStringExtra("expectedDateOfCompletion");
        actualDateOfCompletion = intent.getStringExtra("actualDateOfCompletion");
        percentageDone = intent.getStringExtra("percentageDone");
        actionRequired = intent.getLongExtra("actionRequired", 0L);
        selectedIntervention = intent.getLongExtra("selectedIntervention", 0L);
        intervention = (ArrayList<InterventionCategory>) intent.getSerializableExtra("intervention");
        resourcesNeeded = (ArrayList<Long>) intent.getSerializableExtra("resourcesNeeded");
        departmentCategories = (ArrayList<Long>) intent.getSerializableExtra("departmentCategories");
        strategyCategories = (ArrayList<Long>) intent.getSerializableExtra("strategyCategories");
        potentialChallenges = (ArrayList<Long>) intent.getSerializableExtra("potentialChallenges");
        strategyCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, StrategyCategory.getAll());
        strategys.setAdapter(strategyCategoryArrayAdapter);
        potentialChallengesCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, PotentialChallengesCategory.getAll());
        challenges.setAdapter(potentialChallengesCategoryArrayAdapter);
        strategys.setItemsCanFocus(false);
        strategys.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        challenges.setItemsCanFocus(false);
        challenges.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if (driverId != 0L) {
            KeyProblem d = KeyProblem.findById(driverId);
            for (ActionRequired action : ActionRequired.findByKeyProblem(d)) {
                ArrayList<StrategyCategory> list = (ArrayList<StrategyCategory>) StrategyCategory.findByActionRequired(action);
                int count = strategyCategoryArrayAdapter.getCount();
                for (int k = 0; k < count; k++) {
                    StrategyCategory current = strategyCategoryArrayAdapter.getItem(k);
                    if (list.contains(current)) {
                        strategys.setItemChecked(k, true);
                    }
                }
                ArrayList<PotentialChallengesCategory> challengesList = (ArrayList<PotentialChallengesCategory>) PotentialChallengesCategory.findByActionRequired(action);
                count = potentialChallengesCategoryArrayAdapter.getCount();
                for (int k = 0; k < count; k++) {
                    PotentialChallengesCategory current = potentialChallengesCategoryArrayAdapter.getItem(k);
                    if (challengesList.contains(current)) {
                        challenges.setItemChecked(k, true);
                    }
                }
            }
        } else if (strategyCategories != null) {
            ArrayList<Long> list = strategyCategories;
            int count = strategyCategoryArrayAdapter.getCount();
            for (int k = 0; k < count; k++) {
                StrategyCategory current = strategyCategoryArrayAdapter.getItem(k);
                if (list.contains(current.getId())) {
                    strategys.setItemChecked(k, true);
                }
            }

            ArrayList<Long> list1 = potentialChallenges;
            count = potentialChallengesCategoryArrayAdapter.getCount();
            for (int k = 0; k < count; k++) {
                PotentialChallengesCategory current = potentialChallengesCategoryArrayAdapter.getItem(k);
                if (list1.contains(current.getId())) {
                    challenges.setItemChecked(k, true);
                }
            }
        }
        setSupportActionBar(createToolBar("FNC Mobile::Create/ Edit Ward Intervention - Final"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save.setOnClickListener(this);
        for(InterventionCategory m : intervention){
            Log.d("Inter", AppUtil.createGson().toJson(m));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == save.getId()) {
            save();
        }
    }

    public void save() {
        if (isComplete(intervention)) {
            if (microPlan != 0L) {
                item = QuarterlyMicroPlan.findById(microPlan);
            } else {
                item = new QuarterlyMicroPlan();
                item.ward = Ward.findById(ward);
                item.period = Period.findById(period);
                item.save();
            }

            if (driverId != 0L) {
                driver = KeyProblem.findById(driverId);
            } else {
                driver = new KeyProblem();
                driver.keyProblemCategory = KeyProblemCategory.findById(driverOfStuntingCategory);
                driver.quarterlyMicroPlan = item;
                driver.save();
            }

            if (driverId != 0L) {
                for (ActionRequired action : ActionRequired.findByKeyProblem(KeyProblem.findById(driverId))) {
                    for (ActionRequiredPotentialChallengesCategoryContract c : ActionRequiredPotentialChallengesCategoryContract.findByActionRequired(action)) {
                        c.delete();
                        Log.d("Deleted challenge", c.potentialChallengesCategory.name);
                    }
                    for (ActionRequiredResourcesNeededContract c : ActionRequiredResourcesNeededContract.findByActionRequired(action)) {
                        c.delete();
                        Log.d("Deleted resource", c.resourcesNeededCategory.name);
                    }
                    for (ActionRequiredStrategyCategoryContract c : ActionRequiredStrategyCategoryContract.findByActionRequired(action)) {
                        c.delete();
                        Log.d("Deleted strategy", c.strategyCategory.name);
                    }
                    for (ActionRequiredDepartmentCategoryContract c : ActionRequiredDepartmentCategoryContract.findByActionRequired(action)) {
                        c.delete();
                        Log.d("Deleted department", c.departmentCategory.name);
                    }
                    action.delete();
                }
            }

            if (driverId == 0L) {
                for (int i = 0; i < indicators.size(); i++) {
                    KeyProblemIndicatorContract indicatorContract = new KeyProblemIndicatorContract();
                    indicatorContract.keyProblem = driver;
                    indicatorContract.indicator = Indicator.findByServerId(indicators.get(i).serverId);
                    indicatorContract.save();
                }
                for (int i = 0; i < intervention.size(); i++) {
                    KeyProblemInterventionCategoryContract interventionContract = new KeyProblemInterventionCategoryContract();
                    interventionContract.keyProblem = driver;
                    interventionContract.interventionCategory = InterventionCategory.findByServerId(intervention.get(i).serverId);
                    interventionContract.save();
                    for (ActionRequired actionRequired : intervention.get(i).actionRequireds) {
                        Log.d("Action",  AppUtil.createGson().toJson(actionRequired));
                        actionRequired.actionCategory = ActionCategory.findByServerId(actionRequired.actionCategory.serverId);
                        actionRequired.keyProblem = driver;
                        actionRequired.interventionCategory = InterventionCategory.findByServerId(intervention.get(i).serverId);
                        actionRequired.save();
                        Log.d("Saved action", AppUtil.createGson().toJson(actionRequired));
                        for(ResourcesNeededCategory resource : actionRequired.resourcesNeededCategorys){
                            ActionRequiredResourcesNeededContract resourcesNeededContract = new ActionRequiredResourcesNeededContract();
                            resourcesNeededContract.actionRequired = actionRequired;
                            resourcesNeededContract.resourcesNeededCategory = ResourcesNeededCategory.findByServerId(resource.serverId);
                            resourcesNeededContract.save();
                            Log.d("Saved res contract", AppUtil.createGson().toJson(resourcesNeededContract));
                        }
                        for(DepartmentCategory item : actionRequired.departmentCategorys){
                            ActionRequiredDepartmentCategoryContract departmentCategoryContract = new ActionRequiredDepartmentCategoryContract();
                            departmentCategoryContract.actionRequired = actionRequired;
                            departmentCategoryContract.departmentCategory = DepartmentCategory.findByServerId(item.serverId);
                            departmentCategoryContract.save();
                            Log.d("Saved dpt contract", AppUtil.createGson().toJson(departmentCategoryContract));
                        }
                    }
                }
                /*for (InterventionCategory interventionCategory : InterventionCategory.findByKeyProblem(driver)) {
                    Log.d("Interventions", InterventionCategory.findByKeyProblem(driver).size() + " ");
                    ActionRequired action = new ActionRequired();
                    action.actionCategory = ActionCategory.findById(actionRequired);
                    if (!actualDateOfCompletion.equals("")) {
                        action.actualDateOfCompletion = DateUtil.getDateFromString(actualDateOfCompletion);
                    }
                    action.keyProblem = driver;
                    action.expectedDateOfCompletion = DateUtil.getDateFromString(expectedDateOfCompletion);
                    action.interventionCategory = interventionCategory;
                    if (!percentageDone.equals("")) {
                        action.percentageDone = Integer.parseInt(percentageDone);
                    }
                    action.save();
                    for (int i = 0; i < getPotentialChallenges().size(); i++) {
                        ActionRequiredPotentialChallengesCategoryContract challengesCategoryContract = new ActionRequiredPotentialChallengesCategoryContract();
                        challengesCategoryContract.actionRequired = action;
                        challengesCategoryContract.potentialChallengesCategory = PotentialChallengesCategory.findById(getPotentialChallenges().get(i));
                        challengesCategoryContract.save();
                    }
                    for (int i = 0; i < resourcesNeeded.size(); i++) {
                        ActionRequiredResourcesNeededContract resourcesNeededContract = new ActionRequiredResourcesNeededContract();
                        resourcesNeededContract.actionRequired = action;
                        resourcesNeededContract.resourcesNeededCategory = ResourcesNeededCategory.findById(resourcesNeeded.get(i));
                        resourcesNeededContract.save();
                    }
                    for (int i = 0; i < departmentCategories.size(); i++) {
                        ActionRequiredDepartmentCategoryContract departmentCategoryContract = new ActionRequiredDepartmentCategoryContract();
                        departmentCategoryContract.actionRequired = action;
                        departmentCategoryContract.departmentCategory = DepartmentCategory.findById(departmentCategories.get(i));
                        departmentCategoryContract.save();
                    }
                    for (int i = 0; i < getStrategies().size(); i++) {
                        ActionRequiredStrategyCategoryContract strategyCategoryContract = new ActionRequiredStrategyCategoryContract();
                        strategyCategoryContract.actionRequired = action;
                        strategyCategoryContract.strategyCategory = StrategyCategory.findById(getStrategies().get(i));
                        strategyCategoryContract.save();
                    }
                    for (int i = 0; i < intervention.size(); i++) {
                        KeyProblemInterventionCategoryContract interventionContract = new KeyProblemInterventionCategoryContract();
                        interventionContract.keyProblem = driver;
                        interventionContract.interventionCategory = InterventionCategory.findByServerId(intervention.get(i).serverId);
                        interventionContract.save();
                    }
                    for (int i = 0; i < indicators.size(); i++) {
                        KeyProblemIndicatorContract indicatorContract = new KeyProblemIndicatorContract();
                        indicatorContract.keyProblem = driver;
                        indicatorContract.indicator = Indicator.findByServerId(indicators.get(i).serverId);
                        indicatorContract.save();
                    }
                }*/
            }
            Intent intent = new Intent(ActionRequiredFinalActivity.this, LoadActionPlanActivity.class);
            Log.d("microPlan", microPlan + " ");
            if (microPlan != 0L) {
                intent.putExtra("microPlan", microPlan);
            } else {
                intent.putExtra("microPlan", item.getId());
            }
            startActivity(intent);
            finish();
        } else

        {
            Intent intent = new Intent(this, ActionPlanItemActivity.class);
            intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
            intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
            intent.putExtra("percentageDone", percentageDone);
            intent.putExtra("actionRequired", actionRequired);
            intent.putExtra("resourcesNeeded", resourcesNeeded);
            intent.putExtra("district", district);
            intent.putExtra("ward", ward);
            intent.putExtra("period", period);
            intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
            intent.putExtra("indicators", indicators);
            intent.putExtra("interventions", interventions);
            intent.putExtra("microPlan", microPlan);
            intent.putExtra("driverId", driverId);
            intent.putExtra("strategyCategories", getStrategies());
            intent.putExtra("potentialChallenges", getPotentialChallenges());
            intent.putExtra("departmentCategories", departmentCategories);
            intent.putExtra("driver", driver1);
            ArrayList<InterventionCategory> items = new ArrayList<>();
            for (InterventionCategory m : intervention) {
                if (m.serverId.equals(selectedIntervention)) {
                    for (ActionRequired a : m.actionRequireds) {
                        a.potentialChallengesCategorys = getPotentialChallengesCategories();
                        a.strategyCategorys = getStrategyCategories();
                        Log.d("Action", AppUtil.createGson().toJson(a));
                    }
                }
                items.add(m);
            }
            for(InterventionCategory m : items){
                Log.d("Intervention", m.name + " " + AppUtil.createGson().toJson(m));
            }
            intent.putExtra("intervention", items);
            startActivity(intent);
            finish();
        }
    }



    private ArrayList<Long> getPotentialChallenges(){
        ArrayList<Long> a = new ArrayList<>();
        for(int i = 0; i < challenges.getCount(); i++){
            if(challenges.isItemChecked(i)){
                a.add(potentialChallengesCategoryArrayAdapter.getItem(i).getId());
            }else{
                a.remove(potentialChallengesCategoryArrayAdapter.getItem(i).getId());
            }
        }
        return a;
    }

    private ArrayList<Long> getStrategies(){
        ArrayList<Long> a = new ArrayList<>();
        for(int i = 0; i < strategys.getCount(); i++){
            if(strategys.isItemChecked(i)){
                a.add(strategyCategoryArrayAdapter.getItem(i).getId());
            }else{
                a.remove(strategyCategoryArrayAdapter.getItem(i).getId());
            }
        }
        return a;
    }

    private ArrayList<PotentialChallengesCategory> getPotentialChallengesCategories(){
        ArrayList<PotentialChallengesCategory> a = new ArrayList<>();
        for(int i = 0; i < challenges.getCount(); i++){
            if(challenges.isItemChecked(i)){
                a.add(potentialChallengesCategoryArrayAdapter.getItem(i));
            }else{
                a.remove(potentialChallengesCategoryArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private ArrayList<StrategyCategory> getStrategyCategories(){
        ArrayList<StrategyCategory> a = new ArrayList<>();
        for(int i = 0; i < strategys.getCount(); i++){
            if(strategys.isItemChecked(i)){
                a.add(strategyCategoryArrayAdapter.getItem(i));
            }else{
                a.remove(strategyCategoryArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    public void onBackPressed(){
        Intent intent = new Intent(ActionRequiredFinalActivity.this, ActionRequiredStep2Activity.class);
        intent.putExtra("expectedDateOfCompletion", expectedDateOfCompletion);
        intent.putExtra("actualDateOfCompletion", actualDateOfCompletion);
        intent.putExtra("percentageDone", percentageDone);
        intent.putExtra("actionRequired", actionRequired);
        intent.putExtra("resourcesNeeded", resourcesNeeded);
        intent.putExtra("district", district);
        intent.putExtra("ward", ward);
        intent.putExtra("period", period);
        intent.putExtra("driverOfStuntingCategory", driverOfStuntingCategory);
        intent.putExtra("indicators", indicators);
        intent.putExtra("interventions", interventions);
        intent.putExtra("microPlan", microPlan);
        intent.putExtra("driverId", driverId);
        intent.putExtra("strategyCategories", getStrategies());
        intent.putExtra("potentialChallenges", getPotentialChallenges());
        intent.putExtra("departmentCategories", departmentCategories);
        intent.putExtra("driver", driver1);
        ArrayList<InterventionCategory> items = new ArrayList<>();
        for(InterventionCategory m : intervention){
            if(m.serverId.equals(selectedIntervention)){
                for(ActionRequired a : m.actionRequireds){
                    a.potentialChallengesCategorys = getPotentialChallengesCategories();
                    a.strategyCategorys = getStrategyCategories();
                    Log.d("Action", AppUtil.createGson().toJson(a));
                }
            }
            items.add(m);
            Log.d("Inter", AppUtil.createGson().toJson(m));
        }
        intent.putExtra("intervention", items);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean isComplete(ArrayList<InterventionCategory> list){
        boolean isComplete = true;
        for(InterventionCategory item : list){
            if(item.actionRequireds.size() == 0){
                isComplete = false;
                break;
            }
        }
        return isComplete;
    }
}
