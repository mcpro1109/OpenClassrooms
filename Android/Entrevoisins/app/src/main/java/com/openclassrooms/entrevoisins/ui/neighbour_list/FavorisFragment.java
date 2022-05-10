package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.FavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

public class FavorisFragment extends Fragment {


    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private RecyclerView mRecyclerView;
    private List<Neighbour> mNeighbours = mApiService.getFavoriteNeighbours();
    private FavoriteNeighbourRecyclerViewAdapter adapter;

    public static FavorisFragment newInstance() {
        FavorisFragment fFragment = new FavorisFragment();
        return fFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favoris_fragment, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        //pour ajouter l'adapter pour lier avec la recyclerview
        adapter = new FavoriteNeighbourRecyclerViewAdapter(this, mNeighbours, new FavoriteNeighbourRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                NeighbourProfileActivity.navigate(getActivity(), mNeighbours.get(position));
            }

            @Override
            public void onDelete(View v, Neighbour neighbour) {
                mApiService.deleteNeighbour(neighbour);
            }
        });

        mRecyclerView.setAdapter(adapter);
        return view;

    }

    //utilisation de eventbus pour envoyer les évènements ajout/suppression des favoris

    @Subscribe
    public void onFavorite(FavoriteNeighbourEvent event) {
        //Toast.makeText(requireContext(), "event", Toast.LENGTH_SHORT).show();

        mApiService.setFavoris(event.neighbour, event.isFavorite);
        Log.e("app", "count=" + mApiService.getFavoriteNeighbours().size());
        adapter.update(mApiService.getFavoriteNeighbours());
        Log.e("app2", "count=" + mApiService.getFavoriteNeighbours().size());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}