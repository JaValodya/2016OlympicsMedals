package com.cybertek;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedalsTests {
	public static WebDriver driver;

	@BeforeClass // runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public static void goToWebSite() {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
	}

	/*
	 * Test Case 1
	 */
	@Test
	public void sortTest() {

		goToWebSite();

		List<WebElement> ranks = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		if (!(ranks.get(0).getText().equals(1))) {
			driver.findElement(By
					.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'Rank']"))
					.click();
			ranks = driver.findElements(By
					.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		}
		System.out.println(ranks.size());
		String ranksExpected = "12345678910";
		String ranksActual = "";
		
		for (int i = 0; i < ranks.size(); i++) {
			ranksActual += ranks.get(i).getText();

		}
		Assert.assertEquals(ranksActual, ranksExpected);
		System.out.println();

		driver.findElement(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'NOC']"))
				.click();

		List<WebElement> countryNames = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th"));
		List<String> countriesActual = new ArrayList<>();
		List<String> countriesExpected = new ArrayList<>();
		for (WebElement webElement : countryNames) {
			countriesActual.add(webElement.getText());
			countriesExpected.add(webElement.getText());
		}
		Collections.sort(countriesExpected);

		Assert.assertEquals(countriesActual, countriesExpected);

		 ranks = driver.findElements(
		 By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		 for (int i = 0; i < ranks.size() - 1; i++) {
		 ranksActual +=ranksActual += ranks.get(i).getText();
		 }
		 Assert.assertNotEquals(ranksActual, ranksExpected);
	}

	public static String getGoldMedals() {

		WebElement goldColumn = driver.findElement(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'Gold']"));
		goldColumn.click(); // one click sorts the column in acceding order
		goldColumn.click(); // second click sorts the column in descending order
		String country = driver
				.findElement(By.xpath(
						"//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th[1]"))
				.getText();
		return country;
	}

	public static String getSilverMedals() {

		WebElement silverColumn = driver.findElement(By
				.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'Silver']"));
		silverColumn.click();
		silverColumn.click();
		String country = driver
				.findElement(By.xpath(
						"//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th[1]"))
				.getText();

		return country;
	}

	public static String getBronzeMedals() {

		WebElement bronzeColumn = driver.findElement(By
				.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'Bronze']"));
		bronzeColumn.click();
		bronzeColumn.click();
		String country = driver
				.findElement(By.xpath(
						"//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th[1]"))
				.getText();

		return country;
	}

	public static String mostMedals() {

		WebElement totalColumn = driver.findElement(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'Total']"));
		totalColumn.click();
		totalColumn.click();
		String country = driver
				.findElement(By.xpath(
						"//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th[1]"))
				.getText();

		return country;
	}

	/*
	 * Test Case 2
	 */

	@Test
	public void theMost() {
		goToWebSite();
		String goldMostExpected = " United States (USA)";
		String silverMostExpected = " United States (USA)";
		String bronzeMostExpected = " United States (USA)";
		String totalMostExpected = " United States (USA)";

		Assert.assertEquals(getGoldMedals(), goldMostExpected);
		Assert.assertEquals(getSilverMedals(), silverMostExpected);
		Assert.assertEquals(getBronzeMedals(), bronzeMostExpected);
		Assert.assertEquals(goldMostExpected, totalMostExpected);
	}

	/*
	 * Following method will return countries list with the number of Silver Medals
	 * and takes number of medals as parameter
	 */

	public static List<String> countrysWithSilverMedals(int numberOfMedals) {

		List<String> countrys = new ArrayList<>();
		Map<String, String> silverMedals_Countries = new HashMap<String, String>();

		List<WebElement> countryNames = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th"));
		List<WebElement> countrysSilverMedals = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[3]"));

		for (int i = 0; i < countryNames.size() - 1; i++) {

			silverMedals_Countries.put(countryNames.get(i).getText(), countrysSilverMedals.get(i).getText());
		}
		Set<Entry<String, String>> silverMedals_CountriesList = silverMedals_Countries.entrySet();
		for (Entry<String, String> temp : silverMedals_CountriesList) {

			if (temp.getValue().equals(String.valueOf(numberOfMedals))) {
				countrys.add(temp.getKey());

			}

		}

		return countrys;
	}

	/*
	 * Test Case 3
	 */
	@Test
	public void counrtyByMedal() {
		goToWebSite();
		List<String> medals18CountrysExpected = Arrays.asList(" France (FRA)", " China (CHN)");
		List<String> medals18Countrys = countrysWithSilverMedals(18);
		System.out.println(medals18Countrys);
		Assert.assertEquals(medals18Countrys, medals18CountrysExpected);
	}

	/*
	 * Following method will takes country name as parameter and returns the row and
	 * column number as String
	 */

	public static String getCountryTableCoordinates(String Country) {

		// HashMap<Integer, Integer> coordinates = new HashMap<>();
		String coordinates = "";
		List<WebElement> rows = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr"));
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).getText().contains(Country)) {
				int column = 2; // how others get this???
				int row = i + 1;
				// coordinates.put(i + 1, column);
				coordinates += row + "," + column;
			}
		}

		return coordinates;
	}

	/*
	 * Test Case 4
	 */
	@Test
	public void getIndex() {
		goToWebSite();
		driver.findElement(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//th[. = 'NOC']"))
				.click();
		String coordinatesExpected = "7,2";
		String coordinatesActual = getCountryTableCoordinates("Japan");
		System.out.println(coordinatesActual);
		Assert.assertEquals(coordinatesActual, coordinatesExpected);
	}

	/*
	 * Following method will takes number of medals as parameter and will return the
	 * list of countries that have the sum of bronze medals
	 */

	public static List<String> getCountriesWithSilverMedalsSum(int num) {
		List<String> countrys = new ArrayList<>();

		Map<String, String> silverMedals_Countries = new HashMap<String, String>();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> countryNames = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th"));
		List<WebElement> countrysBronseMedals = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[4]"));

		String[] countryNamesstr = new String[10];
		String[] countryBronzeMedalsstr = new String[10];
		for (int i = 0; i < countryNames.size() - 1; i++) {
			countryNamesstr[i] = countryNames.get(i).getText();
			countryBronzeMedalsstr[i] = countrysBronseMedals.get(i).getText();

			//silverMedals_Countries.put(countryNames.get(i).getText(), countrysBronseMedals.get(i).getText());
		}
		//Set<Entry<String, String>> silverMedals_CountriesList = silverMedals_Countries.entrySet();
		// for (Entry<String, String> temp : silverMedals_CountriesList) {

		for (int i = 0; i < countryNamesstr.length; i++) {
			for (int j = 0; j < countryNamesstr.length; j++) {
				if (j == i) {
					continue;
				}
				if (Integer.parseInt(countryBronzeMedalsstr[i]) + Integer.parseInt(countryBronzeMedalsstr[j]) == num) {
					countrys.add(countryNamesstr[i]);
				}
			}

		}
		//
		// if (temp.getValue().equals(String.valueOf(numberOfMedals))) {
		// countrys.add(temp.getKey());
		//
		// }

		// }
		Collections.sort(countrys);
		return countrys;

	}

	/*
	 * Test Case 5
	 */
	@Test
	public void agetSum() {

		goToWebSite();
		List<String> countriesExpected = Arrays.asList(" Italy (ITA)", " Australia (AUS)");
		Collections.sort(countriesExpected);
		List<String> countriesActual = getCountriesWithSilverMedalsSum(18);
		System.out.println(countriesActual);
		Assert.assertEquals(countriesActual, countriesExpected);
	}

	@AfterClass
	public void shutDown() {
		driver.quit();
	}

}
