package ordination.controller;

import ordination.ordination.*;
import ordination.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setup() {
        controller = Controller.getTestController();
    }

    @Test
    void TC1_opretPNOrdination_startDenErMindreEndSlutDen() {
        //Arrange
        LocalDate startDen = LocalDate.of(2023,1,10);
        LocalDate slutDen = LocalDate.of(2023,1,12);
        Patient patient = new Patient("121256-0512", "Jane Jensen", 63.4);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
        double antal = 2;

        //Act
        PN pn = controller.opretPNOrdination(startDen,slutDen,patient,laegemiddel,antal);

        //Assert
        assertTrue(patient.getOrdinationer().contains(pn));
    }

    @Test
    void TC2_opretPNOrdination_slutDenErMindreEndStartDen() {
        //Arrange
        LocalDate startDen = LocalDate.of(2023, 1, 10);
        LocalDate slutDen = LocalDate.of(2023, 1, 9);
        Patient patient = new Patient("121256-0512", "Jane Jensen", 63.4);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
        double antal = 2;

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.getController().opretPNOrdination(startDen,slutDen,patient,laegemiddel,antal);
        });
        assertEquals(exception.getMessage(), "Start dato skal komme inden slutdato");
    }

    @Test
    void TC3_opretPNOrdination_AntalErNegativ() {
        //Arrange
        LocalDate startDen = LocalDate.of(2023, 1, 10);
        LocalDate slutDen = LocalDate.of(2023, 1, 11);
        Patient patient = new Patient("121256-0512", "Jane Jensen", 63.4);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
        double antal = -2;

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Controller.getController().opretPNOrdination(startDen,slutDen,patient,laegemiddel,antal);
        });
        assertEquals(exception.getMessage(), "Antal må ikke være negativ");
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
        DagligFast dagligFast = controller.opretDagligFastOrdination(startDen,slutDen,patient,laegemiddel,morgenAntal,middagAntal,aftenAntal,natAntal);

        //assert
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
    void TC2_anbefaletDosisPrDoegn_24kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 24);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act
        double faktiskeDosis = controller.anbefaletDosisPrDoegn(patient, laegemiddel);

        // Assert
        double forventetDosis = 2.4;
        assertEquals(forventetDosis, faktiskeDosis,0.0001);
    }

    @Test
    void TC3_anbefaletDosisPrDoegn_25kg() {
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
    void TC4_anbefaletDosisPrDoegn_120kg() {
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
    void TC5_anbefaletDosisPrDoegn_121kg() {
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
    void TC6_anbefaletDosisPrDoegn_minus1kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", -10);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.anbefaletDosisPrDoegn(patient, laegemiddel);
        });
        assertEquals(exception.getMessage(), "Vægten på patienten er negativ");
    }

    @Test
    void TC7_anbefaletDosisPrDoegn_70kg_laegemiddelEnhedPerKiloDoegnLetMinus0d15() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 70);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", -0.15, 0.15, 0.16, "Styk");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.anbefaletDosisPrDoegn(patient, laegemiddel);
        });
        assertEquals(exception.getMessage(), "Lægemiddellets enhed pr kg pr døgn for let, normal eller tung er negativ");
    }

    //------------------------------------------------------------------------------------------------------------------
    @Test
    void TC1_AntalOrdinationerPrVaegtPrLaegemiddel_startErMindreEndSlut() {
        //Arrange
        double vægtStart = 25;
        double vægtSlut = 100;
        controller.createSomeObjects();
        Laegemiddel laegemiddel = controller.getAllLaegemidler().get(1);

        //Act
        int expected = controller.antalOrdinationerPrVægtPrLægemiddel(vægtStart,vægtSlut,laegemiddel);

        //Assert
        int actual = 3;
        assertEquals(expected,actual);
    }

    @Test
    void TC2_AntalOrdinationerPrVaegtPrLaegemiddel_slutErMindreEndStart() {
        //Arrange
        double vægtStart = 50;
        double vægtSlut = 40;
        controller.createSomeObjects();
        Laegemiddel laegemiddel = controller.getAllLaegemidler().get(1);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.antalOrdinationerPrVægtPrLægemiddel(vægtStart,vægtSlut,laegemiddel);
        });
        assertEquals(exception.getMessage(), "Vægt start må ikke være større end vægt slut");
    }

    //-------------------------------------------------------------------------------------------------
    @Test
    void opretDagligSkaevOrdination_TC1() { //startDen, slutDen, patient, laegemiddel, klokkeslæt, antalenheder

        //Arrange
        LocalDate startDen = LocalDate.of(2023, 02, 16);
        LocalDate slutDen = LocalDate.of(2023, 02, 26);
        Patient patient = new Patient("1234567899", "Jens Jensen", 80.0);
        Laegemiddel laegemiddel = new Laegemiddel("Paracetamol", 0.1, 0.15, 0.16, "Styk");
        LocalTime[] klokkeslet = new LocalTime[6];
        double[] antalEnheder = new double[6];

        //Act
        DagligSkaev dagligSkaev = controller.opretDagligSkaevOrdination(startDen, slutDen, patient, laegemiddel, klokkeslet, antalEnheder);

        //Assert
        assertTrue(patient.getOrdinationer().contains(dagligSkaev));
    }

    @Test
    void opretDagligSkaevOrdination_TC2() { //startDen, slutDen, patient, laegemiddel, klokkeslæt, antalenheder

        //Arrange
        LocalDate startDen = LocalDate.of(2023, 02, 16);
        LocalDate slutDen = LocalDate.of(2023, 02, 26);
        Patient patient = new Patient("1234567899", "Jens Jensen", 80.0);
        Laegemiddel laegemiddel = new Laegemiddel("Paracetamol", 0.1, 0.15, 0.16, "Styk");
        LocalTime[] klokkeslet = new LocalTime[6];
        double[] antalEnheder = new double[6];

        //Act
        DagligSkaev dagligSkaev = controller.opretDagligSkaevOrdination(startDen, slutDen, patient, laegemiddel, klokkeslet, antalEnheder);

        //Assert
        assertTrue(patient.getOrdinationer().contains(dagligSkaev));
    }


    @Test
    void opretDagligSkaevOrdination_TC3() {

        //Arrange
        LocalDate startDen = LocalDate.of(2023, 02, 16);
        LocalDate slutDen = LocalDate.of(2023, 02, 06);
        Patient patient = new Patient("1234567899", "Jens Jensen", 80.0);
        Laegemiddel laegemiddel = new Laegemiddel("Paracetamol", 0.1, 0.15, 0.16, "Styk");
        LocalTime[] klokkeslet = new LocalTime[6];
        double[] antalEnheder = new double[6];

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.opretDagligSkaevOrdination(startDen, slutDen, patient, laegemiddel, klokkeslet, antalEnheder);
        });
        assertEquals(exception.getMessage(), "Slut dato er efter startdato");

    }

    @Test
    void opretDagligSkaevOrdination_TC4() { //startDen, slutDen, patient, laegemiddel, klokkeslæt, antalenheder

        //Arrange
        LocalDate startDen = LocalDate.of(2023, 02, 16);
        LocalDate slutDen = LocalDate.of(2023, 02, 26);
        Patient patient = new Patient("1234567899", "Jens Jensen", 80.0);
        Laegemiddel laegemiddel = new Laegemiddel("Paracetamol", 0.1, 0.15, 0.16, "Styk");
        LocalTime[] klokkeslet = new LocalTime[7];
        double[] antalEnheder = new double[6];

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.opretDagligSkaevOrdination(startDen, slutDen, patient, laegemiddel, klokkeslet, antalEnheder);
        });
        assertEquals(exception.getMessage(), "Antal klokkeslet og enheder passer ikke sammen");

    }

    @Test
    void opretDagligSkaevOrdination_TC5() { //startDen, slutDen, patient, laegemiddel, klokkeslæt, antalenheder

        //Arrange
        LocalDate startDen = LocalDate.of(2023, 02, 16);
        LocalDate slutDen = LocalDate.of(2023, 02, 26);
        Patient patient = new Patient("1234567899", "Jens Jensen", 80.0);
        Laegemiddel laegemiddel = new Laegemiddel("Paracetamol", 0.1, 0.15, 0.16, "Styk");
        LocalTime[] klokkeslet = new LocalTime[6];
        double[] antalEnheder = new double[7];

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.opretDagligSkaevOrdination(startDen, slutDen, patient, laegemiddel, klokkeslet, antalEnheder);
        });
        assertEquals(exception.getMessage(), "Antal klokkeslet og enheder passer ikke sammen");

    }
}