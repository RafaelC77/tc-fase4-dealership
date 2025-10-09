package tech.challenge.dtos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CarSoldRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        CarSoldRequestDto dto = new CarSoldRequestDto();

        assertNotNull(dto);
        assertNull(dto.getCarId());
        assertNull(dto.getSaleDate());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime saleDate = LocalDateTime.of(2025, 10, 8, 14, 30, 0);
        CarSoldRequestDto dto = new CarSoldRequestDto("CAR-001", saleDate);

        assertEquals("CAR-001", dto.getCarId());
        assertEquals(saleDate, dto.getSaleDate());
    }

    @Test
    void testSettersAndGetters() {
        CarSoldRequestDto dto = new CarSoldRequestDto();
        LocalDateTime saleDate = LocalDateTime.of(2025, 10, 8, 15, 45, 30);

        dto.setCarId("CAR-002");
        dto.setSaleDate(saleDate);

        assertEquals("CAR-002", dto.getCarId());
        assertEquals(saleDate, dto.getSaleDate());
    }

    @Test
    void testCarIdIndependently() {
        CarSoldRequestDto dto = new CarSoldRequestDto();

        assertNull(dto.getCarId());

        dto.setCarId("TEST-001");
        assertEquals("TEST-001", dto.getCarId());

        dto.setCarId("TEST-002");
        assertEquals("TEST-002", dto.getCarId());

        dto.setCarId(null);
        assertNull(dto.getCarId());
    }

    @Test
    void testSaleDateIndependently() {
        CarSoldRequestDto dto = new CarSoldRequestDto();

        assertNull(dto.getSaleDate());

        LocalDateTime date1 = LocalDateTime.of(2025, 1, 15, 10, 0);
        dto.setSaleDate(date1);
        assertEquals(date1, dto.getSaleDate());

        LocalDateTime date2 = LocalDateTime.of(2025, 12, 31, 23, 59);
        dto.setSaleDate(date2);
        assertEquals(date2, dto.getSaleDate());

        dto.setSaleDate(null);
        assertNull(dto.getSaleDate());
    }


    @Test
    void testConstructorWithNullValues() {
        CarSoldRequestDto dto = new CarSoldRequestDto(null, null);

        assertNotNull(dto);
        assertNull(dto.getCarId());
        assertNull(dto.getSaleDate());
    }

    @Test
    void testSaleDateWithDifferentTimeFormats() {
        CarSoldRequestDto dto = new CarSoldRequestDto();

        // Testa com hora exata
        LocalDateTime exactTime = LocalDateTime.of(2025, 6, 15, 14, 30, 45);
        dto.setSaleDate(exactTime);
        assertEquals(exactTime, dto.getSaleDate());

        // Testa meia-noite
        LocalDateTime midnight = LocalDateTime.of(2025, 6, 15, 0, 0, 0);
        dto.setSaleDate(midnight);
        assertEquals(midnight, dto.getSaleDate());

        // Testa final do dia
        LocalDateTime endOfDay = LocalDateTime.of(2025, 6, 15, 23, 59, 59);
        dto.setSaleDate(endOfDay);
        assertEquals(endOfDay, dto.getSaleDate());
    }

    @Test
    void testMultipleSettersCallsDoNotInterfere() {
        CarSoldRequestDto dto = new CarSoldRequestDto();
        LocalDateTime date1 = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2025, 12, 31, 20, 0);

        // Define carId primeiro
        dto.setCarId("CAR-100");
        assertEquals("CAR-100", dto.getCarId());
        assertNull(dto.getSaleDate());

        // Define saleDate
        dto.setSaleDate(date1);
        assertEquals("CAR-100", dto.getCarId());
        assertEquals(date1, dto.getSaleDate());

        // Altera carId - saleDate não deve ser afetado
        dto.setCarId("CAR-200");
        assertEquals("CAR-200", dto.getCarId());
        assertEquals(date1, dto.getSaleDate());

        // Altera saleDate - carId não deve ser afetado
        dto.setSaleDate(date2);
        assertEquals("CAR-200", dto.getCarId());
        assertEquals(date2, dto.getSaleDate());
    }

    @Test
    void testImmutableLocalDateTime() {
        LocalDateTime originalDate = LocalDateTime.of(2025, 5, 20, 12, 0);
        CarSoldRequestDto dto = new CarSoldRequestDto("CAR-500", originalDate);

        // LocalDateTime é imutável, então modificar a referência original
        // não deve afetar o DTO
        LocalDateTime modifiedDate = originalDate.plusDays(10);

        assertEquals(LocalDateTime.of(2025, 5, 20, 12, 0), dto.getSaleDate());
        assertNotEquals(modifiedDate, dto.getSaleDate());
    }
}