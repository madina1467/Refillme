package com.example.bluetooth2.database;

import com.example.bluetooth2.dao.Order;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {

    private static FirebaseDB instance;
    private static FirebaseFirestore db;

    private FirebaseDB() {
        this.db = FirebaseFirestore.getInstance();
        this.setup();
    }

    public static synchronized FirebaseDB getInstance() {
        if (instance == null) {
            instance = new FirebaseDB();
        }
        return instance;
    }

    public void setup() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void add(Order order){
        Map<String, Object> orderMap = new HashMap<>();

        orderMap.put("orderId", order.getOrderId());
        orderMap.put("branchId", order.getBranchId());
        orderMap.put("productId", order.getProductId());
        orderMap.put("productName", order.getProductName());
        orderMap.put("amount", order.getAmount());
        orderMap.put("price", order.getPrice());
        orderMap.put("paidByCompany", order.getPaidByCompany());
        orderMap.put("date", order.getDate());
        orderMap.put("paymentStatus", order.getPaymentStatus());

        db.collection(order.getCity()).document(String.valueOf(order.getBranchId()))
                .collection(order.getOrderDate()).document(order.getOrderTime())
                .set(orderMap);
    }
}
