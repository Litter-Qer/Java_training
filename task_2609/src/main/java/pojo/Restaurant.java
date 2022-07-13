package pojo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 火锅类
 */
public abstract class Restaurant {
    protected String type;
    protected String name;
    protected String address;
    protected ArrayList<String> dishes;
    protected Integer id;
    protected final HashMap<Integer,ArrayList<String>> id2Dishes;

    public Restaurant() {
        this.id = 10000;
        this.id2Dishes = new HashMap<>();
    }

    public Restaurant(String name) {
        this();
        this.name = name;
    }

    public abstract void order(ArrayList<String> orders);

    public abstract void serving(Integer orderId);

    public abstract void receive(Integer orderId);

    public void addDish(String dish){
        this.dishes.add(dish);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<Integer, ArrayList<String>> getId2Dishes() {
        return id2Dishes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<String> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "type=" + type +
                "\nname=" + name +
                "\naddress=" + address +
                "\ndishes=" + dishes +
                "\nid=" + id +
                "\nid2Dishes=" + id2Dishes + "\n";
    }
}
