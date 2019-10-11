
public class Application {
    public static void main(String[] args) throws Exception {
        int shares = 4, treshold = 3;
        ShamirSecretSharing share = new ShamirSecretSharing(shares, treshold);
    }
}
