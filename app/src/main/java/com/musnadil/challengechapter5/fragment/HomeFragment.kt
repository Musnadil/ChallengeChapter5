package com.musnadil.challengechapter5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.adapter.NewsAdapter
import com.musnadil.challengechapter5.api.model.Article
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
        fatchNews()
        setCountry()

    }


    private fun fatchNews(country:String="id") {
        val apiKey = "de0e45bbc3fd4286b6d2cf8120c756ea"
        ApiClient.instance.getAllNews(country,apiKey = apiKey).enqueue(
            object : Callback<GetAllNews> {
                override fun onResponse(call: Call<GetAllNews>, response: Response<GetAllNews>) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        if (body != null) {
                            showList(body.articles)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Server sedang sibuk", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<GetAllNews>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showList(data: List<Article>?) {
        val adapter = NewsAdapter(object : NewsAdapter.OnClickListener {
            override fun onClickItem(data: Article) {
                val bundle = Bundle().apply {
                    putString("img",data.urlToImage)
                    putString("title",data.title)
                    putString("publisher",data.source!!.name)
                    putString("time_published",data.publishedAt)
                    putString("content",data.content)
                    putString("url_laman",data.url)
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailNewsFragment,bundle)
            }
        })
        adapter.submitData(data)
        binding.rvBerita.adapter = adapter
    }

    private fun setPantun() {
        val arrayPantun = arrayOf(
            getString(R.string.pantun_satu),
            getString(R.string.pantun_dua),
            getString(R.string.pantun_tiga)
        )
        val pantun = arrayPantun.random()
        binding.tvPantun.text = pantun
    }

    private fun logout() {
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
    private fun setCountry(){
        val spinner = binding.spinnerCountry
        val country = arrayOf("Indonesia","Malaysia")
        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,country)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val countrySelected :String = if (country[position] == "Indonesia"){
                    "id"
                }else{
                    "my"
                }

                Toast.makeText(requireContext(), "Menampilkan berita ${country[position]}", Toast.LENGTH_SHORT).show()
                fatchNews(countrySelected)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireContext(), "nothing selected", Toast.LENGTH_SHORT).show()
            }

        }
    }
}