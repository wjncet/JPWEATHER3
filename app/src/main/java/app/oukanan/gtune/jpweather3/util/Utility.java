package app.oukanan.gtune.jpweather3.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            String todayLabel ="";
            String tomorrowLabel ="";
            String today = "";
            String telop = "";
            String max = "";
            String min = "";
            String tomorrow = "";
            String tom_telop = "";
            String tom_max = "";
            String tom_min = "";

            int size = forecasts.length();

            for (int i = 0; i < size; i++) {
                JSONObject info = forecasts.getJSONObject(i);
               // String date = info.getString("date");
                dateLabel = info.getString("dateLabel");

              //  int relt = isThisTime(date); TODO
                if (dateLabel.equals("今日")) {
                    todayLabel = dateLabel;
                    today =info.getString("date");
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

                   // break;
                } else if (dateLabel.equals("明日")) {
                    tomorrowLabel = dateLabel;
                    tomorrow =info.getString("date");
                    tom_telop = info.getString("telop");
                    JSONObject tom_temperature = info.getJSONObject("temperature");
                    try {
                        JSONObject tom_minObj = tom_temperature.getJSONObject("min");
                        tom_min = tom_minObj.getString("celsius");
                    } catch (JSONException e) {
                        tom_min = "--";
                    }
                    try {
                        JSONObject tom_maxObj = tom_temperature.getJSONObject("max");
                        tom_max = tom_maxObj.getString("celsius");
                    } catch (JSONException e) {
                        tom_max = "--";
                    }


                } else {
                    continue;
                }
            }

//=---------------------------------------------------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
            SharedPreferences.Editor editor = PreferenceManager
                    .getDefaultSharedPreferences(context).edit();
            editor.putBoolean("selected", true);
            editor.putString("title", title);
            editor.putString("subCode2", subCode2);
            editor.putString("publicTime", sdf.format(sdf.parse(publicTime)));

            editor.putString("todayLabel", todayLabel);
            editor.putString("min", min);
            editor.putString("max", max);
            editor.putString("telop", telop);
            editor.putString("current_date", today);

            editor.putString("tomorrowLabel", tomorrowLabel);
            editor.putString("tom_min", tom_min);
            editor.putString("tom_max", tom_max);
            editor.putString("tom_telop", tom_telop);
            editor.putString("tomorrow_date", tomorrow);






            editor.commit();
//=---------------------------------------------------
//            saveWeatherInfo(context, title, subCode2, min, max,
//                    telop, publicTime);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


//    public static void saveWeatherInfo(Context context, String title,
//                                       String subCode2, String min, String max, String telop,
//                                       String publicTime) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
//        SharedPreferences.Editor editor = PreferenceManager
//                .getDefaultSharedPreferences(context).edit();
//        editor.putBoolean("selected", true);
//        editor.putString("title", title);
//        editor.putString("subCode2", subCode2);
//        editor.putString("min", min);
//        editor.putString("max", max);
//        editor.putString("telop", telop);
//        editor.putString("publicTime", sdf.format(sdf.parse(publicTime)));
//        editor.putString("current_date", sdf.format(new Date()));
//        editor.commit();
//    }

    private static int isThisTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String param = sdf.format(time);
        String now = sdf.format(new Date());
        if (time.equals(now)) {
            return 0;
        } else if (time.equals(getTomorrow())) {
            return 1;
        }
        return 99;
    }

    private static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        String tomorrowDate
                = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        return tomorrowDate;
    }
}
