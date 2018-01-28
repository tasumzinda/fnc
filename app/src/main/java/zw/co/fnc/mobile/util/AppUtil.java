package zw.co.fnc.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 12/14/2016.
 */
public class AppUtil {

    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String APP_URL = "http://192.168.43.234:8084/fnc-mobile/rest/mobile/";
    public static String LOGGED_IN = "LOGGED_IN";
    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String WEB_SERVICE_URL = "WEB_SERVICE_URL";
    private static Gson gson;
    private static AppUtil appInstance;
    private static Context mContext;
    private RequestQueue requestQueue;

    private AppUtil(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized AppUtil getInstance(Context context) {
        if (appInstance == null) {
            appInstance = new AppUtil(context);
        }
        return appInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> request) {
        getRequestQueue().add(request);
    }

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
        return gson;
    }

    public static HttpUrl getLoginUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("login/get-user"));
    }

    public static HttpUrl getProvinceUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/province"));
    }

    public static HttpUrl getDistrictUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/district"));
    }

    public static HttpUrl getWardUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/ward"));
    }

    public static HttpUrl getActionCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/action-category"));
    }

    public static HttpUrl getDepartmentCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/department-category"));
    }

    public static HttpUrl getIndicatorUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/indicator"));
    }

    public static HttpUrl getPeriodUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/period"));
    }

    public static HttpUrl getKeyProblemCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/key-problem-category"));
    }

    public static HttpUrl getInterventionCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/intervention-category"));
    }

    public static HttpUrl getPotentialChallengesCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/potential-challenges-category"));
    }

    public static HttpUrl getResourcesNeededCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/resources-needed-category"));
    }

    public static HttpUrl getStrategyCategoryUrl(Context context) {
        return HttpUrl.parse(getWebService(context).concat("static/strategy-category"));
    }

    public static HttpUrl getPushPlanUrl(Context context, Long id){
        return HttpUrl.parse(getWebService(context).concat("form/action-plan")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }

    public static HttpUrl getPushKeyProblemUrl(Context context, Long id){
        return HttpUrl.parse(getWebService(context).concat("form/key-problem")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }

    public static HttpUrl getPushActionRequiredUrl(Context context, Long id){
        return HttpUrl.parse(getWebService(context).concat("form/action-required")).newBuilder()
                .setQueryParameter("id", String.valueOf(id))
                .build();
    }

    public static HttpUrl getDownloadActionPlanUrl(Context context, Long period, Long ward){
        return HttpUrl.parse(getWebService(context).concat("form/get-plan")).newBuilder()
                .setQueryParameter("period", String.valueOf(period))
                .setQueryParameter("ward", String.valueOf(ward))
                .build();
    }

    public static HttpUrl getDownloadKeyProblemsUrl(Context context, Long plan){
        return HttpUrl.parse(getWebService(context).concat("form/get-key-problem")).newBuilder()
                .setQueryParameter("plan", String.valueOf(plan))
                .build();
    }

    public static HttpUrl getDownloadMotherChildsUrl(Context context, Long ward){
        return HttpUrl.parse(getWebService(context).concat("csv/download")).newBuilder()
                .setQueryParameter("ward", String.valueOf(ward))
                .build();
    }

    public static HttpUrl getActionRequiredUrl(Context context, Long keyProblem){
        return HttpUrl.parse(getWebService(context).concat("form/get-action-required")).newBuilder()
                .setQueryParameter("keyProblem", String.valueOf(keyProblem))
                .build();
    }

    public static String getWebService(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(WEB_SERVICE_URL, "url");
    }

    public static String getUsername(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(USERNAME, "Anonymous");
    }

    public static String getPassword(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PASSWORD, "Anonymous");
    }

    public static Boolean isUserLoggedIn(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(LOGGED_IN, Boolean.FALSE);
    }

    public static void removePrefs(String key, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void savePrefs(String key, Boolean value, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void savePrefs(String key, Long value, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void savePrefs(String key, String value, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void createSnackBarShort(View view, String mgs) {
        Snackbar.make(view, mgs, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public static void createSnackBarLong(View view, String mgs) {
        Snackbar.make(view, mgs, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static OkHttpClient createAuthenticationData(OkHttpClient client, final Context context) {
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) {
                //String credential = Credentials.basic(AppUtil.getUsername(context), AppUtil.getPassword(context));
                String credential = Credentials.basic("jmuzinda@gmail.com", "16-JudgE@84");
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) {
                return null; // Null indicates no attempt to authenticate.
            }
        });
        return client;
    }

    public static OkHttpClient getUnsafeOkHttpClient(OkHttpClient client) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            client.setSslSocketFactory(sslSocketFactory);
            client.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient connectionSettings(OkHttpClient client) {
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        return client;
    }

    public static String run(HttpUrl httpUrl, Context context) throws IOException {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.createAuthenticationData(client, context);
        client = AppUtil.getUnsafeOkHttpClient(client);
        Response response = null;
        try {
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();

            response = client.newCall(request).execute();

            if (AppUtil.responseCount(response) >= 3) {
                return "authentication_error";
            }

            result = response.body().string();
        } catch (SocketTimeoutException e) {
            result = "Server Unavailable - Try Again Later";
        }
        return result;

    }

    public static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public static void createLongNotification(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void createShortNotification(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static String getResponeBody(OkHttpClient client, HttpUrl httpUrl) {
        Response response = null;
        String result = "";
        try {
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();
            response = client.newCall(request).execute();
            if (AppUtil.responseCount(response) >= 3) {
                return "authentication_error";
            }
            result = response.body().string();
        } catch (SocketTimeoutException e) {
            result = "Server Unavailable - Try Again Later";
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    public static String getResponeBody(OkHttpClient client, HttpUrl httpUrl, String json) {
        Response response = null;
        String result = "";
        Log.d("Json", json);
        try {
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .post(AppUtil.getPostBody(json))
                    .build();

            response = client.newCall(request).execute();

            if (AppUtil.responseCount(response) >= 3) {
                return "authentication_error";
            }
            result = response.body().string();
        } catch (SocketTimeoutException e) {
            result = "Server Unavailable - Try Again Later";
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public static RequestBody getPostBody(String json) {
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

}
