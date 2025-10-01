package tech.challenge.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.challenge.entities.CarEntity;
import tech.challenge.services.CarService;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CarControllerTest {
    
    @InjectMock
    CarService carService;
    
    @Test
    public void testCreateCarEndpoint() {
        CarEntity newCar = new CarEntity();
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        newCar.setYear(2020);
        newCar.setPrice(100000.0);
        
        // Mock da resposta do serviço
        CarEntity mockResponse = new CarEntity();
        mockResponse.setCarId(UUID.randomUUID());
        mockResponse.setBrand("Toyota");
        mockResponse.setModel("Corolla");
        mockResponse.setYear(2020);
        mockResponse.setPrice(100000.0);
        
        Mockito.when(carService.createCar(Mockito.any(CarEntity.class)))
               .thenReturn(mockResponse);

        given()
                .contentType("application/json")
                .body(newCar)
                .when().post("/cars")
                .then()
                .statusCode(201)
                .body("brand", equalTo("Toyota"))
                .body("model", equalTo("Corolla"));
    }

    @Test
    public void testFindAllCarsEndpoint() {
        given()
                .when().get("/cars")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateCarEndpoint() {
        var carId = "550e8400-e29b-41d4-a716-446655440000";

        CarEntity newCar = new CarEntity();
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        newCar.setYear(2020);
        newCar.setPrice(100000.0);
        newCar.setCarId(UUID.fromString(carId));

        CarEntity updatedCar = new CarEntity();
        updatedCar.setBrand("Honda");
        updatedCar.setModel("Civic");
        updatedCar.setYear(2020);
        updatedCar.setPrice(100000.0);
        
        // Mock da resposta do serviço
        CarEntity mockResponse = new CarEntity();
        mockResponse.setCarId(UUID.fromString(carId));
        mockResponse.setBrand("Honda");
        mockResponse.setModel("Civic");
        mockResponse.setYear(2020);
        mockResponse.setPrice(100000.0);
        
        Mockito.when(carService.updateCar(Mockito.eq(UUID.fromString(carId)), Mockito.any(CarEntity.class)))
               .thenReturn(mockResponse);

        given()
                .contentType("Application/json")
                .body(updatedCar)
                .when().put("/cars/" + carId)
                .then()
                .statusCode(200)
                .body("brand", equalTo("Honda"))
                .body("model", equalTo("Civic"));
    }

    @Test
    public void testUpdateCarEndpointWithNonExistentId() {
        var carId = java.util.UUID.randomUUID();

        CarEntity updatedCar = new CarEntity();
        updatedCar.setBrand("Honda");
        updatedCar.setModel("Civic");
        updatedCar.setYear(2020);
        updatedCar.setPrice(100000.0);
        
        Mockito.when(carService.updateCar(Mockito.eq(carId), Mockito.any(CarEntity.class)))
               .thenReturn(null);

        given()
                .contentType("Application/json")
                .body(updatedCar)
                .when().put("/cars/" + carId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindCarByIdEndpoint() {
        var carId = "550e8400-e29b-41d4-a716-446655440000";
        
        // Mock da resposta do serviço
        CarEntity mockResponse = new CarEntity();
        mockResponse.setCarId(UUID.fromString(carId));
        mockResponse.setBrand("Honda");
        mockResponse.setModel("Civic");
        mockResponse.setYear(2020);
        mockResponse.setPrice(100000.0);
        
        Mockito.when(carService.findById(UUID.fromString(carId)))
               .thenReturn(mockResponse);

        given()
                .when().get("/cars/" + carId)
                .then()
                .statusCode(200)
                .body("brand", equalTo("Honda"))
                .body("model", equalTo("Civic"));
    }
}
