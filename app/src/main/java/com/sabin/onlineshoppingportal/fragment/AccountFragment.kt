package com.sabin.onlineshoppingportal.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sabin.onlineshoppingportal.R
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private lateinit var txtusername : TextView
    private lateinit var txtFname : TextView
    private lateinit var txtemail : TextView
    private lateinit var txtType : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_account, container, false)

        txtusername = view.findViewById(R.id.txtusername)
        txtFname = view.findViewById(R.id.txtFname)
        txtemail = view.findViewById(R.id.txtemail)
        txtType = view.findViewById(R.id.txtType)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getSingleUser()
                if(response.success==true){
                    txtusername.setText("${response.data?.username}")
                    txtFname.setText("${response.data?.fullname}")
                    txtemail.setText("${response.data?.email}")
                    txtType.setText("${response.data?.accountType}")
                }
            }    catch (ex: Exception) {

            }       }

        return view
    }
}