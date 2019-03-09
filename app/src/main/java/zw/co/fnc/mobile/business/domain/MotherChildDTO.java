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
    @Expose
    public String age;
    @Expose
    public String motherChild;


    public MotherChildDTO(){

    }

    public static MotherChildDTO fromJSON(JSONObject object){
        MotherChildDTO item = new MotherChildDTO();
        item.code = String.valueOf(object.optLong("code"));
        item.name = object.optString("name");
        item.dob = object.optString("dob");
        item.houseHold = object.optString("household");
        item.village = object.optString("village");
        item.ward = object.optString("ward");
        item.district = object.optString("district");
        item.province = object.optString("province");
        item.age = object.optString("age");
        item.motherChild = object.optString("motherChild");
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
