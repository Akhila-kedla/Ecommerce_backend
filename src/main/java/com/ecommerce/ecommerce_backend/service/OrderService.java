package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.*;
import com.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderItemRepository orderItemRepository) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Order checkout(Long userId, String paymentMode) {

        logger.info("Starting checkout for userId: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart is empty"));

        if (cart.getCartItems().isEmpty()) {
            logger.warning("Checkout failed -> Empty cart");
            throw new RuntimeException("No items in cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMode(paymentMode);
        order.setPaymentStatus(Order.PaymentStatus.PAYMENT_SUCCESS);
        order.setOrderStatus(Order.OrderStatus.PLACED);

        List<OrderItem> orderItemList = new ArrayList<>();

        double total = 0;

        for (CartItem ci : cart.getCartItems()) {

            Product product = ci.getProduct();

            if (product.getStock() < ci.getQuantity()) {
                logger.warning("Product out of stock: " + product.getName());
                throw new ResourceNotFoundException("Product out of stock: " + product.getName());
            }

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(product.getPrice());

            orderItemList.add(oi);

            total += product.getPrice() * ci.getQuantity();

            product.setStock(product.getStock() - ci.getQuantity());
            productRepository.save(product);

            logger.info("OrderItem added: " + product.getName() +
                    " qty=" + ci.getQuantity());
        }

        order.setOrderItems(orderItemList);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        clearCart(cart);

        logger.info("Checkout completed successfully for userId: " + userId);
        return savedOrder;
    }


    private void clearCart(Cart cart) {

        logger.info("Clearing cart for userId: " + cart.getUser().getId());

        for (CartItem item : cart.getCartItems()) {
            cartItemRepository.delete(item);
        }

        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }


    public Order getOrderById(Long id) {

        logger.info("Fetching order by ID: " + id);

        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }


    public List<Order> getUserOrders(Long userId) {

        logger.info("Fetching all orders for user: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return orderRepository.findByUser(user);
    }


    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {

        logger.info("Updating order status for orderId: " + orderId);

        Order order = getOrderById(orderId);
        order.setOrderStatus(status);

        Order updated = orderRepository.save(order);

        logger.info(" Order updated successfully: " + orderId);
        return updated;
    }
}
