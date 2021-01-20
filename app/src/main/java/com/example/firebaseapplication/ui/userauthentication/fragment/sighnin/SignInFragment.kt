package com.example.firebaseapplication.ui.userauthentication.fragment.sighnin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.firebaseapplication.R
import com.example.firebaseapplication.databinding.FragmentSignInBinding
import com.example.firebaseapplication.ui.profile.UserProfileActivity
import com.example.firebaseapplication.ui.userauthentication.fragment.register.RegisterFragment.Companion.SIGN_IN_RESULT_CODE
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

/*
private const val TAG = "SignInFragment"*/

class SignInFragment : Fragment() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var username: String
    private lateinit var password: String
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val manager: FragmentManager by lazy { childFragmentManager }
    private lateinit var mMyFragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiUi()
        /*instantiateFragments(savedInstanceState)*/
    }

    private fun intiUi() {
        with(binding) {
            etUsername.setSelection(etUsername.text.toString().length)
            etPassword.setSelection(etPassword.text.toString().length)

            btnLogIn.setOnClickListener {
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
                        userValidationWithFirebase()
                    }
                }
            }
            tvRegister.setOnClickListener {
//                launchSignInFlow()
                findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
            }
        }
    }

    /*private fun instantiateFragments(inState: Bundle?) {
        val transaction: FragmentTransaction = manager.beginTransaction()
        if (inState != null) {
            mMyFragment = (manager.getFragment(inState, TAG) as SignInFragment?)!!
        } else {
            mMyFragment = SignInFragment()
            transaction.add(mMyFragment, TAG)
            transaction.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        manager.putFragment(outState, TAG, SignInFragment())
    }*/


    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
            // This is where you can provide more ways for users to register and
            // sign in.
        )

        // Create and launch the sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE.
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), SIGN_IN_RESULT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "${auth.currentUser!!.email}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Sign in unsuccessful ${response?.error?.errorCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun userValidationWithFirebase() {
        binding.btnLogIn.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.btnLogIn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                    beginActivity()
                } else {
                    binding.btnLogIn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            beginActivity()
        }
    }

    private fun beginActivity() {
        startActivity(UserProfileActivity.newIntent(requireContext()))
        requireActivity().finish()
    }
}