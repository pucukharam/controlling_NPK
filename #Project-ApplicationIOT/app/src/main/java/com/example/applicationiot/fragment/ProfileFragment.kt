package com.example.applicationiot.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.applicationiot.activity.HomeActivity
import com.example.applicationiot.activity.LanguageSettingActivity
import com.example.applicationiot.activity.NotificationSettingActivity
import com.example.applicationiot.activity.RegistrationActivity
import com.example.applicationiot.databinding.FragmentLocationBinding
import com.example.applicationiot.databinding.FragmentProfileBinding
import com.example.applicationiot.util.BaseAppCompatActivity
import com.example.applicationiot.util.SessionManager

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): ProfileFragment{
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
        val TAG="profile"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvNotification.setOnClickListener {
            ( requireActivity() as BaseAppCompatActivity).goToPage(NotificationSettingActivity::class.java)
        }
        binding.cvLanguage.setOnClickListener {
            ( requireActivity() as BaseAppCompatActivity).goToPage(LanguageSettingActivity::class.java)
        }
        if(SessionManager.getLanguage(requireContext())=="in"){
            binding.tvLang.text="Indonesia"
        }else{
            binding.tvLang.text="English"
        }
        if(SessionManager.getNotification(requireContext())){
            binding.tvNotification.text="On"
        }else{
            binding.tvNotification.text="Off"
        }
        binding.tvLogOut.setOnClickListener {
            SessionManager.clearData(requireContext())
            ( requireActivity() as BaseAppCompatActivity).goToPageAndClearStack(RegistrationActivity::class.java)
        }
        binding.tvName.text= SessionManager.getName(requireContext())
        binding.tvEmail.text= SessionManager.getEmail(requireContext())

    }

    override fun onResume() {
        super.onResume()
        if(SessionManager.getLanguage(requireContext())=="in"){
            binding.tvLang.text="Indonesia"
        }else{
            binding.tvLang.text="English"
        }
        if(SessionManager.getNotification(requireContext())){
            binding.tvNotification.text="On"
        }else{
            binding.tvNotification.text="Off"
        }
    }
}