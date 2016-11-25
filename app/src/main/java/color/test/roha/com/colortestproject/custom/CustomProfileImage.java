package color.test.roha.com.colortestproject.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import color.test.roha.com.colortestproject.R;

/**
 * Created by dev on 2016-11-25.
 */

public class CustomProfileImage extends RelativeLayout{

    public CustomProfileImage(Context context) {
        super(context);
        init();
    }

    public CustomProfileImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProfileImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){


        setBackgroundResource(R.drawable.img_profile_center);
    }
}
