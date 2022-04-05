package com.phoncleaner.boosterapp.phonemaster.ui
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.content.Intent

import android.content.IntentFilter
import android.graphics.Color
import android.os.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.phoncleaner.boosterapp.phonemaster.MyApplication
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentEnergySavingBinding

class FragmentEnergySaving : Fragment() {
    fun setProgressCOlor(){
        binding.crpvProgress.setProgressStartColor(Color.parseColor("#24DFBD"))
        binding.crpvProgress.setProgressEndColor(Color.parseColor("#222BFF"))
    }
    private var _binding: FragmentEnergySavingBinding? = null
    private val binding get() = _binding!!
    val handler = Handler(Looper.getMainLooper())
    var mInterstitialAd: InterstitialAd? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnergySavingBinding.inflate(inflater, container, false)
        val view = binding.root
        val infielder = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = requireActivity().registerReceiver(null, infielder)
        val level = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
binding.crpvProgress.progress=100
        val finalUsageTimeNormalMode = String.format("%.02f", level*10/100F-0.5F).replace(".", " ч. ").replace(",", " ч. ")+" м."
        MyApplication.worktime= finalUsageTimeNormalMode
        val updateTask: Runnable = object : Runnable {
            override fun run() {
                binding.tvWorkTime.text = MyApplication.worktime
                handler.postDelayed(this, 10)
            }
        }
        var adRequest = AdRequest.Builder().build()
        handler.postDelayed(updateTask, 10)
        binding.tvEnergyPercent.text = "$level%"
        binding.ywView.progress = level
        binding.ywView.setFrontWaveColor(Color.parseColor("#772499DF"))
        binding.ywView.setBehindWaveColor(Color.parseColor("#4DBDFC"))
        InterstitialAd.load(requireContext(),getString(R.string.inter_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {

                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {

                mInterstitialAd = interstitialAd

            }
        })
binding.vNormalMode.setOnClickListener {
    binding.vNormalMode.isClickable = false
    if(MyApplication.bluetoothPermissionGranted){
setProgressCOlor()
        (requireActivity() as MainActivity).selectTab("normalmode")

        binding.vNormalMode.isClickable =false

    }
    else{
      //  askPermissions.requestBlueTooth()
    }
}
        binding.vUltraMode.setOnClickListener {
            binding.vUltraMode.isClickable = false
            if(MyApplication.bluetoothPermissionGranted){
                setProgressCOlor()

                binding.vUltraMode.isClickable =false
                //  iFragment.regulate(false,2)
                (requireActivity() as MainActivity).selectTab("ultramode")


            }
            else{
              //  askPermissions.requestBlueTooth()
            }
        }
        binding.vExtremeMode.setOnClickListener {
            binding.vExtremeMode.isClickable = false
            if(MyApplication.bluetoothPermissionGranted){
                setProgressCOlor()
                binding.vExtremeMode.isClickable =false
                (requireActivity() as MainActivity).selectTab("extrememode")


            }
            else{
             //   askPermissions.requestBlueTooth()
            }
        }

        return view
    }
}