package me.suhyuk.springcore.controllers;

import me.suhyuk.springcore.entities.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @Autowired
    private VehicleDetails vehicleDetails;

    @RequestMapping("/sboot/vehicle")
    public String getVehicle() {
        return vehicleDetails.getVehicles().toString();
    }
}
