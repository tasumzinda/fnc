package zw.co.fnc.mobile.business.domain;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "district")
public class District extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "province")
    public Province province;

    public District(){
        super();
    }

    public static List<District> getAll(){
        return new Select().from(District.class).execute();
    }

    public static List<District> getByProvince(Province province){
        return new Select()
                .from(District.class)
                .where("province = ?", province.getId())
                .orderBy("name ASC")
                .execute();
    }

    public static District findById(Long Id){
        return new Select()
                .from(District.class)
                .where("Id = ?", Id)
                .executeSingle();
    }

    public static District findByServerId(Long id){
        return new Select()
                .from(District.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public String toString(){
        return name;
    }

    public static District fromJSON(JSONObject object){
        District item = new District();
        try{
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
            JSONObject province = object.getJSONObject("province");
            item.province = Province.findByServerId(province.getLong("id"));
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<District> fromJSON(JSONArray array){
        ArrayList<District> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            District item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }
}
