import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class EmbeddingWork {

	private static final int[][] H = {	{0, 0, 0, 1, 1, 1, 1},
										{0, 1, 1, 0, 0, 1, 1},
										{1, 0, 1, 0, 1, 0, 1}};
	
	private static final int xDimension = 7;
	private static final int mDimension = 3;
	private static Scanner coverImageScanner;
	private static Scanner messageScanner;
	private static PrintWriter printWriter;
	
	private static void initialization() {
		try {
			coverImageScanner = new Scanner(new File("coverImage.txt"));
			messageScanner = new Scanner(new File("message.txt"));
			printWriter = new PrintWriter(new File("coverImageWithEmbeddedMessage.txt"));
		} catch (Exception exception) {
			System.out.println("Something wrong: " + exception.getMessage());
		}
	}
	public static void embedMessage() throws Exception {
		initialization();
		int[] x = new int[xDimension];
		int[] m = new int[mDimension];
		int[] Hx;
		int[] sub;
		int s;
		int[] y;
		
		for (int i = 0; i < 3; i++) {
			x = readCoverImagePart();
			m = readMessagePart();
			Hx = multiplicationHtoX(x);
			sub = subtractionMandHX(m, Hx);
			s = getSFromBinaryPresentation(sub);
			y = embed(x, s);
			printPartWithEmbeddedMessage(y);
		}
		
		printWriter.flush();
		printWriter.close();
	}

	private static int[] readCoverImagePart() {
		int[] result = new int[xDimension];
		
		for (int i = 0; i < xDimension; i++) {
			if (coverImageScanner.hasNext()) {
				result[i] = coverImageScanner.nextByte();
			}
		}
		 
		return result;
	}

	private static int[] readMessagePart() {
		int[] result = new int[mDimension];
		
		for (int i = 0; i < mDimension; i++) {
			if (messageScanner.hasNext()) {
				result[i] = messageScanner.nextByte();
			}
		}
		
		return result;
	}

	private static int[] multiplicationHtoX(int[] x) {
		int[] result = new int[mDimension];
		
		for (int i = 0; i < mDimension; i++) {
			int summ = 0;
			for (int j = 0; j < xDimension; j++) {
				summ = ((summ == x[j] * H[i][j]) ? 0 : 1);
			}
			result[i] = summ;
		}
		
		return result;
	}

	private static int[] subtractionMandHX(int[] m, int[] Hx) {
		int[] result = new int[mDimension];
		
		for (int i = 0; i < mDimension; i++) {
			result[i] = ((m[i] == Hx[i]) ? 0 : 1);
		}
		
		return result;
	}

	private static int getSFromBinaryPresentation(int[] sub) {
		int result = 0;
		
		for (int i = 0; i < mDimension; i++) {
			result += (int)(Math.pow(2, mDimension - i - 1) * sub[i]);
		}
		
		return result - 1;
	}

	private static int[] embed(int[] x,int s) {
		if (s != -1) {
			x[s] = ((x[s] == 1) ? 0 : 1);
		}
		return x;
	}

	private static void printPartWithEmbeddedMessage(int[] y) {
		for (int i = 0; i < xDimension; i++) {
			printWriter.print(y[i] + " ");
		}
	}
}
