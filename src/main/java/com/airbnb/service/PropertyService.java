package com.airbnb.service;

import com.airbnb.model.Property;
import com.airbnb.model.User;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    Property createProperty(User user, Property property);

    List<Property> getAllProperties();

    Property getPropertyById(Long id) throws Exception;

    Property updateProperty(Long id, Property propertyDetails) throws Exception;

    void deleteProperty(Long id) throws Exception;

    List<Property> getPropertiesByUser(User user);
}
