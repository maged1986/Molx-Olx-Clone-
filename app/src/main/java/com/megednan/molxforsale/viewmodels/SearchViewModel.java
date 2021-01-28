package com.megednan.molxforsale.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.repository.DataRepository;

public class SearchViewModel extends ViewModel {
    private DataRepository repository;

    @ViewModelInject
    public SearchViewModel() {
    }


    public FirestoreRecyclerOptions<AdPost> getSearchOptions(String searchItem) {
        return repository.getSearchOptions(searchItem);
    }
}