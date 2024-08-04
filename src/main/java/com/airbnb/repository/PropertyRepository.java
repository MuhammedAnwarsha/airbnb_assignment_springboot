package com.airbnb.repository;

import com.airbnb.model.Property;
import com.airbnb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByOwner(User user);
}
