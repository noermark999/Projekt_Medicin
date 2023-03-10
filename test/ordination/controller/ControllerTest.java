package ordination.controller;

import ordination.ordination.*;
import ordination.storage.Storage;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setup() {
        controller = Controller.getTestController();
    }

    @Test
    @Order(10)
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
    @Order(11)
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
    @Order(12)
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
        assertEquals(exception.getMessage(), "Antal m?? ikke v??re negativ");
    }

    @Test
    @Order(13)
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
    @Order(14)
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
    @Order(15)
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
        assertEquals(exception.getMessage(), "Antal skal v??re positiv");
    }

    //------------------------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Order(5)
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
    @Order(6)
    void TC6_anbefaletDosisPrDoegn_minus1kg() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", -10);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.anbefaletDosisPrDoegn(patient, laegemiddel);
        });
        assertEquals(exception.getMessage(), "V??gten p?? patienten er negativ");
    }

    @Test
    @Order(7)
    void TC7_anbefaletDosisPrDoegn_70kg_laegemiddelEnhedPerKiloDoegnLetMinus0d15() {
        // Arrange
        Patient patient = new Patient("121256-0512", "Jane Jensen", 70);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", -0.15, 0.15, 0.16, "Styk");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.anbefaletDosisPrDoegn(patient, laegemiddel);
        });
        assertEquals(exception.getMessage(), "L??gemiddellets enhed pr kg pr d??gn for let, normal eller tung er negativ");
    }

    //------------------------------------------------------------------------------------------------------------------
    @Test
    @Order(8)
    void TC1_AntalOrdinationerPrVaegtPrLaegemiddel_startErMindreEndSlut() {
        //Arrange
        double v??gtStart = 25;
        double v??gtSlut = 100;
        controller.createSomeObjects();
        Laegemiddel laegemiddel = controller.getAllLaegemidler().get(1);

        //Act
        int expected = controller.antalOrdinationerPrV??gtPrL??gemiddel(v??gtStart,v??gtSlut,laegemiddel);

        //Assert
        int actual = 3;
        assertEquals(expected,actual);
    }

    @Test
    @Order(9)
    void TC2_AntalOrdinationerPrVaegtPrLaegemiddel_slutErMindreEndStart() {
        //Arrange
        double v??gtStart = 50;
        double v??gtSlut = 40;
        controller.createSomeObjects();
        Laegemiddel laegemiddel = controller.getAllLaegemidler().get(1);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.antalOrdinationerPrV??gtPrL??gemiddel(v??gtStart,v??gtSlut,laegemiddel);
        });
        assertEquals(exception.getMessage(), "V??gt start m?? ikke v??re st??rre end v??gt slut");
    }

    //-------------------------------------------------------------------------------------------------
    @Test
    @Order(16)
    void TC1_opretDagligSkaevOrdination() { //startDen, slutDen, patient, laegemiddel, klokkesl??t, antalenheder

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
    @Order(17)
    void TC2_opretDagligSkaevOrdination() { //startDen, slutDen, patient, laegemiddel, klokkesl??t, antalenheder

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
    @Order(18)
    void TC3_opretDagligSkaevOrdination() {

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
    @Order(19)
    void TC4_opretDagligSkaevOrdination() { //startDen, slutDen, patient, laegemiddel, klokkesl??t, antalenheder

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
    @Order(20)
    void TC5_opretDagligSkaevOrdination() { //startDen, slutDen, patient, laegemiddel, klokkesl??t, antalenheder

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