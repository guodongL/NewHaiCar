package com.example.administrator.myhaicar.main.mine.MyCar.Acticity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.ConfirmPopWindowThree;
import com.example.administrator.myhaicar.commond.PhotoUtil;
import com.example.administrator.myhaicar.main.mine.Account.MineDetail.SelectPopuWindow;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.zhy.changeskin.SkinManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import static com.example.administrator.myhaicar.commond.PhotoUtil.equalRatioScale;

public class CarPhotoActivity extends AppCompatActivity {
    private Context mContext=this;
    private ImageView toolBar_back;
    private ImageView image_dravelPhoto;
    private ImageView image_identityPhoto;
    private Button button_addCarInfo;
    private PhotoUtil photoUtil;
    private SelectPopuWindow selsctwindow;
    private ImageView imageView_Object;
    private static final String IMAGE_FILE_NAME1 = "/car_driv.jpg";
    private static final String IMAGE_FILE_NAME2 = "/car_peop.jpg";
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 4;
    private static final int CODE_RESULT_REQUEST = 3;
    private RelativeLayout activity_car_photo;
    public static final int CAMRA_SETRESULT_CODE = 0;
    public static final int PHOTO_SETRESULT_CODE = 1;
    private Uri imageFileUri1;
    private Uri imageFileUri2;
    private String imageFilePath1;
    private String imageFilePath2;
    private   File f;
    private   File f1;
    private  String colors,brand_id,usr_id,car_id;
    private Bitmap photo;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private ProgressDialog dialog;
    private ProgressBar mProgressBar;
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            setTitle("loading...");
           mProgressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onAfter() {
            super.onAfter();
            setTitle("Sample-okHttp");
            mProgressBar.setVisibility(View.GONE);
        }
        @Override
        public void onError(Call call, Exception e) {
            dialog.dismiss();
            Toast.makeText(mContext, "网络链接失败，稍后请重试", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String s) {
            dialog.dismiss();
            //Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext,MyCarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
        setContentView(R.layout.activity_car_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initClick();
    }
    private void initView() {
        Intent intent=this.getIntent();
        car_id = intent.getStringExtra("car_id");
        colors = intent.getStringExtra("colors");
        brand_id = intent.getStringExtra("brand_id");
        usr_id = PreferencesUtils.getString(mContext, "usr_id");
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
        button_addCarInfo= (Button) findViewById(R.id.button_addCarInfo);
        activity_car_photo= (RelativeLayout) findViewById(R.id.activity_car_photo);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        image_dravelPhoto= (ImageView) findViewById(R.id.image_dravelPhoto);
        image_identityPhoto= (ImageView) findViewById(R.id.image_identityPhoto);

    }

    private void initClick() {
        button_addCarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (f == null || f1 == null) {
                    ConfirmPopWindowThree confirmPopWindowtwo = new ConfirmPopWindowThree(mContext,"请上传图片");
                    confirmPopWindowtwo.showAtLocation(activity_car_photo, Gravity.CENTER, 0, 0);
                }else if (car_id==null){
                   HTTP_Addcar();
               } else {
                   HTTP_Updatecar();
                }
            }
        });
        image_dravelPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_Object = image_dravelPhoto;
                selsctwindow = new SelectPopuWindow(mContext, itemsOnClick);
                selsctwindow.showAtLocation(activity_car_photo, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                selsctwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp =getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                //photoUtil=new PhotoUtil(mContext,activity_car_photo,IMAGE_FILE_NAME1,image_dravelPhoto);
            }
        });
        image_identityPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_Object = image_identityPhoto;
                selsctwindow = new SelectPopuWindow(mContext, itemsOnClick);
                selsctwindow.showAtLocation(activity_car_photo, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                selsctwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp =getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                //photoUtil=new PhotoUtil(mContext,activity_car_photo,IMAGE_FILE_NAME2,image_identityPhoto);
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void HTTP_Addcar(){
        Map<String, String> params = new HashMap<>();
        params.put("acts", "add");
        params.put("car_number", " ");
        params.put("car_color", colors);
        params.put("car_srt_id", brand_id);
        params.put("usr_id", usr_id);
        dialog = ProgressDialog.show(mContext, "提示", "上传车辆信息中...");
        OkHttpUtils.post()
                .addFile("car_driv", "updriv.png", f)//
                .addFile("car_peop", "upID.png", f1)//*//*
                .url(UrlConfig.Path.CarManager_URL)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }
    public void HTTP_Updatecar(){
        Map<String, String> params = new HashMap<>();
        params.put("acts", "upd");
        params.put("car_id", car_id);
        params.put("car_number", " ");
        params.put("car_color", colors);
        params.put("car_srt_id", brand_id);
        params.put("usr_id", usr_id);
        dialog = ProgressDialog.show(mContext, "提示", "上传车辆信息中...");
        OkHttpUtils.post()
                .addFile("car_driv", "updriv.png", f)//
                .addFile("car_peop", "upID.png", f1)///*
                .url(UrlConfig.Path.CarManager_URL)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selsctwindow.dismiss();
            switch (view.getId()) {
                //拍照获得头像
                case R.id.btn_take_photo:
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||
                            ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity)mContext,
                                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        takePhoto();
                    }
                    break;
                case R.id.btn_pick_photo:
                    if (ContextCompat.checkSelfPermission(mContext,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity)mContext,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                    } else {
                        choosePhoto();
                    }
                    break;
            }
        }
    };
    private void choosePhoto() {
        // 设置文件类型
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (imageView_Object == image_dravelPhoto) {
            imageFilePath1 = Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME1;
            File tempFile1 = new File(imageFilePath1);
            imageFileUri1 = Uri.fromFile(tempFile1);
           /* if (tempFile1.exists()){
                tempFile1.delete();
            }
            try {
                tempFile1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } else if (imageView_Object == image_identityPhoto) {
            imageFilePath2 = Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME2;
            File tempFile1 = new File(imageFilePath2);
            imageFileUri2 = Uri.fromFile(tempFile1);
        }
//如果你想在Activity中得到新打开Activity关闭后返回的数据，
//你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
        startActivityForResult(intentFromGallery, CAMRA_SETRESULT_CODE);
    }
    // 启动手机相机拍摄照片作为头像
    private void takePhoto() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            if (imageView_Object == image_dravelPhoto) {
                imageFilePath1= Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME1;
                File tempFile1 = new File(imageFilePath1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    imageFileUri1 = FileProvider.getUriForFile(mContext,"com.example.administrator.myhaicar.Fileprovider", tempFile1);
                    intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                }else {
                    /**
                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                     */
                    imageFileUri1 = Uri.fromFile(tempFile1);
                }
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri1);
            } else if (imageView_Object == image_identityPhoto) {
                imageFilePath2 = Environment.getExternalStorageDirectory() + IMAGE_FILE_NAME2;
                File tempFile1 = new File(imageFilePath2);
                imageFileUri2 = Uri.fromFile(tempFile1);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri2);
            }
        }
        startActivityForResult(intentFromCapture, PHOTO_SETRESULT_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {//取消
            //Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CAMRA_SETRESULT_CODE://如果是来自本地的
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;
            case PHOTO_SETRESULT_CODE:
                if (hasSdcard()) {
                    if (imageView_Object==image_dravelPhoto) {
                        imageFilePath1=  Environment.getExternalStorageDirectory()+IMAGE_FILE_NAME1;
                        File tempFile1 = new File(
                                imageFilePath1);
                        cropRawPhoto(Uri.fromFile(tempFile1));
                    }else if (imageView_Object==image_identityPhoto){
                        imageFilePath2=  Environment.getExternalStorageDirectory()+IMAGE_FILE_NAME2;
                        File tempFile2 = new File(
                                imageFilePath2);
                        cropRawPhoto(Uri.fromFile(tempFile2));
                    }
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//设置图片框
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode ==MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (imageView_Object==image_dravelPhoto) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                File tempFile1 = new File(imageFilePath1);
                intent.putExtra("noFaceDetection", false);
                Uri imageFileUri = Uri.fromFile(tempFile1);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(uri, "image/*");
            }else {
                File tempFile1 = new File(imageFilePath1);
                Uri imageFileUri = Uri.fromFile(tempFile1);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
               /* if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    String url = GetImagePath.getPath(mContext, uri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                    intent.setDataAndType(Uri.fromFile(new File(url)), "image*//*");
                }else {*/
                intent.setDataAndType(uri, "image/*");
            }
        }else if (imageView_Object==image_identityPhoto){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                File tempFile2 = new File(imageFilePath2);
                intent.putExtra("noFaceDetection", false);
                Uri imageFileUri = Uri.fromFile(tempFile2);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(uri, "image/*");
            }else {
                File tempFile2 = new File(imageFilePath2);
                Uri imageFileUri = Uri.fromFile(tempFile2);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
               /* if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    String url = GetImagePath.getPath(mContext, uri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                    intent.setDataAndType(Uri.fromFile(new File(url)), "image*//*");
                }else {*/
                intent.setDataAndType(uri, "image/*");

            }
        }
        //把裁剪的数据填入里面
