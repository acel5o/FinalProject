package co.g2academy.tugaslayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import co.g2academy.tugaslayout.adapter.MutasiAdapter;
import co.g2academy.tugaslayout.model.Mutasi;
import co.g2academy.tugaslayout.model.MutasiReq;
import co.g2academy.tugaslayout.model.Request;
import co.g2academy.tugaslayout.viewmodels.NasabahViewModel;

public class Utama extends AppCompatActivity {
    long today;
    long monthBefore;
    private Pair<Long, Long> todayPair;
    private Pair<Long, Long> mothBeforePair;
    private ImageView imageView,out;
    private ImageButton imageButton;
    private TextView saldo;
    NasabahViewModel nasabahViewModel;
    String username;

    Locale localeID = new Locale("in", "ID");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeID);

    ArrayList<Mutasi> MutasiArray = new ArrayList<>();
    MutasiAdapter mutasiAdapter;
    RecyclerView rvNasabah;
    List<Mutasi> mutasis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        store();
        findViewById();
        getClearedUTC();
        initSetting();
        nasabahViewModel = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nasabahViewModel.init();
        getSaldo(username);
        onGroupClick();

        if (mutasiAdapter == null) {
            mutasiAdapter = new MutasiAdapter(Utama.this, MutasiArray);
            rvNasabah.setLayoutManager(new LinearLayoutManager(this));
            rvNasabah.setAdapter(mutasiAdapter);
            rvNasabah.setItemAnimator(new DefaultItemAnimator());
            rvNasabah.setNestedScrollingEnabled(true);
        } else {
            mutasiAdapter.notifyDataSetChanged();
        }
    }


    private void getSaldo(String username){
        try {
            nasabahViewModel.getNasabahRepository(username).observe(this, nasabahResponse -> {
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                if (nasabahResponse.getRespon().equals("User not found!")) {
                    Toast.makeText(getApplicationContext(), nasabahResponse.getRespon(), Toast.LENGTH_LONG).show();
                }else if(nasabahResponse.getRespon().equals("Login is required, please Login first!")) {

                } else {
                    String saldos = nasabahResponse.getRespon();

                    saldo.setText(rupiah.format(Long.valueOf(saldos)));
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSaldo(username);
    }

    void store(){
        SharedPreferences sharedPref = getSharedPreferences("login.nasabah", Context.MODE_PRIVATE);
        username = sharedPref.getString("login.nasabah.username","-");
    }

    void findViewById() {
       imageView = (ImageView) findViewById(R.id.mutasi_img);
       imageButton = (ImageButton) findViewById(R.id.bill);
       saldo = findViewById(R.id.Saldo);
       out = (ImageView) findViewById(R.id.outUtama);
       rvNasabah = findViewById(R.id.nasabahRecyclerView);
    }

    private static Calendar getClearedUTC() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        return calendar;
    }

    private void initSetting() {
        today = MaterialDatePicker.todayInUtcMilliseconds();

        Calendar calendar = getClearedUTC();
        calendar.roll(Calendar.MONTH,  -1);
        monthBefore = calendar.getTimeInMillis();

        todayPair = new Pair<>(today, today);
        mothBeforePair = new Pair<>(monthBefore, monthBefore);
    }



    void onGroupClick(){
        initSetting();
        MaterialDatePicker.Builder<Pair<Long, Long>> builderRange = MaterialDatePicker.Builder.dateRangePicker();
        builderRange.setCalendarConstraints(oneMonthBeforeTodayConstraints().build());
        MaterialDatePicker<Pair<Long, Long>> pickerRange = builderRange.build();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerRange.show(getSupportFragmentManager(), pickerRange.toString());
            }
        });

        pickerRange.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                String from = simpleFormat.format(selection.first);
                String to = simpleFormat.format(selection.second);

                mutasiCek(from,to);

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Tagihan.class);
                startActivity(intent);
                finish();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }



    public void Logout(){
        Log.v("Respon",username);
        Request usernames = new Request(username);
        nasabahViewModel.logOut(usernames).observe(this, nasabahResponse -> {
            Log.v("Respon",nasabahResponse.getRespon());
            if(nasabahResponse.getRespon().equals("Logout success!")){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),
                        nasabahResponse.getRespon(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mutasiCek(String from, String to){
            MutasiReq mutasi = new MutasiReq();
            mutasi.setUsername(username);
            mutasi.setFromDate(from);
            mutasi.setToDate(to);
        try {
            nasabahViewModel.getNasabahsRepository(mutasi).observe(this, nasabahsResponse -> {
                while (nasabahsResponse == null) {
                    try {
                        Thread.sleep(1200);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                List<Mutasi> respon = nasabahsResponse.getRespon();
                if (respon.size() != 0) {
                    mutasis = nasabahsResponse.getRespon();
                    MutasiArray.clear();
                    MutasiArray.addAll(mutasis);
                    mutasiAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Utama.this, "Transaksi Tidak Ditemukan",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private CalendarConstraints.Builder oneMonthBeforeTodayConstraints() {
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        Calendar maxDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, -30); // subtracting 30 days

        constraintsBuilderRange.setStart(minDate.getTimeInMillis());
        constraintsBuilderRange.setEnd(maxDate.getTimeInMillis());

        constraintsBuilderRange.setValidator(new RangeValidator(minDate.getTimeInMillis(), maxDate.getTimeInMillis()));

        return constraintsBuilderRange;
    }

    static class RangeValidator implements CalendarConstraints.DateValidator {

        long minDate, maxDate;

        RangeValidator(long minDate, long maxDate) {
            this.minDate = minDate;
            this.maxDate = maxDate;
        }

        RangeValidator(Parcel parcel) {
        }

        @Override
        public boolean isValid(long date) {
            return !(minDate > date || maxDate < date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static final Parcelable.Creator<RangeValidator> CREATOR = new Parcelable.Creator<RangeValidator>() {

            @Override
            public RangeValidator createFromParcel(Parcel parcel) {
                return new RangeValidator(parcel);
            }

            @Override
            public RangeValidator[] newArray(int size) {
                return new RangeValidator[size];
            }
        };
    }
}
