package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;

/**
 * Created by Administrator on 2017/5/17.
 */

public class CarColorsAdapter extends BaseAdapter {
    private Drawable[] arrDrawable;
    private String[] arrClors;
    private Context mCotext;
    private LayoutInflater mInflater;
    private RadioButton ObjectRadioButton;
    private int clickTemp = -1;//标识选择的Item
    public CarColorsAdapter(Context mCotext,Drawable[] arrDrawable,String[] arrClors){
        this.mCotext=mCotext;
        this.arrDrawable=arrDrawable;
        this.arrClors=arrClors;
        mInflater= (LayoutInflater) mCotext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arrDrawable.length;
    }

    @Override
    public Object getItem(int position) {
        return arrDrawable[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setSeclection(int i) {
        clickTemp = i;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (view==null){
            view=mInflater.inflate(R.layout.carcolors_fragment_item,parent,false);
            mHolder=new ViewHolder(view);
            view.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) view.getTag();
        }
        mHolder.image_car.setImageDrawable(arrDrawable[position]);
        mHolder.radio_button.setButtonDrawable(android.R.color.transparent);
        mHolder.textView_colors.setText(arrClors[position]);
        final ViewHolder finalMHolder = mHolder;
        mHolder.relativeLayout_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = finalMHolder.textView_colors.getText().toString();
//                if (ObjectRadioButton==null)
//                {
//                    ObjectRadioButton = finalMHolder.radio_button;
//                    ObjectRadioButton.setChecked(true);
//                }
//                else if(ObjectRadioButton != finalMHolder.radio_button) {
//                    ObjectRadioButton.setChecked(!ObjectRadioButton.isChecked());
//                    finalMHolder.radio_button.setChecked(!finalMHolder.radio_button.isChecked());
//                    ObjectRadioButton = finalMHolder.radio_button;
//                }
                mItemOnClickListener.itemOnClickListener(finalMHolder.relativeLayout_color, string);

            }
        });
        return view;
    }
    //点击选择颜色的回调函数
    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener){
        this.mItemOnClickListener = listener;
    }
    public interface ItemOnClickListener{
        public void itemOnClickListener(RelativeLayout selectView,String string);
    }
    class ViewHolder{
        private RadioButton radio_button;
        private ImageView image_car;
        private RelativeLayout relativeLayout_color;
        private TextView textView_colors;
        public ViewHolder(View convertView){
            relativeLayout_color= (RelativeLayout) convertView.findViewById(R.id.relativeLayout_color);
            textView_colors= (TextView) convertView.findViewById(R.id.textView_colors);
            radio_button= (RadioButton) convertView.findViewById(R.id.radio_button);
           image_car= (ImageView) convertView.findViewById(R.id.image_car);
        }
    }
}
