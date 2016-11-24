package color.test.roha.com.colortestproject.custom;

import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsEventLogger;

import java.util.concurrent.ExecutorService;

import io.fabric.sdk.android.Fabric;

/**
 * Created by dev on 2016-11-22.
 */

public class CustomDigits extends Digits{

    protected CustomDigits(int themeResId, DigitsEventLogger externalLogger) {
        super(themeResId, externalLogger);
    }

    public static CustomDigits getInstance() {
        return (CustomDigits) Fabric.getKit(Digits.class);
    }

    @Override
    protected ExecutorService getExecutorService() {
        return super.getExecutorService();
    }
}
