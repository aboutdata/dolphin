package com.sabsari.dolphin.utils;

public class Math {
	
	public static boolean isPowerOfTen(int num) {		
		int remainder = num % 10;		
		int quotient = num / 10;
		
		if (remainder != 0) {
			return false;
		}
		else {
			if (quotient == 1)
				return true;
			else
				return isPowerOfTen(num / 10);
		}
	}
}
