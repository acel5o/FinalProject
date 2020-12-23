package co.g2academy.tugaslayout;

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

import co.g2academy.tugaslayout.model.Nasabah;
import co.g2academy.tugaslayout.model.Request;
import co.g2academy.tugaslayout.viewmodels.NasabahViewModel;

public class MainActivity extends AppCompatActivity {

    TextView textView,login;
    EditText user,pass;
    NasabahViewModel nasabahViewModel;
    Request nasabahPayload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        nasabahViewModel = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nasabahViewModel.init();
        clickGroup();
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
            public void onClick(View v) {
                nasabahPayload = new Request(user.getText().toString(), pass.getText().toString());
                doLogin(nasabahPayload);
            }
        });
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
}