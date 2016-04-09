import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ExtractingWork {

	private static final int[][] H = {	{0, 0, 0, 1, 1, 1, 1},
										{0, 1, 1, 0, 0, 1, 1},
										{1, 0, 1, 0, 1, 0, 1}};
	private static Scanner coverImageWithEmbeddedMessageScanner;
	private static PrintWriter printWriter;
	private static final int xDimension = 7;
	private static final int mDimension = 3;
	
	private static void inizialization() {
		try {
			coverImageWithEmbeddedMessageScanner = new Scanner(new File("coverImageWithEmbeddedMessage.txt"));
			printWriter = new PrintWriter(new File("extractedMessage.txt"));
		} catch (Exception exception) {
			System.out.println("Something wrong: " + exception.getMessage());
		}
	}
	
	public static void extractMessage() {
		inizialization();
		int[] y = new int[xDimension];
		int[] extractedMessagePart = new int[mDimension];
		
		for (int i = 0; i < 3; i++) {
			y = readCoverImageWithEmbeddedMessagePart();
			extractedMessagePart = extractMessagePart(y);
			printExtractedMessage(extractedMessagePart);
		}
		
		printWriter.flush();
		printWriter.close();
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

	private static int[] extractMessagePart(int[] y) {
		int[] result = new int[mDimension];
		
		for (int i = 0; i < mDimension; i++) {
			int summ = 0;
			for (int j = 0; j < xDimension; j++) {
				summ = ((summ == y[j] * H[i][j]) ? 0 : 1);
			}
			result[i] = summ;
		}
		
		return result;
	}

	private static void printExtractedMessage(int[] m) {
		for (int i = 0; i < mDimension; i++) {
			printWriter.print(m[i] + " ");
		}
	}
}
