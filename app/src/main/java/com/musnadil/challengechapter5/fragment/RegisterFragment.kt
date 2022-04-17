package com.musnadil.challengechapter5.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.databinding.FragmentRegisterBinding
import com.musnadil.challengechapter5.room.database.UserDatabase
import com.musnadil.challengechapter5.room.entity.User
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class RegisterFragment : DialogFragment() {
    private var myDb: UserDatabase? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setup database
        myDb = UserDatabase.getInstance(requireContext())
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, p2: Int, p3: Int) {
                binding.btnSignup.isEnabled =
                    binding.etUsername.text.toString().isNotEmpty() &&
                            binding.etEmail.text.toString().isNotEmpty() &&
                            binding.etPassowrd.text.toString().isNotEmpty() &&
                            binding.etConfirmPassowrd.text.toString().isNotEmpty()
                if (start != 0) {
                    binding.usernameContainer.error = null
                    binding.emailContainer.error = null
                    binding.passwordContainer.error = null
                    binding.confirmPasswordContainer.error = null
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.etUsername.addTextChangedListener(textWatcher)
        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPassowrd.addTextChangedListener(textWatcher)
        binding.etConfirmPassowrd.addTextChangedListener(textWatcher)

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            dialog?.hide()
        }
        binding.btnSignup.setOnClickListener {
            // disini bawa data username untuk login
            if (binding.etPassowrd.text.toString() != binding.etConfirmPassowrd.text.toString()) {
                binding.confirmPasswordContainer.error = "Password tidak sama"
                binding.etConfirmPassowrd.text?.clear()
                binding.etConfirmPassowrd.requestFocus()
            } else {
            val objectUser = User(
                null,
                binding.etUsername.text.toString(),
                binding.etPassowrd.text.toString(),
                binding.etEmail.text.toString()
            )
                GlobalScope.async {
                    val result = myDb?.userDao()?.addUser(objectUser)
                    runBlocking(Dispatchers.Main) {
                        if (result !=0.toLong()){
                            Toast.makeText(requireContext(), "Registration success", Toast.LENGTH_SHORT).show()
                            val bunlde = Bundle().apply {
                                putString(LoginFragment.USERNAME, binding.etUsername.text.toString())
                            }
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment,bunlde)
                        }else{
                            Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog?.hide()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                dialog?.hide()
            }
        }
    }
}