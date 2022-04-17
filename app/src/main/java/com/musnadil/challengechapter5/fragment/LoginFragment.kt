package com.musnadil.challengechapter5.fragment

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.activity.MainActivity
import com.musnadil.challengechapter5.databinding.FragmentLoginBinding
import com.musnadil.challengechapter5.room.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoginFragment : DialogFragment() {

    private var myDb: UserDatabase? = null
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val SPUSER = "user_login"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUsername()
        myDb = UserDatabase.getInstance(requireContext())

        val preferences = this.activity?.getSharedPreferences(SPUSER, Context.MODE_PRIVATE)
        if (preferences!!.getString(USERNAME,null)!=null){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            val username = preferences.getString(USERNAME,null)
            Toast.makeText(context, "Selamat datang $username", Toast.LENGTH_SHORT).show()
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            dialog?.hide()
        }
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = binding.etUsername.text.toString().isNotEmpty() && binding.etPassowrd.text.toString().isNotEmpty() }
            override fun afterTextChanged(p0: Editable?) {}
        }
        binding.etUsername.addTextChangedListener(textWatcher)
        binding.etPassowrd.addTextChangedListener(textWatcher)

        binding.btnLogin.setOnClickListener {
            // bawa data username
            if (binding.cbRemember.isChecked){
                val editorSp : SharedPreferences.Editor = preferences!!.edit()
                editorSp.putString(USERNAME,binding.etUsername.text.toString())
                editorSp.putString(PASSWORD,binding.etPassowrd.text.toString())
                editorSp.apply()
            }
            GlobalScope.async {
                val result = myDb?.userDao()?.userCheck(
                    binding.etUsername.text.toString(),
                    binding.etPassowrd.text.toString()
                )
                runBlocking(Dispatchers.Main) {
                    if (result == false){
                        closeKeyboard()
                        val snackbar = Snackbar.make(it,"Gagal masuk mungkin anda salah memasukan email atau password",
                            Snackbar.LENGTH_INDEFINITE)
                        snackbar.setAction("Oke") {
                            snackbar.dismiss()
                            binding.etUsername.requestFocus()
                            binding.etUsername.text!!.clear()
                            binding.etPassowrd.text!!.clear()
                        }
                        snackbar.show()
                    }else{
                        Toast.makeText(requireContext(), "Selamat datang ${binding.etUsername.text}", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    private fun closeKeyboard() {
        val activity = activity as MainActivity
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    fun setUsername(){
        val username = arguments?.getString(USERNAME)
        if (username.isNullOrEmpty()) {
            binding.etUsername.hint = null
        } else {
            binding.etUsername.setText(username)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                activity?.finish()
            }
        }
    }

}