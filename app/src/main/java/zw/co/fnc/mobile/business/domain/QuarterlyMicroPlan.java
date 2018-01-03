package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONException;
import org.json.JSONObject;
import zw.co.fnc.mobile.business.domain.util.ActionPlanMonth;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "quarterly_micro_plan")
public class QuarterlyMicroPlan extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "period")
    public Period period;

    @Expose
    @Column(name = "ward")
    public Ward ward;

    public District district;
    public List<KeyProblem> keyProblems;

    @Column
    public Integer pushed = 0;

    public QuarterlyMicroPlan(){
        super();
    }


    public String getName() {
        return district.name+" "+ward.name+" : "+period.name+" Action Plan";
    }

    public static QuarterlyMicroPlan getByPeriodAndWard(Period period, Ward ward){
        return new Select()
                .from(QuarterlyMicroPlan.class)
                .where("period = ?", period.getId())
                .and("ward = ?", ward.getId())
                .executeSingle();
    }

    public static List<QuarterlyMicroPlan> findByPushed(){
        return new Select()
                .from(QuarterlyMicroPlan.class)
                .where("pushed = ?", 1)
                .execute();
    }

    public static QuarterlyMicroPlan findById(Long id){
        return new Select()
                .from(QuarterlyMicroPlan.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static QuarterlyMicroPlan findByServerId(Long id){
        return new Select()
                .from(QuarterlyMicroPlan.class)
                .where("serverId = ?", id)
                .executeSingle();
    }

    public static List<QuarterlyMicroPlan> getAll(){
        return new Select()
                .from(QuarterlyMicroPlan.class)
                .execute();
    }

    public static QuarterlyMicroPlan fromJSON(JSONObject object){
        QuarterlyMicroPlan item = new QuarterlyMicroPlan();
        try{
            if( ! object.isNull("period")){
                JSONObject periodObj = object.getJSONObject("period");
                item.period = Period.findByServerId(periodObj.getLong("id"));
            }

            if( ! object.isNull("ward")){
                JSONObject wardObj = object.getJSONObject("ward");
                item.ward = Ward.findByServerId(wardObj.getLong("id"));
            }
            item.serverId = object.getLong("id");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }
}
