package ordination.controller;

import ordination.ordination.DagligFast;
import ordination.ordination.Laegemiddel;
import ordination.ordination.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setup() {
        controller = Controller.getTestController();
    }

    @Test
    void TC1_opretDagligFastOrdination_11012023() {
        //Arrange
        LocalDate startDen = LocalDate.of(2023,1,10);
        LocalDate slutDen = LocalDate.of(2023,1,11);
        Patient patient = new Patient("123456-7890","Fornavn Efternavn",60);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre",0.1,0.15,0.16,"Styk");
        double morgenAntal = 1;
        double middagAntal = 1;
        double aftenAntal = 1;
        double natAntal = 1;
        //Act
        DagligFast dagligFast = Controller.getController().opretDagligFastOrdination(startDen,slutDen,patient,laegemiddel,morgenAntal,middagAntal,aftenAntal,natAntal);

        //assert
        boolean expectedBoolean = true;
        boolean actualBoolean = patient.getOrdinationer().contains(dagligFast);
        assertTrue(actualBoolean);
    }

    @Test
    void TC2_opretDagligFastOrdination_09012023() {
        //Arrange
        LocalDate startDen = LocalDate.of(2023,1,10);
        LocalDate slutDen = LocalDate.of(2023,1,9);
        Patient patient = new Patient("123456-7890","Fornavn Efternavn",60);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre",0.1,0.15,0.16,"Styk");
        double morgenAntal = 1;
        double middagAntal = 1;
        double aftenAntal = 1;
        double natAntal = 1;

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.getController().opretDagligFastOrdination(startDen,slutDen,patient,laegemiddel,morgenAntal,middagAntal,aftenAntal,natAntal);
        });
        assertEquals(exception.getMessage(), "Slut dato er efter startdato");
    }

    @Test
    void TC3_opretDagligFastOrdination_morgenAntalNegativ1_11012023() {
        //Arrange
        LocalDate startDen = LocalDate.of(2023,1,10);
        LocalDate slutDen = LocalDate.of(2023,1,11);
        Patient patient = new Patient("123456-7890","Fornavn Efternavn",60);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre",0.1,0.15,0.16,"Styk");
        double morgenAntal = -1;
        double middagAntal = 1;
        double aftenAntal = 1;
        double natAntal = 1;

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.getController().opretDagligFastOrdination(startDen,slutDen,patient,laegemiddel,morgenAntal,middagAntal,aftenAntal,natAntal);
        });
        assertEquals(exception.getMessage(), "Antal skal være positiv");
    }

    //------------------------------------------------------------------------------------------------------------------

    @Test
    void TC1_anbefaletDosisPrDoegn_70kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 70);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act
        double faktiskeDosis = controller.anbefaletDosisPrDoegn(patient, laegemiddel);

        // Assert
        double forventetDosis = 10.5;
        assertEquals(forventetDosis, faktiskeDosis);
    }

    @Test
    void TC1_anbefaletDosisPrDoegn_24kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 24);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act
        double faktiskeDosis = controller.anbefaletDosisPrDoegn(patient, laegemiddel);

        // Assert
        double forventetDosis = 2.4;
        assertEquals(forventetDosis, faktiskeDosis);
    }

    @Test
    void TC1_anbefaletDosisPrDoegn_25kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 25);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act
        double faktiskeDosis = controller.anbefaletDosisPrDoegn(patient, laegemiddel);

        // Assert
        double forventetDosis = 3.75;
        assertEquals(forventetDosis, faktiskeDosis);
    }

    @Test
    void TC1_anbefaletDosisPrDoegn_120kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 120);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act
        double faktiskeDosis = controller.anbefaletDosisPrDoegn(patient, laegemiddel);

        // Assert
        double forventetDosis = 18;
        assertEquals(forventetDosis, faktiskeDosis);
    }

    @Test
    void TC1_anbefaletDosisPrDoegn_121kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 121);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act
        double faktiskeDosis = controller.anbefaletDosisPrDoegn(patient, laegemiddel);

        // Assert
        double forventetDosis = 19.36;
        assertEquals(forventetDosis, faktiskeDosis);
    }

    @Test
    void TC1_anbefaletDosisPrDoegn_minus1kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", -10);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.anbefaletDosisPrDoegn(patient, laegemiddel);
        });
        assertEquals(exception.getMessage(), "vægt skal være positive");
    }

    @Test
    void TC1_anbefaletDosisPrDoegn_70kg_laegemiddelEnhedPerKiloDoegnLetMinus0d15() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 70);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", -0.15, 0.15, 0.16, "Styk");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.anbefaletDosisPrDoegn(patient, laegemiddel);
        });
        assertEquals(exception.getMessage(), "enhedPrKgPrDoegnlet skal være positiv");
    }

    //------------------------------------------------------------------------------------------------------------------
}