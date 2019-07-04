package com.letswork.springservice.utils;

import java.util.Random;

public class GFG {
	static Random rd = new Random();
	public static String randomCmnd() {
		String ans = "201";
		for (int i = 0; i < 6; i++) {
			ans += rd.nextInt(9);
		}
		return ans;
	}

	private static String famN[] = { "Lê", "Võ", "Phan", "Phạm", "Nguyễn" };
	private static String subN[] = { "Thị", "Văn", "Duy", "Thanh", "Thị Nguyệt" };
	private static String name[] = { "Đạt", "An", "Hàn", "Huệ", "Huy" };

	public static String randomName() {
		return randomLastName() + randomFirstName();
	}

	public static String randomFirstName() {
		Random rd = new Random();
		String ans = "";
		ans += name[rd.nextInt(4)];
		return ans;
	}

	public static String randomLastName() {
		Random rd = new Random();
		String ans = "";
		ans += famN[rd.nextInt(4)];
		ans += " " + subN[rd.nextInt(4)];
		return ans;
	}

	public static String randomPhone() {
		Random rd = new Random();
		String ans = "01";
		for (int i = 0; i < 9; i++) {
			ans += rd.nextInt(9);
		}
		return ans;
	}

	public static int randomMonney() {
		Random rd = new Random();
		int m = rd.nextInt(50000);
		if(m == 0) m = 321;
		return m * 1000;
	}

	public static String randomEmail() {
		Random rd = new Random();
		int leng = rd.nextInt(16);
		String ans = "";
		for (int i = 0; i < 6 + leng; i++) {
			int x = 65 + rd.nextInt(57);
			if(x>90 && x<97) x= 48 + rd.nextInt(9);
			ans += (char) x;
		}
		return ans += "@gmail.com";
	}

	public static int randomStudy() {
		return new Random().nextInt(5) + 1;
	}
}
