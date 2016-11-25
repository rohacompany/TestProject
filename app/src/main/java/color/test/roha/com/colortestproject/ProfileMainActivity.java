package color.test.roha.com.colortestproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.androidquery.AQuery;

import java.io.File;

import static android.R.attr.start;

public class ProfileMainActivity extends BaseActivity {
    public static final int CROP_RESULT = 1000;
    public static final String CROP_IMAGE_PATH = "";
    public static final int PICK_FROM_CAMERA = 2000;
    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        aq = new AQuery(this);

//        runCameraWithCropCircle();

        setupTop();

        aq.id(R.id.ivProfileMain).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileDialog();
            }
        });

        aq.id(R.id.ivProfileMain).image(R.drawable.img_profile_sample);

        openProfileDialog();
    }

    private void openProfileDialog(){
        Intent intent = new Intent(ProfileMainActivity.this , ProfileDialogActivity.class);
        startActivity(intent);
    }

    @Override
    public void setupTop() {
        super.setupTop();
        setTitle(R.string.activity_auth_3_name);
        setStep(3);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void runCameraWithCropCircle(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

// 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICK_FROM_CAMERA:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setType("image/*");
                intent.setData(mImageCaptureUri); // Uri to the image you want to crop
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("circleCrop", new String(""));
                intent.putExtra("return-data", false);
                File cropImageFile = new File(CROP_IMAGE_PATH); // Path to save the cropped image
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropImageFile));
                startActivityForResult(intent, CROP_RESULT); // CROP = Code to track the result in  onActivityResult
                break;
        }
    }
}
