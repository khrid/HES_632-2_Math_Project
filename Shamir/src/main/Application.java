import java.math.BigInteger;

public class Application {
    public static void main(String[] args) throws Exception {
        int shares = 4, threshold = 3;
        ShamirSecretSharing shamir = new ShamirSecretSharing(shares, threshold);
        System.out.println(shamir.getSecret());
        shamir.generate();
        for (int i = 0; i < shamir.getShares(); i++) {
            System.out.println(i+":"+shamir.f_de_x(BigInteger.valueOf(i)));
        }
        for (BigInteger bi :
             shamir.getfDeX()) {
            System.out.println(bi);
        };

        // eq1 = y1 * ((x-x2)/(x1-x2))*((x-x3)/(x1-x3))*((x-x4)/(x1-x4));

    }
}
