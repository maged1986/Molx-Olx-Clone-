package com.megednan.molxforsale.repository;

import android.content.Context;
import android.net.Uri;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.megednan.molxforsale.firebase.FirebaseDataManager;
import com.megednan.molxforsale.models.AdPost;

import javax.inject.Inject;

public class DataRepository {
    private FirebaseDataManager manager;

    @Inject
    public DataRepository() {
    }

    public String uploadPhoto(Uri uri, Context context) {
        return manager.uploadPhoto(uri,  context);
    }

    public void uploadAd(AdPost post, Context context) {
        manager.uploadAd(post, context);
    }

    public void readProfileData() {
        manager.readProfileData();
    }

    public FirestoreRecyclerOptions<AdPost> getAllAdsOptions() {
        return manager.getAllAdsOptions();
    }

    public FirestoreRecyclerOptions<AdPost> getCategoryOptions(String category) {
        return manager.getCategoryOptions(category);
    }

    public FirestoreRecyclerOptions<AdPost> getMyAdsOptions(FirebaseUser user) {
        return manager.getMyAdsOptions(user);
    }

    public FirestoreRecyclerOptions<AdPost> getSearchOptions(String searchItem) {
        return manager.getSearchOptions(searchItem);
    }
}

