package ordination.ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligSkaevTest {

    private DagligSkaev dagligSkaev;

    @BeforeEach
    void setup() {dagligSkaev = new DagligSkaev(LocalDate.of(2023, 02, 13), LocalDate.of(2023, 02, 15));}

    @Test
    void opretDosis_test1() {

        //Arrange
        //dagligSkaev.opretDosis();

        //Act
        dagligSkaev.opretDosis(LocalTime.of(9, 30), 3);

        //Assert
        //assertTrue(dagligSkaev.getDoser().contains());

    }

}