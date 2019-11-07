package com.digitalhealth.kesehatan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class KamarAdapter extends RecyclerView.Adapter<KamarAdapter.MyViewHolder> {

    Context context;
    ArrayList<My_Ticket>myTicket ;

    public KamarAdapter(Context c, ArrayList<My_Ticket> p){
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_pesanan, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.xjumlah_kamar.setText(myTicket.get(i).getJumlah_pesanan() + " kamar");
        myViewHolder.xjenis_kamar.setText(myTicket.get(i).getJenis_kamar());

        final String getJenis_Kamar = myTicket.get(i).getJenis_kamar();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {

        return myTicket.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xjenis_kamar, xjumlah_kamar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xjenis_kamar = itemView.findViewById(R.id.xjenis_kamar);
            xjumlah_kamar = itemView.findViewById(R.id.xjumlah_kamar);
        }
    }

}
