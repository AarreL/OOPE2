import java.io.FileNotFoundException;

/**
*Oope2HT-luokka jolla käynnistetään ohjelma
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (leinonen.aarre@tuni.fi)
*/

public class Oope2HT {
    public static void main (String[] args) {
        try{
            String tiednimi = args[0];
            String stopit = args[1];

            Kayttoliittyma käyttis = new Kayttoliittyma();

            käyttis.käynnistä(tiednimi, stopit);
        }
        catch(FileNotFoundException e) {
            
        }
    }
}