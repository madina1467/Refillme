package com.example.bluetooth2.database;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class FirebaseDB {

    private static FirebaseDB instance;
    private static FirebaseFirestore db;
//    private static FirebaseUser user;
//    private static FirebaseAuth mAuth;
//    private static Qrcode activity;

    private FirebaseDB() {
        this.db = FirebaseFirestore.getInstance();
//        this.mAuth = FirebaseAuth.getInstance();
        this.setup();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            System.out.println("!!!!!!!! user != null : " + user.toString());
//        } else {
//            System.out.println("!!!!!!!! user is null");
//
//        }
    }

//    static {
//        instance = new FirebaseDB();
//    }
//    public static FirebaseDB getInstance() {
//        return instance;
//    }

    public static synchronized FirebaseDB getInstance() {
        if (instance == null) {
            instance = new FirebaseDB();
        }
//        instance.activity = activity;
        return instance;
    }

    public void setup() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

//        mAuth.signInWithEmailAndPassword("ramonaa.135890@gmail.com", "m1m2m3madina123gmailramonaa") //"m1m2m3m45678"
//                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
////                            Log.d(TAG, "signInWithEmail:success");
//                            System.out.println("!!!!!!!! signInWithEmail:success");
//                            user = mAuth.getCurrentUser();
////                             updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            System.out.println("!!!!!!!! signInWithEmail:failure");
//                            task.getException().printStackTrace();
////                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                            // ...
//                        }
//
//                        // ...
//                    }
//                });
    }

    public void add(){
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.err.println("!!!!! DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("!!!! Error adding document");
                        e.printStackTrace();
                    }
                });
    }
}
