package com.practice.model;

import java.math.BigDecimal;

public record FoodItem(long foodId,
                       String foodItem,
                       BigDecimal price) {
}
