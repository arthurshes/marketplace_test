package mychati.app.Client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import mychati.app.Client.ClientShopsHolders.HomeFragment;
import mychati.app.R;
import mychati.app.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Fragments(new HomeFragment());

binding.bottomHome.setOnItemSelectedListener(item -> {
switch (item.getItemId()){
    case R.id.home:
Fragments(new HomeFragment());
        break;
    case R.id.cart:
        Fragments(new CartFragment());
        break;
    case R.id.chats:
        Fragments(new ChatFragment());
        break;
    case R.id.profil:
        Fragments(new ProfileFragment());
        break;

}
return true;
});

    }
    private void Fragments(Fragment fragment){


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


}