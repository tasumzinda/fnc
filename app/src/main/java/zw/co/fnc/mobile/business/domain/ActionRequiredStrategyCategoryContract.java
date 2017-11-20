package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "action_required_strategy")
public class ActionRequiredStrategyCategoryContract extends Model {

    @Expose
    @Column(name = "action_required")
    public ActionRequired actionRequired;

    @Expose
    @Column(name = "strategy")
    public StrategyCategory strategyCategory;

    public ActionRequiredStrategyCategoryContract(){

    }

    public static List<ActionRequiredStrategyCategoryContract> findByActionRequired(ActionRequired a){
        return new Select()
                .from(ActionRequiredStrategyCategoryContract.class)
                .where("action_required = ?", a.getId())
                .execute();
    }

}
