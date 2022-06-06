package com.musnadil.challengechapter5.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter5.R
import com.musnadil.challengechapter5.data.room.entity.User
import com.musnadil.challengechapter5.ui.theme.ChallengeChapter5Theme
import com.musnadil.challengechapter5.ui.theme.yellow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterCompose : Fragment() {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                ChallengeChapter5Theme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Column {
                            Header()
                            ActionRegister()
                        }
                    }
                }

            }
        }
    }

    private val poppinsFamily = FontFamily(
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.Medium),
        Font(R.font.poppins_regular, FontWeight.Normal)
    )

    @Composable
    fun Header() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Sign Up",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }

    @Composable
    fun ActionRegister() {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        var confirmPasswordVisibility by remember { mutableStateOf(false) }
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text(text = "Username") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text(text = "Confirm Password") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal
                ),
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordVisibility = !confirmPasswordVisibility
                    }) {
                        Icon(
                            imageVector = if (confirmPasswordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (username == "" || email == "" || password == "" || confirmPassword == "") {
                            android.app.AlertDialog.Builder(requireContext())
                                .setTitle("")
                                .setMessage("Semua kolom harus diisi")
                                .setPositiveButton("Coba login kembali") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(
                                requireContext(),
                                "Password konfirmasi tidak sama",
                                Toast.LENGTH_LONG
                            ).show()
                            confirmPassword = ""
                        } else {
                            val user = User(null, username, email, password)
                            authViewModel.register(user)
                            authViewModel.resultRegister.observe(viewLifecycleOwner) {
                                if (it != null) {
                                    if (it != 0.toLong()) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Registration success",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        findNavController().navigate(
                                            R.id.action_registerCompose_to_loginCompose
                                        )
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Registration failed",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.clickable(
                        onClick = {
                            findNavController()
                                .navigate(R.id.action_registerCompose_to_loginCompose)
                        }),
                    text = "Sign In",
                    style = TextStyle(
                        color = yellow,
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        ChallengeChapter5Theme {
            Column {
                Header()
                ActionRegister()
            }
        }
    }
}