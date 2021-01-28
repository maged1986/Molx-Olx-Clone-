package com.megednan.molxforsale.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.repository.DataRepository;

public class AllAdsViewModel extends ViewModel {
    private DataRepository repository;
   @ViewModelInject
    public AllAdsViewModel() {
    }
    public FirestoreRecyclerOptions<AdPost> getAllAdsOptions() {
        return repository.getAllAdsOptions();
    }
}