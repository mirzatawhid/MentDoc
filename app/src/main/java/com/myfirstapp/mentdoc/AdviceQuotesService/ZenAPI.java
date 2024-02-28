package com.myfirstapp.mentdoc.AdviceQuotesService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZenAPI {

    @GET("image")
    Call<ResponseBody> getImageData();
}
