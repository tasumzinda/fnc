package zw.co.fnc.mobile.rest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;
import zw.co.fnc.mobile.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmuzinda on 11/4/17.
 */
public class PullService extends IntentService {

    public static final String NOTIFICATION = "zw.co.fnc";
    private Context context = this;
    public static final String RESULT = "result";

    public PullService(){
        super("PullService");
    }

    public void onHandleIntent(Intent intent){
        int result = Activity.RESULT_OK;
        try{
            for(HttpUrl url : getHttpUrls()){
                if(url.equals(AppUtil.getProvinceUrl(context))){
                    loadProvinces(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getDistrictUrl(context))){
                    loadDistricts(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getWardUrl(context))){
                    loadWards(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getPeriodUrl(context))){
                    loadPeriods(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getActionCategoryUrl(context))){
                    loadActionCategories(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getDepartmentCategoryUrl(context))){
                    loadDepartmentCategories(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getIndicatorUrl(context))){
                    loadIndicators(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getKeyProblemCategoryUrl(context))){
                    loadKeyProblemCategories(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getInterventionCategoryUrl(context))){
                    loadInterventionCategories(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getPotentialChallengesCategoryUrl(context))){
                    loadPotentialChallengesCategories(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getResourcesNeededCategoryUrl(context))){
                    loadResourcesNeededCategories(AppUtil.run(url, this));
                }
                if(url.equals(AppUtil.getStrategyCategoryUrl(context))){
                    loadStrategyCategories(AppUtil.run(url, this));
                }
            }
            for(QuarterlyMicroPlan item : QuarterlyMicroPlan.getAll()){
                QuarterlyMicroPlan res = save(run(AppUtil.getPushPlanUrl(context, item.serverId), item), item);
                if(res != null){
                    for(KeyProblem problem : KeyProblem.findByMicroPlan(item)){
                        problem.quarterlyMicroPlan = res;
                        problem.save();
                        problem.indicators = Indicator.findByKeyProblem(problem);
                        problem.interventions = InterventionCategory.findByKeyProblem(problem);
                        KeyProblem prob = save(run(AppUtil.getPushKeyProblemUrl(context, problem.serverId), problem), problem);
                        List<InterventionCategory> list = InterventionCategory.findByKeyProblem(problem);
                        for(InterventionCategory intervention : list){
                            List<ActionRequired> actionRequireds = ActionRequired.findByKeyProblemAndInterventionCategory(problem, intervention);
                            List<ActionRequired> actionRequiredList = new ArrayList<>();
                            for(ActionRequired actionRequired : actionRequireds){
                                actionRequired.keyProblem = problem;
                                actionRequired.resourcesNeededCategorys = ResourcesNeededCategory.findByActionRequired(actionRequired);
                                actionRequired.potentialChallengesCategorys = PotentialChallengesCategory.findByActionRequired(actionRequired);
                                actionRequired.strategyCategorys = StrategyCategory.findByActionRequired(actionRequired);
                                actionRequired.departments = DepartmentCategory.findByActionRequired(actionRequired);
                                actionRequiredList.add(actionRequired);
                                ActionRequired action = save(run(AppUtil.getPushActionRequiredUrl(context, actionRequired.serverId), actionRequired), actionRequired);
                                Log.d("Action", AppUtil.createGson().toJson(action));
                                if(action != null){
                                    for(ActionRequiredPotentialChallengesCategoryContract m : ActionRequiredPotentialChallengesCategoryContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted challenge", m.potentialChallengesCategory.name);
                                    }

                                    for(ActionRequiredDepartmentCategoryContract m : ActionRequiredDepartmentCategoryContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted department", m.departmentCategory.name);
                                    }

                                    for(ActionRequiredResourcesNeededContract m : ActionRequiredResourcesNeededContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted resource", m.resourcesNeededCategory.name);
                                    }

                                    for(ActionRequiredStrategyCategoryContract m : ActionRequiredStrategyCategoryContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted challenge", m.strategyCategory.name);
                                    }
                                    actionRequired.delete();
                                    action.delete();
                                    Log.d("Deleted action", action.actionCategory.name);
                                }
                            }
                        }
                        if(prob != null){
                            for(KeyProblemIndicatorContract m : KeyProblemIndicatorContract.findByKeyProblem(problem)){
                                m.delete();
                                Log.d("Deleted indicator", m.indicator.name);
                            }

                            for(KeyProblemInterventionCategoryContract m : KeyProblemInterventionCategoryContract.findByKeyProblem(problem)){
                                m.delete();
                                Log.d("Deleted indicator", m.interventionCategory.name);
                            }
                            problem.delete();
                            prob.delete();
                            Log.d("Deleted problem", prob.keyProblemCategory.name);
                        }

                    }
                    if(res != null){
                        item.delete();
                        res.delete();
                        Log.d("Deleted plan", item.serverId + "");
                    }
                }else{
                    for(KeyProblem problem : KeyProblem.findByMicroPlan(item)){
                        List<InterventionCategory> list = InterventionCategory.findByKeyProblem(problem);
                        for(InterventionCategory intervention : list){
                            List<ActionRequired> actionRequireds = ActionRequired.findByKeyProblemAndInterventionCategory(problem, intervention);
                            List<ActionRequired> actionRequiredList = new ArrayList<>();
                            for(ActionRequired actionRequired : actionRequireds){
                                    for(ActionRequiredPotentialChallengesCategoryContract m : ActionRequiredPotentialChallengesCategoryContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted challenge", m.potentialChallengesCategory.name);
                                    }

                                    for(ActionRequiredDepartmentCategoryContract m : ActionRequiredDepartmentCategoryContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted department", m.departmentCategory.name);
                                    }

                                    for(ActionRequiredResourcesNeededContract m : ActionRequiredResourcesNeededContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted resource", m.resourcesNeededCategory.name);
                                    }

                                    for(ActionRequiredStrategyCategoryContract m : ActionRequiredStrategyCategoryContract.findByActionRequired(actionRequired)){
                                        m.delete();
                                        Log.d("Deleted challenge", m.strategyCategory.name);
                                    }
                                    actionRequired.delete();
                            }
                        }
                            for(KeyProblemIndicatorContract m : KeyProblemIndicatorContract.findByKeyProblem(problem)){
                                m.delete();
                                Log.d("Deleted indicator", m.indicator.name);
                            }

                            for(KeyProblemInterventionCategoryContract m : KeyProblemInterventionCategoryContract.findByKeyProblem(problem)){
                                m.delete();
                                Log.d("Deleted indicator", m.interventionCategory.name);
                            }
                            problem.delete();

                    }
                    item.delete();
                }


            }
        }catch (Exception ex){
            ex.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        publishResults(result);
    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    public List<HttpUrl> getHttpUrls() {
        List<HttpUrl> static_lists = new ArrayList<>();
        static_lists.add(AppUtil.getProvinceUrl(context));
        static_lists.add(AppUtil.getDistrictUrl(context));
        static_lists.add(AppUtil.getWardUrl(context));
        static_lists.add(AppUtil.getPeriodUrl(context));
        static_lists.add(AppUtil.getActionCategoryUrl(context));
        static_lists.add(AppUtil.getDepartmentCategoryUrl(context));
        static_lists.add(AppUtil.getIndicatorUrl(context));
        static_lists.add(AppUtil.getKeyProblemCategoryUrl(context));
        static_lists.add(AppUtil.getInterventionCategoryUrl(context));
        static_lists.add(AppUtil.getPotentialChallengesCategoryUrl(context));
        static_lists.add(AppUtil.getResourcesNeededCategoryUrl(context));
        static_lists.add(AppUtil.getStrategyCategoryUrl(context));
        return static_lists;
    }

    private String loadProvinces(String data) {
        String msg = "Province";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Province checkDuplicate = Province.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    Province item = new Province();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Province Status Sync Failed";
        }
        return msg;
    }

    private String loadDistricts(String data) {
        String msg = "District";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<District> list = District.fromJSON(jsonArray);
            for (District district : list) {
                District checkDuplicate = District.findByServerId(district.serverId);
                if (checkDuplicate == null) {
                    district.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "District Status Sync Failed";
        }
        return msg;
    }

    private String loadWards(String data) {
        Log.d("Wards", "Test" + data);
        String msg = "Ward";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<Ward> list = Ward.fromJSON(jsonArray);
            for (Ward ward : list) {
                Ward checkDuplicate = Ward.findByServerId(ward.serverId);
                if (checkDuplicate == null) {
                    ward.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Ward Status Sync Failed";
        }
        return msg;
    }

    private String loadPeriods(String data) {
        String msg = "Period";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Period checkDuplicate = Period.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    Period item = new Period();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Period Status Sync Failed";
        }
        return msg;
    }

    private String loadActionCategories(String data) {
        String msg = "ActionCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                ActionCategory checkDuplicate = ActionCategory.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    ActionCategory item = new ActionCategory();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "ActionCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadDepartmentCategories(String data) {
        String msg = "DepartmentCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                DepartmentCategory checkDuplicate = DepartmentCategory.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    DepartmentCategory item = new DepartmentCategory();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "DepartmentCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadIndicators(String data) {
        String msg = "Indicator";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Indicator checkDuplicate = Indicator.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    Indicator item = new Indicator();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Indicator Status Sync Failed";
        }
        return msg;
    }

    private String loadKeyProblemCategories(String data) {
        String msg = "KeyProblemCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                KeyProblemCategory checkDuplicate = KeyProblemCategory.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    KeyProblemCategory item = new KeyProblemCategory();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "KeyProblemCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadInterventionCategories(String data) {
        String msg = "InterventionCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<InterventionCategory> list = InterventionCategory.fromJSON(jsonArray);
            for (InterventionCategory item : list) {
                InterventionCategory checkDuplicate = InterventionCategory.findByServerId(item.serverId);
                if (checkDuplicate == null) {
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "InterventionCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadPotentialChallengesCategories(String data) {
        String msg = "PotentialChallengesCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                PotentialChallengesCategory checkDuplicate = PotentialChallengesCategory.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    PotentialChallengesCategory item = new PotentialChallengesCategory();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "PotentialChallengesCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadResourcesNeededCategories(String data) {
        String msg = "ResourcesNeededCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                ResourcesNeededCategory checkDuplicate = ResourcesNeededCategory.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    ResourcesNeededCategory item = new ResourcesNeededCategory();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "ResourcesNeededCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadStrategyCategories(String data) {
        String msg = "StrategyCategory";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                StrategyCategory checkDuplicate = StrategyCategory.findByServerId(staticData.serverId);
                if (checkDuplicate == null) {
                    StrategyCategory item = new StrategyCategory();
                    item.serverId = staticData.serverId;
                    item.name = staticData.name;
                    item.save();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "StrategyCategory Status Sync Failed";
        }
        return msg;
    }

    private String run(HttpUrl httpUrl, QuarterlyMicroPlan form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    public QuarterlyMicroPlan save(String data, QuarterlyMicroPlan item){
        Log.d("Result", data);
        try{
            Long id = Long.parseLong(data);
            if(id != 0L){
                item.serverId = id;
                item.pushed = 0;
                item.save();
                Log.d("Saved plan", AppUtil.createGson().toJson(item));
            }else{
                return null;
            }

        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    private String run(HttpUrl httpUrl, KeyProblem form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    public KeyProblem save(String data, KeyProblem item){
        Log.d("Key", data);
        try{
            Long id = Long.parseLong(data);
            item.serverId = id;
            item.save();
            Log.d("Saved problem", AppUtil.createGson().toJson(item));
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    private String run(HttpUrl httpUrl, ActionRequired form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        if(form.actualDateOfCompletion != null){
            form.actualDate = DateUtil.formatDateRest(form.actualDateOfCompletion);
        }
        if(form.expectedDateOfCompletion != null){
            form.expectedDate = DateUtil.formatDateRest(form.expectedDateOfCompletion);
        }
        String json =  AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    public ActionRequired save(String data, ActionRequired item){
        Log.d("Action", data);
        try{
            Long id = Long.parseLong(data);
            item.serverId = id;
            item.save();
            Log.d("Saved action", AppUtil.createGson().toJson(item));
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }






















}
