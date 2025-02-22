package com.ruksana.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ruksana.jobprostuti.DetailsForQbankActivity;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.Model_Firestore_Database;

import java.util.ArrayList;


public class adapter extends RecyclerView.Adapter<adapter.myviewholder>
{
   ArrayList<Model_Firestore_Database> datalist;

    public adapter(ArrayList<Model_Firestore_Database> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") int position) {
      holder.name.setText(datalist.get(position).getName());


      holder.mlayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String url = datalist.get(position).getData().toString();

//              try {
//                  CustomTabsIntent intent = new CustomTabsIntent.Builder()
//                          .build();
//                  intent.launchUrl(v.getContext(), Uri.parse(url));
//              }catch (Exception e){
//                  Toast.makeText(v.getContext(), "No link available.. Please try again later", Toast.LENGTH_SHORT).show();
//              }

              // Create an intent to start the PostActivity
              Intent intent = new Intent(v.getContext(), DetailsForQbankActivity.class);
              intent.putExtra("title", datalist.get(position).getName());
              intent.putExtra("pageNumber", datalist.get(position).getPage());
              intent.putExtra("data", datalist.get(position).getData());
              //intent.putExtra("linkText", datalist.get(position).getLinkText());
              v.getContext().startActivity(intent);

          }
      });

//      holder.watchNowBtn.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              String url = datalist.get(position).getData().toString();
//
//              try {
//                  CustomTabsIntent intent = new CustomTabsIntent.Builder()
//                          .build();
//                  intent.launchUrl(view.getContext(), Uri.parse(url));
//              }catch (Exception e){
//                  Toast.makeText(view.getContext(), "No link available.. Please try again later", Toast.LENGTH_SHORT).show();
//              }
//          }
//      });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
       LinearLayout mlayout;
       TextView name;
       ImageView watchNowBtn;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sample_nameId);
            watchNowBtn=itemView.findViewById(R.id.watchNowButtonId);
            mlayout=itemView.findViewById(R.id.sample_mainLinearId);

        }
    }

}
