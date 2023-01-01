package mychati.app.Holders.ChildHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class GlavChildHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView glavmagname;
    public RoundedImageView imageGlavMag;
    public ItemClickListener itemClickListener;

    public GlavChildHolder(View itemView){
        super(itemView);
        glavmagname=itemView.findViewById(R.id.glavmagname);
        imageGlavMag=itemView.findViewById(R.id.imageGlavMag);
    }

    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
