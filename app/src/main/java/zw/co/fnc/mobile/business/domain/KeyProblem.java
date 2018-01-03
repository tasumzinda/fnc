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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmuzinda on 11/4/17.
 */
@Table(name = "key_problem")
public class KeyProblem extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "quarterly_micro_plan")
    public QuarterlyMicroPlan quarterlyMicroPlan;

    @Expose
    @Column(name = "key_problem_category")
    public KeyProblemCategory keyProblemCategory;

    @Expose
    public List<Indicator> indicators;

    @Expose
    public List<InterventionCategory> interventions;

    public KeyProblem(){
        super();
    }

    public static List<KeyProblem> findByMicroPlan(QuarterlyMicroPlan quarterlyMicroPlan){
        return new Select()
                .from(KeyProblem.class)
                .where("quarterly_micro_plan = ?", quarterlyMicroPlan.getId())
                .execute();
    }

    public static List<KeyProblem> findByKeyProblemCategory(KeyProblemCategory keyProblemCategory){
        return new Select()
                .from(KeyProblem.class)
                .where("key_problem_category = ?", keyProblemCategory.getId())
                .execute();
    }

    public static KeyProblem findById(Long id){
        return new Select()
                .from(KeyProblem.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static KeyProblem findByServerId(Long serverId){
        return new Select()
                .from(KeyProblem.class)
                .where("serverId = ?", serverId)
                .executeSingle();
    }

    public static List<KeyProblem> getAll(){
        return new Select()
                .from(KeyProblem.class)
                .execute();
    }

    public static KeyProblem fromJSON(JSONObject object){
        KeyProblem item = new KeyProblem();
        try{
            item.serverId = object.getLong("id");
            if( ! object.isNull("quarterlyMicroPlan")){
                JSONObject plan = object.getJSONObject("quarterlyMicroPlan");
                item.quarterlyMicroPlan = QuarterlyMicroPlan.findByServerId(plan.getLong("id"));
            }
            if( ! object.isNull("keyProblemCategory")){
                JSONObject problem = object.getJSONObject("keyProblemCategory");
                item.keyProblemCategory = KeyProblemCategory.findByServerId(problem.getLong("id"));
            }
            item.save();
            Log.d("Saved problem", AppUtil.createGson().toJson(item));
            if( ! object.isNull("indicators")){
                JSONArray indicatorArray = object.getJSONArray("indicators");
                for(int i = 0; i < indicatorArray.length(); i++){
                    JSONObject indicatorObject = indicatorArray.getJSONObject(i);
                    Indicator indicator = Indicator.findByServerId(indicatorObject.getLong("id"));
                    KeyProblemIndicatorContract contract = new KeyProblemIndicatorContract();
                    contract.keyProblem = item;
                    contract.indicator = indicator;
                    contract.save();
                    Log.d("Saved indicator", AppUtil.createGson().toJson(contract));
                }
            }

            if( ! object.isNull("interventions")){
                JSONArray interventionArray = object.getJSONArray("interventions");
                for(int i = 0; i < interventionArray.length(); i++){
                    JSONObject interventionObject = interventionArray.getJSONObject(i);
                    InterventionCategory intervention = InterventionCategory.findByServerId(interventionObject.getLong("id"));
                    KeyProblemInterventionCategoryContract contract = new KeyProblemInterventionCategoryContract();
                    contract.keyProblem = item;
                    contract.interventionCategory = intervention;
                    contract.save();
                    Log.d("Saved Intervention", AppUtil.createGson().toJson(contract));
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<KeyProblem> fromJSON(JSONArray array){
        ArrayList<KeyProblem> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            KeyProblem item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }
}