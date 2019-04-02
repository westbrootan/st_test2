package testUrl;

import java.util.regex.Pattern;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@RunWith(Parameterized.class)
public class testUrl{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  static ArrayList<String> num = new ArrayList<>();
  static ArrayList<String> name = new ArrayList<>();
  static ArrayList<String> purl = new ArrayList<>();
  String input1;
  String input2;
  String input3;
  
  public testUrl(String input1,String input2,String input3) {
	  this.input1 = input1;
	  this.input2 = input2;
	  this.input3 = input3;
  }
  
  static {
      String Path = "D:/Java_Project/testUrl/软件测试名单.xls";
      Sheet sheet;
      Workbook book;
      try {
		  book = Workbook.getWorkbook(new File(Path));
		 //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
	      sheet = book.getSheet(0);
	      for (int i = 2; i < 145; i++) {
	          num.add(sheet.getCell(1, i).getContents());
	          name.add(sheet.getCell(2, i).getContents());
	          purl.add(sheet.getCell(3, i).getContents());
	      }
	} catch (BiffException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  @Before
  public void setUp() throws Exception {
	String driverPath = "D:/Java_Project/testBaidu/geckodriver.exe";
	System.setProperty("webdriver.gecko.driver", driverPath);
    driver = new FirefoxDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.get("http://121.193.130.195:8800/login");
  }
  
  @Parameterized.Parameters
  public static Collection<Object[]> parameters() {
	  Object[][] list = new Object[num.size()][];
	  for(int i = 0; i < num.size(); i++) {
		 list[i][0] = num.get(i);
		 list[i][1] = name.get(i);
		 list[i][2] = purl.get(i);
	  }
	  return Arrays.asList(list);
  }
  
  @Test
  public void testUntitledTestCase() throws Exception {    
	    driver.findElement(By.name("id")).click();
	    driver.findElement(By.name("id")).clear();
	    driver.findElement(By.name("id")).sendKeys(input1);
	    driver.findElement(By.id("login_form")).click();
	    driver.findElement(By.name("password")).click();
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys(input1.substring(4));
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='网页模板'])[1]/following::div[4]")).click();
	    driver.findElement(By.id("btn_login")).click();
	    driver.findElement(By.id("student-git")).click();
	    driver.findElement(By.id("student-git")).click();
	    try {
	      assertEquals(input1, driver.findElement(By.id("student-id")).getText());
	      assertEquals(input2, driver.findElement(By.id("student-name")).getText());
	      assertEquals(input3, driver.findElement(By.id("student-git")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.id("btn_logout")).click();
	    driver.findElement(By.id("btn_return")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
