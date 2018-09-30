package com.example.administrator.myhaicar.main.mine.Account.MineDetail;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.PhotoUtil;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.utils.HTTP_GD;
import com.example.administrator.myhaicar.utils.PreferencesUtils;
import com.example.administrator.myhaicar.utils.UrlConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.changeskin.SkinManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import static com.example.administrator.myhaicar.commond.PhotoUtil.equalRatioScale;


public class MineDetailActivity extends AppCompatActivity {
    private RadioButton radioButton_man;
    private RadioButton radioButton_woman;
    private boolean flag1=true;
    private boolean flag2=false;
    private SelectPopuWindow selsctwindow;
    //个人资料中的头像图片
    private SimpleDraweeView roundImage_head;
    //头像拍照储存照片的名称
    private  String IMAGE_FILE_NAME1 = "head.jpg";
    private Context mContext=this;
    //拍照和相册的工具类
    private PhotoUtil photoUtil;
    private RelativeLayout activity_mine_detail;
    private static final int CODE_RESULT_REQUEST = 3;
    //跳转到个人ID和分享邀请好友
    private LinearLayout linear_haiCarID;
    //跳转到输入新手机号重新登录
    private LinearLayout linear_loginPhone;
    //返回上一页面
   private LinearLayout linear_name;
    private LinearLayout linear_city;
    private LinearLayout linear_address;
    private LinearLayout linear_age;
    private LinearLayout linear_jobs;
    private LinearLayout linear_details;
    private ImageView toolBar_back;
    private LinearLayout line_man;
    private LinearLayout line_women;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 4;
    private String imageFilePath;
    private Uri imageFileUri;
    private String usr_id;
    private static final int CROP_PHOTO = 5;
    private static final int REQUEST_CODE_PICK_IMAGE = 4;
    private Bitmap photo;
    private Bitmap bitmap;
    private TextView textView_name,textView_haiCarID,textView_phoneNumber,textView_city,textView_adresss,textView_age,textView_job;
    private File file_head;
    private String phone_number;
    public class MyStringCallback extends StringCallback{
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);

            // mProgressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onAfter() {
            super.onAfter();
            HTTP_MyDetail();
            //mProgressBar.setVisibility(View.GONE);
        }
        @Override
        public void onError(Call call, Exception e) {
            Toast.makeText(mContext, "网络链接失败，稍后请重试", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String s) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
        setContentView(R.layout.activity_mine_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initClick();


    }

    @Override
    protected void onResume() {
        super.onResume();
        HTTP_MyDetail();
    }

    private void initView() {
        usr_id=PreferencesUtils.getString(mContext,"usr_id");
        phone_number = PreferencesUtils.getString(mContext, "phone_number");
        String usr_name = PreferencesUtils.getString(mContext, "usr_name");
        String usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");
        String usr_city1 = PreferencesUtils.getString(mContext, "usr_province");
        String usr_city2 = PreferencesUtils.getString(mContext, "usr_city");
        String usr_city3 = PreferencesUtils.getString(mContext, "usr_region");
        String usr_headimgurl = PreferencesUtils.getString(mContext, "usr_headimgurl");
        //String adress = PreferencesUtils.getString(mContext, "usr_addess");
        //String usr_occupation = PreferencesUtils.getString(mContext, "usr_occupation");
        String usr_age = PreferencesUtils.getString(mContext, "usr_ages");
        textView_name= (TextView) findViewById(R.id.textView_name);
        textView_haiCarID= (TextView) findViewById(R.id.textView_haiCarID);
        textView_phoneNumber= (TextView) findViewById(R.id.textView_phoneNumber);
        textView_city= (TextView) findViewById(R.id.textView_city);
        textView_adresss= (TextView) findViewById(R.id.textView_adresss);
        textView_age= (TextView) findViewById(R.id.textView_age);
        textView_job= (TextView) findViewById(R.id.textView_job);
        line_man= (LinearLayout) findViewById(R.id.line_man);
        line_women= (LinearLayout) findViewById(R.id.line_women);
        linear_loginPhone= (LinearLayout) findViewById(R.id.linear_loginPhone);
        linear_haiCarID= (LinearLayout) findViewById(R.id.linear_haiCarID);
        linear_name= (LinearLayout) findViewById(R.id.linear_name);
        linear_city= (LinearLayout) findViewById(R.id.linear_city);
        linear_age= (LinearLayout) findViewById(R.id.linear_age);
        linear_jobs= (LinearLayout) findViewById(R.id.linear_jobs);
        linear_details= (LinearLayout) findViewById(R.id.linear_details);
        toolBar_back= (ImageView) findViewById(R.id.toolBar_back);
        linear_address= (LinearLayout) findViewById(R.id.linear_address);
        radioButton_man = (RadioButton) findViewById(R.id.radioButton_man);
        radioButton_woman = (RadioButton) findViewById(R.id.radioButton_woman);
        roundImage_head= (SimpleDraweeView) findViewById(R.id.roundImage_head);
        activity_mine_detail= (RelativeLayout) findViewById(R.id.activity_mine_detail);
        radioButton_man.setButtonDrawable(android.R.color.transparent);
        radioButton_woman.setButtonDrawable(android.R.color.transparent);

        textView_name.setText(usr_name);
        textView_phoneNumber.setText(phone_number);
        textView_city.setText(usr_city1+"  "+usr_city2+"  "+usr_city3);
        textView_haiCarID.setText(usr_mime_id);
        if (usr_age.equals("0"))
        {
            textView_age.setText("添加您的年龄");
        }
        else
        {
            textView_age.setText(usr_age);
        }
      /*  if (adress.equals("")){
            textView_adresss.setText("请填写具体地址");
        }else {
            textView_adresss.setText(adress);
        }*/
      /*  if (usr_occupation.equals("0"))
        {
            textView_job.setText("添加您的职业");
        }
        else
        {
            textView_job.setText(usr_occupation);
        }*/
        String flag = PreferencesUtils.getString(mContext, "flag");
        if (flag!=null) {
            if (flag.equals("女")) {
                radioButton_woman.setChecked(true);
            } else {
                radioButton_man.setChecked(true);
            }
        }else {
            radioButton_man.setChecked(true);
        }


    }
public void HTTP_MyDetail(){
    OkHttpUtils.post().url(UrlConfig.Path.MyDetail_URL).addParams("usr_id",usr_id).build().execute(new StringCallback() {
        @Override
        public void onError(Call call, Exception e) {
            Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String s) {
            String jsonString=s;
            if (HTTP_GD.IsOrNot_Null(jsonString))
            {
                return;
            }
            MyDetailBean myDetailBean = MyparseJsonToMessageBean(jsonString);
            String usr_addess = myDetailBean.getUsr_addess();
            String usr_name = myDetailBean.getUsr_name();
            String usr_ages = myDetailBean.getUsr_ages();
            String usr_city = myDetailBean.getUsr_city();
            String usr_mime_id = myDetailBean.getUsr_mime_id();
            String usr_headimgurl = myDetailBean.getUsr_headimgurl();
            String usr_occupation = myDetailBean.getUsr_occupation();
            String usr_recommend_id = myDetailBean.getUsr_recommend_id();
            PreferencesUtils.putString(mContext,"usr_recommend_id",usr_recommend_id);
            PreferencesUtils.putString(mContext,"usr_city",usr_city);
            PreferencesUtils.putString(mContext,"usr_ages",usr_ages);
            PreferencesUtils.putString(mContext,"usr_mime_id",usr_mime_id);
            PreferencesUtils.putString(mContext,"usr_occupation",usr_occupation);
            PreferencesUtils.putString(mContext,"usr_name",usr_name);
            PreferencesUtils.putString(mContext,"usr_headimgurl",usr_headimgurl);
            PreferencesUtils.putString(mContext,"usr_addess",usr_addess);
            String usr_province = myDetailBean.getUsr_province();
            String usr_region = myDetailBean.getUsr_region();
            roundImage_head.setImageURI(Uri.parse(usr_headimgurl));
            textView_haiCarID.setText(usr_mime_id);
            textView_city.setText(usr_province+"  "+usr_city+"  "+usr_region);
            if (usr_occupation.equals("0"))
            {
                textView_job.setText("添加您的职业");
            }
            else
            {
                textView_job.setText(usr_occupation);
            }
            textView_name.setText(usr_name);
            if (usr_ages==null||usr_ages.equals("0"))
            {
                textView_age.setText("添加您的年龄");
            }
            else
            {
                textView_age.setText(usr_ages);
            }
            if (usr_addess==null||usr_addess.equals("")){
                textView_adresss.setText("请填写具体地址");
            }else {
                textView_adresss.setText(usr_addess);
            }
            if (usr_occupation==null||usr_occupation.equals("0"))
            {
                textView_job.setText("添加您的职业");
            }
            else
            {
                textView_job.setText(usr_occupation);
            }
        }
    });
}
/*    private void initData() {
        String phone_number = PreferencesUtils.getString(mContext, "phone_number");
        String usr_name = PreferencesUtils.getString(mContext, "usr_name");
        String usr_age = PreferencesUtils.getString(mContext, "usr_ages");
        String usr_sex = PreferencesUtils.getString(mContext, "usr_sex");
        //职业
        String usr_occupation = PreferencesUtils.getString(mContext, "usr_occupation");
        String usr_mime_id = PreferencesUtils.getString(mContext, "usr_mime_id");
        String usr_city1 = PreferencesUtils.getString(mContext, "usr_province");
        String usr_city2 = PreferencesUtils.getString(mContext, "usr_city");
        String usr_city3 = PreferencesUtils.getString(mContext, "usr_region");
        String adress = PreferencesUtils.getString(mContext, "usr_addess");
        String flag = PreferencesUtils.getString(mContext, "flag");
        if (flag!=null) {
            if (flag.equals("女")) {
                radioButton_woman.setChecked(true);
            } else {
                radioButton_man.setChecked(true);
            }
        }else {
            radioButton_man.setChecked(true);
        }
        textView_haiCarID.setText(usr_mime_id);
        textView_city.setText(usr_city1+"  "+usr_city2+"  "+usr_city3);
       if (adress.equals("")){
            textView_adresss.setText("请填写具体地址");
        }else {
            textView_adresss.setText(adress);
        }
        if (usr_occupation.equals("0"))
        {
            textView_job.setText("添加您的职业");
        }
        else
        {
            textView_job.setText(usr_occupation);
        }
        textView_name.setText(usr_name);
        if (usr_age.equals("0"))
        {
            textView_age.setText("添加您的年龄");
        }
        else
        {
            textView_age.setText(usr_age);
        }
        textView_phoneNumber.setText(phone_number);
    }*/
    private void initClick() {
        //跳转到详细资料
        linear_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,DetailsActivity.class);
                startActivity(intent);
            }
        });
        //跳转到个人姓名
        linear_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,NameActivity.class);
                startActivity(intent);
            }
        });
        //跳转到职业
        linear_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,JobsActivity.class);
                startActivity(intent);
            }
        });
        //跳转到年龄
        linear_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,AgeActivity.class);
                startActivity(intent);
            }
        });
        linear_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,ChooseCityActivity.class);
                startActivity(intent);
            }
        });
        //跳转到具体地址
        linear_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,AddressActivity.class);
                startActivity(intent);
            }
        });
        //跳转到修改新手机号
        linear_loginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,NewPhoneLoginActivity.class);
                startActivity(intent);
            }
        });
        //跳转到嗨车ID
        linear_haiCarID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,ShareIdActivity.class);
                startActivity(intent);
            }
        });
        toolBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        roundImage_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsctwindow = new SelectPopuWindow(mContext, itemsOnClick);
                selsctwindow.showAtLocation(activity_mine_detail, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                selsctwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
               // photoUtil=new PhotoUtil(mContext,activity_mine_detail,IMAGE_FILE_NAME1,roundImage_head);
            }
        });
        line_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton_man.setChecked(true);
                if (radioButton_man.isChecked()){
                    radioButton_woman.setChecked(false);
                    flag1=true;
                    flag2=false;
                    OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts","usr_sex").addParams("usr_id",usr_id).addParams("upcont","男").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onResponse(String s) {
                            MineSucceedBean mineSucceedBean = parseJsonToMessageBean(s);
                            String treatedok = mineSucceedBean.getTreatedok();
                            if (treatedok.equals("1")) {
                                PreferencesUtils.putString(mContext,"flag","男");
                            }
                        }
                    });


                }
            }
        });
        line_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton_woman.setChecked(true);
                if (radioButton_woman.isChecked()){
                    radioButton_man.setChecked(false);
                    flag1=false;
                    flag2=true;
                    OkHttpUtils.post().url(UrlConfig.Path.BondID_URL).addParams("acts","usr_sex").addParams("usr_id",usr_id).addParams("upcont","女").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext,"网络获取失败",Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onResponse(String s) {
                            MineSucceedBean mineSucceedBean = parseJsonToMessageBean(s);
                            String treatedok = mineSucceedBean.getTreatedok();
                            if (treatedok.equals("1")) {
                                PreferencesUtils.putString(mContext,"flag","女");
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CROP_PHOTO:
                if (hasSdcard()) {
                    imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.jpg";
                    File tempFile = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(tempFile);
                    cropRawPhoto(imageFileUri);
                } else {
                    Toast.makeText(mContext, "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;
            case REQUEST_CODE_PICK_IMAGE:
                cropRawPhoto(data.getData());
                break;
            case CODE_RESULT_REQUEST:
                if (data != null) {
                    setImageToHeadView(data);
                }
                break;
            default:
                break;
        }
    }
    private void setImageToHeadView(Intent intent) {
        if (intent != null) {
            try {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri));
                bitmap = equalRatioScale(imageFilePath, 170, 95);
                roundImage_head.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File nf = new File(Environment.getExternalStorageDirectory() + "/Aak");
            nf.mkdir();
            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            file_head = new File(Environment.getExternalStorageDirectory() + "/Aak", "head.jpg");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file_head);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("acts", "usr_headimg");
        params.put("usr_id", usr_id);
        OkHttpUtils.post()
                .addFile("upcont", "upfiles.png", file_head)
                .url(UrlConfig.Path.BondID_URL)
                .params(params)
                .build()
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
    void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.jpg";
            File tempFile = new File(imageFilePath);
            imageFileUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);
        }
        startActivityForResult(intent,CROP_PHOTO);
    }
    void choosePhoto() {
       // Intent intentChoose = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        //intentChoose.setType("image/*");
        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.jpg";
        File tempFile1 = new File(imageFilePath);
        imageFileUri = Uri.fromFile(tempFile1);
        startActivityForResult(intentFromGallery, REQUEST_CODE_PICK_IMAGE);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            File tempFile1 = new File(imageFilePath);
            intent.putExtra("noFaceDetection", false);
            Uri imageFileUri = Uri.fromFile(tempFile1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "image/*");
        } else {
            File tempFile1 = new File(imageFilePath);
            Uri imageFileUri = Uri.fromFile(tempFile1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            intent.setDataAndType(uri, "image/*");
        }
        //把裁剪的数据填入里面
// 设置裁剪
        intent.putExtra("crop", "true");
// aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 4);
// outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent,CODE_RESULT_REQUEST);
    }
    //换肤的evenbus回调
    @Subscribe
    public void onEventMainThread(BankName event) {
        String msg =event.getBankName();
        if (msg.equals("1")){
            SkinManager.getInstance().changeSkin("night");


        }else if (msg.equals("2")){
            SkinManager.getInstance().changeSkin("day");
        }

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
// 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    public MineSucceedBean parseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        MineSucceedBean bean = gson.fromJson(jsonString, new TypeToken<MineSucceedBean>() {
        }.getType());
        return bean;
    }
    public MyDetailBean MyparseJsonToMessageBean(String jsonString) {
        Gson gson = new Gson();
        MyDetailBean bean = gson.fromJson(jsonString, new TypeToken<MyDetailBean>() {
        }.getType());
        return bean;
    }
}
