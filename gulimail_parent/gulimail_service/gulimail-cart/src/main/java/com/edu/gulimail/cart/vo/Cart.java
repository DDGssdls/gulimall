package com.edu.gulimail.cart.vo;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/31 12:24
 * @Description: Cart 类 :整个购物车的实体类
 */

public class Cart {
    /**
     * 商品的数量
     * */
    private Integer countNum;
    /**
     * 商品的类型
     * */
    private Integer countType;
    /**
     * 商品的总价 就是所有的商品 总价 - 优惠的价格
     * */
    private BigDecimal totalAmount;
    /**
     * 商品的优惠的价格
     * */
    private BigDecimal reduce = new BigDecimal("0");

    private List<CartItem> cartItems;

    public Integer getCountNum() {
        int count = 0;
        if (!CollectionUtils.isEmpty(this.cartItems)){
            count = cartItems.stream().mapToInt(CartItem::getCount).sum();
        }
        return count;
    }

    public Integer getCountType() {
        return this.cartItems.size();
    }


    public BigDecimal getTotalAmount() {
        BigDecimal decimal = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(this.cartItems)){
            this.cartItems.forEach(cartItem ->  decimal.add(cartItem.getTotalCount()));
        }
        return decimal.subtract(getReduce());
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
