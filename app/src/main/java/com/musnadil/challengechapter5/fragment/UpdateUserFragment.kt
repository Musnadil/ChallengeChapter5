package com.musnadil.challengechapter5.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.UserManager
import com.musnadil.challengechapter5.databinding.FragmentUpdateUserBinding
import com.musnadil.challengechapter5.room.database.UserDatabase
import com.musnadil.challengechapter5.room.entity.User
import com.musnadil.challengechapter5.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UpdateUserFragment : DialogFragment() {
    private var _binding: FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!
    var myDb: UserDatabase? = null
    private val args: UpdateUserFragmentArgs by navArgs()
    lateinit var homeViewModel: HomeViewModel

    private lateinit var userManager: UserManager

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
        userManager = UserManager(requireContext())

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        homeViewModel.getDataUser().observe(viewLifecycleOwner) {
            binding.etUsername.setText(it.username)
            binding.etEmail.setText(it.email)
            binding.etPassowrd.setText(it.password)
        }
        binding.btnUpdate.setOnClickListener {
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
    }
}