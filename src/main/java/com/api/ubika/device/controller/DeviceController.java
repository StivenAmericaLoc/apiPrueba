package com.api.ubika.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ubika.device.service.IDeviceService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/device/")
public class DeviceController {
	
	@Autowired
	private IDeviceService deviceService;
	
	@GetMapping("{idDevice}")
	public  ResponseEntity<Object> findById(@PathVariable Integer idDevice) {
		return deviceService.findById(idDevice);
	}
	
}
