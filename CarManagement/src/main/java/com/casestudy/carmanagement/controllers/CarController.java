package com.casestudy.carmanagement.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.carmanagement.entities.Car;
import com.casestudy.carmanagement.repositories.CarRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CarController {
	
	
	
	@Autowired
	private CarRepository carRepo;
	
	@GetMapping("/test")
	public String test() {
		return "Test";
	}
	
	@PostMapping("/api/cars/add")
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
	
	@GetMapping("/api/cars")
	public List<Car> getAllCars(){
		return carRepo.findAll();
	}
	
	@GetMapping("/api/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	 @PutMapping("api/cars/edit/{id}")
	    public ResponseEntity<Car> updateCar(@PathVariable("id") Long id,
	                                         @RequestParam("carModel") String carModel,
	                                         @RequestParam("location") String location,
	                                         @RequestParam("price") double price,
	                                         @RequestParam("startDate") String startDate,
	                                         @RequestParam("endDate") String endDate,
	                                         @RequestParam("image") MultipartFile image) {
	        Optional<Car> existingCar = carRepo.findById(id);
	        if (existingCar.isPresent()) {
	            Car car = existingCar.get();
	            car.setCarModel(carModel);
	            car.setLocation(location);
	            car.setPrice(price);
	            car.setStartDate(startDate);
	            car.setEndDate(endDate);
	            try {
	                car.setImage(image.getBytes());
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            carRepo.save(car);
	            return ResponseEntity.ok(car);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	 
	 @DeleteMapping("/api/cars/delete/{id}")
	    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id) {
	        Optional<Car> car = carRepo.findById(id);
	        if (car.isPresent()) {
	            carRepo.delete(car.get());
	            return ResponseEntity.noContent().build(); // Successfully deleted, no content returned
	        } else {
	            return ResponseEntity.notFound().build(); // Car not found
	        }
	    }


}
