package ordination.ordination;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligSkaevTest {

    private DagligSkaev dagligSkaev;

    @Test
    void opretDosis_TC1() {

        //Arrange
        dagligSkaev = new DagligSkaev(LocalDate.of(2023, 02, 13), LocalDate.of(2023, 02, 15));

        //Act
        Dosis dosis = dagligSkaev.opretDosis(LocalTime.of(9, 30), 0);

        //Assert
        assertTrue(dagligSkaev.getDoser().contains(dosis));
    }

    @Test
        void opretDosis_TC2() {

            //Arrange
            dagligSkaev = new DagligSkaev(LocalDate.of(2023, 02, 13), LocalDate.of(2023, 02, 15));

            //Act
            Dosis dosis = dagligSkaev.opretDosis(LocalTime.of(10, 30), 2);

            //Assert
            assertTrue(dagligSkaev.getDoser().contains(dosis));
        }

        @Test
        void opretDosis_TC3() {

            //Arrange
            dagligSkaev = new DagligSkaev(LocalDate.of(2023, 02, 13), LocalDate.of(2023, 02, 15));

            //Act
            Dosis dosis = dagligSkaev.opretDosis(LocalTime.of(17,00),-1);

            //Assert
            assertTrue(dagligSkaev.getDoser().contains(dosis));
        }

        @Test
        void opretDosis_TC4() {

            //Arrange
            dagligSkaev = new DagligSkaev(LocalDate.of(2023, 02, 13), LocalDate.of(2023, 02, 15));

            //Act
            Dosis dosis = dagligSkaev.opretDosis(null, 1);

            //Assert
            assertTrue(dagligSkaev.getDoser().contains(dosis));

        }

    }