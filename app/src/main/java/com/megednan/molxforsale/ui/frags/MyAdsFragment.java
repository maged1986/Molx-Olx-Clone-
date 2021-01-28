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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.adapters.AdsAdapter;
import com.megednan.molxforsale.databinding.AllAdsFragmentBinding;
import com.megednan.molxforsale.databinding.MyAdsFragmentBinding;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.viewmodels.AllAdsViewModel;
import com.megednan.molxforsale.viewmodels.MyAdsViewModel;

public class MyAdsFragment extends Fragment {
    private MyAdsFragmentBinding binding;
    private AdsAdapter adapter;
    private FirestoreRecyclerOptions<AdPost> myAdsOptions;
    private MyAdsViewModel mViewModel;
    private FirebaseAuth auth;
    FirebaseUser user;

    public static MyAdsFragment newInstance() {
        return new MyAdsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_ads_fragment, container, false);
        setUpRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyAdsViewModel.class);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        myAdsOptions=mViewModel.getMyAdsOptions(user);
    }
    private void setUpRecyclerView() {

        adapter = new AdsAdapter(myAdsOptions);
        binding.myAdsFragRv.setHasFixedSize(true);
        binding.myAdsFragRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.myAdsFragRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                AdPost post = documentSnapshot.toObject(AdPost.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("post",post);
                Navigation.findNavController(binding.myAdsFragRv).
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