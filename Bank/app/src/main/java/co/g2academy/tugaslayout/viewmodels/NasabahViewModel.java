package co.g2academy.tugaslayout.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.g2academy.tugaslayout.model.BayarTagihan;
import co.g2academy.tugaslayout.model.CekTagihan;
import co.g2academy.tugaslayout.model.MutasiReq;
import co.g2academy.tugaslayout.model.Nasabah;
import co.g2academy.tugaslayout.model.NasabahResponse;
import co.g2academy.tugaslayout.model.NasabahsResponse;
import co.g2academy.tugaslayout.model.Request;
import co.g2academy.tugaslayout.networking.NasabahRepository;

public class NasabahViewModel extends ViewModel {

    private MutableLiveData<NasabahResponse> mutableNasabahLiveData;
    private NasabahRepository nasabahsRepository;
    private MutableLiveData<NasabahsResponse> mutableLiveData;

    public void init(){
        if (mutableNasabahLiveData != null){
            return;
        }
        nasabahsRepository = NasabahRepository.getInstance();
    }

    //Tagihan
    public LiveData<NasabahResponse> getTagihan(CekTagihan tagihan) {
        if (mutableNasabahLiveData == null){
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableNasabahLiveData = nasabahsRepository.getTagihan(tagihan);
        return mutableNasabahLiveData;
    }

    //BayaTagihan
    public LiveData<NasabahResponse> bayarTagihan(BayarTagihan tagihan) {
        if (mutableNasabahLiveData == null){
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableNasabahLiveData = nasabahsRepository.bayarTagihan(tagihan);
        return mutableNasabahLiveData;
    }

    //Mutasi
    public LiveData<NasabahsResponse> getNasabahsRepository(MutasiReq mutasi) {
        if (mutableNasabahLiveData == null){
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableLiveData = nasabahsRepository.getMutasi(mutasi);
        return mutableLiveData;
    }

    //Saldo
    public LiveData<NasabahResponse> getNasabahRepository(String username) {
        if (mutableNasabahLiveData == null){
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableNasabahLiveData = nasabahsRepository.getSaldo(username);
        return mutableNasabahLiveData;
    }

    //Logout
    public LiveData<NasabahResponse> logOut(Request username) {
        if (mutableNasabahLiveData == null) {
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableNasabahLiveData = nasabahsRepository.logOut(username);

        return mutableNasabahLiveData;
    }

    //Login
    public LiveData<NasabahResponse> postLogin(Request loginRequest) {
        if (mutableNasabahLiveData == null) {
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableNasabahLiveData = nasabahsRepository.postLogin(loginRequest);

        return mutableNasabahLiveData;
    }

    //Registrasi
    public LiveData<NasabahResponse> postNasabahRepository(Nasabah nasabahPayload) {

        if (mutableNasabahLiveData == null) {
            nasabahsRepository = NasabahRepository.getInstance();
        }
        mutableNasabahLiveData = nasabahsRepository.postNasabah(nasabahPayload);
        return mutableNasabahLiveData;
    }
}

