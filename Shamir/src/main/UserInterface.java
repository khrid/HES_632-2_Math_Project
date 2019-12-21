import java.math.BigInteger;
import java.util.Scanner;

public class UserInterface {

    private ShamirSecretSharing shamir;

    public UserInterface() throws Exception {
         shamir = new ShamirSecretSharing();
    }

    public void mainUI() throws Exception {

        System.out.println("Projet Math | David Crittin & Sylvain Meyer");
        System.out.println("--------------------------------------------");
        System.out.println("1. Génération d'un nouveau secret");
        System.out.println("2. Calcul du secret à partir de parts");
        System.out.println("3. Génération d'une nouvelle part de secret");
        System.out.println("4. Suppression d'une part de secret existante");
        System.out.println("5. Modification du seuil");

        switch (userChoice(1,5, "votre choix")){
            case 1:
                generateNewSecretUI();
                break;
            case 2:
                System.out.println("Option 2");
                break;
            case 3:
                generateNewShareUI();
                break;
            case 4:
                System.out.println("Option 4");
                break;
            case 5:
                System.out.println("Option 5");
                break;
        }
    }

    private void generateNewSecretUI() throws Exception {
        int shares = userChoice(2,10, "le nombre de parts");
        int threshold = userChoice(2,10, "le seuil");
        int bit_length = userChoice(32,4096, "la taille du secret");

        shamir.generateNewSecret(shares, threshold, bit_length);
        System.out.println("Secret:\t"+shamir.getSecret());
        for (int i = 0; i < shamir.getShares(); i++) {
            System.out.println("Part "+i+":\t"+shamir.f_de_x(BigInteger.valueOf(i)));
        }

        for (BigInteger bi :
                shamir.getfDeX()) {
            //System.out.println(bi);
        };
        // eq1 = y1 * ((x-x2)/(x1-x2))*((x-x3)/(x1-x3))*((x-x4)/(x1-x4));
    }

    public void generateNewShareUI() throws Exception {
        shamir.generateNewShare();
        System.out.println("Secret:\t"+shamir.getSecret());
        for (int i = 0; i < shamir.getShares(); i++) {
            System.out.println("Part "+i+":\t"+shamir.f_de_x(BigInteger.valueOf(i)));
        }

        for (BigInteger bi :
                shamir.getfDeX()) {
            //System.out.println(bi);
        };
        // eq1 = y1 * ((x-x2)/(x1-x2))*((x-x3)/(x1-x3))*((x-x4)/(x1-x4));
    }

    private int userChoice(int minValue, int maxValue, String text){
        Scanner sc = new Scanner(System.in);
        int userChoice;
        do{
            System.out.println();
            System.out.println("Veuillez entrer "+text+" :");
            userChoice=sc.nextInt();;
        }while (userChoice<minValue || userChoice>maxValue);
        return userChoice;
    }
}
