package ru.gb.generics;

import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit>{
    private List<T> fruits;

    public Box() {
        this.fruits = new ArrayList<>();
    }

    public List<T> getFruits() {
        return fruits;
    }

    public float getWeight(){
        if (fruits.isEmpty()) return 0.0f;
        return fruits.get(0).getWeight() * fruits.size();
    }

    public boolean compare(Box<? extends Fruit> that){
        return this.getWeight() == that.getWeight();
    }

    public void bulk (Box<T> that){
        fruits.addAll(that.getFruits());
        that.getFruits().clear();
    }

    public void add(T fruit){
        fruits.add(fruit);
    }
}
