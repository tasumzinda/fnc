package zw.co.fnc.mobile.business.domain;

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
import java.util.Date;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "ward")
public class Ward extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "district")
    public District district;

    public Ward(){
        super();
    }

    public static List<Ward> getAll(){
        return new Select().from(Ward.class).execute();
    }

    public static List<Ward> getByDistrict(District d){
        return new Select()
                .from(Ward.class)
                .where("district = ?", d.getId())
                .orderBy("name ASC")
                .execute();
    }

    public static Ward findById(Long Id){
        return new Select()
                .from(Ward.class)
                .where("Id = ?", Id)
                .executeSingle();
    }

    public static Ward findByServerId(Long id) {
        return new Select()
                .from(Ward.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public String toString(){
        return name;
    }

    public static Ward fromJSON(JSONObject object){
        Ward item = new Ward();
        try{
            item.name = object.getString("name");
            item.serverId = object.getLong("id");
            JSONObject district = object.getJSONObject("district");
            item.district = District.findByServerId(district.getLong("id"));
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<Ward> fromJSON(JSONArray array){
        ArrayList<Ward> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            Ward item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }
}
