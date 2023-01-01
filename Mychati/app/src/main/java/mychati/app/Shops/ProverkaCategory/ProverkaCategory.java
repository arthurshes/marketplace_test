package mychati.app.Shops.ProverkaCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mychati.app.Client.HomeActivity;
import mychati.app.R;
import mychati.app.Shops.LoadActivity.AptekaShopCategory;
import mychati.app.Shops.LoadActivity.OdejdaShopCategory;

public class ProverkaCategory extends AppCompatActivity {
private DatabaseReference Shop;
private FirebaseAuth mAuth;
private String apt,odejda,mebel,electro,orto,toys,cake,Sravnenie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proverka_category);
        Shop= FirebaseDatabase.getInstance().getReference().child("shops");
        apt="Аптека";
                odejda="Одежда и обувь";
        mebel="Мебельный салон";
                electro="Электронника";
                mAuth=FirebaseAuth.getInstance();
        orto ="Ортопедия";
                toys="Игрушки";
        cake="Кондитерская";


        Shop.child(mAuth.getCurrentUser().getUid()).orderByChild("MagCategory").equalTo(apt).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                startActivity(new Intent(ProverkaCategory.this, AptekaShopCategory.class));
                ssd();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ssd() {
        Shop.child(mAuth.getCurrentUser().getUid()).orderByChild("MagCategory").equalTo(mebel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                startActivity(new Intent(ProverkaCategory.this, OdejdaShopCategory.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}