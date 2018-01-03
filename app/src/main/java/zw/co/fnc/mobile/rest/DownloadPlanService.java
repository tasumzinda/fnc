package zw.co.fnc.mobile.rest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.util.AppUtil;

import java.io.IOException;
import java.util.List;

/**
 * @uthor Tasu Muzinda
 */
public class DownloadPlanService extends IntentService {

    public static final String NOTIFICATION = "zw.co.fnc";
    private Context context = this;
    public static final String RESULT = "result";

    public DownloadPlanService(){
        super("PullService");
    }

    @Override
    public void onHandleIntent(Intent intent){
        Long periodId = intent.getLongExtra("periodId", 0L);
        Long wardId = intent.getLongExtra("wardId", 0L);
        int result = Activity.RESULT_OK;
        try{
            Long plan = loadMicroPlan(AppUtil.run(AppUtil.getDownloadActionPlanUrl(context, periodId, wardId), this));
            if(plan != 0L){
                loadKeyProblems(AppUtil.run(AppUtil.getDownloadKeyProblemsUrl(context, plan), this));
            }

        }catch (IOException ex){
            ex.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        publishResults(result);

    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    private Long loadMicroPlan(String data) {
        QuarterlyMicroPlan plan = null;
        try {
            JSONObject object = new JSONObject(data);
            plan = QuarterlyMicroPlan.fromJSON(object);
            plan.save();
            Log.d("Saved plan", AppUtil.createGson().toJson(plan));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(plan == null){
            return 0L;
        }
        return plan.serverId;
    }

    private String loadKeyProblems(String data) {
        String msg = "KeyProblem";
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(KeyProblem m : KeyProblem.fromJSON(jsonArray)){
                if(m != null){
                    loadActionRequireds(AppUtil.run(AppUtil.getActionRequiredUrl(context, m.serverId), this));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "InterventionCategory Status Sync Failed";
        }
        return msg;
    }

    private String loadActionRequireds(String data){
        String msg = "ActionRequired";
        try{
            JSONArray jsonArray = new JSONArray(data);
            ActionRequired.fromJSON(jsonArray);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return msg;
    }
}
