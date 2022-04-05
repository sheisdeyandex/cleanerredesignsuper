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
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentCleanAnimBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class FragmentCleanAnim() : Fragment() {
    private var mInterstitialAd: InterstitialAd? = null
    private var _binding: FragmentCleanAnimBinding? = null
    private val binding get() = _binding!!
fun cleanAnim(){

    val animation = ObjectAnimator.ofInt(binding.crpvProgress, "progress", 0, 100)
    animation.duration = 10000
    animation.interpolator = DecelerateInterpolator()
    animation.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationRepeat(p0: Animator?) {
        }

    })
    animation.start()
object :CountDownTimer(10000,1000){
    override fun onTick(p0: Long) {
        val rotate =
            RotateAnimation(-30f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 2000
        rotate.interpolator = LinearInterpolator()
        rotate.repeatCount = Animation.REVERSE
        binding.ivCleanAnim.startAnimation(rotate)
    }

    override fun onFinish() {
        binding.ivCleanAnim.clearAnimation()
        object :CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {
                binding.ivCleanAnim.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_done_overall))

            }

            override fun onFinish() {
                (requireActivity() as MainActivity).selectTab("clean")
                (requireActivity() as MainActivity).binding.bnvNav.visibility= View.VISIBLE
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }
            }

        }.start()

    }

}.start()
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCleanAnimBinding.inflate(inflater, container, false)
        val view = binding.root
cleanAnim()
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