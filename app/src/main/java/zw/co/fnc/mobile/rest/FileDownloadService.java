package zw.co.fnc.mobile.rest;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import com.opencsv.CSVWriter;
import com.squareup.okhttp.HttpUrl;
import org.json.JSONArray;
import zw.co.fnc.mobile.business.domain.MotherChildDTO;
import zw.co.fnc.mobile.util.AppUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @uthor Tasu Muzinda
 */
public class FileDownloadService extends IntentService {


    public static final String NOTIFICATION = "zw.co.fnc";
    private Context context = this;
    public static final String RESULT = "result";File directory = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    String saveDir = directory.getPath();

    public FileDownloadService(){
        super("FileDownloadService");
    }

    public void onHandleIntent(Intent intent){
        int result = Activity.RESULT_OK;
        Long ward = intent.getLongExtra("ward", 0L);
        try{
            loadMotherChilds(AppUtil.run(AppUtil.getDownloadMotherChildsUrl(context, ward), this));
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

    private String loadMotherChilds(String data) {
        String msg = "MotherChild";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<MotherChildDTO> list = MotherChildDTO.fromJSON(jsonArray);
            String fileName = "mother_child.csv";
            String filePath = saveDir + File.separator + fileName;
            File file = new File(filePath);
            CSVWriter csvWriter;
            FileWriter fileWriter;
            if(file.exists() && ! file.isDirectory()){
                fileWriter = new FileWriter(filePath, true);
                csvWriter = new CSVWriter(fileWriter);
            }else{
                csvWriter = new CSVWriter(new FileWriter(filePath));
            }
            String [] header = new String[]{"code", "name", "dob", "household", "village", "ward", "district", "province"};
            csvWriter.writeNext(header);
            for (MotherChildDTO item : list) {
                if(item != null){
                    String [] content = new String[]{item.code, item.name, item.dob, item.houseHold, item.village, item.ward, item.district, item.province};
                    csvWriter.writeNext(content);
                }
            }
            csvWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            msg = "MotherChildDTO Status Sync Failed";
        }

        return msg;
    }

}
