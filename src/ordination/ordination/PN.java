package ordination.ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PN extends Ordination{
    private ArrayList<LocalDate> doseringer;  // Givet doseringer
    private double antalEnheder;  // Antal enheder per dosis

    /**
     * Konstruerer en PN ordinering
     * @param startDen startdato
     * @param slutDen slutdato
     * @param antalEnheder antal enheder per dosis
     */
    public PN(LocalDate startDen, LocalDate slutDen, double antalEnheder) {
        super(startDen, slutDen);
        this.doseringer = new ArrayList<>();
        this.antalEnheder = antalEnheder;
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og efter den sidste ordination, og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen dato for ordination
     */
    public boolean givDosis(LocalDate givesDen) {
        if(!givesDen.isBefore(this.getStartDen()) && !givesDen.isAfter(this.getSlutDen()))  {
            if(!givesDen.isBefore(doseringer.get(doseringer.size() - 1))) {
                doseringer.add(givesDen);

            }
            return true;
        }
        return false;   
    }

    /**
     * Beregner gennemsnitlig daglig dosis
     */
    public double doegnDosis() {
        return (antalEnheder * doseringer.size()) / (1.00 * (1 + ChronoUnit.DAYS.between(this.getStartDen(), this.getSlutDen())));
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
     */
    public int getAntalGangeGivet() {
        return doseringer.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
