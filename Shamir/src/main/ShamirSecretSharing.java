import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;

class ShamirSecretSharing {
    /*
    *
    // 1 générer le S avec biginteger
    // 2 aller chercher le nombre premier avec nextprime du s ( p-1 )
    // 3 générer les coefficients du polynome
    // retourner les n parts du secret
    // stocker le nombre de parts du secret
    // stocker le p (step 2)

    // s + a1x + a2x^2 => générer aléatoirement a1 et x2 compris entre 1 et p-1*/
    private int shares;
    private int treshold;
    private int bit_length;
    private BigInteger s;
    private BigInteger p;
    private BigInteger[] pol;
    private BigInteger[] fDeX;

    private JSONObject json = new JSONObject();

    public ShamirSecretSharing() throws Exception {

    }


    public BigInteger getSecret() {
        return s;
    }

    public int getShares() {
        return shares;
    }

    public int getTreshold() {
        return treshold;
    }

    public int getBitLength() {
        return bit_length;
    }

    public void generateNewSecret(int shares, int treshold, int bit_length) throws Exception {

        if (shares <= 1) {
            throw new Exception();
        }
        if (treshold >= shares) {
            throw new Exception();
        }
        if (bit_length < 32 || bit_length > 4096) {
            throw new Exception();
        }

        this.bit_length=bit_length;
        this.s = BigInteger.probablePrime(bit_length, new SecureRandom());
        this.treshold = treshold;
        this.shares = shares;
        this.p = s.nextProbablePrime();

        generateSecret();
    }

    public void generateNewShare() throws Exception {

        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader("C:\\tmp\\shamir\\metadata.json"));
            json = (JSONObject) obj;
        }catch (Exception e){
            e.printStackTrace();
        }


        bit_length=Integer.parseInt(json.get("l").toString());
        treshold = Integer.parseInt(json.get("treshold").toString());
        shares = Integer.parseInt(json.get("shares").toString())+1;
        s = BigInteger.probablePrime(bit_length, new SecureRandom());
        p = s.nextProbablePrime();

        generateSecret();
    }

    private void generateSecret() {
        pol = new BigInteger[shares];
        fDeX = new BigInteger[shares];

        try {
            json.put("shares", shares);
            json.put("treshold", treshold);
            json.put("p", p);
            json.put("s", s);
            json.put("l", bit_length);
            Files.write(Paths.get("C:\\tmp\\shamir\\metadata.json"), json.toJSONString().getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < shares; i++) {
            BigInteger randomNumber;
            do {
                randomNumber = new BigInteger(p.bitLength(), new SecureRandom());
            } while (randomNumber.compareTo(p) >= 0);
            pol[i] = randomNumber;
        }

        for (int i = 0; i < pol.length; i++) {
            //System.out.println(pol[i]);
        }
        //System.out.println();
    }

    public BigInteger f_de_x(BigInteger x) {
        int i = shares-1;
        BigInteger res = BigInteger.ZERO;
        BigInteger base = pol[i];
        while (i > 0) {
            //System.out.println(base);
            res = base.multiply(x).add(pol[i-1]);
            base = res;
            i--;
        }
        try {
            json.put(x, x+":"+res);
            fDeX[x.intValue()] = res;
            Files.write(Paths.get("C:\\tmp\\shamir\\metadata.json"), json.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public BigInteger[] getfDeX() {
        return fDeX;
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
        while (!BigInteger.ZERO.equals(rList.get(i + 1))) {
            i++;
            qList.add(i, rList.get(i - 1).divide(rList.get(i)));
            rList.add(i + 1, rList.get(i - 1).subtract(qList.get(i).multiply(rList.get(i))));
            xList.add(i + 1, xList.get(i - 1).subtract(qList.get(i).multiply(xList.get(i))));
            yList.add(i + 1, yList.get(i - 1).subtract(qList.get(i).multiply(yList.get(i))));
        }
        return yList.get(i).mod(rList.get(0));
    }
}

