// IUserModel.aidl
package com.dkjar.server;
import com.dkjar.server.UserModel;

interface IUserModel {

   void addUser(in UserModel user);

   List<UserModel> getUserList();

}