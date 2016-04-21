package net.simplifiedcoding.gcmpushnotification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextName;
    private EditText editTextEmail;
    private Button button;
    private ProgressDialog loading;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        button = (Button) findViewById(R.id.button);

        loading = new ProgressDialog(this);

        sharedPrefManager = new SharedPrefManager(this);

        if(sharedPrefManager.isUserAdded()){
            startActivity(new Intent(this, UserActivity.class));
            finish();
        }
        button.setOnClickListener(this);
    }

    private void addUser() {

        loading.setMessage("Please wait...");

        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        try {
                            JSONObject obj = new JSONObject(s);
                            if (!obj.getBoolean("error")) {
                                int id = obj.getInt("id");
                                sharedPrefManager.addUser(id, name, email);
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                            }else{
                                Toast.makeText(MainActivity.this, "Oops! Some error occured", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("name", name);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        addUser();
    }
}
