package app.oukanan.gtune.jpweather3.util;

/**
 * Created by 王佳楠 on 2016/07/25.
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}
