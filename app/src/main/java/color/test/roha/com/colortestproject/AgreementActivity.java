package color.test.roha.com.colortestproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.digits.sdk.android.CountryListSpinner;

public class AgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        String link_title = getIntent().getData().toString();
        String title = link_title.substring(link_title.lastIndexOf("/")+1);
        setTitle(title);
        Log.d("KTH" , "link title -> " + link_title);


    }
}
