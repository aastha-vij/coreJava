# Java Strings — Complete Mastery Guide

---

## Table of Contents
1. [String Fundamentals](#1-string-fundamentals)
2. [String Immutability — Deep Dive](#2-string-immutability--deep-dive)
3. [String Pool](#3-string-pool)
4. [All String Methods](#4-all-string-methods)
5. [String Comparison — `==` vs `equals()`](#5-string-comparison----vs-equals)
6. [StringBuilder & StringBuffer — When and How](#6-stringbuilder--stringbuffer--when-and-how)
7. [String Manipulation Patterns](#7-string-manipulation-patterns)
8. [Regular Expressions in Java](#8-regular-expressions-in-java)
9. [String Formatting & Conversion](#9-string-formatting--conversion)
10. [Java 8–17 String Enhancements](#10-java-817-string-enhancements)
11. [String Interview Questions & Answers](#11-string-interview-questions--answers)

---

## 1. String Fundamentals

```java
// String is a class in java.lang — no import needed
// java.lang.String implements Serializable, Comparable<String>, CharSequence

// Ways to create a String
String s1 = "Hello";                        // string literal — uses pool
String s2 = new String("Hello");            // new object on heap — avoids pool
String s3 = new String(new char[]{'H','i'}); // from char array
String s4 = String.valueOf(42);             // from int → "42"
String s5 = String.valueOf(true);           // from boolean → "true"
String s6 = String.valueOf(new char[]{'A','B'}); // from char[] → "AB"
String s7 = new StringBuilder("Hello").toString(); // from StringBuilder

// String length vs array length
String str = "Hello";
int len = str.length();    // method call — ()
int[] arr = {1, 2, 3};
int arrLen = arr.length;   // field — no ()

// String as char array
char[] chars = str.toCharArray();   // ['H','e','l','l','o']
String back = new String(chars);    // "Hello"
```

---

## 2. String Immutability — Deep Dive

### What Immutability Means

Once a `String` object is created, **its content can never change**. Every operation that appears to "modify" a String actually **creates a new String object**.

```java
String s = "Hello";
s.toUpperCase();          // Creates NEW String "HELLO" — s is UNCHANGED
System.out.println(s);   // "Hello" — still the same

// To actually use the result, reassign:
s = s.toUpperCase();      // s now points to NEW "HELLO" object
System.out.println(s);   // "HELLO"

// Concatenation creates new objects
String a = "Hello";
String b = a + ", World";  // new String created
// a still = "Hello", b = "Hello, World"

// String in loop — performance problem
String result = "";
for (int i = 0; i < 10000; i++) {
    result += i;  // creates 10000 new String objects — O(n²)!
}
// Use StringBuilder instead
```

### Why Is String Immutable?

1. **Security**: Class names, file paths, network URLs are Strings. If mutable, malicious code could change them after validation.

```java
// Without immutability — dangerous!
String fileName = "safe_file.txt";
// Another thread changes it to "../../../etc/passwd"
openFile(fileName);  // now opens wrong file!
```

2. **Thread Safety**: Immutable objects are inherently thread-safe — no synchronization needed when sharing Strings across threads.

3. **String Pool**: Pool only works because strings are immutable. If `"Hello"` could be changed to `"World"`, every reference to the pool entry would be corrupted.

4. **HashCode Caching**: `String` caches its `hashCode()` after the first call. This only works if the content never changes.

```java
String key = "browser";
key.hashCode();   // computed and cached
key.hashCode();   // returns cached value — O(1)
// Used in HashMap lookups — String is the ideal HashMap key
```

### Internal Structure of String

```java
// Java 8 and earlier
public final class String {
    private final char[] value;   // actual characters stored here
    private int hash;             // cached hashCode
}

// Java 9+ (Compact Strings)
public final class String {
    private final byte[] value;   // LATIN-1 uses 1 byte per char (50% memory saving!)
    private final byte coder;     // 0 = LATIN-1, 1 = UTF-16
    private int hash;
}
```

---

## 3. String Pool

The **String Pool** (intern pool) is a special area in the **Heap** (moved from PermGen to Heap in Java 7) that caches String literals.

```java
// Literals go to the pool — same content = same reference
String a = "chrome";
String b = "chrome";
System.out.println(a == b);       // true — same pool object

// new String() bypasses the pool
String c = new String("chrome");
System.out.println(a == c);       // false — different heap object
System.out.println(a.equals(c)); // true — same content

// intern() — get the pool reference
String d = c.intern();            // returns pool reference (adds if not present)
System.out.println(a == d);       // true — now same pool object

// Compile-time concatenation — goes to pool
String e = "ch" + "rome";        // compile-time constant → pool
System.out.println(a == e);       // true — compiler optimizes this

// Runtime concatenation — does NOT go to pool
String ch = "ch";
String f = ch + "rome";           // runtime → new heap object
System.out.println(a == f);       // false
System.out.println(a.equals(f)); // true
```

### Pool Diagram

```
Heap Memory
┌─────────────────────────────────────────┐
│  String Pool (intern table)             │
│  ┌─────────────┐  ┌──────────────────┐  │
│  │  "chrome"   │  │  "saucedemo.com" │  │
│  └──────┬──────┘  └──────────────────┘  │
│         │                               │
│  a ─────┘   (literal reference)         │
│  b ─────┘   (same reference)            │
│                                         │
│  c ──→ [new String("chrome")]  ← heap   │
│  d ─────┘   (after intern())            │
└─────────────────────────────────────────┘
```

---

## 4. All String Methods

### Inspection Methods

| Method | Description | Example | Result |
|--------|-------------|---------|--------|
| `length()` | Number of characters | `"hello".length()` | `5` |
| `isEmpty()` | True if length == 0 | `"".isEmpty()` | `true` |
| `isBlank()` | True if empty or all whitespace (Java 11) | `"  ".isBlank()` | `true` |
| `charAt(int i)` | Character at index | `"hello".charAt(1)` | `'e'` |
| `indexOf(String s)` | First index of s (-1 if not found) | `"hello".indexOf("ll")` | `2` |
| `indexOf(String s, int from)` | First index from position | `"hello".indexOf("l", 3)` | `3` |
| `lastIndexOf(String s)` | Last occurrence | `"hello".lastIndexOf("l")` | `3` |
| `contains(CharSequence s)` | Contains substring? | `"hello".contains("ell")` | `true` |
| `startsWith(String prefix)` | Starts with? | `"https://".startsWith("https")` | `true` |
| `startsWith(String p, int offset)` | Starts with at offset | `"chrome".startsWith("rom", 2)` | `true` |
| `endsWith(String suffix)` | Ends with? | `"test.java".endsWith(".java")` | `true` |
| `matches(String regex)` | Matches regex pattern? | `"12345".matches("\\d+")` | `true` |

### Transformation Methods (all return NEW String)

| Method | Description | Example | Result |
|--------|-------------|---------|--------|
| `toUpperCase()` | All uppercase | `"hello".toUpperCase()` | `"HELLO"` |
| `toLowerCase()` | All lowercase | `"HELLO".toLowerCase()` | `"hello"` |
| `trim()` | Remove leading/trailing whitespace (ASCII ≤ 32) | `"  hi  ".trim()` | `"hi"` |
| `strip()` | Remove leading/trailing Unicode whitespace (Java 11) | `"  hi  ".strip()` | `"hi"` |
| `stripLeading()` | Remove leading whitespace (Java 11) | `"  hi  ".stripLeading()` | `"hi  "` |
| `stripTrailing()` | Remove trailing whitespace (Java 11) | `"  hi  ".stripTrailing()` | `"  hi"` |
| `replace(char old, char new)` | Replace all occurrences of char | `"hello".replace('l','r')` | `"herro"` |
| `replace(CharSeq old, CharSeq new)` | Replace all occurrences of string | `"aabbcc".replace("bb","xx")` | `"aaxxcc"` |
| `replaceAll(String regex, String r)` | Replace all regex matches | `"a1b2".replaceAll("\\d","#")` | `"a#b#"` |
| `replaceFirst(String regex, String r)` | Replace first regex match | `"a1b2".replaceFirst("\\d","#")` | `"a#b2"` |
| `substring(int begin)` | Substring from begin to end | `"hello".substring(2)` | `"llo"` |
| `substring(int begin, int end)` | Substring [begin, end) | `"hello".substring(1,4)` | `"ell"` |
| `concat(String s)` | Append string | `"hello".concat(" world")` | `"hello world"` |
| `intern()` | Return pool reference | `new String("hi").intern()` | pool `"hi"` |
| `repeat(int n)` | Repeat n times (Java 11) | `"ab".repeat(3)` | `"ababab"` |

### Split & Join

```java
// split — returns String[]
String csv = "chrome,firefox,edge,safari";
String[] browsers = csv.split(",");          // ["chrome","firefox","edge","safari"]
String[] limited  = csv.split(",", 2);       // ["chrome","firefox,edge,safari"] — limit 2

String path = "src/test/java/LoginPage.java";
String[] parts = path.split("/");            // ["src","test","java","LoginPage.java"]
String[] dotParts = path.split("\\.");       // split on literal dot — needs escaping!

// join — static method
String joined = String.join(", ", "chrome", "firefox", "edge");  // "chrome, firefox, edge"
String joined2 = String.join("-", browsers);                      // "chrome-firefox-edge-safari"

// join from collection
List<String> list = Arrays.asList("A", "B", "C");
String result = String.join("|", list);    // "A|B|C"
```

### Comparison Methods

```java
String s1 = "Hello";
String s2 = "hello";
String s3 = "Hello";

s1.equals(s2)               // false — case sensitive
s1.equalsIgnoreCase(s2)     // true — case insensitive
s1.compareTo(s3)            // 0 — same content
s1.compareTo(s2)            // negative — 'H'(72) < 'h'(104)
s1.compareToIgnoreCase(s2)  // 0 — ignores case
s1.contentEquals(s3)        // true — works with StringBuilder too

// Sorting strings lexicographically
List<String> browsers = Arrays.asList("firefox", "chrome", "edge");
Collections.sort(browsers);         // natural: [chrome, edge, firefox]
browsers.sort(String::compareTo);   // same using method reference
browsers.sort(Comparator.comparingInt(String::length)); // by length: [edge, chrome, firefox]
```

### Conversion Methods

```java
// char[] ↔ String
char[] arr = {'H','i'};
String s = new String(arr);           // "Hi"
String s2 = String.valueOf(arr);      // "Hi"
char[] back = s.toCharArray();        // ['H','i']

// byte[] ↔ String
byte[] bytes = "Hello".getBytes();           // default charset
byte[] utf8  = "Hello".getBytes("UTF-8");   // specific charset
String from  = new String(bytes);
String fromUtf8 = new String(utf8, "UTF-8");

// primitive → String
String.valueOf(42)       // "42"
String.valueOf(3.14)     // "3.14"
String.valueOf(true)     // "true"
String.valueOf('A')      // "A"
Integer.toString(42)     // "42"
42 + ""                  // "42" — least preferred (creates StringBuilder internally)

// String → primitive
Integer.parseInt("42")         // 42
Double.parseDouble("3.14")     // 3.14
Boolean.parseBoolean("true")   // true (case-insensitive)
Long.parseLong("9999999999")   // 9999999999L
```

### chars() and codePoints() (Java 8+)

```java
"hello".chars()
    .forEach(c -> System.out.print((char) c + " ")); // h e l l o

// Frequency count using streams
String str = "aababc";
Map<Character, Long> freq = str.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
// {a=4, b=2, c=1}

// Count vowels
long vowels = "Hello World".chars()
    .filter(c -> "aeiouAEIOU".indexOf(c) >= 0)
    .count();  // 3
```

---

## 5. String Comparison — `==` vs `equals()`

```java
// ALWAYS use .equals() for content comparison — NEVER ==

String a = "test";
String b = "test";
String c = new String("test");

a == b          // true  (both pool references)
a == c          // false (c is heap, not pool)
a.equals(b)     // true
a.equals(c)     // true  ← always use this

// Null-safe comparison — avoid NullPointerException
// BAD
if (str.equals("chrome")) { }        // NPE if str is null!

// GOOD — put literal/known non-null first
if ("chrome".equals(str)) { }        // safe even if str is null

// GOOD — Java 7+ null-safe
if (Objects.equals(str, "chrome")) { } // handles null on both sides

// Comparing ignoring case
String browser = "CHROME";
if (browser.equalsIgnoreCase("chrome")) { }  // true

// compareTo returns:
// 0     — strings are equal
// < 0   — this string is lexicographically less
// > 0   — this string is lexicographically greater
"apple".compareTo("banana")  // negative
"banana".compareTo("apple")  // positive
"apple".compareTo("apple")   // 0
```

---

## 6. StringBuilder & StringBuffer — When and How

### StringBuilder (Not Thread-Safe, Fast)

```java
StringBuilder sb = new StringBuilder();          // empty, capacity 16
StringBuilder sb2 = new StringBuilder(50);      // initial capacity 50
StringBuilder sb3 = new StringBuilder("Hello"); // initial content

// append — most used
sb.append("Hello");         // "Hello"
sb.append(", ");            // "Hello, "
sb.append("World");         // "Hello, World"
sb.append(42);              // "Hello, World42"
sb.append(true);            // "Hello, World42true"
sb.append('!');             // "Hello, World42true!"

// Method chaining (because append returns 'this')
sb.append("Browser: ").append("chrome").append(" v").append(120);

// insert
sb.insert(0, "[START] ");   // insert at index 0
sb.insert(5, "---");        // insert at index 5

// delete
sb.delete(0, 7);            // delete index 0 to 7 (exclusive)
sb.deleteCharAt(0);         // delete single character

// replace
sb.replace(0, 5, "Hi");    // replace range [0,5) with "Hi"

// reverse
sb.reverse();               // reverse entire content

// indexOf / lastIndexOf
sb.indexOf("World");
sb.lastIndexOf("l");

// charAt / setCharAt
char c = sb.charAt(0);
sb.setCharAt(0, 'h');

// length and capacity
sb.length();        // actual characters
sb.capacity();      // internal buffer size (grows automatically)
sb.ensureCapacity(100); // pre-grow buffer

// Convert to String
String result = sb.toString();
```

### StringBuffer (Thread-Safe, Slower)

Identical API to `StringBuilder` — every method is `synchronized`.

```java
// Use only when multiple threads share the same buffer
StringBuffer sbf = new StringBuffer();
// API is identical to StringBuilder
sbf.append("thread-safe content");
```

### Performance Comparison

```java
// String concatenation in loop — O(n²), creates n objects
String s = "";
for (int i = 0; i < 100_000; i++) s += i;   // ~5 seconds

// StringBuilder — O(n), single buffer
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 100_000; i++) sb.append(i); // < 10ms
String result = sb.toString();

// Note: s1 + s2 (not in loop) is fine — compiler converts to StringBuilder
String name = "Hello" + ", " + "World";  // optimized by compiler
```

| | `String` | `StringBuilder` | `StringBuffer` |
|--|---------|----------------|----------------|
| Mutable | No | Yes | Yes |
| Thread-safe | Yes (immutable) | No | Yes |
| Performance | Slow (loop) | Fast | Medium |
| Package | java.lang | java.lang | java.lang |

---

## 7. String Manipulation Patterns

### Reverse a String

```java
// Method 1: StringBuilder.reverse()
String reversed = new StringBuilder(str).reverse().toString();

// Method 2: char array loop
char[] arr = str.toCharArray();
int left = 0, right = arr.length - 1;
while (left < right) {
    char temp = arr[left];
    arr[left++] = arr[right];
    arr[right--] = temp;
}
return new String(arr);

// Method 3: Recursion
String reverse(String s) {
    if (s.length() <= 1) return s;
    return reverse(s.substring(1)) + s.charAt(0);
}

// Method 4: Stream
String reversed2 = IntStream.rangeClosed(1, str.length())
    .mapToObj(i -> String.valueOf(str.charAt(str.length() - i)))
    .collect(Collectors.joining());
```

### Check Palindrome

```java
// Method 1: Two-pointer
boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) return false;
    }
    return true;
}

// Method 2: StringBuilder
boolean isPalin = s.equals(new StringBuilder(s).reverse().toString());

// Ignore case and non-alphanumeric
boolean isPalindromeAlphaNum(String s) {
    String cleaned = s.toLowerCase().replaceAll("[^a-z0-9]", "");
    return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
}
```

### Check Anagram

```java
// Method 1: Sort and compare
boolean isAnagram(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    char[] a = s1.toCharArray(); Arrays.sort(a);
    char[] b = s2.toCharArray(); Arrays.sort(b);
    return Arrays.equals(a, b);
}

// Method 2: Frequency array O(n) time, O(1) space
boolean isAnagram2(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    int[] count = new int[26];
    for (char c : s1.toCharArray()) count[c - 'a']++;
    for (char c : s2.toCharArray()) count[c - 'a']--;
    for (int n : count) if (n != 0) return false;
    return true;
}

// Method 3: HashMap
boolean isAnagram3(String s1, String s2) {
    Map<Character, Integer> map = new HashMap<>();
    for (char c : s1.toCharArray()) map.merge(c, 1, Integer::sum);
    for (char c : s2.toCharArray()) {
        if (!map.containsKey(c)) return false;
        if (map.merge(c, -1, Integer::sum) == 0) map.remove(c);
    }
    return map.isEmpty();
}
```

### Count Occurrences of Character

```java
String str = "automation testing";
char target = 't';

// Method 1: Loop
int count = 0;
for (char c : str.toCharArray()) if (c == target) count++;

// Method 2: replace trick
int count2 = str.length() - str.replace(String.valueOf(target), "").length();

// Method 3: Stream
long count3 = str.chars().filter(c -> c == target).count();

// Method 4: Collections.frequency on list
long count4 = str.chars().mapToObj(c -> (char)c)
    .filter(c -> c == target).count();
```

### Remove Duplicates

```java
// Preserve order, remove duplicate characters
String removeDuplicateChars(String s) {
    StringBuilder sb = new StringBuilder();
    Set<Character> seen = new LinkedHashSet<>();
    for (char c : s.toCharArray()) {
        if (seen.add(c)) sb.append(c);
    }
    return sb.toString();
}
// "aabbbccd" → "abcd"
```

### Find First Non-Repeating Character

```java
Character firstNonRepeating(String s) {
    // LinkedHashMap preserves insertion order
    Map<Character, Integer> freq = new LinkedHashMap<>();
    for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
    for (Map.Entry<Character, Integer> e : freq.entrySet())
        if (e.getValue() == 1) return e.getKey();
    return null;
}
```

### Longest Common Prefix

```java
String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++) {
        while (!strs[i].startsWith(prefix)) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) return "";
        }
    }
    return prefix;
}
// ["flower","flow","flight"] → "fl"
```

### Count Words in a String

```java
String sentence = "  Hello   World  Java  ";

// Method 1: split on whitespace
String[] words = sentence.trim().split("\\s+");
int count = words.length;  // 3

// Method 2: Stream
long count2 = Arrays.stream(sentence.trim().split("\\s+")).count();
```

### String Compression

```java
// "aaabbbccca" → "a3b3c3a1"
String compress(String s) {
    StringBuilder sb = new StringBuilder();
    int count = 1;
    for (int i = 1; i <= s.length(); i++) {
        if (i < s.length() && s.charAt(i) == s.charAt(i-1)) {
            count++;
        } else {
            sb.append(s.charAt(i-1)).append(count);
            count = 1;
        }
    }
    return sb.length() < s.length() ? sb.toString() : s;
}
```

### All Permutations of a String

```java
void permutations(String str, String prefix) {
    if (str.isEmpty()) {
        System.out.println(prefix);
        return;
    }
    for (int i = 0; i < str.length(); i++) {
        permutations(
            str.substring(0, i) + str.substring(i + 1),
            prefix + str.charAt(i)
        );
    }
}
permutations("abc", "");  // abc, acb, bac, bca, cab, cba
```

---

## 8. Regular Expressions in Java

### Pattern & Matcher

```java
import java.util.regex.*;

// Full match
boolean matches = Pattern.matches("\\d{3}-\\d{4}", "123-4567");  // true

// Using Pattern and Matcher (reuse Pattern for performance)
Pattern p = Pattern.compile("\\d+");
Matcher m = p.matcher("abc123def456");

while (m.find()) {
    System.out.println(m.group());  // "123", "456"
    System.out.println(m.start()); // start index
    System.out.println(m.end());   // end index
}

// Groups
Pattern emailPattern = Pattern.compile("(\\w+)@(\\w+)\\.(\\w+)");
Matcher emailMatcher = emailPattern.matcher("user@example.com");
if (emailMatcher.matches()) {
    emailMatcher.group(0);  // "user@example.com" — entire match
    emailMatcher.group(1);  // "user"
    emailMatcher.group(2);  // "example"
    emailMatcher.group(3);  // "com"
}
```

### Common Regex Patterns

| Pattern | Matches |
|---------|---------|
| `\\d` | Digit `[0-9]` |
| `\\D` | Non-digit |
| `\\w` | Word char `[a-zA-Z0-9_]` |
| `\\W` | Non-word char |
| `\\s` | Whitespace (space, tab, newline) |
| `\\S` | Non-whitespace |
| `.` | Any character except newline |
| `^` | Start of string |
| `$` | End of string |
| `*` | 0 or more |
| `+` | 1 or more |
| `?` | 0 or 1 |
| `{n}` | Exactly n |
| `{n,m}` | Between n and m |
| `[abc]` | One of a, b, c |
| `[^abc]` | Not a, b, or c |
| `(a\|b)` | a or b |

```java
// Common validations
"test@email.com".matches("[\\w.]+@[\\w.]+\\.[a-z]{2,}")   // email
"123-456-7890".matches("\\d{3}-\\d{3}-\\d{4}")            // phone
"P@ssw0rd!".matches("(?=.*[A-Z])(?=.*\\d)(?=.*[!@#]).{8,}") // password
"https://saucedemo.com".matches("https?://[\\w./]+")       // URL

// Replace with regex
"Hello   World".replaceAll("\\s+", " ")    // "Hello World"
"abc123def".replaceAll("[^\\d]", "")       // "123" — keep only digits
"abc123def".replaceAll("[^a-zA-Z]", "")    // "abcdef" — keep only letters

// Split with regex
"one1two2three3".split("\\d")              // ["one","two","three",""]
```

---

## 9. String Formatting & Conversion

### String.format()

```java
// %s — string, %d — int, %f — float, %c — char, %b — boolean, %n — newline
String msg = String.format("Test '%s' on %s browser - Status: %s", 
                           "loginTest", "Chrome", "PASSED");

String numeric = String.format("Value: %05d", 42);      // "Value: 00042" (zero-padded, width 5)
String decimal = String.format("%.2f%%", 99.9876);      // "99.99%"
String padded  = String.format("%-20s|", "chrome");     // "chrome              |" (left-aligned)
String upper   = String.format("%S", "chrome");         // "CHROME"

// printf (prints, returns void)
System.out.printf("Test: %-15s Result: %s%n", "loginTest", "PASS");

// Formatted string (Java 15+)
String result = "Test %s: %s".formatted("loginTest", "PASS");
```

### Number ↔ String Conversions

```java
// int / long / double → String
String s1 = String.valueOf(42);          // "42"
String s2 = Integer.toString(42);        // "42"
String s3 = 42 + "";                     // "42" — works but creates StringBuilder
String s4 = String.format("%d", 42);     // "42"

// String → numeric
int    i = Integer.parseInt("42");
long   l = Long.parseLong("9999999999");
double d = Double.parseDouble("3.14");
float  f = Float.parseFloat("3.14");

// Radix conversions
Integer.toBinaryString(10)    // "1010"
Integer.toHexString(255)      // "ff"
Integer.toOctalString(8)      // "10"
Integer.parseInt("1010", 2)   // 10 — binary to int
Integer.parseInt("ff", 16)    // 255 — hex to int

// Char conversions
char c = 'A';
int ascii = (int) c;           // 65
char back = (char) (ascii + 1); // 'B'
Character.isLetter(c)          // true
Character.isDigit('5')         // true
Character.isWhitespace(' ')    // true
Character.toUpperCase('a')     // 'A'
Character.toLowerCase('A')     // 'a'
```

---

## 10. Java 8–17 String Enhancements

```java
// Java 8: String.join()
String.join(", ", "a", "b", "c")           // "a, b, c"

// Java 8: Collectors.joining()
List.of("a","b","c").stream()
    .collect(Collectors.joining(", ", "[", "]"))  // "[a, b, c]"

// Java 8: chars() IntStream
"hello".chars().forEach(c -> System.out.print((char) c));

// Java 11: strip(), stripLeading(), stripTrailing()
"  hello  ".strip()         // "hello"  — Unicode-aware (better than trim())

// Java 11: isBlank()
"  \t  ".isBlank()          // true  (whitespace-only)
"".isBlank()                // true

// Java 11: lines() — splits on line terminators
"one\ntwo\nthree".lines()
    .collect(Collectors.toList())  // ["one", "two", "three"]

// Java 11: repeat(n)
"ha".repeat(3)              // "hahaha"

// Java 12: indent(n)
"hello".indent(4)           // "    hello\n"

// Java 12: transform(Function)
"  HELLO  ".transform(String::strip)
           .transform(String::toLowerCase)  // "hello"

// Java 15: Text Blocks (multi-line strings)
String json = """
    {
        "browser": "chrome",
        "headless": false,
        "timeout": 30
    }
    """;

String html = """
    <html>
        <body>Hello</body>
    </html>
    """;

// Java 15: Formatted (instance method)
String msg = "Browser: %s".formatted("chrome");  // "Browser: chrome"

// Java 16: String methods on streams
"one\ntwo\nthree".lines().count()  // 3

// Java 17: No new String features, but preview features available
```

---

## 11. String Interview Questions & Answers

**Q1: Why is String immutable in Java?**
> Four reasons: (1) **Security** — Strings used as class names, file paths can't be altered. (2) **Thread safety** — immutable = inherently thread-safe. (3) **String pool** — pool only works if shared strings can't be changed. (4) **HashCode caching** — `hashCode()` is cached after first call, safe only because content never changes.

**Q2: What is the String pool and where does it live?**
> The String pool (intern pool) is a cache of String literals. In Java 6 it lived in PermGen. Since Java 7 it's in the **Heap**. When you write `String s = "hello"`, Java checks if `"hello"` exists in the pool and reuses it. `new String("hello")` bypasses the pool and creates a new heap object.

**Q3: What does `intern()` do?**
> `intern()` returns the canonical pool representation. If the pool contains an equal string, it returns that reference. Otherwise, it adds the string to the pool and returns the reference. After calling `intern()`, `==` comparison works like `equals()`.

**Q4: What is the difference between `==` and `.equals()` for Strings?**
> `==` compares **references** (memory addresses). `equals()` compares **content**. Two String literals with the same value will share a pool reference (`==` is true), but a string created with `new String()` has its own heap object (`==` is false even for same content). Always use `.equals()` for content comparison.

**Q5: Reverse words in a sentence.**
```java
String reverseWords(String s) {
    String[] words = s.trim().split("\\s+");
    StringBuilder sb = new StringBuilder();
    for (int i = words.length - 1; i >= 0; i--) {
        sb.append(words[i]);
        if (i > 0) sb.append(" ");
    }
    return sb.toString();
}
// "Hello World Java" → "Java World Hello"
```

**Q6: Check if two strings are rotations of each other.**
```java
boolean isRotation(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    return (s1 + s1).contains(s2);
}
// "abcde" and "cdeab" → true (cdeab is in abcdeabcde)
```

**Q7: Find duplicate characters in a string.**
```java
void findDuplicates(String s) {
    Map<Character, Integer> freq = new LinkedHashMap<>();
    for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
    freq.entrySet().stream()
        .filter(e -> e.getValue() > 1)
        .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
}
```

**Q8: What is the difference between `StringBuilder` and `StringBuffer`?**
> Both are mutable. `StringBuilder` is not thread-safe but faster. `StringBuffer` is thread-safe (all methods are `synchronized`) but slower. Use `StringBuilder` in single-threaded code (most cases). Use `StringBuffer` only when multiple threads share the same buffer.

**Q9: How does Java handle `"hello" + "world"` vs `s1 + s2` (where s1, s2 are variables)?**
> String literal concatenation (`"hello" + "world"`) is evaluated at **compile time** — the compiler replaces it with `"helloworld"`, which goes to the pool. Variable concatenation (`s1 + s2`) is evaluated at **runtime** — the compiler converts it to `new StringBuilder(s1).append(s2).toString()`, which creates a heap object.

**Q10: What is the output?**
```java
String s1 = "Java";
String s2 = "Java";
String s3 = new String("Java");
String s4 = s3.intern();
System.out.println(s1 == s2);   // true — same pool reference
System.out.println(s1 == s3);   // false — s3 is heap, not pool
System.out.println(s1 == s4);   // true — intern() returns pool reference
System.out.println(s1.equals(s3)); // true — same content
```

**Q11: How would you count the number of vowels in a string efficiently?**
```java
long countVowels(String s) {
    return s.toLowerCase().chars()
        .filter("aeiou"::indexOf)   // indexOf returns -1 if not found, else ≥ 0
        // but indexOf(int) on String not boolean — use this instead:
        .filter(c -> "aeiou".indexOf(c) >= 0)
        .count();
}
```

**Q12: Write a method to check if a string has all unique characters.**
```java
// O(n) — HashSet
boolean allUnique(String s) {
    Set<Character> seen = new HashSet<>();
    for (char c : s.toCharArray()) if (!seen.add(c)) return false;
    return true;
}

// O(1) space — bit manipulation (for lowercase a-z only)
boolean allUniqueBit(String s) {
    int checker = 0;
    for (char c : s.toCharArray()) {
        int val = c - 'a';
        if ((checker & (1 << val)) > 0) return false;
        checker |= (1 << val);
    }
    return true;
}
```

**Q13: What does `trim()` vs `strip()` do differently?**
> `trim()` removes characters with Unicode value ≤ 32 (ASCII whitespace only). `strip()` (Java 11) removes **Unicode** whitespace (including non-ASCII spaces like `\u2000`, `\u3000`). Prefer `strip()` in modern Java. `stripLeading()` and `stripTrailing()` remove from one side only.

**Q14: How do you split a string on a period (dot)?**
```java
// WRONG — "." in regex means "any character"
"a.b.c".split(".");     // [] empty or unexpected result

// CORRECT — escape the dot
"a.b.c".split("\\.");   // ["a", "b", "c"]
// OR
"a.b.c".split(Pattern.quote("."));  // ["a", "b", "c"]
```

**Q15: What is a CharSequence?**
> `CharSequence` is an interface implemented by `String`, `StringBuilder`, `StringBuffer`, and `CharBuffer`. It defines the minimal read-only contract: `length()`, `charAt(int)`, `subSequence(int,int)`, `toString()`. Methods that accept `CharSequence` work with all these types.
