package com.megednan.molxforsale.viewmodels;

import android.content.Context;
import android.net.Uri;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.repository.DataRepository;

public class CreateAdViewModel extends ViewModel {
    private DataRepository repository;

    @ViewModelInject
    public CreateAdViewModel() {
    }
    public String uploadPhoto(Uri uri, Context context) {
        return repository.uploadPhoto(uri,  context);
    }

    public void uploadAd(AdPost post, Context context) {
        repository.uploadAd(post, context);
    }

}