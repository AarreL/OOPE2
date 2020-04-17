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

    public String toString() {
        String pvm = päivämäärä.getDayOfMonth() + "." + päivämäärä.getMonthValue() + "." + päivämäärä.getYear();
        String mjono = super.toString();
        String[] mjonoosat = mjono.split("///");
        String merkkiesitys = mjonoosat[0] + "///" + pvm + "///" + mjonoosat[1];
        return merkkiesitys;
    }
}