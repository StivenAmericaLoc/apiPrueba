package com.api.ubika.device.service;

import org.springframework.http.ResponseEntity;

public interface IDeviceService {
	
	ResponseEntity<Object> findById(Integer id);

}
