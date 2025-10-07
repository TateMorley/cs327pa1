import java.math.BigInteger;

public class AndersonThomasSmithAgent {

    private static BigInteger[] encryptDecrypt(BigInteger m, BigInteger e, BigInteger N, BigInteger d) {
        // encrypt message
        BigInteger c = m.modPow(e, N);

        // decrypt message
        BigInteger m2 = c.modPow(d, N);

        BigInteger[] results = { c, m2 };
        return results;
    }

    public static void taskOne() {
        // Flawed Example Using RSA
        String NInput = "1180590145325084590940239490622584768749437722435980772180699";
        BigInteger N = new BigInteger(NInput);

        String eInput = "65537";
        BigInteger e = new BigInteger(eInput);

        // use sage to factor N to find P and Q
        String qInput = "967148115841218361396434822121";
        String pInput = "1220692183532008344492309072419";
        BigInteger q = new BigInteger(qInput);
        BigInteger p = new BigInteger(pInput);

        // number of bits in N
        int numOfBitsInN = N.bitLength();

        // find totient
        BigInteger pMinus1 = p.subtract(BigInteger.ONE);
        BigInteger qMinus1 = q.subtract(BigInteger.ONE);
        BigInteger t = pMinus1.multiply(qMinus1);
        // calculate d
        BigInteger d = e.modInverse(t);

        // message
        BigInteger m = new BigInteger("3"); // hi you

        BigInteger[] results = encryptDecrypt(m, e, N, d);
        System.out.println("************Flawed Example************");
        System.out.println("N = " + N.toString(16));
        System.out.println("p = " + p.toString(16));
        System.out.println("q = " + q.toString(16));
        System.out.println("Bit-length of N = " + numOfBitsInN);
        System.out.println("Bit-length of p = " + p.bitLength());
        System.out.println("Bit-length of q = " + q.bitLength());
        System.out.println("d = " + d.toString(16));
        System.out.println("c = " + results[0].toString(16));
        System.out.println("m2 = " + results[1].toString(16));
        System.out.println("**************************************");
    }

    public static void taskTwo() {
        // Real world example
        String givenP = "bdf78a7a486847dc2fc6cccf45161dad36641ce09a1907ff5c5c088d3f9011135d0b77a75faabc6ff9d42499f9949b61ca5e32b5458b5240e2aafb18d9486bddbb80014b1f8945947eaafe6964a3ea96f345b2f0a93e7db100ab21c7b38d2e0d19fddfe8b8fcf8f593aae667edc15e76d9af847886e2db47a4b53243950eed016439c5874b5de2aba1065faeefdf1d9756ac8bc453b379ae18a85f3e911205b841f8da08ab52963b34661150938c2de16bf910a497049352422873a75531ca59";
        String givenQ = "ff8b62ff55f9f7a5a279db0960921f1b9f04172996867293b3987b1ad49160a2539156bc2c56489a046ede63b34c91ac5fe897d7865c0b62c7eed50c71e62163a6f9795653c6c4e1ad69477739f92b39bb8b9c99d0c780b641abccb307f405f141668847c25fcf2305e62902e6e5325bace643097581bd14f36008c0c8b33e27d06615728dcaa293f18c6a350ab3b7f634a66a097ecedaac8421ca24f24123236f57b4f520739d949594bd6efb029609282c9e87622b0a16514789001df5f545";
        String givenE = "65537";

        // Step 0 find N, p, and q
        BigInteger p = new BigInteger(givenP, 16);
        BigInteger q = new BigInteger(givenQ, 16);
        BigInteger e = new BigInteger(givenE);
        BigInteger N = p.multiply(q);
        BigInteger m = new BigInteger("3");
        BigInteger c = m.modPow(e, N);
        BigInteger d = e.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));

        BigInteger w = p.multiply(q);
        BigInteger w1 = w.divide(p);
        BigInteger w2 = w.divide(q);

        BigInteger y1 = w1.modInverse(p);
        BigInteger y2 = w2.modInverse(q);

        BigInteger z1 = w1.multiply(y1);
        BigInteger z2 = w2.multiply(y2);

        // RSA benchmarking
        BigInteger rsaDecrypted = null;
        long startRSA = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            rsaDecrypted = c.modPow(d, N);
        }
        long endRSA = System.nanoTime();
        double RSAAvgTime = (endRSA - startRSA) / 1_000_000_000.0 / 1000;
        double RSAkbps = N.bitLength() / RSAAvgTime / 1000;

        // CRT benchmarking
        BigInteger crtDecrypted = null;
        long startCRT = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            BigInteger a1 = c.modPow(dp, p);
            BigInteger a2 = c.modPow(dq, q);
            BigInteger leftSide = a1.multiply(z1);
            BigInteger rightSide = a2.multiply(z2);
            BigInteger total = rightSide.add(leftSide);
            crtDecrypted = total.mod(N);
        }
        long endCRT = System.nanoTime();
        double CRTAvgTime = (endCRT - startCRT) / 1_000_000_000.0 / 1000;
        double CRTkbps = N.bitLength() / CRTAvgTime / 1000;
        boolean fast = CRTkbps >= 1_000_000;

        System.out.println("\n**********Real World Example**********");
        System.out.println("Bob's public key (N, e): ");
        System.out.println("N = 0x" + N.toString(16));
        System.out.println("e = 0x" + e.toString(16));
        System.out.println("Bit-length of Bob's N is " + N.bitLength());
        System.out.println("Bit-length of p is " + p.bitLength());
        System.out.println("Bit-length of q is " + q.bitLength());
        System.out.println("Bob's private key d is 0x" + d.toString(16));
        System.out.println("Bob's ciphertext is 0x" + c.toString(16));
        System.out.println("RSA decrypted plaintext = 0x" + rsaDecrypted.toString(16));
        System.out.println("CRT decrypted plaintext = 0x" + crtDecrypted.toString(16));
        System.out.println("RSA decryption speed is " + RSAkbps + "kbps");
        System.out.println("CRT decryption speed is " + CRTkbps + "kbps");
        System.out.println("CRT is " + (RSAAvgTime / CRTAvgTime) + "x faster");
        System.out.println("Fast enough for gigabit internet? " + fast);
        System.out.println("**************************************");
    }

    public static void main(String[] args) {
        taskOne();
        taskTwo();
    }
}
