package co.g2academy.telkom.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import co.g2academy.telkom.model.CekTagihan;
import co.g2academy.telkom.model.TelkomResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelkomRepository {

    private static TelkomRepository telkomRepository;

    public static TelkomRepository getInstance(){
        if (telkomRepository == null){
            telkomRepository = new TelkomRepository();
        }
        return telkomRepository;
    }

    private BankAPI nasabahApi;

    public TelkomRepository(){
        nasabahApi = RetrofitService.cteateService(BankAPI.class);
    }

    //Tagihan
    public MutableLiveData<TelkomResponse> getTagihan(CekTagihan tagihan){
        MutableLiveData<TelkomResponse> userData = new MutableLiveData<>();
        nasabahApi.getTagihan(tagihan).enqueue(new Callback<TelkomResponse>() {
            @Override
            public void onResponse(Call<TelkomResponse> call,
                                   Response<TelkomResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<TelkomResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }
}
