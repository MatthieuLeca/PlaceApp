package com.matt.mentor;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;


import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {
    TextView Name = null;
    TextView Address = null;
    TextView PhoneNumber = null;
    TextView WebsiteUri = null;
    TextView PlaceTypes = null;
    TextView Rating = null;
    Button searchBut = null;

    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initElmt();

        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                GooglePlayServicesUtil.getErrorDialog(status, this,
                        100).show();
            }
        }

        searchBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchAPI();
            }
        });


    }
    private void launchAPI(){
        if (status == ConnectionResult.SUCCESS) {
            int PLACE_PICKER_REQUEST = 199;
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        }
        if (requestCode == 199){

            //process Intent......
            Place place = PlacePicker.getPlace(data, this);
            String strName = String.format("Place: %s", place.getName());
            Name.setText(strName);
            String strAddress = String.format("Addresse: %s", place.getAddress());
            Address.setText(strAddress);
            String strPhoneNumber = String.format("Telephone: %s", place.getPhoneNumber());
            PhoneNumber.setText(strPhoneNumber);
            String strWebsiteUri = String.format("WebSite: %s", place.getWebsiteUri());
            WebsiteUri.setText(strWebsiteUri);
            String strPlaceTypes = String.format("Type: %s", place.getPlaceTypes());
            PlaceTypes.setText(strPlaceTypes);
            String strRating = String.format("Note: %s", place.getRating());
            Rating.setText(strRating);

        }
    }

    private void initElmt() {
        searchBut = (Button) findViewById(R.id.buttonSearch);
        Name = (TextView) findViewById(R.id.Name);
        Address = (TextView) findViewById(R.id.Address);
        PhoneNumber = (TextView) findViewById(R.id.PhoneNumber);
        WebsiteUri = (TextView) findViewById(R.id.WebsiteUri);
        PlaceTypes = (TextView) findViewById(R.id.PlaceTypes);
        Rating = (TextView) findViewById(R.id.Rating);
    }

}
/*
getName() – Nom du lieu.
getAddress() – Adresse du lieu dans un format lisible.
getID() – Identifiant texte pour le lieu. Lisez la suite pour en savoir plus sur les identifiants de lieu.
getPhoneNumber() – Numéro de téléphone du lieu.
getWebsiteUri() – URI du site Web du lieu, si connu. Il s'agit du site Web géré par le professionnel ou autre entité associée au lieu. Renvoie la valeur null si aucun site Web n'est connu.
getLatLng() – Position géographique du lieu, spécifié sous la forme de valeurs de latitude et longitude.
getViewport() – Fenêtre d'affichage renvoyée sous la forme d'un objet LatLngBounds, utile pour afficher le lieu sur une carte. Peut renvoyer la valeur null si la taille du lieu est inconnue.
getLocale() – Paramètre régional selon lequel le nom et l'adresse sont localisés.
getPlaceTypes() – Liste des types de lieu qui caractérisent ce lieu. Pour obtenir une liste des types de lieu disponibles, voir la documentation sur l'interface Place.
getPriceLevel() – Niveau de prix pour le lieu, renvoyé sous la forme d'un entier compris entre 0 (le moins cher) et 4 (le plus cher).
getRating() – Note globale du lieu, renvoyée sous la forme d'une valeur flottante comprise entre 1.0 et 5.0, basée sur l'ensemble des avis des utilisateurs.

*/
