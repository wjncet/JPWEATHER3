package app.oukanan.gtune.jpweather3.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.oukanan.gtune.jpweather3.R;
import app.oukanan.gtune.jpweather3.util.HttpCallbackListener;
import app.oukanan.gtune.jpweather3.util.HttpUtil;
import app.oukanan.gtune.jpweather3.util.Utility;


/**
 * Created by 王佳楠 on 2016/07/25.
 */
public class WeatherActivity extends Activity implements OnClickListener {

    private LinearLayout weatherInfoLayout;
    private LinearLayout tom_weatherInfoLayout;
    /**
     *
     */
    private TextView cityNameText;
    /**
     *
     */
    private TextView publishText;
    /**
     *
     */
    private TextView tom_weatherDespText;
    /**
     *
     */
    private TextView tom_temp1Text;
    /**
     *
     */
    private TextView tom_temp2Text;
    /**
     *
     */
    private TextView tomorrowDateText;

    /**
     *
     */
    private TextView weatherDespText;
    /**
     *
     */
    private TextView temp1Text;
    /**
     *
     */
    private TextView temp2Text;
    /**
     *
     */
    private TextView currentDateText;

    /**
     *
     */
    private Button switchCity;
    /**
     *
     */
    private Button refreshWeather;

    /**
     *
     */
    private Button rain;

    /**
     *
     */
    private Button clothes;

    /**
     *
     */
    private Button checkBtn;

    /**
     *
     */
    private Button unkonw1Btn;
    /**
     *
     */
    private Button unkonw2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);

        tom_weatherInfoLayout = (LinearLayout) findViewById(R.id.tom_weather_info_layout);
        tom_weatherDespText = (TextView) findViewById(R.id.tom_weather_desp);
        tom_temp1Text = (TextView) findViewById(R.id.tom_temp1);
        tom_temp2Text = (TextView) findViewById(R.id.tom_temp2);
        tomorrowDateText = (TextView) findViewById(R.id.tomorrow_date);

        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        checkBtn = (Button) findViewById(R.id.checkbtn);
        rain = (Button) findViewById(R.id.rain);
        clothes = (Button) findViewById(R.id.clothes);

        String sub2Code = getIntent().getStringExtra("sub2Code");
        if (!TextUtils.isEmpty(sub2Code)) {
            publishText.setText("同期中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            tom_weatherInfoLayout.setVisibility(View.INVISIBLE);
            queryWeatherInfo(sub2Code);
        } else {
            showWeather();
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        checkBtn.setOnClickListener(this);

        rain.setOnClickListener(this);
        clothes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同期中...");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String sub2Code = prefs.getString("subCode2", "");
                if (!TextUtils.isEmpty(sub2Code)) {
                    queryWeatherInfo(sub2Code);
                }
                break;
            case R.id.checkbtn:
                Intent checkIntent = new Intent(WeatherActivity.this, GotoLivedoorActivity.class);
                startActivity(checkIntent);
                break;

            case R.id.rain:

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.rain_layout, (ViewGroup) findViewById(R.id.rain_v_layout));
          //      AlertDialog.Builder customBuilder  = new AlertDialog.Builder(WeatherActivity.this,R.style.CustomAlertDialog);// 自分のstyle
                AlertDialog.Builder customBuilder  = new AlertDialog.Builder(WeatherActivity.this);
//                String[] data = {"未実装中1", "未実装中2", "未実装中3", "未実装中4"};
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
//                ListView listView = (ListView) layout.findViewById(R.id.rain_listView);
//                listView.setAdapter(adapter);
                // dialog.setTitle("降水");
//                  dialog.setView(layout).setNegativeButton("閉じる", null).show();


                TextView rain06= (TextView) layout.findViewById(R.id.rain06);
                TextView umbrella06= (TextView) layout.findViewById(R.id.umbrella06);
                TextView rain612= (TextView) layout.findViewById(R.id.rain612);
                TextView umbrella612= (TextView) layout.findViewById(R.id.umbrella612);
                TextView rain1218= (TextView) layout.findViewById(R.id.rain1218);
                TextView umbrella1218= (TextView) layout.findViewById(R.id.umbrella1218);
                TextView rain1824= (TextView) layout.findViewById(R.id.rain1824);
                TextView umbrella1824= (TextView) layout.findViewById(R.id.umbrella1824);

                SharedPreferences rainPre = PreferenceManager.getDefaultSharedPreferences(this);
                rain06.setText(rainPre.getString("rain06", "APIでは"));
                umbrella06.setText(rainPre.getString("umbrella06", "これらの情報"));
                rain612.setText(rainPre.getString("rain612", "を提供していない"));
                umbrella612.setText(rainPre.getString("umbrella612", ""));
                rain1218.setText(rainPre.getString("rain1218", ""));
                umbrella1218.setText(rainPre.getString("umbrella1218", ""));
                rain1824.setText(rainPre.getString("rain1824", ""));
                umbrella1824.setText(rainPre.getString("umbrella1824", ""));

                final  AlertDialog dialog = customBuilder .setView(layout).create();
                dialog.show();//setPositiveButton("閉じる", null)

                Button colse = (Button)layout.findViewById(R.id.dia_close);
                colse.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


//                Button b = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
//                b.setBackgroundColor(Color.GRAY);
//          //      Log.d("dasf:::: ", "" + getDialog().getWindow());
//                b.setTextColor(Color.WHITE);
//                b.setWidth(500000000);
                break;
          case R.id.clothes:

                break;
            default:
                break;
        }
    }


    private void queryWeatherInfo(String sub2Code) {
        String address = "http://weather.livedoor.com/forecast/webservice/json/v1?city=" + sub2Code;
        queryFromServer(address, sub2Code);
    }


    private void queryFromServer(final String address, final String sub2Code) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Utility.handleWeatherResponse(WeatherActivity.this, response, sub2Code);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });

            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同期失敗");
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
     */
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("title", ""));
        publishText.setText(prefs.getString("publicTime", "") + "発表");

        temp1Text.setText(prefs.getString("min", "") + "℃");
        temp2Text.setText(prefs.getString("max", "") + "℃");
        weatherDespText.setText(prefs.getString("telop", ""));
        currentDateText.setText(prefs.getString("todayLabel", "") + " " + prefs.getString("current_date", ""));

        tom_temp1Text.setText(prefs.getString("tom_min", "") + "℃");
        tom_temp2Text.setText(prefs.getString("tom_max", "") + "℃");
        tom_weatherDespText.setText(prefs.getString("tom_telop", ""));
        tomorrowDateText.setText(prefs.getString("tomorrowLabel", "") + " " + prefs.getString("tomorrow_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
        tom_weatherInfoLayout.setVisibility(View.VISIBLE);
    }
}
