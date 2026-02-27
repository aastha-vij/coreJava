# Design Patterns in Java — Complete Reference Guide

> Every pattern is shown with a general example AND an automation/framework example.

---

## Table of Contents
1. [Creational Patterns](#creational-patterns)
   - [Singleton](#1-singleton)
   - [Factory Method](#2-factory-method)
   - [Abstract Factory](#3-abstract-factory)
   - [Builder](#4-builder)
   - [Prototype](#5-prototype)
2. [Structural Patterns](#structural-patterns)
   - [Adapter](#6-adapter)
   - [Decorator](#7-decorator)
   - [Proxy](#8-proxy)
   - [Facade](#9-facade)
   - [Composite](#10-composite)
3. [Behavioral Patterns](#behavioral-patterns)
   - [Strategy](#11-strategy)
   - [Observer](#12-observer)
   - [Command](#13-command)
   - [Template Method](#14-template-method)
   - [Iterator](#15-iterator)
   - [Chain of Responsibility](#16-chain-of-responsibility)
4. [Interview Q&A](#interview-qa)

---

## Creational Patterns

### 1. Singleton

**Intent:** Ensure only ONE instance of a class exists globally.

**When to use:** Config loader, driver manager, logger, report manager.

```java
// Thread-safe Singleton using double-checked locking
public final class ConfigManager {
    private static volatile ConfigManager instance;
    private final Properties props;

    private ConfigManager() {                          // private — no new ConfigManager()
        props = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/config.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {                        // first check (no lock)
            synchronized (ConfigManager.class) {
                if (instance == null) {                // second check (with lock)
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public String get(String key) { return props.getProperty(key); }
    public String get(String key, String defaultVal) { return props.getProperty(key, defaultVal); }
}

// Usage — same instance everywhere
String url     = ConfigManager.getInstance().get("url");
String browser = ConfigManager.getInstance().get("browser", "chrome");
```

**Enum Singleton (Best Practice — thread-safe, serialization-safe):**
```java
public enum ExtentReportManager {
    INSTANCE;

    private ExtentReports extent;

    ExtentReportManager() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("Reports/report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    public ExtentReports getExtent() { return extent; }
}

// Usage
ExtentReportManager.INSTANCE.getExtent().createTest("Login Test");
```

---

### 2. Factory Method

**Intent:** Define an interface for creating objects, but let subclasses decide which class to instantiate.

**When to use:** Creating different types of drivers, reporters, or data readers without exposing creation logic.

```java
// Product interface
public interface WebDriver { void get(String url); /* ... */ }

// Concrete products
public class ChromeDriver implements WebDriver { /* chrome impl */ }
public class FirefoxDriver implements WebDriver { /* firefox impl */ }

// Factory
public class BrowserDriverFactory {
    public WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":  return new ChromeDriver(buildChromeOptions());
            case "firefox": return new FirefoxDriver();
            case "edge":    return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}

// Client — only knows WebDriver interface, not concrete type
BrowserDriverFactory factory = new BrowserDriverFactory();
WebDriver driver = factory.createDriver("chrome");
driver.get("https://saucedemo.com");   // works for any browser
```

**Data Reader Factory:**
```java
public interface TestDataReader {
    Map<String, String> readRow(int rowIndex);
}

public class CsvDataReader implements TestDataReader { /* reads CSV */ }
public class JsonDataReader implements TestDataReader { /* reads JSON */ }
public class ExcelDataReader implements TestDataReader { /* reads Excel */ }

public class DataReaderFactory {
    public static TestDataReader create(String type) {
        switch (type.toLowerCase()) {
            case "csv":   return new CsvDataReader();
            case "json":  return new JsonDataReader();
            case "excel": return new ExcelDataReader();
            default: throw new IllegalArgumentException("Unknown data type: " + type);
        }
    }
}

TestDataReader reader = DataReaderFactory.create("csv");
```

---

### 3. Abstract Factory

**Intent:** Create families of related objects without specifying their concrete classes. A factory of factories.

**When to use:** Cross-browser, cross-platform, cross-environment test execution.

```java
// Abstract factory
public interface UIComponentFactory {
    Button createButton();
    TextField createTextField();
    Dropdown createDropdown();
}

// Concrete factories (one per browser/platform)
public class ChromeComponentFactory implements UIComponentFactory {
    public Button      createButton()    { return new ChromeButton(); }
    public TextField   createTextField() { return new ChromeTextField(); }
    public Dropdown    createDropdown()  { return new ChromeDropdown(); }
}

public class MobileComponentFactory implements UIComponentFactory {
    public Button      createButton()    { return new MobileButton(); }
    public TextField   createTextField() { return new MobileTextField(); }
    public Dropdown    createDropdown()  { return new MobileDropdown(); }
}

// Client — completely decoupled from concrete types
public class LoginPageTest {
    private final UIComponentFactory factory;

    public LoginPageTest(UIComponentFactory factory) {
        this.factory = factory;
    }

    public void runTest() {
        Button loginBtn    = factory.createButton();    // works for any platform
        TextField username = factory.createTextField();
        loginBtn.click();
    }
}

// Wire up at startup
UIComponentFactory factory = System.getProperty("platform").equals("mobile")
    ? new MobileComponentFactory()
    : new ChromeComponentFactory();
new LoginPageTest(factory).runTest();
```

---

### 4. Builder

**Intent:** Construct complex objects step by step. Separates construction from representation.

**When to use:** Objects with many optional parameters (avoids telescoping constructors).

```java
public class DriverConfig {
    // Required
    private final String browser;
    // Optional
    private final boolean headless;
    private final int implicitWait;
    private final int pageLoadTimeout;
    private final String downloadPath;
    private final String proxyHost;
    private final int proxyPort;

    private DriverConfig(Builder builder) {
        this.browser         = builder.browser;
        this.headless        = builder.headless;
        this.implicitWait    = builder.implicitWait;
        this.pageLoadTimeout = builder.pageLoadTimeout;
        this.downloadPath    = builder.downloadPath;
        this.proxyHost       = builder.proxyHost;
        this.proxyPort       = builder.proxyPort;
    }

    // Getters
    public String getBrowser()       { return browser; }
    public boolean isHeadless()      { return headless; }
    public int getImplicitWait()     { return implicitWait; }

    public static class Builder {
        private final String browser;             // required
        private boolean headless     = false;     // optional with defaults
        private int implicitWait     = 10;
        private int pageLoadTimeout  = 30;
        private String downloadPath  = "";
        private String proxyHost     = "";
        private int proxyPort        = 0;

        public Builder(String browser) {
            this.browser = browser;
        }

        public Builder headless(boolean val)        { headless = val; return this; }
        public Builder implicitWait(int val)         { implicitWait = val; return this; }
        public Builder pageLoadTimeout(int val)      { pageLoadTimeout = val; return this; }
        public Builder downloadPath(String val)      { downloadPath = val; return this; }
        public Builder proxy(String host, int port)  { proxyHost = host; proxyPort = port; return this; }

        public DriverConfig build() {
            if (browser == null || browser.isEmpty())
                throw new IllegalStateException("Browser is required");
            return new DriverConfig(this);
        }
    }
}

// Usage — clean, self-documenting
DriverConfig config = new DriverConfig.Builder("chrome")
    .headless(true)
    .implicitWait(30)
    .pageLoadTimeout(60)
    .downloadPath("/tmp/downloads")
    .build();
```

---

### 5. Prototype

**Intent:** Clone existing objects instead of creating new ones from scratch.

```java
// Page config that can be cloned with small modifications
public class TestEnvironment implements Cloneable {
    private String baseUrl;
    private String dbUrl;
    private Map<String, String> headers = new HashMap<>();

    @Override
    protected TestEnvironment clone() throws CloneNotSupportedException {
        TestEnvironment clone = (TestEnvironment) super.clone();
        clone.headers = new HashMap<>(this.headers);  // deep copy mutable field
        return clone;
    }
}

TestEnvironment prod = new TestEnvironment();
prod.setBaseUrl("https://www.prod.saucedemo.com");

TestEnvironment staging = prod.clone();
staging.setBaseUrl("https://staging.saucedemo.com");  // only URL differs
// Both share same DB config, headers — reduces setup
```

---

## Structural Patterns

### 6. Adapter

**Intent:** Allow incompatible interfaces to work together by wrapping one with an adapter.

**When to use:** Integrating a 3rd party library without changing existing code.

```java
// Existing interface your framework uses
public interface Reporter {
    void startTest(String testName);
    void pass(String message);
    void fail(String message);
    void endTest();
}

// 3rd party Extent Report API (different interface)
// ExtentTest.log(Status.PASS, "message")

// Adapter — wraps ExtentTest, exposes your Reporter interface
public class ExtentReportAdapter implements Reporter {
    private ExtentTest extentTest;
    private ExtentReports extent;

    public ExtentReportAdapter(ExtentReports extent) {
        this.extent = extent;
    }

    @Override
    public void startTest(String testName) {
        extentTest = extent.createTest(testName);
    }

    @Override
    public void pass(String message) {
        extentTest.log(Status.PASS, message);
    }

    @Override
    public void fail(String message) {
        extentTest.log(Status.FAIL, message);
    }

    @Override
    public void endTest() {
        extent.flush();
    }
}

// Your framework code never changes — works with any Reporter
Reporter reporter = new ExtentReportAdapter(extentReports);
reporter.startTest("Login Test");
reporter.pass("Login successful");
```

---

### 7. Decorator

**Intent:** Dynamically add behaviour to objects without modifying their class. Wraps the original in layers.

**When to use:** Adding logging, retry, screenshots, timing to existing page methods.

```java
// Component interface
public interface PageActions {
    void clickElement(WebElement element);
    void enterText(WebElement element, String text);
}

// Concrete component
public class SeleniumActions implements PageActions {
    public void clickElement(WebElement element)           { element.click(); }
    public void enterText(WebElement element, String text) { element.sendKeys(text); }
}

// Decorator — adds logging
public class LoggingDecorator implements PageActions {
    private final PageActions wrapped;
    private final Logger log;

    public LoggingDecorator(PageActions wrapped, Logger log) {
        this.wrapped = wrapped;
        this.log = log;
    }

    @Override
    public void clickElement(WebElement element) {
        log.info("Clicking element: " + element);
        wrapped.clickElement(element);
        log.info("Clicked successfully");
    }

    @Override
    public void enterText(WebElement element, String text) {
        log.info("Entering text: " + text);
        wrapped.enterText(element, text);
    }
}

// Decorator — adds screenshot on failure
public class ScreenshotDecorator implements PageActions {
    private final PageActions wrapped;
    private final WebDriver driver;

    public ScreenshotDecorator(PageActions wrapped, WebDriver driver) {
        this.wrapped = wrapped;
        this.driver = driver;
    }

    @Override
    public void clickElement(WebElement element) {
        try {
            wrapped.clickElement(element);
        } catch (Exception e) {
            takeScreenshot("click_failure");
            throw e;
        }
    }
    /* ... */
}

// Stack decorators
PageActions actions = new ScreenshotDecorator(
    new LoggingDecorator(
        new SeleniumActions(),
        log
    ),
    driver
);
actions.clickElement(loginButton);  // logs → screenshots on failure → actual click
```

---

### 8. Proxy

**Intent:** Provide a surrogate or placeholder for another object to control access.

**Types:** Virtual proxy (lazy init), Protection proxy (access control), Remote proxy (network), Logging proxy.

```java
// WebDriver Proxy for logging all commands
public class LoggingWebDriverProxy implements InvocationHandler {
    private final WebDriver realDriver;
    private final Logger log;

    public LoggingWebDriverProxy(WebDriver realDriver, Logger log) {
        this.realDriver = realDriver;
        this.log = log;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("WebDriver." + method.getName() + "(" + Arrays.toString(args) + ")");
        long start = System.currentTimeMillis();
        Object result = method.invoke(realDriver, args);
        log.info("Completed in " + (System.currentTimeMillis() - start) + "ms");
        return result;
    }
}

// Create proxy
WebDriver driver = new ChromeDriver();
WebDriver proxy = (WebDriver) Proxy.newProxyInstance(
    driver.getClass().getClassLoader(),
    new Class[]{WebDriver.class},
    new LoggingWebDriverProxy(driver, log)
);
proxy.get("https://saucedemo.com");  // logs: "WebDriver.get([https://saucedemo.com])"
```

---

### 9. Facade

**Intent:** Provide a simple interface to a complex subsystem.

**When to use:** The Page Object itself is a Facade — it hides Selenium's complex API behind simple methods.

```java
// Complex subsystem — Selenium + Wait + JS + Actions
public class LoginPage extends ElementUtils {    // ← FACADE

    @FindBy(css = "#user-name")  private WebElement txt_username;
    @FindBy(css = "#password")   private WebElement txt_pwd;
    @FindBy(id = "login-button") private WebElement btn_login;

    // Simple facade method — hides all complexity
    public ProductsPage login(String user, String pass) {
        driver.get(prop.getProperty("url"));                      // navigation
        waitForElementVisible(txt_username).sendKeys(user);       // wait + interact
        waitForElementVisible(txt_pwd).sendKeys(pass);
        waitForElementClickable(btn_login).click();               // wait + click
        return new ProductsPage(driver, log);                     // return next page
    }
}

// Test code — simple facade call
ProductsPage page = loginPage.login("standard_user", "secret_sauce");
// Internally: navigate + wait + type + wait + click + init next page
```

---

### 10. Composite

**Intent:** Treat individual objects and compositions of objects uniformly (tree structure).

```java
// Test suite hierarchy
public interface TestComponent {
    void run();
    String getName();
}

// Leaf
public class TestCase implements TestComponent {
    private final String name;
    private final Runnable testLogic;

    public TestCase(String name, Runnable logic) {
        this.name = name;
        this.testLogic = logic;
    }

    @Override
    public void run()        { System.out.println("Running: " + name); testLogic.run(); }
    @Override
    public String getName()  { return name; }
}

// Composite
public class TestSuite implements TestComponent {
    private final String name;
    private final List<TestComponent> children = new ArrayList<>();

    public TestSuite(String name) { this.name = name; }

    public void add(TestComponent component)    { children.add(component); }
    public void remove(TestComponent component) { children.remove(component); }

    @Override
    public void run()       { children.forEach(TestComponent::run); }
    @Override
    public String getName() { return name; }
}

// Build composite tree
TestSuite regression = new TestSuite("Regression Suite");
TestSuite smoke = new TestSuite("Smoke Tests");
smoke.add(new TestCase("Login Test", () -> runLoginTest()));
smoke.add(new TestCase("Homepage Test", () -> runHomepageTest()));
regression.add(smoke);
regression.add(new TestCase("Full Checkout Flow", () -> runCheckoutTest()));
regression.run();  // runs all recursively
```

---

## Behavioral Patterns

### 11. Strategy

**Intent:** Define a family of algorithms, encapsulate each, make them interchangeable.

**When to use:** Different browsers, different data sources, different wait strategies.

```java
// Strategy interface
public interface WaitStrategy {
    WebElement waitFor(WebDriver driver, By locator);
}

// Concrete strategies
public class ImplicitWaitStrategy implements WaitStrategy {
    @Override
    public WebElement waitFor(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }
}

public class ExplicitWaitStrategy implements WaitStrategy {
    private final int timeoutSeconds;

    public ExplicitWaitStrategy(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public WebElement waitFor(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

public class FluentWaitStrategy implements WaitStrategy {
    @Override
    public WebElement waitFor(WebDriver driver, By locator) {
        return new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(NoSuchElementException.class)
            .until(d -> d.findElement(locator));
    }
}

// Context — uses a strategy
public class ElementFinder {
    private WaitStrategy strategy;

    public ElementFinder(WaitStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(WaitStrategy strategy) {
        this.strategy = strategy;   // swap at runtime
    }

    public WebElement find(WebDriver driver, By locator) {
        return strategy.waitFor(driver, locator);
    }
}

// Usage
ElementFinder finder = new ElementFinder(new ExplicitWaitStrategy(30));
finder.setStrategy(new FluentWaitStrategy());  // swap strategy
```

---

### 12. Observer

**Intent:** When one object changes state, all dependents are notified automatically.

**When to use:** Test event system — notify reporters, loggers, screenshot services on test events.

```java
// Observer interface
public interface TestEventListener {
    void onTestStarted(String testName);
    void onTestPassed(String testName);
    void onTestFailed(String testName, Throwable cause);
}

// Observable (Subject)
public class TestRunner {
    private final List<TestEventListener> listeners = new ArrayList<>();

    public void addListener(TestEventListener listener) { listeners.add(listener); }
    public void removeListener(TestEventListener listener) { listeners.remove(listener); }

    private void notifyStarted(String name)               { listeners.forEach(l -> l.onTestStarted(name)); }
    private void notifyPassed(String name)                { listeners.forEach(l -> l.onTestPassed(name)); }
    private void notifyFailed(String name, Throwable e)   { listeners.forEach(l -> l.onTestFailed(name, e)); }

    public void runTest(String name, Runnable test) {
        notifyStarted(name);
        try {
            test.run();
            notifyPassed(name);
        } catch (Throwable e) {
            notifyFailed(name, e);
        }
    }
}

// Concrete observers
public class ExtentReportListener implements TestEventListener { /* update report */ }
public class ScreenshotListener implements TestEventListener  { /* capture on fail */ }
public class SlackNotifier implements TestEventListener       { /* post to Slack */ }

// Wire up
TestRunner runner = new TestRunner();
runner.addListener(new ExtentReportListener());
runner.addListener(new ScreenshotListener());
runner.addListener(new SlackNotifier());
runner.runTest("Login Test", () -> loginPage.login());
```

---

### 13. Command

**Intent:** Encapsulate a request as an object, supporting undo/redo and queuing.

```java
// Command interface
public interface TestCommand {
    void execute();
    void undo();
}

// Concrete commands
public class NavigateCommand implements TestCommand {
    private final WebDriver driver;
    private final String url;
    private String previousUrl;

    public NavigateCommand(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
    }

    @Override
    public void execute() {
        previousUrl = driver.getCurrentUrl();
        driver.get(url);
    }

    @Override
    public void undo() {
        driver.get(previousUrl);
    }
}

// Invoker — runs commands with undo support
public class TestInvoker {
    private final Deque<TestCommand> history = new ArrayDeque<>();

    public void execute(TestCommand cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}
```

---

### 14. Template Method

**Intent:** Define the skeleton of an algorithm in a base class, letting subclasses fill in specific steps.

**When to use:** Test lifecycle — `@BeforeMethod`, test body, `@AfterMethod` skeleton defined in `BaseTest`.

```java
// Template method in BaseTest
public abstract class BaseTest {

    protected WebDriver driver;

    // TEMPLATE METHOD — defines the test lifecycle skeleton
    public final void runTest() {
        setUp();          // step 1 — common
        executeTest();    // step 2 — subclass provides this
        tearDown();       // step 3 — common
    }

    // Common steps — implemented here
    protected void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    protected void tearDown() {
        if (driver != null) driver.quit();
    }

    // Abstract — each test class implements this
    protected abstract void executeTest();
}

// Subclass fills in only the test logic
public class LoginTest extends BaseTest {
    @Override
    protected void executeTest() {
        driver.get("https://saucedemo.com");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }
}
```

---

### 15. Iterator

**Intent:** Provide a way to sequentially access elements without exposing underlying representation.

> Java's `Iterable` / `Iterator` interfaces are the built-in implementation.

```java
// Custom Iterator for test data
public class TestDataIterator implements Iterator<Map<String, String>> {
    private final List<Map<String, String>> data;
    private int cursor = 0;

    public TestDataIterator(List<Map<String, String>> data) { this.data = data; }

    @Override public boolean hasNext() { return cursor < data.size(); }
    @Override public Map<String, String> next() { return data.get(cursor++); }
}

// Usage
TestDataIterator it = new TestDataIterator(csvData);
while (it.hasNext()) {
    Map<String, String> row = it.next();
    loginPage.loginWithUserInfo(row.get("username"), row.get("password"));
}
```

---

### 16. Chain of Responsibility

**Intent:** Pass a request along a chain of handlers, each deciding to handle or pass on.

```java
// Handler interface
public abstract class ValidationHandler {
    protected ValidationHandler next;

    public ValidationHandler setNext(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public abstract boolean validate(String username, String password);
}

// Handlers
public class NullCheckHandler extends ValidationHandler {
    @Override
    public boolean validate(String username, String password) {
        if (username == null || password == null) {
            System.out.println("FAIL: null credentials");
            return false;
        }
        return next == null || next.validate(username, password);
    }
}

public class LengthHandler extends ValidationHandler {
    @Override
    public boolean validate(String username, String password) {
        if (username.length() < 3 || password.length() < 6) {
            System.out.println("FAIL: too short");
            return false;
        }
        return next == null || next.validate(username, password);
    }
}

// Build chain
ValidationHandler nullCheck = new NullCheckHandler();
ValidationHandler lengthCheck = new LengthHandler();
nullCheck.setNext(lengthCheck);

nullCheck.validate("admin", "secret_sauce");  // passes all checks
nullCheck.validate(null, "pass");             // fails at null check
```

---

## Interview Q&A

**Q: What are the three types of design patterns?**
> **Creational**: How objects are created (Singleton, Factory, Builder, Prototype, Abstract Factory)
> **Structural**: How objects are composed (Adapter, Decorator, Proxy, Facade, Composite, Bridge, Flyweight)
> **Behavioral**: How objects communicate (Strategy, Observer, Command, Template Method, Iterator, Chain of Responsibility, State, Visitor, Mediator, Memento)

**Q: Singleton vs Static class — when to use each?**
> Use Singleton when you need: a single instance with state, lazy initialization, ability to implement interfaces/extend classes, or polymorphism.
> Use Static class for stateless utility methods (e.g., `Math`, `Arrays`, `Collections`). Static classes can't be passed as objects or used polymorphically.

**Q: What is the difference between Factory Method and Abstract Factory?**
> Factory Method creates ONE product type but lets subclasses choose the concrete class.
> Abstract Factory creates FAMILIES of related products — it's a collection of factory methods that produce related objects.

**Q: Decorator vs Inheritance — when to choose Decorator?**
> Use Decorator when: behaviours need to be combined in many combinations (2^n combinations with inheritance), behaviour needs to be added/removed at runtime, you can't modify the original class (closed for modification).
> Inheritance creates a new class. Decorator wraps an existing object at runtime.

**Q: What design patterns are used in Selenium/TestNG framework?**
> - **Factory**: `BrowserDriverFactory.createDriver()` → returns `WebDriver`
> - **Page Object Model**: Facade + Encapsulation per page
> - **Singleton**: Config manager, Report manager
> - **Builder**: Driver options config
> - **Observer**: TestNG `ITestListener` / Extent report listeners
> - **Template Method**: `BaseTest.setUp()` / `tearDown()` lifecycle
> - **Strategy**: Different wait strategies, different browser configs
> - **Decorator**: Adding logging/screenshot to page actions
> - **Proxy**: `@FindBy` / `PageFactory` uses Java dynamic proxy
> - **Iterator**: DataProvider iterating test data rows

**Q: What is SOLID? How do patterns relate to it?**
> **S** — Single Responsibility: each class has one reason to change (Command, Strategy)
> **O** — Open/Closed: open for extension, closed for modification (Decorator, Observer)
> **L** — Liskov Substitution: subclasses can replace parent (Template Method, Factory)
> **I** — Interface Segregation: small, focused interfaces (Strategy, Command)
> **D** — Dependency Inversion: depend on abstractions, not concretions (Factory, Abstract Factory)
