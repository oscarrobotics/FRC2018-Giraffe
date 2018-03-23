package org.usfirst.frc.team832.robot.func;

public class Calcs {
	public static double map(double value, double in_min, double in_max, double out_min, double out_max) {
		  return (value - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		}
		
	public static double clip(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}
	
	public static boolean inRange(int value, int min, int max) {
	   return (value>= min) && (value<= max);
	}
}
