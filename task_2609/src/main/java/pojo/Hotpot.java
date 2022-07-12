package pojo;

import java.text.MessageFormat;
import java.util.ArrayList;

public class Hotpot extends Restaurant {
    private ArrayList<String> soupBase;
    private String slogan;
    private int branch;

    public Hotpot() {
    }

    public Hotpot(String name) {
        this.name = name;

    }

    /**
     * 接受顾客的点单，并且展示顾客的单号。把顾客的单存入一个hashmap以便后续使用
     *
     * @param orders 顾客点的单
     */
    @Override
    public void order(ArrayList<String> orders) {
        this.id++;
        id2Dishes.put(this.id, orders);
        StringBuilder str = new StringBuilder();
        str.append("You've ordered the following: \n");
        int i = 1;
        for (String order : orders) {
            str.append(MessageFormat.format("Dish {0}: {1}\n", i, order));
            i++;
        }
        str.append(MessageFormat.format(
                "Thank you for your order!" +
                        "Your order id is: {0}\n", this.id.toString()));
        System.out.println(str);
    }

    /**
     * 上菜，并且显示所有的菜品
     *
     * @param orderId 订单号
     */
    @Override
    public void serving(Integer orderId) {
        StringBuilder str = new StringBuilder();
        if (this.id2Dishes.get(orderId) != null) {
            ArrayList<String> orders = this.id2Dishes.get(orderId);
            str.append("Now serving:\n");
            for (String order : orders) {
                str.append(MessageFormat.format("Dish {0}\n", order));
            }
            str.append("All dishes have been served. Thank you!\n");
        } else {
            str.append("Invalid order id\n");
        }
        System.out.println(str);
    }

    public void receive(Integer orderId) {
        System.out.println("Pay received! Thank you\n");
    }

    public ArrayList<String> getSoupBase() {
        return soupBase;
    }

    public void setSoupBase(ArrayList<String> soupBase) {
        this.soupBase = soupBase;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        String str = MessageFormat.format(
                "Name: {0} \nType: {1} \nAddress: {2} \nBranch: {3} \nSlogan: {4}\nId: {5}\n",
                this.name, this.type, this.address, this.branch, this.slogan, this.id);
        return String.valueOf(str);
    }
}
