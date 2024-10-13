package com.example.consumoresapplication.service;

import com.example.consumoresapplication.entity.comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
     @GET("comments")
    public abstract Call<List<comment>> listaCommment();
}
