package co.g2academy.tugaslayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.g2academy.tugaslayout.model.Nasabah;
import co.g2academy.tugaslayout.model.Request;
import co.g2academy.tugaslayout.viewmodels.NasabahViewModel;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    TextView textView,login;
    EditText user,pass;
    NasabahViewModel nasabahViewModel;
    Request nasabahPayload;

    String TAG = MainActivity.class.getSimpleName();

    String SITE_KEY = "6LeUqhYaAAAAAMvAzo1nqpXwVIaIUfVX-AGxRU3E";
    String SECRET_KEY = "6LeUqhYaAAAAAAS3dMrcaXxYow1uyO1S6WxJepRX";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        nasabahViewModel = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nasabahViewModel.init();
        clickGroup();

        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public void init(){
        textView = (TextView) findViewById(R.id.createNew);
        login = findViewById(R.id.btn_login);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
    }

    public void clickGroup(){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SafetyNet.getClient(MainActivity.this).verifyWithRecaptcha(SITE_KEY)
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                if (!response.getTokenResult().isEmpty()) {
                                    handleSiteVerify(response.getTokenResult());
                                }
                            }
                        })
                        .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    Log.d(TAG, "Error message: " + CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                                } else {
                                    Log.d(TAG, "Unknown type of error: " + e.getMessage());
                                }
                            }
                        });
            }
        });
    }

    protected  void handleSiteVerify(final String responseToken){
        //it is google recaptcha siteverify server
        //you can place your server url
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")){
                                nasabahPayload = new Request(user.getText().toString(), pass.getText().toString());
                                doLogin(nasabahPayload);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("error-codes")),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Log.d(TAG, "JSON exception: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error message: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                2000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void doLogin(Request nasabahPayloads ){
        try {
            nasabahViewModel.postLogin(nasabahPayloads).observe(this, userResponse -> {

                SharedPreferences sharedPref = getSharedPreferences("login.nasabah", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (userResponse.getRespon().equals("User has already login")) {
                    Toast.makeText(getApplicationContext(), userResponse.getRespon(), Toast.LENGTH_LONG).show();
                } else if (userResponse.getRespon().equals("Wrong Username or Password!")) {
                    Toast.makeText(getApplicationContext(), userResponse.getRespon(), Toast.LENGTH_LONG).show();
                } else if (userResponse.getRespon().equals("Wrong Password!")) {
                    Toast.makeText(getApplicationContext(), userResponse.getRespon(), Toast.LENGTH_LONG).show();
                }else if(userResponse.getRespon().equals("Sudah Login!")){
                    doLogin(nasabahPayload);
                } else{
                    editor.putString("login.nasabah.username", userResponse.getRespon());
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), Utama.class);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2212) {
            if (resultCode == Activity.RESULT_OK) {
                String selectedImage = data.getExtras().getString("streetkey");
                Toast.makeText(MainActivity.this,selectedImage,Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}