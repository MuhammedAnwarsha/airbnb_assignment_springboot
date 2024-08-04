package com.airbnb.controller;

import com.airbnb.model.Property;
import com.airbnb.model.User;
import com.airbnb.service.PropertyService;
import com.airbnb.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserServices userService;


    @PostMapping("/properties")
    public ResponseEntity<Property> createProperty(@RequestHeader("Authorization") String jwt,
                                                   @RequestBody Property property) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(propertyService.createProperty(user, property), HttpStatus.CREATED);
    }


    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAllProperty(@RequestHeader("Authorization") String jwt) {

        return new ResponseEntity<>(propertyService.getAllProperties(), HttpStatus.OK);
    }


    @GetMapping("/properties/{id}")
    public ResponseEntity<?> getPropertyById(@RequestHeader("Authorization") String jwt,
                                             @PathVariable Long id) throws Exception {

        try {

            Property property = propertyService.getPropertyById(id);
            return new ResponseEntity<>(property, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/properties/{id}")
    public ResponseEntity<Object> updateProperty(@RequestHeader("Authorization") String jwt,
                                                 @PathVariable Long id,
                                                 @RequestBody Property propertyDetails) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Property property = propertyService.getPropertyById(id);

        // check if the current user is the owner of the property
        if (!property.getOwner().equals(user)) {
            return new ResponseEntity<>(Map.of("message", "You are not authorized to update this property"), HttpStatus.FORBIDDEN);
        }

        Property updatedProperty = propertyService.updateProperty(id, propertyDetails);
        return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
    }


    @DeleteMapping("/properties/{id}")
    public ResponseEntity<String> deleteProperty(@RequestHeader("Authorization") String jwt,
                                                 @PathVariable Long id) throws Exception {
        try {
            User user = userService.findUserProfileByJwt(jwt);

            Property property = propertyService.getPropertyById(id);

            // check if the current user is the owner of the property
            if (!property.getOwner().equals(user)) {
                throw new Exception("You are not authorized to delete this property");
            }

            propertyService.deleteProperty(id);
            return new ResponseEntity<>("Property deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/users/{userId}/properties")
    public ResponseEntity<Object> getUserProperties(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable Long userId) throws Exception {

        try {
            User user = userService.findUserById(userId);
            List<Property> properties = propertyService.getPropertiesByUser(user);
            return new ResponseEntity<>(properties, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
