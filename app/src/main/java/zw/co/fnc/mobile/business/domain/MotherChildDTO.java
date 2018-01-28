package zw.co.fnc.mobile.business.domain;

import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @uthor Tasu Muzinda
 */
public class MotherChildDTO implements Serializable{

    @Expose
    public String code;
    @Expose
    public String name;
    @Expose
    public String dob;
    @Expose
    public String houseHold;
    @Expose
    public String village;
    @Expose
    public String ward;
    @Expose
    public String district;
    @Expose
    public String province;


    public MotherChildDTO(){

    }

    public static MotherChildDTO fromJSON(JSONObject object){
        MotherChildDTO item = new MotherChildDTO();
        try{
            item.code = String.valueOf(object.getLong("code"));
            item.name = object.getString("name");
            item.dob = object.getString("dob");
            item.houseHold = object.getString("household");
            item.village = object.getString("village");
            item.ward = object.getString("ward");
            item.district = object.getString("district");
            item.province = object.getString("province");
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public static ArrayList<MotherChildDTO> fromJSON(JSONArray array){
        ArrayList<MotherChildDTO> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
            }catch (JSONException ex){
                ex.printStackTrace();
                continue;
            }
            MotherChildDTO item = fromJSON(object);
            if(item != null){
                list.add(item);
            }
        }
        return list;
    }

}
