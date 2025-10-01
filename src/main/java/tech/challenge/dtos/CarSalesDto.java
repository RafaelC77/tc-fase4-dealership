package tech.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarSalesDto {

    @JsonProperty("carId")
    private String carId;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("model")
    private String model;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("status")
    private String status;

    public CarSalesDto() {}

    public CarSalesDto(String carId, String brand, String model, Integer year,
                       Double price, String status) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("CarSalesDto{carId='%s', brand='%s', model='%s', year=%d, price=%f, status='%s'}", 
                           carId, brand, model, year, price, status);
    }
}