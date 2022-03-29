package secu_dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.log;

public class Main {

    static int sVN = 5;
    static int sSTM = 5;
    static int sRANDU = 5;

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
     * Cette fonction applique l'algorithme STM à un entier. Elle prends une graine en entrée et retourne
     * l'entier correspondant en sortie.
     * @param graine Graine du générateur.
     * @return Valeur courante obtenue par l'algorithme STM.
     */
    public static int STM(int graine){
        int res;
        /* a = 16807, b = 0, m = 2^31 - 1 */
        res = Math.floorMod(graine*16807, (int) (Math.pow(2,31)-1)); // %2^31 = 2 147 483 648
        return res;
    }

    /**
     * Cette fonction applique l'algorithme RANDU à un entier. Elle prends une graine en entrée et retourne
     * l'entier correspondant en sortie.
     * @param graine Graine du générateur.
     * @return Valeur courante obtenue par l'algorithme RANDU.
     */
    public static int RANDU(int graine){
        int res;
        /* a = 65539, b = 0, m = 2^31 - 1 */
        res = Math.floorMod(graine*65539, (int)Math.pow(2, 31)); // %2^31 = 2 147 483 648
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

    public double Exponentielle(double lambda) {
        Random rand = new Random();
        return -(1 / lambda) * log( 1 - rand.nextDouble() );
    }


    /**
     * Main permetant d'executer les tests.
     * @param args
     */
    public static void main(String[] args) {


        /* Liste pour stocker les résultats des tests */
        ArrayList<Integer> listeVonNeuman = new ArrayList<>();
        ArrayList<Integer> listeSTM = new ArrayList<>();
        ArrayList<Integer> listeRANDU = new ArrayList<>();
        ArrayList<Integer> listeRAND = new ArrayList<>();
        /* Generateur Random de Java*/
        int resRand;
        Random rand = new Random();

        /* Test visuel pour 1 000 valeurs */
        System.out.println("----- \nTest avec 1000 valeurs\n-----");
        for(int i = 0; i<1000;i++){
            /* VonNeuman */
            sVN = vonNeuman(sVN);
            listeVonNeuman.add(sVN);
            /* STM */
            sSTM = STM(sSTM);
            listeSTM.add(sSTM);
            /* RANDU */
            sRANDU = RANDU(sRANDU);
            listeRANDU.add(sRANDU);
            /* RAND JAVA */
            resRand = rand.nextInt((int) Math.pow(2,31));
            listeRAND.add(resRand);
        }
        System.out.println("VonNeuman: \n"+listeVonNeuman+"\n");
        System.out.println("STM: \n"+listeSTM+"\n");
        System.out.println("RANDU: \n"+listeRANDU+"\n");
        System.out.println("RAND: \n"+listeRAND+"\n");
    }
}
