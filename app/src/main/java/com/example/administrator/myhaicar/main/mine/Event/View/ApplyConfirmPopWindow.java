package com.example.administrator.myhaicar.main.mine.Event.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.administrator.myhaicar.R;

public class ApplyConfirmPopWindow extends PopupWindow{
	private Context context;
	private TextView titleTv;
	private View okBtn;
	private String title;
	private OnDialogClickListener dialogClickListener;
	private Button apply;

	public ApplyConfirmPopWindow(Context context,String title,Button apply) {
		super(context);
		this.title=title;
		this.apply=apply;
		this.context = context;
		initalize();
	}

	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_dialog2, null);
		setContentView(view);
		initWindow();

		titleTv = (TextView) view.findViewById(R.id.title_name);
		titleTv.setText(title);
		okBtn =  view.findViewById(R.id.btn_ok);
		okBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				apply.setClickable(true);
				dismiss();
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
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
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
