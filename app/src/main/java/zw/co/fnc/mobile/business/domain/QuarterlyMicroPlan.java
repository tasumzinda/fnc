package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import zw.co.fnc.mobile.business.domain.util.ActionPlanMonth;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "quarterly_micro_plan")
public class QuarterlyMicroPlan extends Model implements Serializable{

    @Expose
    @Column(name = "period")
    public Period period;

    @Expose
    @Column(name = "ward")
    public Ward ward;

    @Expose
    @Column(name = "action_plan_month")
    public ActionPlanMonth actionPlanMonth;

    @Expose
    public Province province;

    @Expose
    public District district;

    @Expose
    public Boolean actionPlanDone = Boolean.FALSE;

    @Expose
    public List<KeyProblem> keyProblems;

    @Expose
    public Boolean completed = Boolean.FALSE;

    @Expose
    public String name;

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

    public static QuarterlyMicroPlan findById(Long id){
        return new Select()
                .from(QuarterlyMicroPlan.class)
                .where("Id = ?", id)
                .executeSingle();
    }
}