// 设置裁剪
        intent.putExtra("crop", "true");
// aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 2);
// outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 400);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent,CODE_RESULT_REQUEST);
    }
    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     * @param intent
     */
    private void setImageToHeadView(Intent intent) {
        if (intent  != null) {
            try {
                if (imageView_Object==image_dravelPhoto) {
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri1));
                    bitmap = equalRatioScale(imageFilePath1, 600, 400);
                }else if (imageView_Object==image_identityPhoto){
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri2));
                    bitmap1 = equalRatioScale(imageFilePath2, 130, 80);
                }
                imageView_Object.setImageBitmap(photo);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            if (imageView_Object == image_dravelPhoto) {
                File nf = new File(Environment.getExternalStorageDirectory() + "/Ask");
                nf.mkdir();
                //在根目录下面的ASk文件夹下 创建okkk.jpg文件
                f = new File(Environment.getExternalStorageDirectory()+ "/Ask", "car_driv.jpg");
                if (!nf.exists()) {
                    return;
                }
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 95, out);
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (imageView_Object == image_identityPhoto) {
                File nf = new File(Environment.getExternalStorageDirectory() + "/Ask");
                nf.mkdir();
                //在根目录下面的ASk文件夹下 创建okkk.jpg文件
                f1 = new File(Environment.getExternalStorageDirectory() + "/Ask", "car_peop.jpg");
                if(!nf.exists()){
                    //Toast.makeText(AddCarActivity.this, "文件不存在，请修改文件路径",Toast.LENGTH_SHORT).show();
                    return;
                }
                FileOutputStream out1 = null;
                try {
                    out1 = new FileOutputStream(f1);
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 90, out1);
                    try {
                        out1.flush();
                        out1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
// 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

}
