package com.megednan.molxforsale.ui.frags;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.adapters.AdsAdapter;
import com.megednan.molxforsale.databinding.AllAdsFragmentBinding;
import com.megednan.molxforsale.databinding.CategoryAdsFragmentBinding;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.viewmodels.CategoryAdsViewModel;

public class CategoryAdsFragment extends Fragment {
    private CategoryAdsFragmentBinding binding;
    private AdsAdapter adapter;
    private FirestoreRecyclerOptions<AdPost> categoryAdsOptions;
    private CategoryAdsViewModel mViewModel;
    private CategoryAdsFragmentArgs args;

    public static CategoryAdsFragment newInstance() {
        return new CategoryAdsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.category_ads_fragment, container, false);
        setUpRecyclerView();
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CategoryAdsViewModel.class);
        if (getArguments() != null) {
            args = CategoryAdsFragmentArgs.fromBundle(getArguments());
            String category = args.getCategory();
            categoryAdsOptions = mViewModel.getCategoryOptions(category);
        }
    }
    private void setUpRecyclerView() {
        adapter = new AdsAdapter(categoryAdsOptions);
        binding.categoryAdsFragRv.setHasFixedSize(true);
        binding.categoryAdsFragRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.categoryAdsFragRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                AdPost post = documentSnapshot.toObject(AdPost.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("post",post);
                Navigation.findNavController(binding.categoryAdsFragRv).
                        navigate(R.id.action_categoryAdsFragment_to_adDetailsFragment,bundle);
            }

        });
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}