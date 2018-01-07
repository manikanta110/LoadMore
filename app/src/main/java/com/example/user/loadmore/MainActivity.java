package com.example.user.loadmore;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.loadmore.Interface.ILoadMore;
import com.example.user.loadmore.Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    List<Item>itemList =  new ArrayList<>();
    MyAdapter myAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random10Data();

        recyclerView =(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter =  new MyAdapter(recyclerView,this,itemList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setiLoadMore(new ILoadMore() {
            @Override
            public void OnLoadMore() {
                if(itemList.size()<=50){
                    itemList.add(null);
                    myAdapter.notifyItemInserted(itemList.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            itemList.remove(itemList.size()-1);
                            myAdapter.notifyItemRemoved(itemList.size());

                            int index = itemList.size();
                            int end = index+10;

                            for(int i = index;i<end;i++){

                                String name = UUID.randomUUID().toString();
                                Item item = new Item(name,name.length());
                                itemList.add(item);

                            }
                            myAdapter.notifyDataSetChanged();
                            myAdapter.setLoaded();


                        }
                    },5000);

                }
                else{
                    Toast.makeText(MainActivity.this, "Data Loaded Completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    private void random10Data() {

        for(int i=0;i<=10;i++){

            String name = UUID.randomUUID().toString();
            Item item = new Item(name,name.length());
            itemList.add(item);

        }


    }
}
