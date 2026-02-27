package InterviewQuestions;

public class _003_firstNonRepeatingChar {

	public static void main(String[] args) {

		String str = "haabfebecd";
		System.out.println(_01_BFA(str));

	}
	
	public static char _01_BFA(String str) {

		for (int i = 0; i < str.length(); i++) {
			Boolean isRepeated = false;
			for (int j = 0; j < str.length(); j++) {
				if(i!=j && str.charAt(i)== str.charAt(j)) {
					isRepeated= true;
					break;
				}
			}
			if(!isRepeated)
				return str.charAt(i);
		}
		return '\0';
	}
}
