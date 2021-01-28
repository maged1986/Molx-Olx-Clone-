package com.megednan.molxforsale.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.repository.DataRepository;

public class MyAdsViewModel extends ViewModel {
    private DataRepository repository;

    @ViewModelInject
    public MyAdsViewModel() {
    }
    public FirestoreRecyclerOptions<AdPost> getMyAdsOptions(FirebaseUser user) {
        return repository.getMyAdsOptions(user);
    }
}