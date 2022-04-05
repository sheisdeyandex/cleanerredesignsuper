package com.phoncleaner.boosterapp.phonemaster.ui
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phoncleaner.boosterapp.phonemaster.MyApplication
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentExtremePowerSavingBinding

class FragmentExtremePowerSaving : Fragment() {
    private var _binding: FragmentExtremePowerSavingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtremePowerSavingBinding.inflate(inflater, container, false)
        val view = binding.root
        (requireActivity() as MainActivity).binding.bnvNav.visibility = View.GONE
         binding.tvUltrapowertext.text = binding.tvUltrapowertext.text.toString() +" "+ MyApplication.finalExtremeModeUsageTime
binding.tvHoursText.text = "(+4 ч. 00 м.)"
        binding.crpvProgress.progress=100

        binding.crpvProgress.setProgressStartColor(Color.parseColor("#6AFEDC"))
        binding.crpvProgress.setProgressEndColor(Color.parseColor("#467AFF"))
        binding.materialButtonDoIt.setOnClickListener {
            MyApplication.worktime =  MyApplication.finalExtremeModeUsageTime
            (requireActivity() as MainActivity).selectTab("extrememodeanim")

        }

        return view
    }

}