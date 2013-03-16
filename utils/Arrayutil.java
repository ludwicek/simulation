package utils;

public class Arrayutil {
	public static double sumArray(double [] arr) {
		double sum = 0; 
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
}
