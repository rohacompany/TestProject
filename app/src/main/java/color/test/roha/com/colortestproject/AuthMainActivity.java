package color.test.roha.com.colortestproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.androidquery.AQuery;
import com.digits.sdk.android.models.PhoneNumber;

import java.util.Locale;
import java.util.regex.Pattern;

import color.test.roha.com.colortestproject.custom.CountryListSpinner;

import static color.test.roha.com.colortestproject.MainActivity._PERMISSION_REQUEST_CODE;

public class AuthMainActivity extends BaseActivity {
    TextView tvLinkify;
    CountryListSpinner countryListSpinner;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "kc1Das6xy7btT2jyqCg5alErx";
    private static final String TWITTER_SECRET = "hpKwtAeKL5ZWBgnnJfIb76ME6Payxo3sxhzASPQNgP9YPTogxG";
    boolean isTestMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_main);
        aq = new AQuery(this);

        tvLinkify = (TextView)findViewById(R.id.aggrement);
        countryListSpinner = (CountryListSpinner)findViewById(R.id.dgts__countryCode);

        checkPermission();

        setupTop();
    }

    @Override
    public void setupTop() {
        super.setupTop();

        setTitle(R.string.activity_auth_1_name);
        aq.id(R.id.ibtn_common_back).gone();
    }

    private void setLinkfy(){
        String text = getString(R.string.auth_phone_1);
        tvLinkify.setText(text);

        Pattern pattern1 = Pattern.compile("이용약관");
        Pattern pattern2 = Pattern.compile("개인 정보 취급 방침");


        Linkify.addLinks(tvLinkify, pattern1, "agreement1://");
        Linkify.addLinks(tvLinkify, pattern2, "agreement2://");

    }

    public void checkPermission(){
        int permissionCheck1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionCheck2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int permissionCheck3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck4 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck1 == PackageManager.PERMISSION_DENIED
                || permissionCheck2 == PackageManager.PERMISSION_DENIED
                || permissionCheck3 == PackageManager.PERMISSION_DENIED
                || permissionCheck4 == PackageManager.PERMISSION_DENIED
                ){
            ActivityCompat.requestPermissions(this , new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE} ,

                    _PERMISSION_REQUEST_CODE);
        }else{
            setup();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        setup();
    }

    public void setup(){

        setLinkfy();

        final PhoneNumber validPhoneNumber = new PhoneNumber("01064292256", "KR", "82");
        final CountryListSpinner countryListSpinner = (CountryListSpinner) findViewById(R.id.dgts__countryCode);
        countryListSpinner.setSelectedForCountry(new Locale("",
                        validPhoneNumber.getCountryIso()),
                validPhoneNumber.getCountryCode());

        aq.id(R.id.btnSendAuthPinCode).clicked(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String country_code = countryListSpinner.getSelectedCountCode() + "";
                String phone_number = aq.id(R.id.phone_number).getText().toString();

                Log.d("KTH" , country_code + "," + phone_number);

                if(isTestMode){
                    Intent intent = new Intent(AuthMainActivity.this, ProfileMainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(AuthMainActivity.this, AuthPinConfirmActivity.class);
                    intent.putExtra("country_code", country_code);
                    intent.putExtra("phone_number", phone_number);
                    startActivity(intent);
                }
            }
        });

        aq.id(R.id.phone_number).text(getMyPhoneNumber());

    }

    private String getMyPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = mTelephonyMgr.getLine1Number();
        if(phoneNumber.startsWith("82")){
            phoneNumber.replace("82","0");
        }else if(phoneNumber.startsWith("+82")){
            phoneNumber.replace("+82","0");
        }
        return phoneNumber;
    }

    private String getMy10DigitPhoneNumber(String phoneNumber){
        String s = phoneNumber;
        return s.substring(2);
    }


}
