package com.megednan.molxforsale.ui.frags;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.databinding.AdDetailsFragmentBinding;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.viewmodels.AdDetailsViewModel;

public class AdDetailsFragment extends Fragment {

    private AdDetailsFragmentBinding binding;
    private AdDetailsFragmentArgs args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.ad_details_fragment, container, false);
        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            args=AdDetailsFragmentArgs.fromBundle(getArguments());
            AdPost post=args.getAdPost();
            binding.adDetailsTvTitle.setText(post.getTitle());
            binding.adDetailsTvCategory.setText(post.getCategory());
            binding.adDetailsTvCity.setText(post.getCity());
            binding.adDetailsTvDescription.setText(post.getDescription());
            binding.adDetailsTvEmail.setText(post.getEmail());
            String price=String.valueOf(post.getPrice()) ;
            binding.adDetailsTvPrice.setText(price);
            Glide.with(binding.adDetailsIvImage).load(post.getImageUrl())
                    .centerCrop().into(binding.adDetailsIvImage);
        }
    }


}