package com.example.cecile.guilp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DatalistActivity extends AppCompatActivity {
    String myJson;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom";
    private static final String TAG_PRENOM = "prennom";
    private static final String TAG_EMAIL ="email";

    JSONArray users = null;
    ArrayList<HashMap<String, String>> usersList;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);

        setTitle("Utilisateurs");
        list = (ListView) findViewById(R.id.listView);
        usersList = new ArrayList<HashMap<String,String>>();
        getData();
    }

    private void getData() {

        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String getData_url = "http://guilp.alwaysdata.net/getData.php";
                String line;
                String result =null;
                try {


                    URL url = new URL(getData_url);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    InputStream is = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    is.close();
                    conn.disconnect();
                    result= stringBuilder.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJson=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJson);
            users = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < users.length(); i++) {
                JSONObject c = users.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String nom = c.getString(TAG_NOM);
                String prenom = c.getString(TAG_PRENOM);
                String email = c.getString(TAG_EMAIL);


                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_NOM, nom);
                persons.put(TAG_PRENOM, prenom);
                persons.put(TAG_EMAIL, email);


                usersList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    DatalistActivity.this, usersList, R.layout.list_item,
                    new String[]{TAG_ID, TAG_NOM, TAG_PRENOM, TAG_EMAIL},
                    new int[]{R.id.id, R.id.signup_Fname, R.id.signup_name, R.id.signup_email}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
