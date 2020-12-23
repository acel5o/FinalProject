package co.g2academy.tugaslayout;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import co.g2academy.tugaslayout.model.Nasabah;
import co.g2academy.tugaslayout.viewmodels.NasabahViewModel;

public class Register  extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    EditText nama,ktp,alamat,username,password;
    private Nasabah nasabah=new Nasabah();
    NasabahViewModel nasabahViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        clickGroup();
        nasabahViewModel = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nasabahViewModel.init();
    }



    public void init(){
        imageView = (ImageView) findViewById(R.id.outRegister);
        textView = (TextView) findViewById(R.id.btn_regis);
        nama = (EditText) findViewById(R.id.nama);
        ktp = (EditText) findViewById(R.id.ktp);
        alamat = (EditText) findViewById(R.id.alamat);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    public void clickGroup(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Nasabah nasabahPayload = new Nasabah();
                    nasabahPayload.setNama(nama.getText().toString());
                    nasabahPayload.setKtp(ktp.getText().toString());
                    nasabahPayload.setAlamat(alamat.getText().toString());
                    nasabahPayload.setSaldo(0);
                    nasabahPayload.setUsername(username.getText().toString());
                    nasabahPayload.setPassword(password.getText().toString());
                    postNasabah(nasabahPayload);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    private void postNasabah(Nasabah nasabahPayload ){
        try {
            nasabahViewModel.postNasabahRepository(nasabahPayload).observe(this, nasabahResponse -> {
               while (nasabahResponse==null){
                   try {
                       Thread.sleep(1500);
                   } catch (Exception e) {
                       System.out.println(e);
                   }
               }
                if (nasabahResponse.getRespon().equals("Sukses")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (nasabahResponse.getRespon().equals("No.KTP sudah digunakan")) {
                    Toast.makeText(getApplicationContext(), nasabahResponse.getRespon(), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
