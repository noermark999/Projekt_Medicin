package ordination.ordination;

import java.time.LocalTime;

/**
 * Denne klasse modellerer en dosis med en tid og et antal
 */
public class Dosis {
    private LocalTime tid; // doserings tid
    private double antal; // doserings antal

    /**
     * Opretter en dosis med en givet tid og antal
     * @param tid doserings tid
     * @param antal doserings antal
     */
    public Dosis(LocalTime tid, double antal) {
        super();
        this.tid = tid;
        this.antal = antal;
    }

    /**
     * Returnerer doserings antal
     * @return doserings antal
     */
    public double getAntal() {
        return antal;
    }

    /**
     * Saetter doserings antal tl den angivne vaerdi
     * @param antal doserings nye antal
     */
    public void setAntal(double antal) {
        this.antal = antal;
    }

    /**
     * Returnerer doserings tid
     * @return doserings tid
     */
    public LocalTime getTid() {
        return tid;
    }

    /**
     * Saetter doserings tid til den angivne vaerdi
     * @param tid doserings nye tid
     */
    public void setTid(LocalTime tid) {
        this.tid = tid;
    }

    /**
     * To-string metode
     */
    @Override
    public String toString(){
        return "Kl: " + tid + "   antal:  " + antal;
    }
}
