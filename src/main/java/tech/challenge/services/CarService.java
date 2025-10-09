package tech.challenge.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.challenge.dtos.CarSalesDto;
import tech.challenge.dtos.CarSoldRequestDto;
import tech.challenge.entities.CarEntity;
import tech.challenge.enums.CarStatus;
import tech.challenge.interfaces.SalesRestClient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CarService {

    @Inject
    @RestClient
    SalesRestClient salesRestClient;

    public CarEntity createCar(CarEntity carEntity) {

        CarEntity.persist(carEntity);

        try {
            CarSalesDto salesDto = new CarSalesDto(
                    carEntity.getCarId().toString(),
                    carEntity.getBrand(),
                    carEntity.getModel(),
                    carEntity.getYear(),
                    carEntity.getPrice(),
                    carEntity.getStatus().name()
            );

            salesRestClient.saveCar(salesDto);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return carEntity;
    }

    public List<CarEntity> findAll() {
        return CarEntity.findAll().list();
    }

    public CarEntity findById(UUID id) {
        return CarEntity.findById(id);
    }

    public CarEntity updateCar(UUID id, CarEntity updateCar) {
        CarEntity existingCar = CarEntity.findById(id);
        if (existingCar != null && existingCar.getStatus() == CarStatus.AVAILABLE) {
            existingCar.setBrand(updateCar.getBrand());
            existingCar.setModel(updateCar.getModel());
            existingCar.setYear(updateCar.getYear());
            existingCar.setPrice(updateCar.getPrice());

            return existingCar;
        }
        return null;
    }

    public CarEntity setCarAsSold(CarSoldRequestDto request) {
        UUID carId = UUID.fromString(request.getCarId());
        CarEntity existingCar = CarEntity.findById(carId);
        if (existingCar != null) {
            existingCar.setStatus(CarStatus.SOLD);
            existingCar.setSaleDate(request.getSaleDate());
        }
        return existingCar;
    }

}