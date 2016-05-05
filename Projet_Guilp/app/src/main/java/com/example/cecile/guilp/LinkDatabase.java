package com.example.cecile.guilp;

import android.app.AlertDialog;
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
 * Created by cecile on 21/04/2016.
 */
public class LinkDatabase  extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alerdialog;

    public LinkDatabase(Context ctxt)
    {
        context = ctxt;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://guilp.alwaysdata.net/login.php";
        if (type.equals("login")) {
            try {
                String txtmail = params[1];
                String txtPassword = params[2];
                URL url = new URL(login_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String post_data = URLEncoder.encode("txtmail", "UTF-8") + "=" + URLEncoder.encode(txtmail, "UTF-8") + "&"
                        + URLEncoder.encode("txtPassword", "UTF-8") + "=" + URLEncoder.encode(txtPassword, "UTF-8");
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
    protected  void onPreExecute(){
        alerdialog = new AlertDialog.Builder(context).create();
        alerdialog.setTitle("Login status");
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result.contentEquals("success")) {

            context.startActivity(new Intent(context, DatalistActivity.class));


        }else if(result.contentEquals("champsvide")) {
            Toast toast= Toast.makeText(context, "Vous devez remplir tout les champs !", Toast.LENGTH_SHORT);
            toast.show();

            }else if(result.contentEquals("erreur"))
        {
            Toast toast= Toast.makeText(context, "Oups une erreur est survenue lors de votre inscription! veuillez recommencer", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast= Toast.makeText(context, "les mots de passe ne sont pas identiques", Toast.LENGTH_SHORT);
            toast.show();
        }



    }
    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }
}
