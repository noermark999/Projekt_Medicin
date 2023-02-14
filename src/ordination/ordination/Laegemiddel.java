package ordination.ordination;

/**
 * Denne klasse modellerer et laegemiddel med et navn, samt en vaegtbestemt dosis for de tre vaegtgrupper
 */
public class Laegemiddel {
    private String navn;  // Laegemidlets navn
    private double enhedPrKgPrDoegnLet;   // Faktor der anvendes hvis patient vejer < 25 kg
    private double enhedPrKgPrDoegnNormal;// Faktor der anvendes hvis 25 kg <= patient vægt <= 120 kg
    private double enhedPrKgPrDoegnTung;  // Faktor der anvendes hvis patient vægt > 120 kg
    private String enhed; // Laegemidlets enhed

    /**
     * Opretter et laegemiddel med et navn, vaegtbestemt enheder per doegn for let, normal, tung, og en enhed
     * @param navn laegemidlets navn
     * @param enhedPrKgPrDoegnLet laegemidlets enhed per kilo per doegn (kategori: let)
     * @param enhedPrKgPrDoegnNormal laegemidlets enhed per kilo per doegn (kategori: normal)
     * @param enhedPrKgPrDoegnTung laegemidlets enhed per kilo per doegn (kategori: tung)
     * @param enhed laegemidlets enhed
     */
    public Laegemiddel(String navn, double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal, 
            double enhedPrKgPrDoegnTung, String enhed) {
        this.navn = navn;
        this.enhedPrKgPrDoegnLet = enhedPrKgPrDoegnLet;
        this.enhedPrKgPrDoegnNormal = enhedPrKgPrDoegnNormal;
        this.enhedPrKgPrDoegnTung = enhedPrKgPrDoegnTung;
        this.enhed = enhed;
    }

    /**
     * Returnerer laegemidlets enhed
     * @return laegemidlets enhed
     */
    public String getEnhed() {
        return enhed;
    }

    /**
     * Returnerer laegemidlets navn
     * @return laegemidlets navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Returnerer laegemidlets enheder per kilo hvert doegn for kategorien: let
     * @return laegemidlets enheder per kilo per doegn
     */
    public double getEnhedPrKgPrDoegnLet() {
        return enhedPrKgPrDoegnLet;
    }

    /**
     * Returnerer laegemidlets enheder per kilo hvert doegn for kategorien: normal
     * @return laegemidlets enheder per kilo per doegn
     */
    public double getEnhedPrKgPrDoegnNormal() {
        return enhedPrKgPrDoegnNormal;
    }

    /**
     * Returnerer laegemidlets enheder per kilo hvert doegn for kategorien: tung
     * @return laegemidlets enheder per kilo per doegn
     */
    public double getEnhedPrKgPrDoegnTung() {
        return enhedPrKgPrDoegnTung;
    }

    /**
     * To-string metode
     */
    @Override
    public String toString(){
        return navn;
    }
}
