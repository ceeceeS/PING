package com.example.cecile.guilp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    EditText firstName,lastName,password,cofirmPassword,email;

    String LOG="SignupActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstName = (EditText) findViewById(R.id.signup_name);
        lastName = (EditText) findViewById(R.id.signup_Fname);
        password = (EditText) findViewById(R.id.signup_password);
        cofirmPassword = (EditText) findViewById(R.id.signup_confirmpass);
        email = (EditText) findViewById(R.id.signup_email);
    }



    public void onSignupClick(View view)
    {
        if(view.getId()== R.id.sign_button) {
            String nom = lastName.getText().toString();
            String prenom = firstName.getText().toString();
            String pass = password.getText().toString();
            String cpass = cofirmPassword.getText().toString();
            String mail = email.getText().toString();
            String type = "signup";
            LinkDatabase linkdb = new LinkDatabase(this);
            linkdb.execute(type, nom, prenom, pass, cpass, mail);
        }


    }

    public void redirectBtnLogin(View view)
    {
        if(view.getId()== R.id.log_button)
        {
            Intent i = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(i);
        }
    }



}
