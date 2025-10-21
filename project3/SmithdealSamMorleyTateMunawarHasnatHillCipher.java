import java.util.Arrays;

public class SmithdealSamMorleyTateMunawarHasnatHillCipher {

    String encodeString = "abcdefghijklmnopqrstuvwxyz";
    int divisor = 26;

    public int[][] findDecryptionKey(int encryptionKey[][]){
        int[][] decryptionKey = new int[2][2];
        int a = encryptionKey[0][0];
        int b = encryptionKey[0][1];
        int c = encryptionKey[1][0];
        int d = encryptionKey[1][1];
        int denominator = (a*d)-(b*c);

        if(denominator>divisor){
            denominator = denominator%divisor;
        }

        decryptionKey[0][0] = ((d * xgcd(denominator,divisor))%divisor + divisor)%divisor;
        decryptionKey[0][1] = ((-b * xgcd(denominator,divisor))%divisor + divisor)%divisor;
        decryptionKey[1][0] = ((-c * xgcd(denominator,divisor))%divisor + divisor)%divisor;
        decryptionKey[1][1] = ((a * xgcd(denominator,divisor))%divisor + divisor)%divisor;

        return decryptionKey;
    }

    public int[] encrypt(int plainText[], int encryptionKey[][]){
        int[] encryptedMsg;
        int currentIndex = 0;
        if(plainText.length%2==1){
            encryptedMsg = new int[plainText.length+1];
            plainText = Arrays.copyOf(plainText, plainText.length + 1);
            //25 used to pad with 'z' if message length uneven
            plainText[plainText.length-1] = 25;
        }
        else{
            encryptedMsg = new int[plainText.length];
        }

        for(int x = 0;x<plainText.length-1;x+=2){
            encryptedMsg[currentIndex++] = (encryptionKey[0][0] * plainText[x] + encryptionKey[0][1] * plainText[x+1])%divisor;
            encryptedMsg[currentIndex++] = (encryptionKey[1][0] * plainText[x] + encryptionKey[1][1] * plainText[x+1])%divisor;
        }

        //times
        return encryptedMsg;
    }

    public int[] decrypt(int cipherText[], int decryptionKey[][]){
        int[] decryptedMsg = new int[cipherText.length];
        int currIndex = 0;

        for (int i = 0; i < cipherText.length; i += 2)
        {
            int first = (decryptionKey[0][0] * cipherText[i] + decryptionKey[0][1] * cipherText[i + 1]) % divisor;
            int second = (decryptionKey[1][0] * cipherText[i] + decryptionKey[1][1] * cipherText[i + 1]) % divisor;

            decryptedMsg[currIndex++] = (first % 26 + 26) % 26;
            decryptedMsg[currIndex++] = (second % 26 + 26) % 26;
        }
        
        return decryptedMsg;
    }

    //converts a string to an array of ints
    //each int in the array returned, is the index of that letter in the alphabet
    //example: "abc" returns [0,1,2]
    public int[] textToInt(String text){
        int[] result = new int[text.length()];
        text = text.toLowerCase();
        for(int x = 0;x<text.length();x++){
            result[x] = encodeString.indexOf(text.charAt(x));
        }
        return result;
    }

    public String intToText(int[] ints) {
        StringBuilder result = new StringBuilder(); // or StringBuffer if you prefer

        for (int x = 0; x < ints.length; x++) {
            result.append(encodeString.charAt(ints[x]));
        }

        return result.toString(); // convert StringBuilder to String
    }


	public int xgcd(int inE, int inZ) {
		int s_previous = 1;
		int t_previous = 0;

		int s_current = 0;
		int t_current = 1;

		int d_previous = inZ;
		int d_current = inE;

		int q;
		int d_next;
		int s_next;
		int t_next;
		while (d_current != 0) {
			// d and q column
			q = d_previous / d_current;
			d_next = d_previous - (d_current * q);

			// s and t column
			s_next = s_previous - (s_current * q);
			t_next = t_previous - (t_current * q);

			// prep for next iteration
			s_previous = s_current;
			s_current = s_next;

			t_previous = t_current;
			t_current = t_next;

			d_previous = d_current;
			d_current = d_next;
		}

		// Inverse exists
		if (d_previous == 1)
		{
			if (t_previous > 0)
			{
				return t_previous;
			} else
			{
				return t_previous + inZ;
			}
		}

		return -1;
	}

    public static void main(String[] args) throws Exception {
        int encryptionKey[][] = {{16,7},{9,14}};
        SmithdealSamMorleyTateMunawarHasnatHillCipher instance = new SmithdealSamMorleyTateMunawarHasnatHillCipher();
        String msg = "jmucsiscool";
        int decryptionKey[][] = instance.findDecryptionKey(encryptionKey);
        int encryptedMsg[] = instance.encrypt(instance.textToInt(msg), encryptionKey);
        int decryptedMsg[] = instance.decrypt(encryptedMsg, decryptionKey);

        System.out.println("MESSAGE: "+msg);
        System.out.print("ENCRYPTION KEY: ");
        System.out.println("("+encryptionKey[0][0] + " " + encryptionKey[0][1] + ")");
        System.out.println("\t\t("+encryptionKey[1][0] + " " + encryptionKey[1][1] + ")");
        System.out.print("DECRYPTION KEY: ");
        System.out.println("("+decryptionKey[0][0] + " " + decryptionKey[0][1] + ")");
        System.out.println("\t\t("+decryptionKey[1][0] + " " + decryptionKey[1][1] + ")");
        System.out.println("ENCRYPTED MESSAGE: "+instance.intToText(encryptedMsg));
        System.out.println("DECRYPTED MESSAGE: "+instance.intToText(decryptedMsg));
    }
}
