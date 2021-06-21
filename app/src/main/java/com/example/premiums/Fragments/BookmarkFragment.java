package com.example.premiums.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.premiums.Adapters.UserCard_Adapter;
import com.example.premiums.Models.Information_Users_Model;
import com.example.premiums.Models.UserCard_Model;
import com.example.premiums.R;
import com.example.premiums.RoomDatabase.AsyncTask.PersonalAsync.GetPersonal_Async;
import com.example.premiums.RoomDatabase.AsyncTask.PersonalAsync.UpdatePersonal_Async;
import com.example.premiums.RoomDatabase.RoomFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static androidx.navigation.Navigation.findNavController;

public class BookmarkFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private UserCard_Adapter userCard_adapter;

    private ArrayList<Information_Users_Model> model = new ArrayList<>();
    private ArrayList<UserCard_Model> card_models = new ArrayList<>();

    private androidx.appcompat.widget.SearchView search;
    private LottieAnimationView lottie;
    private TextView emptyTv;
    View view;

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        recyclerView = view.findViewById(R.id.bookRecycler);
        lottie = view.findViewById(R.id.bookLottie);
        emptyTv = view.findViewById(R.id.book_empty_tv);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showOrHideNoteImage();
        GetData();
        RecyclerViw_Listener();

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_view, menu);
        MenuItem item = menu.findItem(R.id.search);
        search = (androidx.appcompat.widget.SearchView) item.getActionView();
        search.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        userCard_adapter.getFilter().filter(newText);

        return false;
    }

    private void showOrHideNoteImage() {

        if (card_models.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            lottie.setVisibility(View.VISIBLE);
            emptyTv.setVisibility(View.VISIBLE);

        } else {
            lottie.setVisibility(View.GONE);
            emptyTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }

    }

    private void GetData() {
        model.clear();
        card_models.clear();

        try {
            model.addAll(new GetPersonal_Async(RoomFactory.getPersonalDatabase(requireContext()).getPersonalDAO()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < model.size(); i++) {

            if (model.get(i).isBook()) {
                card_models.add(new UserCard_Model(
                        model.get(i).getName(),
                        model.get(i).getPhone(),
                        model.get(i).getImage(),
                        model.get(i).isBook()));
                Toast.makeText(requireContext(), "book :" + model.get(i).isBook(), Toast.LENGTH_SHORT).show();
            }
        }
        showOrHideNoteImage();
    }


    //Match RecyclerView With The Adapter
    private void RecyclerViw_Listener() {
        userCard_adapter = new UserCard_Adapter(card_models, requireContext(), new UserCard_Adapter.OnUserCardClick() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }, new UserCard_Adapter.OnUserCardLongClick() {
            @Override
            public void onItemLongClick(View view, int position) {
            }
        }, new UserCard_Adapter.OnPhoneIconClick() {
            @Override
            public void onPhoneIconClick(View view, int position) {
                UserCard_Model userCard_model = card_models.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", userCard_model.getUserPhone(), null));
                startActivity(intent);
            }
        }, new UserCard_Adapter.OnBookmarkIconClick() {
            @Override
            public void OnBookmarkIconClick(ImageView imageView, int position) {

                Information_Users_Model information_users_model = (Information_Users_Model) model.get(position);

                if (card_models.get(position).isBook()) {
                    Toast.makeText(requireContext(), "this is true & normal book", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(R.drawable.ic_normal_bookmark);

                    model.get(position).setBook(false);
                    new UpdatePersonal_Async(RoomFactory.getPersonalDatabase(requireContext()).getPersonalDAO()).execute(information_users_model);
                    card_models.clear();
                    GetData();
                } else {
                    Toast.makeText(requireContext(), "this is false & right book", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(R.drawable.ic_right_bookmark);
                    model.get(position).setBook(true);
                    new UpdatePersonal_Async(RoomFactory.getPersonalDatabase(requireContext()).getPersonalDAO()).execute(information_users_model);
                    card_models.clear();
                    GetData();
                }
            }
        }, new UserCard_Adapter.OnUserDetailsClick() {
            @Override
            public void onDetailsClick(View view, int position) {
                Information_Users_Model information_users_model = model.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", information_users_model);

                findNavController(view).navigate(R.id.detailsFragment, bundle);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(userCard_adapter);
    }


}