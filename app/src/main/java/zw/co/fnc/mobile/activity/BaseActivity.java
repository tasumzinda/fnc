package zw.co.fnc.mobile.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.rest.PullService;
import zw.co.fnc.mobile.util.AppUtil;
import zw.co.fnc.mobile.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseActivity extends AppCompatActivity {

    public Context context = this;
    public Menu menu;
    public Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        if (android.R.id.home == id) {
            onBackPressed();
        }

        if (id == R.id.action_home) {
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.action_infor) {
            AppUtil.getAppDialog(context);
            return true;
        }

        if (id == R.id.action_refresh) {
            syncAppData();
            return true;
        }

        if (id == R.id.action_change_password) {
            intent = new Intent(context, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.action_update){
            WVersionManager versionManager = new WVersionManager(this);
            versionManager.setVersionContentUrl("http://update.pzat.org/update/version.txt"); // your update content url, see the response format below
            versionManager.setUpdateUrl("http://update.pzat.org/update/app-release.apk");
            versionManager.setIgnoreThisVersionLabel(" ");
            versionManager.setRemindMeLaterLabel("");
            versionManager.checkVersion();
        }

        if (id == R.id.action_logout) {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AppUtil.loginReset(context);
                            Intent out = new Intent(context, StartActivity.class);
                            out.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(out);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }

        if (id == R.id.action_exit) {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }


        if (id == R.id.action_settings) {
            intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public Toolbar createToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        return toolbar;
    }

    public void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void syncAppData() {
        if (AppUtil.isNetworkAvailable(context)) {
            progressDialog = ProgressDialog.show(this, "Please wait", "Syncing with Server...", true);
            progressDialog.setCancelable(true);
            Intent intent = new Intent(this, PullService.class);
            startService(intent);
        } else {
            AppUtil.createShortNotification(this, "No Internet, Check Connectivity!");
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            updateView();
            if (bundle != null) {
                int resultCode = bundle.getInt(PullService.RESULT);
                if (resultCode == RESULT_OK) {
                    createNotificationDataSync("Sync Success", "Application Data Updated");
                    AppUtil.createShortNotification(context, "Application Data Updated");
                } else {
                    createNotificationDataSync("Sync Fail", "Incomplete Application Data");
                    AppUtil.createShortNotification(context, "Incomplete Application Data");
                }
            }
        }
    };


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(PullService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item_down = menu.findItem(R.id.action_refresh);
        item_down.setVisible(true);
        return true;
    }

    public void createNotificationDataSync(String title, String msg) {
        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(msg).setSmallIcon(R.mipmap.ic_launcher)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }

    public void updateView() {

    }

    public boolean checkDateFormat(String text){
        if (text == null || !text.matches("([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public void updateLabel(Date date, EditText field){
        field.setText(DateUtil.getStringFromDate(date));
    }

    public void onExit() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("no", null).show();
    }
}
