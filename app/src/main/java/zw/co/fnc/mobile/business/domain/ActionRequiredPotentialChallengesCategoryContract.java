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
@Table(name = "action_required_potential_challenges")
public class ActionRequiredPotentialChallengesCategoryContract extends Model{

    @Expose
    @Column(name = "action_required")
    public ActionRequired actionRequired;

    @Expose
    @Column(name = "potential_challenges")
    public PotentialChallengesCategory potentialChallengesCategory;

    public ActionRequiredPotentialChallengesCategoryContract(){

    }

    public static List<ActionRequiredPotentialChallengesCategoryContract> findByActionRequired(ActionRequired a){
        return new Select()
                .from(ActionRequiredPotentialChallengesCategoryContract.class)
                .where("action_required = ?", a.getId())
                .execute();
    }
}
