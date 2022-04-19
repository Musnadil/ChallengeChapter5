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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.adapter.NewsAdapter
import com.musnadil.challengechapter5.api.model.Article
import com.musnadil.challengechapter5.api.model.GetAllNews
import com.musnadil.challengechapter5.api.service.ApiClient
import com.musnadil.challengechapter5.databinding.FragmentHomeBinding
import com.musnadil.challengechapter5.room.database.UserDatabase
import com.musnadil.challengechapter5.room.entity.User
import com.musnadil.challengechapter5.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences
    var myDb : UserDatabase? = null
    private val args : HomeFragmentArgs by navArgs()
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        preferences = requireContext().getSharedPreferences(LoginFragment.SPUSER, Context.MODE_PRIVATE)

        getUser()
        homeViewModel.userLoggedin.observe(viewLifecycleOwner){
            binding.tvUsername.text = it.username
        }
        setPantun()
        logout()
        fatchNews("id")
        setCountry()
        updateUser()
    }
    private fun fatchNews(country:String) {
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
                    }else if (code == 400){
                        Toast.makeText(requireContext(), "The request was unacceptable.", Toast.LENGTH_SHORT).show()
                    }else if(code == 401){
                        Toast.makeText(requireContext(), "Your API key was missing from the request, or wasn't correct.", Toast.LENGTH_SHORT).show()
                    }else if(code == 429){
                        Toast.makeText(requireContext(), "You made too many requests", Toast.LENGTH_SHORT).show()
                    }else if(code ==500){
                        Toast.makeText(requireContext(), "Something went wrong on our side.", Toast.LENGTH_SHORT).show()
                    }else {
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
    private fun updateUser(){
        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_updateUserFragment)
        }
    }
    fun getUser() {
        myDb = UserDatabase.getInstance(requireContext())
        preferences =
            requireContext().getSharedPreferences(LoginFragment.SPUSER, Context.MODE_PRIVATE)
        var username = preferences!!.getString(LoginFragment.USERNAME, null)
        var password = preferences!!.getString(LoginFragment.PASSWORD, null)
        if (username.isNullOrEmpty() && password.isNullOrEmpty()) {
            username = args.username
            password = args.password
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val data = myDb?.userDao()?.getUser(username.toString(), password.toString())
            runBlocking(Dispatchers.Main) {
                if (data != null) {
                    val user = User(
                        data.id,
                        data.username,
                        data.email,
                        data.password
                    )
                    homeViewModel.getUser(user)
                    val navigateUpdate =
                        HomeFragmentDirections.actionHomeFragmentToUpdateUserFragment(user)
                    binding.btnUpdate.setOnClickListener {
                        findNavController().navigate(navigateUpdate)
                    }
                }
            }
        }

    }

}