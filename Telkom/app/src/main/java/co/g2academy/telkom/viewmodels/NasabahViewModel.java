package co.g2academy.telkom.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.g2academy.telkom.model.CekTagihan;
import co.g2academy.telkom.model.TelkomResponse;
import co.g2academy.telkom.networking.TelkomRepository;


public class NasabahViewModel extends ViewModel {

    private MutableLiveData<TelkomResponse> mutableNasabahLiveData;
    private TelkomRepository telkomRepository;

    public void init() {
        if (mutableNasabahLiveData != null) {
            return;
        }
        telkomRepository = TelkomRepository.getInstance();
    }

    //Tagihan
    public LiveData<TelkomResponse> getTagihan(CekTagihan tagihan) {
        if (mutableNasabahLiveData == null) {
            telkomRepository = TelkomRepository.getInstance();
        }
        mutableNasabahLiveData = telkomRepository.getTagihan(tagihan);
        return mutableNasabahLiveData;
    }

}

