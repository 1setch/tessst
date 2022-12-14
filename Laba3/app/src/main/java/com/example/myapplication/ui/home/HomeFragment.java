package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // My code begin:

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://raw.githubusercontent.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();

                UserService service = retrofit.create(UserService.class);

                Call<String> fetchUsersRequest = service.fetchUsers();

                fetchUsersRequest.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Gson gson = new Gson();
                        City[] cytis = gson.fromJson(response.body(), City[].class);
                        for (int i = 0; i < cytis.length; i++) {
                            System.out.print("Название: ");
                            System.out.print(cytis[i].Name);
                            System.out.print(", Страна: ");
                            System.out.print(cytis[i].Country);
                            System.out.print(", Язык: ");
                            System.out.print(cytis[i].language);
                            System.out.print(", Популяция: ");
                            System.out.print(cytis[i].Population);
                            System.out.print(", Площадь: ");
                            System.out.println(cytis[i].square);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(123123123);
                    }
                });
            }
        });

        thread.start();

        // My code end

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface UserService {
        @GET("Lpirskaya/JsonLab/master/City2022.json")
        Call<String> fetchUsers();
    }

    public class City {
        @SerializedName("Population")
        @Expose
        private long Population = 0;
        @SerializedName("Country")
        @Expose
        private String Country = "";
        @SerializedName("Name")
        @Expose
        private String Name = "";
        @SerializedName("language")
        @Expose
        private String language = "";
        @SerializedName("square")
        @Expose
        private int square = 0;
    }
}