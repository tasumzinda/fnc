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
@Table(name = "action_category")
public class ActionCategory extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    public ActionCategory(){
        super();
    }

    public static List<ActionCategory> getAll(){
        return new Select().from(ActionCategory.class).execute();
    }

    public static ActionCategory findById(Long id){
        return new Select()
                .from(ActionCategory.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static ActionCategory findByServerId(Long id){
        return new Select()
                .from(ActionCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public String toString(){
        return name;
    }
}
