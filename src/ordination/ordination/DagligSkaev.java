package ordination.ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{

    //Linkattribut til Dosis
    private ArrayList<Dosis> doser = new ArrayList<>();

    //Constructor
    // TODO
    public DagligSkaev(LocalDate startDen, LocalDate slutDen) {
        super(startDen, slutDen);
    }

    //Vedligeholder forbindelsen til Dosis
    public ArrayList<Dosis> getDoser() {
        return new ArrayList<>(doser);
    }

    public void opretDosis(LocalTime tid, double antal) {
        // TODO
        Dosis dosis = new Dosis(tid, antal);
        doser.add(dosis);
    }

    @Override
    public double samletDosis() {
       double sum = 0;
        for (Dosis dosis : doser) {

       }
    }

    @Override
    public double doegnDosis() {
        double sum = 0;
        for (Dosis dosis : doser) {

        }

    }

    @Override
    public String getType() {
        return null;
    }

}
