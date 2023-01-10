package com.example.mybitirmeproject.cargostatus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mybitirmeproject.R
import com.example.mybitirmeproject.becourier.OrderItemAdapter
import com.example.mybitirmeproject.data.Order
import com.example.mybitirmeproject.databinding.FragmentCargoStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_cargo_status.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class CargoStatusFragment : Fragment() {
    private var _binding: FragmentCargoStatusBinding? = null
    private val binding get() = _binding!!
    var courierId = ""
    private var mAuth: String? = null
    val database= Firebase.database
    val myRef = database.getReference("Orders")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCargoStatusBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance().uid.toString()

        // Inflate the layout for this fragment
        var orders=ArrayList<Order>()

        //var view=inflater.inflate(R.layout.fragment_be_courier, container, false)
        var recyclerViewList: RecyclerView =view.findViewById(R.id.rv_my_ordersList_status)

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.getChildren()) {
                    if(postSnapshot.key.equals("siparisNo")){

                    }
                    else{
                            val value:Long=postSnapshot.child("cost").value as Long?:0
                            if(postSnapshot.hasChild("courierId")){

                                courierId = postSnapshot.child("courierId").value.toString()
                            }

                            val order= Order(
                                name=postSnapshot.child("name").value.toString() ,
                                adress=postSnapshot.child("adress").value.toString(),
                                deliveryAdress=postSnapshot.child("deliveryAdress").value.toString(),
                                description =postSnapshot.child("description").value.toString(),
                                cost = value.toInt(),
                               // courierId = postSnapshot.child("courierId").value.toString(),
                                demandedTime = postSnapshot.child("demanded_Time")?.value.toString(),
                                orderId = postSnapshot.key?:"",


                                inOrder = if (postSnapshot.hasChild("inTask")){
                                    postSnapshot.child("inTask").value==true
                                }else false,
                                ownerId =  if (postSnapshot.hasChild("ownerId")){
                                    postSnapshot.child("ownerId").value.toString()
                                }else null,
                                isReceivedFromCourier =  if (postSnapshot.hasChild("isReceivedFromCourier")){
                                    postSnapshot.child("isReceivedFromCourier").value==true
                                }else false,
                                isPackageOnTheWay =  if (postSnapshot.hasChild("isPackageOnTheWay")){
                                    postSnapshot.child("isPackageOnTheWay").value==true
                                }else false,
                                isPackageArrived =  if (postSnapshot.hasChild("isPackageArrived")){
                                    postSnapshot.child("isPackageArrived").value==true
                                }else false,
                            )
                            //if (!order.inOrder) orders.add(order)
                            //else if (orders.contains(order)) orders.remove(order)
                            if (order.ownerId==mAuth) orders.add(order)
                        }




                }
                recyclerViewList.adapter= OrderItemAdapter(orders,object : OrderItemAdapter.OnItemListener{
                    override fun onItemClick(item: Order) {
                        super.onItemClick(item)
                        Toast.makeText(requireContext(),"Sipariş Seçildi.", Toast.LENGTH_LONG).show()

                        binding.clJobStatus.visibility=View.GONE

                        binding.button5.isVisible=true

                        binding.button5.setOnClickListener {

                            if(!courierId.isNullOrEmpty()){
                                Log.d("button5 basıldı","$courierId")
                                findNavController().navigate(CargoStatusFragmentDirections.actionCargoStatusFragmentToKargomNerede(courierId))
                            }

                          //  Log.d("button5 e basıldı", "\$courierId" )
                        }




                        binding.clIsRFromCourier.background =
                            if (item.isReceivedFromCourier) ContextCompat.getDrawable(context!!, R.drawable.bg_green)
                            else ContextCompat.getDrawable(context!!, R.drawable.bg_yellow)

                        binding.clIsOnTheWay.background =
                            if (item.isPackageOnTheWay) ContextCompat.getDrawable(context!!, R.drawable.bg_green)
                            else ContextCompat.getDrawable(context!!, R.drawable.bg_yellow)

                        binding.clIsArrived.background =
                            if (item.isPackageArrived) ContextCompat.getDrawable(context!!, R.drawable.bg_green)
                            else ContextCompat.getDrawable(context!!, R.drawable.bg_yellow)

                        binding.clJobStatus.visibility=View.VISIBLE
                        //findNavController().navigate(BeCourierFragmentDirections.actionBeCourierFragmentToOrderInfoFragment(item))
                    }
                })

            }


            override fun onCancelled(error: DatabaseError) {

            }

        })






        return view
    }


}