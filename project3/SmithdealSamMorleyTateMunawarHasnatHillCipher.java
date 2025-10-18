public class SmithdealSamMorleyTateMunawarHasnatHillCipher {

    String encodeString = "abcdefghijklmnopqrstuvwxyz";
    final int divisor = 26;

    public static int[][] findDecryptionKey(int encryptionKey[][]){
        return null;
    }

    public static int[] encrypt(int plainText[], int encryptionKey[][]){
        return null;
    }

    public static int[] decrypt(int cipherText[], int decryptionKey[][]){
        return null;
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

    }
}
