
package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.FavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public class NeighbourProfileActivity extends AppCompatActivity {

    private ImageView mImageProfilView;
    private FloatingActionButton mImageFavorisView;
    private CollapsingToolbarLayout mCollapsingToobar;
    private Toolbar mToobar;
    private TextView mTextNameProfilView;
    private TextView mTextAdressProfilView;
    private TextView mTextTelProfilView;
    private TextView mTextMailProfilView;
    private TextView mTextAProposView;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profil_view);
        ButterKnife.bind(this);

        mImageProfilView = findViewById(R.id.profilView);
        mImageFavorisView = findViewById(R.id.favorisView);
        mCollapsingToobar = findViewById(R.id.collapsing_toolbar);
        mToobar = findViewById(R.id.toolbar);
        mTextNameProfilView = findViewById(R.id.nameProfilView);
        mTextAdressProfilView = findViewById(R.id.adresseProfilView);
        mTextTelProfilView = findViewById(R.id.telProfilView);
        mTextMailProfilView = findViewById(R.id.mailProfilView);
        mTextAProposView = findViewById(R.id.textaPropos);

        //insertion des valeurs de chaque profil dans les champs

        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");

        mCollapsingToobar.setTitle(neighbour.getName());
        mTextNameProfilView.setText(neighbour.getName());
        mTextAdressProfilView.setText(neighbour.getAddress());
        mTextTelProfilView.setText(neighbour.getPhoneNumber());
        mTextAProposView.setText(neighbour.getAboutMe());


        //retour en arrière avec la toolbar
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToobar.setNavigationOnClickListener(view -> onBackPressed());

        //insertion de l'image
        String res_image = neighbour.getAvatarUrl();
        Glide.with(this)
                .load(res_image)
                .into(mImageProfilView);

        //mettre en favoris : retenir l'info
        adaptFavoriteButton(neighbour);

        //bouton favoris
        mImageFavorisView.setOnClickListener(v -> {

            //changement de couleur et toast selon si ajout ou retrait des favoris
            boolean isFavorite = isFavorite(neighbour);
            syncFavoriteButton(neighbour);

            EventBus.getDefault().post(new FavoriteNeighbourEvent(neighbour, !isFavorite));
        });

    }

    public static void navigate(FragmentActivity activity, Neighbour neighbour) {

        //pour aller chercher les valeurs contenues dans neighbours
        Intent profilIntent = new Intent(activity, NeighbourProfileActivity.class);
        profilIntent.putExtra("neighbour", neighbour);
        activity.startActivity(profilIntent);

    }

    //pour savoir si un voisin est favoris
    public boolean isFavorite(Neighbour neighbour) {
        boolean isFavorite = false;
        for (Neighbour neighbours : mApiService.getFavoriteNeighbours()) {
            if (neighbours.getId() == neighbour.getId()) {
                isFavorite = true;
            }
        }
        return isFavorite;
    }

    //condition changement de couleur du bouton ajouter aux favoris selon statut favoris du voisin

    public void syncFavoriteButton(Neighbour neighbour) {

        if (!isFavorite(neighbour)) {
            Toast.makeText(NeighbourProfileActivity.this, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
            mImageFavorisView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.yellow)));
            mImageFavorisView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.white)));
        } else {
            Toast.makeText(NeighbourProfileActivity.this, "Retiré des favoris", Toast.LENGTH_SHORT).show();
            mImageFavorisView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.white)));
            mImageFavorisView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.colorPrimary)));
        }
    }

    public void adaptFavoriteButton(Neighbour neighbour) {
        if (isFavorite(neighbour)) {
            mImageFavorisView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.yellow)));
            mImageFavorisView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.white)));
        } else {
            mImageFavorisView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.white)));
            mImageFavorisView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.colorPrimary)));
        }
    }
}

