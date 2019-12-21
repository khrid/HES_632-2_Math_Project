import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShamirSecretSharingTest {
    @Test
    void sharesAndTresholdAreSet() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing();
        shamir.generateNewSecret(3, 2, 32);
        assertEquals(shamir.getShares(), 3);
        assertEquals(shamir.getTreshold(), 2);
    }

    @Test
    void tresholdEqualsShares() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing();
        assertThrows(Exception.class, () -> {
            shamir.generateNewSecret(3, 2, 32);;
        });
    }

    @Test
    void tresholdGreaterThanShares() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing();
        assertThrows(Exception.class, () -> {
            shamir.generateNewSecret(3, 2, 32);;
        });
    }

    @Test
    void notEnoughShares() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing();
        assertThrows(Exception.class, () -> {
            shamir.generateNewSecret(3, 2, 32);;
        });
    }

    @Test
    void validateInverseMultiplicatif() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing();
        shamir.generateNewSecret(3, 2, 32);
        BigInteger m = BigInteger.probablePrime(shamir.getBitLength(), new SecureRandom());

        for (BigInteger i = BigInteger.ONE; i.compareTo(m) < 0; i = i.nextProbablePrime().multiply(i)) {
            System.out.println(i);
            BigInteger biC = i.modInverse(m);
            assertEquals(biC, shamir.modInverseBigInt(m, i));
        }
    }

}