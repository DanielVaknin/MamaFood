package com.daniel.mamafood.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    List<Meal> data = new LinkedList<>();

    public final static Model instance = new Model();

    private Model(){
        Meal meal1 = new Meal();
        meal1.setName("test");
        meal1.setPrice(137.5);
        data.add(meal1);

        Meal meal2 = new Meal();
        meal2.setName("test2");
        meal2.setPrice((double) 154);
        data.add(meal2);
    }

    public List<Meal> getAllMeals() {
        return data;
    }

    public Meal getMealById(String id) {
        for (Meal m : data) {
            if (m.getId().equals(id))
                return m;
        }
        return null;
    }
}
