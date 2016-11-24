package color.test.roha.com.colortestproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.androidquery.AQuery;

import java.util.concurrent.TimeUnit;

public class AuthPinConfirmActivity extends AppCompatActivity {
    AQuery aq;
    CountDownTimer timer;
    int init_timer_value = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pin_confirm);

        aq = new AQuery(this);

        String param_country_code = getIntent().getStringExtra("country_code");
        String param_phone_number = getIntent().getStringExtra("phone_number");

        aq.id(R.id.phone_number).text( "+" + param_country_code + " " +  param_phone_number);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTimerText(3 , 0);
        startTimer();

        aq.id(R.id.timer).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        aq.id(R.id.btnSendAuthPinCode).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthPinConfirmActivity.this , ProfileMainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * REQUEST SMS
     */
    private void requestSMS(){

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
}
