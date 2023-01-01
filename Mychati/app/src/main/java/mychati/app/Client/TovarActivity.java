package mychati.app.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import mychati.app.Client.ClientTovarsAdaoter.TovarsAdapter;
import mychati.app.Client.ClientTovarsHolder.ClientTivarHolder;
import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;
import mychati.app.Shops.ShopName;

public class TovarActivity extends AppCompatActivity {
private DatabaseReference tovars;
private RecyclerView recyclerView;
private String uid,srav,otpravprice;
private int test;
private int tovarprice;
private int priceint;


    private ValueEventListener mRefUserListener;
    private HashMap <DatabaseReference,ValueEventListener> value=new HashMap<>() ;

private FirebaseAuth mAuth;
private DatabaseReference tovarDocart;
private DatabaseReference otzyv;
private AppCompatButton predl;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tovar);
        layoutManager=new GridLayoutManager(this,2);
        Log.d("Uid",getIntent().getExtras().get("ShopUid").toString());
        tovarDocart=FirebaseDatabase.getInstance().getReference().child("DoCart");
otzyv=FirebaseDatabase.getInstance().getReference().child("otzyv");
        recyclerView=(RecyclerView) findViewById(R.id.tovarrec);
        tovars= FirebaseDatabase.getInstance().getReference().child("Tovars");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

mAuth=FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<TovarsAdapter> options=new FirebaseRecyclerOptions.Builder<TovarsAdapter>()
                .setQuery(tovars.orderByChild("ShopUid").equalTo(getIntent().getExtras().get("ShopUid").toString()),TovarsAdapter.class).build();
        FirebaseRecyclerAdapter<TovarsAdapter, ClientTivarHolder> adapter=new FirebaseRecyclerAdapter<TovarsAdapter, ClientTivarHolder>(options) {
            @Override
            protected void onBindViewHolder( @androidx.annotation.NonNull ClientTivarHolder holder, int position,  @androidx.annotation.NonNull TovarsAdapter model) {
        holder.texttovarprice.setText(model.getTovarPrice()+"₽");
        holder.texttovarname.setText(model.getTovarName());
holder.texttovarprice.setHint(model.getProductTime());
holder.texttovarname.setHint(model.getShopUid());
holder.tovarpus.setHint(model.getTovarImage());
holder.tovarcart.setHint(model.getMagLogo());
holder.texttovarstatyesnalkli.setHint(model.getMagName());

holder.tovarminus.setHint(model.getTovarPrice());
holder.cardTovar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        TovarBottomOpisFragment tovarBottomOpisFragment=new TovarBottomOpisFragment();
        Bundle data=new Bundle();
data.putString("Uid",holder.texttovarname.getHint().toString());
data.putString("ShopUid",holder.texttovarname.getHint().toString());
        data.putString("ProdId",holder.texttovarprice.getHint().toString());
        tovarBottomOpisFragment.setArguments(data);
        tovarBottomOpisFragment.show(getSupportFragmentManager(),tovarBottomOpisFragment.getTag());


    }
});





                String sa="1";
                int nas1=Integer.parseInt(model.getTovarStatus());
                int nas2=Integer.parseInt(sa);


if (nas2==nas1){
    holder.texttovarstatyesnalkli.setVisibility(View.VISIBLE);
    holder.texttovarprice.setEnabled(true);


}else{
    holder.texttovarstatnonalkli.setVisibility(View.VISIBLE);


    holder.texttovarprice.setEnabled(false);
}





















holder.texttovarprice.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        holder.tovarcart.setVisibility(View.VISIBLE);
        holder.tovarminus.setVisibility(View.VISIBLE);
        holder.tovarpus.setVisibility(View.VISIBLE)
        ;
        holder.texttovarprice.setVisibility(View.INVISIBLE);

        HashMap<String,Object>docart=new HashMap<>();















docart.put("MagName",holder.texttovarstatyesnalkli.getHint().toString());
docart.put("MagLogo",holder.tovarcart.getHint().toString());

docart.put("ShopUid",holder.texttovarname.getHint().toString());

