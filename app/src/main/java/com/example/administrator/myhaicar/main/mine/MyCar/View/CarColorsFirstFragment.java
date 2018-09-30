package com.example.administrator.myhaicar.main.mine.MyCar.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.MyCar.Adapater.CarColorsAdapter;
import com.example.administrator.myhaicar.utils.PreferencesUtils;

/**
 * Created by Administrator on 2017/5/17.
 */

public class CarColorsFirstFragment extends Fragment  {
    private Context mContext;
    private GridView gridview_carColors;
    private Drawable[] arrDrawable;
    private String[] arrColors;
    private String arrColor;
    private boolean flag=false;
    private TextView textView;
    private CarColorsAdapter colorsAdapter;
    private MyListener mListener;
    private boolean skin;
    private  TypedArray typedArray;
    public interface MyListener{
        public void onClickOnClickListener(RelativeLayout view,String string);
    }
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mListener = (MyListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName()
                    +" must implements interface MyListener");
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        skin = PreferencesUtils.getBoolean(mContext, "skin");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.carcolors_firstfragment,container,false);
        gridview_carColors= (GridView) view.findViewById(R.id.gridview_carColors);
        textView= (TextView) view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrColors=getResources().getStringArray(R.array.arrColors1);
        arrDrawable=new Drawable[arrColors.length];
        if (skin){
            typedArray=getResources().obtainTypedArray(R.array.arrCarcolors1night);
        }else {
             typedArray=getResources().obtainTypedArray(R.array.arrCarcolors1day);
        }

        for (int i=0;i<arrColors.length;i++){
            arrDrawable[i]=typedArray.getDrawable(i);
        }
        colorsAdapter=new CarColorsAdapter(mContext,arrDrawable,arrColors);
        colorsAdapter.setmItemOnClickListener(new CarColorsAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(RelativeLayout selectView,String string) {
                mListener.onClickOnClickListener(selectView,string);
            }
        });
        gridview_carColors.setAdapter(colorsAdapter);
        gridview_carColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              colorsAdapter.setSeclection(position);
              colorsAdapter.notifyDataSetChanged();
              arrColor = arrColors[position];
              arrColor = arrColor.replace("色", "");
              flag=true;

          }
      });


    }


}
