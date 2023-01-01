package mychati.app.Client.OpisanieBottomFrag;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.HashMap;

import mychati.app.Client.ClientTovarsAdaoter.Docart;
import mychati.app.Client.TovarActivity;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TovarBottomOpisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TovarBottomOpisFragment extends BottomSheetDialogFragment {
    private String Productid,UidShop,MagLogo,MagName;
    private TextView textopis,textprice,textname,txtPlus,txtMinus,textValue,texttovarno,texttovaryes;
    private FirebaseAuth mAuth;
    private FloatingActionButton floatingActionButtonbottt;
    private RoundedImageView imageTovar;
    private static int PHONE_NUMBER=0;
    private DatabaseReference docart;
String priem;

private AppCompatButton pluscarzin,plusdocart,deletedocart;
    private DatabaseReference tovar;
    View vb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TovarBottomOpisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TovarBottomOpisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TovarBottomOpisFragment newInstance(String param1, String param2) {
        TovarBottomOpisFragment fragment = new TovarBottomOpisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vb = inflater.inflate(R.layout.fragment_tovar_bottom_opis, container, false);



Productid=getArguments().getString("ProdId");
UidShop=getArguments().getString("Uid");
tovar= FirebaseDatabase.getInstance().getReference().child("Tovars");

       docart=FirebaseDatabase.getInstance().getReference().child("DoCart");
textopis=vb.findViewById(R.id.textopisalser);
        deletedocart=vb.findViewById(R.id.deletedocart);
txtPlus=vb.findViewById(R.id.tovarpluscart);
txtMinus=vb.findViewById(R.id.tovarminuscart);
textValue=vb.findViewById(R.id.tovarcartfrag);
texttovarno=vb.findViewById(R.id.texttovarstatnonalbotfrag);
texttovaryes=vb.findViewById(R.id.texttovarstatyesnalbotfrag);
textprice=vb.findViewById(R.id.textopisprice);
mAuth=FirebaseAuth.getInstance();
        plusdocart=vb.findViewById(R.id.plusdocart);
        floatingActionButtonbottt=vb.findViewById(R.id.floatingActionButtonbottt);
deletedocart.setEnabled(false);
textname=vb.findViewById(R.id.textnazopiser);
imageTovar=vb.findViewById(R.id.roundedImageViewOpiser);

tovar.child(Productid).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            textopis.setText(snapshot.child("TovarOpisanie").getValue().toString());
            textname.setText(snapshot.child("TovarName").getValue().toString());
            textname.setHint(snapshot.child("TovarPrice").getValue().toString());
            textprice.setText(snapshot.child("TovarPrice").getValue().toString()+"₽");
textopis.setHint(snapshot.child("ShopUid").getValue().toString());
textprice.setHint(snapshot.child("TovarImage").getValue().toString());
MagLogo=snapshot.child("MagLogo").getValue().toString();
MagName=snapshot.child("MagName").getValue().toString();
            String sa="1";
            int nak1=Integer.parseInt(snapshot.child("TovarStatus").getValue().toString());
            int nak2=Integer.parseInt(sa);


            if (nak1==nak2){
                texttovaryes.setVisibility(View.VISIBLE);
                plusdocart.setEnabled(true);
            }else{
                texttovarno.setVisibility(View.VISIBLE);
                plusdocart.setVisibility(View.INVISIBLE);
                plusdocart.setEnabled(false);
                txtMinus.setEnabled(false);
                deletedocart.setVisibility(View.VISIBLE);
                txtPlus.setEnabled(false);
            }







            Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(20).oval(false).build();


            Picasso.get().load(snapshot.child("TovarImage").getValue().toString()).transform(transformation).into(imageTovar);

        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
  textValue.setText(snapshot.child("TovarValue").getValue().toString());

  priem=snapshot.child("TovarValue").getValue().toString();

            String s="0";
            int n1=Integer.parseInt(snapshot.child("TovarValue").getValue().toString());
            int n2=Integer.parseInt(s);


            if (n1==n2){



                txtMinus.setEnabled(false);

plusdocart.setEnabled(false);

                plusdocart.setVisibility(View.INVISIBLE);
            }else{

                plusdocart.setVisibility(View.VISIBLE);
                txtMinus.setEnabled(true);
plusdocart.setEnabled(true);

            }
        }else {

            plusdocart.setVisibility(View.VISIBLE);

            plusdocart.setEnabled(true);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});















txtPlus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String  CurrentValue=textValue.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value++;
     textValue.setText(String.valueOf(value));










        HashMap<String,Object> docarty=new HashMap<>();

        docarty.put("TovarValue",textValue.getText().toString());

       docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).updateChildren(docarty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                } else {

                }
            }
        });
    }
});


txtMinus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String  CurrentValue=textValue.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value--;
        textValue.setText(String.valueOf(value));




















        HashMap<String,Object> docarty=new HashMap<>();

        docarty.put("TovarValue",textValue.getText().toString());

        docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).updateChildren(docarty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                } else {

                }
            }
        });
    }
});



Log.d("Jp",textValue.getText().toString());
Log.d("Jp",priem+"priemstring");









plusdocart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {





        HashMap<String,Object>newtovar=new HashMap<>();
    newtovar.put("MagName",MagName);
        newtovar.put("MagLogo",MagLogo);

       newtovar.put("ShopUid",UidShop);

        newtovar.put("MyUID",mAuth.getCurrentUser().getUid());

     docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).updateChildren(newtovar).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
HashMap<String,Object>twos=new HashMap<>();
                   twos.put("tovarname",textname.getText().toString());
                    twos.put("MyUID",mAuth.getCurrentUser().getUid());
                    twos.put("tovarcartShopuid",UidShop);
                    twos.put("tovarImage",textprice.getHint().toString());
                    twos.put("Price", textname.getHint().toString());
                    twos.put("ProductId",Productid);
                   docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).updateChildren(twos).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                       }
                   });

                } else {


                }
            }
        });








    }
});


return vb;
    }
}