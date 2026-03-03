package InterviewQuestions;

public class _008_Set_hasUniqueChars {

	public static void main(String[] args) {
		String str1 = "abcdefg"; // should return true
		String str2 = "hello"; // should return false
		
		System.out.println(_01_BFA(str1));
		System.out.println(_01_BFA(str2));

	}

	static boolean _01_BFA(String str) {
		
		for (int i = 0; i < str.length(); i++) {
			for (int j = 0; j < str.length(); j++) {
				if(i!=j && str.charAt(i)==str.charAt(j))
					return false;
			}
		}
		return true;
	}
}
