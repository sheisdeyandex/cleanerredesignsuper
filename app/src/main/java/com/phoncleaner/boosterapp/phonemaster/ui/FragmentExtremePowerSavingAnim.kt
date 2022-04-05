package com.phoncleaner.boosterapp.phonemaster.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.bluetooth.BluetoothManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.phoncleaner.boosterapp.phonemaster.MyApplication
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentExtremePowerSavingAnimBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class FragmentExtremePowerSavingAnim : Fragment() {
    private var _binding: FragmentExtremePowerSavingAnimBinding? = null
    private val binding get() = _binding!!
    var mInterstitialAd: InterstitialAd? = null
    fun openAndroidPermissionsMenu() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:" + requireActivity().packageName)
        startActivity(intent)
    }
    var showSettings:Boolean= false
    fun setBrightness(brightness: Int) {
        var brightness = brightness
        if (brightness < 0) brightness = 0 else if (brightness > 255) brightness = 255
        val cResolver = requireActivity().applicationContext.contentResolver
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
    }
    val handler = Handler(Looper.getMainLooper())
    fun initAnim(){
        val updateTask: Runnable = object : Runnable {
            override fun run() {
                binding.tvNormalModePercent.text = binding.crpvProgress.progress.toInt().toString()+"%"
                if(binding.crpvProgress.progress.toInt() == 12){
                    binding.ivDone.visibility= View.VISIBLE
                    binding.tvFirstText.setTextColor(Color.WHITE)
                    binding.spinKit.visibility = View.GONE

                }
                else   if(binding.crpvProgress.progress.toInt() == 24){
                    binding.ivDoneSecond.visibility= View.VISIBLE
                    binding.tvSecondText.setTextColor(Color.WHITE)
                    binding.spinKitSecond.visibility = View.GONE
                }
                else   if(binding.crpvProgress.progress.toInt() == 48){
                    binding.ivDoneThird.visibility= View.VISIBLE
                    binding.tvThirdText.setTextColor(Color.WHITE)
                    binding.spinKitThird.visibility = View.GONE
                }
                else   if(binding.crpvProgress.progress.toInt() == 72){
                    binding.ivDoneFourth.visibility= View.VISIBLE
                    binding.tvFourthText.setTextColor(Color.WHITE)
                    binding.spinKitFourth.visibility = View.GONE
                }
                else   if(binding.crpvProgress.progress.toInt() == 98){
                    binding.ivDoneFifth.visibility= View.VISIBLE
                    binding.tvFifthText.setTextColor(Color.WHITE)
                    binding.spinKitFifth.visibility = View.GONE
                }
                handler.postDelayed(this, 1)
            }
        }

        handler.postDelayed(updateTask, 1)
        val animation = ObjectAnimator.ofInt(binding.crpvProgress, "progress", 0,100)
        animation.duration = 5000
        animation.interpolator = DecelerateInterpolator()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                val bluetoothAdapter = (requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
                if (bluetoothAdapter!=null&&bluetoothAdapter.isEnabled){
                    bluetoothAdapter.disable()}
                ContentResolver.setMasterSyncAutomatically(false)

            }
            override fun onAnimationEnd(animator: Animator) {

                (requireActivity() as MainActivity).binding.bnvNav.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    showSettings = Settings.System.canWrite(requireContext())
                    if(showSettings){

                        setBrightness(25)
                        if (mInterstitialAd != null) {
                            if(!MyApplication.premiumUser){

                                mInterstitialAd?.show(requireActivity())
                            }
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        }
                        (requireActivity() as MainActivity).selectTab("energysaving") }
                    else{
                        if (mInterstitialAd != null) {
                            if(!MyApplication.premiumUser){

                                mInterstitialAd?.show(requireActivity())
                            }
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        }
                        (requireActivity() as MainActivity).selectTab("energysaving")
                        openAndroidPermissionsMenu()
                    }
                }
                else{
                    if (mInterstitialAd != null) {
                        if(!MyApplication.premiumUser){

                            mInterstitialAd?.show(requireActivity())
                        }
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                    (requireActivity() as MainActivity).selectTab("energysaving") }

            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        animation.start()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtremePowerSavingAnimBinding.inflate(inflater, container, false)
        val view = binding.root
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),getString(R.string.inter_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
//        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
//            override fun onAdDismissedFullScreenContent() {
//                Log.d("sukagdereklama", "addismissed")
//            }
//
//            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                Log.d("sukagdereklama", "Ad failed to show.")
//            }
//
//            override fun onAdShowedFullScreenContent() {
//                Log.d("sukagdereklama", "Ad showed fullscreen content.")
//                mInterstitialAd = null
//            }
//        }
        initAnim()
        return view
    }
}