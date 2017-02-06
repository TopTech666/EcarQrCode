package example.zxing.ForFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import example.zxing.R;

/*************************************
 * 功能：
 * 创建者： kim_tony
 * 创建日期：2017/2/5
 * 版权所有：深圳市亿车科技有限公司
 *************************************/

public class mCaptureFragment extends Fragment {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_scan, container, true);

        //获取   DecoratedBarcodeView对象
        barcodeScannerView = initializeContent(view);

        //初始化 CaptureManager
        capture = new CaptureManager(this, barcodeScannerView, false);
        //识别回调
        capture.decode(new BarcodeCallback() {
            @Override
            public void barcodeResult(final BarcodeResult result) {
                capture.barcodeView.pause();
                capture.beepManager.playBeepSoundAndVibrate();

                capture.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "扫描结果：" + result.toString(), Toast.LENGTH_SHORT).show();
//                        capture.barcodeView.resume();
                    }
                });
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
        barcodeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture.barcodeView.resume();
            }
        });
        return view;
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent(View view) {
        view.findViewById(R.id.iv_light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTorch();
            }
        });
        return (DecoratedBarcodeView) view.findViewById(R.id.zxing_barcode_scanner);

    }

    @Override
    public void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        capture.onDestroy();
    }



    private  void setTorch(){
        if(isOff) {
            setTorchOff();
        } else{
            setTorchOn();
        }
        isOff=!isOff;

    }

    boolean isOff;
    public void setTorchOff() {
        barcodeScannerView.setTorchOff();
    }


    public void setTorchOn() {
        barcodeScannerView.setTorchOn();
    }


}
