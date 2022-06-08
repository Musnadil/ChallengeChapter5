package com.musnadil.challengechapter5.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.BuildConfig
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.adapter.NewsAdapter
import com.musnadil.challengechapter5.data.api.Status
import com.musnadil.challengechapter5.data.api.model.Article
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val arrayPantun = mutableListOf<String>()
    private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getDataUser()
        detailNews()
        arrayPantun.addAll(
            listOf(
                getString(R.string.pantun_satu),
                getString(R.string.pantun_dua),
                getString(R.string.pantun_tiga)
            )
        )
        logout()
        getUser()
        binding.tvPantun.setOnClickListener {
            setPantun()
        }
        setFlavor()
    }

    private fun setFlavor() {
        if (BuildConfig.FLAVOR.equals("Berita")) {
            fatchNews("id")
            setPantun()
            binding.spinnerCountry.visibility = View.GONE
        } else {
            binding.tvPantun.visibility = View.GONE
            setCountry()
        }
    }
    //test CI-1

    private fun fatchNews(country: String) {
        val apiKey = "de0e45bbc3fd4286b6d2cf8120c756ea"
        homeViewModel.news.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> if (it.data?.body() != null) {
                            binding.pbLoading.visibility = View.GONE
                            adapter.submitData(it.data?.body()!!.articles)
                        }
                        400 -> {
                            Toast.makeText(
                                requireContext(),
                                "Your API key was missing from the request, or wasn't correct.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        429 -> {
                            Toast.makeText(
                                requireContext(),
                                "You made too many requests",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        500 -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong on our side.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                Status.ERROR -> {}
            }
        }
        homeViewModel.getNews(country, apiKey)
    }

    private fun detailNews() {
        adapter = NewsAdapter(object : NewsAdapter.OnClickListener {
            override fun onClickItem(data: Article) {
                val bundle = Bundle().apply {
                    putString("img", data.urlToImage)
                    putString("title", data.title)
                    putString("publisher", data.source!!.name)
                    putString("time_published", data.publishedAt)
                    putString("content", data.content)
                    putString("url_laman", data.url)
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailNewsFragment, bundle)
            }
        })
        binding.rvBerita.adapter = adapter
    }

    private fun setPantun() {
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
                    homeViewModel.deleteUserPref()
                    homeViewModel.user.observe(viewLifecycleOwner) {
                        if (it.id == UserPreferences.DEFAULT_ID && findNavController().currentDestination?.id == R.id.homeFragment) {
                            binding.btnUpdate.visibility = View.GONE
                            binding.tvUsername.visibility = View.GONE
                            findNavController().navigate(R.id.action_homeFragment_to_loginCompose)
                        }
                    }
                }
            }
            dialogKonfirmasi.show()
        }
    }

    private fun setCountry() {
        val spinner = binding.spinnerCountry
        val country = arrayOf(
            "China",
            "India",
            "Indonesia",
            "Japan",
            "Malaysia",
            "Philippines",
            "Poland",
            "Portugal",
            "Romania",
            "Russia",
            "Saudi Arabia",
            "Serbia",
            "Singapore",
            "Thailand",
            "Turkey",
            "UAE",
            "Ukraine"
        )
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, country)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val countrySelected: String = when (country[position]) {
                    "China" -> "ch"
                    "India" -> "in"
                    "Indonesia" -> "id"
                    "Japan" -> "jp"
                    "Malaysia" -> "my"
                    "Philippines" -> "ph"
                    "Poland" -> "pl"
                    "Portugal" -> "pt"
                    "Romania" -> "ro"
                    "Russia" -> "ru"
                    "Saudi Arabia" -> "sa"
                    "Serbia" -> "rs"
                    "Singapore" -> "sg"
                    "Thailand" -> "th"
                    "Turkey" -> "tr"
                    "UAE" -> "ae"
                    "Ukraine" -> "ua"
                    else -> {
                        "ch"
                    }
                }
                Toast.makeText(
                    requireContext(),
                    "Menampilkan berita ${country[position]}",
                    Toast.LENGTH_SHORT
                ).show()
                fatchNews(countrySelected)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireContext(), "nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUser() {
        homeViewModel.user.observe(viewLifecycleOwner) {
            binding.tvUsername.text = it.username
            val navigateUpdate =
                HomeFragmentDirections.actionHomeFragmentToUpdateUserFragment(it)
            binding.btnUpdate.setOnClickListener {
                findNavController().navigate(navigateUpdate)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}