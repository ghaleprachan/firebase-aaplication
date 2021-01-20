package com.example.firebaseapplication.ui.userauthentication.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseapplication.R
import com.example.firebaseapplication.databinding.FragmentRegisterBinding
import com.example.firebaseapplication.ui.profile.UserProfileActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {
    private lateinit var username: String
    private lateinit var password: String
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiInit()
    }

    private fun uiInit() {
        with(binding) {
            etUsername.setSelection(etUsername.text.toString().length)
            etPassword.setSelection(etPassword.text.toString().length)

            btnRegister.setOnClickListener {
                when {
                    etUsername.text!!.isEmpty() -> {
                        etUsername.requestFocus()
                        tilUsername.error = "Enter username!"
                    }
                    etPassword.text!!.isEmpty() -> {
                        tilUsername.error = null
                        etPassword.requestFocus()
                        tilPassword.error = "Enter password!"
                    }
                    else -> {
                        tilUsername.error = null
                        tilPassword.error = null
                        username = etUsername.text.toString()
                        password = etPassword.text.toString()
                        registerUserToFirebase()
                    }
                }
            }
            tvLogIn.setOnClickListener {
                /*findNavController().navigate(R.id.action_registerFragment_to_signInFragment)*/
                requireActivity().onBackPressed()
            }
        }
    }

    private fun registerUserToFirebase() {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    startActivity(UserProfileActivity.newIntent(requireContext()))
                } else {
                    Toast.makeText(requireContext(), "Authentication failed. ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}