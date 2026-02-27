# Core Java Concepts — Complete Reference Guide

---

## Table of Contents

1. [Java Fundamentals — Data Types, Variables, Type Casting](#1-java-fundamentals--data-types-variables-type-casting)
2. [OOP Overview — The Four Pillars](#2-oop-overview--the-four-pillars)
3. [Classes & Objects](#3-classes--objects)
4. [Constructors](#4-constructors)
5. [`this` Keyword](#5-this-keyword)
6. [`super` Keyword](#6-super-keyword)
7. [Access Modifiers](#7-access-modifiers)
8. [Encapsulation](#8-encapsulation)
9. [Inheritance](#9-inheritance)
10. [Polymorphism](#10-polymorphism)
11. [Abstraction — Abstract Classes](#11-abstraction--abstract-classes)
12. [Interfaces](#12-interfaces)
13. [Abstract Class vs Interface](#13-abstract-class-vs-interface)
14. [Static Keyword](#14-static-keyword)
15. [Final Keyword](#15-final-keyword)
16. [String, StringBuilder, StringBuffer](#16-string-stringbuilder-stringbuffer)
17. [Exception Handling](#17-exception-handling)
18. [Generics](#18-generics)
19. [Lambda Expressions & Functional Interfaces](#19-lambda-expressions--functional-interfaces)
20. [Stream API](#20-stream-api)
21. [Java Memory Model — Stack vs Heap](#21-java-memory-model--stack-vs-heap)
22. [Wrapper Classes & Autoboxing](#22-wrapper-classes--autoboxing)
23. [Design Patterns in Automation](#23-design-patterns-in-automation)
24. [Interview Q&A](#24-interview-qa)

---

## 1. Java Fundamentals — Data Types, Variables, Type Casting

### Primitive Data Types

| Type | Size | Default | Range | Example |
|------|------|---------|-------|---------|
| `byte` | 1 byte | 0 | -128 to 127 | `byte b = 100;` |
| `short` | 2 bytes | 0 | -32,768 to 32,767 | `short s = 1000;` |
| `int` | 4 bytes | 0 | -2³¹ to 2³¹-1 | `int i = 42;` |
| `long` | 8 bytes | 0L | -2⁶³ to 2⁶³-1 | `long l = 100L;` |
| `float` | 4 bytes | 0.0f | ±3.4e38 | `float f = 3.14f;` |
| `double` | 8 bytes | 0.0d | ±1.8e308 | `double d = 3.14;` |
| `char` | 2 bytes | '\u0000' | 0 to 65,535 | `char c = 'A';` |
| `boolean` | 1 bit | false | true / false | `boolean b = true;` |

### Variables

```java
// Local variable — declared inside a method, no default value, must initialize before use
public void method() {
    int count = 0;   // must be initialized — no default
    count++;
}

// Instance variable — declared in class, outside methods, has default value
public class Driver {
    private String browser;      // default: null
    private int timeout = 30;    // initialized at declaration
    private boolean isHeadless;  // default: false
}

// Static (class) variable — shared across all instances
public class Config {
    public static final String BASE_URL = "https://www.saucedemo.com";
    public static int instanceCount = 0;
}

// Constants — static + final (by convention UPPERCASE_SNAKE_CASE)
public static final int MAX_RETRIES = 3;
```

### Type Casting

```java
// Implicit (Widening) — automatic, no data loss
int i = 100;
long l = i;       // int → long  (widening)
double d = i;     // int → double (widening)

// Explicit (Narrowing) — manual, may lose data
double pi = 3.14159;
int truncated = (int) pi;    // 3  — decimal part lost
long bigNum = 1234567890L;
int smallNum = (int) bigNum; // may overflow

// Autoboxing / Unboxing (primitive ↔ wrapper)
int primitive = 5;
Integer boxed = primitive;     // autoboxing: int → Integer
int unboxed = boxed;           // unboxing: Integer → int

// String conversions
String s = String.valueOf(42);     // 42  → "42"
int n = Integer.parseInt("42");    // "42" → 42
double d2 = Double.parseDouble("3.14"); // "3.14" → 3.14
```

### Operators

```java
// Arithmetic: +, -, *, /, % (modulo)
int rem = 10 % 3;   // 1

// Comparison: ==, !=, <, >, <=, >=
// Logical: &&, ||, !
// Bitwise: &, |, ^, ~, <<, >>, >>>
// Ternary: condition ? trueVal : falseVal
String status = (score >= 60) ? "PASS" : "FAIL";

// instanceof — type check (important for polymorphism)
WebDriver driver = new ChromeDriver();
boolean isChromeDriver = driver instanceof ChromeDriver; // true
```

---

## 2. OOP Overview — The Four Pillars

Object-Oriented Programming organizes code around **objects** — bundles of state (fields) and behaviour (methods).

| Pillar | One-line definition | Key keyword(s) |
|--------|---------------------|----------------|
| **Encapsulation** | Hide internal state; expose only what is needed | `private`, getters/setters |
| **Inheritance** | A class acquires properties of another class | `extends`, `super` |
| **Polymorphism** | One interface, many implementations | `@Override`, method overloading |
| **Abstraction** | Hide complex implementation, show only the contract | `abstract`, `interface` |

### Automation Big Picture — OOP in Action

The `UI-Automation_Selenium` project is a textbook example of all four pillars:

```
WebDriver (Interface — Abstraction)
     ↑ implemented by
ChromeDriver / FirefoxDriver (Polymorphism — same interface, different behaviour)

BasePage (Encapsulation — protected fields, no public setters)
     ↑ extended by
ElementUtils extends BasePage (Inheritance — inherits driver, wait, log)
     ↑ extended by
LoginPage extends ElementUtils (Multi-level Inheritance)
ProductsPage extends ElementUtils
CartPage extends ElementUtils

BaseTest (Base test class — Inheritance + Encapsulation)
     ↑ extended by
_01_ApplicationBehaviorForDifferentLoginAttempt extends BaseTest
_02_Add_Product_To_Cart_And_Checkout extends BaseTest
```

---

## 3. Classes & Objects

### Anatomy of a Class

```java
// Class = blueprint; Object = instance of the blueprint
public class BrowserConfig {

    // Fields (state)
    private String browserName;   // instance field
    private boolean isHeadless;
    public static int count = 0;  // class (static) field

    // Constructor
    public BrowserConfig(String browserName, boolean isHeadless) {
        this.browserName = browserName;
        this.isHeadless = isHeadless;
        count++;
    }

    // Methods (behaviour)
    public String getBrowserName() { return browserName; }
    public boolean isHeadless()    { return isHeadless; }

    @Override
    public String toString() {
        return "BrowserConfig{browser=" + browserName + ", headless=" + isHeadless + "}";
    }
}

// Creating objects
BrowserConfig chrome  = new BrowserConfig("chrome", false);
BrowserConfig firefox = new BrowserConfig("firefox", true);
System.out.println(BrowserConfig.count);     // 2  — static field shared by all
System.out.println(chrome.getBrowserName()); // chrome
System.out.println(chrome);                  // BrowserConfig{browser=chrome, headless=false}
```

### Object Memory

```java
BrowserConfig a = new BrowserConfig("chrome", false);
BrowserConfig b = a;         // b points to SAME object, NOT a copy
b.browserName = "firefox";   // changes 'a' too — same reference!

// To compare content, always override equals() and hashCode()
a.equals(b);    // true (same reference here)
a == b;         // true (same memory address)
```

---

## 4. Constructors

A constructor is a special method with the **same name as the class** and **no return type**. Called automatically when an object is created with `new`.

### Types of Constructors

```java
public class TestConfig {

    private String browser;
    private String url;
    private int timeout;

    // 1. Default constructor (compiler provides this if you write none)
    public TestConfig() {
        this.browser = "chrome";
        this.url     = "https://www.saucedemo.com";
        this.timeout = 30;
    }

    // 2. Parameterized constructor
    public TestConfig(String browser, String url) {
        this.browser = browser;
        this.url     = url;
        this.timeout = 30;   // default
    }

    // 3. Parameterized — all fields
    public TestConfig(String browser, String url, int timeout) {
        this.browser = browser;
        this.url     = url;
        this.timeout = timeout;
    }

    // 4. Copy constructor
    public TestConfig(TestConfig other) {
        this.browser = other.browser;
        this.url     = other.url;
        this.timeout = other.timeout;
    }
}

// Usage
TestConfig defaultConfig    = new TestConfig();
TestConfig customConfig     = new TestConfig("firefox", "https://example.com", 60);
TestConfig copiedConfig     = new TestConfig(customConfig);   // copy
```

### Constructor Chaining with `this()`

`this(...)` calls another constructor **in the same class**. Must be the **first statement**.

```java
public class TestConfig {
    private String browser;
    private String url;
    private int timeout;

    public TestConfig() {
        this("chrome");   // calls TestConfig(String)
    }

    public TestConfig(String browser) {
        this(browser, "https://www.saucedemo.com");  // calls TestConfig(String, String)
    }

    public TestConfig(String browser, String url) {
        this(browser, url, 30);   // calls TestConfig(String, String, int)
    }

    public TestConfig(String browser, String url, int timeout) {
        this.browser = browser;   // actual field assignment happens here
        this.url     = url;
        this.timeout = timeout;
    }
}
// new TestConfig() → TestConfig(String) → TestConfig(String,String) → TestConfig(String,String,int)
```

### Constructor Chaining with `super()`

`super(...)` calls the **parent class constructor**. Must be the **first statement** (cannot combine with `this()` on the same line).

```java
// From the automation project — real example:

// BasePage constructor
protected BasePage(WebDriver driver, Logger log) {
    this.driver = driver;
    this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
    this.log    = log;
}

// ElementUtils explicitly calls BasePage's constructor
protected ElementUtils(WebDriver driver, Logger log) {
    super(driver, log);    // ← calls BasePage(WebDriver, Logger)
    this.driver = driver;
    this.log    = log;
    this.js     = (JavascriptExecutor) driver;
    this.act    = new Actions(driver);
}

// LoginPage explicitly calls ElementUtils (which chains up to BasePage)
public LoginPage(WebDriver driver, Logger log) {
    super(driver, log);    // ← calls ElementUtils(WebDriver, Logger) → BasePage(WebDriver, Logger)
    this.driver = driver;
    PageFactory.initElements(driver, this);
}
```

> **Rule:** If you don't call `super()` explicitly, Java inserts `super()` (no-arg) automatically. If the parent has no no-arg constructor, it's a **compile error**.

---

## 5. `this` Keyword

`this` refers to the **current object instance**. Five main uses:

### 5.1 Disambiguate field vs parameter (same name)

```java
public class BasePage {
    protected WebDriver driver;
    protected Logger log;

    protected BasePage(WebDriver driver, Logger log) {
        this.driver = driver;   // this.driver = field, driver = parameter
        this.log    = log;
    }
}
```

Without `this.driver`, the parameter `driver` would be assigned to itself — the field would remain `null`.

### 5.2 Call another constructor in the same class — `this()`

```java
public TestConfig() {
    this("chrome");   // delegates to TestConfig(String browser)
}
```

### 5.3 Pass current object as argument

```java
public class LoginPage extends ElementUtils {
    public LoginPage(WebDriver driver, Logger log) {
        super(driver, log);
        PageFactory.initElements(driver, this);  // passes current LoginPage instance
    }
}
// PageFactory uses 'this' to find @FindBy annotated fields inside LoginPage
```

### 5.4 Return current object (builder/fluent pattern)

```java
public class DriverBuilder {
    private String browser = "chrome";
    private boolean headless = false;
    private int implicitWait = 10;

    public DriverBuilder browser(String browser) {
        this.browser = browser;
        return this;   // returns current object → enables chaining
    }

    public DriverBuilder headless(boolean headless) {
        this.headless = headless;
        return this;
    }

    public DriverBuilder implicitWait(int seconds) {
        this.implicitWait = seconds;
        return this;
    }

    public WebDriver build() { /* create driver */ return null; }
}

// Fluent chaining because each method returns 'this'
WebDriver driver = new DriverBuilder()
    .browser("chrome")
    .headless(true)
    .implicitWait(30)
    .build();
```

### 5.5 Reference outer class from inner class

```java
public class OuterClass {
    private String name = "outer";

    class InnerClass {
        private String name = "inner";
        void display() {
            System.out.println(name);              // "inner" — inner class field
            System.out.println(OuterClass.this.name); // "outer" — outer class field
        }
    }
}
```

---

## 6. `super` Keyword

`super` refers to the **parent (superclass) object**. Three main uses:

### 6.1 Call parent constructor — `super()`

Must be the **very first statement** in a constructor.

```java
// Automation chain: LoginPage → ElementUtils → BasePage

// BasePage
protected BasePage(WebDriver driver, Logger log) {
    this.driver = driver;
    this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
    this.log    = log;
}

// ElementUtils extends BasePage
protected ElementUtils(WebDriver driver, Logger log) {
    super(driver, log);  // ← FIRST statement — initializes BasePage fields
    this.js  = (JavascriptExecutor) driver;
    this.act = new Actions(driver);
}

// LoginPage extends ElementUtils
public LoginPage(WebDriver driver, Logger log) {
    super(driver, log);  // ← calls ElementUtils → which calls BasePage
    PageFactory.initElements(driver, this);
}
```

When you call `new LoginPage(driver, log)`, the chain runs:
1. `LoginPage` constructor fires
2. `super(driver, log)` → `ElementUtils` constructor fires
3. `super(driver, log)` → `BasePage` constructor fires (fields initialized here)
4. `BasePage` finishes → back to `ElementUtils` (js, act initialized)
5. `ElementUtils` finishes → back to `LoginPage` (PageFactory runs)

### 6.2 Call parent method — `super.methodName()`

```java
public class BasePage {
    protected void openUrl(String url) {
        driver.get(url);
        log.info("Opened URL: " + url);
    }
}

public class LoginPage extends ElementUtils {
    public ProductsPage login() {
        super.openUrl(prop.getProperty("url"));   // calls BasePage.openUrl()
        waitForElementVisible(txt_username).sendKeys(userName);
        waitForElementVisible(txt_pwd).sendKeys(password);
        waitForElementClickable(btn_login).click();
        return new ProductsPage(driver, log);
    }
}
```

### 6.3 Access parent class field — `super.fieldName`

```java
public class ElementUtils extends BasePage {
    protected WebDriver driver;   // ElementUtils also declares driver

    protected ElementUtils(WebDriver driver, Logger log) {
        super(driver, log);
        this.driver = driver;     // this.driver = ElementUtils field
        // super.driver = BasePage field (same object reference here, but different field)
    }
}
```

### The Full Inheritance Chain — Automation Project

```
BasePage
  ├── fields: driver, wait, log, prop
  ├── constructor: BasePage(WebDriver, Logger)
  └── methods: waitForElementClickable(), waitForElementVisible(), openUrl(), etc.
        ↑ extends
  ElementUtils extends BasePage
  ├── additional fields: js (JavascriptExecutor), act (Actions)
  ├── constructor: ElementUtils(WebDriver, Logger) → calls super(driver, log)
  └── methods: clickOnElement(), enterText(), selectDropdown(), scrollToBottom(), etc.
        ↑ extends
  LoginPage extends ElementUtils
  ├── fields: txt_username, txt_pwd, btn_login (WebElements via @FindBy)
  ├── constructor: LoginPage(WebDriver, Logger) → calls super(driver, log)
  └── methods: login(), loginWithUserInfo(), getErrorMsg(), logout()
```

---

## 7. Access Modifiers

Control **visibility** of classes, fields, constructors, and methods.

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|----------|-----------|-------------|----------|-----------|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| *(default/package-private)* | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

### In the Automation Project

```java
public class BasePage {
    protected WebDriver driver;     // accessible in subclasses (ElementUtils, LoginPage, etc.)
    protected WebDriverWait wait;   // accessible in subclasses
    protected Logger log;           // accessible in subclasses
    protected Properties prop;      // accessible in subclasses

    protected BasePage(WebDriver driver, Logger log) { ... }   // only subclasses can call

    // private helper — nobody outside should call this
    private By getLocatorFromWebElement(WebElement element) { ... }

    // protected utility — available to subclasses but not test code
    protected void waitFor(ExpectedCondition condition, Integer timeoutInSeconds) { ... }
}

public class LoginPage extends ElementUtils {
    private WebElement txt_username;   // hidden — login page internal detail
    private String url;                // hidden — config detail

    public ProductsPage login() { ... }         // public API — tests call this
    public String getErrorMsg() { ... }         // public API — tests assert on this
}

public class BaseTest {
    public WebDriver driver;            // public — test classes access directly
    public LoginPage loginPage;         // public — test classes access directly
    protected Logger log;               // protected — test subclasses inherit
    protected Properties prop;          // protected — test subclasses read config
}
```

**Rule of Thumb:** Use the most restrictive modifier possible.
- `private` → fields, internal helpers
- `protected` → things subclasses need but external code doesn't
- `public` → API that callers (test classes, other modules) use
- `default` → package-level utilities

---

## 8. Encapsulation

**Encapsulation** = binding data (fields) and methods together + restricting direct access to fields via `private` + providing controlled access via getters/setters.

### Why Encapsulate?

1. **Control**: validate before setting values
2. **Hide complexity**: `LoginPage` doesn't expose its WebElements — tests just call `login()`
3. **Change internals freely**: if you rename `txt_username`, no test breaks

```java
// BAD — no encapsulation
public class User {
    public String username;
    public String password;
}
// Anyone can do: user.password = "";  ← no validation, exposes internals

// GOOD — encapsulated
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username cannot be empty");
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6)
            throw new IllegalArgumentException("Password too short");
        this.password = password;
    }
    // no getPassword() — intentionally hidden
}
```

### Encapsulation in the Automation Project

```java
// LoginPage encapsulates ALL interaction details
// Test code never touches WebElement directly — it calls methods

// In test class:
loginPage.loginWithUserInfo(username, password);   // ← clean API
Assert.assertEquals(loginPage.getErrorMsg(), expectedMsg);

// Inside LoginPage — hidden from tests:
@FindBy(css = "#user-name")
private WebElement txt_username;    // private — tests can't access

public void loginWithUserInfo(String userName, String pwd) {
    driver.get(url);
    waitForElementVisible(this.txt_username).sendKeys(userName);  // encapsulated
    waitForElementVisible(this.txt_pwd).sendKeys(pwd);
    waitForElementClickable(this.btn_login).click();
}
```

If the login page UI changes (new locator), only `LoginPage.java` changes — zero test changes.

---

## 9. Inheritance

**Inheritance** = a child class acquires fields and methods of a parent class. Creates an **IS-A** relationship. Reduces code duplication.

### Single Inheritance

```java
// Parent class
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;

    protected BasePage(WebDriver driver, Logger log) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.log    = log;
    }

    protected WebElement waitForElementVisible(WebElement ele) {
        return wait.until(ExpectedConditions.visibilityOf(ele));
    }

    protected WebElement waitForElementClickable(WebElement ele) {
        return wait.until(ExpectedConditions.elementToBeClickable(ele));
    }
}

// Child class
public class ElementUtils extends BasePage {
    protected JavascriptExecutor js;
    protected Actions act;

    protected ElementUtils(WebDriver driver, Logger log) {
        super(driver, log);             // inherit BasePage's fields
        this.js  = (JavascriptExecutor) driver;
        this.act = new Actions(driver);
    }

    protected void clickOnElement(WebElement element) {
        WebElement webElement = waitForElementClickable(element);  // method from BasePage!
        webElement.click();
    }

    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }
}
```

### Multi-Level Inheritance

Java allows a chain: A → B → C (but NOT multiple parents for a class — use interfaces for that).

```java
// Three levels deep in the automation project:
BasePage  →  ElementUtils  →  LoginPage

public class LoginPage extends ElementUtils {
    // LoginPage inherits from ElementUtils (which inherited from BasePage)
    // LoginPage can use: driver, wait, log (BasePage fields)
    //                    js, act (ElementUtils fields)
    //                    waitForElementVisible(), waitForElementClickable() (BasePage methods)
    //                    clickOnElement(), scrollToBottom() (ElementUtils methods)
}
```

### What is Inherited?

| Member | Inherited? | Notes |
|--------|-----------|-------|
| `public` fields/methods | ✅ | Fully accessible |
| `protected` fields/methods | ✅ | Accessible in child |
| `private` fields/methods | ❌ | NOT inherited — hidden |
| `default` (package) | ✅ if same package | ❌ across packages |
| Constructors | ❌ | Not inherited, but `super()` calls them |
| `static` methods | ✅ (shadowed, not overridden) | Called on class, not object |

### `extends` vs Composition (IS-A vs HAS-A)

```java
// IS-A (Inheritance): LoginPage IS-A ElementUtils (IS-A BasePage)
public class LoginPage extends ElementUtils { ... }

// HAS-A (Composition): BaseTest HAS-A LoginPage
public class BaseTest {
    public LoginPage loginPage;   // composed, not extended
    // BaseTest is NOT a LoginPage, it CONTAINS a LoginPage
}
```

> **Favour composition over inheritance** when the relationship is "has-a" not "is-a".

---

## 10. Polymorphism

**Polymorphism** = "many forms". One name, different behaviour depending on context. Two types:

### 10.1 Compile-time Polymorphism — Method Overloading

Same method name, different parameter list (type, count, or order). Resolved at **compile time**.

```java
// From ElementUtils — overloaded enterText methods
protected void enterText(WebElement element, String text) {
    element.sendKeys(text);
}

protected void enterText(WebElement element, String text, boolean clear) {
    if (clear) element.clear();
    element.sendKeys(text);
}

protected void enterText(WebElement element, String text, boolean clear, boolean pressEnter) {
    if (clear) element.clear();
    element.sendKeys(text);
    if (pressEnter) element.sendKeys(Keys.ENTER);
}

// Caller decides which one based on arguments:
enterText(usernameField, "admin");                      // 2-param version
enterText(searchBox, "test query", true);               // 3-param version
enterText(formField, "value", true, true);              // 4-param version
```

```java
// From ElementUtils — overloaded waitForElementPresence methods
protected WebElement waitForElementPresence(By locator)          { ... }  // By locator
protected WebElement waitForElementPresence(WebElement element)  { ... }  // WebElement

// Overloaded SwitchToWindow
protected void SwitchTowindow(int windowNumber)    { ... }  // by index
protected void SwitchTowindow(String expectedTitle){ ... }  // by title
```

### 10.2 Runtime Polymorphism — Method Overriding

Child class provides a **specific implementation** of a method already defined in the parent. Resolved at **runtime** via dynamic dispatch. Requires `@Override` annotation (not mandatory but strongly recommended).

```java
// Parent
public class BasePage {
    protected void openUrl(String url) {
        driver.get(url);
        log.info("Opened URL: " + url);
    }
}

// Child overrides to add extra behaviour
public class LoginPage extends ElementUtils {
    @Override
    protected void openUrl(String url) {
        super.openUrl(url);              // still call parent's logic
        waitForElementVisible(txt_username); // then wait for login form
    }
}
```

### Polymorphism with `WebDriver` (the most important example)

```java
// WebDriver is an interface — all these are valid:
WebDriver driver = new ChromeDriver();    // ChromeDriver IS-A WebDriver
WebDriver driver = new FirefoxDriver();   // FirefoxDriver IS-A WebDriver
WebDriver driver = new EdgeDriver();      // EdgeDriver IS-A WebDriver

// Same code, different runtime behaviour — that IS polymorphism:
driver.get("https://www.saucedemo.com");  // behaves differently for each browser
driver.findElement(By.id("user-name"));   // same call, browser-specific implementation
driver.quit();

// In BrowserDriverFactory.java:
public WebDriver createDriver() {
    switch (browser) {
        case "chrome":  driver.set(new ChromeDriver(chromeOptions)); break;
        case "firefox": driver.set(new FirefoxDriver()); break;
    }
    return driver.get();  // returns WebDriver reference — caller doesn't know which browser
}

// In BaseTest — purely polymorphic:
public WebDriver driver;  // declared as interface type
driver = factory.createDriver();  // actual type determined at runtime
driver.manage().window().maximize();  // works regardless of actual browser
```

### Rules for Method Overriding

| Rule | Detail |
|------|--------|
| Method signature | Must be **identical** (name + parameter list) |
| Return type | Must be same or **covariant** (subtype) |
| Access modifier | Cannot be **more restrictive** (`public` can't become `protected`) |
| Exceptions | Cannot throw **new or broader checked exceptions** |
| `static` methods | Cannot be overridden — they are **hidden**, not overridden |
| `private` methods | Cannot be overridden — not visible to child |
| `final` methods | Cannot be overridden |

---

## 11. Abstraction — Abstract Classes

**Abstraction** = hide implementation details, expose only the essential contract. Achieved via abstract classes and interfaces.

### Abstract Class

- Cannot be instantiated directly (`new AbstractClass()` is a compile error)
- Can have both `abstract` methods (no body) and concrete methods (with body)
- Can have constructors, fields, `static` methods
- A subclass **must implement** all abstract methods (or itself be declared abstract)

```java
// Abstract base — defines the contract for all page objects
public abstract class AbstractPage {

    protected WebDriver driver;
    protected Logger log;

    // Constructor — abstract classes CAN have constructors
    protected AbstractPage(WebDriver driver, Logger log) {
        this.driver = driver;
        this.log    = log;
    }

    // Abstract method — every page MUST define this
    public abstract boolean isPageLoaded();

    // Abstract method — every page MUST define this
    public abstract String getPageTitle();

    // Concrete method — shared implementation, every page inherits this
    public void navigateTo(String url) {
        driver.get(url);
        log.info("Navigated to: " + url);
    }

    // Concrete method — shared
    public void takeScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        // save to file...
    }
}

// Concrete implementation
public class LoginPage extends AbstractPage {

    @FindBy(id = "login-button")
    private WebElement btn_login;

    public LoginPage(WebDriver driver, Logger log) {
        super(driver, log);
        PageFactory.initElements(driver, this);
    }

    @Override
    public boolean isPageLoaded() {
        return btn_login.isDisplayed();  // specific to login page
    }

    @Override
    public String getPageTitle() {
        return driver.getTitle();
    }
}
```

### Abstract Class — Key Facts

```java
AbstractPage page = new AbstractPage(driver, log);  // ❌ COMPILE ERROR — cannot instantiate

AbstractPage page = new LoginPage(driver, log);      // ✅ polymorphism — reference type is abstract
page.navigateTo("https://www.saucedemo.com");        // calls concrete method
page.isPageLoaded();                                 // calls LoginPage.isPageLoaded() at runtime
```

---

## 12. Interfaces

An interface is a **100% abstract contract**. It defines WHAT a class must do, not HOW.

### `WebDriver` — The Quintessential Interface Example

```
org.openqa.selenium.WebDriver (Interface)
    ↑ implemented by
RemoteWebDriver (abstract class implements WebDriver)
    ↑ extended by
ChromeDriver extends RemoteWebDriver    (Chrome-specific how)
FirefoxDriver extends RemoteWebDriver   (Firefox-specific how)
EdgeDriver extends RemoteWebDriver      (Edge-specific how)
```

The contract `WebDriver` defines:
```java
public interface WebDriver {
    void get(String url);
    String getTitle();
    String getCurrentUrl();
    String getPageSource();
    WebElement findElement(By by);
    List<WebElement> findElements(By by);
    void close();
    void quit();
    // ... more
}
// Every browser implements these differently — polymorphism through interface
```

### Declaring & Implementing an Interface

```java
// Interface — only the contract
public interface Reportable {
    void generateReport(String fileName);       // implicitly public abstract
    String getReportTitle();                    // implicitly public abstract

    // Java 8+: default method — has a body, optional to override
    default String getTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }

    // Java 8+: static method
    static String getReportDirectory() {
        return "Reports/";
    }

    // Java 9+: private method (helper for default methods)
    // private void formatDate() { ... }
}

// Implementation
public class ExtentReporter implements Reportable {

    @Override
    public void generateReport(String fileName) {
        System.out.println("Generating Extent Report: " + fileName);
    }

    @Override
    public String getReportTitle() {
        return "UI Automation Test Report";
    }
    // getTimestamp() inherited from interface — no need to override
}
```

### Multiple Interface Implementation

Java classes can implement **multiple interfaces** (solving the "no multiple inheritance" limitation).

```java
public interface Loggable {
    void log(String message);
}

public interface Screenshottable {
    void takeScreenshot(String name);
}

public interface Retryable {
    int getMaxRetries();
    boolean shouldRetry(Throwable t);
}

// One class, three contracts
public class TestListener implements Loggable, Screenshottable, Retryable {

    @Override
    public void log(String message) { System.out.println(message); }

    @Override
    public void takeScreenshot(String name) { /* capture screen */ }

    @Override
    public int getMaxRetries() { return 2; }

    @Override
    public boolean shouldRetry(Throwable t) {
        return t instanceof AssertionError;
    }
}
```

### Interface Fields

All fields in an interface are implicitly `public static final` (constants):

```java
public interface TestConstants {
    String BASE_URL = "https://www.saucedemo.com";  // public static final
    int DEFAULT_TIMEOUT = 30;
    String VALID_USER = "standard_user";

    // Usage — no instance needed:
    // TestConstants.BASE_URL
    // TestConstants.DEFAULT_TIMEOUT
}
```

---

## 13. Abstract Class vs Interface

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Instantiation | ❌ Cannot | ❌ Cannot |
| Multiple inheritance | ❌ A class extends ONE abstract class | ✅ A class implements MANY interfaces |
| Constructor | ✅ Has constructor | ❌ No constructor |
| Fields | Any type (private, protected, static...) | Only `public static final` (constants) |
| Methods | Abstract + concrete | Abstract + `default` + `static` (Java 8+) |
| Access modifiers | Any | Methods are implicitly `public` |
| `extends` / `implements` | Child uses `extends` | Class uses `implements` |
| IS-A relationship | Stronger (shared state + behaviour) | Weaker (contract only) |
| When to use | Close relatives sharing code | Unrelated classes sharing a contract |

### Decision Guide

```
Does the child SHARE STATE (fields) with the parent?
  YES → Abstract Class  (e.g., BasePage with driver, wait, log)

Does it define a CAPABILITY that unrelated classes can have?
  YES → Interface  (e.g., Comparable, Runnable, Screenshottable)

Do you need MULTIPLE "parents"?
  YES → Interface (classes can implement many)

Does the base class have MEANINGFUL CONCRETE methods?
  YES → Abstract Class or regular class

Is it purely a CONTRACT with no state?
  YES → Interface
```

### Real Example

```java
// BasePage is NOT abstract in the automation project — it's a concrete class
// But it COULD be, because it's never instantiated directly:

// abstract class approach — forces subclasses to define page-specific behaviour
public abstract class BasePage {
    protected WebDriver driver;

    protected BasePage(WebDriver driver, Logger log) { ... }

    // Concrete — shared by all pages
    protected WebElement waitForElementVisible(WebElement ele) { ... }

    // Abstract — each page has its own loading indicator
    public abstract boolean isPageLoaded();
}

// Interface approach — contract without state
public interface PageActions {
    void navigateTo(String url);
    boolean isPageLoaded();
    String getPageTitle();
}
// Then: public class LoginPage extends BasePage implements PageActions { ... }
```

---

## 14. Static Keyword

`static` means **belonging to the class, not to any instance**. Shared across all objects.

### Static Variable (Class Variable)

```java
public class BrowserDriverFactory {
    private static int driverCount = 0;   // shared across ALL instances

    public WebDriver createDriver() {
        driverCount++;
        log.info("Driver #" + driverCount + " created");
        // ...
    }

    public static int getDriverCount() {
        return driverCount;
    }
}

BrowserDriverFactory f1 = new BrowserDriverFactory("chrome", log);
BrowserDriverFactory f2 = new BrowserDriverFactory("firefox", log);
f1.createDriver();   // driverCount = 1
f2.createDriver();   // driverCount = 2 — same variable, different instance
System.out.println(BrowserDriverFactory.getDriverCount()); // 2
```

### Static Method

- Cannot access instance fields (`this.field`) — no instance context
- Cannot call non-static methods directly
- Called on the **class**, not an object

```java
public class TestUtils {
    // static utility — no state needed, call without creating object
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
                   .format(new java.util.Date());
    }

    public static String buildScreenshotName(String testName) {
        return "ScreenShot/" + testName + "_" + getTimestamp() + ".png";
    }
}

// Usage — no object creation needed
String path = TestUtils.buildScreenshotName("loginTest");
```

### Static Block (Static Initializer)

Runs **once** when the class is first loaded. Used for one-time setup.

```java
public class ConfigurationUtils {
    private static Properties prop;

    static {
        // Runs once when class is loaded
        prop = new Properties();
        try {
            prop.load(ConfigurationUtils.class
                      .getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public Properties getProperty() {
        return prop;
    }
}
```

### Static Nested Class

```java
public class TestConfig {
    // Outer class fields...

    // Static nested class — accessed as TestConfig.Builder
    public static class Builder {
        private String browser = "chrome";
        private boolean headless = false;

        public Builder browser(String b) { this.browser = b; return this; }
        public Builder headless(boolean h) { this.headless = h; return this; }

        public TestConfig build() {
            return new TestConfig(browser, headless);
        }
    }
}

// Usage
TestConfig config = new TestConfig.Builder()
    .browser("firefox")
    .headless(true)
    .build();
```

### Static Import

```java
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

// Now you can call without class prefix:
assertEquals(loginPage.getErrorMsg(), expectedMsg);  // instead of Assert.assertEquals(...)
assertTrue(loginPage.VerifyErrorMsgIsGettingDisplayed());
```

---

## 15. Final Keyword

`final` = **cannot be changed** (once assigned / extended / overridden).

### Final Variable — Constant

```java
// Primitive final — value cannot change
public static final int DEFAULT_TIMEOUT = 30;
public static final String BASE_URL = "https://www.saucedemo.com";

// Reference final — reference cannot change, but object's state CAN change
public final List<String> browsers = new ArrayList<>();
browsers.add("chrome");   // ✅ — modifying the object
browsers.add("firefox");  // ✅
browsers = new ArrayList<>(); // ❌ COMPILE ERROR — cannot reassign reference

// final parameter — cannot reassign the parameter inside the method
public void createDriver(final String browser) {
    // browser = "chrome"; ❌ — cannot reassign
    log.info("Creating driver for: " + browser);  // ✅ — reading is fine
}

// Effectively final — Java 8+ lambda/anonymous class can use local variables
// that are never reassigned (even without final keyword)
String url = "https://www.saucedemo.com";
Runnable r = () -> System.out.println(url);  // url is effectively final
```

### Final Method — Cannot Be Overridden

```java
public class BasePage {
    // This setup must NEVER be changed by subclasses
    public final void initDriver(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
}

public class LoginPage extends BasePage {
    // @Override initDriver() ← ❌ COMPILE ERROR — final cannot be overridden
}
```

### Final Class — Cannot Be Extended

```java
// Classic example — String is final
public final class String { ... }  // cannot extend String

// In automation — use final to prevent unintended extension
public final class ConfigurationUtils {
    // This utility class should never be subclassed
}
```

### `final` vs `finally` vs `finalize`

| Keyword | Context | Purpose |
|---------|---------|---------|
| `final` | Variable/Method/Class | Prevent modification/override/extension |
| `finally` | Exception handling | Block that **always runs** (cleanup) |
| `finalize()` | Object lifecycle | Called by GC before object destroyed (deprecated Java 9+) |

```java
public void runTest() {
    WebDriver driver = null;
    try {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    } catch (Exception e) {
        log.error("Test failed: " + e.getMessage());
    } finally {
        if (driver != null) driver.quit();  // ALWAYS runs — cleanup
    }
}
```

---

## 16. String, StringBuilder, StringBuffer

### String — Immutable

```java
// String objects are immutable — every "modification" creates a new object
String s1 = "Hello";
String s2 = s1.concat(", World");  // s1 unchanged, new String created
System.out.println(s1);  // "Hello"
System.out.println(s2);  // "Hello, World"

// String pool — literals are cached
String a = "chrome";
String b = "chrome";
System.out.println(a == b);       // true  — same pool reference
System.out.println(a.equals(b));  // true  — same content

String c = new String("chrome");
System.out.println(a == c);       // false — different heap object!
System.out.println(a.equals(c));  // true  — same content
// ALWAYS use .equals() for String comparison, never ==
```

### Key String Methods

```java
String url = "  https://www.SAUCEDEMO.com/login  ";

url.trim()                        // "https://www.SAUCEDEMO.com/login" — remove whitespace
url.strip()                       // same as trim(), Java 11+
url.toLowerCase()                 // "  https://www.saucedemo.com/login  "
url.toUpperCase()                 // "  HTTPS://WWW.SAUCEDEMO.COM/LOGIN  "
url.contains("saucedemo")         // false (case sensitive)
url.toLowerCase().contains("saucedemo") // true

String locator = "css:#user-name";
locator.startsWith("css:")        // true
locator.endsWith("user-name")     // true
locator.replace("css:", "")       // "#user-name"
locator.split(":")                // ["css", "#user-name"]
locator.substring(4)              // "#user-name"  (from index 4 to end)
locator.substring(0, 3)           // "css"         (from 0 to 3 exclusive)
locator.indexOf(":")              // 3
locator.length()                  // 14
locator.charAt(0)                 // 'c'
locator.isEmpty()                 // false
locator.isBlank()                 // false  (Java 11 — checks whitespace too)
"".isEmpty()                      // true
"  ".isBlank()                    // true   (Java 11)

// String.format
String msg = String.format("Test '%s' failed on %s browser", "loginTest", "chrome");
// "Test 'loginTest' failed on chrome browser"

// join
String browsers = String.join(", ", "chrome", "firefox", "edge");
// "chrome, firefox, edge"

// valueOf
String num = String.valueOf(42);     // "42"
String bool = String.valueOf(true);  // "true"
```

### StringBuilder — Mutable, Not Thread-Safe

Use when building strings in loops or concatenating many parts. Much faster than `String` concatenation in a loop.

```java
// BAD — O(n²) — creates a new String object every iteration
String result = "";
for (String browser : browsers) {
    result += browser + ", ";   // creates new String each time!
}

// GOOD — O(n) — StringBuilder modifies in-place
StringBuilder sb = new StringBuilder();
for (String browser : browsers) {
    sb.append(browser).append(", ");
}
sb.deleteCharAt(sb.length() - 2);  // remove trailing ", "
String result = sb.toString();
```

### StringBuilder Methods

```java
StringBuilder sb = new StringBuilder("Hello");

sb.append(", World");          // "Hello, World"
sb.insert(5, " Java");         // "Hello Java, World"
sb.delete(5, 10);              // "Hello, World"
sb.replace(7, 12, "Java");     // "Hello, Java"
sb.reverse();                  // "avaJ ,olleH"
sb.charAt(0);                  // 'a'
sb.setCharAt(0, 'A');          // "AvaJ ,olleH"
sb.indexOf("J");               // 3
sb.length();                   // 11
sb.capacity();                 // default 16 + content length
sb.deleteCharAt(0);            // "vaJ ,olleH"
sb.toString();                 // convert to String
```

### StringBuffer — Mutable, Thread-Safe

Same API as `StringBuilder` but every method is synchronized. Use only in multithreaded contexts.

| | `String` | `StringBuilder` | `StringBuffer` |
|---|---------|----------------|----------------|
| Mutable | ❌ | ✅ | ✅ |
| Thread-safe | ✅ (immutable) | ❌ | ✅ |
| Performance | Slow (new objects) | Fast | Medium (sync overhead) |
| Use case | Literals, constants | Single thread, loop building | Multi-thread shared logs |

---

## 17. Exception Handling

Exceptions are events that disrupt normal program flow. Java has a structured mechanism to handle them.

### Exception Hierarchy

```
Throwable
├── Error                  (JVM-level — OutOfMemoryError, StackOverflowError — don't catch)
└── Exception
    ├── Checked Exceptions  (must handle at compile time — IOException, SQLException)
    └── RuntimeException    (unchecked — NullPointerException, ArrayIndexOutOfBoundsException)
        └── Selenium exceptions → WebDriverException (extends RuntimeException)
            ├── NoSuchElementException
            ├── TimeoutException
            ├── StaleElementReferenceException
            ├── ElementNotInteractableException
            └── ElementClickInterceptedException
```

### try-catch-finally

```java
// Basic structure
try {
    // code that might throw
    WebElement element = driver.findElement(By.id("user-name"));
    element.sendKeys("standard_user");
} catch (NoSuchElementException e) {
    log.error("Element not found: " + e.getMessage());
    // handle gracefully
} catch (TimeoutException e) {
    log.error("Timeout: " + e.getMessage());
} catch (WebDriverException e) {
    log.error("WebDriver error: " + e.getMessage());
} finally {
    // ALWAYS runs — even if exception is thrown or caught
    log.info("Attempt to interact with login field complete");
}
```

### Multi-catch (Java 7+)

```java
try {
    doSomething();
} catch (NoSuchElementException | TimeoutException e) {
    log.error("Element issue: " + e.getMessage());
}
```

### From the Automation Project — Real Exception Handling

```java
// BasePage.waitForElementVisible()
protected WebElement waitForElementVisible(WebElement ele) {
    try {
        return wait.until(ExpectedConditions.visibilityOf(ele));
    } catch (TimeoutException e) {
        log.error("Timeout waiting for element to be visible: " + e.getMessage());
        throw new NoSuchElementException("Element not found: " + ele);
        // Re-throws as different exception type — wrapping
    }
}

// BasePage.waitTime() — InterruptedException handling
protected void waitTime(int wait) {
    synchronized (driver) {
        try {
            driver.wait(wait);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();    // restore interrupted status — best practice
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }
}

// ElementUtils.clickOnElement() — retry on failure
protected void clickOnElement(WebElement element) {
    WebElement webElement = waitForElementClickable(element);
    try {
        webElement.click();
    } catch (Exception e) {
        webElement.click();  // retry once
        System.err.println("Click failed: " + e.getMessage());
    }
}
```

### `throw` vs `throws`

```java
// throws — declares that method MAY throw a checked exception (caller must handle)
public void loadConfig(String filePath) throws IOException {
    // ...
}

// throw — actually throws an exception instance
public WebDriver createDriver(String browser) {
    if (browser == null || browser.isEmpty()) {
        throw new IllegalArgumentException("Browser name cannot be null or empty");
    }
    // ...
}
```

### Custom Exceptions

```java
// Custom checked exception
public class BrowserNotFoundException extends Exception {
    public BrowserNotFoundException(String browser) {
        super("Browser not supported: " + browser);
    }

    public BrowserNotFoundException(String browser, Throwable cause) {
        super("Browser not supported: " + browser, cause);
    }
}

// Custom unchecked exception
public class PageLoadException extends RuntimeException {
    private final String pageName;

    public PageLoadException(String pageName, String message) {
        super(message);
        this.pageName = pageName;
    }

    public String getPageName() { return pageName; }
}

// Usage
public WebDriver createDriver(String browser) throws BrowserNotFoundException {
    switch (browser.toLowerCase()) {
        case "chrome":  return new ChromeDriver();
        case "firefox": return new FirefoxDriver();
        default: throw new BrowserNotFoundException(browser);
    }
}
```

### try-with-resources (Java 7+)

Automatically closes `AutoCloseable` resources. The `close()` is called even if an exception occurs.

```java
// Without try-with-resources — verbose
FileReader fr = null;
try {
    fr = new FileReader("config.properties");
    // use fr
} finally {
    if (fr != null) fr.close();
}

// With try-with-resources — clean
try (FileReader fr = new FileReader("config.properties");
     BufferedReader br = new BufferedReader(fr)) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
// fr and br are automatically closed here
```

### Checked vs Unchecked

| | Checked | Unchecked |
|---|---------|-----------|
| Extends | `Exception` | `RuntimeException` |
| Must handle? | ✅ (compile error if not) | ❌ (optional) |
| When | Recoverable external issues | Programming errors |
| Examples | `IOException`, `SQLException` | `NullPointerException`, `IndexOutOfBoundsException` |
| Selenium | — | All Selenium exceptions (`WebDriverException` subtypes) |

---

## 18. Generics

Generics allow you to write **type-safe, reusable code** without duplicating logic for different types.

### Why Generics?

```java
// Without generics — unsafe, requires casting
List list = new ArrayList();
list.add("chrome");
list.add(42);          // ← programmer error, no compile check
String s = (String) list.get(1);  // ← ClassCastException at runtime!

// With generics — type-safe
List<String> browsers = new ArrayList<>();
browsers.add("chrome");
// browsers.add(42);  ← COMPILE ERROR — caught early!
String b = browsers.get(0);  // no cast needed
```

### Generic Class

```java
// Generic pair — holds any two related values
public class TestResult<T> {
    private String testName;
    private T result;

    public TestResult(String testName, T result) {
        this.testName = testName;
        this.result = result;
    }

    public String getTestName() { return testName; }
    public T getResult() { return result; }
}

// Usage
TestResult<Boolean> loginResult = new TestResult<>("Login Test", true);
TestResult<String> urlResult    = new TestResult<>("URL Test", "https://saucedemo.com");
TestResult<Integer> countResult = new TestResult<>("Item Count", 5);

boolean passed = loginResult.getResult();   // no cast!
```

### Generic Method

```java
public class AssertionUtils {

    // Generic method — works for any comparable type
    public static <T extends Comparable<T>> void assertGreaterThan(T actual, T expected, String msg) {
        if (actual.compareTo(expected) <= 0) {
            throw new AssertionError(msg + ": expected " + actual + " > " + expected);
        }
    }

    // Generic pair-check
    public static <T> void assertNotNull(T obj, String fieldName) {
        if (obj == null) {
            throw new AssertionError(fieldName + " should not be null");
        }
    }
}

AssertionUtils.assertGreaterThan(5, 3, "Item count");       // Integer
AssertionUtils.assertGreaterThan("z", "a", "Sort order");   // String
AssertionUtils.assertNotNull(driver, "WebDriver");
```

### Bounded Type Parameters

```java
// <T extends BasePage> — T must be BasePage or a subclass
public class PageNavigator<T extends BasePage> {
    private T page;

    public PageNavigator(T page) { this.page = page; }

    public T getPage() { return page; }

    public boolean verifyLoaded() {
        return page.isPageLoaded();  // can call BasePage methods
    }
}

PageNavigator<LoginPage>    loginNav = new PageNavigator<>(loginPage);
PageNavigator<ProductsPage> prodNav  = new PageNavigator<>(productsPage);
```

### Wildcards

```java
// ? extends — read-only (upper-bounded): accepts Page or any subclass
public void printAllPageTitles(List<? extends BasePage> pages) {
    for (BasePage page : pages) {
        System.out.println(page.getPageTitle());
    }
}

// ? super — write-only (lower-bounded): accepts BasePage or any superclass
public void addPages(List<? super LoginPage> destination) {
    destination.add(new LoginPage(driver, log));
}

// ? — unbounded wildcard
public void printSize(List<?> list) {
    System.out.println(list.size());
}
```

---

## 19. Lambda Expressions & Functional Interfaces

### Functional Interface

An interface with **exactly one abstract method** (SAM — Single Abstract Method). Can have `default` and `static` methods.

```java
// Built-in functional interfaces — java.util.function
// Runnable     — () → void
// Supplier<T>  — () → T
// Consumer<T>  — T → void
// Function<T,R>— T → R
// Predicate<T> — T → boolean
// BiFunction<T,U,R> — (T, U) → R
```

### Lambda Syntax

```java
// Old: Anonymous inner class
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Test started");
    }
};

// Lambda — (parameters) -> { body }
Runnable r2 = () -> System.out.println("Test started");

// Lambda with one parameter — parentheses optional
Consumer<String> log = message -> System.out.println("[LOG] " + message);

// Lambda with multiple parameters
BiFunction<String, String, Boolean> login =
    (user, pass) -> user.equals("admin") && pass.length() >= 6;

// Lambda with block body
Function<String, WebDriver> driverFactory = browser -> {
    switch (browser.toLowerCase()) {
        case "chrome":  return new ChromeDriver();
        case "firefox": return new FirefoxDriver();
        default: throw new IllegalArgumentException("Unknown browser: " + browser);
    }
};
```

### Lambdas in the Automation Project

```java
// From BrowserDriverFactory — ThreadLocal with lambda
private ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> null);

// From BasePage — custom ExpectedCondition
wait.until((d) -> ele.isDisplayed());

// From test class — Consumer<LoginPage> as DataProvider
public Object[] getData() {
    return new Object[] {
        validLogin1.andThen(successfulLogin),     // Consumer chaining
        invalidLogin1.andThen(unsuccessfulLogin)
    };
}

// PriorityQueue with lambda comparator
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Sorting with lambda
browsers.sort((a, b) -> a.length() - b.length());

// forEach with lambda
freq.forEach((key, value) ->
    System.out.println("Browser: " + key + ", Count: " + value));

// Stream with lambda
List<String> chromeBrowsers = browsers.stream()
    .filter(b -> b.startsWith("chrome"))
    .collect(Collectors.toList());
```

### Method References — Shorthand for Lambdas

```java
// Static method reference: Class::staticMethod
Function<String, Integer> parse = Integer::parseInt;
// equivalent to: s -> Integer.parseInt(s)

// Instance method reference: instance::method
Consumer<String> printer = System.out::println;
// equivalent to: s -> System.out.println(s)

// Arbitrary instance method reference: Class::instanceMethod
Function<String, String> lower = String::toLowerCase;
// equivalent to: s -> s.toLowerCase()

// Constructor reference: Class::new
Supplier<ArrayList<String>> listFactory = ArrayList::new;
// equivalent to: () -> new ArrayList<>()

// In automation
browsers.forEach(System.out::println);
List<String> titles = pageTitles.stream()
    .map(String::toLowerCase)
    .collect(Collectors.toList());
```

---

## 20. Stream API

Streams allow **declarative data processing** — what to do, not how to do it. Introduced in Java 8.

```
Source (Collection/Array/File) → Intermediate Operations → Terminal Operation
```

### Creating Streams

```java
// From Collection
List<String> browsers = Arrays.asList("chrome", "firefox", "edge", "safari");
Stream<String> stream1 = browsers.stream();
Stream<String> parallel = browsers.parallelStream();

// From Array
int[] nums = {1, 2, 3, 4, 5};
IntStream intStream = Arrays.stream(nums);

// From values
Stream<String> stream2 = Stream.of("chrome", "firefox");

// From range
IntStream range = IntStream.range(0, 10);    // 0..9
IntStream closed = IntStream.rangeClosed(1, 5); // 1..5
```

### Intermediate Operations (Lazy — return Stream)

```java
List<String> testNames = Arrays.asList(
    "Login_Test", "Logout_Test", "Add_To_Cart", "Checkout_Test", "Login_Fail");

// filter — keep elements matching predicate
testNames.stream()
    .filter(t -> t.startsWith("Login"))      // ["Login_Test", "Login_Fail"]

// map — transform each element
testNames.stream()
    .map(String::toUpperCase)                // ["LOGIN_TEST", "LOGOUT_TEST", ...]
    .map(t -> t.replace("_", " "))

// flatMap — flatten nested collections
List<List<String>> nested = Arrays.asList(
    Arrays.asList("chrome", "firefox"), Arrays.asList("edge"));
nested.stream()
    .flatMap(Collection::stream)             // ["chrome", "firefox", "edge"]

// distinct — remove duplicates (uses equals/hashCode)
// sorted — natural or custom order
// peek — debug, doesn't modify
// limit(n) — take first n
// skip(n) — skip first n

testNames.stream()
    .filter(t -> t.contains("Test"))
    .sorted()
    .limit(2)
    .peek(t -> System.out.println("Processing: " + t))
    .collect(Collectors.toList());
```

### Terminal Operations (Eager — produce result)

```java
// collect — most versatile terminal
List<String> loginTests = testNames.stream()
    .filter(t -> t.contains("Login"))
    .collect(Collectors.toList());

Map<Boolean, List<String>> grouped = testNames.stream()
    .collect(Collectors.partitioningBy(t -> t.contains("Login")));

Map<Integer, List<String>> byLength = testNames.stream()
    .collect(Collectors.groupingBy(String::length));

String joined = testNames.stream()
    .collect(Collectors.joining(", ", "[", "]"));  // "[Login_Test, Logout_Test, ...]"

// count
long loginCount = testNames.stream()
    .filter(t -> t.startsWith("Login"))
    .count();   // 2

// findFirst / findAny
Optional<String> first = testNames.stream()
    .filter(t -> t.contains("Cart"))
    .findFirst();
first.ifPresent(t -> System.out.println("Found: " + t));
first.orElse("No cart test found");

// anyMatch / allMatch / noneMatch
boolean hasLoginTest  = testNames.stream().anyMatch(t -> t.contains("Login"));
boolean allHaveTest   = testNames.stream().allMatch(t -> t.endsWith("Test"));
boolean noEmptyNames  = testNames.stream().noneMatch(String::isEmpty);

// min / max
Optional<String> shortest = testNames.stream()
    .min(Comparator.comparingInt(String::length));

// reduce — combine all elements into one
int sum = IntStream.rangeClosed(1, 5).reduce(0, Integer::sum);  // 15

// forEach
testNames.stream().forEach(System.out::println);

// toArray
String[] arr = testNames.stream().toArray(String[]::new);
```

### Optional — Avoiding NullPointerException

```java
Optional<String> opt = testNames.stream()
    .filter(t -> t.contains("Payment"))
    .findFirst();

// Don't use: opt.get() — throws NoSuchElementException if empty

opt.isPresent()                // true/false
opt.isEmpty()                  // Java 11+
opt.get()                      // ⚠️ only if isPresent() is true
opt.orElse("default")          // value or default
opt.orElseGet(() -> computeDefault())   // value or supplier result
opt.orElseThrow(() -> new RuntimeException("Not found"))
opt.ifPresent(t -> System.out.println(t))
opt.map(String::toLowerCase)   // transform if present
opt.filter(t -> t.length() > 5) // filter if present
```

---

## 21. Java Memory Model — Stack vs Heap

Understanding memory is critical for debugging and writing efficient code.

### Stack Memory

- Stores **method frames** (local variables, method call info)
- **Thread-private** — each thread has its own stack
- **LIFO** order — method frames pushed/popped
- Fixed size — `StackOverflowError` if exceeded (e.g., infinite recursion)
- Variables on stack are automatically cleaned when method returns

### Heap Memory

- Stores **objects** (all `new` creations)
- **Shared** across all threads — needs synchronization for safety
- Managed by Garbage Collector
- Large — `OutOfMemoryError` if heap exhausted

```java
public class MemoryDemo {

    // Static field — stored in Method Area (PermGen/Metaspace), shared
    public static final String BASE_URL = "https://saucedemo.com";

    public void launchBrowser() {
        // Stack: 'browser' variable (reference) lives here
        String browser = "chrome";              // reference on stack, "chrome" in heap string pool

        // Stack: 'driver' reference lives here; ChromeDriver object lives in heap
        WebDriver driver = new ChromeDriver();  // object on heap

        // Stack: 'timeout' primitive lives directly on stack
        int timeout = 30;                       // value on stack (no heap involved)

        // When launchBrowser() returns, stack frame is popped:
        // 'browser', 'driver', 'timeout' references removed from stack
        // ChromeDriver object on heap is eligible for GC (if no other references)
    }
}
```

### Garbage Collection

Java automatically frees heap memory. You don't call `free()` like in C/C++.

```java
WebDriver driver = new ChromeDriver();     // ChromeDriver object on heap
driver = new FirefoxDriver();              // previous ChromeDriver now unreachable → GC eligible

// Explicitly remove reference
driver = null;      // FirefoxDriver now eligible for GC

// In BaseTest — proper cleanup prevents memory leaks
@AfterMethod
public void tearDown() {
    if (driver != null) {
        driver.quit();   // closes browser (OS resource)
        driver = null;   // removes reference → enables GC
    }
}
```

### String Pool

```java
String a = "chrome";       // Stored in String pool (heap)
String b = "chrome";       // Same pool reference
a == b;                    // true — same object in pool

String c = new String("chrome");  // New object in heap (NOT pool)
a == c;                    // false — different heap objects
a.equals(c);               // true  — same content

String d = c.intern();     // Returns pool reference
a == d;                    // true  — intern() finds/adds to pool
```

---

## 22. Wrapper Classes & Autoboxing

Every primitive has a corresponding **Wrapper class** in `java.lang`:

| Primitive | Wrapper | Useful Methods |
|-----------|---------|----------------|
| `int` | `Integer` | `parseInt()`, `valueOf()`, `MAX_VALUE`, `MIN_VALUE`, `toBinaryString()` |
| `long` | `Long` | `parseLong()`, `valueOf()` |
| `double` | `Double` | `parseDouble()`, `isNaN()`, `isInfinite()` |
| `boolean` | `Boolean` | `parseBoolean()`, `valueOf()`, `TRUE`, `FALSE` |
| `char` | `Character` | `isLetter()`, `isDigit()`, `toUpperCase()`, `toLowerCase()` |
| `byte` | `Byte` | `parseByte()` |
| `short` | `Short` | `parseShort()` |
| `float` | `Float` | `parseFloat()` |

### Autoboxing & Unboxing

```java
// Autoboxing — primitive → Wrapper (automatic)
int primitive = 42;
Integer wrapper = primitive;          // int → Integer
List<Integer> list = new ArrayList<>();
list.add(10);                         // int → Integer (autoboxing in add)

// Unboxing — Wrapper → primitive (automatic)
Integer w = Integer.valueOf(100);
int p = w;                            // Integer → int
int sum = w + 50;                     // w unboxed to int before +

// Common traps
Integer a = 127;
Integer b = 127;
a == b;           // true  — Integer cache: -128 to 127 pooled

Integer c = 128;
Integer d = 128;
c == d;           // false — outside cache, new objects!
c.equals(d);      // true  — use equals() for Integer comparison

// NullPointerException trap
Integer val = null;
int x = val;      // NullPointerException — unboxing null throws NPE!

// String → numeric conversion (common in test data)
String timeout = "30";
int t = Integer.parseInt(timeout);       // 30
double d2 = Double.parseDouble("3.14"); // 3.14
boolean b2 = Boolean.parseBoolean("true"); // true (case-insensitive)
```

### Wrapper in Automation

```java
// Reading from Properties (returns String) — convert to needed type
Properties prop = new ConfigurationUtils().getProperty();
int implicitWait = Integer.parseInt(prop.getProperty("implicitWait", "10"));
boolean isHeadless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

// Environment variables (always String) — same pattern
String isHeadlessEnv = System.getenv("isHeadless");
boolean headless = (isHeadlessEnv != null) ? Boolean.parseBoolean(isHeadlessEnv) : false;
```

---

## 23. Design Patterns in Automation

### Pattern 1: Factory Pattern — `BrowserDriverFactory`

Creates objects without exposing the creation logic. Caller only knows the product (`WebDriver`), not the concrete type.

```java
public class BrowserDriverFactory {
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    // Factory method — returns WebDriver (interface), hides concrete type
    public WebDriver createDriver() {
        log.info("Create driver: " + browser);
        switch (browser) {
            case "chrome":         return new ChromeDriver(buildChromeOptions());
            case "firefox":        return new FirefoxDriver();
            case "chromeheadless": return new ChromeDriver(buildHeadlessChromeOptions());
            case "firefoxheadless":return buildHeadlessFirefox();
            default:
                log.warn("Unknown browser: " + browser + ". Falling back to Chrome.");
                return new ChromeDriver();
        }
    }
}

// Usage in BaseTest — caller is decoupled from browser creation
BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
driver = factory.createDriver();   // don't know/care if it's Chrome or Firefox
```

**Why it's a Factory:** The caller says "give me a WebDriver" without needing to know which one. Swap chrome for firefox? Just change config — zero test code changes.

### Pattern 2: Page Object Model — Encapsulation + Inheritance

POM is not one GoF pattern — it combines multiple:
- **Encapsulation**: hide locators and interactions inside page class
- **Inheritance**: page classes extend `BasePage`/`ElementUtils`
- **Facade**: page class provides simple methods hiding complex Selenium calls

```java
// Page object encapsulates everything about the login page
public class LoginPage extends ElementUtils {

    // Encapsulated — tests never see these
    @FindBy(css = "#user-name") private WebElement txt_username;
    @FindBy(css = "#password")  private WebElement txt_pwd;
    @FindBy(id = "login-button") private WebElement btn_login;

    // Clean API — what tests actually call
    public ProductsPage login() {
        driver.get(url);
        waitForElementVisible(txt_username).sendKeys(userName);  // inherited wait
        waitForElementVisible(txt_pwd).sendKeys(password);
        waitForElementClickable(btn_login).click();
        return new ProductsPage(driver, log);  // returns next page → chaining
    }

    public String getErrorMsg() {
        return waitForElementVisible(label_errorMsg).getText();
    }
}

// Test class — reads like plain English, no Selenium noise
public class LoginTest extends BaseTest {
    @Test
    public void validLoginTest() {
        ProductsPage productsPage = loginPage.login();
        Assert.assertTrue(productsPage.isPageLoaded());
    }
}
```

### Pattern 3: Singleton — Single Instance

Ensures only **one instance** of a class exists throughout the application lifecycle. Common for driver, config, reporter.

```java
public class ConfigurationUtils {
    private static ConfigurationUtils instance;
    private Properties prop;

    // Private constructor — prevents new ConfigurationUtils()
    private ConfigurationUtils() {
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config", e);
        }
    }

    // Thread-safe singleton
    public static synchronized ConfigurationUtils getInstance() {
        if (instance == null) {
            instance = new ConfigurationUtils();
        }
        return instance;
    }

    public Properties getProperty() { return prop; }
    public String getProperty(String key) { return prop.getProperty(key); }
}

// Usage — same instance everywhere
String url = ConfigurationUtils.getInstance().getProperty("url");
```

### Pattern 4: Builder Pattern

Constructs complex objects step-by-step. Solves the "telescoping constructor" problem (many optional parameters).

```java
public class DriverConfig {
    private final String browser;
    private final boolean headless;
    private final int implicitWait;
    private final String downloadPath;
    private final String profilePath;

    private DriverConfig(Builder builder) {
        this.browser       = builder.browser;
        this.headless      = builder.headless;
        this.implicitWait  = builder.implicitWait;
        this.downloadPath  = builder.downloadPath;
        this.profilePath   = builder.profilePath;
    }

    // Getters
    public String getBrowser()      { return browser; }
    public boolean isHeadless()     { return headless; }
    public int getImplicitWait()    { return implicitWait; }

    public static class Builder {
        private String browser = "chrome";     // defaults
        private boolean headless = false;
        private int implicitWait = 10;
        private String downloadPath = "";
        private String profilePath = "";

        public Builder browser(String browser)          { this.browser = browser; return this; }
        public Builder headless(boolean headless)        { this.headless = headless; return this; }
        public Builder implicitWait(int seconds)         { this.implicitWait = seconds; return this; }
        public Builder downloadPath(String path)         { this.downloadPath = path; return this; }
        public Builder profilePath(String path)          { this.profilePath = path; return this; }

        public DriverConfig build() {
            if (browser == null || browser.isEmpty())
                throw new IllegalStateException("Browser must be specified");
            return new DriverConfig(this);
        }
    }
}

// Clean construction — only specify what you need
DriverConfig config = new DriverConfig.Builder()
    .browser("chrome")
    .headless(true)
    .implicitWait(30)
    .build();
```

### Pattern 5: Strategy Pattern — Different Login Strategies

Define a family of algorithms, encapsulate each one, and make them interchangeable.

```java
// Strategy interface
@FunctionalInterface
public interface LoginStrategy {
    void execute(LoginPage loginPage);
}

// Strategies as lambdas (from the automation project concept)
LoginStrategy validLogin     = page -> page.loginWithUserInfo("standard_user", "secret_sauce");
LoginStrategy lockedLogin    = page -> page.loginWithUserInfo("locked_out_user", "secret_sauce");
LoginStrategy invalidLogin   = page -> page.loginWithUserInfo("invalid_user", "wrong_pass");

// Consumer chaining (actual pattern in the project)
Consumer<LoginPage> validLogin1 = page -> page.loginWithUserInfo("standard_user", "secret_sauce");
Consumer<LoginPage> successfulLogin = page -> {
    Assert.assertTrue(new ProductsPage(driver, log).isPageLoaded());
};

// Combined: login THEN verify
Consumer<LoginPage> fullTest = validLogin1.andThen(successfulLogin);
fullTest.accept(loginPage);  // executes both consumers in sequence
```

---

## 24. Interview Q&A

### OOP

**Q: What is the difference between Abstraction and Encapsulation?**
> **Abstraction** = hiding the *implementation details* and showing only the essential contract. Example: `WebDriver` interface hides how Chrome/Firefox actually drives the browser — you just call `driver.get(url)`.
> **Encapsulation** = binding data and methods together and *hiding internal state* via access modifiers. Example: `LoginPage` hides its `txt_username` field (private) and exposes only `login()` and `getErrorMsg()` methods.
> One-liner: Abstraction is about *design* (what), Encapsulation is about *protection* (how).

**Q: Can you have an abstract class without any abstract methods?**
> Yes. An abstract class with only concrete methods is valid. It just cannot be instantiated. You'd use this when you want to prevent direct instantiation but provide full implementations to subclasses. `BasePage` in the automation project is essentially this — it could be declared `abstract` since it's never instantiated directly.

**Q: What is the difference between method overloading and overriding?**
> **Overloading** = same method name, different parameters, in the SAME class. Resolved at compile time. Example: `enterText(element, text)` vs `enterText(element, text, clear)` in `ElementUtils`.
> **Overriding** = subclass re-implements a method from the parent with the SAME signature. Resolved at runtime. Example: Each page class overrides `isPageLoaded()`.

**Q: What is covariant return type?**
> An overriding method can return a subtype of the parent method's return type. E.g., if parent returns `BasePage`, child can return `LoginPage` (which is-a `BasePage`).

**Q: Can we override static methods?**
> No — static methods are **hidden**, not overridden. When a child class declares a static method with the same signature, it creates a new class-level method, not polymorphic behaviour. `super.staticMethod()` vs `Child.staticMethod()` always calls the class it's declared on.

**Q: Why can't we have multiple inheritance in Java?**
> The **Diamond Problem** — if class C extends A and B, and both A and B have a method `m()`, which version does C inherit? Java avoids this ambiguity for classes. Interfaces solve it since Java 8 via `default` methods with explicit resolution: `A.super.m()`.

---

### `this` and `super`

**Q: When is `super()` called implicitly?**
> If a constructor does not explicitly call `super(...)` or `this(...)`, the compiler inserts `super()` (no-arg) as the first statement. If the parent has no no-arg constructor, it's a compile error. This is why `BasePage` has a constructor `BasePage(WebDriver, Logger)` and `ElementUtils` must explicitly call `super(driver, log)`.

**Q: Can `this()` and `super()` be in the same constructor?**
> No. Both must be the first statement, so only one can exist in a constructor.

---

### Interfaces & Abstract Classes

**Q: Can an interface extend another interface?**
> Yes, and it can extend multiple interfaces. `interface C extends A, B { }`. A class implementing C must implement all methods from A, B, and C.

**Q: What is a marker interface?**
> An interface with no methods. Used to "tag" classes. Examples: `Serializable`, `Cloneable`. JVM or frameworks check `instanceof` to enable special behaviour.

**Q: Can an interface have a constructor?**
> No. Interfaces cannot be instantiated, so constructors are meaningless. They do have `static` and `default` methods (Java 8+).

---

### Exceptions

**Q: What is the difference between `throw` and `throws`?**
> `throws` is a declaration on a method signature: "this method might throw this exception". `throw` is the actual act of throwing an exception instance inside a method body.

**Q: What happens if an exception is thrown in a `finally` block?**
> It replaces any exception thrown in the `try` or `catch` block. The original exception is lost. This is why you should be careful about code in `finally` — keep it to cleanup only (e.g., `driver.quit()`).

**Q: Why is it bad practice to catch `Exception` or `Throwable`?**
> It swallows unexpected errors (like `NullPointerException`, `OutOfMemoryError`) masking bugs. Always catch the most specific exception type. Catch `Exception` only at the top-most level (like a TestNG listener) to log and rethrow.

---

### Static & Final

**Q: Can a static method be overridden?**
> No — it's **hidden**. The child class can declare a static method with the same signature, but it's a separate class-level method. No runtime polymorphism applies to static methods.

**Q: What is a static factory method?**
> A `static` method that returns an instance of the class, providing an alternative to constructors. Example: `Integer.valueOf(42)`, `List.of("a","b")`, `Optional.of(x)`. Benefits: descriptive names, can return cached instances, can return subtypes.

**Q: Why is `String` immutable and `final`?**
> Immutable — allows safe sharing across threads, enables the String pool (cache), and is a security requirement (class names, file paths). Final — prevents a malicious subclass from overriding `equals()` or `hashCode()` and breaking security.

---

### Memory & Performance

**Q: What is the difference between Stack and Heap?**
> Stack: per-thread, stores method frames + local variables, automatically managed (LIFO), fast, small. Heap: shared, stores all objects, managed by GC, large but slower.

**Q: What causes a `StackOverflowError`?**
> Infinite or very deep recursion — each method call adds a frame to the stack until it overflows. Example: forgot base case in recursive reverseLinkedList.

**Q: When do you get `OutOfMemoryError`?**
> When the heap is full — too many objects, memory leak (holding references preventing GC), or too large an object (loading giant file into memory).

---

### Generics & Lambdas

**Q: What is type erasure in generics?**
> Generics are a compile-time feature only. At runtime, `List<String>` and `List<Integer>` are both just `List`. The type parameter is erased by the compiler. This is why you can't do `new T()` or `instanceof List<String>`.

**Q: What is the difference between `Consumer`, `Supplier`, and `Function`?**
> `Consumer<T>`: takes T, returns nothing — side effects. `t -> System.out.println(t)`
> `Supplier<T>`: takes nothing, returns T — lazy factory. `() -> new ChromeDriver()`
> `Function<T,R>`: takes T, returns R — transformation. `s -> s.toUpperCase()`

---