docart.put("MyUID",mAuth.getCurrentUser().getUid());

        tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                  HashMap<String,Object>slatslat=new HashMap<>();
                  slatslat.put("tovarname",holder.texttovarname.getText().toString());
                  slatslat.put("MyUID",mAuth.getCurrentUser().getUid());
                    slatslat.put("tovarcartShopuid",holder.texttovarname.getHint().toString());
                    slatslat.put("Fixprice",holder.tovarminus.getHint().toString());
               slatslat.put("tovarImage",holder.tovarpus.getHint().toString());
                  slatslat.put("Price",holder.tovarminus.getHint().toString());
                 slatslat.put("ProductId",holder.texttovarprice.getHint().toString());
                  slatslat.put("TovarValue","1");

                    tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child((holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid())).updateChildren(slatslat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(TovarActivity.this, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                            }else{
                                String message = task.getException().toString();
                                Toast.makeText(TovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {

                    String message = task.getException().toString();
                    Toast.makeText(TovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });









    }
});






holder.tovarpus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      String  CurrentValue=holder.tovarcart.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value++;
        holder.tovarcart.setText(String.valueOf(value));
test=Integer.valueOf(holder.tovarcart.getText().toString());
priceint=Integer.valueOf(holder.tovarminus.getHint().toString());

        String dollar=String.valueOf(test*priceint);
        HashMap<String,Object>docart=new HashMap<>();

        docart.put("TovarValue",holder.tovarcart.getText().toString());
docart.put("Price",dollar);

        tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TovarActivity.this, "Изменено", Toast.LENGTH_SHORT).show();

                } else {

                    String message = task.getException().toString();
                    Toast.makeText(TovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
});
holder.tovarminus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      String  CurrentValue=holder.tovarcart.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value--;
        holder.tovarcart.setText(String.valueOf(value));

        test=Integer.valueOf(holder.tovarcart.getText().toString());
        priceint=Integer.valueOf(holder.tovarminus.getHint().toString());
String dollar=String.valueOf(test*priceint);

        HashMap<String,Object>docart=new HashMap<>();

        docart.put("TovarValue",holder.tovarcart.getText().toString());
        docart.put("Price",dollar);
        tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TovarActivity.this, "Изменено", Toast.LENGTH_SHORT).show();

                } else {

                    String message = task.getException().toString();
                    Toast.makeText(TovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
});


mRefUserListener=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            holder.texttovarprice.setVisibility(View.INVISIBLE);
            holder.tovarcart.setVisibility(View.VISIBLE);
            holder.tovarminus.setVisibility(View.VISIBLE);
            holder.tovarpus.setVisibility(View.VISIBLE);





























            holder.tovarcart.setText(snapshot.child("TovarValue").getValue().toString());

            Log.d("ki",snapshot.child("TovarValue").getValue().toString());









            // int price=Integer.parseInt(snapshot.child("Price").getValue().toString());





            String s="0";
            int n1=Integer.parseInt(snapshot.child("TovarValue").getValue().toString());
            int n2=Integer.parseInt(s);

















            if (n1==n2){
                tovarDocart.child(mAuth.getCurrentUser().getUid()+holder.texttovarname.getHint().toString()).child(model.getProductTime()+mAuth.getCurrentUser().getUid()).removeValue();


                holder.tovarminus.setVisibility(View.INVISIBLE);
                holder.tovarpus.setVisibility(View.INVISIBLE);
                holder.tovarcart.setVisibility(View.INVISIBLE);
                holder.texttovarprice.setVisibility(View.VISIBLE);

            }else{
                holder.tovarminus.setVisibility(View.VISIBLE);
                holder.tovarpus.setVisibility(View.VISIBLE);
                holder.tovarcart.setVisibility(View.VISIBLE);
                holder.texttovarprice.setVisibility(View.INVISIBLE);

            }









        }else{
            holder.texttovarprice.setVisibility(View.VISIBLE);
            holder.tovarcart.setVisibility(View.INVISIBLE);
            holder.tovarminus.setVisibility(View.INVISIBLE);
            holder.tovarpus.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};

                tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(model.getProductTime()+mAuth.getCurrentUser().getUid()).addValueEventListener(mRefUserListener);

value.put(tovarDocart,mRefUserListener);










                Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(12).oval(false).build();


                Picasso.get().load(model.getTovarImage()).transform(transformation).into(holder.imageTovar);





            }

            @Override
            public ClientTivarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tovars,parent,false);
              ClientTivarHolder holder=new ClientTivarHolder(view);


                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    @Override
    public void onPause() {
        super.onPause();
        for (Map.Entry<DatabaseReference,ValueEventListener> entry:value.entrySet()){
            entry.getKey().removeEventListener(entry.getValue());

        }
    }

}