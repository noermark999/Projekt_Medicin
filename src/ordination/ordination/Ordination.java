package ordination.ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Denne klasse modellerer en ordination med en start- og slutdato, samt et muligt laegemiddel
 */
public abstract class Ordination {
    private LocalDate startDen; // Startdatoen
    private LocalDate slutDen; // Slutdatoen
    private Laegemiddel laegemiddel; // Associering: 0..1 Laegemiddel

    /**
     * Opretter en Ordination med en start- og slutdato
     * @param startDen startdatoen
     * @param slutDen slutdatoen
     */
    public Ordination(LocalDate startDen, LocalDate slutDen) {
        this.startDen = startDen;
        this.slutDen = slutDen;
    }

    /**
     * Returnerer startdatoen
     * @return startdatoen
     */
    public LocalDate getStartDen() {
        return startDen;
    }

    /**
     * Returnerer slutdatoen
     * @return slutdatoen
     */
    public LocalDate getSlutDen() {
        return slutDen;
    }

    /**
     * Returnerer laegemidlet
     * @return laegemiddel
     */
    public Laegemiddel getLaegemiddel() {
        return laegemiddel;
    }

    /**
     * Saetter laegemiddel til det angivet laegemiddel
     * @param laegemiddel det nye laegemiddel
     */
    public void setLaegemiddel(Laegemiddel laegemiddel) {
        this.laegemiddel = laegemiddel;
    }

    /**
     * Antal hele dage mellem startdato og slutdato. Begge dage inklusive.
     * @return antal dage ordinationen gælder for
     */
    public int antalDage() {
        return (int) ChronoUnit.DAYS.between(startDen, slutDen) + 1;
    }

    /**
     * To-string metode
     */
    @Override
    public String toString() {
        return startDen.toString();
    }

    /**
     * Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
     * @return samlet dosis
     */
    public abstract double samletDosis();

    /**
     * Returnerer den gennemsnitlige dosis givet pr dag i den periode ordinationen er gyldig
     * @return doegndosis
     */
    public abstract double doegnDosis();

    /**
     * Returnerer ordinationstypen som en String
     * @return ordinationstypen
     */
    public abstract String getType();
}
