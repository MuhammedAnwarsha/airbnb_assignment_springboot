package com.airbnb.service;

import com.airbnb.model.Property;
import com.airbnb.model.User;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.UserRepository;
import com.airbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Property createProperty(User user, Property property) {

        property.setOwner(user);
        return propertyRepository.save(property);
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Property getPropertyById(Long id) throws Exception {

        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            throw new Exception("Property not found with id: " + id);
        }
        return property.get();
    }

    @Override
    public Property updateProperty(Long id, Property propertyDetails) throws Exception {

        Property property = getPropertyById(id);

        //update propertyDetails
        property.setName(propertyDetails.getName());
        property.setDescription(propertyDetails.getDescription());
        property.setAddress(propertyDetails.getAddress());
        property.setPricePerNight(propertyDetails.getPricePerNight());
        property.setNumberOfBedrooms(propertyDetails.getNumberOfBedrooms());
        property.setNumberOfBathrooms(propertyDetails.getNumberOfBathrooms());
        property.setAvailable(propertyDetails.isAvailable());
        property.setDrinkAllowed(propertyDetails.isDrinkAllowed());
        property.setPetAllowed(propertyDetails.isPetAllowed());
        property.setMaxCheckoutTimeInNights(propertyDetails.getMaxCheckoutTimeInNights());
        property.setExtraCharges(propertyDetails.getExtraCharges());

        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(Long id) throws Exception {

        Property property = getPropertyById(id);

        propertyRepository.delete(property);
    }

    @Override
    public List<Property> getPropertiesByUser(User user) {
        return propertyRepository.findByOwner(user);
    }
}
