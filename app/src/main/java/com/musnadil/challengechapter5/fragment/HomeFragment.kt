package com.musnadil.challengechapter5.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayPantun = arrayOf(getString(R.string.pantun_satu),getString(R.string.pantun_dua),getString(R.string.pantun_tiga))
        val pantun = arrayPantun.random()

        binding.tvPantun.text = pantun
    }

}