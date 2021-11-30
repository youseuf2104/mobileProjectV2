package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {
    EditText username,email, password, conpassword;
    Button signup;
    float p=0;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        username = root.findViewById(R.id.username1);
        email = root.findViewById(R.id.email1);
        password = root.findViewById(R.id.password1);
        conpassword = root.findViewById(R.id.confirmpass);
        signup = root.findViewById(R.id.signup);

        username.setTranslationX(800);
        email.setTranslationX(800);
        password.setTranslationX(800);
        conpassword.setTranslationX(800);
        signup.setTranslationX(800);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        conpassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override

            public void onClick(View view) {
                DBHelper database = new DBHelper(getContext());
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                String emailText = email.getText().toString();
                String conpasswordText = conpassword.getText().toString();
                Toast myToast;

                if (usernameText.equals("") || passwordText.equals("") || emailText.equals("") || conpasswordText.equals("")) {
                    myToast = Toast.makeText(getActivity(), "Empty Field(s) found. Try Again.", Toast.LENGTH_SHORT);
                }
                else if (database.checkUser(usernameText) || database.checkEmail(emailText)){
                    myToast = Toast.makeText(getActivity(), "Username or Email taken. Try Again.", Toast.LENGTH_SHORT);
                }
                else if ( passwordText.length()<5){
                    myToast = Toast.makeText(getActivity(), "Password is too short. Try Again.", Toast.LENGTH_SHORT);
                }
                else if (!(passwordText.equals(conpasswordText))){
                    myToast = Toast.makeText(getActivity(), "Passwords do no match. Try Again.", Toast.LENGTH_SHORT);
                }
                else {
                    database.insertAccount(usernameText,emailText,passwordText);
                    username.setText("");
                    email.setText("");
                    password.setText("");
                    conpassword.setText("");
                    myToast = Toast.makeText(getActivity(), "Sign up successful.", Toast.LENGTH_SHORT);
                }
                myToast.show();
            }

        });









        return root;
    }
}
