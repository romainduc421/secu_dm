import java.util.ArrayList;

public class Main {

    static int sVN;
    static int sSTM;
    static int sRANDU;

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
     * @param graine
     * @return
     */
    public static int STM(int graine){
        int res;
        res = (graine*16807) % (int)(Math.pow(2,31)-1);
        return res;
    }

    /**
     *
     * @param graine
     * @return
     */
    public static int RANDU(int graine)
    {
        int res;
        res = (graine*65539) % (int)Math.pow(2, 31);    // %2^31 = 2147483648
        return res;
    }




    public static void main(String[] args) {
        int graine = 1315;

        System.out.println("Test avec la graine de vonNeuman: "+graine);
        int res = vonNeuman(graine);
        System.out.println("Nombre généré: "+res);

        System.out.println("Test avec la graine de STM: "+graine);
        res = STM(graine);
        System.out.println("Nombre généré: "+res);

        System.out.println("Test avec la graine de STM: "+graine);
        res = RANDU(graine);
        System.out.println("Nombre généré: "+res);

    }
}
