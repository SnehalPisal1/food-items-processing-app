package com.practice.service;

import com.practice.model.FoodItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class FoodService {
    
    private final List<FoodItem> foodItems;

    private static final Logger logger = Logger.getLogger(FoodService.class.getName());
    public FoodService(List<FoodItem> foodItemList) {
        this.foodItems = foodItemList;
    }


    public List<FoodItem> sortByPrice(String order) {

        return switch (order) {
            case "asc" -> {
                logger.info("---Ascending Sort Operation---");

                yield foodItems.stream()
                        .sorted(Comparator.comparing(FoodItem::price)).toList();
            }
            case "desc" -> {

                logger.info("---Descending Sort Operation---");

                yield foodItems.stream().
                        sorted(Comparator.comparing(FoodItem::price).reversed()).toList();
            }
            default -> throw new IllegalArgumentException("Invalid sorting order");
        };
    }

    public List<FoodItem> filterPrice(String operator , String value) {

        if(operator == null || operator.isEmpty()){
            throw new IllegalArgumentException("Operator should not be null or empty");
        }
        if(value == null || value.isEmpty()){
            throw new IllegalArgumentException("value should not be null or empty");
        }
        BigDecimal val = new BigDecimal(value);
        return foodItems.stream().filter(item
                -> camparePrice(item.price(), val,operator)).toList();
    }

    private boolean camparePrice(BigDecimal price, BigDecimal value, String operator) {
        int comparison = price.compareTo(value);
        return switch (operator) {
            case "<" -> comparison < 0;
            case "<=" -> comparison <= 0;
            case ">" -> comparison > 0;
            case ">=" -> comparison >= 0;
            case "=" -> comparison == 0;
            case "!=" -> comparison != 0;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    public List<FoodItem> filterName(String name) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("Name should not be null or empty");
        }
        return foodItems.stream().filter(f -> name.equalsIgnoreCase(f.foodItem())).toList();
    }

    public List<FoodItem> filterId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }
        return foodItems.stream().filter(foodItem -> id == foodItem.foodId()).toList();
    }

    public void statistics() {
        // cheapest food item
        if (foodItems.isEmpty()) {
            System.out.println("No food items available for statistics.");
            return;
        }
        FoodItem cheapItem = foodItems.stream().min(Comparator.comparing(FoodItem :: price)).orElseThrow();
        FoodItem expensive = foodItems.stream().max(Comparator.comparing(FoodItem :: price)).orElseThrow();

        BigDecimal total = BigDecimal.ZERO;
        int count = 0;
        for(FoodItem foodItem :foodItems){
            total = total.add(foodItem.price());
             count++;
        }
        BigDecimal average = total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);


        System.out.println("Cheapest Item : "+ cheapItem);
        System.out.println("Expensive Item : "+ expensive);
        System.out.println("Average : "+average);


    }
}
