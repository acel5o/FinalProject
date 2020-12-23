package co.g2academy.telkom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import co.g2academy.telkom.model.CekTagihan;
import co.g2academy.telkom.viewmodels.NasabahViewModel;

public class Utama extends AppCompatActivity {

    NasabahViewModel nasabahViewModel;
    TextView txt_nomor,txtTagih;
    ImageView refresh;

    Locale localeID = new Locale("in", "ID");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        init();

        nasabahViewModel = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nasabahViewModel.init();
        refreshButton();
        callTagih();
    }

    private void refreshButton(){
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTagih();
            }
        });
    }

    private void callTagih(){
        CekTagihan cek = new CekTagihan();
        cek.setNomor(txt_nomor.getText().toString());
        cekTagihan(cek);
    }

    private void init(){
        txt_nomor = findViewById(R.id.txt_nomor);
        txtTagih  = findViewById(R.id.rupiah);
        refresh = findViewById(R.id.refresh);
    }

    private void cekTagihan(CekTagihan nomor) {
        try {
            nasabahViewModel.getTagihan(nomor).observe(this, userResponse -> {

                if (userResponse.getRespon().equals("Nomor tidak ditemukan")) {
                    Toast.makeText(getApplicationContext(), userResponse.getRespon(), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    txtTagih.setText(rupiah.format(Long.valueOf(userResponse.getRespon())));
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
