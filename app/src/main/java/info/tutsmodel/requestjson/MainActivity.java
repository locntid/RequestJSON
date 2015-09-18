package info.tutsmodel.requestjson;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://raw.githubusercontent.com/locntid/RequestJSON/master/app/src/main/res/assets/demo.json";
        new GetJSON().execute(url);

    }


    class GetJSON extends AsyncTask<String,Void, List<User>>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");

            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<User> doInBackground(String... params) {
            List<User> list = new ArrayList<>();
            try {
                FetchJSON fetchJSON = new FetchJSON(params[0]);
                JSONArray jsonArray = fetchJSON.fetchJSON();

                /*
                Phan thich theo cau truc json
                 */
                for (int i = 0;i < jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    User user = new User();
                    user.name = object.getString("name");
                    user.age = object.getInt("age");
                    list.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            ListView listView = (ListView) findViewById(R.id.listView);
            UserAdapter adapter = new UserAdapter(MainActivity.this,users);
            listView.setAdapter(adapter);
            progressDialog.dismiss();
            super.onPostExecute(users);
        }
    }
}
