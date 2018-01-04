package zw.co.fnc.mobile.business.domain;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.co.fnc.mobile.util.AppUtil;
import zw.co.fnc.mobile.util.DateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tasu Muzinda on 3/7/2017.
 */
@Table(name = "action_required")
public class ActionRequired extends Model implements Serializable{

    @SerializedName("id")
    @Column
    @Expose
    public Long serverId;

    @Expose
    @Column(name = "intervention_category")
    public InterventionCategory interventionCategory;

    @Expose
    @Column(name = "key_problem")
    public KeyProblem keyProblem;

    @Expose
    @Column(name = "action_category")
    public ActionCategory actionCategory;

    @Column(name = "expected_date_of_completion")
    public Date expectedDateOfCompletion;

    @Column(name = "actual_date_of_completion")
    public Date actualDateOfCompletion;

    @Expose
    @Column(name = "percentage_done")
    public Integer percentageDone;

    @Expose
    public String actualDate;

    @Expose
    public String expectedDate;
    @Expose
    public List<ResourcesNeededCategory> resourcesNeededCategorys;

    @Expose
    public List<DepartmentCategory> departments;

    @Expose
    @SerializedName("strategies")
    public List<StrategyCategory> strategyCategorys;

    @Expose
    public List<PotentialChallengesCategory> potentialChallengesCategorys;

    public ActionRequired() {
        super();
    }

    public static List<ActionRequired> findByKeyProblem(KeyProblem driver){
        return new Select()
                .from(ActionRequired.class)
                .where("key_problem = ?", driver.getId())
                .execute();
    }

    public static ActionRequired findByServerId(Long serverId){
        return new Select()
                .from(ActionRequired.class)
                .where("serverId = ?", serverId)
                .executeSingle();
    }

    public static ActionRequired findById(Long Id){
        return new Select()
                .from(ActionRequired.class)
                .where("Id = ?", Id)
                .executeSingle();
    }

    public static List<ActionRequired> findByInterventionCategory(InterventionCategory interventionCategory){
        return new Select()
                .from(ActionRequired.class)
                .where("intervention_category = ?", interventionCategory.getId())
                .execute();
    }

    public static List<ActionRequired> findByKeyProblemAndInterventionCategory(KeyProblem keyProblem, InterventionCategory interventionCategory){
        return new Select()
                .from(ActionRequired.class)
                .where("intervention_category = ?", interventionCategory.getId())
                .where("key_problem = ?", keyProblem.getId())
                .execute();
    }

    public static ActionRequired fromJSON(JSONObject object){
        ActionRequired item = new ActionRequired();
        try{
            item.serverId = object.getLong("id");
            if( ! object.isNull("interventionCategory")){
                JSONObject intervention = object.getJSONObject("interventionCategory");
                item.interventionCategory = InterventionCategory.findByServerId(intervention.getLong("id"));
            }
            if( ! object.isNull("keyProblem")){
                JSONObject problem = object.getJSONObject("keyProblem");
                item.keyProblem = KeyProblem.findByServerId(problem.getLong("id"));
            }
            if( ! object.isNull("actionCategory")){
                JSONObject action = object.getJSONObject("actionCategory");
                item.actionCategory = ActionCategory.findByServerId(action.getLong("id"));
            }
            if( ! object.isNull("actualDate")){
                item.actualDateOfCompletion = DateUtil.getFromString(object.getString("actualDate"));
            }

            if( ! object.isNull("expectedDate")){
                item.expectedDateOfCompletion = DateUtil.getFromString(object.getString("expectedDate"));
            }
            if( ! object.isNull("percentageDone")){
                item.percentageDone = object.getInt("percentageDone");
            }
            item.save();
            Log.d("Saved action", AppUtil.createGson().toJson(item));
            if( ! object.isNull("resourcesNeededCategorys")){
                JSONArray resourceArray = object.getJSONArray("resourcesNeededCategorys");
                for(int i = 0; i < resourceArray.length(); i++){
                    JSONObject resourceObject = resourceArray.getJSONObject(i);
                    ResourcesNeededCategory resource = ResourcesNeededCategory.findByServerId(resourceObject.getLong("id"));
                    ActionRequiredResourcesNeededContract contract = new ActionRequiredResourcesNeededContract();
                    contract.actionRequired = item;
                    contract.resourcesNeededCategory = resource;
                    contract.save();
                    Log.d("Saved resource", AppUtil.createGson().toJson(contract));
                }
            }

            if( ! object.isNull("departments")){
                JSONArray departmentArray = object.getJSONArray("departments");
                for(int i = 0; i < departmentArray.length(); i++){
                    JSONObject departmentObject = departmentArray.getJSONObject(i);
                    DepartmentCategory department = DepartmentCategory.findByServerId(departmentObject.getLong("id"));
                    ActionRequiredDepartmentCategoryContract contract = new ActionRequiredDepartmentCategoryContract();
                    contract.actionRequired = item;
                    contract.departmentCategory = department;
                    contract.save();
                    Log.d("Saved department", AppUtil.createGson().toJson(contract));
                }
            }

            if( ! object.isNull("strategies")){
                JSONArray strategyArray = object.getJSONArray("strategies");
                for(int i = 0; i < strategyArray.length(); i++){
                    JSONObject strategyObject = strategyArray.getJSONObject(i);
                    StrategyCategory strategy = StrategyCategory.findByServerId(strategyObject.getLong("id"));
                    ActionRequiredStrategyCategoryContract contract = new ActionRequiredStrategyCategoryContract();
                    contract.actionRequired = item;
                    contract.strategyCategory = strategy;
                    contract.save();
                    Log.d("Saved strategy", AppUtil.createGson().toJson(contract));
                }
            }

            if( ! object.isNull("potentialChallengesCategorys")){
                JSONArray challengeArray = object.getJSONArray("potentialChallengesCategorys");
                for(int i = 0; i < challengeArray.length(); i++){
                    JSONObject challengeObject = challengeArray.getJSONObject(i);
                    PotentialChallengesCategory challenge = PotentialChallengesCategory.findByServerId(challengeObject.getLong("id"));
                    ActionRequiredPotentialChallengesCategoryContract contract = new ActionRequiredPotentialChallengesCategoryContract();
                    contract.actionRequired = item;
                    contract.potentialChallengesCategory = challenge;
                    contract.save();
                    Log.d("Saved challenge", AppUtil.createGson().toJson(contract));
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<ActionRequired> fromJSON(JSONArray array){
        ArrayList<ActionRequired> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            ActionRequired item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }

}
