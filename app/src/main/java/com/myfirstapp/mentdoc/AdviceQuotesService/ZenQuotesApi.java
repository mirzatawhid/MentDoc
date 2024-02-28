package com.myfirstapp.mentdoc.AdviceQuotesService;

import com.myfirstapp.mentdoc.AdviceQuotesService.QuoteImage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ZenQuotesApi {
    @GET("image")
    Call<QuoteImage> getQuoteImage();
}
