package zw.co.fnc.mobile.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.rest.PullService;
import zw.co.fnc.mobile.util.AppUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @uthor Tasu Muzinda
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText userNameField;
    private EditText passwordField;
    private Button button;
    private EditText[] fields;
    private EditText url;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        userNameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        url = (EditText) findViewById(R.id.url);
        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        fields = new EditText[] {userNameField, passwordField};
        url.setText(AppUtil.APP_URL);
    }
    private void loginRemote(){
        String URL = url.getText().toString()+ "login/get-user?userName=" + userNameField.getText().toString();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, URL, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        boolean hasLoggedIn = sharedPreferences.contains(AppUtil.LOGGED_IN);
                        if(! hasLoggedIn){
                            progressDialog.setTitle("Signing in....please wait");
                            progressDialog.setCancelable(false);
                            AppUtil.savePrefs(AppUtil.LOGGED_IN, Boolean.TRUE, getApplicationContext());
                            AppUtil.savePrefs(AppUtil.USERNAME, userNameField.getText().toString(), getApplicationContext());
                            AppUtil.savePrefs(AppUtil.PASSWORD, passwordField.getText().toString(), getApplicationContext());
                            AppUtil.savePrefs(AppUtil.WEB_SERVICE_URL, url.getText().toString(), getApplicationContext());
                            progressDialog.hide();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.createShortNotification(getApplicationContext(), "Incorrect username or password");
                    }
                }){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userNameField.getText().toString(), passwordField.getText().toString()).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        AppUtil.getInstance(getApplicationContext()).getRequestQueue().add(jsObjRequest);
    }


    public void onClick(View view){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(view.getId() == button.getId()){
            if(validate(fields)){
                if(AppUtil.isNetworkAvailable(getApplicationContext())){
                    loginRemote();
                }else if(sharedPreferences.contains("USERNAME")){
                    if(AppUtil.getUsername(this).equals(userNameField.getText().toString()) && AppUtil.getPassword(this).equals(passwordField.getText().toString())){
                        AppUtil.savePrefs(AppUtil.LOGGED_IN, Boolean.TRUE, getApplicationContext());
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }else{
                        AppUtil.createShortNotification(getApplicationContext(), "Wrong username or password");
                    }

                }else{
                    AppUtil.createShortNotification(getApplicationContext(), "No internet connection");
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        onExit();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(PullService.NOTIFICATION));
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void onStop(){
        super.onStop();
    }

}
