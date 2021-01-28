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
import com.megednan.molxforsale.databinding.CategoryAdsFragmentBinding;
import com.megednan.molxforsale.databinding.SearchFragmentBinding;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.viewmodels.CategoryAdsViewModel;
import com.megednan.molxforsale.viewmodels.SearchViewModel;

public class SearchFragment extends Fragment {
    private SearchFragmentBinding binding;
    private AdsAdapter adapter;
    private FirestoreRecyclerOptions<AdPost> searchAdsOptions;
    private SearchFragmentArgs args;
    private SearchViewModel mViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        setUpRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        if (getArguments() != null) {
            args = SearchFragmentArgs.fromBundle(getArguments());
            String searchItem = args.getSearchItem();
            searchAdsOptions = mViewModel.getSearchOptions(searchItem);
        }
    }

    private void setUpRecyclerView() {
        adapter = new AdsAdapter(searchAdsOptions);
        binding.searchFragRv.setHasFixedSize(true);
        binding.searchFragRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchFragRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                AdPost post = documentSnapshot.toObject(AdPost.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", post);
                Navigation.findNavController(binding.searchFragRv).
                        navigate(R.id.action_searchFragment_to_adDetailsFragment, bundle);
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