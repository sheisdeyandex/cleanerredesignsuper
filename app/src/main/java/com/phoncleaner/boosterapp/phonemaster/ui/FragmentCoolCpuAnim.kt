package com.phoncleaner.boosterapp.phonemaster.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

import com.phoncleaner.boosterapp.phonemaster.MyApplication
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentCoolCpuAnimBinding


class FragmentCoolCpuAnim() : Fragment() {
    private var _binding: FragmentCoolCpuAnimBinding? = null
    private val binding get() = _binding!!

    private var mInterstitialAd: InterstitialAd? = null
    private fun cpuTemperature(): Float {
        val process: Process
        return try {
            process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp")
            process.waitFor()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val line = reader.readLine()
            if (line != null) {
                val temp = line.toFloat()
                temp / 1000.0f
            } else {
                51.0f
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0.0f
        }
    }
fun coolAnim(){
    val rotate =
        RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    rotate.duration = 1000
    rotate.interpolator = LinearInterpolator()

rotate.repeatCount = Animation.INFINITE
    binding.ivCpuAnimIcon.startAnimation(rotate)
    binding.ivAnimFirst.setImageDrawable(MyApplication.animImageDrawable)
    binding.ivAnimSecond.setImageDrawable(MyApplication.animImageDrawableSecond)
    binding.ivAnimThird.setImageDrawable(MyApplication.animImageDrawableThird)
    binding.ivAnimFourth.setImageDrawable(MyApplication.animImageDrawablefourth)
    binding.ivAnimFifth.setImageDrawable(MyApplication.animImageDrawableFifth)
    binding.clAnimFirst.transitionToEnd()
    val animation = ObjectAnimator.ofInt(binding.crpvProgress, "progress", 0, 100)
    animation.duration = 5000
    animation.interpolator = DecelerateInterpolator()
    animation.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            object : CountDownTimer(2000, 1000) {
                override fun onTick(p0: Long) {
                    binding.tvCooled.text =
                        getString(R.string.coolled) + " " + (cpuTemperature() - 3.5f).toString() + "℃"
                }

                override fun onFinish() {
                    binding.ivCpuAnimIcon.clearAnimation()
                    binding.ivCpuAnimIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_done_overall))
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(requireActivity())
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                    (requireActivity() as MainActivity).selectTab("coolcpu")
                    (requireActivity() as MainActivity).binding.bnvNav.visibility = View.VISIBLE
                }
            }.start()
        }

        override fun onAnimationCancel(p0: Animator?) {

        }

        override fun onAnimationRepeat(p0: Animator?) {

        }

    })
animation.start()
    object :CountDownTimer(1000,1000){
        override fun onTick(p0: Long) {

        }

        override fun onFinish() {
            binding.clAnimSecond.transitionToEnd()
            object :CountDownTimer(1000,1000){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    binding.clAnimThird.transitionToEnd()
                    object :CountDownTimer(1000,1000){
                        override fun onTick(p0: Long) {

                        }

                        override fun onFinish() {
                            binding.clAnimFourth.transitionToEnd()
                            object :CountDownTimer(1000,1000){
                                override fun onTick(p0: Long) {

                                }

                                override fun onFinish() {
                                    binding.clAnimFifth.transitionToEnd()


                                }
                            }.start()
                        }
                    }.start()
                }

            }.start()
        }

    }.start()
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoolCpuAnimBinding.inflate(inflater, container, false)
        val view = binding.root
coolAnim()
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),getString(R.string.inter_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
        return view
    }


}