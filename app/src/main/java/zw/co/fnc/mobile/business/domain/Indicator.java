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
@Table(name = "indicator")
public class Indicator extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    public Indicator(){
        super();
    }

    public static List<Indicator> getAll(){
        return new Select().from(Indicator.class).execute();
    }

    public static Indicator findById(Long id){
        return new Select()
                .from(Indicator.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static Indicator findByServerId(Long id){
        return new Select()
                .from(Indicator.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public static List<Indicator> findByKeyProblem(KeyProblem  problem){
        return new Select()
                .from(Indicator.class)
                .innerJoin(KeyProblemIndicatorContract.class)
                .on("key_problem_indicator.indicator = indicator.id")
                .where("key_problem_indicator.key_problem = ?", problem.getId())
                .execute();
    }

    public String toString(){
        return name;
    }
}
