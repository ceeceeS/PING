package com.example.cecile.guilp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity  {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    EditText email, password;
    String LOG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.Login_pass);

    }

    public void OnclickBtnLogin(View view)
    {
        if(view.getId() == R.id.login_button)
        {
        String GEmail = email.getText().toString();
        String GPassword = password.getText().toString();
        String type = "login";
        LinkDatabase linkdb = new LinkDatabase(this);
        linkdb.execute(type,GEmail,GPassword);
        }
        if(view.getId() == R.id.redirect_signup_button)
        {
            Intent i = new Intent(LoginActivity.this,SignupActivity.class);
            startActivity(i);
        }
    }

   /* @Override
    public void processFinish(String s) {
        Log.d(LOG, s);
        if(s.contains("succes")){
            Toast.makeText(LoginActivity.this,"succes Login",Toast.LENGTH_LONG).show();
            Intent in = new Intent(LoginActivity.this,Home_Action.class);
            startActivity(in);
        }
        else{
            Toast.makeText(LoginActivity.this, "erreur Login", Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
    }*/
}
