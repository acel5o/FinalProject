package co.g2academy.tugaslayout.networking;

import co.g2academy.tugaslayout.model.BayarTagihan;
import co.g2academy.tugaslayout.model.CekTagihan;
import co.g2academy.tugaslayout.model.MutasiReq;
import co.g2academy.tugaslayout.model.Nasabah;
import co.g2academy.tugaslayout.model.NasabahResponse;
import co.g2academy.tugaslayout.model.NasabahsResponse;
import co.g2academy.tugaslayout.model.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface BankAPI {
    @POST("register/")
    Call<NasabahResponse> postNasabah(@Body Nasabah body);

    @POST("bayar/")
    Call<NasabahResponse> bayarTagihan(@Body BayarTagihan body);

    @POST("tagihan/")
    Call<NasabahResponse> getTagihan(@Body CekTagihan body);

    @POST("login/")
    Call<NasabahResponse> postLogin(@Body Request body);

    @GET("user/{username}")
    Call<NasabahResponse> getSaldo(@Path("username") String path);

    @POST("logout/")
    Call<NasabahResponse> logOut(@Body Request Body);

    @POST("mutasi/")
    Call<NasabahsResponse> getMutasi(@Body MutasiReq mutasi);
}
