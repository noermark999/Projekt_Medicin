package ordination.ordination;

import java.util.ArrayList;

/**
 * Denne klasse modellerer en patient med et cpr nummer, navn, og en vaegt.
 */
public class Patient {
    private String cprnr;  // Patientens cpr nummer
    private String navn;  // Patientens Navn
    private double vaegt; // Patientens vaegt
    private ArrayList<Ordination> ordinationer; // Associering: --> 0..* Ordination

    /**
     * Opretter en patient med det givet cpr nummer, navn, og vaegt
     * @param cprnr patientens cpr nummer
     * @param navn patientens navn
     * @param vaegt patientens vaegt
     */
    public Patient(String cprnr, String navn, double vaegt) {
        this.cprnr = cprnr;
        this.navn = navn;
        this.vaegt = vaegt;
        ordinationer = new ArrayList<>();
    }

    /**
     * Returnerer patientens cpr nummer
     * @return patientens cpr nummer
     */
    public String getCprnr() {
        return cprnr;
    }

    /**
     * Returnerer patientens navn
     * @return patientens navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Saetter patientens navn til det angivet navn
     * @param navn patientens nye navn
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    /**
     * Returnerer patientens vaegt
     * @return patientens vaegt
     */
    public double getVaegt(){
        return vaegt;
    }

    /**
     * Saetter patientens vaegt til den angivet vaerdi
     * @param vaegt patientens nye vaegt
     */
    public void setVaegt(double vaegt){
        this.vaegt = vaegt;
    }

    /**
     * Returnerer patientens ordineringer
     * @return patientens ordineringer
     */
    public ArrayList<Ordination> getOrdinationer() {
        return ordinationer;
    }

    /**
     * Tilfoejer ordinering til patientens ordineringer hvis den ikke allerede er
     * @param ordination den tilfoejede ordinering
     */
    public void addOrdination(Ordination ordination) {
        if(!ordinationer.contains(ordination)) {
            ordinationer.add(ordination);
        }
    }

    /**
     * To-string metode
     */
    @Override
    public String toString(){
        return navn + "  " + cprnr;
    }
}
