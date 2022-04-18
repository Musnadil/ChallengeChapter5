package com.musnadil.challengechapter5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.api.model.GetAllNews
import com.musnadil.challengechapter5.api.service.ApiClient
import com.musnadil.challengechapter5.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(LoginFragment.USERNAME)
        preferences =
            requireContext().getSharedPreferences(LoginFragment.SPUSER, Context.MODE_PRIVATE)
        if (preferences.getString(LoginFragment.USERNAME, null) == null) {
            binding.tvUsername.text = username
        } else {
            binding.tvUsername.text = "${preferences.getString(LoginFragment.USERNAME, null)}"
        }
        setPantun()
        logout()
        binding.tvWelcome.setOnClickListener {
            fatchNews()
        }
    }


    private fun fatchNews() {
        val apiKey = "de0e45bbc3fd4286b6d2cf8120c756ea"
        ApiClient.instance.getAllNews(apiKey = apiKey).enqueue(
            object : Callback<GetAllNews> {
                override fun onResponse(call: Call<GetAllNews>, response: Response<GetAllNews>) {
                    val body = response.body()
                    if (body != null) {
                        if (body.status == "ok") {
                            Log.d("response", body.articles!![0].toString())
                        }

                    }
                }
                override fun onFailure(call: Call<GetAllNews>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun setPantun() {
        val arrayPantun = arrayOf(
            getString(R.string.pantun_satu),
            getString(R.string.pantun_dua),
            getString(R.string.pantun_tiga)
        )
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
                setPositiveButton("Ya") { dialog, which ->
                    dialog.dismiss()

                    preferences.edit().clear().apply()
                    findNavController().navigate(R.id.action_homeFragment_to_homeLoginFragment)
                }
            }
            dialogKonfirmasi.show()
        }
    }

}