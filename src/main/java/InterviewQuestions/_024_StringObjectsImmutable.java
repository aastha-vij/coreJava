package InterviewQuestions;

public class _024_StringObjectsImmutable {
    public static void main(String[] args) {
        String original = "Hello";
        String modified = original.concat("World");
        System.out.println(modified); //HelloWorld

        if (original == modified)
            System.out.println("The strings are the same object."); // if
        else
            System.out.println("The strings are different objects.");

        String a = "hello";
        a.concat("world");
        System.out.println(a); // hello

        String b = a.concat("world");
        System.out.println(b); // helloworld


        StringBuffer c = new StringBuffer("hello");
        c.append("world");
        System.out.println(c); // helloworld

        String str1 = "Some Text";
        String str2 = "Some Text";
        String str3 = new String("Some Text");
        String str4 = new String("Some Text");

        System.out.println(str1==str2); // true
        System.out.println(str1.equals(str2)); // true
        System.out.println(str3==str4); // false
        System.out.println(str3.equals(str4)); // true
    }

}
