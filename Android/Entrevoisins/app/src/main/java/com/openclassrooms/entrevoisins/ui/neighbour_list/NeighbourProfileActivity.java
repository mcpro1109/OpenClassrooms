
package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

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

    public static final String Image= "Image";

    private NeighbourApiService mApiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profil_view);

        mImageProfilView = findViewById(R.id.profilView);
        mImageFavorisView = findViewById(R.id.favorisView);
        mCollapsingToobar=findViewById(R.id.collapsing_toolbar);
        mToobar=findViewById(R.id.toolbar);
        mTextNameProfilView = findViewById(R.id.nameProfilView);
        mTextAdressProfilView = findViewById(R.id.adresseProfilView);
        mTextTelProfilView = findViewById(R.id.telProfilView);
        mTextMailProfilView = findViewById(R.id.mailProfilView);
        mTextAProposView = findViewById(R.id.textaPropos);

        //insertion des valeurs de chaque profil dans les champs

        Intent profilIntent=getIntent();

        mCollapsingToobar.setTitle(getIntent().getStringExtra("name"));
        mTextNameProfilView.setText(getIntent().getStringExtra("name"));
        mTextAdressProfilView.setText(getIntent().getStringExtra("adress"));
        mTextTelProfilView.setText(getIntent().getStringExtra("phone"));
        mTextAProposView.setText(getIntent().getStringExtra("aboutme"));
        mTextMailProfilView.setText(getIntent().getStringExtra("mail"));

        //retour en arrière avec la toolbar
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToobar.setNavigationOnClickListener(view -> onBackPressed());

        //insertion de l'image
            String res_image=getIntent().getStringExtra("image");
            Glide.with(this)
                .load(res_image)
                .into(mImageProfilView);




        //mettre en favoris : retenir l'info
        mImageFavorisView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                boolean isFABOpen=false;
                if(!isFABOpen){
                    
                    Toast.makeText(NeighbourProfileActivity.this, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
                    mImageFavorisView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this,R.color.yellow)));
                    mImageFavorisView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this,R.color.white)));
                    isFABOpen=true;
                }
                /*if (isFABOpen){
                   
                    Toast.makeText(NeighbourProfileActivity.this, "Retiré des favoris", Toast.LENGTH_SHORT).show();
                    mImageFavorisView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.white)));
                    mImageFavorisView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NeighbourProfileActivity.this, R.color.colorPrimary)));
                    isFABOpen=false;
                }*/


                //favorisIntent.putExtra("favoris", NeighbourProfileActivity.class);

                //updateDetails();
            }
        });




    }

    public void updateDetails(){
        Intent favorisIntent= new Intent("favoris");
        Bundle bundle=new Bundle();

    }

    public static void navigate(FragmentActivity activity, Neighbour neighbour) {

        //pour aller chercher les valeurs contenues dans neighbours
        Intent profilIntent = new Intent(activity, NeighbourProfileActivity.class);
        profilIntent.putExtra("name", neighbour.getName());
        profilIntent.putExtra("adress", neighbour.getAddress());
        profilIntent.putExtra("phone", neighbour.getPhoneNumber());
        profilIntent.putExtra("aboutme", neighbour.getAboutMe());
        profilIntent.putExtra("mail", "xxxxx@email.com");
        profilIntent.putExtra("image",neighbour.getAvatarUrl());
        activity.startActivity(profilIntent);

    }


}

