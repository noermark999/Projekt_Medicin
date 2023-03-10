package ordination.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ordination.ordination.*;
import ordination.storage.Storage;

/**
 * Controller-klasse
 */
public class Controller {
	private Storage storage; // Controllerens storage
	private static Controller controller; // Statisk Controller

	/**
	 * Opretter en Controller
	 */
	private Controller() {
		storage = new Storage();
	}

	/**
	 * Returnerer Controller hvis der er en, ellers opretter ny controller
	 * @return controller
	 */
	public static Controller getController() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	/**
	 * Returnerer Controller
	 * @return controller
	 */
	public static Controller getTestController() {
		return new Controller();
	}

	/**
	 * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
	 * ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: antal >= 0
	 * @return opretter og returnerer en PN ordination.
	 */
	public PN opretPNOrdination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double antal) {
		if(startDen.isAfter(slutDen)) {
			throw new IllegalArgumentException("Start dato skal komme inden slutdato");
		} else if (antal < 0) {
			throw new IllegalArgumentException("Antal må ikke være negativ");
		} else {
			PN pn = new PN(startDen, slutDen, antal);
			pn.setLaegemiddel(laegemiddel);
			patient.addOrdination(pn);
			return pn;
		}
	}

	/**
	 * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: margenAntal, middagAntal, aftanAntal, natAntal >= 0
	 */
	public DagligFast opretDagligFastOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			double morgenAntal, double middagAntal, double aftenAntal,
			double natAntal) {
		if (slutDen.isBefore(startDen)) {
			throw new IllegalArgumentException("Slut dato er efter startdato");
		} else {
			DagligFast dagligFast = new DagligFast(startDen, slutDen);
			dagligFast.setLaegemiddel(laegemiddel);
			patient.addOrdination(dagligFast);
			dagligFast.createDosis(LocalTime.of(8,0),morgenAntal);
			dagligFast.createDosis(LocalTime.of(12,0),middagAntal);
			dagligFast.createDosis(LocalTime.of(18,0),aftenAntal);
			dagligFast.createDosis(LocalTime.of(23,0),natAntal);
			return dagligFast;
		}
	}

	/**
	 * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke.
	 * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige kastes også en IllegalArgumentException.
	 *
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: alle tal i antalEnheder > 0
	 */
	public DagligSkaev opretDagligSkaevOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			LocalTime[] klokkeSlet, double[] antalEnheder) {
		if (slutDen.isBefore(startDen)) {
			throw new IllegalArgumentException("Slut dato er efter startdato");
		} else if (klokkeSlet.length != antalEnheder.length) {
			throw new IllegalArgumentException("Antal klokkeslet og enheder passer ikke sammen");
		} else {
			DagligSkaev dagligSkaev = new DagligSkaev(startDen, slutDen);
			dagligSkaev.setLaegemiddel(laegemiddel);
			patient.addOrdination(dagligSkaev);
			for (int i = 0; i < klokkeSlet.length; i++) {
				dagligSkaev.opretDosis(klokkeSlet[i], antalEnheder[i]);
			}
			return dagligSkaev;
		}
	}

	/**
	 * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
	 * datoen ikke er indenfor ordinationens gyldighedsperiode, eller den ikke
	 * forekommer efter de andre allerede anvendelser, kastes en IllegalArgumentException
	 * Pre: ordination og dato er ikke null
	 */
	public void ordinationPNAnvendt(PN ordination, LocalDate dato) {
		if(!ordination.givDosis(dato)) { // Dosis oprettes her hvis der ikke returneres false
			throw new IllegalArgumentException("Datoen er ugyldig");
		}
	}

	/**
	 * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
	 * til patientens vægt). Det er en forskellig enheds faktor der skal
	 * anvendes, og den er afhængig af patientens vægt.
	 * Pre: patient og lægemiddel er ikke null
	 */
	public double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
		double vaegt = patient.getVaegt();
		if (vaegt < 0) {
			throw new IllegalArgumentException("Vægten på patienten er negativ");
		} else if (laegemiddel.getEnhedPrKgPrDoegnLet() < 0 || laegemiddel.getEnhedPrKgPrDoegnNormal() < 0 || laegemiddel.getEnhedPrKgPrDoegnTung() < 0 ) {
			throw new IllegalArgumentException("Lægemiddellets enhed pr kg pr døgn for let, normal eller tung er negativ");
		} else if (vaegt < 25) {
			return vaegt * laegemiddel.getEnhedPrKgPrDoegnLet();
		} else if (vaegt > 120) {
			return vaegt * laegemiddel.getEnhedPrKgPrDoegnTung();
		} else {
			return vaegt * laegemiddel.getEnhedPrKgPrDoegnNormal();
		}
	}

	/**
	 * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
	 * ordinationer.
	 * Pre: laegemiddel er ikke null
	 */
	public int antalOrdinationerPrVægtPrLægemiddel(double vægtStart,
			double vægtSlut, Laegemiddel laegemiddel) {
		int result = 0;
		if (vægtStart > vægtSlut) {
			throw new IllegalArgumentException("Vægt start må ikke være større end vægt slut");
		} else {
			for (Patient p : storage.getAllPatienter()) {
				if (p.getVaegt() >= vægtStart && p.getVaegt() <= vægtSlut) {
					for (Ordination o : p.getOrdinationer()) {
						if (o.getLaegemiddel().equals(laegemiddel)) {
							result++;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returnerer alle patienter i storage
	 * @return patienterne i storage
	 */
	public List<Patient> getAllPatienter() {
		return storage.getAllPatienter();
	}

	/**
	 * Returnerer alle laegemidler i storage
	 * @return alle laegemidler i storage
	 */
	public List<Laegemiddel> getAllLaegemidler() {
		return storage.getAllLaegemidler();
	}

	/**
	 * Metode der kan bruges til at checke at en startDato ligger før en
	 * slutDato.
	 *
	 * @return true hvis startDato er før slutDato, false ellers.
	 */
	private boolean checkStartFoerSlut(LocalDate startDato, LocalDate slutDato) {
		boolean result = true;
		if (slutDato.compareTo(startDato) < 0) {
			result = false;
		}
		return result;
	}

	public Patient opretPatient(String cpr, String navn, double vaegt) {
		Patient p = new Patient(cpr, navn, vaegt);
		storage.addPatient(p);
		return p;
	}

	public Laegemiddel opretLaegemiddel(String navn,
			double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal,
			double enhedPrKgPrDoegnTung, String enhed) {
		Laegemiddel lm = new Laegemiddel(navn, enhedPrKgPrDoegnLet,
				enhedPrKgPrDoegnNormal, enhedPrKgPrDoegnTung, enhed);
		storage.addLaegemiddel(lm);
		return lm;
	}

	public void createSomeObjects() {
		this.opretPatient("121256-0512", "Jane Jensen", 63.4);
		this.opretPatient("070985-1153", "Finn Madsen", 83.2);
		this.opretPatient("050972-1233", "Hans Jørgensen", 89.4);
		this.opretPatient("011064-1522", "Ulla Nielsen", 59.9);
		this.opretPatient("090149-2529", "Ib Hansen", 87.7);

		this.opretLaegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
		this.opretLaegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
		this.opretLaegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
		this.opretLaegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk");

		this.opretPNOrdination(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 12),
				storage.getAllPatienter().get(0), storage.getAllLaegemidler()
						.get(1),
				123);

		this.opretPNOrdination(LocalDate.of(2021, 2, 12), LocalDate.of(2021, 2, 14),
				storage.getAllPatienter().get(0), storage.getAllLaegemidler()
						.get(0),
				3);

		this.opretPNOrdination(LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 25),
				storage.getAllPatienter().get(3), storage.getAllLaegemidler()
						.get(2),
				5);

		this.opretPNOrdination(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 12),
				storage.getAllPatienter().get(0), storage.getAllLaegemidler()
						.get(1),
				123);

		this.opretDagligFastOrdination(LocalDate.of(2021, 1, 10),
				LocalDate.of(2021, 1, 12), storage.getAllPatienter().get(1),
				storage.getAllLaegemidler().get(1), 2, 0, 1, 0);

		LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40),
				LocalTime.of(16, 0), LocalTime.of(18, 45) };
		double[] an = { 0.5, 1, 2.5, 3 };

		this.opretDagligSkaevOrdination(LocalDate.of(2021, 1, 23),
				LocalDate.of(2021, 1, 24), storage.getAllPatienter().get(1),
				storage.getAllLaegemidler().get(2), kl, an);
	}

}
