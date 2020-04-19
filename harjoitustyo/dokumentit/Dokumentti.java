package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.Tietoinen;
import java.util.LinkedList;

/*
*Abstrakti dokumentti-yliluokka vitsi ja uutinen luokille
*
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


    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        if(hakusanat!=null && hakusanat.size()>0) {
            String[] sanalista = teksti.split(" ");
            int listapit = sanalista.length;
            int hakupit = hakusanat.size();
            int h = 0;
            int l = 0;
            int laskuri = 0;
            while(h<hakupit) {
                l = 0;
                while(l<listapit) {
                    if(hakusanat.get(h).equals(sanalista[l])) {
                        laskuri++;
                        l = listapit;
                    }
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

    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {
        if(sulkusanat!=null && välimerkit!=null && sulkusanat.size()>0 && välimerkit.length()>0) {
            int välipit = välimerkit.length();
            int välilaskuri = 0;
            while(välilaskuri<välipit) {
                String poistom = "" + välimerkit.charAt(välilaskuri);
                teksti = teksti.replace(poistom, "");
                välilaskuri++;
            }
            teksti = teksti.toLowerCase();

            String[] tekstisanat = teksti.split(" ");
            int sanamäärä = tekstisanat.length;
            int sulkumäärä = sulkusanat.size();
            int sanalaskuri = 0;
            int sulkulaskuri = 0;
            while(sanalaskuri < sanamäärä) {
                sulkulaskuri = 0;
                while(sulkulaskuri < sulkumäärä) {
                    if(tekstisanat[sanalaskuri].equals(sulkusanat.get(sulkulaskuri))) {
                        if(sanalaskuri == 0) {
                            teksti = teksti.replace(tekstisanat[sanalaskuri] + " ", "");
                        }
                        else if(sanalaskuri == sanamäärä - 1) {
                            teksti = teksti.replace(" " + tekstisanat[sanalaskuri], "");
                        }
                        else {
                            teksti = teksti.replace(" " + tekstisanat[sanalaskuri] + " ", " ");
                        }
                    }
                    sulkulaskuri++;
                }
                sanalaskuri++;
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        String palautettava;
        palautettava = tunniste + "///" + teksti;
        return palautettava;
    }

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