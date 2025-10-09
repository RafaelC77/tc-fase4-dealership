package tech.challenge.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.challenge.dtos.CarSalesDto;
import tech.challenge.dtos.CarSoldRequestDto;
import tech.challenge.entities.CarEntity;
import tech.challenge.enums.CarStatus;
import tech.challenge.interfaces.SalesRestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class CarServiceTest {

    @Inject
    CarService carService;

    @InjectMock
    @RestClient
    SalesRestClient salesRestClient;

    private CarEntity testCar;
    private UUID testCarId;

    @BeforeEach
    void setUp() {
//        testCarId = UUID.randomUUID();
        testCar = new CarEntity();
//        testCar.setCarId(testCarId);
        testCar.setBrand("Toyota");
        testCar.setModel("Corolla");
        testCar.setYear(2023);
        testCar.setPrice(100000.0);
//        testCar.setStatus(CarStatus.AVAILABLE);

        // Limpar qualquer interação anterior no mock
        Mockito.reset(salesRestClient);
    }

    @Test
    @Transactional
    void testCreateCar_Success() {
        // Arrange
        Response mockResponse = Response.ok().build();
        when(salesRestClient.saveCar(any(CarSalesDto.class))).thenReturn(mockResponse);

        // Act
        CarEntity result = carService.createCar(testCar);

        // Assert
        assertNotNull(result);
        assertEquals(testCar.getBrand(), result.getBrand());
        assertEquals(testCar.getModel(), result.getModel());
        assertEquals(testCar.getYear(), result.getYear());
        assertEquals(testCar.getPrice(), result.getPrice());

        // Verifica se o salesRestClient foi chamado
        verify(salesRestClient, times(1)).saveCar(any(CarSalesDto.class));
    }

    @Test
    @Transactional
    void testCreateCar_SalesRestClientThrowsException() {
        // Arrange
        when(salesRestClient.saveCar(any(CarSalesDto.class)))
                .thenThrow(new RuntimeException("Erro na comunicação com o serviço de vendas"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carService.createCar(testCar);
        });

        assertNotNull(exception);
        verify(salesRestClient, times(1)).saveCar(any(CarSalesDto.class));
    }

    @Test
    @Transactional
    void testFindAll() {
        // Arrange
        CarEntity car1 = new CarEntity();
//        car1.setCarId(UUID.randomUUID());
        car1.setBrand("Honda");
        car1.setModel("Civic");
        car1.setYear(2022);
        car1.setPrice(90000.0);
        car1.persist();

        CarEntity car2 = new CarEntity();
//        car2.setCarId(UUID.randomUUID());
        car2.setBrand("Ford");
        car2.setModel("Focus");
        car2.setYear(2021);
        car2.setPrice(80000.0);
        car2.persist();

        // Act
        List<CarEntity> result = carService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 2);
    }

    @Test
    @Transactional
    void testFindById_Found() {
        // Arrange
        testCar.persist();
        UUID testCarId = testCar.getCarId();

        // Act
        CarEntity result = carService.findById(testCarId);

        // Assert
        assertNotNull(result);
        assertEquals(testCarId, result.getCarId());
        assertEquals(testCar.getBrand(), result.getBrand());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act
        CarEntity result = carService.findById(nonExistentId);

        // Assert
        assertNull(result);
    }

    @Test
    @Transactional
    void testUpdateCar_Success() {
        // Arrange
        testCar.persist();
        UUID testCarId = testCar.getCarId();

        CarEntity updateData = new CarEntity();
        updateData.setBrand("Toyota Updated");
        updateData.setModel("Corolla XEI");
        updateData.setYear(2024);
        updateData.setPrice(110000.0);

        // Act
        CarEntity result = carService.updateCar(testCarId, updateData);

        // Assert
        assertNotNull(result);
        assertEquals("Toyota Updated", result.getBrand());
        assertEquals("Corolla XEI", result.getModel());
        assertEquals(2024, result.getYear());
        assertEquals(110000.0, result.getPrice());
        assertEquals(CarStatus.AVAILABLE, result.getStatus());
    }

    @Test
    void testUpdateCar_CarNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        CarEntity updateData = new CarEntity();
        updateData.setBrand("Toyota");

        // Act
        CarEntity result = carService.updateCar(nonExistentId, updateData);

        // Assert
        assertNull(result);
    }

    @Test
    @Transactional
    void testUpdateCar_CarAlreadySold() {
        // Arrange
        testCar.setStatus(CarStatus.SOLD);
        testCar.persist();
        UUID carId = testCar.getCarId();

        CarEntity updateData = new CarEntity();
        updateData.setBrand("Toyota Updated");

        // Act
        CarEntity result = carService.updateCar(carId, updateData);

        // Assert
        assertNull(result);
    }

    @Test
    @Transactional
    void testSetCarAsSold_Success() {
        // Arrange
        testCar.persist();

        LocalDateTime saleDate = LocalDateTime.now();
        CarSoldRequestDto request = new CarSoldRequestDto(testCar.getCarId().toString(), saleDate);

        // Act
        CarEntity result = carService.setCarAsSold(request);

        // Assert
        assertNotNull(result);
        assertEquals(CarStatus.SOLD, result.getStatus());
        assertEquals(saleDate, result.getSaleDate());
    }

    @Test
    void testSetCarAsSold_CarNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        CarSoldRequestDto request = new CarSoldRequestDto(
                nonExistentId.toString(),
                LocalDateTime.now()
        );

        // Act
        CarEntity result = carService.setCarAsSold(request);

        // Assert
        assertNull(result);
    }
}