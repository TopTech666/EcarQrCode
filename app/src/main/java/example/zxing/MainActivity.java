package example.zxing;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;

import example.zxing.ForAcitivity.mCaptureCustomActivity;
import example.zxing.ForFragment.mCaptureFragmentActivity;
import example.zxing.DecodEncode.QrCodeMainActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //调用系统lib包的识别activity
    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);   //识别类型
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    //调用自定义activity
    public void scanBarcodeCustomOptions(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(mCaptureCustomActivity.class);
        integrator.initiateScan();
    }

    //调用fragment识别
    public void sanForFragment(View view) {
        startActivity(new Intent(this, mCaptureFragmentActivity.class));
    }


    /**
     * Sample of scanning from a Fragment
     */
    public static class ScanFragment extends Fragment {

        public ScanFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_scan, container, false);
            Button scan = (Button) view.findViewById(R.id.scan_from_fragment);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanFromFragment();
                }
            });
            return view;
        }

        public void scanFromFragment() {
            IntentIntegrator.forFragment(this).initiateScan();
//            startActivity(new Intent(getActivity(),mCaptureFragmentActivity.class));
        }


    }


    public void qrcodePic(View view) {
        startActivity(new Intent(MainActivity.this, QrCodeMainActivity.class));
    }


}
