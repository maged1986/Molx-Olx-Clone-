package com.megednan.molxforsale.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.repository.DataRepository;

public class CategoryAdsViewModel extends ViewModel {
    private DataRepository repository;
 @ViewModelInject
     public CategoryAdsViewModel() {
    }
    public FirestoreRecyclerOptions<AdPost> getCategoryOptions(String category) {
        return repository.getCategoryOptions(category);
    }

}