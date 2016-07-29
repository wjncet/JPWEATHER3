package app.oukanan.gtune.jpweather3.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by 王佳楠 on 2016/07/29.
 */
public class CustomAlertDialog extends Dialog {

    public CustomAlertDialog(Context context) {
        super(context);
    }

    public CustomAlertDialog(Context context, int themeId) {
        super(context, themeId);
    }

    public static class Builder {

        private Context mContext;
        private String mMessage;
//        private String mTitle, mMessage;
//        private String mPositiveButtonText, mNegativeButtonText;

        private String mButtonText;

//        private OnClickListener mPositiveButtonClickListener,
//                mNegativeButtonClickListener;

        private OnClickListener mButtonClickListener;

        public Builder(Context context) {
            mContext = context;
        }
        public Builder setMessage(int resId) {
            mMessage = (String) mContext.getText(resId);
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setMButton(int positiveButtonTextId,
                                         OnClickListener listener) {
            mButtonText = (String) mContext
                    .getText(positiveButtonTextId);
            mButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            mButtonText = positiveButtonText;
            mButtonClickListener = listener;
            return this;
        }
    }

}
