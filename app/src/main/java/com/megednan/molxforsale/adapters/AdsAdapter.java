package com.megednan.molxforsale.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.models.AdPost;

public class AdsAdapter extends FirestoreRecyclerAdapter<AdPost, AdsAdapter.AdHolder> {
    public OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdsAdapter(@NonNull FirestoreRecyclerOptions<AdPost> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull AdHolder holder, int position, @NonNull AdPost model) {
        Glide.with(holder.imageView).load(model.getImageUrl()).fitCenter().into(holder.imageView);
        holder.textViewTitle.setText(model.getTitle());
        String price = String.valueOf(model.getPrice());
        holder.textViewPrice.setText(price);
        holder.textViewProvince.setText(model.getState_province());

    }

    @NonNull
    @Override
    public AdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_item,
                parent, false);
        return new AdHolder(v);
    }


    public class AdHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewPrice;
        TextView textViewProvince;
        ImageView imageView;
        CardView parent;

        public AdHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.ad_item_tv_title);
            textViewPrice = itemView.findViewById(R.id.ad_item_tv_price);
            textViewProvince = itemView.findViewById(R.id.ad_item_tv_state_province);
            imageView = itemView.findViewById(R.id.ad_item_iv_image);
            parent = itemView.findViewById(R.id.ad_item_cv_parent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
