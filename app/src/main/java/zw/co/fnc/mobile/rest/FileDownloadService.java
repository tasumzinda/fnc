package zw.co.fnc.mobile.rest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.squareup.okhttp.HttpUrl;
import zw.co.fnc.mobile.util.AppUtil;

import java.io.File;
import java.io.IOException;

/**
 * @uthor Tasu Muzinda
 */
public class FileDownloadService extends IntentService {


    public static final String NOTIFICATION = "zw.co.fnc";
    private Context context = this;
    public static final String RESULT = "result";
    String fileUrl = "https://static.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg";
    File directory = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    String saveDir = directory.getPath();

    public FileDownloadService(){
        super("PullService");
    }

    public void onHandleIntent(Intent intent){
        int result = Activity.RESULT_OK;
        try{
            HttpDownloadUtils.downloadFile(fileUrl, saveDir);
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
}
