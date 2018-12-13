import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class tåg {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Hur många platser?");
		//int matrixRowSeats = scan.nextInt();
		int matrixRowSeats = 309;
		
		System.out.println("Hur många stationer?");
		int matrixColStations = 15;
		//int matrixColStations = scan.nextInt() - 1;


		int[][] matrix = new int[matrixRowSeats][matrixColStations];
		int[][] matrixRandomComparison = new int[matrixRowSeats][matrixColStations];

		int[] seatcombs = possibleSeatCombs(matrix, matrixColStations, matrixRowSeats);
		int[] seatcombsRandom = possibleSeatCombs(matrix, matrixColStations, matrixRowSeats);
		// Enter Matrix Data
		createTrain(matrix, matrixRowSeats, matrixColStations);
		// Skriv ut tåget
		printMatrix(matrix, matrixRowSeats, matrixColStations);

		
		
		System.out.println("Hur många passagerare max?");
		int r = 0;
		int passagerare = scan.nextInt();
		int[] ourSum = new int[passagerare/20];
		int[] randomSum = new int[passagerare/20];
		while(r < passagerare/20){
			placePassengersSmart(matrix, matrixRowSeats, matrixColStations, r*20);
			placePassengerRandomPick(matrixRandomComparison, matrixRowSeats, matrixColStations, r*20);
			randomSum[r] = sumMatrix(matrixRandomComparison, matrixRowSeats, matrixColStations);
			ourSum[r] = sumMatrix(matrix, matrixRowSeats, matrixColStations);
			System.out.println(Arrays.toString(randomSum));
			System.out.println(Arrays.toString(ourSum));
			for(int kk = 0; kk < matrixRowSeats; kk++) {
				seatcombs[kk] = 0;
				seatcombsRandom[kk] = 0;
			}
			r = r+1;

			createTrain(matrix, matrixRowSeats, matrixColStations);
			createTrain(matrixRandomComparison, matrixRowSeats, matrixColStations);
		}
		/*seatcombs = possibleSeatCombs(matrix, matrixColStations, matrixRowSeats);
		System.out.println(Arrays.toString(seatcombs));
		System.out.println("Detta är tåget när alla passagerare placerats ut");
		printMatrix(matrix, matrixRowSeats, matrixColStations);
		printMatrix(matrixRandomComparison, matrixRowSeats, matrixColStations);
		seatcombs = possibleSeatCombs(matrix, matrixColStations, matrixRowSeats);
		seatcombsRandom = possibleSeatCombs(matrixRandomComparison, matrixColStations, matrixRowSeats);
		System.out.println("Här är alla kombinationer som är kvar för slumpen!");
		System.out.println(Arrays.toString(seatcombsRandom));
		System.out.println("Och här är alla kombinationer som är kvar för vår beräkning!");
		System.out.println(Arrays.toString(seatcombs));
		System.out.println(" ");
		System.out.println(" ");
		
		int randomMatrixPassengers = sumMatrix(matrixRandomComparison, matrixRowSeats, matrixColStations);
		int ourMatrixPassengers = sumMatrix(matrix, matrixRowSeats, matrixColStations);
		
		System.out.println("Här är kombinationerna för random");
		int randomSum = sumArray(seatcombsRandom);
		System.out.println(+randomSum);
		System.out.println("Här är kombinationerna för oss");
		int oursum = sumArray(seatcombs);
		System.out.println(+oursum);
		
		System.out.println("Vi hade " +ourMatrixPassengers +" passagerarenheter.");
		System.out.println("Random hade " +randomMatrixPassengers +" passagerarenheter.");
	*/
	}

	public static void createTrain(int[][] matrix, int matrixRowSeats, int matrixColStations) {

		for (int i = 0; i < matrixRowSeats; i++) {
			for (int j = 0; j < matrixColStations; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	public static void printMatrix(int[][] matrix, int matrixRowSeats, int matrixColStations) {
		System.out.println("Detta är ditt tåg! ");

		for (int i = 0; i < matrixRowSeats; i++) {
			System.out.print('[');
			for (int j = 0; j < matrixColStations; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print(']');
			System.out.println();
		}
	}

	public static int[] possibleSeatCombs(int[][] matrix, int matrixColStation, int matrixRowSeats) {
		int x = 0;
		int oldx;
		int r = 0;

		int[] MatrixRowCombs = new int[matrixRowSeats];
		for (int i = 0; i < matrixRowSeats; i++) { // för varje plats
			for (int j = 0; j < matrixColStation; j++) { // så räknas kombinationerna av stationer ut
				if (matrix[i][j] != 1) {
					oldx = x;
					r = r + 1;
					x = oldx + r; // antal plats-kombinationer just nu
				} else { // om det sitter någon i vägen, börja om efter denne
					MatrixRowCombs[i] = MatrixRowCombs[i] + x; // lägg till de föregående kombinationerna
					x = 0;
					r = 0;
				}

			}
			MatrixRowCombs[i] = MatrixRowCombs[i] + x; // på raden i (dvs för varje plats)
			oldx = 0;
			r = 0;
			x = 0;
			// System.out.println(MatrixRowCombs[i]);
		}
		return MatrixRowCombs;
	}

	public static void placePassenger(int[][] matrix, int fromStation, int toStation, int maxSeats, int totalStations) {
		int passTest = 0;
		int distTravel = toStation - fromStation;
		int[] seatDifference = new int[maxSeats];

		if (fromStation >= 0 && toStation >= 0) {
			// testar för alla rader (stationer)
			for (int j = 0; j < maxSeats; j++) {
				// testar för alla kolumner (platser)
				for (int i = fromStation; i < toStation; i++) {
					if (matrix[j][i - 1] == 0) {
						passTest = passTest + 1;
					} else {
						i = toStation;
						passTest = 0;
					}

					// Här placeras personen ut om det GÅR
					if (passTest == distTravel) {

						int[] possibleSeatCombsBefore = possibleSeatCombs(matrix, totalStations, maxSeats);
						// System.out.println(Arrays.toString(possibleSeatCombsBefore));

						for (int placeSeats = fromStation - 1; placeSeats < toStation - 1; placeSeats++) {
							matrix[j][placeSeats] = 1;
						}

						// kollar hur bra placering det blir
						// System.out.println(" ");
						// System.out.println("Såhär blir det om du placeras ut");
						// printMatrix(testMatrix, maxSeats, totalStations);
						// System.out.println("Med dessa kombinationerna");
						int[] possibleSeatCombsAfter = possibleSeatCombs(matrix, totalStations, maxSeats);
						// System.out.println(Arrays.toString(possibleSeatCombsAfter));
						// combAfterPlaced[j] = possibleSeatCombsAfter[j];

						// for(int k = 0; k < maxSeats; k++){ //alla kombinationer
						// seatDifference[k] = possibleSeatCombsAfter[k] - possibleSeatCombsBefore[k];
						// }
						seatDifference[j] = Math.abs(possibleSeatCombsBefore[j] - possibleSeatCombsAfter[j]);
						// slut på checka läget

						for (int placeSeats = fromStation - 1; placeSeats < toStation - 1; placeSeats++) {
							// tar bort din placering
							matrix[j][placeSeats] = 0;
						}
						// System.out.println("Såhär blir det om du placeras ut");
						// printMatrix(testMatrix, maxSeats, totalStations);

					} else {
						seatDifference[j] = 0;
					}
				}
				passTest = 0;
			}
			passTest = 0;
		}
		int startPosition = StartPosition(seatDifference);
		if (startPosition != -1) {
			for (int placeSeats = fromStation - 1; placeSeats < toStation - 1; placeSeats++) {
				matrix[startPosition][placeSeats] = 1;
				if (placeSeats == toStation - 2) {
					// System.out.println("Detta är när du blivit placerad :)");
					// printMatrix(matrix, maxSeats, totalStations);
				}
			}

		} else {
			// System.err.println("Kunde inte placera dig!");
			// System.out.println(" ");
			// System.out.println(" ");
		}

		// System.out.println("Såhär blir det om du placeras ut");
		// printMatrix(testMatrix, maxSeats, totalStations);
		// System.out.println(" ");
		// System.out.println("Detta är hur många kombinationer du förlorar med
		// respektive val av plats");
		// System.out.println(Arrays.toString(seatDifference));

	}

	// Hitta minsta värdet i array, för startposition
	public static int StartPosition(int[] arr1) {
		int index = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] < min && arr1[i] != 0) {
				min = arr1[i];
				index = i;
			} else if (arr1[i] == 0) {

			}

		}
		if (min == 0 || min == Integer.MAX_VALUE) {
			index = -1;
		}
		return index;
	}

	public static int whenPlaced(int[][] matrix, int maxStations, int maxSeats) {
		Scanner scanSeat = new Scanner(System.in);
		int whereFrom = 0;
		int whereTo = 0;

		System.out.println("Varifrån vill du åka?");
		while (whereFrom < 1 || whereFrom > maxStations + 1) {
			whereFrom = scanSeat.nextInt() - 1;
			if (whereFrom < 1 || whereFrom > maxStations + 1) {
				System.out.println("Den stationen finns inte kompis! Testa igen!");
			}
		}
		System.out.println("Varifrån vill du åka?");
		while (whereTo < whereFrom || whereTo > maxStations + 1) {
			whereTo = scanSeat.nextInt() - 1;
			if (whereTo < whereFrom || whereTo > maxStations + 1) {
				System.out.println("Den stationen finns inte kompis! Testa igen!");
			}
		}

		for (int i = 0; i < maxSeats; i++) {

		}

		int x = 0;
		return x;
	}

	/* Placera passagerare på vilken plats som helst */
	public static void placePassengerRandomPick(int[][] matrixRandomComparison, int maxSeats, int totalStations, int Passagerare) {
		Random rand = new Random(10000);
		//Scanner scan = new Scanner(System.in);
		//System.out.println("Hur många passagerare vill du ha?");
		//int totalPassengers = scan.nextInt();

		for (int i = 0; i < Passagerare; i++) {
			int fromRandom = 0;
			int toRandom = 0;
			while (fromRandom == toRandom) {
				int firstNumber = rand.nextInt(totalStations + 1) + 1;
				int secondNumber = rand.nextInt(totalStations + 1) + 1;
				if (firstNumber > secondNumber) {
					toRandom = firstNumber;
					fromRandom = secondNumber;
				} else {
					toRandom = secondNumber;
					fromRandom = firstNumber;
				}
			}
			placePassengerRandom(matrixRandomComparison, fromRandom, toRandom, maxSeats, totalStations);
		}
	}

	public static void placePassengersSmart(int[][] matrix, int maxSeats, int totalStations, int Passagerare) {
		Random rand = new Random(10000);
		//Scanner scan = new Scanner(System.in);
		//System.out.println("Hur många passagerare vill du ha?");
		//int totalPassengers = scan.nextInt();
		for (int i = 0; i < Passagerare; i++) {
			int fromRandom = 0;
			int toRandom = 0;
			while (fromRandom == toRandom) {
				int firstNumber = rand.nextInt(totalStations + 1) + 1;
				int secondNumber = rand.nextInt(totalStations + 1) + 1;
				if (firstNumber > secondNumber) {
					toRandom = firstNumber;
					fromRandom = secondNumber;
				} else {
					toRandom = secondNumber;
					fromRandom = firstNumber;
				}
			}
			placePassenger(matrix, fromRandom, toRandom, maxSeats, totalStations);
		}
		
		

	}

	public static void placePassengerRandom(int[][] matrix, int fromStation, int toStation, int maxSeats, int totalStations) {
		int passTest = 0;
		int distTravel = toStation - fromStation;
		int[] indexPassedTest = new int[maxSeats];
		int position = -1;

		if (fromStation >= 0 && toStation >= 0) {
			// testar för alla rader (stationer)
			for (int j = 0; j < maxSeats; j++) {
				// testar för alla kolumner (platser)
				for (int i = fromStation; i < toStation; i++) {
					if (matrix[j][i - 1] == 0) {
						passTest = passTest + 1;
					} else {
						i = toStation;
						passTest = 0;
					}
					if (passTest == distTravel) {
						indexPassedTest[j] = j;
					} else {
						indexPassedTest[j] = -1;
					}
				}
				passTest = 0;
			}
			passTest = 0;
		}
		if (maxValue(indexPassedTest) != -1) {
			while (position == -1) {
				position = getRandom(indexPassedTest);
			}
		}
		if (position != -1) {
			for (int placeSeats = fromStation - 1; placeSeats < toStation - 1; placeSeats++) {
				matrix[position][placeSeats] = 1;
				if (placeSeats == toStation - 2) {
				}
			}

		}
	}

	public static int getRandom(int[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	// hittar STÖRSTA värdet av array
	public static int maxValue(int[] arr1) {
		int max = arr1[0];
		for (int i = 1; i < arr1.length; i++) {
			if (arr1[i] > max) {
				max = arr1[i];
			}
		}
		return max;
	}
	
	public static int sumArray(int[] array) {
		int sum = 0;
		for(int i = 0; i < array.length; i++) {
		    sum += array[i];
		}
		return sum;
	}
	public static int sumMatrix(int[][] matrix, int matrixRowSeats, int matrixColStations) {
		int sum = 0;
		for(int j = 0; j < matrixRowSeats; j++){
			for(int i = 0; i < matrixColStations; i++) {
			    sum = sum + matrix[j][i];
			}
		}
		return sum;
	}
	
	
}