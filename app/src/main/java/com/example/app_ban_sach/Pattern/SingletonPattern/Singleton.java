package com.example.app_ban_sach.Pattern.SingletonPattern;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Singleton {
    public static FirebaseDatabase db = FirebaseDatabase.getInstance();
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private static final Singleton InstanceFirebase = new Singleton();
    private Singleton(){}
//    public static void GetFirebaseStorage(){
//        firebaseStorage = FirebaseStorage.getInstance();
//    }
//
//    public static void GetFirebaseDatabase(){
//        db = FirebaseDatabase.getInstance();
//    }

}
