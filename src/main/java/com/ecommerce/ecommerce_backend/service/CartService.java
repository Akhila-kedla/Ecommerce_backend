package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.*;
import com.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.repository.CartItemRepository;
import com.ecommerce.ecommerce_backend.repository.CartRepository;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CartService {

    private static final Logger logger = Logger.getLogger(CartService.class.getName());

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }


    public Cart getOrCreateCart(Long userId) {

        logger.info("Fetching cart for user: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.severe("User not found: " + userId);
                    return new ResourceNotFoundException("User not found");
                });

        Optional<Cart> cartOptional = cartRepository.findByUser(user);

        if (cartOptional.isPresent()) {
            logger.info("Existing cart found for user: " + userId);
            return cartOptional.get();
        }

        logger.info("No cart found → creating new cart for user: " + userId);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);

        return cartRepository.save(cart);
    }


    public Cart addProductToCart(Long userId, Long productId, int quantity) {

        logger.info("Adding product " + productId + " (qty: " + quantity + ") to cart for user: " + userId);

        Cart cart = getOrCreateCart(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.severe("Product not found: " + productId);
                    return new ResourceNotFoundException("Product not found");
                });

        Optional<CartItem> existed = cartItemRepository.findByCartAndProduct(cart, product);

        if (existed.isPresent()) {

            CartItem item = existed.get();
            logger.info("Product already in cart → Increasing quantity");
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);

        } else {

            logger.info("Adding new product into cart");

            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

            cartItemRepository.save(cartItem);
        }

        updateCartTotal(cart);

        logger.info("Product added. Cart total updated.");
        return cartRepository.save(cart);
    }


    public Cart updateItemQuantity(Long userId, Long productId, int newQuantity) {

        logger.info("Updating quantity for product " + productId + " to " + newQuantity + " for user: " + userId);

        Cart cart = getOrCreateCart(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.severe("Product not found: " + productId);
                    return new ResourceNotFoundException("Product not found");
                });

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> {
                    logger.severe("Item not found in cart for product: " + productId);
                    return new ResourceNotFoundException("Item not found in cart");
                });

        item.setQuantity(newQuantity);
        cartItemRepository.save(item);

        updateCartTotal(cart);

        logger.info("Quantity updated successfully");
        return cartRepository.save(cart);
    }


    public Cart removeFromCart(Long userId, Long productId) {

        logger.warning("Removing product " + productId + " from cart for user: " + userId);

        Cart cart = getOrCreateCart(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.severe("Product not found: " + productId);
                    return new ResourceNotFoundException("Product not found");
                });

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> {
                    logger.severe("Item not found in cart for product: " + productId);
                    return new ResourceNotFoundException("Item not found");
                });

        cartItemRepository.delete(item);

        updateCartTotal(cart);

        logger.info("Product removed and cart updated");
        return cartRepository.save(cart);
    }


    public Cart getUserCart(Long userId) {
        logger.info("Fetching user cart for user: " + userId);
        return getOrCreateCart(userId);
    }


    private void updateCartTotal(Cart cart) {

        logger.info("Recalculating cart total");

        double sum = 0;

        for (CartItem item : cart.getCartItems()) {
            sum += item.getProduct().getPrice() * item.getQuantity();
        }

        cart.setTotalPrice(sum);

        logger.info("New cart total: " + sum);
    }
}
