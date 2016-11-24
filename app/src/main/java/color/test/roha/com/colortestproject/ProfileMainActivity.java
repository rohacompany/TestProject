package color.test.roha.com.colortestproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.io.File;

public class ProfileMainActivity extends AppCompatActivity {
    public static final int CROP_RESULT = 1000;
    public static final String CROP_IMAGE_PATH = "";
    public static final int PICK_FROM_CAMERA = 2000;
    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        runCameraWithCropCircle();

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
