package com.daniel.mamafood.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class Meal {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String userId;
    private Long lastUpdated;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("description", description);
        result.put("price", price);
        result.put("imageUrl", imageUrl);
        result.put("userId", userId);
        result.put("lastUpdated", FieldValue.serverTimestamp());

        return result;
    }

    public void fromMap(Map<String, Object> map) {
        id = (String)map.get("id");
        name = (String)map.get("name");
        description = (String)map.get("description");
        price = (Double)map.get("price");
        imageUrl = (String)map.get("imageUrl");
        userId = (String)map.get("userId");

        Timestamp ts = (Timestamp) map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
