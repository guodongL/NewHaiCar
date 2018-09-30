package com.example.administrator.myhaicar.main.mine.MyCar.Adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.myhaicar.R;
import com.example.administrator.myhaicar.commond.MyListView;


/**
 * Created by Administrator on 2017/5/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MainViewHolder> {
    private ListViewAdapter listViewAdapter;
private Context mContext;
    // 列表展开标识
    int opened = -1;
    public RecyclerAdapter(Context context){
       this.mContext=context;
        listViewAdapter=new ListViewAdapter(mContext);
        }


    /**
     * 绑定item布局
     * @param parent
     * @param pos
     * @return
     */
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        return new MainViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false));
    }

    /**
     * 绑定数据到控件
     * @param holder
     * @param pos
     */
    @Override
    public void onBindViewHolder(MainViewHolder holder, int pos) {

        holder.bind(pos);
    }

    /**
     * 返回列表条数
     * @return
     */
    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * 实例化控件等操作
     */
    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // 隐藏的内容
        public final LinearLayout linear;
        public MyListView listView_item;

        // 实例化
        public MainViewHolder(ViewGroup itemView) {
            super(itemView);
            linear = ((LinearLayout) itemView.findViewById(R.id.linear));
            listView_item= (MyListView) itemView.findViewById(R.id.listView_item);
            listView_item.setAdapter(listViewAdapter);
            itemView.setOnClickListener(this);
        }

        // 此方法实现列表的展开和关闭
        public void bind(int pos) {
            if (pos == opened)
                linear.setVisibility(View.VISIBLE);
            else
                linear.setVisibility(View.GONE);

        }

        /**
         * 为item添加点击效果
         * (recyclerView是不提供onItemClickListener的。所以列表的点击事件需要我们自己来实现)
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (opened == getPosition()) {
                opened = -1;
                notifyItemChanged(getPosition());
            }
            else {
                int oldOpened = opened;
                opened = getPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }
        }
    }
}
