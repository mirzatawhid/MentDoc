package com.myfirstapp.mentdoc.AdviceQuotesService;
import com.google.gson.annotations.SerializedName;

public class QuoteImage {
    @SerializedName("success")
    private boolean success;

    @SerializedName("url")
    private String imageUrl;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
