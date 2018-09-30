package com.example.administrator.myhaicar.main.order.This.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;


;import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;

import org.greenrobot.eventbus.EventBus;

public class ConfirmPopWindowApp extends PopupWindow{
	private Context context;
	private TextView titleTv,contentTv;
	private View okBtn;
	private String title1;
	private String title2;
	private OnDialogClickListener dialogClickListener;

	public ConfirmPopWindowApp(Context context,String title1,String title2) {
		super(context);
		this.context = context;
		this.title1=title1;
		this.title2=title2;
		initalize();
	}

	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_dialog_login, null);
		this.setContentView(view);
		initWindow();
		titleTv = (TextView) view.findViewById(R.id.title_name);
		contentTv = (TextView)  view.findViewById(R.id.text_view);
		titleTv.setText(title1);
		contentTv.setText(title2);
		okBtn =  view.findViewById(R.id.btn_ok);
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(dialogClickListener != null){
					dialogClickListener.onOKClick();
				}
			}
		});
	}

	private void initWindow() {
		ColorDrawable dw = new ColorDrawable(0);
		this.setBackgroundDrawable(dw);
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth(LayoutParams.MATCH_PARENT);/*((int) (d.widthPixels * 0.8));*/
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();

	}

	public void showAtBottom(View view){
		showAsDropDown(view, Math.abs((view.getWidth() - getWidth())/2), 20);
	}
	
	public void setOnDialogClickListener(OnDialogClickListener clickListener){
		dialogClickListener = clickListener;
	}
	
	public interface OnDialogClickListener{
		void onOKClick();
	}

}
