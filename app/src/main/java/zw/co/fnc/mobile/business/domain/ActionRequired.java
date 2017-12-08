package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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

    @Expose
    @Column(name = "expected_date_of_completion")
    public Date expectedDateOfCompletion;

    @Expose
    @Column(name = "actual_date_of_completion")
    public Date actualDateOfCompletion;

    @Expose
    @Column(name = "percentage_done")
    public Integer percentageDone;

    @Expose
    public String interventionName;

    @Expose
    public String driverOfStuntingName;

    @Expose
    public List<ResourcesNeededCategory> resourcesNeededCategorys;

    public List<Long> resourceIds;

    @Expose
    public List<DepartmentCategory> departmentCategorys;

    public List<Long> departmentIds;

    @Expose
    public List<StrategyCategory> strategyCategorys;

    @Expose
    public List<PotentialChallengesCategory> potentialChallengesCategorys;

    public ActionRequired() {
        super();
    }

    public ActionRequired(String interventionName, String driverOfStuntingName) {
        this.interventionName = interventionName;
        this.driverOfStuntingName = driverOfStuntingName;
    }

    public static List<ActionRequired> findByKeyProblem(KeyProblem driver){
        return new Select()
                .from(ActionRequired.class)
                .where("key_problem = ?", driver.getId())
                .execute();
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

}
