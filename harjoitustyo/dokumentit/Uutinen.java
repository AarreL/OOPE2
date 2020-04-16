package harjoitustyo.dokumentit;

import java.time.LocalDate;

/*
*Uutinen aliluokka jolla mallinnetaan uutista
*
*
*/

public class Uutinen extends Dokumentti {

    private LocalDate päivämäärä;

    //Aksessorit

    public LocalDate päivämäärä() {
        return päivämäärä;
    }
    public void päivämäärä(LocalDate uusiarvo) {
        if(uusiarvo != null) {
            päivämäärä = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Rakentaja

    public Uutinen(int uusitunniste, LocalDate pvm, String teksti) {
        super(uusitunniste, teksti);
        päivämäärä(pvm);
    }
}