package zw.co.fnc.mobile.business.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaticData {

    public Long serverId;
    public String name;

    public static StaticData fromJson(JSONObject jsonObject) {
        StaticData staticData = new StaticData();
        try {
            staticData.serverId = jsonObject.getLong("id");
            staticData.name =  jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return staticData;
    }

    public static ArrayList<StaticData> fromJson(JSONArray jsonArray) {
        ArrayList<StaticData> list = new ArrayList<StaticData>(jsonArray.length());
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject item = null;
            try {
                item = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            StaticData staticData = StaticData.fromJson(item);
            if (staticData != null) {
                list.add(staticData);
            }
        }

        return list;
    }
}
