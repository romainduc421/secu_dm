package secu_dm;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static org.apache.commons.math3.special.Erf.erfc;

public class Main {

    static int sVN = 5;
    static int sSTM = 5;
    static int sRANDU = 5;
    public static int cpt=0;
    public static boolean ontime=false;

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
    public static int randu(int graine){
        int res;
        /* a = 65539, b = 0, m = 2^31 - 1 */
        res = Math.floorMod(graine*65539, (int)Math.pow(2, 31)); // %2^31 = 2 147 483 648
        return res;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Cette fonction permet de transformer un Integer en forme binaire de nbBits bits sous forme d'un String
     * @param nbBits nombre de bit binaire
     * @param n valeur decimal du nombre a convertir
     * @return Un string correspondant au nombre en base 2
     */
    public static String toBinary(int nbBits, int n){
        String binaire = "";
        for(int i = nbBits-1; i >= 0;i--){
            if(n >= Math.pow(2,i) ){
                n = (int) (n - Math.pow(2,i));
                binaire += "1";
            } else {
                binaire += "0";
            }
        }
        return binaire;
    }


    /**
     * Fonction qui retourne la p-valeur d'une séquence d'Integer codé sous nb bits.
     * @param x vecteur contenant les nombres de la séquence
     * @param nb nombre de bit codant les entiers
     * @return p-valeur
     */
    public static double frequency(ArrayList<Integer> x, int nb)
    {
        /* On va stocker les mots binaires sous forme de boolean pour gagner en efficacité */
        ArrayList<Boolean> epsilon = new ArrayList<>();
        /* String contenant la convertion en binaire de nb Bits */
        String mot;
        if(x.isEmpty()){
            System.err.println("MonobitFrequencyTest : first arg's wrong");
            return -1.0;
        }
        /* Pour chaque Integer i du vecteur x */
        for (Integer i:x) {
            /* Transforme i en mot de nb bits */
            mot = toBinary(nb,i);
            /* Ajouter le mot a la suite de epsilon (1 = true, 0 = false) */
            for(int j = 0;j < mot.length();j++){
                if(mot.charAt(j)=='0'){
                    epsilon.add(false);
                } else {
                    epsilon.add(true);
                }
            }
        }
        /* Calcul de SN */
        int sn = 0, ones=0, zeroes=0;
        /* Si le bit est un 1, on fait +1, sinon on fait -1 */
        for (Boolean b:epsilon) {
            if(b){
                sn++;
                ones+=1;
            } else {
                sn--;
                zeroes+=1;
            }
        }
        /*if (cpt==3 && !ontime) {
            System.out.println(Arrays.toString(epsilon.toArray()));
            ontime = true;
        }*/
        if(cpt==3 )
        {
            System.out.println(" Ones count = "+ones);
            System.out.println(" zeroes count =  "+zeroes);
            //ontime = true;
        }
        /* Calcul de sobs */
        double sobs = abs(sn) / sqrt(nb*x.size());

        /* Calcul de Pvaleur */
        /* double pValue = 2 * (1 - cdf('Normal', sobs)) */
        double pValue = erfc(sobs / sqrt(2.0));
        if(pValue>=0.01)
            System.out.println("Monobit test passed");
        else
            System.out.println("Monobit test failed");
        return pValue;
        //return sobs;
    }

    public static double runs(ArrayList<Integer> x, int nb)
    {
        /* On va stocker les mots binaires sous forme de boolean pour gagner en efficacité */
        ArrayList<Boolean> epsilon = new ArrayList<>();
        /* String contenant la convertion en binaire de nb Bits */
        String mot;
        if(x.isEmpty()){
            System.err.println("RunsTest : first arg's wrong");
            return -1.0;
        }
        /* Pour chaque Integer i du vecteur x */
        for (Integer i:x)
        {
            /* Transforme i en mot de nb bits */
            mot = toBinary(nb,i);
            /* Ajouter le mot a la suite de epsilon (1 = true, 0 = false) */
            for(int j = 0;j < mot.length();j++){
                if(mot.charAt(j)=='0'){
                    epsilon.add(false);
                } else {
                    epsilon.add(true);
                }
            }
        }
        double Pi=0;
        int Vn_obs=1;
        int taille = epsilon.size();
        if(taille==10)
            System.out.println(taille);
        for (Boolean aBoolean : epsilon) {
            if (aBoolean)
                Pi++;
        }

        Pi/=taille;
        if(abs(Pi-0.5) >= (2.0/ sqrt(taille))) return 0.0;

        for(int l=0; l<taille-1; l++)
            if(epsilon.get(l) != epsilon.get(l+1))
                Vn_obs+=1;

        if(taille==10) {
            System.out.println("V" + taille + "_obs = " + Vn_obs);
            System.out.println("Pi = " + Pi);
        }
        double pValue = erfc(abs(Vn_obs - 2.0 * taille * Pi *(1.0 - Pi)) / (2.0 * sqrt(taille) * Pi * (1.0 - Pi)) /sqrt(2));
        if(pValue < 0.01)
            System.out.println("runs failed");
        else
            System.out.println("runs passed");
        return pValue;
    }

    public static double frequencyVN(ArrayList<Integer> x, int nb){
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

    public static double exponentielle(double lambda) {
        Random rand = new Random();
        return -(1 / lambda) * log(1 - rand.nextDouble());
    }


    /**
     * Main permetant d'executer les tests.
     * @param args
     */
    public static void main(String[] args) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("test.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintStream printStream = new PrintStream(fos);
        System.setOut(printStream);


        /* Liste pour stocker les résultats des tests */
        ArrayList<Integer> listeVonNeuman = new ArrayList<>();
        ArrayList<Integer> listeSTM = new ArrayList<>();
        ArrayList<Integer> listeRANDU = new ArrayList<>();
        ArrayList<Integer> listeRAND = new ArrayList<>();
        ArrayList<Double> listeExp = new ArrayList<>();
        /* Generateur Random de Java*/
        int resRand;

        //Random rand = new Random(55L);
        double resExp;

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
            sRANDU = randu(sRANDU);
            listeRANDU.add(sRANDU);
            /* RAND JAVA */
            //resRand = rand.nextInt((int) Math.pow(2,31));
            resRand = getRandomNumber(0, (int) (pow(2,31)-1));
            listeRAND.add(resRand);
            /* Exp */
            resExp = exponentielle(0.35);
            listeExp.add(resExp);

        }
        System.out.println("VonNeuman: \n"+listeVonNeuman+"\n");
        System.out.println("STM: \n"+listeSTM+"\n");
        System.out.println("RANDU: \n"+listeRANDU+"\n");
        System.out.println("RAND: \n"+listeRAND+"\n");
        System.out.println("EXP: \n"+listeExp+"\n");
        cpt=0;
        System.out.println("********* VonNeumann **********");
        for(int k=0; k<100; k++)
        {
            System.out.println("\n ---- Iteration "+k+" ----");
            listeVonNeuman.removeAll(listeVonNeuman);
            for(int l=0; l<1000; l++) {
                sVN = vonNeuman(sVN);
                listeVonNeuman.add(sVN);
            }
            System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeSTM,14)));
            System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeSTM,14)));
            //2^(13.2877124) = 10 000

        }
        cpt++;

        System.out.println("********* Standard Minimal **********");
        for(int k=0; k<100; k++)
        {
            System.out.println("\n ---- Iteration "+k+" ----");
            listeSTM.removeAll(listeSTM);
            for(int l=0; l<1000; l++) {
                sSTM = STM(sSTM);
                listeSTM.add(sSTM);
            }
            System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeSTM,32)));
            System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeSTM,32)));
        }
        cpt++;

        System.out.println("********* RANDU **********");
        for(int k=0; k<100; k++)
        {
            System.out.println("\n ---- Iteration "+k+" ----");
            listeRANDU.removeAll(listeRANDU);
            for(int l=0; l<1000; l++) {
                sRANDU = randu(sRANDU);
                listeRANDU.add(sRANDU);
            }
            System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeRANDU,32)));
            System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeRANDU,32)));
        }
        cpt++;

        System.out.println("********* RAND **********");
        for(int k=0; k<100; k++)
        {
            System.out.println("\n ---- Iteration "+k+" ----");
            listeRAND.removeAll(listeRAND);
            for(int l=0; l<1000; l++) {
                //resRand = rand.nextInt((int) Math.pow(2,31));
                resRand = getRandomNumber(0, (int) (pow(2,31)-1));
                listeRAND.add(resRand);
            }
            System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeRAND,32)));
            System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeRAND,32)));

        }
        cpt++;

        ArrayList<Integer> test = new ArrayList<>();
        test.add(725);  //1011010101
        System.out.println("\n");
        double restest = frequency(test,10);
        System.out.println("\n\nTest de l'enonce freqency : "+(new DecimalFormat("0.0000000")).format(restest));

        test.removeAll(test);
        test.add(619);  //1001101011
        System.out.println("\n");
        System.out.println("\n\nTest de l'enonce runs : "+(new DecimalFormat("0.0000000")).format(runs(test,10)));

    }


}
