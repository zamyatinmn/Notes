package com.example.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.ui.list.ListOfNotesFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class AuthFragment extends Fragment {

    private static final String TAG = AuthFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 40404;
    private GoogleSignInClient googleSignInClient;
    private com.google.android.gms.common.SignInButton SignInBtn;
    private TextView emailView;
    private Button continueBtn;
    private Button signOutBtn;
    private App app;

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth, container, false);
        app = (App) requireActivity().getApplication();
        initGoogleSign();
        initView(view);
        enableSign();
        return view;
    }

    private void initGoogleSign() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void initView(View view) {
        SignInBtn = view.findViewById(R.id.sign_in_btn);
        SignInBtn.setOnClickListener(v -> signIn()
        );
        emailView = view.findViewById(R.id.email);
        continueBtn = view.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(v -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ListOfNotesFragment.newInstance())
                .commit());
        signOutBtn = view.findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(v -> signOut());
    }

    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(task -> {
                    updateUI("");
                    enableSign();
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            disableSign();
            updateUI(account.getEmail());
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            disableSign();
            updateUI(account.getEmail());

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(String email) {
        emailView.setText(email);
    }

    private void enableSign() {
        SignInBtn.setEnabled(true);
        continueBtn.setEnabled(false);
        signOutBtn.setEnabled(false);
        app.isAuthorized = false;
    }

    private void disableSign() {
        SignInBtn.setEnabled(false);
        continueBtn.setEnabled(true);
        signOutBtn.setEnabled(true);
        app.isAuthorized = true;
    }
}
