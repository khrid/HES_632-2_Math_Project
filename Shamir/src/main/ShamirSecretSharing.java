import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

class ShamirSecretSharing {
    /*
    *
    // 1 générer le S avec biginteger
    // 2 aller chercher le nombre premier avec nextprime du s ( p-1 )
    // 3 générer les coefficiens du polynome
    // retourner les n parts du secret
    // stocker le nombre de parts du secret
    // stocker le p (step 2)

    // s + a1x + a2x^2 => générer aléatoirement a1 et x2 compris entre 1 et p-1*/
    static final int BIT_LENGTH = 256;

    private int shares;
    private int treshold;
    private BigInteger secret;
    private BigInteger nextPrime;

    public ShamirSecretSharing(int shares, int treshold) throws Exception {

        if (shares <= 1) {
            throw new Exception();
        }
        if (treshold >= shares) {
            throw new Exception();
        }

        this.secret = BigInteger.probablePrime(BIT_LENGTH, new SecureRandom());
        this.treshold = treshold;
        this.shares = shares;
        this.nextPrime = secret.nextProbablePrime();
    }


    public int getShares() {
        return shares;
    }

    public int getTreshold() {
        return treshold;
    }

    public BigInteger modInverseBigInt(BigInteger a, BigInteger b) {

        ArrayList<BigInteger> rList = new ArrayList<>();
        ArrayList<BigInteger> qList = new ArrayList<>();
        ArrayList<BigInteger> xList = new ArrayList<>();
        ArrayList<BigInteger> yList = new ArrayList<>();

        qList.add(0, BigInteger.ZERO);
        rList.add(0, a);
        rList.add(1, b);
        xList.add(0, BigInteger.ONE);
        xList.add(1, BigInteger.ZERO);
        yList.add(0, BigInteger.ZERO);
        yList.add(1, BigInteger.ONE);

        int i = 0;
        while (!rList.get(i + 1).equals(BigInteger.ZERO)) {
            i++;
            qList.add(i, rList.get(i - 1).divide(rList.get(i)));
            rList.add(i + 1, rList.get(i - 1).subtract(qList.get(i).multiply(rList.get(i))));
            xList.add(i + 1, xList.get(i - 1).subtract(qList.get(i).multiply(xList.get(i))));
            yList.add(i + 1, yList.get(i - 1).subtract(qList.get(i).multiply(yList.get(i))));
        }

        return yList.get(i);
    }
}

