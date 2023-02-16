package ordination.ordination;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DagligFastTest {
    private DagligFast dagligFast;

    @BeforeEach
    void setup() {
        LocalDate startDen = LocalDate.of(2023, 1, 10);
        LocalDate slutDen = LocalDate.of(2023, 1, 20);
        dagligFast = new DagligFast(startDen, slutDen);
    }

    @Test
    @Order(1)
    void TC1_createDosis_antal0() {
        // Arrange
        LocalTime givDen = LocalTime.of(8,0);

        // Act
        Dosis dosis = dagligFast.createDosis(givDen, 0);
        double faktiskeAntal = dagligFast.getDoser()[0].getAntal();
        boolean faktiskeBoolean = dagligFast.getDoser()[0].equals(dosis);

        // Assert
        double forventetAntal = 0;
        assertEquals(forventetAntal, faktiskeAntal);
        boolean forventetBoolean = true;
        assertEquals(forventetBoolean,faktiskeBoolean);
    }

    @Test
    @Order(2)
    void TC2_createDosis_antal2() {
        // Arrange
        LocalTime givDen = LocalTime.of(8,0);


        // Act
        Dosis dosis = dagligFast.createDosis(givDen, 2);
        double faktiskeAntal = dagligFast.getDoser()[0].getAntal();
        boolean faktiskeBoolean = dagligFast.getDoser()[0].equals(dosis);

        // Assert
        double forventetAntal = 2;
        assertEquals(forventetAntal, faktiskeAntal);
        boolean forventetBoolean = true;
        assertEquals(forventetBoolean,faktiskeBoolean);
    }

    @Test
    @Order(3)
    void TC3_createDosis_antalMinus1() {
        // Arrange
        LocalTime givDen = LocalTime.of(8,0);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dagligFast.createDosis(givDen, -1);
        });
        assertEquals(exception.getMessage(), "Antal skal være positiv");
    }

    @Test
    @Order(4)
    void TC4_createDosis_tidNull() {
        // Arrange
        LocalTime givDen = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dagligFast.createDosis(givDen, 0);
        });
        assertEquals(exception.getMessage(), "Tid må ikke være null");
    }

    @Test
    @Order(5)
    void TC5_createDosis_femDoseringer() {
        // Arrange
        LocalTime givDen = LocalTime.of(8,0);

        // Act & Assert
        dagligFast.createDosis(givDen, 2);
        dagligFast.createDosis(givDen, 2);
        dagligFast.createDosis(givDen, 2);
        dagligFast.createDosis(givDen, 2);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dagligFast.createDosis(givDen, 2);
        });
        assertEquals(exception.getMessage(), "Der er allerede oprettet maks antal doser");
    }

}