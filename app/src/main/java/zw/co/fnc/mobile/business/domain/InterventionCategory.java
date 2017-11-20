package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "intervention_category")
public class InterventionCategory extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "key_problem_category")
    public KeyProblemCategory keyProblemCategory;

    @Expose
    public List<ActionRequired> actionRequireds = new ArrayList<>();

    public InterventionCategory(){
        super();
    }

    public static List<InterventionCategory> getAll(){
        return new Select().from(InterventionCategory.class).execute();
    }

    public static InterventionCategory findById(Long id){
        return new Select()
                .from(InterventionCategory.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static InterventionCategory findByServerId(Long id){
        return new Select()
                .from(InterventionCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public static List<InterventionCategory> findByKeyProblemCategory(KeyProblemCategory keyProblemCategory){
        return new Select()
                .from(InterventionCategory.class)
                .where("key_problem_category = ?", keyProblemCategory.getId())
                .execute();
    }

    public static List<InterventionCategory> findByKeyProblem(KeyProblem  problem){
        return new Select()
                .from(InterventionCategory.class)
                .innerJoin(KeyProblemInterventionCategoryContract.class)
                .on("key_problem_intervention.intervention = intervention_category.id")
                .where("key_problem_intervention.key_problem = ?", problem.getId())
                .execute();
    }

    public String toString(){
        return name;
    }

    public static InterventionCategory fromJSON(JSONObject object){
        InterventionCategory item = new InterventionCategory();
        try{
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
            JSONObject keyProblemCategory = object.getJSONObject("keyProblemCategory");
            item.keyProblemCategory = KeyProblemCategory.findByServerId(keyProblemCategory.getLong("id"));
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<InterventionCategory> fromJSON(JSONArray array){
        ArrayList<InterventionCategory> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            InterventionCategory item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }
}
