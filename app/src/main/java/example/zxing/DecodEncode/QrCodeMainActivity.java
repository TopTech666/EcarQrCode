package example.zxing.DecodEncode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.util.QrUtil;

import java.util.ArrayList;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import example.zxing.R;
import qrcode.ecar.com.albumlib.PickOrTakeImageActivity;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/*************************************
 * 功能： 二维码界面
 * 创建者： kim_tony
 * 创建日期：2017/2/6
 * 版权所有：深圳市亿车科技有限公司
 *************************************/

public class QrCodeMainActivity extends Activity {

    /**
     * 返回的图片的信息  列表
     */
    ArrayList<String> picklist = new ArrayList<String>();
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_main);
        editText = (EditText) findViewById(R.id.et_qrcode);
        imageView = (ImageView) findViewById(R.id.iv_qrcode);
    }

    //打开相册
    public void openAlbum(View view) {

//        QrUtil.openAlbum(this);
        startActivityForResult(new Intent(this, PickOrTakeImageActivity.class), REQUEST_CODE);
    }

    //生成二维码
    public void encode(View view) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Toast.makeText(this, "请输入二维码内容", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = QrUtil.createQrCode(editText.getText().toString(), 1000);
        imageView.setImageBitmap(bitmap);
    }

    //相册回调 识别二维码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                picklist = data.getStringArrayListExtra("data");
                final String photo_path = picklist.get(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // <3> 对返回的解析的Result对象进行判断,获取字符串
                        String result = QrUtil.getStringFromQRCode(photo_path);
                        // String result = decode(photo_path);
                        if (result == null) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "图片格式有误", Toast.LENGTH_SHORT)
                                    .show();
                            Looper.loop();
                        } else {
                            String recode = QrUtil.recode(result.toString());
//                            Intent data = new Intent();
//                            data.putExtra("result", recode);
//                            setResult(300, data);
//                            finish();
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "结果：" + recode, Toast.LENGTH_SHORT)
                                    .show();
                            Looper.loop();

                        }
                    }
                }).start();
            }
        }

    }


}
