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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.adapters.AdsAdapter;
import com.megednan.molxforsale.databinding.AllAdsFragmentBinding;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.viewmodels.AllAdsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllAdsFragment extends Fragment {


    private AllAdsFragmentBinding binding;
    private AdsAdapter adapter;
    private AllAdsViewModel mViewModel;
    private FirestoreRecyclerOptions<AdPost> allAdsOptions;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.all_ads_fragment, container, false);
        setUpRecyclerView();
        return binding.getRoot();


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllAdsViewModel.class);
       allAdsOptions=mViewModel.getAllAdsOptions();
    }

    private void setUpRecyclerView() {

        adapter = new AdsAdapter(allAdsOptions);
        binding.allAdsFragRv.setHasFixedSize(true);
        binding.allAdsFragRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.allAdsFragRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                AdPost post = documentSnapshot.toObject(AdPost.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("post",post);
                  Navigation.findNavController(binding.allAdsFragRv).
                          navigate(R.id.action_allAdsFragment_to_adDetailsFragment,bundle);
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