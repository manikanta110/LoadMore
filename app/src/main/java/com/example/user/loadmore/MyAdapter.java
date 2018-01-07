package com.example.user.loadmore;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.loadmore.Interface.ILoadMore;
import com.example.user.loadmore.Model.Item;

import java.util.List;

/**
 * Created by user on 07-Jan-18.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_VIEW = 0,ITEM_LOADING=1;
    ILoadMore iLoadMore;
    boolean isloading;
    Activity activity;
    List<Item>itemList;
    int visibleThreshold = 5;
    int lastVisibleitem,totalItemCount;

    public MyAdapter(RecyclerView recyclerView,Activity activity, List<Item> itemList) {
        this.activity = activity;
        this.itemList = itemList;
        final LinearLayoutManager layoutManager  = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleitem = layoutManager.findLastVisibleItemPosition();
                if (!isloading && totalItemCount <= (lastVisibleitem + visibleThreshold)){

                    if(iLoadMore  != null){
                        iLoadMore.OnLoadMore();
                        isloading = true;
                    }

                }


            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position)==null ? ITEM_LOADING:ITEM_VIEW;
    }


    public void setiLoadMore(ILoadMore iLoadMore) {
        this.iLoadMore = iLoadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ITEM_VIEW){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_layout,parent,false);
            return  new ItemViewHolder(view);
        }
        else if(viewType==ITEM_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading,parent,false);
            return  new LoadingViewHolder(view);


        }
       return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){

            Item item = itemList.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder)holder;
            viewHolder.Name.setText(itemList.get(position).getName());
            viewHolder.length.setText(String.valueOf(itemList.get(position).getLength()));

        }
        else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setLoaded() {
        isloading= false;
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView Name,length;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Name = (TextView)itemView.findViewById(R.id.name);
            length = (TextView)itemView.findViewById(R.id.length);
        }
    }
}
