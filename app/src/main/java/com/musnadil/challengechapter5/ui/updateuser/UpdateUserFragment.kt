package com.musnadil.challengechapter5.ui.updateuser

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.data.datastore.UserPreferences
import com.musnadil.challengechapter5.databinding.FragmentUpdateUserBinding
import com.musnadil.challengechapter5.data.room.database.UserDatabase
import com.musnadil.challengechapter5.data.room.entity.User
import com.musnadil.challengechapter5.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

class UpdateUserFragment : DialogFragment() {
    private var _binding: FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!
    var myDb: UserDatabase? = null
    private val args: UpdateUserFragmentArgs by navArgs()
    lateinit var homeViewModel: HomeViewModel

    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        _binding = FragmentUpdateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDb = UserDatabase.getInstance(requireContext())
        userPreferences = UserPreferences(requireContext())

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        homeViewModel.getDataUser()
        homeViewModel.user.observe(viewLifecycleOwner) {
            binding.etUsername.setText(it.username)
            binding.etEmail.setText(it.email)
            binding.etPassowrd.setText(it.password)
        }
        binding.btnUpdate.setOnClickListener {
            updateUser()
        }

        binding.btnAdd.setOnClickListener {
            openImagePicker()
        }
    }

    private fun updateUser() {
        val user = User(
            args.user.id,
            binding.etUsername.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassowrd.text.toString()
        )
        lifecycleScope.launch(Dispatchers.IO) {
            val result = myDb?.userDao()?.updateItem(user)
            runBlocking(Dispatchers.Main) {
                if (result != 0) {
                    Toast.makeText(
                        requireContext(), "User berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "User gagal diupdate", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            if (result != 0) {
                homeViewModel.setDataUser(user)
            }
        }
        dialog?.dismiss()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    fileUri?.let { loadImage(it) }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }
    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .circleCrop()
                .into(ivProfile)

        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }
}