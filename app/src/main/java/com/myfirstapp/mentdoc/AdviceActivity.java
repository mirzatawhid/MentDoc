package com.myfirstapp.mentdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.myfirstapp.mentdoc.AdviceQuotesService.ApiService;
import com.myfirstapp.mentdoc.AdviceQuotesService.QuoteImage;
import com.myfirstapp.mentdoc.AdviceQuotesService.ZenAPI;
import com.myfirstapp.mentdoc.AdviceQuotesService.ZenQuotesApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdviceActivity extends AppCompatActivity {

    ImageView imgQuotes;
    Button btnMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        imgQuotes = findViewById(R.id.img_quotes);
        btnMore = findViewById(R.id.btn_more);

        //getQuoteImage();



        loadQuoteImage();
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuoteImage();
            }
        });

    }

    private void loadQuoteImage(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://zenquotes.io/api/").addConverterFactory(GsonConverterFactory.create()).build();
        ZenAPI quoteService = retrofit.create(ZenAPI.class);

        Call<ResponseBody> call = quoteService.getImageData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Load the image using Picasso
                        Log.d("API Request", "URL: suiii" + response.body());
                        try {
                            // Convert the response body InputStream to a byte array
                            InputStream inputStream = response.body().byteStream();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                byteArrayOutputStream.write(buffer, 0, bytesRead);
                            }
                            byte[] imageBytes = byteArrayOutputStream.toByteArray();

                            // Load the byte array into Picasso
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                            // Load the Bitmap into Picasso and display it in your ImageView
                            imgQuotes.setImageBitmap(bitmap);


                        } catch (IOException e) {
                            // Handle the exception
                            e.printStackTrace();
                        }
                    } else {
                        // Handle the case when the response body is null
                        Log.e("Image Load Error", "Response body is null");
                    }
                } else {
                    // Handle unsuccessful response (e.g., non-200 HTTP status code)
                    Log.e("Image Load Error", "Response code: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getQuoteImage() {
        ZenQuotesApi api = ApiService.getInstance();

        Call<QuoteImage> call = api.getQuoteImage();
        call.enqueue(new Callback<QuoteImage>() {
            @Override
            public void onResponse(Call<QuoteImage> call, Response<QuoteImage> response) {
                if (response.isSuccessful()) {
                    QuoteImage quoteImage = response.body();
                    if (quoteImage != null) {
                        String imageUrl = quoteImage.getImageUrl();
                        loadQuoteImage(imageUrl);
                    }
                } else {
                    // Handle API error
                    Log.e("API Error", "Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<QuoteImage> call, Throwable t) {
                // Handle network or other errors
                Log.e("Network Error", "Error: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void loadQuoteImage(String imageUrl) {
        // Use Glide to load the image into the ImageView
        Glide.with(this)
                .load(imageUrl)
                .into(imgQuotes);
    }




    public void GoToHome(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}