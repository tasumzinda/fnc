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
@Table(name = "potential_challenges_category")
public class PotentialChallengesCategory extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    public PotentialChallengesCategory(){
        super();
    }

    public static List<PotentialChallengesCategory> getAll(){
        return new Select().from(PotentialChallengesCategory.class).execute();
    }

    public static PotentialChallengesCategory findById(Long id){
        return new Select()
                .from(PotentialChallengesCategory.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static PotentialChallengesCategory findByServerId(Long id) {
        return new Select()
                .from(PotentialChallengesCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public static List<PotentialChallengesCategory> findByActionRequired(ActionRequired action){
        return new Select()
                .from(PotentialChallengesCategory.class)
                .innerJoin(ActionRequiredPotentialChallengesCategoryContract.class)
                .on("action_required_potential_challenges.potential_challenges = potential_challenges_category.id")
                .where("action_required_potential_challenges.action_required = ?", action.getId())
                .execute();
    }

    public String toString(){
        return name;
    }
}
