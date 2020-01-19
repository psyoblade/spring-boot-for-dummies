package me.suhyuk.springcore.services;

import me.suhyuk.springcore.entities.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVehicleService {

    private VehicleDetails vehicleDetails;

    public VehicleDetails getVehicleDetails(String style) {
        return vehicleDetails;
    }
}
