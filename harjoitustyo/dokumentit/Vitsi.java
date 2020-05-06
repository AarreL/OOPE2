package harjoitustyo.dokumentit;

/**
*Konkreettinen vitsi-luokka jolla mallinnetaan tekstikokoelman vitsejä
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (leinonen.aarre@tuni.fi)
*/

public class Vitsi extends Dokumentti {

    private String laji;

    //Aksessorit

    public String laji() {
        return laji;
    }
    public void laji(String uusiarvo) {
        if(uusiarvo!=null && uusiarvo.length()>0) {
            laji = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Rakentaja

    public Vitsi(int uusitunniste, String laji, String teksti) {
        super(uusitunniste, teksti);
        laji(laji);
    }

    public String toString() {
        String mjono = super.toString();
        String[] mjonoosat = mjono.split("///");
        String merkkiesitys = mjonoosat[0] + "///" + laji + "///" + mjonoosat[1];
        return merkkiesitys;
    }
}