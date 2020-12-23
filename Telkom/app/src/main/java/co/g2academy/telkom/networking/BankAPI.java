package co.g2academy.telkom.networking;

import co.g2academy.telkom.model.CekTagihan;
import co.g2academy.telkom.model.TelkomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface BankAPI {
    @POST("tagihan/")
    Call<TelkomResponse> getTagihan(@Body CekTagihan body);
}
