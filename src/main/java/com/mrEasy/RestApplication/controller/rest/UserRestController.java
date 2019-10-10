package com.mrEasy.RestApplication.controller.rest;

import com.mrEasy.RestApplication.UserProfile;
import com.mrEasy.RestApplication.entity.Apartment;
import com.mrEasy.RestApplication.entity.House;
import com.mrEasy.RestApplication.entity.Role;
import com.mrEasy.RestApplication.entity.User;
import com.mrEasy.RestApplication.repo.ApartmentRepo;
import com.mrEasy.RestApplication.repo.HouseRepo;
import com.mrEasy.RestApplication.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserRepo userRepo;
    private final ApartmentRepo apartmentRepo;
    private final HouseRepo houseRepo;

    @Autowired
    public UserRestController(UserRepo userRepo, ApartmentRepo apartmentRepo, HouseRepo houseRepo) {
        this.userRepo = userRepo;
        this.apartmentRepo = apartmentRepo;
        this.houseRepo = houseRepo;
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return null;
        } else {
            House house = houseRepo.findByAddress(user.getAddress());
            Apartment apartment;
            if (house == null) {
                house = new House(user.getAddress());
                houseRepo.save(house);
                apartment = new Apartment(house, user.getApartmentNumber());
                apartmentRepo.save(apartment);
            } else {
                apartment = apartmentRepo.findByHouseAndApartmentNumber(house, user.getApartmentNumber());
                if (apartment == null) {
                    apartment = new Apartment(house, user.getApartmentNumber());
                    apartmentRepo.save(apartment);
                }
            }

            user.setApartment(apartment);
            user.setRoles(Collections.singleton(Role.USER));

            return userRepo.save(user);
        }
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable("id") User userFromDb,
                           @RequestBody User user) {
        User tempUser = userRepo.findByUsername(user.getUsername());
        if (tempUser != null && tempUser.getUserId() != userFromDb.getUserId()) {
            return null;
        } else {
            House house = houseRepo.findByAddress(user.getAddress());
            Apartment apartment;
            if (house == null) {
                house = new House(user.getAddress());
                houseRepo.save(house);
                apartment = new Apartment(house, user.getApartmentNumber());
                apartmentRepo.save(apartment);
            } else {
                apartment = apartmentRepo.findByHouseAndApartmentNumber(house, user.getApartmentNumber());
                if (apartment == null) {
                    apartment = new Apartment(house, user.getApartmentNumber());
                    apartmentRepo.save(apartment);
                }
            }

            user.setApartment(apartment);

            BeanUtils.copyProperties(user, userFromDb, "userId", "roles");

            return userRepo.save(userFromDb);
        }
    }
}
