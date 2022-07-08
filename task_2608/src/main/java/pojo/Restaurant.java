package pojo;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 火锅类
 */
public class Restaurant {
    private String type;
    private String name;
    private String address;
    private ArrayList<String> chefs;
    private ArrayList<String> waiters;
    private ArrayList<String> dishes;
    private Integer id;
    private final HashMap<Integer,ArrayList<String>> id2Dishes;

    public Restaurant() {
        this.id = 10000;
        this.id2Dishes = new HashMap<>();
    }

    /**
     * 接受顾客的点单，并且展示顾客的单号。把顾客的单存入一个hashmap以便后续使用
     * @param orders 顾客点的单
     */
    public void order(ArrayList<String> orders){
        this.id++;
        id2Dishes.put(this.id,orders);
        StringBuilder str = new StringBuilder();
        str.append("You've ordered the following: \n");
        int i = 1;
        for (String order :orders) {
            str.append(MessageFormat.format("Dish {0}: {1}\n",i,order));
            i++;
        }
        str.append(MessageFormat.format(
                "Thank you for your order!" +
                "Your order id is: {0}\n",this.id.toString()));
        System.out.println(str);
    }

    /**
     * 上菜，并且显示所有的菜品
     * @param orderId 订单号
     */
    public void serving(Integer orderId){
        StringBuilder str = new StringBuilder();
        if (this.id2Dishes.get(orderId) != null){
            ArrayList<String> orders = this.id2Dishes.get(orderId);
            str.append("Now serving:\n");
            for (String order :orders) {
                str.append(MessageFormat.format("Dish {0}\n",order));
            }
            str.append("All dishes have been served. Thank you!\n");
        } else {
            str.append("Invalid order id\n");
        }
        System.out.println(str);
    }

    /**
     * 收钱, 应该是按照订单号来计算，并且每个单只能收一次钱
     * @param orderId 订单号
     */
    public void receive(Integer orderId){
        System.out.println("Pay received! Thank you\n");
    }

    public void addChef(String chef){
        this.chefs.add(chef);
    }

    public void addWaiter(String waiter){
        this.waiters.add(waiter);
    }

    public void addDish(String dish){
        this.dishes.add(dish);
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

    public ArrayList<String> getChefs() {
        return chefs;
    }

    public void setChefs(ArrayList<String> chefs) {
        this.chefs = chefs;
    }

    public ArrayList<String> getWaiters() {
        return waiters;
    }

    public void setWaiters(ArrayList<String> waiters) {
        this.waiters = waiters;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<String> dishes) {
        this.dishes = dishes;
    }
}
