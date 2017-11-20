package zw.co.fnc.mobile.business.domain;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "province")
public class Province extends Model implements Serializable{

    @Expose
    @Column(name = "name")
    public String name;

    @SerializedName("id")
    @Expose
    @Column(name = "serverId")
    public Long serverId;

    public Province(){
        super();
    }

    public static List<Province> getAll(){
        return new Select().from(Province.class).execute();
    }

    public static Province findById(Long Id){
        return new Select()
                .from(Province.class)
                .where("Id =? ", Id)
                .executeSingle();
    }

    public static Province findByServerId(Long Id){
        return new Select()
                .from(Province.class)
                .where("serverId =? ", Id)
                .executeSingle();
    }
    public String toString(){
        return name;
    }


}
