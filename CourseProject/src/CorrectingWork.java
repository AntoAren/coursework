import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class CorrectingWork {

	private static final int[][] H = {	{0, 0, 0, 1, 1, 1, 1},
										{0, 1, 1, 0, 0, 1, 1},
										{1, 0, 1, 0, 1, 0, 1}};

	private static final int xDimension = 7;
	private static final int mDimension = 3;
	private static Scanner coverImageScanner;
	private static Scanner coverImageWithEmbeddedMessageScanner;
	private static Scanner messageScanner;
	private static PrintWriter printWriter;

	private static void initialization() {
		try{
			coverImageScanner = new Scanner(new File("coverImage.txt"));
			messageScanner = new Scanner(new File("message.txt"));
			coverImageWithEmbeddedMessageScanner = new Scanner(new File("coverImageWithEmbeddedMessage.txt"));
			printWriter = new PrintWriter(new File("coverImageWithEmbeddedMessageAndCorrection.txt"));
		} catch (Exception exception) {
			System.out.println("Something wrong: " + exception.getMessage());
		}
	}
	public static void correctEmbedding() {
		initialization();
		int[] x = new int[xDimension];
		int[] y = new int[xDimension];
		int[] y_ = new int[xDimension];
		int[] m = new int[mDimension];
		int numberOnesInY;
		int numberOnesInX;
		int gamma;
		
		for (int i = 0; i < 3; i++) {
			x = readCoverImagePart();
			y = readCoverImageWithEmbeddedMessagePart();
			m = readMessagePart();
			numberOnesInX = calculateNumberOnes(x);
			numberOnesInY = calculateNumberOnes(y);
			gamma = defineGamma(m, x);
			if (numberOnesInY > numberOnesInX) {
				y_ = replaceOneToZero(y, gamma);
			}
			if (numberOnesInY < numberOnesInX) {
				y_ = replaceZeroToOne(y, gamma);
			}
			printPartWithEmbeddedMessageAndCorrection(y_);
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

	private static int[] readCoverImageWithEmbeddedMessagePart() {
		int[] result = new int[xDimension];
		
		for (int i = 0; i < xDimension; i++) {
			if (coverImageWithEmbeddedMessageScanner.hasNext()) {
				result[i] = coverImageWithEmbeddedMessageScanner.nextByte();
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

	private static int calculateNumberOnes(int[] array) {
		int summ = 0;

		for (int i = 0; i < xDimension; i++) {
			summ += array[i];
		}

		return summ;
	}

	private static int defineGamma(int[] m, int[] x) {
		int gamma = 0;
		int[] Hx = new int[mDimension];
		int[] sub = new int[mDimension];
		
		Hx = multiplicationHtoX(x);
		sub = subtractionMandHX(m, Hx);
		gamma = getGammaFromBinaryPresentation(sub);
		
		return gamma;
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

	private static int getGammaFromBinaryPresentation(int[] sub) {
		int result = 0;
		
		for (int i = 0; i < mDimension; i++) {
			result += (int)(Math.pow(2, mDimension - i - 1) * sub[i]);
		}
		
		return result - 1;
	}

	private static int[] replaceZeroToOne(int[] y, int gamma) {
		for (int i = 0; i < xDimension; i++) {
			if ((y[i] == 0) && (gamma != i)) {
				y[i] = 1;
				break;
			}
		}
		
		return y;
	}

	private static int[] replaceOneToZero(int[] y, int gamma) {
		for (int i = 0; i < xDimension; i++) {
			if ((y[i] == 1) && (gamma != i)) {
				y[i] = 0;
				break;
			}
		}
		
		return y;
	}

	private static void printPartWithEmbeddedMessageAndCorrection(int[] y_) {
		for (int i = 0; i < xDimension; i++) {
			printWriter.print(y_[i] + " ");
		}
	}
}
