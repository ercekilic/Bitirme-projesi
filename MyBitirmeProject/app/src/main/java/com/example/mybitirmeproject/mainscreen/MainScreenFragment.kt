package com.example.mybitirmeproject.mainscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mybitirmeproject.databinding.FragmentMainScreenBinding
import com.example.mybitirmeproject.service.LocationService
import com.example.mybitirmeproject.service.Util
import kotlinx.android.synthetic.main.fragment_main_screen.view.*


class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    lateinit var button_profil:Button
    lateinit var button_getCourier:Button
    lateinit var button_siparisListele:Button
    lateinit var button_paketDurumu:Button
    lateinit var button2:Button

    lateinit var locationService : LocationService
    lateinit var mServiceIntent : Intent
    lateinit var mActivity : Activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        locationService = LocationService()
        mActivity = requireActivity()

        mServiceIntent = Intent(activity?.applicationContext,locationService.javaClass )
        activity?.applicationContext?.startService(mServiceIntent)
        if (Util.isMyServiceRunning(locationService.javaClass, mActivity)) {
            activity?.applicationContext?.startService(mServiceIntent)
            Toast.makeText(
                mActivity,
                "Servis Başlatıldı.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                mActivity,
                "Servis Çalışmaya devam ediyor.",
                Toast.LENGTH_SHORT
            ).show()
        }

        //val view: View = inflater.inflate(R.layout.fragment_main_screen, container, false)
        button_profil=view.button_Profil
        button_getCourier=view.button_siparisVer
        button_siparisListele=view.button3
        button_paketDurumu=view.button_paketDurumu
        button2=view.button2

        button_getCourier.setOnClickListener {
            findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToGetCourierFragment())
        }

        button_profil.setOnClickListener {
            findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToProfilFragment())
        }
        button_siparisListele.setOnClickListener {
            findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToBeCourierFragment())
        }
        button_paketDurumu.setOnClickListener {
            findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToCargoStatusFragment())
        }
        button2.setOnClickListener {
            findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToMyJobsFragment())

        }

        return view
    }



}
