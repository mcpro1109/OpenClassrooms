package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteNeighbourRecyclerViewAdapter.ViewHolder> {

    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private List<Neighbour> mNeighbours;
    private RecyclerViewClickListener listener;
    private ItemClickListener mClickListener;

    FavoriteNeighbourRecyclerViewAdapter(FavorisFragment favorisFragment, List<Neighbour> items, RecyclerViewClickListener listener) {
        mNeighbours = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteNeighbourRecyclerViewAdapter.ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        //ajout du click sur les élèments de la recyclerview pour accéder au profil
        holder.mDeleteButton.setOnClickListener(v -> listener.onDelete(v, neighbour));
        holder.itemView.setOnClickListener(v -> listener.onClick(v, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    //ajouter un neighbour aux favoris
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    void setClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.mClickListener = (ItemClickListener) itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<Neighbour> neighbours) {

        notifyDataSetChanged();
    }

    //ajout de l'interface pour définir la position pour le click du recyclerview et le delete

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
        void onDelete(View v, Neighbour neighbour);
    }
}
