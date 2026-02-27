# TestNG & JUnit — Complete Reference Guide

---

## Table of Contents
1. [TestNG Overview & Setup](#1-testng-overview--setup)
2. [TestNG Annotations — Lifecycle](#2-testng-annotations--lifecycle)
3. [Assertions](#3-assertions)
4. [DataProvider](#4-dataprovider)
5. [Groups & Dependencies](#5-groups--dependencies)
6. [Listeners](#6-listeners)
7. [Parallel Execution](#7-parallel-execution)
8. [testng.xml Configuration](#8-testngxml-configuration)
9. [Retry Mechanism](#9-retry-mechanism)
10. [JUnit 5 (Jupiter)](#10-junit-5-jupiter)
11. [TestNG vs JUnit 5 Comparison](#11-testng-vs-junit-5-comparison)
12. [Interview Q&A](#12-interview-qa)

---

## 1. TestNG Overview & Setup

TestNG (Test Next Generation) is a testing framework inspired by JUnit with advanced features like grouping, data-driven testing, parallel execution, and flexible configuration.

### Maven Dependency

```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
    <scope>test</scope>
</dependency>
```

---

## 2. TestNG Annotations — Lifecycle

### Complete Lifecycle Order

```
@BeforeSuite       → Once before all tests in the suite (testng.xml)
  @BeforeTest      → Once before all <test> tags in testng.xml
    @BeforeClass   → Once before all @Test methods in a class
      @BeforeMethod → Before EACH @Test method
        @Test          ← test method runs here
      @AfterMethod  → After EACH @Test method
    @AfterClass    → Once after all @Test methods in a class
  @AfterTest       → Once after all <test> tags
@AfterSuite        → Once after all tests in the suite
```

### All Annotations

```java
@BeforeSuite
public void globalSetup(ITestContext ctx) {
    // Runs once per suite — initialize Extent Reports, DB connection
    log.info("Suite started: " + ctx.getSuite().getName());
}

@BeforeTest
public void testSetup(ITestContext ctx) {
    // Runs once per <test> tag in testng.xml
    log.info("Test set: " + ctx.getName());
}

@BeforeClass
public void classSetup() {
    // Runs once before first @Test in this class
    // Good for heavy setup reused by all methods in class
}

@BeforeMethod(alwaysRun = true)
public void setUp(Method method, ITestContext ctx) {
    // From BaseTest — driver creation per test method
    log.info("Starting test: " + method.getName());
    driver = factory.createDriver();
}

@Test(
    groups      = {"smoke", "regression"},
    description = "Verify successful login",
    priority    = 1,
    enabled     = true,
    timeOut     = 5000,              // fail if test exceeds 5 seconds
    retryAnalyzer = RetryAnalyzer.class,
    dependsOnMethods = "loginTest",
    dataProvider = "loginData",
    dataProviderClass = DataProviders.class,
    expectedExceptions = { IllegalArgumentException.class }  // test passes if this exception thrown
)
public void loginTest() { }

@AfterMethod(alwaysRun = true)
public void tearDown(ITestResult result) {
    // alwaysRun = true ensures tearDown runs even if @BeforeMethod fails
    if (result.getStatus() == ITestResult.FAILURE) {
        takeScreenshot(result.getName());
    }
    driver.quit();
}

@AfterClass
public void classTeardown() { }

@AfterTest
public void testTeardown() { }

@AfterSuite
public void globalTeardown() {
    // flush Extent Reports
    extent.flush();
}
```

### `@Test` Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| `groups` | String[] | Assign to groups for selective execution |
| `description` | String | Human-readable description |
| `priority` | int | Lower = runs first (default 0) |
| `enabled` | boolean | `false` = skip this test |
| `timeOut` | long | Fail if test takes longer (milliseconds) |
| `invocationCount` | int | Run this test N times |
| `threadPoolSize` | int | Run with N threads (with invocationCount) |
| `dependsOnMethods` | String[] | Run after specified methods |
| `dependsOnGroups` | String[] | Run after specified groups complete |
| `dataProvider` | String | Data provider method name |
| `dataProviderClass` | Class | Class containing the data provider |
| `retryAnalyzer` | Class | Retry logic class |
| `expectedExceptions` | Class[] | Test passes if exception thrown |
| `alwaysRun` | boolean | For Before/After — run even if dependency fails |

---

## 3. Assertions

### TestNG Hard Assertions

```java
import org.testng.Assert;

// Equality
Assert.assertEquals(actual, expected);
Assert.assertEquals(actual, expected, "failure message");
Assert.assertNotEquals(actual, unexpected);

// Boolean
Assert.assertTrue(condition);
Assert.assertFalse(condition);
Assert.assertTrue(condition, "message shown on failure");

// Null checks
Assert.assertNull(object);
Assert.assertNotNull(object);
Assert.assertNotNull(object, "Object should not be null");

// Reference equality
Assert.assertSame(obj1, obj2);       // same object reference
Assert.assertNotSame(obj1, obj2);

// Arrays
Assert.assertEquals(actualArray, expectedArray);

// Fail explicitly
Assert.fail("Forced failure");
Assert.fail("Test should not reach here");

// Example in automation
Assert.assertEquals(loginPage.getErrorMsg(), "Epic sadface: Username and password do not match any user in this service");
Assert.assertTrue(productsPage.isPageLoaded(), "Products page should be loaded after login");
Assert.assertNotNull(driver, "WebDriver should not be null after setUp");
```

### Soft Assertions — Collect All Failures

```java
import org.testng.asserts.SoftAssert;

@Test
public void verifyProductPage() {
    SoftAssert softAssert = new SoftAssert();

    // All assertions execute regardless of failures
    softAssert.assertEquals(productPage.getTitle(), "Products", "Page title mismatch");
    softAssert.assertTrue(productPage.isAddToCartVisible(), "Add to cart button not visible");
    softAssert.assertEquals(productPage.getItemCount(), 6, "Item count mismatch");
    softAssert.assertFalse(productPage.isCartEmpty(), "Cart should be empty");

    softAssert.assertAll();  // report ALL failures together
    // Without assertAll(), failures are silently swallowed!
}
```

---

## 4. DataProvider

```java
// Basic DataProvider
@DataProvider(name = "loginCredentials")
public Object[][] loginCredentials() {
    return new Object[][] {
        {"standard_user",  "secret_sauce",  true},   // valid login
        {"locked_out_user", "secret_sauce", false},  // locked account
        {"invalid_user",    "wrong_pass",   false},  // wrong credentials
        {"",               "secret_sauce",  false},  // empty username
        {"standard_user",  "",             false}    // empty password
    };
}

@Test(dataProvider = "loginCredentials")
public void loginTest(String username, String password, boolean shouldSucceed) {
    loginPage.loginWithUserInfo(username, password);
    if (shouldSucceed) {
        Assert.assertTrue(productsPage.isPageLoaded());
    } else {
        Assert.assertTrue(loginPage.isErrorDisplayed());
    }
}

// DataProvider in a separate class
@DataProvider(name = "csvFileReader", parallel = true)
public static Iterator<Object[]> csvData(Method method) {
    // read CSV file using method name to find the right data file
    List<Object[]> data = new ArrayList<>();
    // ... read from CSV
    return data.iterator();
}

@Test(dataProvider = "csvFileReader", dataProviderClass = CsvDataProviders.class)
public void _01_Validate_Incorrect_Login_Attempt(Map<String, String> testData) {
    String username    = testData.get("username");
    String password    = testData.get("password");
    String expectedMsg = testData.get("expectedResult");
    loginPage.loginWithUserInfo(username, password);
    Assert.assertEquals(loginPage.getErrorMsg(), expectedMsg);
}

// Lazy DataProvider using Iterator (memory efficient for large data)
@DataProvider(name = "largeDataSet")
public Iterator<Object[]> largeData() {
    return new Iterator<Object[]>() {
        int count = 0;
        @Override public boolean hasNext() { return count < 10000; }
        @Override public Object[] next()   { return new Object[]{"user" + count++}; }
    };
}
```

---

## 5. Groups & Dependencies

### Groups

```java
// Assign tests to groups
@Test(groups = "smoke")
public void loginTest() { }

@Test(groups = {"smoke", "regression"})
public void productPageTest() { }

@Test(groups = "regression")
public void checkoutFlowTest() { }

@Test(groups = "sanity")
public void sanityTest() { }
```

Run specific groups in `testng.xml`:
```xml
<groups>
    <run>
        <include name="smoke"/>
        <exclude name="sanity"/>
    </run>
</groups>
```

### Method Dependencies

```java
@Test
public void loginTest() { /* login */ }

@Test(dependsOnMethods = "loginTest")
public void addToCartTest() {
    // runs AFTER loginTest
    // SKIPPED (not failed) if loginTest fails
}

@Test(dependsOnMethods = "addToCartTest", alwaysRun = false)
public void checkoutTest() {
    // runs AFTER addToCartTest
}
```

### Group Dependencies

```java
@Test(groups = "checkout", dependsOnGroups = "login")
public void checkoutTest() {
    // all tests in "login" group must pass first
}
```

---

## 6. Listeners

### ITestListener

```java
public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter reporter = new ExtentSparkReporter("Reports/report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(),
                                            result.getMethod().getDescription());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, result.getThrowable());

        // Screenshot
        Object instance = result.getInstance();
        WebDriver driver = ((BaseTest) instance).driver;
        String screenshotPath = takeScreenshot(driver, result.getName());
        extentTest.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test SKIPPED: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
```

### ISuiteListener

```java
public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        System.out.println("Suite started: " + suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Suite finished. Total tests: " +
            suite.getResults().values().stream()
                 .mapToInt(r -> r.getTestContext().getAllTestMethods().length).sum());
    }
}
```

### Registering Listeners

```java
// Method 1: Annotation on class
@Listeners({TestListener.class, SuiteListener.class})
public class LoginTest extends BaseTest { }

// Method 2: testng.xml (affects all tests in suite)
<listeners>
    <listener class-name="com.swag.labs.BaseComponents.TestListener"/>
    <listener class-name="com.swag.labs.BaseComponents.RetryTestListener"/>
</listeners>
```

---

## 7. Parallel Execution

```xml
<!-- testng.xml parallel modes -->

<!-- Parallel by methods — most common, each test method in own thread -->
<suite name="Suite" parallel="methods" thread-count="4">

<!-- Parallel by classes — each class in own thread -->
<suite name="Suite" parallel="classes" thread-count="3">

<!-- Parallel by tests — each <test> tag in own thread -->
<suite name="Suite" parallel="tests" thread-count="2">

<!-- Parallel by instances — each class instance in own thread -->
<suite name="Suite" parallel="instances" thread-count="4">
```

### Thread-Safe Driver with ThreadLocal

```java
// In BaseTest — each test gets its own driver
public class BaseTest {
    // NOT static — instance field, but @BeforeMethod creates new driver per test
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
        driver = factory.createDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}

// For sharing driver across classes — use ThreadLocal
public class DriverManager {
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) { TL_DRIVER.set(driver); }
    public static WebDriver getDriver()            { return TL_DRIVER.get(); }
    public static void removeDriver()              { TL_DRIVER.remove(); }
}
```

---

## 8. testng.xml Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">

<suite name="Regression Suite"
       parallel="methods"
       thread-count="4"
       verbose="1">

    <!-- Listeners apply to entire suite -->
    <listeners>
        <listener class-name="com.swag.labs.BaseComponents.TestListener"/>
        <listener class-name="com.swag.labs.BaseComponents.RetryTestListener"/>
    </listeners>

    <!-- Parameters accessible via @Parameters or ITestContext -->
    <parameter name="browser" value="chrome"/>
    <parameter name="baseUrl" value="https://www.saucedemo.com"/>

    <!-- Group definitions -->
    <groups>
        <define name="smoke">
            <include name="login"/>
            <include name="navigation"/>
        </define>
        <run>
            <include name="smoke"/>
            <include name="regression"/>
            <exclude name="wip"/>
        </run>
    </groups>

    <!-- Test 1: Smoke Tests -->
    <test name="Smoke Tests" preserve-order="true">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="com.swag.labs.Tests._01_ApplicationBehaviorForDifferentLoginAttempt">
                <methods>
                    <include name="_01_Validate_Incorrect_Login_Attempt"/>
                    <exclude name="_02_.*"/>  <!-- exclude by regex -->
                </methods>
            </class>
        </classes>
    </test>

    <!-- Test 2: Full Regression -->
    <test name="Full Regression">
        <packages>
            <package name="com.swag.labs.Tests"/>  <!-- all tests in package -->
        </packages>
    </test>

</suite>
```

### @Parameters

```java
@Parameters({"browser", "baseUrl"})
@BeforeMethod
public void setUp(String browser, String baseUrl) {
    driver = new BrowserDriverFactory(browser, log).createDriver();
    driver.get(baseUrl);
}
```

---

## 9. Retry Mechanism

```java
// RetryAnalyzer — implements IRetryAnalyzer
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRIES = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRIES) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + " (attempt " + retryCount + ")");
            return true;   // retry
        }
        return false;      // don't retry, mark as failed
    }
}

// Apply to test
@Test(retryAnalyzer = RetryAnalyzer.class)
public void flakyTest() { }

// Apply globally via Listener
public class RetryTestListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);  // applies to ALL tests
    }
}
```

---

## 10. JUnit 5 (Jupiter)

### Maven Dependencies

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

### Lifecycle Annotations

```java
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

class LoginTest {

    @BeforeAll
    static void globalSetup() {
        // once before all tests in class (must be static unless @TestInstance(PER_CLASS))
    }

    @BeforeEach
    void setUp() {
        // before each test
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Valid login should navigate to products")
    @Tag("smoke")
    void validLogin() {
        // test body
    }

    @Test
    @Disabled("Bug #123 — skip until fixed")
    void disabledTest() { }

    @Test
    @Timeout(5)   // fail if test runs > 5 seconds
    void timedTest() { }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @AfterAll
    static void globalTeardown() { }
}
```

### JUnit 5 Assertions

```java
import static org.junit.jupiter.api.Assertions.*;

assertEquals("Products", pageTitle);
assertNotEquals("Login", pageTitle);
assertTrue(isLoaded, "Page should be loaded");
assertFalse(isErrorVisible);
assertNull(driver);
assertNotNull(driver, "Driver should be initialized");
assertThrows(IllegalArgumentException.class, () -> new BrowserFactory(null));
assertDoesNotThrow(() -> driver.get("https://saucedemo.com"));
assertAll("Login validation",
    () -> assertEquals("Products", pageTitle),
    () -> assertTrue(addToCart.isDisplayed()),
    () -> assertEquals(6, itemCount)
);
```

### JUnit 5 Parameterized Tests

```java
@ParameterizedTest
@ValueSource(strings = {"chrome", "firefox", "edge"})
void testWithBrowser(String browser) { }

@ParameterizedTest
@CsvSource({
    "standard_user, secret_sauce, true",
    "locked_out_user, secret_sauce, false"
})
void loginTest(String user, String pass, boolean expected) { }

@ParameterizedTest
@CsvFileSource(resources = "/test-data/login-data.csv", numLinesToSkip = 1)
void loginTestFromCsv(String user, String pass, boolean expected) { }

@ParameterizedTest
@MethodSource("provideBrowsers")
void testWithBrowserProvider(String browser) { }

static Stream<String> provideBrowsers() {
    return Stream.of("chrome", "firefox", "edge");
}

@ParameterizedTest
@EnumSource(BrowserType.class)
void testWithEnum(BrowserType browser) { }
```

---

## 11. TestNG vs JUnit 5 Comparison

| Feature | TestNG | JUnit 5 |
|---------|--------|---------|
| Lifecycle annotations | `@BeforeSuite/Test/Class/Method` | `@BeforeAll/@BeforeEach` |
| Test annotation | `@Test` | `@Test` (different package) |
| Groups | `@Test(groups = "smoke")` | `@Tag("smoke")` |
| Data-driven | `@DataProvider` | `@ParameterizedTest` + providers |
| Dependencies | `dependsOnMethods/Groups` | No direct equivalent |
| Parallel | `testng.xml parallel` | `@Execution(CONCURRENT)` |
| Assertions | `Assert.*` | `Assertions.*` (support `assertAll`) |
| Listeners | `ITestListener` | `Extension` API |
| Config file | `testng.xml` | `junit-platform.properties` |
| Soft assert | `SoftAssert` | `assertAll()` built-in |
| Skip test | `enabled = false` | `@Disabled` |
| Timeout | `timeOut = 5000` | `@Timeout(5)` |
| Retry | `IRetryAnalyzer` | 3rd party extension |
| Industry use | Selenium/API testing | Spring Boot, unit testing |

---

## 12. Interview Q&A

**Q: What is the difference between `@BeforeMethod` and `@BeforeClass`?**
> `@BeforeMethod`: runs before **each** test method — perfect for driver creation/teardown (reset state per test).
> `@BeforeClass`: runs once before **all** methods in the class — for expensive setup reused by all tests (DB connection, test data setup). Methods must be static unless `@TestInstance(PER_CLASS)` in JUnit 5.

**Q: What is `alwaysRun = true`?**
> Makes the annotated method run even if the test it's associated with (or its dependency) fails or throws an exception. Critical for `@AfterMethod` teardown — you always want to close the browser even if the test fails.

**Q: How does `@DataProvider` work? What does it return?**
> A `@DataProvider` method returns `Object[][]` (or `Iterator<Object[]>`). Each inner `Object[]` is one set of parameters for one test run. TestNG calls the `@Test` method once for each inner array.

**Q: What is `ITestResult`? What statuses does it have?**
> `ITestResult` encapsulates the result of a single test run. Statuses: `SUCCESS (1)`, `FAILURE (2)`, `SKIP (3)`, `SUCCESS_PERCENTAGE_FAILURE (4)`, `STARTED (16)`. Accessed in `@AfterMethod` to check if the test passed or failed.

**Q: How do you run only smoke tests from the command line?**
```bash
mvn test -Dgroups="smoke"                         # Maven Surefire
mvn test -DtestSuiteFile=smoke-testng.xml         # specific testng.xml
```

**Q: What is the purpose of `IAnnotationTransformer`?**
> Allows programmatic modification of test annotations at runtime. Most commonly used to globally apply `retryAnalyzer` to all tests without adding `retryAnalyzer = ...` to every `@Test`. Register as a listener in `testng.xml`.

**Q: What is `SoftAssert`? When would you use it over hard assert?**
> `SoftAssert` collects all assertion failures and reports them together at `assertAll()`. Use it when you want to verify multiple independent things in one test and see ALL failures, not just the first one. Hard assert stops the test at first failure — good when subsequent steps depend on the assertion being true.

**Q: How do you handle test order in TestNG?**
> By default, TestNG doesn't guarantee order. Options:
> 1. `@Test(priority = n)` — lower number runs first
> 2. `preserve-order="true"` in testng.xml + list methods in order
> 3. `dependsOnMethods` — implicit ordering via dependencies
> Best practice: tests should be independent and order shouldn't matter for unit/integration tests.
