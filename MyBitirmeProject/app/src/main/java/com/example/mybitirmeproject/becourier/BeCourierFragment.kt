package com.example.mybitirmeproject.becourier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mybitirmeproject.R
import com.example.mybitirmeproject.data.Order
import com.example.mybitirmeproject.databinding.FragmentBeCourierBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BeCourierFragment : Fragment() {
    private var _binding: FragmentBeCourierBinding? = null
    private val binding get() = _binding!!
    val database= Firebase.database
    val myRef = database.getReference("Orders")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentBeCourierBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inflate the layout for this fragment
        var orders=ArrayList<Order>()

        //var view=inflater.inflate(R.layout.fragment_be_courier, container, false)
        var recyclerViewList:RecyclerView=view.findViewById(R.id.rv_ordersList)

        myRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.getChildren()) {
                        if(postSnapshot.key.equals("siparisNo")){

                    }
                    else{
                            val value:Long=postSnapshot.child("cost").value as Long?:0
                            val order=Order(
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
                            if (!order.inOrder) orders.add(order)
                            else if (orders.contains(order)) orders.remove(order)
                            //orders = orders.filter { it.inOrder } as ArrayList<Order>

                        }

                }
                recyclerViewList.adapter=OrderItemAdapter(orders,object :OrderItemAdapter.OnItemListener{
                    override fun onItemClick(item: Order) {
                        super.onItemClick(item)


                        Toast.makeText(requireContext(),"Sipariş Seçildi.",Toast.LENGTH_LONG).show()
                        findNavController().navigate(BeCourierFragmentDirections.actionBeCourierFragmentToOrderInfoFragment(item))
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return view
    }


}