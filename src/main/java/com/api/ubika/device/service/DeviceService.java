package com.api.ubika.device.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.americaloc.api.APIOperationResult;
import com.americaloc.api.GenericDeviceInfo;
import com.americaloc.api.admin.AdminAPIController;
import com.api.ubika.device.object.DeviceModel;

@Service
public class DeviceService implements IDeviceService {
	
	@Value("${my.property.url}")
	private String url;
	
	@Value("${my.property.user}")
	private String user;
	
	@Value("${my.property.pass}")
	private String pass;

	@Override
	public ResponseEntity<Object> findById(Integer id) {
		try {
			//String token = "df1c3473-0da7-434b-9e82-5d86d5e21258";
			String token = "a2877a11-c679-4bb6-b0db-1902cdbea698";
			AdminAPIController api = new AdminAPIController();
			if (api.setDBParameters(url, user, pass)) {
				APIOperationResult result = api.getGenericDeviceInfo(token, id);
				GenericDeviceInfo device = (GenericDeviceInfo) result.data;
				DeviceModel model = new DeviceModel();
				model.setIdDevice(device.deviceId);
				model.setNombre(device.deviceName);
				return new ResponseEntity<Object>(model, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.CONFLICT);
			}		
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
