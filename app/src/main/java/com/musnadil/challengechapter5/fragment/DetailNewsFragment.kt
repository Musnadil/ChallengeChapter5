package com.musnadil.challengechapter5.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.databinding.FragmentDetailNewsBinding


class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = arguments?.getString("img")
        val title = arguments?.getString("title")
        val sourceName = arguments?.getString("publisher")
        val publishedAt = arguments?.getString("time_published")
        val content = arguments?.getString("content","Tidak ada konten yang di tampilkan, silahkan klik tumbol kunjungi laman berita untuk melihat detail berita")
        val urlLaman = arguments?.getString("url_laman")

        Glide.with(requireContext())
            .load(image)
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .into(binding.imgDetailNews)
        binding.tvDetailNewsTittle.text = title
        binding.tvSourceName.text = sourceName
        binding.tvPublishedAt.text = publishedAt
        binding.tvContent.text = content

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}