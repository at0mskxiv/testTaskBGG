package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static io.restassured.RestAssured.given;

public class StepDefinitions {
    BasePage basePage = new BasePage();
    Response response;
    Map<String, String> map = new HashMap<>();
    Map<Integer, String> finalMap = new TreeMap<>(Comparator.reverseOrder());

    @Given("Open homepage of the site")
    public void openHomepageOfTheSite() throws InterruptedException {
        BaseTest.driver.get("https://boardgamegeek.com/");
        Assertions.assertTrue(basePage.isLogoDisplayed());
    }

    @When("Navigate to the page of the most top game with increasing rank in \"The Hotness\" left side menu")
    public void navigateToThePageOfTheMostTopGameWithIncreasingRankInLeftSideMenu() throws InterruptedException {
        basePage.clickSeeAllHotness();
        Assertions.assertTrue(basePage.isHotnessHeaderDisplayed());
    }


    @When("Retrieve information about a particular game from site api")
    public void retrieveInformationAboutAParticularGameFromSiteApi() {
        String id = basePage.getTopHotnessGameId();

        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://boardgamegeek.com/xmlapi/";
        RestAssured.defaultParser = Parser.JSON;
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = given();
        // specify the method type (GET) and the parameters if any.
        response = httpRequest.request(Method.GET, "boardgame/" + id);
    }

    @When("Parse response to get most voted option in Language Dependence poll")
    public void parseResponseToGetMostVotedOptionInLanguageDependencePoll() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(response.asString())));

        //Get all
        NodeList nList = document.getElementsByTagName("result");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getParentNode().getParentNode().getAttributes().getNamedItem("name").toString().contains("language_dependence"))
            {
                Element eElement = (Element) node.getChildNodes();
                map.put("value", eElement.getAttribute("value"));
                map.put("numvotes", eElement.getAttribute("numvotes"));
                map.forEach((k, v) -> finalMap.put(Integer.valueOf(eElement.getAttribute("numvotes")), eElement.getAttribute("value")));
            }
        }
    }

    @Then("Assert Language Dependence text presented in the game page Description block equals the most voted Language Dependence level")
    public void assertLanguageDependenceTextPresentedInTheGamePageDescriptionBlockEqualsTheMostVotedLanguageDependenceLevel() throws InterruptedException {
        String mostVotedOption = finalMap.values().iterator().next();
        basePage.clickOnHotnessGame();
        Assertions.assertTrue(mostVotedOption.equals(basePage.getLanguageDependence()));
    }
}
