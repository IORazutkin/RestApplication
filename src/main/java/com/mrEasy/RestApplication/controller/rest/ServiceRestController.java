package com.mrEasy.RestApplication.controller.rest;

import com.mrEasy.RestApplication.entity.Service;
import com.mrEasy.RestApplication.repo.ServiceRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceRestController {
    private ServiceRepo serviceRepo;

    @Autowired
    public ServiceRestController(ServiceRepo serviceRepo) {
        this.serviceRepo = serviceRepo;
    }

    @GetMapping
    public List<Service> getList() {
        return serviceRepo.findAll(Sort.by("title"));
    }

    @PostMapping
    public Service add(@RequestBody Service service) {
        return serviceRepo.save(service);
    }

    @PutMapping("{id}")
    public Service update(@PathVariable("id") Service serviceFromDb,
                          @RequestBody Service service) {
        BeanUtils.copyProperties(service, serviceFromDb, "serviceId");
        return serviceRepo.save(serviceFromDb);
    }
}