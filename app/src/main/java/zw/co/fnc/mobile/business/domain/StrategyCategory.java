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
 * Created by User on 3/7/2017.
 */
@Table(name = "strategy_category")
public class StrategyCategory extends Model implements Serializable {

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    public StrategyCategory(){
        super();
    }

    public static List<StrategyCategory> getAll(){
        return new Select().from(StrategyCategory.class).execute();
    }

    public static StrategyCategory findById(Long id){
        return new Select()
                .from(StrategyCategory.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static List<StrategyCategory> findByActionRequired(ActionRequired action){
        return new Select()
                .from(StrategyCategory.class)
                .innerJoin(ActionRequiredStrategyCategoryContract.class)
                .on("action_required_strategy.strategy = strategy_category.id")
                .where("action_required_strategy.action_required = ?", action.getId())
                .execute();
    }

    public static StrategyCategory findByServerId(Long id) {
        return new Select()
                .from(StrategyCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public String toString(){
        return name;
    }
}
