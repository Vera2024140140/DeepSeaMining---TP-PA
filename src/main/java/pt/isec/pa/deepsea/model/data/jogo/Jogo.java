package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.grelhas.GrelhaSuperficie;

public class Jogo {
    private final Navio navio;
    private final GrelhaSuperficie grelhaSuperficie;

    public Jogo() {
        this.grelhaSuperficie = new GrelhaSuperficie();
        this.navio = new Navio();
    }

    // -- Getter's
    Navio getNavio() {
        return navio;
    }

    GrelhaSuperficie getGrelhaSuperficie() {
        return grelhaSuperficie;
    }
}
