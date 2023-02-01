package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1, "Drinks"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, "Drinks")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(12.0), result);
    }

    @Test
    public void testItemBasedDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, "Tea"),
                new Product("Biscuits", BigDecimal.valueOf(3.5), 3, "Biscuits")
        ));
        BigDecimal result = service.calculateTotalPrice(cart);
        Assertions.assertEquals(BigDecimal.valueOf(33.0), result.setScale(1, RoundingMode.CEILING));
    }

    @Test
    public void testCategoryBasedDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, "Drinks"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, "Drinks"),
                new Product("Cheese", BigDecimal.valueOf(8), 2, "Food")
        ));
        BigDecimal result = service.calculateTotalPrice(cart);
        Assertions.assertEquals(BigDecimal.valueOf(31.84), result.setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void testItemBasedAndCategoryBasedDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 5, "Drinks"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, "Drinks"),
                new Product("Pepsi", BigDecimal.valueOf(1.5), 1, "Drinks"),
                new Product("Cheese", BigDecimal.valueOf(8), 2, "Food")
        ));
        BigDecimal result = service.calculateTotalPrice(cart);
        Assertions.assertEquals(BigDecimal.valueOf(50.65), result.setScale(2, RoundingMode.CEILING));
    }

}