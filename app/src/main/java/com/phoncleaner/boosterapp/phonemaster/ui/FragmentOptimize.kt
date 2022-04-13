package com.phoncleaner.boosterapp.phonemaster.ui

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.text.format.Formatter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.phoncleaner.boosterapp.phonemaster.MyApplication
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentOptimizeBinding


class FragmentOptimize() : Fragment() {

    private var _binding: FragmentOptimizeBinding? = null
    private val binding get() = _binding!!

    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptimizeBinding.inflate(inflater, container, false)
        val view = binding.root
        if(SDK_INT>= Build.VERSION_CODES.R){
            if (!Environment.isExternalStorageManager()){
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    ), 1
                )
            }
        }
        else{
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 1
            )
        }
        getRamUsageInfo()
        var   adRequest = AdRequest.Builder().build()
if(MyApplication.optimize){
    optimize()
}
        if(!MyApplication.premiumUser){
        }
        InterstitialAd.load(requireContext(),getString(R.string.inter_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {

                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {

                mInterstitialAd = interstitialAd

            }
        })
        binding.materialButton.setOnClickListener {
        optimize()
        }
        return view
    }
    fun optimize(){
        val pm: PackageManager =requireActivity().packageManager

        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (packageInfo in packages) {
            if(!isSystemPackage(packageInfo)){
                val mActivityManager =
                    requireActivity().getSystemService(ACTIVITY_SERVICE) as ActivityManager
                mActivityManager.killBackgroundProcesses(packageInfo.packageName)
            } }
        binding.crpvProgress.progress = 100
        val animation = ObjectAnimator.ofInt(binding.crpvProgress, "progress", 0, 100)
        animation.duration = 5000
        animation.interpolator = DecelerateInterpolator()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                binding.materialButton.isEnabled = false
                binding.tvRam.visibility = View.GONE
                binding.tvRamUsageFound.visibility = View.GONE
                binding.tvFoundUsedRam.textSize = 18f
                binding.tvFoundUsedRam.text = resources.getString(R.string.optimizing)
            }
            override fun onAnimationEnd(animator: Animator) {
                binding.materialButton.isEnabled= false
                getRamUsageInfo()
                binding.tvRam.visibility = View.VISIBLE
                binding.tvFoundUsedRam.textSize = 36f
                binding.tvRamUsageFound.visibility = View.VISIBLE
                binding.tvMbText.text = getString(R.string.optimized)
                binding.crpvProgress.setProgressStartColor(Color.parseColor("#6AFEDC"))
                binding.crpvProgress.setProgressEndColor(Color.parseColor("#467AFF"))
                binding.ivCpuIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_cpu_blue_icon))
                binding.clOptiomize.transitionToEnd()

                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        animation.start()
    }

    val handler = Handler(Looper.getMainLooper())
    private fun isSystemPackage(pkgInfo: ApplicationInfo): Boolean {
        return pkgInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
    private  fun getRamUsageInfo(){
        val memoryInfo = ActivityManager.MemoryInfo()
        (requireActivity().getSystemService(ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val nativeHeapSize = memoryInfo.totalMem
        val nativeHeapFreeSize = memoryInfo.availMem
        val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize
        var usedMemInPercentage = usedMemInBytes * 100 / nativeHeapSize
       binding.tvRamUsed.text = "$usedMemInPercentage%"
        binding.tvFoundUsedRam.text = Formatter.formatFileSize(requireContext(), usedMemInBytes)
val totalsize = Formatter.formatFileSize(requireContext(), nativeHeapSize)
    val usedsize = Formatter.formatFileSize(requireContext(), usedMemInBytes)
        binding.tvRamUsedFromTo.text = "$usedsize / $totalsize"

        val animation = ObjectAnimator.ofInt(binding.crpvProgress, "progress", 0, usedMemInPercentage.toInt())
        animation.duration = 2000
        animation.interpolator = DecelerateInterpolator()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }
            override fun onAnimationEnd(animator: Animator) {

            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        animation.start()
    }
}