package com.ecommerce.ecommerce_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce_backend.entity.Cart;
import com.ecommerce.ecommerce_backend.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

	Optional<Cart> findByUser(User user);

	Cart save(Cart cart);

}
