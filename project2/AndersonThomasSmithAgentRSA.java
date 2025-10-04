import java.math.BigInteger;



public class AndersonThomasSmithAgent{

private static BigInteger[] encryptDecrypt(BigInteger m, BigInteger e, BigInteger N, BigInteger d){
        //encrypt message 
    BigInteger c = m.modPow(e,N);

    //decrypt message
    BigInteger m2 = c.modPow(d,N);

    BigInteger[] results = {c,m2};
    return results;
}
public static void main(String[] args){
    String NInput = "1180590145325084590940239490622584768749437722435980772180699";
    BigInteger N = new BigInteger(NInput);

    String eInput = "65537";
    BigInteger e = new BigInteger(eInput);

    //use sage to factor N to find P and Q
    String qInput = "967148115841218361396434822121";
    String pInput = "1220692183532008344492309072419";
    BigInteger q = new BigInteger(qInput);
    BigInteger p = new BigInteger(pInput);

    //number of bits in N
    int numOfBitsInN = N.bitLength();

    //find totient
    BigInteger pMinus1 = p.subtract(BigInteger.ONE);
    BigInteger qMinus1 = q.subtract(BigInteger.ONE);
    BigInteger t = pMinus1.multiply(qMinus1);
    //calculate d
    BigInteger d = e.modInverse(t);

    //message
    BigInteger m = new BigInteger("3201087"); //hi you

    BigInteger[] results = encryptDecrypt(m,e,N,d);
    System.out.println("************Flawed Example************");
    System.out.println("N = "+N.toString(16));
    System.out.println("p = "+p.toString(16));
    System.out.println("q = "+q.toString(16));
    System.out.println("Bit-length of N = "+numOfBitsInN);
    System.out.println("d = "+d.toString(16));
    System.out.println("c = "+results[0].toString(16));
    System.out.println("m2 = "+results[1].toString(16));
    System.out.println("**************************************");
}
}


