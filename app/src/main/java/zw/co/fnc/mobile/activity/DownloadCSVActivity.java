package zw.co.fnc.mobile.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;
import zw.co.fnc.mobile.rest.FileDownloadService;
import zw.co.fnc.mobile.util.AppUtil;

/**
 * @uthor Tasu Muzinda
 */
public class DownloadCSVActivity extends BaseActivity implements View.OnClickListener{

    private Spinner province;
    private Spinner district;
    private Spinner ward;
    private Spinner period;
    private Button search;
    ArrayAdapter<Province> provinceArrayAdapter;
    ArrayAdapter<District> districtArrayAdapter;
    ArrayAdapter<Ward> wardArrayAdapter;
    ArrayAdapter<Period> periodArrayAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        ward = (Spinner) findViewById(R.id.ward);
        period = (Spinner) findViewById(R.id.period);
        search = (Button) findViewById(R.id.btn_search);
        search.setText("Download CSV File");
        search.setOnClickListener(this);
        provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, District.getByProvince((Province) province.getSelectedItem()));
                districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtArrayAdapter);
                districtArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wardArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, Ward.getByDistrict((District) district.getSelectedItem()));
                wardArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ward.setAdapter(wardArrayAdapter);
                wardArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        periodArrayAdapter = new ArrayAdapter<Period>(this, R.layout.simple_spinner_item, Period.getAll());
        periodArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period.setAdapter(periodArrayAdapter);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        if(AppUtil.isNetworkAvailable(this)){
            progressDialog.setTitle("Downloading CSV File.Please wait......");
            progressDialog.setCancelable(true);
            progressDialog.show();
            Intent intent = new Intent(this, FileDownloadService.class);
            intent.putExtra("ward", ((Ward) ward.getSelectedItem()).serverId);
            startService(intent);
        }else{
            AppUtil.createShortNotification(this, "No internet. Check connectivity");
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (bundle != null) {
                int resultCode = bundle.getInt(FileDownloadService.RESULT);
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
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(FileDownloadService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
