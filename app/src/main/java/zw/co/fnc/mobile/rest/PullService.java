package zw.co.fnc.mobile.rest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.squareup.okhttp.HttpUrl;
import org.json.JSONArray;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;

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
        for(HttpUrl url : getHttpUrls()){
            try{
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
            }catch (IOException ex){
                ex.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }

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
                    Log.d("Saved province", staticData.name);
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
                Log.d("District", AppUtil.createGson().toJson(district));
                if (checkDuplicate == null) {
                    district.save();
                    Log.d("Saved district", district.name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "District Status Sync Failed";
        }
        return msg;
    }

    private String loadWards(String data) {
        String msg = "Ward";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<Ward> list = Ward.fromJSON(jsonArray);
            for (Ward ward : list) {
                Ward checkDuplicate = Ward.findByServerId(ward.serverId);
                if (checkDuplicate == null) {
                    ward.save();
                    Log.d("Saved ward", ward.name);
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
                    Log.d("Saved period", staticData.name);
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
                    Log.d("Saved action category", staticData.name);
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
                    Log.d("Saved departcategory", staticData.name);
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
                    Log.d("Saved indicator", staticData.name);
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
                    Log.d("Saved key problem", staticData.name);
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
                Log.d("Intervention", AppUtil.createGson().toJson(item));
                InterventionCategory checkDuplicate = InterventionCategory.findByServerId(item.serverId);
                if (checkDuplicate == null) {
                    item.save();
                    Log.d("Saved intervention", item.name);
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
                    Log.d("Saved challenge", staticData.name);
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
                    Log.d("Saved resource", staticData.name);
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
                    Log.d("Saved strategy", staticData.name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "StrategyCategory Status Sync Failed";
        }
        return msg;
    }
}
