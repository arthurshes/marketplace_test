package mychati.app.Client.ClientShopsHolders;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mychati.app.Client.ClientShopsModel.ShopAdapter;
import mychati.app.Client.ProfileAdapter.ProfileAdapter;
import mychati.app.Client.ProfileHolders.ProfileHolder;
import mychati.app.Holders.ChildHolders.GlavChildHolder;
import mychati.app.Holders.GlavParentHolder;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private DatabaseReference proverka;
    private DatabaseReference otzyv;
    private  DatabaseReference prover;
    private ValueEventListener list1;
    private HashMap<DatabaseReference,ValueEventListener> hashMapHash=new HashMap<>();
    private TextView textesti;
    private Double itog;
    private Double otztvValues;
    private DatabaseReference otzest;
  private int testint;
    private RecyclerView rechome;
    private String saveCurrentDate, saveCurrentTime, ProductRandomKey;
    private DatabaseReference myname;
    private FirebaseAuth mAuth;
    private RecyclerView rectwohome;
    FirebaseRecyclerAdapter<ShopAdapter, ClientShopHolder>magAdapter;
    private FirebaseRecyclerAdapter<ShopAdapter,GlavChildHolder>glavAdapter;
    private DatabaseReference shops;
    private RecyclerView recAptek;
    private DatabaseReference shopipht;
    private ValueEventListener refTwoRaw;
    private HashMap<DatabaseReference, ValueEventListener> valche = new HashMap<>();
    private Dialog dialog;
    private ValueEventListener mRefUserListener;
    private HashMap<DatabaseReference, ValueEventListener> value = new HashMap<>();
    RecyclerView.LayoutManager layoutManagerThree;
    RecyclerView.LayoutManager layoutManagerHoriz;

    View Homefrag;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
     Homefrag= inflater.inflate(R.layout.fragment_home, container, false);
        otzyv = FirebaseDatabase.getInstance().getReference().child("otzyv");
        myname = FirebaseDatabase.getInstance().getReference().child("client");
        proverka = FirebaseDatabase.getInstance().getReference().child("oformzakaz");
        dialog = new Dialog(getContext());
shops=FirebaseDatabase.getInstance().getReference().child("shops");
layoutManagerThree=new LinearLayoutManager(this.getContext());
otzest=FirebaseDatabase.getInstance().getReference().child("otzyv");
        recAptek=Homefrag.findViewById(R.id.recAptek);
        recAptek.setLayoutManager(layoutManagerThree);
