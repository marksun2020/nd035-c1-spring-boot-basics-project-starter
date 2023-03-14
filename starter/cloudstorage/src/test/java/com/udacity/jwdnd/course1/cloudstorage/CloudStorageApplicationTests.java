package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		clickElement(webDriverWait, "login-button");

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void doLogOut()
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		clickElement(webDriverWait, "logout-button");

		webDriverWait.until(ExpectedConditions.titleContains("Login"));

	}

	private void clickElement(WebDriverWait webDriverWait, String elementId) {
		clickElement(webDriverWait, elementId, 0);
	}

	private void clickElement(WebDriverWait webDriverWait, String elementId, int index) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
		WebElement element = driver.findElements(By.id(elementId)).get(index);
		element.click();
	}

	private void typeText(WebDriverWait webDriverWait, String textFieldId, String text) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(textFieldId)));
		WebElement fileSelectButton = driver.findElement(By.id(textFieldId));
		fileSelectButton.sendKeys(text);
	}

	private void clearText(WebDriverWait webDriverWait, String textFieldId) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(textFieldId)));
		WebElement fileSelectButton = driver.findElement(By.id(textFieldId));
		fileSelectButton.clear();
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testUnathorizedUser() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		//Visit the sign-up page.
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

		//Visit the login page.
		driver.get("http://localhost:" + this.port + "/login");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		// Visit home page failed
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void SignAndUnsignUser() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Visit home page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		doLogOut();

		// Visit home page failed
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void addNote() {
		String newNoteTitle = "new note title";
		String newNoteDescription = "new note description";

		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		createNote(newNoteTitle, newNoteDescription, webDriverWait);

		// assertion
		Assertions.assertTrue(driver.getPageSource().contains(newNoteTitle));
		Assertions.assertTrue(driver.getPageSource().contains(newNoteDescription));
	}

	@Test
	public void editNote() {
		String newNoteTitle1 = "new note title-1";
		String newNoteDescription1 = "new note description-1";
		String newNoteTitle2 = "new note title-2";
		String newNoteDescription2 = "new note description-2";
		String editedNoteTitle = "edited note title-1";
		String editedNoteDescription = "edited note description-1";

		// For keeping the test independent, a new user should be created and a new note will be stored
		//
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// add one note
		createNote(newNoteTitle1, newNoteDescription1, webDriverWait);
		// add one more note
		createNote(newNoteTitle2, newNoteDescription2, webDriverWait);

		// edit the second note.
		clickElement(webDriverWait, "nav-notes-tab");
		clickElement(webDriverWait, "edit-note-button", 1);
		clearText(webDriverWait, "note-title");
		typeText(webDriverWait, "note-title", editedNoteTitle);
		clearText(webDriverWait, "note-description");
		typeText(webDriverWait, "note-description", editedNoteDescription);
		clickElement(webDriverWait, "submit-note-button");

		clickElement(webDriverWait, "nav-notes-tab");
		// assertion
		Assertions.assertTrue(driver.getPageSource().contains(newNoteTitle1));
		Assertions.assertTrue(driver.getPageSource().contains(newNoteDescription1));
		Assertions.assertFalse(driver.getPageSource().contains(newNoteTitle2));
		Assertions.assertFalse(driver.getPageSource().contains(newNoteDescription2));
		Assertions.assertTrue(driver.getPageSource().contains(editedNoteTitle));
		Assertions.assertTrue(driver.getPageSource().contains(editedNoteDescription));
	}

	@Test
	public void deleteNote() {
		String newNoteTitle1 = "new note title-1";
		String newNoteDescription1 = "new note description-1";
		String newNoteTitle2 = "title to delete";
		String newNoteDescription2 = "description to delete";


		// For keeping the test independent, a new user should be created and a new note will be stored
		//
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// add one note
		createNote(newNoteTitle1, newNoteDescription1, webDriverWait);
		// add one more note
		createNote(newNoteTitle2, newNoteDescription2, webDriverWait);

		// delete the second note
		clickElement(webDriverWait, "nav-notes-tab");
		clickElement(webDriverWait, "delete-note-button", 1);

		clickElement(webDriverWait, "nav-notes-tab");
		// assertion
		Assertions.assertTrue(driver.getPageSource().contains(newNoteTitle1));
		Assertions.assertTrue(driver.getPageSource().contains(newNoteDescription1));
		Assertions.assertFalse(driver.getPageSource().contains(newNoteTitle2));
		Assertions.assertFalse(driver.getPageSource().contains(newNoteDescription2));
	}

	@Test
	public void addCredential() {
		String newUrl = "new url";
		String newUsername = "new user";
		String newPassword = "new pass";

		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Choose Credential bookmark
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// add one credential
		createCredential(newUrl, newUsername, newPassword, webDriverWait);

		// assertion
		clickElement(webDriverWait, "nav-credentials-tab");
		Assertions.assertTrue(driver.getPageSource().contains(newUrl));
		Assertions.assertTrue(driver.getPageSource().contains(newUsername));
		Assertions.assertFalse(driver.getPageSource().contains(newPassword));
	}

	@Test
	public void editCredential() {
		String newUrl1 = "new url-1";
		String newUsername1 = "new user-1";
		String newPassword1 = "new pass-1";
		String newUrl2 = "new url-2";
		String newUsername2 = "new user-2";
		String newPassword2 = "new pass-2";
		String editedUrl1 = "edited url-1";
		String editedUsername1 = "edited user-1";

		// For keeping the test independent, a new user should be created and a new note will be stored
		//
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// add one credential
		createCredential(newUrl1, newUsername1, newPassword1, webDriverWait);
		// add one more credential
		createCredential(newUrl2, newUsername2, newPassword2, webDriverWait);

		// edit the second credential.
		clickElement(webDriverWait, "nav-credentials-tab");
		clickElement(webDriverWait, "edit-credential-button", 1);
		clearText(webDriverWait, "credential-url");
		typeText(webDriverWait, "credential-url", editedUrl1);
		clearText(webDriverWait, "credential-username");
		typeText(webDriverWait, "credential-username", editedUsername1);
		clickElement(webDriverWait, "submit-credential-button");

		clickElement(webDriverWait, "nav-credentials-tab");
		// assertion
		Assertions.assertTrue(driver.getPageSource().contains(newUrl1));
		Assertions.assertTrue(driver.getPageSource().contains(newUsername1));
		Assertions.assertFalse(driver.getPageSource().contains(newUrl2));
		Assertions.assertFalse(driver.getPageSource().contains(newUsername2));
		Assertions.assertTrue(driver.getPageSource().contains(editedUrl1));
		Assertions.assertTrue(driver.getPageSource().contains(editedUsername1));
	}

	@Test
	public void deleteCredential() {
		String newUrl1 = "new url-1";
		String newUsername1 = "new user-1";
		String newPassword1 = "new pass-1";
		String newUrl2 = "url to delete";
		String newUsername2 = "username to delete";
		String newPassword2 = "pass to delete";

		// For keeping the test independent, a new user should be created and a new note will be stored
		//
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// add one credential
		createCredential(newUrl1, newUsername1, newPassword1, webDriverWait);
		// add one more credential
		createCredential(newUrl2, newUsername2, newPassword2, webDriverWait);

		// delete the second credential
		clickElement(webDriverWait, "nav-credentials-tab");
		clickElement(webDriverWait, "delete-credential-button", 1);

		clickElement(webDriverWait, "nav-credentials-tab");

		// assertion
		Assertions.assertTrue(driver.getPageSource().contains(newUrl1));
		Assertions.assertTrue(driver.getPageSource().contains(newUsername1));
		Assertions.assertFalse(driver.getPageSource().contains(newUrl2));
		Assertions.assertFalse(driver.getPageSource().contains(newUsername2));
	}


	private void createNote(String noteTitle, String noteDescription, WebDriverWait webDriverWait) {
		clickElement(webDriverWait, "nav-notes-tab");
		clickElement(webDriverWait, "add-note-button");
		typeText(webDriverWait, "note-title", noteTitle);
		typeText(webDriverWait, "note-description", noteDescription);
		clickElement(webDriverWait, "submit-note-button");
	}

	private void createCredential(String url, String username, String password, WebDriverWait webDriverWait) {
		clickElement(webDriverWait, "nav-credentials-tab");

		// add new credential
		clickElement(webDriverWait, "add-credential-button");

		// type url
		typeText(webDriverWait, "credential-url", url);

		// type username
		typeText(webDriverWait, "credential-username", username);

		// type password
		typeText(webDriverWait, "credential-password", password);

		// confirm adding new credential
		clickElement(webDriverWait, "submit-credential-button");
	}

}
