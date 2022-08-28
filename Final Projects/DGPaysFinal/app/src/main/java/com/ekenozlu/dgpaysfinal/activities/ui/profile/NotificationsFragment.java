package com.ekenozlu.dgpaysfinal.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ekenozlu.dgpaysfinal.activities.MainActivity;
import com.ekenozlu.dgpaysfinal.activities.SecondActivity;
import com.ekenozlu.dgpaysfinal.databinding.FragmentNotificationsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    private String userName,userSurname,userMail;
    boolean isEditing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getDataFromDB();

        binding.editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo();
            }
        });

        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intentToMain = new Intent(getContext(), MainActivity.class);
                startActivity(intentToMain);
            }
        });
        return root;
    }

    public void getDataFromDB(){
        firebaseFirestore.collection("users").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                userMail = (String) documentSnapshot.get("email");
                binding.emailTextView.setText( (String) documentSnapshot.get("email"));
                userName = (String) documentSnapshot.get("name");
                binding.nameTextView.setText( (String) documentSnapshot.get("name"));
                userSurname = (String) documentSnapshot.get("surname");
                binding.surnameTextView.setText( (String) documentSnapshot.get("surname"));
            }
        });
    }

    public void editInfo(){
        if(!isEditing){
            isEditing = true;
            binding.nameTextView.setVisibility(View.GONE);
            binding.surnameTextView.setVisibility(View.GONE);

            binding.nameEditText.setVisibility(View.VISIBLE);
            binding.surnameEditText.setVisibility(View.VISIBLE);

            binding.nameEditText.setText(userName);
            binding.surnameEditText.setText(userSurname);
            binding.editInfoButton.setText("SAVE");
        }
        else{
            isEditing = false;
            userName = binding.nameEditText.getText().toString();
            userSurname = binding.surnameEditText.getText().toString();
            binding.nameEditText.setVisibility(View.GONE);
            binding.surnameEditText.setVisibility(View.GONE);

            binding.nameTextView.setVisibility(View.VISIBLE);
            binding.surnameTextView.setVisibility(View.VISIBLE);
            binding.editInfoButton.setText("EDIT YOUR PROFILE");
            syncDB();
        }

    }

    public void syncDB(){
        Map<String,Object> tempObject = new HashMap<>();
        tempObject.put("name",userName);
        tempObject.put("surname",userSurname);
        firebaseFirestore.collection("users").document(auth.getUid()).update(tempObject).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getDataFromDB();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}