package com.example.mybitirmeproject.getcourier

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mybitirmeproject.data.Order
import com.example.mybitirmeproject.databinding.FragmentGetCourierBinding
import com.example.mybitirmeproject.firebaseUtil.FirebaseUtil
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_get_courier.*
import kotlinx.android.synthetic.main.fragment_get_courier.view.*
import java.util.*


class GetCourierFragment : Fragment() {
    lateinit var buttonGiveOrder:Button
    lateinit var name:EditText
    lateinit var description:EditText
    lateinit var adress:EditText
    lateinit var deliveryAdress:EditText
    lateinit var cost:EditText
    lateinit var time:EditText
    private var mAuth: String? = null
    private var _binding: FragmentGetCourierBinding? = null
    private val binding get() = _binding!!
    var alinacakAdresLat = 0.0
    var alinacakAdresLong = 0.0
    var teslimatAdresLat = 0.0
    var teslimatAdresLong = 0.0


    val database= Firebase.database
    val myRef = database.getReference("Orders")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetCourierBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
       // var view=inflater.inflate(R.layout.fragment_get_courier, container, false)

        buttonGiveOrder=view.bt_give_order
        name=view.er_order_name
        description=view.et_order_desription
        adress=view.et_order_adress
        deliveryAdress=view.et_order_delivery
        cost=view.et_order_cost
        time=view.et_order_demandedTime
        mAuth = FirebaseAuth.getInstance().uid.toString()
        buttonGiveOrder.setOnClickListener {
            siparisNoOku()
        }

        context?.let { Places.initialize(it,"AIzaSyCvceAU5zxQLKh_5Yqx0uLJj3ujqQ5mIz0") }
        view.et_order_adress.isFocusable = false
        view.et_order_delivery.isFocusable = false
        view.et_order_adress.setOnClickListener {
            var fieldList :List< Place.Field> = Arrays.asList(Place.Field.ADDRESS,
                                            Place.Field.LAT_LNG,Place.Field.NAME)

            var intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                                    fieldList).build(requireContext())

            startActivityForResult(intent,100)
        }
        view.et_order_delivery.setOnClickListener {
            var fieldList :List< Place.Field> = Arrays.asList(Place.Field.ADDRESS,
                Place.Field.LAT_LNG,Place.Field.NAME)

            var intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                fieldList).build(requireContext())

            startActivityForResult(intent,101)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            var place = data?.let { Autocomplete.getPlaceFromIntent(it) }
            view?.et_order_adress?.setText(place?.address)
            alinacakAdresLat = place?.latLng?.latitude?:0.0
            alinacakAdresLong = place?.latLng?.longitude?:0.0

        }
        else if (requestCode == 101 && resultCode == RESULT_OK){
            var place = data?.let { Autocomplete.getPlaceFromIntent(it) }
            view?.et_order_delivery?.setText(place?.address)
            teslimatAdresLat = place?.latLng?.latitude?:0.0
            teslimatAdresLong = place?.latLng?.longitude?:0.0
        }
    }
    private fun siparisNoOku(){
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               val value:Long= (snapshot.child("siparisNo").value as Long)
                myRef.child("siparisNo").setValue(value.toInt().plus(1))
               siparisOlustur(value.toInt().plus(1))
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }
    private fun siparisOlustur(value: Int) {
        var costString=cost.text.toString()
        var costInt=Integer.parseInt(costString)
        val order=Order(
            name.text.toString(),
            description.text.toString(),
            adress.text.toString(),
            deliveryAdress.text.toString(),
            costInt,
            et_order_demandedTime.text.toString(),
            value.toString(),
            inOrder = false,
            mAuth,
            false,
            false,
            false
        )
        myRef.child(value.toString()).setValue(order).addOnCompleteListener {
            FirebaseUtil.addSingleValue("Orders",value.toString(),"alinacakAdresLat",alinacakAdresLat)
            FirebaseUtil.addSingleValue("Orders",value.toString(),"alinacakAdresLong",alinacakAdresLong)
            FirebaseUtil.addSingleValue("Orders",value.toString(),"teslimatAdresLat",teslimatAdresLat)
            FirebaseUtil.addSingleValue("Orders",value.toString(),"teslimatAdresLong",teslimatAdresLong)
            Toast.makeText(requireContext(),"Kargo isteği kaydedildi.",Toast.LENGTH_LONG).show()
        }

    }


}