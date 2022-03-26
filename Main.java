package secu_dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    static long sVN = 1315L;
    static long sSTM = 1920931019L;
    static long sRANDU = 292019209L;

    /**
     * Cette fonction applique l'algorithme de Von Neumann à un entier. Elle prends une graine en entrée et retourne
     * l'entier correspondant en sortie.
     * @param graine Graine du générateur.
     * @return Valeur courante obtenue par l'algorithme de VonNeumann
     */
    public static long vonNeuman(long graine){
        long res;
        //Racine élevée au carré
        res = graine * graine;
        /* Si le nombre n'est pas compris entre 0 et 9999, on retire le premier et le dernier chiffre */
        while(!(0 <= res && res <= 9999)){
            /* On convertie le nombre en chaine de caractère */
            String str = Long.toString(res);
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
    public static long STM(long graine){
        long res;
        res = (graine*16807) % (int)(Math.pow(2,31)-1);
        return res;
    }

    /**
     *
     * @param graine Graine du RNG randu
     * @return Valeur courante obtenue par l'algo de congruence linéaire RANDU
     */
    public static long RANDU(long graine)
    {
        long res;
        res = (graine*65539) % (int)Math.pow(2, 31);    // %2^31 = 2147483648
        return res;
    }




    public static void main(String[] args) {

        long res;
        Random rand = new Random(), rand2 = new Random(1315);
        List<Long> values = new ArrayList<>(1000);
        System.out.println("Test avec la graine de vonNeuman: "+sVN);

        for(int k=0; k<1000; k++)
        {
            res = vonNeuman(sVN);
            sVN = (long)rand.nextInt(10000);
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);

        System.out.println("Test avec la graine de vonNeumann dans l'ordre: "+sVN);
        for(int k=0; k<1000; k++)
        {
            res = vonNeuman(sVN);
            sVN = k;
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);


        System.out.println("Test avec la graine de STM: "+sSTM);
        for(int k=0; k<1000; k++)
        {
            res = STM(sSTM);
            sSTM = rand.nextLong() & 0xffffffffL;
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);

        System.out.println("Test avec la graine de RANDU: "+sRANDU);
        for(int k=0; k<1000; k++)
        {
            res = RANDU(sRANDU);
            sRANDU = rand.nextLong() & 0xffffffffL;
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);

        System.out.println("Test avec RAND: ");
        for(int k=0; k<1000; k++)
        {
            res = rand2.nextLong() & 0xffffffffL;
            values.add(res);
        }
        System.out.println(values);
        values.removeAll(values);

    }
}
