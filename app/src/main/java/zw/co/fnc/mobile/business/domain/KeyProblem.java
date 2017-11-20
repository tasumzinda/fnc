package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmuzinda on 11/4/17.
 */
@Table(name = "key_problem")
public class KeyProblem extends Model implements Serializable{

    @Expose
    @Column(name = "quarterly_micro_plan")
    public QuarterlyMicroPlan quarterlyMicroPlan;

    @Expose
    @Column(name = "key_problem_category")
    public KeyProblemCategory keyProblemCategory;

    public ArrayList<Indicator> indicators;

    public ArrayList<Long> indicatorsId;

    public ArrayList<InterventionCategory> interventions;

    public ArrayList<Long> interventionsId;

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
}