package ordination.ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PNTest {
    private LocalDate startDen;
    private LocalDate slutDen;
    private PN pn;

    @BeforeEach
    void setup() {
        LocalDate startDen = LocalDate.of(2023,1,1);
        LocalDate slutDen = LocalDate.of( 2023,1,10);
        pn = new PN(startDen, slutDen, 2);
    }

    @Test
    void TC1_givDosis_dato¤01¤01¤2023() {
        // Arrange
        LocalDate givDen = LocalDate.of(  2023,1,1);

        // Act
        boolean faktiskeBoolean = pn.givDosis(givDen);

        // Assert
        boolean forventetBoolean = true;
        assertEquals(forventetBoolean, faktiskeBoolean);
    }

    @Test
    void TC2_givDosis_dato¤$10¤01¤2023() {
        // Arrange
        LocalDate givDen = LocalDate.of(  2023,1,10);

        // Act
        boolean faktiskeBoolean = pn.givDosis(givDen);

        // Assert
        boolean forventetBoolean = true;
        assertEquals(forventetBoolean, faktiskeBoolean);
    }

    @Test
    void TC3_givDosis_datoNull() {
        // Arrange
        LocalDate givDen = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pn.givDosis(givDen);
        });
        assertEquals(exception.getMessage(), "Dato må ikke være null");
    }

    @Test
    void TC2_givDosis_dato¤30¤12¤2022() {
        // Arrange
        LocalDate givDen = LocalDate.of(  2022,12,30);

        // Act
        boolean faktiskeBoolean = pn.givDosis(givDen);

        // Assert
        boolean forventetBoolean = false;
        assertEquals(forventetBoolean, faktiskeBoolean);
    }

    @Test
    void TC2_givDosis_dato¤11¤01¤2023() {
        // Arrange
        LocalDate givDen = LocalDate.of(  2023,1,11);

        // Act
        boolean faktiskeBoolean = pn.givDosis(givDen);

        // Assert
        boolean forventetBoolean = false;
        assertEquals(forventetBoolean, faktiskeBoolean);
    }





}