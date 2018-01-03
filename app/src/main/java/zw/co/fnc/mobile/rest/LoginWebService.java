package zw.co.fnc.mobile.rest;

import android.content.Context;
import android.os.AsyncTask;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import zw.co.fnc.mobile.util.AppUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @uthor Tasu Muzinda
 */
public class LoginWebService  extends AsyncTask<String, Integer, String> {

    private OkHttpClient client = new OkHttpClient();
    private Context context;

    public LoginWebService(Context context) {
        this.context = context;
    }

    public HttpUrl URL() {
        return AppUtil.getLoginUrl(context).newBuilder()
                .setQueryParameter("email", AppUtil.getUsername(context))
                .build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            result = run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    private String run() throws IOException {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        Response response=null;
        try {
            Request request = new Request.Builder()
                    .url(URL())
                    .build();
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }catch (SocketTimeoutException e) {
            return "Server Unavailable - Try Again Later";
        }
        return response.body().string();



    }
}
