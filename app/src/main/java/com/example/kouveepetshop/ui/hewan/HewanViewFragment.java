package com.example.kouveepetshop.ui.hewan;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.SplashScreen;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiHewan;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.recycle_adapter.HewanRecycleAdapter;
import com.example.kouveepetshop.result.hewan.ResultHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewanViewFragment extends Fragment {

    private List<HewanModel> hewanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HewanRecycleAdapter hewanRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    View myView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_hewan_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewHewan);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllHewan();

        return myView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.SearchTxt);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hewanRecycleAdapter.getFilter().filter(newText);
                return true;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LogOut:
                showDialog();
                return true;
            default:
                break;
        }

        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    public void showAllHewan(){
        ApiHewan apiHewan = ApiClient.getClient().create(ApiHewan.class);
        Call<ResultHewan> hewanCall = apiHewan.getAllHewan();

        hewanCall.enqueue(new Callback<ResultHewan>() {
            @Override
            public void onResponse(Call<ResultHewan> call, Response<ResultHewan> response) {
                if(response.isSuccessful()){
                    hewanList = response.body().getListHewan();
                    hewanRecycleAdapter = new HewanRecycleAdapter(getActivity(), hewanList);
                    recyclerView.setAdapter(hewanRecycleAdapter);
                    hewanRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Hewan is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultHewan> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doLogout(){
        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        SP.spEditor.clear();
        SP.saveSPBoolean(UserSharedPreferences.SP_ISLOGIN, false);
        SP.spEditor.apply();
        Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        getActivity().finish();
        startActivity(intent);
    }

    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure want to logout ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doLogout();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}