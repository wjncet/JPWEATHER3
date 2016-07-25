package app.oukanan.gtune.jpweather3.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import app.oukanan.gtune.jpweather3.R;
import app.oukanan.gtune.jpweather3.lvitem.Area;
import app.oukanan.gtune.jpweather3.lvitem.AreaAdapter;
import app.oukanan.gtune.jpweather3.model.DataMd;
import app.oukanan.gtune.jpweather3.model.Main;
import app.oukanan.gtune.jpweather3.model.Sub1;
import app.oukanan.gtune.jpweather3.model.Sub2;
import app.oukanan.gtune.jpweather3.util.JPWeatherDB;

/**
 * Created by 王佳楠 on 2016/07/25.
 */
public class ChooseAreaActivity extends Activity {

    public static final int LEVEL_MAIN = 0;
    public static final int LEVEL_SUB1 = 1;
    public static final int LEVEL_SUB2 = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private AreaAdapter adapter;
    private JPWeatherDB jpWeatherDB;
    private List<Area> dataList = new ArrayList<Area>();

    /**
     * 省列表
     */
    private List<Main> mainList;
    /**
     * 市列表
     */
    private List<Sub1> sub1List;
    /**
     * 县列表
     */
    private List<Sub2> sub2List;
    /**
     * 选中的省份
     */
    private Main selectedMain;
    /**
     * 选中的城市
     */
    private Sub1 selectedSub1;
    /**
     * 当前选中的级别
     */
    private int currentLevel;
    /**
     * 是否从WeatherActivity中跳转过来。
     */
    private boolean isFromWeatherActivity;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                queryMain();
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);

        adapter = new AreaAdapter(ChooseAreaActivity.this, R.layout.lv_item, dataList);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        jpWeatherDB = JPWeatherDB.getInstance(this);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index,
                                    long arg3) {
                if (currentLevel == LEVEL_MAIN) {
                    selectedMain = mainList.get(index);
                    querySub1();
                } else if (currentLevel == LEVEL_SUB1) {
                    selectedSub1 = sub1List.get(index);
                    querySub2();
                } else if (currentLevel == LEVEL_SUB2) {
                    String sub2Code = sub2List.get(index).getSub2_code();
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra("sub2Code", sub2Code);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryMain();
    }

    private void intData() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                int size = 0;
                size = DataMd.mainArrName.length;
                for (
                        int i = 0;
                        i < size; i++)

                {
                    Main main = new Main();
                    main.setMain_name(DataMd.mainArrName[i]);
                    main.setMain_code(DataMd.mainArrCode[i]);
                    jpWeatherDB.saveMain(main);
                }

                size = DataMd.sub1ArrName.length;
                for (
                        int i = 0;
                        i < size; i++)

                {
                    Sub1 sub1 = new Sub1();
                    sub1.setSub1_name(DataMd.sub1ArrName[i]);
                    sub1.setSub1_code(DataMd.sub1ArrCode[i]);
                    Log.d("sub1ArrCode[i]:::", DataMd.sub1ArrCode[i].substring(0, 2));
                    sub1.setMain_code(DataMd.sub1ArrCode[i].substring(0, 2));
                    jpWeatherDB.saveSub1(sub1);
                }

                size = DataMd.sub2ArrName.length;
                for (
                        int i = 0;
                        i < size; i++)

                {
                    Sub2 sub2 = new Sub2();
                    sub2.setSub2_name(DataMd.sub2ArrName[i]);
                    sub2.setSub2_code(DataMd.sub2ArrCode[i]);
                    sub2.setSub1_code(DataMd.sub2ArrSub1Code[i]);
                    jpWeatherDB.saveSub2(sub2);
                }
                Message msg = new Message();
                msg.what = 100;
                handler.sendMessage(msg);
            }
        }).start();

        closeProgressDialog();

    }

    private void queryMain() {
        showProgressDialog();
        mainList = jpWeatherDB.loadMain();
        if (mainList.size() > 0) {
            dataList.clear();
            for (Main main : mainList) {
                dataList.add(new Area(main.getMain_name()));
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("王佳楠専用");
            currentLevel = LEVEL_MAIN;
            closeProgressDialog();
        } else {
            intData();
        }
    }

    private void querySub1() {
        sub1List = jpWeatherDB.loadSub1(selectedMain.getMain_code());
        if (sub1List.size() > 0) {
            dataList.clear();
            for (Sub1 sub1 : sub1List) {
                dataList.add(new Area(sub1.getSub1_name()));
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedMain.getMain_name());
            currentLevel = LEVEL_SUB1;
            Log.d("querySub1", "DBから取得");
        }

    }

    private void querySub2() {
        sub2List = jpWeatherDB.loadSub2(selectedSub1.getSub1_code());
        if (sub2List.size() > 0) {
            dataList.clear();
            for (Sub2 sub2 : sub2List) {
                dataList.add(new Area(sub2.getSub2_name()));
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedSub1.getSub1_name());
            currentLevel = LEVEL_SUB2;
            Log.d("querySub2", "DBから取得");
        }

    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 捕获Back按键
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_SUB2) {
            querySub1();
        } else if (currentLevel == LEVEL_SUB1) {
            queryMain();
        }
//        else {
//            if (isFromWeatherActivity) {
//                Intent intent = new Intent(this, WeatherActivity.class);
//                startActivity(intent);
//            }
//            finish();
//        }
    }

}
