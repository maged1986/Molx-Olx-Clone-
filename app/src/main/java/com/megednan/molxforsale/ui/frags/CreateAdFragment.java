package com.megednan.molxforsale.ui.frags;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.databinding.CreateAdFragmentBinding;
import com.megednan.molxforsale.models.AdPost;
import com.megednan.molxforsale.viewmodels.AllAdsViewModel;
import com.megednan.molxforsale.viewmodels.CreateAdViewModel;

import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateAdFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int PICKFILE_REQUEST_CODE = 1234;
    private CreateAdViewModel mViewModel;
    private String category;
    private final String postId = FirebaseDatabase.getInstance().getReference().push().getKey();
    private FirebaseAuth auth= FirebaseAuth.getInstance();
    private FirebaseUser userId=auth.getCurrentUser();
    private CreateAdFragmentBinding binding;
    private Uri selectedImageUri;
    private String imageUrl;




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_ad_fragment, container, false);
        return binding.getRoot();    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(CreateAdViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner
                , android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(this);


        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });
        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.uploadAd(getPost(),getContext());
                resetFields();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data.getData();
            Glide.with(binding.getRoot()).load(selectedImageUri).centerCrop().into(binding.postImage);
            Date date = java.util.Calendar.getInstance().getTime();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("posts/users/" + "/" + date + userId + "/post_image");
            storageReference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                        }
                    });
                }
            });
                    Toast.makeText(getContext(),imageUrl, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        binding.postImage.setImageResource(R.drawable.ic_launcher_foreground);
        binding.inputTitle.setText("");
        binding.inputDescription.setText("");
        binding.inputPrice.setText("");
        binding.inputCountry.setText("");
        binding.inputStateProvince.setText("");
        binding.inputCity.setText("");
        binding.inputEmail.setText("");
    }
    private AdPost getPost(){
        AdPost post = new AdPost();
        post.setUserId(userId.getUid());
        post.setCity(binding.inputCity.getText().toString());
        post.setEmail(binding.inputEmail.getText().toString());
        post.setCountry(binding.inputCountry.getText().toString());
        post.setDescription(binding.inputDescription.getText().toString());
        post.setPostId(postId);
        float price = Float.parseFloat((binding.inputPrice.getText().toString()));
        post.setPrice(price);
        post.setState_province(binding.inputStateProvince.getText().toString());
        post.setTitle(binding.inputTitle.getText().toString());
        post.setCategory(category);
        post.setImageUrl(imageUrl);
                return post;

    }


}