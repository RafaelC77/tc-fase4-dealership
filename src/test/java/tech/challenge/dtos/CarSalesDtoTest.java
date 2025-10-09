
package tech.challenge.dtos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CarSalesDtoTest {

    @Test
    void testNoArgsConstructor() {
        CarSalesDto dto = new CarSalesDto();

        assertNotNull(dto);
        assertNull(dto.getCarId());
        assertNull(dto.getBrand());
        assertNull(dto.getModel());
        assertNull(dto.getYear());
        assertNull(dto.getPrice());
        assertNull(dto.getStatus());
    }

    @Test
    void testAllArgsConstructor() {
        CarSalesDto dto = new CarSalesDto(
                "CAR-001",
                "Toyota",
                "Corolla",
                2023,
                85000.00,
                "disponivel"
        );

        assertEquals("CAR-001", dto.getCarId());
        assertEquals("Toyota", dto.getBrand());
        assertEquals("Corolla", dto.getModel());
        assertEquals(2023, dto.getYear());
        assertEquals(85000.00, dto.getPrice());
        assertEquals("disponivel", dto.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        CarSalesDto dto = new CarSalesDto();

        dto.setCarId("CAR-002");
        dto.setBrand("Honda");
        dto.setModel("Civic");
        dto.setYear(2024);
        dto.setPrice(95000.00);
        dto.setStatus("vendido");

        assertEquals("CAR-002", dto.getCarId());
        assertEquals("Honda", dto.getBrand());
        assertEquals("Civic", dto.getModel());
        assertEquals(2024, dto.getYear());
        assertEquals(95000.00, dto.getPrice());
        assertEquals("vendido", dto.getStatus());
    }

    @Test
    void testToString() {
        CarSalesDto dto = new CarSalesDto(
                "CAR-003",
                "Ford",
                "Focus",
                2022,
                70000.00,
                "disponivel"
        );

        String result = dto.toString();

        assertNotNull(result);
        assertTrue(result.contains("CarSalesDto"));
        assertTrue(result.contains("carId='CAR-003'"));
        assertTrue(result.contains("brand='Ford'"));
        assertTrue(result.contains("model='Focus'"));
        assertTrue(result.contains("year=2022"));
        assertTrue(result.contains("price=70000"));
        assertTrue(result.contains("status='disponivel'"));
    }

    @Test
    void testAllFieldsIndependently() {
        CarSalesDto dto = new CarSalesDto();

        // Testa carId
        dto.setCarId("TEST-001");
        assertEquals("TEST-001", dto.getCarId());

        // Testa brand
        dto.setBrand("Chevrolet");
        assertEquals("Chevrolet", dto.getBrand());

        // Testa model
        dto.setModel("Onix");
        assertEquals("Onix", dto.getModel());

        // Testa year
        dto.setYear(2025);
        assertEquals(2025, dto.getYear());

        // Testa price
        dto.setPrice(75000.50);
        assertEquals(75000.50, dto.getPrice());

        // Testa status
        dto.setStatus("reservado");
        assertEquals("reservado", dto.getStatus());
    }
}