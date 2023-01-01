package mychati.app.Shops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import mychati.app.R;

public class NewTovarRwoTwo extends AppCompatActivity {
    private RoundedImageView roundedImageViewTovar;
    private AppCompatButton savebutton;
    private DatabaseReference Shop;
    private TextView odej_text_careg;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private static final int GALLERYPICK = 1;
    private Uri ImageUriTwo;
    private EditText editprice,editopisanie,editnametovar;
    private String DownloadImageUrlTwo,MyPhone,saveCurrentDate,saveCurrentTime,ProductRandomKey,MyRanKey,MyName,MyLogo;
    private StorageReference imagetovar;
    private DatabaseReference tovars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tovar_rwo_two);
        odej_text_careg=(TextView) findViewById(R.id.odej_text_careg);
        imagetovar= FirebaseStorage.getInstance().getReference().child("TovarImage");
        Shop= FirebaseDatabase.getInstance().getReference().child("shops");
        editnametovar=(EditText)findViewById(R.id.editnametovar);
        editprice=(EditText)findViewById(R.id.editprice);
        editopisanie=(EditText)findViewById(R.id.editopisannie);
        progressDialog = new ProgressDialog(this);
        tovars= FirebaseDatabase.getInstance().getReference().child("Tovars");
        savebutton=(AppCompatButton)findViewById(R.id.buttonsavetovar);
        roundedImageViewTovar=(RoundedImageView)findViewById(R.id.roundedImageViewTovar);
        odej_text_careg.setText(getIntent().getExtras().get("category").toString());
        mAuth=FirebaseAuth.getInstance();


Shop.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            MyName=snapshot.child("MagName").getValue().toString();
            MyLogo=snapshot.child("MagLogo").getValue().toString();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




        if (mAuth.getCurrentUser().getPhoneNumber()==null){
            Shop.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                        MyPhone=snapshot.child("MagNumber").getValue().toString();

                        Log.d("Mavro",MyPhone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }




        roundedImageViewTovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpneGallery();
            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadtovar();
            }
        });
    }
    private void loadtovar() {

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HHmmss");
        saveCurrentTime=currentTime.format(calendar.getTime());

        ProductRandomKey=saveCurrentDate+saveCurrentTime;
        MyRanKey=mAuth.getCurrentUser().getUid()+ProductRandomKey;
        progressDialog.setTitle("загрузка данных....");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        StorageReference filePath = imagetovar.child(ImageUriTwo.getLastPathSegment() + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUriTwo);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(NewTovarRwoTwo.this, "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( NewTovarRwoTwo.this, "Изображение загружено", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadImageUrlTwo = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadImageUrlTwo = task.getResult().toString();


                            SaveTovarInfo();
                        }


                    }
                });
            }
        });



    }

    private void SaveTovarInfo() {
        if (TextUtils.isEmpty(editnametovar.getText().toString())){
            Toast.makeText(this, "Введите название товара", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editopisanie.getText().toString())){
            Toast.makeText(this, "Введите описание товара", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(editprice.getText().toString())){
            Toast.makeText(this, "Введите цену товара", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object> tovarMap=new HashMap<>();
            tovarMap.put("TovarName",editnametovar.getText().toString());
            tovarMap.put("TovarOpisanie",editopisanie.getText().toString());
            tovarMap.put("MagName",MyName);
            tovarMap.put("MagLogo",MyLogo);
            tovarMap.put("TovarPrice",editprice.getText().toString());
            tovarMap.put("TovarImage",DownloadImageUrlTwo);
            tovarMap.put("ShopUid",mAuth.getCurrentUser().getUid());
            tovarMap.put("ShopPhone",mAuth.getCurrentUser().getPhoneNumber());
            tovarMap.put("ShopPhoneReg",MyPhone);
            tovarMap.put("TovarStatus","1");
            tovarMap.put("TovarCateg",odej_text_careg.getText().toString());
            tovarMap.put("ProductTime",MyRanKey);
            tovars.child(MyRanKey).updateChildren(tovarMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(NewTovarRwoTwo.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewTovarRwoTwo.this,ShopProfileActivity.class));


                    } else {
                        progressDialog.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(NewTovarRwoTwo.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }















    }

    private void OpneGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            ImageUriTwo = data.getData();
            roundedImageViewTovar.setImageURI(ImageUriTwo);
        }

    }

}