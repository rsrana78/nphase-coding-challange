package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartService {

    private final int productQuantityForDiscount = 3;
    private final double discountPercentage = 0.10;

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        Map<String, Integer> tempMap = new HashMap<>();
        List<String> discountCategoryList = new ArrayList<>();
        shoppingCart.getProducts().forEach(product -> {
            if(tempMap.containsKey(product.getCategory()) && (product.getQuantity() + tempMap.get(product.getCategory())) > productQuantityForDiscount){
                discountCategoryList.add(product.getCategory());
            }
            tempMap.put(product.getCategory(), product.getQuantity());
        });
        return shoppingCart.getProducts()
                .stream()
                .map(product -> {
                    if(product.getQuantity() > productQuantityForDiscount || discountCategoryList.contains(product.getCategory())){
                        BigDecimal totalPrice = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                        return totalPrice.subtract(totalPrice.multiply(BigDecimal.valueOf(discountPercentage)));
                    }else{
                        return product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                    }
                })
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
