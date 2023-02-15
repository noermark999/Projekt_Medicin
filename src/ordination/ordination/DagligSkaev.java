package ordination.ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{

    //Linkattribut til Dosis
    private ArrayList<Dosis> doser = new ArrayList<>();

    //Constructor
    public DagligSkaev(LocalDate startDen, LocalDate slutDen) {
        super(startDen, slutDen);
    }

    //Vedligeholder forbindelsen til Dosis
    public ArrayList<Dosis> getDoser() {
        return new ArrayList<>(doser);
    }

    public void opretDosis(LocalTime tid, double antal) {
        Dosis dosis = new Dosis(tid, antal);
        doser.add(dosis);
    }

    @Override
    public double samletDosis() {
       double sum = 0;
        for (Dosis dosis : doser) {
            sum = sum + dosis.getAntal();
       }
        return sum;
    }

    @Override
    public double doegnDosis() {
        return samletDosis() / (ChronoUnit.DAYS.between(getStartDen(), getSlutDen()) + 1);
    }

    @Override
    public String getType() {
        return "Daglig skæv";
    }

}
