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
@Table(name = "resources_needed_category")
public class ResourcesNeededCategory extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    public ResourcesNeededCategory(){
        super();
    }

    public static List<ResourcesNeededCategory> getAll(){
        return new Select().from(ResourcesNeededCategory.class).execute();
    }

    public static ResourcesNeededCategory findById(Long id){
        return new Select()
                .from(ResourcesNeededCategory.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static ResourcesNeededCategory findByServerId(Long id) {
        return new Select()
                .from(ResourcesNeededCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public static List<ResourcesNeededCategory> findByActionRequired(ActionRequired action){
        return new Select()
                .from(ResourcesNeededCategory.class)
                .innerJoin(ActionRequiredResourcesNeededContract.class)
                .on("action_required_resources_needed.resources_needed = resources_needed_category.id")
                .where("action_required_resources_needed.action_required = ?", action.getId())
                .execute();
    }

    public String toString(){
        return name;
    }
}
