package color.test.roha.com.colortestproject;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ll = (LinearLayout) findViewById(R.id.activity_intro);


        Button btnColor1 = (Button) findViewById(R.id.color1);
        Button btnColor2 = (Button) findViewById(R.id.color2);
        Button btnColor3 = (Button) findViewById(R.id.color3);
        Button btnColor4 = (Button) findViewById(R.id.color4);

        btnColor1.setOnClickListener(this);
        btnColor2.setOnClickListener(this);
        btnColor3.setOnClickListener(this);
        btnColor4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int colorResdId = R.color.intro1;
        switch (v.getId()){
            case R.id.color1:
                colorResdId = R.color.intro1;
                break;
            case R.id.color2:
                colorResdId = R.color.intro2;
                break;
            case R.id.color3:
                colorResdId = R.color.intro3;
                break;
            case R.id.color4:
                colorResdId = R.color.intro4;
                break;
        }

        if(ll!=null){
            ll.setBackgroundColor(getResources().getColor(colorResdId));
        }
    }
}
