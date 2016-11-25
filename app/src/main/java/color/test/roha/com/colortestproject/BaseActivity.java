package color.test.roha.com.colortestproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.androidquery.AQuery;

/**
 * Created by dev on 2016-11-25.
 */

public class BaseActivity extends AppCompatActivity{
    AQuery aq;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aq = new AQuery(this);
    }

    public void setupTop(){
        aq.id(R.id.ibtn_common_back).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(int title){
        aq.id(R.id.tv_common_title).text(title);
    }

    public void setStep(int stepNumber){
        Context resContext = null;
        try {
            resContext = createPackageContext(getPackageName(), 0);

            Resources res = resContext.getResources();

            int stepResourceId = res.getIdentifier("@drawable/img_register_step" + stepNumber, "drawable", getPackageName());

            aq.id(R.id.iv_common_top_step).image(stepResourceId);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
