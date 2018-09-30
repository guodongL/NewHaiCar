package com.example.administrator.myhaicar.commond;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.MineDetail.SelectPopuWindow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CarrayDraw on 2015/8/12.
 */
public class PhotoUtil {
    private SelectPopuWindow selsctwindow;
    private Context context;
    public static final int CAMRA_SETRESULT_CODE = 0;
    public static final int PHOTO_SETRESULT_CODE = 1;
    private RelativeLayout main_activity;
    private String name;
    private Bitmap photo;
    private Bitmap bitmap;
    private Uri imageFileUri1;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 4;
    private File file;
    private static final int CODE_RESULT_REQUEST = 3;//最终裁剪后的结果
    private ImageView imageView_Object;


    public PhotoUtil(final Context context, RelativeLayout main_activity, String name, ImageView imageView_Object) {
        this.context = context;
        this.main_activity = main_activity;
        this.name = name;
        this.imageView_Object = imageView_Object;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            //View view=initView();
            /*dialog=new AlertDialog.Builder(context).setTitle("标题").setView(view).create();
            dialog.show();*/
            selsctwindow = new SelectPopuWindow(context, itemsOnClick);
            selsctwindow.showAtLocation(main_activity, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = 0.7f;
            ((Activity) context).getWindow().setAttributes(lp);
            selsctwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((Activity) context).getWindow().setAttributes(lp);
                }
            });

        } else {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }

    //���õ������
    private StateListDrawable getBackGroundColor() {
        Drawable press = new ColorDrawable(0xffd7d7d7);
        Drawable normal = new ColorDrawable(0xffffffff);
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, press);
        drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
        return drawable;
    }

    private OnClickListener itemsOnClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            selsctwindow.dismiss();
            switch (view.getId()) {
                //拍照获得头像
                case R.id.btn_take_photo:
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||
                            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity)context,
                                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                       takePhoto();
                    }
                    break;
                case R.id.btn_pick_photo:
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity)context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                    } else {
                      choosePhoto();
                    }
                    break;
            }
        }
    };
    public void choosePhoto() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");//选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        File tempFile1 = new File(getPhotoPath());
        imageFileUri1 = Uri.fromFile(tempFile1);
        ((Activity) context).startActivityForResult(intentFromGallery, CAMRA_SETRESULT_CODE);
    }
    public void takePhoto(){
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hasSdcard()) {
            File file = new File(getPhotoPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                imageFileUri1 = FileProvider.getUriForFile(context, "com.example.administrator.newhaicar.Fileprovider", file);
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            } else {
                /**
                 * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                 * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                 */
                imageFileUri1 = Uri.fromFile(file);
            }
            intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri1);
        } else {
            Toast.makeText(context, "没有SDCard", Toast.LENGTH_SHORT).show();
        }
        ((Activity) context).startActivityForResult(intent1, PHOTO_SETRESULT_CODE);
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

    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    //����·��
    public String getPhotoPath() {
        if (hasSdcard()) {
            file = new File(Environment.getExternalStorageDirectory() + "/Ask");
            if (!file.exists()) {
                file.mkdirs();
            }
        }else {
            Toast.makeText(context,"没有SDCard", Toast.LENGTH_SHORT).show();
        }
            String path = file.getPath() + name;
            return path;

    }

    public String getCamerPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "/image");
        if (!file.exists()) {
            file.mkdirs();
        }
        String path = file.getPath() + name;
        return path;
    }

    public void setImageToHeadView(Intent intent) {
        if (intent != null) {
            try {
                photo = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFileUri1));
                bitmap = equalRatioScale(getPhotoPath(), 170, 95);
                imageView_Object.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public File getfile() {
        File nf = new File(Environment.getExternalStorageDirectory() + "/Ask");
        nf.mkdir();
        //在根目录下面的ASk文件夹下 创建okkk.jpg文件
        File f = new File(Environment.getExternalStorageDirectory() + "/Ask", name);
        if (!nf.exists()) {
            return nf;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
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
        return f;
    }

    public static Bitmap equalRatioScale(String path, int targetW, int targetH) {
        // 获取option
        BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecodeBounds设置为true,这样使用该option decode出来的Bitmap是null，
        // 只是把长宽存放到option中
        options.inJustDecodeBounds = true;
        // 此时bitmap为null
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int inSampleSize = 1; // 1是不缩放
        // 计算宽高缩放比例
        int inSampleSizeW = options.outWidth / targetW;
        int inSampleSizeH = options.outHeight / targetH;
        // 最终取大的那个为缩放比例，这样才能适配，例如宽缩放3倍才能适配屏幕，而
        // 高不缩放就可以，那样的话如果按高缩放，宽在屏幕内就显示不下了
        if (inSampleSizeW > inSampleSizeH) {
            inSampleSize = inSampleSizeW;
        } else {
            inSampleSize = inSampleSizeH;
        }
        // 设置缩放比例
        options.inSampleSize = 1;
        // 一定要记得将inJustDecodeBounds设为false，否则Bitmap为null
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }
    public static Bitmap readBitmapAutoSize(String filePath) {
        Bitmap bm = null;
        try {

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opt);
            opt.inDither = false;
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inSampleSize = computeSampleSize(opt, -1, 900 * 900);
            opt.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(filePath, opt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static String bitmaptoString(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        byte[] bb = Base64.encode(bytes, Base64.DEFAULT);
        try {
            result = new String(bb, "UTF-8").replace("+", "%2B");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public String getCameraPath(Intent data) {
        Uri originalUri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(originalUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }


}
