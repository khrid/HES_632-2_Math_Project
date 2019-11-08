import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShamirSecretSharingTest {
    @Test
    void sharesAndTresholdAreSet() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing(3, 2);
        assertEquals(shamir.getShares(), 3);
        assertEquals(shamir.getTreshold(), 2);
    }

    @Test
    void tresholdEqualsShares() throws Exception {
        assertThrows(Exception.class, () -> {
            new ShamirSecretSharing(2, 2);
        });
    }

    @Test
    void tresholdGreaterThanShares() throws Exception {
        assertThrows(Exception.class, () -> {
            new ShamirSecretSharing(2, 3);
        });
    }

    @Test
    void notEnoughShares() throws Exception {
        assertThrows(Exception.class, () -> {
            new ShamirSecretSharing(1, 2);
        });
    }

    @Test
    void validateInverseMultiplicatif() throws Exception {
        ShamirSecretSharing shamir = new ShamirSecretSharing(3, 2);
        BigInteger m = BigInteger.probablePrime(ShamirSecretSharing.BIT_LENGTH, new SecureRandom());

        for (BigInteger i = BigInteger.ONE; i.compareTo(m) < 0; i = i.nextProbablePrime().multiply(i)) {
            System.out.println(i);
            BigInteger biC = i.modInverse(m);
            assertEquals(biC, shamir.modInverseBigInt(m, i));
        }
    }

}