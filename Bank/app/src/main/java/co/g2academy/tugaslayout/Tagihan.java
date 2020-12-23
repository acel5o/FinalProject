package co.g2academy.tugaslayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import co.g2academy.tugaslayout.model.BayarTagihan;
import co.g2academy.tugaslayout.model.CekTagihan;
import co.g2academy.tugaslayout.model.Request;
import co.g2academy.tugaslayout.viewmodels.NasabahViewModel;

public class Tagihan extends AppCompatActivity {
    NasabahViewModel nasabahViewModel;
    ImageView imageView;
    EditText nomor;
    TextView btn_cek,tagihan,btn_bayar;
    String username,tagihanTxt;

    Locale localeID = new Locale("in", "ID");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);
        nasabahViewModel = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nasabahViewModel.init();
        store();
        init();
        ButtonGroup();
    }

    public void init(){
        imageView = (ImageView) findViewById(R.id.outTagihan);
        btn_cek = findViewById(R.id.btn_cek);
        nomor = findViewById(R.id.notelp);
        tagihan = findViewById(R.id.tagihan);
        btn_bayar = findViewById(R.id.btn_bayar);
    }

    void store(){
        SharedPreferences sharedPref = getSharedPreferences("login.nasabah", Context.MODE_PRIVATE);
        username = sharedPref.getString("login.nasabah.username","-");
    }

    public void ButtonGroup(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Utama.class);
                startActivity(intent);
                finish();
            }
        });

        btn_cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CekTagihan cek = new CekTagihan();
                cek.setNomor(nomor.getText().toString());

                cekTagihan(cek);
            }
        });

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tagihan.getText().equals("Rp. 0")){
                    Toast.makeText(getApplicationContext(),"Anda tidak memiliki tagihan!",Toast.LENGTH_SHORT)
                            .show();
                }else {
                    BayarTagihan bayar = new BayarTagihan(username, nomor.getText().toString(), Integer.valueOf(tagihanTxt));
                    bayarTagihan(bayar);
                }
            }
        });
    }

    private void cekTagihan(CekTagihan nomor) {
        try {
            nasabahViewModel.getTagihan(nomor).observe(this, userResponse -> {

                if (userResponse.getRespon().equals("Nomor tidak ditemukan")) {
                    Toast.makeText(getApplicationContext(), userResponse.getRespon(), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    tagihanTxt = userResponse.getRespon();
                    tagihan.setText(rupiah.format(Long.valueOf(userResponse.getRespon())));
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void bayarTagihan(BayarTagihan nomor) {
        try {
            nasabahViewModel.bayarTagihan(nomor).observe(this, userResponse -> {

                if (!userResponse.getRespon().equals("Sukses")) {
                    Toast.makeText(getApplicationContext(), userResponse.getRespon(), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    tagihanTxt = userResponse.getRespon();
                    tagihan.setText("Rp. 0");
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
