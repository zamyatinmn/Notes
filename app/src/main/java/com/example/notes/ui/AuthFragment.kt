package com.example.notes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notes.App
import com.example.notes.R
import com.example.notes.ui.list.ListOfNotesFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class AuthFragment : Fragment() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var SignInBtn: SignInButton
    private lateinit var emailView: TextView
    private lateinit var continueBtn: Button
    private lateinit var signOutBtn: Button
    private lateinit var app: App
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.auth, container, false)
        app = requireActivity().application as App
        initGoogleSign()
        initView(view)
        enableSign()
        return view
    }

    private fun initGoogleSign() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun initView(view: View) {
        SignInBtn = view.findViewById(R.id.sign_in_btn)
        SignInBtn.setOnClickListener {
            signIn()
        }
        emailView = view.findViewById(R.id.email)
        continueBtn = view.findViewById(R.id.continue_btn)
        continueBtn.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListOfNotesFragment.newInstance())
                .commit()
        }
        signOutBtn = view.findViewById(R.id.sign_out_btn)
        signOutBtn.setOnClickListener { signOut() }
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                updateUI("")
                enableSign()
            }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        account?.let {
            disableSign()
            it.email?.let { email -> updateUI(email) }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            disableSign()
            account?.email?.let { updateUI(it) }
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateUI(email: String) {
        emailView.text = email
    }

    private fun enableSign() {
        SignInBtn.isEnabled = true
        continueBtn.isEnabled = false
        signOutBtn.isEnabled = false
        app.isAuthorized = false
    }

    private fun disableSign() {
        SignInBtn.isEnabled = false
        continueBtn.isEnabled = true
        signOutBtn.isEnabled = true
        app.isAuthorized = true
    }

    companion object {
        private val TAG = AuthFragment::class.java.simpleName
        private const val RC_SIGN_IN = 40404
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
}