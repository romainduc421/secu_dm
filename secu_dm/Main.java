package secu_dm;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;
import static org.apache.commons.math3.special.Erf.erfc;

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
        /* Racine élevée au carré */
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
        int sn = 0;
        /* Si le bit est un 1, on fait +1, sinon on fait -1 */
        for (Boolean b:epsilon) {
            if(b){
                sn++;
            } else {
                sn--;
            }
        }
        /* Calcul de sobs */
        double sobs = abs(sn) / sqrt(nb*x.size());
        /* Calcul de Pvaleur */
        double pValue = erfc(sobs / sqrt(2.0));
        if(pValue>=0.01)
            System.out.println("Monobit test passed");
        else
            System.out.println("Monobit test failed");
        return pValue;
    }

    /**
     * Fonction qui retourne la p-valeur d'une séquence d'Integer codé sous nb bits.
     * @param x vecteur contenant les nombres de la séquence
     * @param nb nombre de bit codant les entiers
     * @return p-valeur
     */
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
        if(pValue >= 0.01)
            System.out.println("runs passed");
        else
            System.out.println("runs failed");
        return pValue;
    }

    /**
     *
     * @param lambda
     * @return
     */
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
        double resExp = 0;
        Random rand = new Random();

        System.out.println("Test visuel (voir graphiques): \n");
        /* Test visuel pour 1 000 valeurs */
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
            resRand = rand.nextInt((int) Math.pow(2,31));
            listeRAND.add(resRand);
            /* Exp */
            resExp = exponentielle(0.35);
            listeExp.add(resExp);
        }
        System.out.println("VonNeumann: "+listeVonNeuman);
        System.out.println("STM: "+listeSTM);
        System.out.println("RANDU: "+listeRANDU);
        System.out.println("RAND: "+listeRAND);
        System.out.println("EXP: "+listeExp);



        System.out.println("\n\nDebuts des tests de frequency et run: \n");
        System.out.println("\n********* VonNeumann **********");
        for(int k=0; k<100; k++)
        {
            listeVonNeuman.removeAll(listeVonNeuman);
            for(int l=0; l<1000; l++) {
                sVN = vonNeuman(sVN);
                listeVonNeuman.add(sVN);
            }
            if(k<5){
                System.out.println("\n ---- Iteration "+k+" ----");
                System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeVonNeuman,14)));
                System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeVonNeuman,14)));
            }
        }

        System.out.println("\n********* Standard Minimal **********");
        for(int k=0; k<100; k++)
        {
            listeSTM.removeAll(listeSTM);
            for(int l=0; l<1000; l++) {
                sSTM = STM(sSTM);
                listeSTM.add(sSTM);
            }
            if(k<5){
                System.out.println("\n ---- Iteration "+k+" ----");
                System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeSTM,31)));
                System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeSTM,31)));
            }
        }

        System.out.println("\n********* RANDU **********");
        for(int k=0; k<100; k++)
        {
            listeRANDU.removeAll(listeRANDU);
            for(int l=0; l<1000; l++) {
                sRANDU = randu(sRANDU);
                listeRANDU.add(sRANDU);
            }
            if(k<5){
                System.out.println("\n ---- Iteration "+k+" ----");
                System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeRANDU,31)));
                System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeRANDU,31)));
            }
        }

        System.out.println("\n********* RAND **********");
        for(int k=0; k<100; k++)
        {
            listeRAND.removeAll(listeRAND);
            for(int l=0; l<1000; l++) {
                resRand = rand.nextInt((int) Math.pow(2,47));
                listeRAND.add(resRand);
            }
            if(k<5){
                System.out.println("\n ---- Iteration "+k+" ----");
                System.out.println("monobit freq : p_value = "+(new DecimalFormat("0.0000000")).format(frequency(listeRAND,31)));
                System.out.println("runs : p_value = "+(new DecimalFormat("0.0000000")).format(runs(listeRAND,31)));
            }
        }
    }
}
