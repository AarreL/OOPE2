package harjoitustyo.dokumentit;

import java.time.LocalDate;

/*
*Uutinen aliluokka jolla mallinnetaan uutista
*
*
*/

public class Uutinen extends Dokumentti {

    private LocalDate paivamaara;

    //Aksessorit

    public LocalDate paivamaara() {
        return paivamaara;
    }
    public void paivamaara(LocalDate uusiarvo) {
        if(uusiarvo != null) {
            paivamaara = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Rakentaja

    public Uutinen(int uusitunniste, LocalDate pvm, String teksti) {
        super(uusitunniste, teksti);
        paivamaara(pvm);
    }

    public String toString() {
        String pvm = paivamaara.getDayOfMonth() + "." + paivamaara.getMonthValue() + "." + paivamaara.getYear();
        String mjono = super.toString();
        String[] mjonoosat = mjono.split("///");
        String merkkiesitys = mjonoosat[0] + "///" + pvm + "///" + mjonoosat[1];
        return merkkiesitys;
    }
}