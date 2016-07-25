package app.oukanan.gtune.jpweather3.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 王佳楠 on 2016/07/25.
 */
public class Utility {


    /**
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
     */
    public static void handleWeatherResponse(Context context, String response, String subCode2) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            // JSONObject title = jsonObject.getJSONObject("title");

            String title = jsonObject.getString("title");
            String publicTime = jsonObject.getString("publicTime");
            JSONArray forecasts = jsonObject.getJSONArray("forecasts");
            String dateLabel = "";
            String telop = "";
            String max = "";
            String min = "";
            int size = forecasts.length();

            for (int i = 0; i < size; i++) {
                JSONObject info = forecasts.getJSONObject(i);
                String date = info.getString("date");
                if (isThisTime(date)) {
                    dateLabel = info.getString("dateLabel");
                    telop = info.getString("telop");
                    JSONObject temperature = info.getJSONObject("temperature");


                    try {
                        JSONObject minObj = temperature.getJSONObject("min");
                        min = minObj.getString("celsius");
                    } catch (JSONException e) {
                        min = "--";
                    }
                    try {
                        JSONObject maxObj = temperature.getJSONObject("max");
                        max = maxObj.getString("celsius");
                    } catch (JSONException e) {
                        max = "--";
                    }

                    break;
                } else {
                    continue;
                }
            }


            Log.d("title ::: ", forecasts.getJSONObject(0).getString("dateLabel"));


            saveWeatherInfo(context, title, subCode2, min, max,
                    telop, publicTime);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void saveWeatherInfo(Context context, String title,
                                       String subCode2, String min, String max, String telop,
                                       String publicTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putBoolean("selected", true);
        editor.putString("title", title);
        editor.putString("subCode2", subCode2);
        editor.putString("min", min);
        editor.putString("max", max);
        editor.putString("telop", telop);
        editor.putString("publicTime", sdf.format(sdf.parse(publicTime)));
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }

    private static boolean isThisTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String param = sdf.format(time);
        String now = sdf.format(new Date());
        if (time.equals(now)) {
            return true;
        }
        return false;
    }
}
