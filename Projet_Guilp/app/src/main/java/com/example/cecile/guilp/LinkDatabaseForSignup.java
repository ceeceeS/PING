package com.example.cecile.guilp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cecile on 29/04/2016.
 */
public class LinkDatabaseForSignup  extends AsyncTask<String, Void, String> {
    Context context;

    public LinkDatabaseForSignup(Context ctxt) {
        context = ctxt;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String signup_url = "http://guilp.alwaysdata.net/signup.php";
        if (type.equals("signup")) {
            try {
                String lastName = params[1];
                String firstName = params[2];
                String password = params[3];
                String repeatpass = params[4];
                String email = params[5];
                URL url = new URL(signup_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String post_data = URLEncoder.encode("txtname", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&" +
                        URLEncoder.encode("txtprenom", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                        URLEncoder.encode("txtPassword", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("txtRepeatPassword", "UTF-8") + "=" + URLEncoder.encode(repeatpass, "UTF-8") + "&" +
                        URLEncoder.encode("txtmail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream is = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                is.close();
                conn.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result)
    {
        if(result.contentEquals("success")) {
            Toast toast= Toast.makeText(context, "Inscription effectuée. A présent connectez vous!", Toast.LENGTH_SHORT);
            toast.show();
            context.startActivity(new Intent(context, LoginActivity.class));


        }else if(result.contentEquals("champsvide")) {
            Toast toast= Toast.makeText(context, "Vous devez remplir tout les champs !", Toast.LENGTH_SHORT);
            toast.show();

        }
        else
        {
            Toast toast= Toast.makeText(context, "L'email ou le mots de passe est incorrecte", Toast.LENGTH_SHORT);
            toast.show();
        }



    }
    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }
}
