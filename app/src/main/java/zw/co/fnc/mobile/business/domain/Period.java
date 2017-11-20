package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import zw.co.fnc.mobile.business.domain.util.PeriodType;
import zw.co.fnc.mobile.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "period")
public class Period extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Column
    @Expose
    public String name;

    public Period() {
        super();
    }
    public static List<Period> getAll(){
        return new Select().from(Period.class).execute();
    }

    @Override
    public String toString(){
        return name;
    }

    public static Period findById(Long id){
        return new Select()
                .from(Period.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static Period findByServerId(Long id) {
        return new Select()
                .from(Period.class)
                .where("serverId =?", id)
                .executeSingle();
    }

}
