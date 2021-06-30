package com.dkjar.server;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.dkjar.server.common.LogUtils;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dkjar.server.databinding.ActivityCommonBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CommonActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCommonBinding binding;
    private String TAG = "COMMON";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_common);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //数组
        arrayList(); //顺序表

        hashMap();
    }


    public void arrayList(){
        ArrayList data = new ArrayList();

        LogUtils.INSTANCE.v(TAG, "DEFAULTCAPACITY_EMPTY_ELEMENTDATA: " + getArrayListCapacity(data));
        data.add("1"); // ensureCapacityInternal   oldCapacity + (oldCapacity >> 1); 当前容量*0.5 增加
        LogUtils.INSTANCE.v(TAG, "add one Size : " + getArrayListCapacity(data));
        for(int i = 0; i < 100; i++){
            data.add(String.valueOf(i));
            LogUtils.INSTANCE.v(TAG, "Data Size : " + data.size() + " CAPACITY : "  + getArrayListCapacity(data));
        }
    }

    public void LinkedList(){
        LinkedList list = new LinkedList();
        list.add(1);
    }

    public void hashMap() {
        HashMap map = new HashMap(); ///transient
        map.put(1, "1");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_common);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static int getArrayListCapacity(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            //获取 elementData 字段
            Field field = arrayListClass.getDeclaredField("elementData");
            //开始访问权限
            field.setAccessible(true);
            //把示例传入get，获取实例字段elementData的值
            Object[] objects = (Object[])field.get(arrayList);
            //返回当前ArrayList实例的容量值
            return objects.length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("baibai", "服务端：book 连接 Service 成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}