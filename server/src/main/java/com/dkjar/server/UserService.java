package com.dkjar.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserService extends Service {

    private List<UserModel> userModelList = new ArrayList<>();


    public List<UserModel> getUserModelList() {
        return userModelList;
    }

    public UserService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        userModelList.add(new UserModel(1, "user1", "张中1"));
        userModelList.add(new UserModel(2, "user2", "张中2"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    IUserModel.Stub binder = new IUserModel.Stub() {

        @Override
        public void addUser(UserModel user) throws RemoteException {
            userModelList.add(user);
            Log.d("aidl", "server name:" + user.getUserName() + " nick:" + user.getUserNick());
        }

        @Override
        public List<UserModel> getUserList() throws RemoteException {
            return userModelList;
        }
    };
}