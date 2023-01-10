package com.example.mybitirmeproject.orderinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mybitirmeproject.data.BIRTHDAY_DATE_FORMAT
import com.example.mybitirmeproject.data.Order
import com.example.mybitirmeproject.databinding.FragmentOrderInfoBinding
import com.example.mybitirmeproject.firebaseUtil.FirebaseUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_order_info.view.*
import java.text.SimpleDateFormat
import java.util.*


class OrderInfoFragment : Fragment() {
    private var _binding: FragmentOrderInfoBinding? = null
    private val binding get() = _binding!!
    lateinit var order : Order
    lateinit var button: Button
    private var mAuth: String? = null

    lateinit var demandedTime:String
    //lateinit var snackbar: Snackbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             order =it.getParcelable<Order>("order")?:Order("bos","bos","bos","bos",0,"","",false,"",false,false,false)
            if (order.demandedTime.isNullOrEmpty()||order.demandedTime=="null")demandedTime="Belirtilmemiş"
            else demandedTime = order.demandedTime.toString()
            Toast.makeText(requireContext(), order.toString(), Toast.LENGTH_SHORT).show()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        view.tw_order_info_id.setText(order.name)
        view.textView12.setText(order.description)
        view.tw_order_info_adres1.setText(order.adress)
        view.tw_order_info_adres2.setText(order.deliveryAdress)
        view.editTextNumber2.setText(order.cost.toString())
        view.tw_order_time_demanded.setText(demandedTime)
        button=view.button

        mAuth = FirebaseAuth.getInstance().uid.toString()

        button.setOnClickListener {
            Toast.makeText(requireContext(), "Sipariş Alındı ", Toast.LENGTH_LONG).show()
            val currentTime: Date = Calendar.getInstance().getTime()
            val sdf = SimpleDateFormat(BIRTHDAY_DATE_FORMAT, Locale.getDefault())
            val currentDateandTime: String = sdf.format(currentTime)

            FirebaseUtil.addSingleValue("Orders",order.orderId,"courierId",mAuth.toString())
            FirebaseUtil.addSingleValue("Orders",order.orderId,"startTime",currentDateandTime)
            FirebaseUtil.addSingleValue("Orders",order.orderId,"inTask",true)

        }

        return view
    }

}