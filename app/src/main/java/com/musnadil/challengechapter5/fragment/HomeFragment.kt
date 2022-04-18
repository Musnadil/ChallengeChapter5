package com.musnadil.challengechapter5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ini nanti diganti jangan menggunakan shared preferences
        preferences = requireContext().getSharedPreferences(LoginFragment.SPUSER, Context.MODE_PRIVATE)
        binding.tvUsername.text = "Welcome ${preferences.getString(LoginFragment.USERNAME, null)}"
        setPantun()
        logout()
    }


    fun setPantun(){
        val arrayPantun = arrayOf(getString(R.string.pantun_satu),getString(R.string.pantun_dua),getString(R.string.pantun_tiga))
        val pantun = arrayPantun.random()
        binding.tvPantun.text = pantun
    }
    fun logout() {
        binding.btnLogout.setOnClickListener {
            val dialogKonfirmasi = AlertDialog.Builder(requireContext())
            dialogKonfirmasi.apply {
                setTitle("Logout")
                setMessage("Apakah anda yakin ingin log out?")
                setNegativeButton("Batal") { dialog, which ->
                    dialog.dismiss()
                }
                setPositiveButton("Logout") { dialog, which ->
                    dialog.dismiss()

                    preferences.edit().clear().apply()
                    findNavController().navigate(R.id.action_homeFragment_to_homeLoginFragment)
                }
            }
            dialogKonfirmasi.show()
        }
    }

}