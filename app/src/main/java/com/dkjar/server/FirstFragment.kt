package com.dkjar.server

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dkjar.server.databinding.FragmentFirstBinding
import com.dkjar.server.handle.HandlerActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var userService: IUserModel? = null
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = Intent()
        intent.action = "com.dkjar.server.userservice"
        intent.setPackage("com.dkjar.server")

        context?.bindService(intent, connection, AppCompatActivity.BIND_AUTO_CREATE)

    }

    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("aidl", "client：book 连接 Service 成功")
            Log.d("aidl", "name: " +  name.toString())
            userService = IUserModel.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("aidl", "服务端：book 断开连接 Service 成功")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.handleBtn.setOnClickListener {
            var intent = Intent(context, HandlerActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.listBtn.setOnClickListener {
            var intent = Intent(context, CommonActivity::class.java)
            activity?.startActivity(intent)
        }

        var i = 3;
        binding.addBtn.setOnClickListener {
            userService?.addUser(UserModel(i++, "user$i", "用户$i"))
        }

        binding.userListBtn.setOnClickListener {
           val list =  userService?.userList
            if (list != null) {
                for(u in list){
                    Log.d("aidl", u.userName + u.userNick)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.unbindService(connection)
    }
}