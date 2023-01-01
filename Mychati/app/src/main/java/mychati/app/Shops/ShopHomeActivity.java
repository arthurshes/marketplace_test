package mychati.app.Shops;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import mychati.app.Client.CartFragment;
import mychati.app.Client.ChatFragment;
import mychati.app.Client.ClientShopsHolders.HomeFragment;
import mychati.app.Client.HomeActivity;
import mychati.app.Client.ProfileFragment;
import mychati.app.Client.ZakazActivFrag;
import mychati.app.R;
import mychati.app.Shops.ProverkaCategory.ProverkaCategory;
import mychati.app.databinding.ActivityHomeBinding;
import mychati.app.databinding.ActivityShopHomeBinding;

public class ShopHomeActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private DatabaseReference shopRef;

    ActivityShopHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        binding=ActivityShopHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        shopRef= FirebaseDatabase.getInstance().getReference().child("shops");













        binding.bottomHomeShop.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.shop_home:
                    Fragments(new HomeFragment());
                    break;
                case R.id.chat_shop:
                  startActivity(new Intent(ShopHomeActivity.this, ProverkaCategory.class));
                    break;
                case R.id.zakaz_shop:
                    Fragments(new ZakazShopopFragment());
                    break;
                case R.id.shop_profile:
                 startActivity(new Intent(ShopHomeActivity.this,ShopProfileActivity.class));
                    break;

            }
            return true;
        });
    }
    private void Fragments(Fragment fragment){


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_shop,fragment);
        fragmentTransaction.commit();
    }
}