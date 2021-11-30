package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {
    EditText email, username, password;
    Button login;
    TextView forgetpassword;
    float p=0;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

    email = root.findViewById(R.id.username1);
    password = root.findViewById(R.id.password);
    username = root.findViewById(R.id.username1);
    forgetpassword = root.findViewById(R.id.forgetpassword);
    login = root.findViewById(R.id.button);

    username.setTranslationX(800);
    email.setTranslationX(800);
    password.setTranslationX(800);
    forgetpassword.setTranslationX(800);
    login.setTranslationX(800);

    email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetpassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();




        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override

            public void onClick(View view) {
                DBHelper database = new DBHelper(getContext());
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                boolean checker = database.checkLogin(usernameText,passwordText);
                Toast myToast;
                if (checker) {
                    myToast = Toast.makeText(getActivity(), "Logged in Successful!", Toast.LENGTH_SHORT);
                    //Move to MainActivity from here
                }

                else {
                    myToast = Toast.makeText(getActivity(), "Log in error. Try Again.", Toast.LENGTH_SHORT);
                }
                myToast.show();







                /*
                if (usernameText.equals("") || passwordText.equals("")){
                    Toast myToast = Toast.makeText(getActivity(),"Log In Error. Try Again.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    database.insertAccount(usernameText, "random email", passwordText);
                }

                 */

            }
        });

        return root;

    }


}
