package tech.challenge.dtos;

import java.time.LocalDateTime;

public class CarSoldRequestDto {
    private String carId;
    private LocalDateTime saleDate;

    public CarSoldRequestDto() {}

    public CarSoldRequestDto(String carId, LocalDateTime saleDate) {
        this.carId = carId;
        this.saleDate = saleDate;
    }

    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public LocalDateTime getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(LocalDateTime saleDate) {
    this.saleDate = saleDate;
}
}
