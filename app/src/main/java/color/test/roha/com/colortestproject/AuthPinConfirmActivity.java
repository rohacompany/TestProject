package color.test.roha.com.colortestproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import color.test.roha.com.colortestproject.datas.SMSAuthVO;

public class AuthPinConfirmActivity extends BaseActivity {
    CountDownTimer timer;
    int init_timer_value = 10000;
    String param_country_code,param_phone_number;
    public static final String CS_NUMBER = "07073538495";
    private SMSReceiver _receiver;
    SMSAuthVO _receivedVO;
    private String _received_auth_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pin_confirm);

        aq = new AQuery(this);

        param_country_code = getIntent().getStringExtra("country_code");
        param_phone_number = getIntent().getStringExtra("phone_number");

        aq.id(R.id.phone_number).text( "+" + param_country_code + " " +  param_phone_number);

        setTimerText(3 , 0);
        startTimer();

        aq.id(R.id.timer).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSMS();
                startTimer();
            }
        });

        aq.id(R.id.btnSendAuthPinCode).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmVerification();
            }
        });

        registerReceiver();

        requestSMS();

        setupTop();
    }

    @Override
    public void setupTop() {
        super.setupTop();

        setTitle(R.string.activity_auth_2_name);
        setStep(2);
    }

    /**
     * REQUEST SMS
     */
    private void requestSMS(){
        HashMap<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("version" , "3.0");
        paramMap.put("method" , "sms_auth");
        paramMap.put("rphone" , param_country_code +"-"+ param_phone_number);
        paramMap.put("alert" , "TW");

        aq.ajax(URLManager._MAIN_JSON_QUERY_HOST , paramMap , JSONObject.class , new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                Logger.log("object - > " + object);
                if(object!=null){
                    Gson gson = new Gson();
                    _receivedVO = gson.fromJson(object.toString() , SMSAuthVO.class);
                }
            }
        });
    }

    private void startTimer(){
        //타이머 설정
        timer = new CountDownTimer(init_timer_value, 1000) {
            @Override
            public void onFinish() {
                aq.id(R.id.timer).text(getText(R.string.re_send)).background(R.drawable.img_btn_bg_red);
            }

            @Override
            public void onTick(long millisUntilFinished) {

                long min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));


                Log.v("KTH", min + "," + sec);

                setTimerText(min , sec);

            }
        };
        timer.start();
    }

    private void setTimerText(long min , long sec){
        aq.id(R.id.timer).text(String.format(getString(R.string.auth_pine_code_timeout_format) , min , sec)).background(R.drawable.img_btn_bg_grey);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void registerReceiver() {
        unregisterReceiver();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMSReceiver.ACTION);

        _receiver = new SMSReceiver();
        registerReceiver(_receiver, intentFilter);
    }
    private void unregisterReceiver() {
        if(_receiver != null) {
            unregisterReceiver(_receiver);
            _receiver = null;
        }
    }

    public void confirmVerification(){
        String mPineCode = aq.id(R.id.pin_code).getText().toString();

        if(_receivedVO!=null){
            if(mPineCode.equalsIgnoreCase(_received_auth_code)
                    && mPineCode.equalsIgnoreCase(_receivedVO.getAuth_code())
                    ){
                Intent intent = new Intent(AuthPinConfirmActivity.this , ProfileMainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this , "인증코드가 일치하지 않습니다." , Toast.LENGTH_SHORT).show();
                aq.id(R.id.pin_code).text(null).getView().requestFocus();
            }
        }
    }


    private class SMSReceiver extends BroadcastReceiver {
        private static final String TAG="KTH";
        private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onReceive(Context context, Intent intent) {
            // 문자만 수신받음
            Logger.log("receive intent!!");
            if(intent.getAction().equals(ACTION)) {
                final Bundle bundle = intent.getExtras();

                if(bundle == null)
                    return;

                final Object[] pdusObj = (Object[])bundle.get("pdus");
                if(pdusObj == null)
                    return;

                final SmsMessage[] smsMessages = new SmsMessage[pdusObj.length];

                for(int i=0; i<smsMessages.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[])pdusObj[i]);

                    final String address = smsMessages[i].getOriginatingAddress();
                    final String message = smsMessages[i].getDisplayMessageBody();

                    if(address.equals(CS_NUMBER)) {
//                        String adjustMessage = message.replace("[Web발신]", "");
//                        int begin = adjustMessage.indexOf('[');
//                        int end = adjustMessage.indexOf(']');
//
//                        // 인증번호를 찾았다
//                        final String codeMessage = adjustMessage.substring(begin+1, end);

                        final String codeMessage = removeCharExceptNumber(message);

                        _received_auth_code = codeMessage;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.log("code -> " + codeMessage);
//                                _code.setText(codeMessage);
                                aq.id(R.id.pin_code).text(codeMessage);

                                mHandler.sendEmptyMessageDelayed( 1 , 1000);
                            }
                        });


                    }
                }
            }
        }

        private String removeCharExceptNumber(String str) {
            return str.replaceAll("[^0-9]", "");
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                confirmVerification();
            }
        }
    };

    @Override
    protected void onDestroy() {
        if(_receiver!=null)
            unregisterReceiver();

        super.onDestroy();
    }
}
