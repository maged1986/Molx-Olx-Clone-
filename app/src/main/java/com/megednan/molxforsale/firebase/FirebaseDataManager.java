package com.megednan.molxforsale.firebase;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.megednan.molxforsale.models.AdPost;

import java.util.Date;

import javax.inject.Inject;

public class FirebaseDataManager {
    String imageUrl;
    private static final String TAG = "FirebaseDataManager";

    @Inject
    public FirebaseDataManager() {
    }

    public String uploadPhoto(Uri uri, Context context) {
        Date date = java.util.Calendar.getInstance().getTime();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("posts/users/" + "/" + date  + "/post_image");
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUrl = uri.toString();
                        Toast.makeText(context, "Successful Upload Photo",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return imageUrl;
    }

    public void uploadAd(AdPost post, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Ads").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(context, "Data Update", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }
    public void readProfileData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
    public FirestoreRecyclerOptions<AdPost> getAllAdsOptions(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference adsRef = db.collection("Ads");
        FirestoreRecyclerOptions<AdPost> options = new FirestoreRecyclerOptions.Builder<AdPost>()
                .setQuery(adsRef, AdPost.class)
                .build();
        return options;
    } public FirestoreRecyclerOptions<AdPost> getSearchOptions(String searchItem){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference adsRef = db.collection("Ads");
        Query query= adsRef.whereEqualTo("title", searchItem);
        FirestoreRecyclerOptions<AdPost> options = new FirestoreRecyclerOptions.Builder<AdPost>()
                .setQuery(query, AdPost.class)
                .build();
        return options;
    }
    public FirestoreRecyclerOptions<AdPost> getMyAdsOptions(FirebaseUser user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference adsRef = db.collection("Ads");
        Query query= adsRef.whereEqualTo("userId", user);
        FirestoreRecyclerOptions<AdPost> options = new FirestoreRecyclerOptions.Builder<AdPost>()
                .setQuery(query, AdPost.class)
                .build();
        return options;
    }
    public FirestoreRecyclerOptions<AdPost> getCategoryOptions(String category){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference adsRef = db.collection("Ads");
        Query query= adsRef.whereEqualTo("category", category);
        FirestoreRecyclerOptions<AdPost> options = new FirestoreRecyclerOptions.Builder<AdPost>()
                .setQuery(query, AdPost.class)
                .build();
        return options;
    }


}
