package com.example.mybitirmeproject.myJobs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mybitirmeproject.R
import com.example.mybitirmeproject.becourier.OrderItemAdapter
import com.example.mybitirmeproject.data.Order
import com.example.mybitirmeproject.databinding.FragmentMyJobsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_my_jobs.*


class myJobsFragment : Fragment() {
    private var _binding: FragmentMyJobsBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentMyJobsBinding.inflate(inflater, container, false)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance().uid.toString()

        // Inflate the layout for this fragment
        var orders=ArrayList<Order>()

        //var view=inflater.inflate(R.layout.fragment_be_courier, container, false)
        var recyclerViewList: RecyclerView =view.findViewById(R.id.rv_my_ordersList)

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.getChildren()) {
                    if(postSnapshot.key.equals("siparisNo")){

                    }
                    else{
                        if (postSnapshot.child("courierId").value == mAuth){

                            val value:Long=postSnapshot.child("cost").value as Long?:0
                            val order= Order(
                                name=postSnapshot.child("name").value.toString() ,
                                adress=postSnapshot.child("adress").value.toString(),
                                deliveryAdress=postSnapshot.child("deliveryAdress").value.toString(),
                                description =postSnapshot.child("description").value.toString(),
                                cost = value.toInt(),
                                demandedTime = postSnapshot.child("demanded_Time")?.value.toString(),
                                orderId = postSnapshot.key?:"",
                                inOrder = if (postSnapshot.hasChild("inTask")){
                                    postSnapshot.child("inTask").value==true
                                }else false,
                                ownerId =  if (postSnapshot.hasChild("ownerId")){
                                    postSnapshot.child("ownerId").value.toString()
                                }else null,
                                isReceivedFromCourier =  if (postSnapshot.hasChild("ownerId")){
                                    postSnapshot.child("ownerId").value==true
                                }else false,
                                isPackageOnTheWay =  if (postSnapshot.hasChild("ownerId")){
                                    postSnapshot.child("ownerId").value==true
                                }else false,
                                isPackageArrived =  if (postSnapshot.hasChild("ownerId")){
                                    postSnapshot.child("ownerId").value==true
                                }else false,
                            )
                            //if (!order.inOrder) orders.add(order)
                            //else if (orders.contains(order)) orders.remove(order)
                            orders.add(order)
                        }


                    }

                }
                recyclerViewList.adapter= OrderItemAdapter(orders,object : OrderItemAdapter.OnItemListener{
                    override fun onItemClick(item: Order) {
                        super.onItemClick(item)

                        button4.isVisible= true
                        button4.setOnClickListener {
                            myRef.addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (postSnapshot in snapshot.getChildren()){
                                        if(postSnapshot.key.equals("siparisNo")){

                                        }
                                        else{
                                            if (postSnapshot.child("orderId").value == item.orderId){
                                                val lat = postSnapshot.child("alinacakAdresLat").value as Double
                                                val long = postSnapshot.child("alinacakAdresLong").value as Double
                                                findNavController().navigate(myJobsFragmentDirections.actionMyJobsFragmentToKuryeRotasyon(lat, long))

                                                Log.d("button4 e basıldı","4 basıldı")
                                            }
                                    }
                                }}

                                override fun onCancelled(error: DatabaseError) {

                                }

                            })

                        }

                        button5.isVisible= true
                        button5.setOnClickListener {
                            myRef.addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (postSnapshot in snapshot.getChildren()){
                                        if(postSnapshot.key.equals("siparisNo")){

                                        }
                                        else{
                                            if (postSnapshot.child("orderId").value == item.orderId){
                                                val lat = postSnapshot.child("teslimatAdresLat").value as Double
                                                val long = postSnapshot.child("teslimatAdresLong").value as Double
                                                findNavController().navigate(myJobsFragmentDirections.actionMyJobsFragmentToKuryeRotasyon(lat, long))

                                                Log.d("button4 e basıldı","4 basıldı")
                                            }
                                        }
                                    }}

                                override fun onCancelled(error: DatabaseError) {

                                }

                            })

                        }


                        Toast.makeText(requireContext(),"Sipariş Seçildi.", Toast.LENGTH_LONG).show()
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