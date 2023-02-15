package ordination.ordination;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.DAYS;

public class DagligFast extends Ordination {
    private Dosis[] doser = new Dosis[4];

    public DagligFast(LocalDate startDen, LocalDate slutDen) {
        super(startDen, slutDen);
    }

    @Override
    public double samletDosis() {
        double result = 0;
        for (int i = 0; i < 4; i++) {
            result += doser[i].getAntal();
        }
        return result * (DAYS.between(getStartDen(), getSlutDen()) + 1);
    }

    @Override
    public double doegnDosis() {
        return samletDosis() / (DAYS.between(getStartDen(), getSlutDen()) + 1);
    }

    @Override
    public String getType() {
        return "Daglig Fast";
    }

    public Dosis createDosis(LocalTime tid, double antal) {
        Dosis dosis = new Dosis(tid, antal);
        if (tid == null) {
            throw new IllegalArgumentException("Tid må ikke være null");
        } else if (antal < 0) {
            throw new IllegalArgumentException("Antal skal være positiv");
        } else if (doser[doser.length - 1] != null) {
            throw new IllegalArgumentException("Der er allerede oprettet maks antal doser");
        } else {
            for (int i = 0; i < 4; i++) {
                if (doser[i] == null) {
                    doser[i] = dosis;
                    return dosis;
                }
            }
        }
        return null;
    }

    public Dosis[] getDoser() {
        return doser;
    }
}