prover=FirebaseDatabase.getInstance().getReference().child("otzyv");
        recAptek.setLayoutManager(layoutManagerThree);
        textesti=Homefrag.findViewById(R.id.textesti);

        mAuth = FirebaseAuth.getInstance();
        shopipht = FirebaseDatabase.getInstance().getReference().child("DoCart");
        rectwohome=Homefrag.findViewById(R.id.rectwohome);
        layoutManagerHoriz=new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        rectwohome.setHasFixedSize(true);
        rectwohome.setLayoutManager(layoutManagerHoriz);

        mRefUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ShopAdapter shopAdapter = ds.getValue(ShopAdapter.class);
                        assert shopAdapter != null;


                        Log.d("jipe", shopAdapter.getProductId());

                        dialog.setContentView(R.layout.layot_otzyvov);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        AppCompatButton appCompatButton = dialog.findViewById(R.id.button_otzyv_otprav);
                        RatingBar ratingBar = dialog.findViewById(R.id.ratingBarotzyvdia);
                        TextView texrstyle = dialog.findViewById(R.id.textstatusOtzyv);
                        TextView zavershitzakaz = dialog.findViewById(R.id.zavershitzakaz);
                        EditText opisanieotzyv = dialog.findViewById(R.id.opisanieotzyv);
                        TextView textViewzaverh = dialog.findViewById(R.id.textViewzaverh);

                        textViewzaverh.setHint(shopAdapter.getShopId());
                        zavershitzakaz.setHint(shopAdapter.getProductId());


                        refTwoRaw = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    textViewzaverh.setText(shopAdapter.getZaverName() + " завершил заказ. " + snapshot.child("clientName").getValue().toString() + ",оцените нас");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };

                        myname.child(mAuth.getCurrentUser().getUid()).addValueEventListener(refTwoRaw);

                        valche.put(myname, refTwoRaw);
                        zavershitzakaz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                shopipht.child(mAuth.getCurrentUser().getUid()).removeValue();

                                proverka.child(mAuth.getCurrentUser().getUid() + shopAdapter.getShopUid()).removeValue();
                            }
                        });

                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                if (v < 1) {
                                    texrstyle.setText("Отвратительно");
                                    texrstyle.setHint(String.valueOf(v));
                                } else if (v > 0 && v < 2) {
                                    texrstyle.setText("Плохо");
                                    texrstyle.setHint(String.valueOf(v));
                                } else if (v > 1 && v < 3) {
                                    texrstyle.setText("Cредне");
                                    texrstyle.setHint(String.valueOf(v));
                                } else if (v > 2 && v < 4) {
                                    texrstyle.setText("Нормально");
                                    texrstyle.setHint(String.valueOf(v));

                                } else if (v > 3 && v < 5) {
                                    texrstyle.setText("Хорошо");
                                    texrstyle.setHint(String.valueOf(v));
                                } else if (v > 4 && v < 6) {
                                    texrstyle.setText("Отлично");
                                    texrstyle.setHint(String.valueOf(v));
                                }

                            }
                        });
                        dialog.show();

                        appCompatButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (TextUtils.isEmpty(opisanieotzyv.getText().toString())) {
                                    Toast.makeText(getContext(), "Опишите ваше мнение ", Toast.LENGTH_SHORT).show();
                                } else {

                                    Calendar calendar = Calendar.getInstance();

                                    SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
                                    saveCurrentDate = currentDate.format(calendar.getTime());

                                    SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
                                    saveCurrentTime = currentTime.format(calendar.getTime());

                                    ProductRandomKey = saveCurrentDate + saveCurrentTime;

                                    HashMap<String, Object> men = new HashMap<>();
                                    men.put("Value", texrstyle.getHint().toString());
                                    men.put("ShopUid", shopAdapter.getShopUid());
                                    men.put("text", opisanieotzyv.getText().toString());
                                    otzyv.child(shopAdapter.getShopId()).child(mAuth.getCurrentUser().getUid() + ProductRandomKey).updateChildren(men).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Отзыв отправлен,спасибо", Toast.LENGTH_SHORT).show();

                                                proverka.child(mAuth.getCurrentUser().getUid() + shopAdapter.getProductId() + shopAdapter.getShopId()).removeValue();
                                                dialog.dismiss();
                                            } else {

                                                String message = task.getException().toString();
                                                Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        proverka.orderByChild("Zakazstatus").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(mRefUserListener);

        value.put(proverka, mRefUserListener);



























































        return Homefrag;
    }

    @Override
    public void onResume() {
        super.onResume();









































        FirebaseRecyclerOptions<ShopAdapter> options=new FirebaseRecyclerOptions.Builder<ShopAdapter>()
                .setQuery(shops,ShopAdapter.class).build();
        glavAdapter=new FirebaseRecyclerAdapter<ShopAdapter, GlavChildHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull GlavChildHolder holder, int position, @androidx.annotation.NonNull ShopAdapter model) {

                textesti.setVisibility(View.VISIBLE);


                holder.glavmagname.setText(model.getMagName());

                Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(15).oval(false).build();
                Picasso.get().load(model.getMagLogo()).transform(transformation).into(holder.imageGlavMag);





            }

            @Override
            public GlavChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_glavmag,parent,false);
                GlavChildHolder holder=new GlavChildHolder (view);


                return holder;
            }
        };
        rectwohome.setAdapter(glavAdapter);







        FirebaseRecyclerOptions<ShopAdapter> Options =new FirebaseRecyclerOptions.Builder<ShopAdapter>()
                .setQuery(shops,ShopAdapter.class).build();
        magAdapter=new FirebaseRecyclerAdapter<ShopAdapter, ClientShopHolder>(Options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull ClientShopHolder holder, int position, @androidx.annotation.NonNull ShopAdapter model) {

                Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(15).oval(false).build();
                Picasso.get().load(model.getMagLogo()).transform(transformation).into(holder.imageLogoApteka);


                holder.aotekaname.setText(model.getMagName());

                holder.aotekaname.setHint(model.getMagUid());

                Log.d("Окси",holder.aotekaname.getHint().toString());





                prover.child(holder.aotekaname.getHint().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        testint=Integer.valueOf(""+snapshot.getChildrenCount());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });




                list1=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            Double diablo=0.0;
                            for (DataSnapshot ds:snapshot.getChildren()){
                                ShopAdapter shopAdapter=ds.getValue(ShopAdapter.class);
                                assert shopAdapter !=null;
                                String string=shopAdapter.getValue();
                                otztvValues=Double.parseDouble(string);

                                diablo=diablo+otztvValues;
                                itog=diablo/testint;


                                if (itog>=0.5){
                                    holder.rateyellow1.setVisibility(View.VISIBLE);
                                    holder.rateyellow3.setVisibility(View.INVISIBLE);
                                    holder.rateyellow4.setVisibility(View.INVISIBLE);
                                    holder.rateyellow5.setVisibility(View.INVISIBLE);
                                }else if (itog>=1.5){
                                    holder.rateyellow1.setVisibility(View.VISIBLE);
                                    holder.rateyellow2.setVisibility(View.VISIBLE);
                                    holder.rateyellow3.setVisibility(View.INVISIBLE);
                                    holder.rateyellow4.setVisibility(View.INVISIBLE);
                                    holder.rateyellow5.setVisibility(View.INVISIBLE);
                                }else if (itog>=2.5){
                                    holder.rateyellow1.setVisibility(View.VISIBLE);
                                    holder.rateyellow2.setVisibility(View.VISIBLE);
                                    holder.rateyellow3.setVisibility(View.VISIBLE);
                                    holder.rateyellow4.setVisibility(View.INVISIBLE);
                                    holder.rateyellow5.setVisibility(View.INVISIBLE);
                                }else if (itog>=3.5){
                                    holder.rateyellow1.setVisibility(View.VISIBLE);
                                    holder.rateyellow2.setVisibility(View.VISIBLE);
                                    holder.rateyellow3.setVisibility(View.VISIBLE);
                                    holder.rateyellow4.setVisibility(View.VISIBLE);
                                    holder.rateyellow5.setVisibility(View.INVISIBLE);
                                }else if (itog>=4.5){
                                    holder.rateyellow1.setVisibility(View.VISIBLE);
                                    holder.rateyellow2.setVisibility(View.VISIBLE);
                                    holder.rateyellow3.setVisibility(View.VISIBLE);
                                    holder.rateyellow4.setVisibility(View.VISIBLE);
                                    holder.rateyellow5.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
















                otzest.child(holder.aotekaname.getHint().toString()).addValueEventListener(list1);

                hashMapHash.put(otzest,list1);




            }

            @Override
            public ClientShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apteka
                        ,parent,false);
                ClientShopHolder holder=new ClientShopHolder ( view);


                return holder;
            }
        };
        recAptek.setAdapter(magAdapter);


















        magAdapter.startListening();
        glavAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        for (Map.Entry<DatabaseReference, ValueEventListener> entry : value.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());


        }

        for (Map.Entry<DatabaseReference,ValueEventListener> entry:hashMapHash.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());

        }
        for (Map.Entry<DatabaseReference,ValueEventListener> entry:valche.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());

        }
        magAdapter.stopListening();
        glavAdapter.stopListening();

    }

    @Override
    public void onStop() {
        super.onStop();



        for (Map.Entry<DatabaseReference, ValueEventListener> entry : value.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());


        }

        for (Map.Entry<DatabaseReference,ValueEventListener> entry:hashMapHash.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());

        }
        for (Map.Entry<DatabaseReference,ValueEventListener> entry:valche.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());

        }
        magAdapter.stopListening();
        glavAdapter.stopListening();


    }
}