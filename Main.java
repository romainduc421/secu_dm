package secu_dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.special.Erf;

public class Main {

    static int sVN = 1315;
    static int sSTM = 1923122;
    static int sRANDU = 2920192;

    /**
     * Cette fonction applique l'algorithme de Von Neumann à un entier. Elle prends une graine en entrée et retourne
     * l'entier correspondant en sortie.
     * @param graine Graine du générateur.
     * @return Valeur courante obtenue par l'algorithme de VonNeumann
     */
    public static int vonNeuman(int graine){
        int res;
        //Racine élevée au carré
        res = graine * graine;
        /* Si le nombre n'est pas compris entre 0 et 9999, on retire le premier et le dernier chiffre */
        while(!(0 <= res && res <= 9999)){
            /* On convertie le nombre en chaine de caractère */
            String str = Integer.toString(res);
            /* On retire le premier et le dernier caractère de la chaine */
            str = str.substring(1,str.length()-1);
            /* On convertie la chaine en int */
            res = Integer.parseInt(str);
        }
        /* On renvoie l'entier */
        return res;
    }

    /**
     *
     * @param graine Graine du RNG Standard Minimal
     * @return valeur courante obtenue par l'algo de congruence linéaire Standard Minimal
     */
    public static int STM(int graine){
        int res;
        res = (graine*16807) % (int)(Math.pow(2,31)-1);
        return res;
    }

    /**
     *
     * @param graine Graine du RNG randu
     * @return Valeur courante obtenue par l'algo de congruence linéaire RANDU
     */
    public static int RANDU(int graine)
    {
        int res;
        res = (graine*65539) % (int)Math.pow(2, 31);    // %2^31 = 2147483648
        return res;
    }


    public static double frequency(List<Integer> x, int nb){
        /*int i,j;
        double p_value, sobs;
        int s=0;
        int word;
        int bit;
        for(i=0; i<x.size(); i++){
            word = x.get(i);

            for (j=0; j<64; j++){
                bit = (word <<(63-j));
                System.out.println(bit);
                bit >>= 63;
                s +=  (2*bit - 1);
            }
        }
        sobs = Math.abs(s) / Math.sqrt(nb);
        p_value = Erf.erfc(sobs / Math.sqrt(2.0));
        return p_value;*/return 0.0;
    }

    public static double frequencyVN(List<Integer> x, int nb){
        /*int i,j;
        double p_value, sobs;
        int s=0;
        int bit, word;
        for(i=0; i<x.size(); i++){
            word = x.get(i);
            for (j=0; j<13; j++){       //13
                bit = word<<(32-j);
                bit = bit >> 32;
                s = (int) (s + (2*bit - 1));
            }
        }
        sobs = Math.abs(s) / Math.sqrt((double)nb);
        p_value = Erf.erfc(sobs / Math.sqrt(2.0d));
        return p_value;*/return 0.0;
    }


    public static void main(String[] args) {

        int res;
        int l,k;
        Random rand = new Random(), rand2 = new Random(1315);
        List<Integer> values = new ArrayList<>(1000);
        System.out.println("Test avec la graine de vonNeuman: "+sVN);

        for(k=0; k<1000; k++)
        {
            res = vonNeuman(sVN);
            sVN = rand.nextInt((int) (Math.pow(2,31) ));
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);

        System.out.println("Test avec la graine de vonNeumann dans l'ordre: "+sVN);
        for(k=0; k<1000; k++)
        {
            res = vonNeuman(sVN);
            sVN = k;
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);


        System.out.println("Test avec la graine de STM: "+sSTM);
        for(k=0; k<1000; k++)
        {
            res = STM(sSTM) ;
            sSTM = rand.nextInt((int) (Math.pow(2,31) )) & 0xffff;
            values.add(Math.abs(res));
        }
        System.out.println(values);
        values.removeAll(values);

        System.out.println("Test avec la graine de RANDU: "+sRANDU);
        for(k=0; k<1000; k++)
        {
            res = RANDU(sRANDU) ;
            sRANDU = rand.nextInt((int) (Math.pow(2,31) )) & 0xffff;
            values.add(Math.abs(res));
        }
        System.out.println(values);
        values.removeAll(values);

        System.out.println("Test avec RAND: ");
        for(k=0; k<1000; k++)
        {
            res = rand2.nextInt((int) Math.pow(2,31));
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);

    }
}
