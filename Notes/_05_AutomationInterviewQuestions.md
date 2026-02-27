# Automation Testing — Interview Questions & Answers

---

## Table of Contents
1. [Selenium WebDriver Fundamentals](#1-selenium-webdriver-fundamentals)
2. [Locators & WebElements](#2-locators--webelements)
3. [Waits — Implicit, Explicit, Fluent](#3-waits--implicit-explicit-fluent)
4. [Page Object Model (POM)](#4-page-object-model-pom)
5. [TestNG](#5-testng)
6. [Framework Design](#6-framework-design)
7. [Advanced Selenium](#7-advanced-selenium)
8. [CI/CD & Reporting](#8-cicd--reporting)
9. [API Testing (REST Assured Concepts)](#9-api-testing-rest-assured-concepts)
10. [QA Processes & Mindset](#10-qa-processes--mindset)

---

## 1. Selenium WebDriver Fundamentals

**Q: What is Selenium WebDriver? How is it different from Selenium RC?**
> Selenium WebDriver is a browser automation library that communicates **directly** with the browser using the browser's native support (WebDriver protocol / W3C standard). Selenium RC used a server-side proxy and JavaScript injection to control the browser, which was slow and prone to JavaScript security restrictions. WebDriver is faster, more reliable, and supports modern browsers.

**Q: What is the WebDriver interface in Selenium?**
> `org.openqa.selenium.WebDriver` is a Java interface that defines the contract all browser drivers must implement:
> `get()`, `getTitle()`, `getCurrentUrl()`, `findElement()`, `findElements()`, `close()`, `quit()`, `getWindowHandles()`, `switchTo()`, `navigate()`, `manage()`.
> Concrete implementations: `ChromeDriver`, `FirefoxDriver`, `EdgeDriver`, `RemoteWebDriver`.

**Q: What is the difference between `driver.close()` and `driver.quit()`?**
> `close()` — closes the **current window** (the one in focus). If it's the only window, the session ends.
> `quit()` — closes **all browser windows** opened by the WebDriver session and terminates the driver process cleanly. Always call `quit()` in `tearDown()` to prevent browser process leaks.

**Q: How do you launch different browsers in Selenium?**
```java
WebDriver driver = new ChromeDriver();       // Chrome
WebDriver driver = new FirefoxDriver();      // Firefox
WebDriver driver = new EdgeDriver();         // Edge
// With options:
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriver driver = new ChromeDriver(options);
```
> In the framework (`BrowserDriverFactory`), the browser type is read from config/environment variables and the appropriate driver is created via a `switch` statement — **Factory Pattern**.

**Q: What is `RemoteWebDriver` and when do you use it?**
> `RemoteWebDriver` allows running tests on a remote machine or in the cloud (Selenium Grid, BrowserStack, Sauce Labs). Instead of creating `ChromeDriver` locally, you connect to a Grid hub:
```java
URL gridUrl = new URL("http://grid-host:4444/wd/hub");
DesiredCapabilities caps = new DesiredCapabilities();
caps.setBrowserName("chrome");
WebDriver driver = new RemoteWebDriver(gridUrl, caps);
```

**Q: How does WebDriver communicate with the browser?**
> WebDriver uses the **W3C WebDriver Protocol** (HTTP REST API). When you call `driver.get("url")`, it sends an HTTP POST request to the browser's native driver (ChromeDriver, GeckoDriver). The browser driver receives it, instructs the browser, and returns the result as JSON. This is why you need `chromedriver.exe` in the PATH — it's the local server.

---

## 2. Locators & WebElements

**Q: What locator strategies are available in Selenium?**
> 8 locator strategies:
> `By.id`, `By.name`, `By.className`, `By.tagName`, `By.linkText`, `By.partialLinkText`, `By.cssSelector`, `By.xpath`
> **Priority**: `id` > `name` > `cssSelector` > `xpath` (fastest to slowest, most stable to least stable)

**Q: What is the difference between XPath and CSS Selector? Which is faster?**
> **CSS Selector** is generally faster — browsers natively parse CSS for rendering, so CSS engines are heavily optimized.
> **XPath** is more powerful — can navigate parent elements (CSS can't go up), supports `text()`, `contains()`, `starts-with()`, `following-sibling`, etc.
> Use CSS for simple locators. Use XPath when you need text-based matching or parent traversal.

**Q: Write a CSS selector for:**
```css
/* By ID */              #user-name
/* By class */           .btn_action
/* By attribute */       input[type='submit']
/* By partial attribute */ [data-test*='error']
/* Parent > child */     .login-wrapper > form > input
/* nth child */          ul li:nth-child(2)
/* Multiple classes */   .btn.btn-primary
```

**Q: Write an XPath for:**
```xpath
// By ID:          //*[@id='user-name']
// By text:        //button[text()='Login']
// Contains text:  //div[contains(text(),'Error')]
// Contains attr:  //input[contains(@class,'btn')]
// Starts-with:    //div[starts-with(@id,'product-')]
// Ancestor:       //input[@id='user-name']/ancestor::form
// Following sibling: //label[text()='Username']/following-sibling::input
// Nth child:      //ul/li[2]
// Last:           //ul/li[last()]
// Position:       //ul/li[position()>2]
```

**Q: What is `@FindBy` and `PageFactory`?**
> `@FindBy` is a TestNG/JUnit annotation from `org.openqa.selenium.support` that lazily initializes `WebElement` fields. `PageFactory.initElements(driver, this)` processes the annotations and creates proxies. The actual `findElement()` call happens when you first **use** the element (lazy loading).
```java
@FindBy(css = "#user-name")
private WebElement txt_username;   // not found yet
// when you call txt_username.sendKeys() → findElement is called then
```

**Q: What is `StaleElementReferenceException`? How do you handle it?**
> Thrown when a WebElement reference becomes stale — the DOM has changed (page refresh, AJAX update, navigation) but your code holds an old reference. Solutions:
> 1. Re-find the element before using it
> 2. Use `WebDriverWait` with retry (catch `StaleElementReferenceException`)
> 3. Use `@FindBy` with `PageFactory` — it re-finds on each use

```java
// From ElementUtils.java in the project — retry on stale
int attempts = 0;
while (attempts < 2) {
    try {
        element = we;
        break;
    } catch (StaleElementReferenceException e) {
        e.printStackTrace();
    }
    attempts++;
}
```

**Q: What is the difference between `findElement()` and `findElements()`?**
> `findElement()` returns a single `WebElement` — throws `NoSuchElementException` if not found.
> `findElements()` returns `List<WebElement>` — returns **empty list** (not exception) if not found. Useful for checking element existence: `driver.findElements(By.id("x")).isEmpty()`.

---

## 3. Waits — Implicit, Explicit, Fluent

**Q: What are the three types of waits in Selenium?**

**1. Implicit Wait** — global setting, waits for ALL elements for a set duration:
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```
> Drawback: applies to every `findElement()`, even when element is immediately available. Cannot be mixed with Explicit Wait (unpredictable behaviour).

**2. Explicit Wait** — waits for a **specific condition** on a **specific element**:
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-btn")));
```
> Polls every 500ms by default. Preferred over implicit wait. Used throughout `BasePage` in the automation project.

**3. Fluent Wait** — Explicit wait with custom **polling interval** and **ignored exceptions**:
```java
Wait<WebDriver> wait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(30))
    .pollingEvery(Duration.ofMillis(500))
    .ignoring(NoSuchElementException.class);
WebElement element = wait.until(driver -> driver.findElement(By.id("result")));
```
> Most powerful wait. Use for elements that appear at irregular intervals.

**Q: What is `ExpectedConditions`?**
> A utility class with pre-built conditions for `WebDriverWait.until()`:
> `visibilityOf`, `elementToBeClickable`, `presenceOfElementLocated`, `invisibilityOf`, `textToBePresentInElement`, `alertIsPresent`, `numberOfWindowsToBe`, `titleContains`, `urlContains`, `frameToBeAvailableAndSwitchToIt`.

**Q: Why should you avoid mixing implicit and explicit waits?**
> They interact in undefined ways. If implicit wait is set to 10s and explicit wait is set to 5s, the driver might wait up to 10s (implicit) even inside the explicit wait. Always use one or the other — the automation project uses only explicit waits (via `BasePage`).

---

## 4. Page Object Model (POM)

**Q: What is the Page Object Model? Why use it?**
> POM is a design pattern where each web page (or significant component) has a corresponding Java class. The class:
> - Stores all **locators** as private fields
> - Exposes **methods** representing user actions
> - Returns the **next page object** after navigation
>
> Benefits: separation of concerns, readability, maintainability. If a locator changes, update only the page class — zero test changes.

**Q: What does `PageFactory.initElements(driver, this)` do?**
> It scans the class for `@FindBy`-annotated fields and sets up lazy-initialized proxies. The element is only located in the DOM when it's actually accessed (first call to `.click()`, `.sendKeys()`, etc.). The `this` refers to the current page object instance.

**Q: What is the difference between POM and Page Factory?**
> **POM** is the design **pattern** (class per page, methods as actions).
> **PageFactory** is a Selenium **utility** that implements POM using `@FindBy` annotations and lazy initialization. You can implement POM without PageFactory (using `driver.findElement()` directly), but PageFactory reduces boilerplate.

**Q: What is a fluent/method-chaining POM?**
> Each page method returns the next page object (or `this` if staying on the same page), enabling chain calls:
```java
loginPage.login()                    // returns ProductsPage
        .addFirstItemToCart()        // returns ProductsPage
        .goToCart()                  // returns CartPage
        .checkout()                  // returns CheckoutPage
        .fillDetails("John","Doe")   // returns CheckoutPage
        .finish();                   // returns OrderConfirmationPage
```

**Q: How do you handle dynamic elements in POM?**
> Use XPath with `contains()` or `starts-with()`, or pass locator parameters:
```java
public WebElement getProductByName(String productName) {
    return driver.findElement(
        By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']"));
}
```

---

## 5. TestNG

**Q: What is the TestNG execution order for annotations?**
```
@BeforeSuite  → @BeforeTest  → @BeforeClass  → @BeforeMethod
                                                      ↓
                                               @Test (method)
                                                      ↓
@AfterSuite  ← @AfterTest   ← @AfterClass   ← @AfterMethod
```

**Q: What is `@DataProvider`? How does it work?**
> Supplies multiple data sets to a `@Test` method, running it once per data set:
```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][] {
        {"standard_user", "secret_sauce", true},
        {"locked_out_user", "secret_sauce", false},
        {"invalid_user", "wrong_pass", false}
    };
}

@Test(dataProvider = "loginData")
public void loginTest(String user, String pass, boolean shouldPass) { ... }
```

**Q: What is the difference between `@BeforeMethod` and `@BeforeClass`?**
> `@BeforeMethod` runs before **each test method** — typically used for driver setup/teardown in the automation project (`BaseTest.setUp()`).
> `@BeforeClass` runs once before **all methods in the class** — typically used for heavier setup (DB connection, test data preparation).

**Q: What is a TestNG Listener? How do you create one?**
> A listener implements `ITestListener`, `ISuiteListener`, or `IInvokedMethodListener` and receives callbacks for test events:
```java
public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        // take screenshot, log failure, update report
    }
    @Override
    public void onTestSuccess(ITestResult result) { /* ... */ }
    @Override
    public void onTestStart(ITestResult result) { /* ... */ }
}
```
> Register in `testng.xml` or `@Listeners(TestListener.class)` on the class.

**Q: How do you run tests in parallel in TestNG?**
```xml
<suite name="Parallel Suite" parallel="methods" thread-count="4">
  <test name="Login Tests">
    <classes>
      <class name="com.tests.LoginTest"/>
    </classes>
  </test>
</suite>
```
> `parallel="methods"` — each test method in its own thread.
> `parallel="classes"` — each class in its own thread.
> `parallel="tests"` — each `<test>` tag in its own thread.
> Use `ThreadLocal<WebDriver>` to keep driver instances thread-safe.

**Q: What is `@Test(dependsOnMethods = "...")`?**
> Makes a test method depend on another. If the dependency fails, the dependent test is skipped:
```java
@Test
public void loginTest() { ... }

@Test(dependsOnMethods = "loginTest")
public void addToCartTest() { ... }  // skipped if loginTest fails
```
> Avoid this in unit tests — tests should be independent. Acceptable in end-to-end flows.

**Q: What are TestNG Groups?**
```java
@Test(groups = {"smoke", "regression"})
public void loginTest() { ... }

@Test(groups = {"regression"})
public void fullFlowTest() { ... }
```
> Run only specific groups: in `testng.xml` with `<groups><run><include name="smoke"/></run></groups>`.

---

## 6. Framework Design

**Q: What is the structure of your automation framework?**
> The `UI-Automation_Selenium` project follows a layered architecture:
>
> ```
> Tests (test logic, assertions)
>    ↓ extends
> BaseTest (driver setup, teardown, common fields)
>    ↓ uses
> Page Objects (page-specific actions, locators)
>    ↓ extends
> ElementUtils (advanced interactions: JS, Actions, Alerts, iframes)
>    ↓ extends
> BasePage (waits, driver, log, common utilities)
>    ↓ uses
> BrowserDriverFactory (creates WebDriver based on config)
> ConfigurationUtils (loads properties file)
> ```

**Q: How do you handle test data in your framework?**
> Multiple approaches in the project:
> 1. **Properties file** (`config.properties`) — URL, browser, credentials
> 2. **CSV file** — data-driven tests via `CsvDataProviders` + `@DataProvider`
> 3. **JSON file** — structured test data
> 4. **Hardcoded in DataProvider** — for simple, stable test data

**Q: How do you take screenshots on test failure?**
```java
// In TestListener.onTestFailure()
public void onTestFailure(ITestResult result) {
    Object testInstance = result.getInstance();
    WebDriver driver = ((BaseTest) testInstance).driver;
    TakesScreenshot ts = (TakesScreenshot) driver;
    File src = ts.getScreenshotAs(OutputType.FILE);
    String dest = "ScreenShot/" + result.getName() + "_" + timestamp + ".png";
    FileUtils.copyFile(src, new File(dest));
    // Attach to Extent Report
}
```

**Q: How do you make your framework thread-safe for parallel execution?**
> Use `ThreadLocal<WebDriver>`:
```java
// In BrowserDriverFactory
private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
driver.set(new ChromeDriver());    // sets for current thread
driver.get();                       // gets current thread's driver
driver.remove();                    // cleans up after test
```

**Q: What is the difference between hard and soft assertions?**
> **Hard assertion** (TestNG `Assert.*`): fails the test immediately on first failure. Remaining assertions are not executed.
> **Soft assertion** (`SoftAssert`): collects all failures and reports them all at once at `assertAll()`:
```java
SoftAssert softAssert = new SoftAssert();
softAssert.assertEquals(title, "Products");
softAssert.assertTrue(addToCartBtn.isDisplayed());
softAssert.assertEquals(itemCount, 3);
softAssert.assertAll();  // reports all failures together
```

---

## 7. Advanced Selenium

**Q: How do you handle `Alert` in Selenium?**
```java
// Simple alert
Alert alert = driver.switchTo().alert();
alert.getText();    // get alert message
alert.accept();     // click OK
alert.dismiss();    // click Cancel
alert.sendKeys("text");  // for prompt dialog
```

**Q: How do you handle multiple windows/tabs?**
```java
String parentWindow = driver.getWindowHandle();
Set<String> allWindows = driver.getWindowHandles();
for (String handle : allWindows) {
    if (!handle.equals(parentWindow)) {
        driver.switchTo().window(handle);
        // work on new window
        driver.close();
    }
}
driver.switchTo().window(parentWindow);  // back to parent
```

**Q: How do you handle iframes?**
```java
// Switch to iframe by index / id / name / WebElement
driver.switchTo().frame(0);
driver.switchTo().frame("frameName");
driver.switchTo().frame(driver.findElement(By.id("frameId")));
// Switch back to main document
driver.switchTo().defaultContent();
// Switch to parent frame
driver.switchTo().parentFrame();
```

**Q: When and how do you use `JavascriptExecutor`?**
> When standard Selenium interactions fail or are not supported:
```java
JavascriptExecutor js = (JavascriptExecutor) driver;

// Click when element is not interactable
js.executeScript("arguments[0].click()", element);

// Scroll to element
js.executeScript("arguments[0].scrollIntoView(true)", element);

// Scroll to bottom
js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

// Set value
js.executeScript("arguments[0].value = arguments[1]", element, "text");

// Get title
String title = (String) js.executeScript("return document.title");

// Highlight element (debug)
js.executeScript("arguments[0].style.border='3px solid red'", element);
```

**Q: How do you use the `Actions` class?**
```java
Actions act = new Actions(driver);

// Hover
act.moveToElement(element).perform();

// Right click
act.contextClick(element).perform();

// Double click
act.doubleClick(element).perform();

// Drag and drop
act.dragAndDrop(source, target).perform();

// Key combinations
act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();

// Chain actions
act.moveToElement(menu).click().moveToElement(subMenu).click().perform();
```

**Q: How do you handle dropdowns?**
```java
// HTML <select> element
Select select = new Select(dropdownElement);
select.selectByVisibleText("Chrome");
select.selectByValue("chrome");
select.selectByIndex(0);
select.getOptions();           // List<WebElement>
select.getFirstSelectedOption();
select.deselectAll();          // for multi-select only
select.isMultiple();           // check if multi-select
```

**Q: How do you handle file upload?**
```java
// Using sendKeys on file input
WebElement fileInput = driver.findElement(By.id("file-upload"));
fileInput.sendKeys("C:\\path\\to\\file.txt");

// Using Robot class (for OS-level dialogs)
Robot robot = new Robot();
StringSelection selection = new StringSelection("C:\\path\\to\\file.txt");
Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
robot.keyPress(KeyEvent.VK_CONTROL);
robot.keyPress(KeyEvent.VK_V);
robot.keyRelease(KeyEvent.VK_V);
robot.keyRelease(KeyEvent.VK_CONTROL);
robot.keyPress(KeyEvent.VK_ENTER);
robot.keyRelease(KeyEvent.VK_ENTER);
```

**Q: How do you scroll in Selenium?**
```java
// Scroll to element
js.executeScript("arguments[0].scrollIntoView(true)", element);

// Scroll by pixel
js.executeScript("window.scrollBy(0, 500)");

// Scroll to bottom
js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

// Using Actions
act.moveToElement(element).perform();  // also scrolls into view

// Using Keys
element.sendKeys(Keys.PAGE_DOWN);
```

---

## 8. CI/CD & Reporting

**Q: How do you integrate Selenium tests with Jenkins?**
> 1. Install Jenkins, Maven plugin, TestNG plugin
> 2. Create a Maven project in Jenkins
> 3. Connect to GitHub repository
> 4. Build command: `mvn clean test -DbrowserName=chrome -DisHeadless=true`
> 5. Post-build: publish TestNG report, archive screenshots
> 6. Configure triggers: on push, scheduled (cron), or manual

**Q: What is Extent Report? How do you configure it?**
> Extent Reports is a reporting library. Configure via `TestListener`:
```java
public class TestListener implements ITestListener {
    ExtentReports extent;
    ExtentTest test;

    @Override
    public void onStart(ITestContext ctx) {
        ExtentSparkReporter reporter = new ExtentSparkReporter("Reports/report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) { test.pass("Test PASSED"); }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
        // take screenshot and attach
    }

    @Override
    public void onFinish(ITestContext ctx) { extent.flush(); }
}
```

**Q: How do you run tests headlessly in CI?**
```java
// Chrome headless options
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--disable-gpu");
options.addArguments("--no-sandbox");
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--window-size=1920,1080");
```
> Or pass as environment variable: `IS_HEADLESS=true` which the framework reads via `System.getenv()`.

---

## 9. API Testing (REST Assured Concepts)

**Q: What is REST Assured? What can you validate?**
> REST Assured is a Java DSL for testing RESTful APIs. You can validate: status code, response body (JSON/XML), headers, response time, schema.

**Q: What is the structure of a REST Assured test?**
```java
given()
    .baseUri("https://api.example.com")
    .header("Authorization", "Bearer " + token)
    .contentType(ContentType.JSON)
    .body("{ \"username\": \"admin\" }")
.when()
    .post("/api/login")
.then()
    .statusCode(200)
    .body("token", notNullValue())
    .body("user.name", equalTo("Admin"))
    .header("Content-Type", containsString("application/json"))
    .time(lessThan(2000L));
```

**Q: What is the difference between GET, POST, PUT, PATCH, DELETE?**
> `GET` — Retrieve resource, no body, idempotent
> `POST` — Create resource, has body, not idempotent
> `PUT` — Replace resource completely, idempotent
> `PATCH` — Partial update, not always idempotent
> `DELETE` — Remove resource, idempotent

**Q: What HTTP status codes do you commonly validate?**
> `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, `409 Conflict`, `422 Unprocessable Entity`, `500 Internal Server Error`

---

## 10. QA Processes & Mindset

**Q: What is the difference between verification and validation?**
> **Verification**: Are we building the product right? (code reviews, static analysis, unit tests)
> **Validation**: Are we building the right product? (acceptance testing, user testing)

**Q: What is a regression test? When do you run it?**
> Regression testing verifies that existing functionality still works after a code change. Run after every significant change, before every release. Automated regression suites are run in CI pipelines on every push.

**Q: What is the difference between smoke and sanity testing?**
> **Smoke testing**: Quick high-level check — does the build even work? (log in, navigate to key pages). Run first to decide if further testing is worth doing.
> **Sanity testing**: After a bug fix — verify the specific fix works and didn't break closely related functionality.

**Q: What is the Test Pyramid?**
```
        /\
       /  \    E2E Tests (UI) — few, slow, expensive
      /────\
     /      \  Integration Tests — moderate
    /────────\
   /          \ Unit Tests — many, fast, cheap
  /────────────\
```
> More unit tests, fewer E2E tests. E2E tests are brittle and slow — automate only the critical paths.

**Q: What is flaky test? How do you handle it?**
> A test that passes sometimes and fails sometimes without code changes. Causes: race conditions, timing issues, test data pollution, environment dependency. Fixes: proper waits, test isolation, deterministic test data, retry mechanism (`RetryTestListener` in the project).

**Q: How do you prioritize what to automate?**
> Automate: regression-heavy features, data-driven tests, repetitive manual tests, smoke suites, cross-browser tests.
> Don't automate: one-time exploratory tests, tests requiring human judgement (UX), tests that change frequently (unstable features), captcha/MFA.
