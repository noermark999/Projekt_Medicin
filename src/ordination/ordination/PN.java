package ordination.ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Denne klasse modellerer en PN ordination
 */
public class PN extends Ordination{
    private ArrayList<LocalDate> doseringer;  // Givet doseringer
    private double antalEnheder;  // Antal enheder per dosis

    /**
     * Opretter en PN ordinering
     * @param startDen startdato
     * @param slutDen slutdato
     * @param antalEnheder antal enheder per dosis
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     * Pre: antal >= 0
     */
    public PN(LocalDate startDen, LocalDate slutDen, double antalEnheder) {
        super(startDen, slutDen);
        this.doseringer = new ArrayList<>();
        this.antalEnheder = antalEnheder;
    }

    /**
     * Returnerer doseringerne
     * @return doseringer
     */
    public ArrayList<LocalDate> getDoseringer() {
        return doseringer;
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og efter den sidste ordination, og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen dato for ordination
     */
    public boolean givDosis(LocalDate givesDen) {
        if (givesDen == null) {
            throw new IllegalArgumentException("Dato må ikke være null");
        } else if(!givesDen.isBefore(this.getStartDen()) && !givesDen.isAfter(this.getSlutDen()))  {
            if(doseringer.size() == 0 || !givesDen.isBefore(doseringer.get(doseringer.size() - 1))) {
                doseringer.add(givesDen);
                return true;
            }
        }
        return false;   
    }

    /**
     * Beregner gennemsnitlig daglig dosis
     */
    public double doegnDosis() {
        if(doseringer.size() == 0) {
            return 0;
        } else {
            return (antalEnheder * doseringer.size()) / (1.00 * (1 + ChronoUnit.DAYS.between(doseringer.get(0), doseringer.get(doseringer.size() - 1))));
        }
    }

    @Override
    public String getType() {
        return "PN";
    }

    /**
     * Beregner samlet dosis
     */
    public double samletDosis() {
        return (antalEnheder * doseringer.size());
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return antal gange givet
     */
    public int getAntalGangeGivet() {
        return doseringer.size();
    }

    /**
     * Returnerer antal enheder
     * @return antal enheder
     */
    public double getAntalEnheder() {
        return antalEnheder;
    }

}
