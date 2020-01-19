package me.suhyuk.springcore.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
public class VehicleDetails implements Serializable, Cloneable {

    private List<String> vehicles;

    public VehicleDetails(String... args) {
        this.vehicles = Arrays.asList(args);
    }

    @Bean
    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    public String getVehicleDetails() {
        return toString();
    }

    @Override
    public String toString() {
        return String.join(",", vehicles);
    }
}
