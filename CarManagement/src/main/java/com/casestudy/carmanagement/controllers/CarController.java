package com.casestudy.carmanagement.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.carmanagement.entities.Car;
import com.casestudy.carmanagement.repositories.CarRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {
	
	@Autowired
	private CarRepository carRepo;
	@PostMapping("/addCar")
	    public Car addCar(
	            @RequestParam("carModel") String carModel,
	            @RequestParam("location") String location,
	            @RequestParam("price") Double price,
	            @RequestParam("startDate") String startDate,
	            @RequestParam("endDate") String endDate,
	            @RequestParam("image") MultipartFile image
	    ) throws IOException {
	        Car car = new Car();
	        car.setCarModel(carModel);
	        car.setLocation(location);
	        car.setPrice(price);
	        car.setStartDate(startDate);
	        car.setEndDate(endDate);
	        car.setImage(image.getBytes());
	        return carRepo.save(car);
	    }
	
	@GetMapping("/car/{carModel}")
	public Car getCarByModel(@PathVariable String carModel) {
		return carRepo.findByCarModel(carModel);
	}

}
