package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.Tietoinen;
import java.util.LinkedList;

/**
*Abstrakti dokumentti-yliluokka vitsi ja uutinen luokille
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (aarre.leinonen@tuni.fi)
*/


public abstract class Dokumentti implements Comparable<Dokumentti>, Tietoinen<Dokumentti> {

    //Dokumenttien yhteiset tiedot
    private int tunniste;
    private String teksti;


    //Aksessorit
    public int tunniste() {
        return tunniste;
    }
    public void tunniste(int uusiarvo) {
        if(uusiarvo > 0) {
            tunniste = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String teksti() {
        return teksti;
    }
    public void teksti(String uusiarvo) {
        if(uusiarvo!=null && uusiarvo.length()>0) {
            teksti = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Rakentajat

    public Dokumentti(int tunnistearvo, String tekstiarvo) {
        tunniste(tunnistearvo);
        teksti(tekstiarvo);
    }

    /**
     * Metodi jolla tutkitaan löytyykö haettavia sanoja tästä dokumentista
     * 
     * @param hakusanat lista sanoista joita halutaan dokumentista hakea
     * @return true jos haku täsmää, false jos ei täsmää
     * @throws IllegalArgumentException
     */
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        //Tarkistukset
        if(hakusanat!=null && hakusanat.size()>0) {

            String[] sanalista = teksti.split(" ");
            int listapit = sanalista.length;
            int hakupit = hakusanat.size();

            //Laskurit silmukoihin
            int h = 0;
            int l = 0;
            int laskuri = 0;
            while(h<hakupit) {
                l = 0;
                while(l<listapit) {
                    //Kun hakusana löytyy, keskeytetään tämä silmukka ja aletaan hakea seuraavaa
                    if(hakusanat.get(h).equals(sanalista[l])) {
                        laskuri++;
                        l = listapit;
                    }
                    //paitsi jos kaikki hakusanat on jo löydetty ja voidaan lopettaa haku
                    if(laskuri == hakupit) {
                        return true;
                    }
                    l++;
                }
                h++;
            }
            return false;
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Metodi joka siivoaa tätä dokumenttia poistaen ensin välimerkit
     * sitten muokkaamalla tekstin pieniksi kirjaimiksi ja siistimällä välit
     * ja lopuksi poistamalla sulkusanalistalla esiintyvät sanat
     * dokumentista
     * 
     * @param sulkusanat lista sulkusanoista
     * @param välimerkit merkkijonoesitys poistettavista välimerkeistä
     * @throws IllegalArgumentException
     */

    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {
        //Tarkistukset
        if(sulkusanat!=null && välimerkit!=null && sulkusanat.size()>0 && välimerkit.length()>0) {

            int välipit = välimerkit.length();
            int välilaskuri = 0;

            //Poistetaan parametrina saadut välimerkit
            while(välilaskuri<välipit) {
                String poistom = "" + välimerkit.charAt(välilaskuri);
                teksti = teksti.replace(poistom, "");
                välilaskuri++;
            }

            //Pienennetään kirjainkoko ja poistetaan mahdolliset turhat välit
            teksti = teksti.toLowerCase();
            teksti = teksti.replace("  ", " ");
            teksti = teksti.trim();

            String[] tekstisanat = teksti.split(" ");
            int sanamäärä = tekstisanat.length;
            int sulkumäärä = sulkusanat.size();
            int sanalaskuri = 0;
            int sulkulaskuri = 0;
            int eka = 0;
            int vika = sanamäärä -1;

            //Silmukoidaan rinnakkain sulkusanalistaa sekä dokumenttia
            //Ja poistetaan sulkusanalistalla esiintyvät sanat dokumentista
            while(sulkulaskuri < sulkumäärä) {
                sanalaskuri = 0;
                while(sanalaskuri < sanamäärä) {
                    if(tekstisanat[sanalaskuri].equals(sulkusanat.get(sulkulaskuri))) {
                        if(sanalaskuri == eka) {
                            teksti = teksti.replace(" " + tekstisanat[sanalaskuri] + " ", " ");
                            teksti = teksti.replaceFirst(tekstisanat[sanalaskuri] + " ", "");
                            eka++;
                        }
                        else if(sanalaskuri == vika) {
                            teksti = teksti.replace(" " + tekstisanat[sanalaskuri], "");
                            vika--;
                        }
                        else {
                            teksti = teksti.replace(" " + tekstisanat[sanalaskuri] + " ", " ");
                        }
                    }
                    sanalaskuri++;
                }
                sulkulaskuri++;
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    //Korvataan yliluokan tostring
    @Override
    public String toString() {
        String palautettava;
        palautettava = tunniste + "///" + teksti;
        return palautettava;
    }

    //Korvataan yliluokan equals
    @Override
    public boolean equals(Object verrattava) {
        if(this == verrattava) {
            return true;
        } else if(!(verrattava instanceof Dokumentti)) {
            return false;
        } else {
            Dokumentti valmisverrattava = (Dokumentti) verrattava;
            if(valmisverrattava.tunniste == tunniste) {
                return true;
            }
            else {
                return false;
            }
        }

    }

    //Korvataan yliluokan compareTo vertaamalla ainoastaan tunnisteita
    @Override
    public int compareTo(Dokumentti verrattava) {
        if(tunniste < verrattava.tunniste) {
            return -1;
        }else if(tunniste == verrattava.tunniste) {
            return 0;
        } else {
            return 1;
        }
    }

}