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
        this.dagligFast = new DagligFast(LocalDate.of(2023,1,1),LocalDate.of(2022,1,10));
    }

    @Test
    @Order(1)
    void TC1_createDosis_antal0() {
        //Arrange
        LocalTime tid = LocalTime.NOON;
        double antal = 0;

        //Act
        dagligFast.createDosis(tid, antal);

        //Assert
        double expectedAntal = 0;
        double actualAntal = dagligFast.getDoser()[0].getAntal();
        assertEquals(expectedAntal,actualAntal);
    }
    @Test
    @Order(2)
    void TC2_createDosis_antal2() {
        //Arrange
        LocalTime tid = LocalTime.NOON;
        double antal = 2;

        //Act
        dagligFast.createDosis(tid, antal);

        //Assert
        double expectedAntal = 2;
        double actualAntal = dagligFast.getDoser()[0].getAntal();
        assertEquals(expectedAntal,actualAntal);
    }

    @Test
    @Order(3)
    void TC3_createDosis_antalNegativ1() {
        //Arrange
        LocalTime tid = LocalTime.NOON;
        double antal = -1;

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dagligFast.createDosis(tid,antal);
        });
        assertEquals(exception.getMessage(), "Antal skal være positiv");
    }

    @Test
    @Order(4)
    void TC4_createDosis_nullTid() {
        //Arrange
        LocalTime tid = null;
        double antal = 1;

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dagligFast.createDosis(tid,antal);
        });
        assertEquals(exception.getMessage(), "Tid må ikke være null");
    }

    @Test
    @Order(5)
    void TC5_createDosis_5Dosis() {
        //Arrange
        LocalTime tid = LocalTime.NOON;
        double antal = 1;
        dagligFast.createDosis(LocalTime.NOON,1);
        dagligFast.createDosis(LocalTime.NOON,1);
        dagligFast.createDosis(LocalTime.NOON,1);
        dagligFast.createDosis(LocalTime.NOON,1);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dagligFast.createDosis(tid,antal);
        });
        assertEquals(exception.getMessage(), "Der er allerede oprettet maks antal doser");
    }
}