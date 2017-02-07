package com.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/*************************************
 * 功能：二维码工具类
 * 创建者： kim_tony
 * 创建日期：2017/2/6
 * 版权所有：深圳市亿车科技有限公司
 *************************************/

public class QrUtil {

    /****************************************
     * 方法描述：打开相册
     *
     * @param
     * @return
     ****************************************/
    public static void openAlbum(Activity activity) {
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        innerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码");
        activity.startActivityForResult(wrapperIntent, REQUEST_CODE);
    }
     /****************************************
      方法描述：解析相册二维码图片
      @param     path  图片路径
      @return
      ****************************************/
    public static String getStringFromQRCode(String path) {
        return QRCodeDecoder.syncDecodeQRCode(PhotoUtil.getBitmapByFile(path));
    }

    /****************************************
     方法描述：生成二维码图片
     @param     content  二维码内容  size  大小
     @return
     ****************************************/
    public static Bitmap createQrCode(String content,int size) {
        return QRCodeEncoder.syncEncodeQRCode(content,size) ;
    }

    public static String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234ISO8859-1", formart);
            } else {
                formart = str;
                Log.i("1234stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            //TODOAuto-generatedcatchblock
            e.printStackTrace();
        }
        return formart;
    }



}
