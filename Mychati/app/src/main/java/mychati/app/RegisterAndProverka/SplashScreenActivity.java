package mychati.app.RegisterAndProverka;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mychati.app.CategUserActivity;
import mychati.app.Client.ClientNameActivity;
import mychati.app.Client.HomeActivity;
import mychati.app.HelloActivity.HelloClientActivity;
import mychati.app.HelloActivity.HelloDayActivity;
import mychati.app.HelloActivity.HelloVecherActivity;
import mychati.app.R;
import mychati.app.Shops.ShopCategoryActivity;
import mychati.app.Shops.ShopHomeActivity;


public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference shop;


String name;
    private DatabaseReference clientse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();


shop= FirebaseDatabase.getInstance().getReference().child("shops");
clientse=FirebaseDatabase.getInstance().getReference().child("client");
        currentUser = mAuth.getCurrentUser();






        if (currentUser != null) {
            openShop();



        }else{
            startActivity(new Intent(SplashScreenActivity.this,RegisterPhoneActivity.class));
            finish();
        }
    }

    private void openShop() {

        shop.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {


                    name = snapshot.child("MagName").getValue().toString();

                    Date currentdate = new Date();
                    int hour = currentdate.getHours();

                    if (hour >= 6 && hour < 12) {
                        Intent nma = new Intent(SplashScreenActivity.this, HelloClientActivity.class);
                        nma.putExtra("name", name);
                        nma.putExtra("ident", "1");
                        startActivity(nma);
                        finish();
                    }
                    if (hour >= 12 && hour < 16) {
                        Intent ma = new Intent(SplashScreenActivity.this, HelloDayActivity.class);
                        ma.putExtra("name", name);
                        ma.putExtra("ident", "1");
                        startActivity(ma);
                        finish();
                    }
                    if (hour >= 16 && hour < 24) {


                        Intent nit = new Intent(SplashScreenActivity.this, HelloVecherActivity.class);
                        nit.putExtra("name", name);
                        nit.putExtra("ident", "1");
                        startActivity(nit);
finish();
                    }
                    if (hour >= 0 && hour < 6) {

                    }


                } else {
                    openCLient();
                    Toast.makeText(SplashScreenActivity.this, "Не курьер", Toast.LENGTH_SHORT).show();
                                                                                                           }
                                                                                                       }
                                                                                                       @Override
                                                                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                                                                       }
                                                                                                   });






    }

    private void openCLient() {



        clientse.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {













                    name=snapshot.child("clientName").getValue().toString();
                    Log.d("name",name);
                    Date currentdate=new Date();
                    int hour=currentdate.getHours();

                    if (hour>=6&&hour<12){
                        Intent nm=new Intent(SplashScreenActivity.this,HelloClientActivity.class);
                        nm.putExtra("name",name);
                        nm.putExtra("ident","2");
                        startActivity(nm);
                        finish();
                    }if (hour>=12&&hour<16){
                        Intent mak=new Intent(SplashScreenActivity.this, HelloDayActivity.class);
                        mak.putExtra("name",name);
                        mak.putExtra("ident","2");
                        startActivity(mak);
                        finish();
                    }if (hour>=16&&hour<24){
                        Intent ni=new Intent(SplashScreenActivity.this, HelloVecherActivity.class);
                        ni.putExtra("name",name);
                        ni.putExtra("ident","2");
                        startActivity(ni);
                        finish();
                    }if (hour>=0&&hour<6){

                    }

                } else {
                    startActivity(new Intent(SplashScreenActivity.this, CategUserActivity.class));
                    Toast.makeText(SplashScreenActivity.this, "Не курьер", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}