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

        BigInteger m = BigInteger.probablePrime(ShamirSecretSharing.BIT_LENGTH, new SecureRandom());
        //BigInteger m = new BigInteger(String.valueOf(59));
        int a = 12;
        BigInteger biA = new BigInteger(String.valueOf(a));
        BigInteger biM = new BigInteger(String.valueOf(m));
        BigInteger biC = biA.modInverse(biM);

        ShamirSecretSharing shamir = new ShamirSecretSharing(3, 2);
        assertEquals(biC, shamir.modInverseBigInt(m, new BigInteger(String.valueOf(a))));
    }

}