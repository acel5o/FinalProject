package co.g2academy.tugaslayout.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import co.g2academy.tugaslayout.model.BayarTagihan;
import co.g2academy.tugaslayout.model.CekTagihan;
import co.g2academy.tugaslayout.model.MutasiReq;
import co.g2academy.tugaslayout.model.Nasabah;
import co.g2academy.tugaslayout.model.NasabahResponse;
import co.g2academy.tugaslayout.model.NasabahsResponse;
import co.g2academy.tugaslayout.model.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NasabahRepository {

    private static NasabahRepository nasabahsRepository;

    public static NasabahRepository getInstance(){
        if (nasabahsRepository == null){
            nasabahsRepository = new NasabahRepository();
        }
        return nasabahsRepository;
    }

    private BankAPI nasabahApi;
    //Saldo
    public MutableLiveData<NasabahResponse> getSaldo(String username){
        MutableLiveData<NasabahResponse> userData = new MutableLiveData<>();
        nasabahApi.getSaldo(username).enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call,
                                   Response<NasabahResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    //Mutasi
    public MutableLiveData<NasabahsResponse> getMutasi(MutasiReq mutasi){
        MutableLiveData<NasabahsResponse> userData = new MutableLiveData<>();
        nasabahApi.getMutasi(mutasi).enqueue(new Callback<NasabahsResponse>() {
            @Override
            public void onResponse(Call<NasabahsResponse> call,
                                   Response<NasabahsResponse> response) {
                if (response.isSuccessful()){
                    Log.v("Body",response.body().toString());
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<NasabahsResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    //Logout
    public MutableLiveData<NasabahResponse> logOut(Request username){
        MutableLiveData<NasabahResponse> userData = new MutableLiveData<>();
        nasabahApi.logOut(username).enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call,
                                   Response<NasabahResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    //Login
    public MutableLiveData<NasabahResponse> postLogin(Request loginRequest){
        MutableLiveData<NasabahResponse> userData = new MutableLiveData<>();
        nasabahApi.postLogin(loginRequest).enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call,
                                   Response<NasabahResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    //Tagihan
    public MutableLiveData<NasabahResponse> getTagihan(CekTagihan tagihan){
        MutableLiveData<NasabahResponse> userData = new MutableLiveData<>();
        nasabahApi.getTagihan(tagihan).enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call,
                                   Response<NasabahResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    //Bayar
    public MutableLiveData<NasabahResponse> bayarTagihan(BayarTagihan payload){
        MutableLiveData<NasabahResponse> userData = new MutableLiveData<>();
        nasabahApi.bayarTagihan(payload).enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call,
                                   Response<NasabahResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }


    public NasabahRepository(){
        nasabahApi = RetrofitService.cteateService(BankAPI.class);
    }
    //Registrasi
    public MutableLiveData<NasabahResponse> postNasabah(Nasabah nasabahPayload){
        final MutableLiveData<NasabahResponse> nasabahData = new MutableLiveData<>();
        nasabahApi.postNasabah(nasabahPayload).enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call,
                                   Response<NasabahResponse> response) {
                if (response.isSuccessful()){
                    nasabahData.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return nasabahData;
    }
}
