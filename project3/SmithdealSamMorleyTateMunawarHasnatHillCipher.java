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
            encryptedMsg[currentIndex++] = (encryptionKey[0][0] * plainText[x] + encryptionKey[0][1] * plainText[x+1])%26;
            encryptedMsg[currentIndex++] = (encryptionKey[1][0] * plainText[x] + encryptionKey[1][1] * plainText[x+1])%26;
        }

        //times
        return encryptedMsg;
    }

    public static int[] decrypt(int cipherText[], int decryptionKey[][]){
        return null;
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
        int test[][] = {{9,4},{5,7}};
        SmithdealSamMorleyTateMunawarHasnatHillCipher t = new SmithdealSamMorleyTateMunawarHasnatHillCipher();
        int answer[][] = t.findDecryptionKey(test);

        // for(int x = 0;x<2;x++){
        //     for(int y = 0;y<2;y++){
        //         System.out.println(answer[x][y]);
        // }
        // }
        String word = "runmanrun";
        int v[] = t.textToInt(word);
        int g[] = t.encrypt(v,test);
        for(var x = 0;x<g.length;x++){
            System.out.println(g[x]);
        }

    }
}
