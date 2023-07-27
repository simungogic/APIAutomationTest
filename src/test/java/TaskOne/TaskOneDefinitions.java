package TaskOne;

import Utility.HttpHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;

public class TaskOneDefinitions {

    private Response response;

    @Given("Request parameters are set")
    public void setReqParams() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @When("I send GET request to {string}")
    public void sendGetReq(String uri) {
        response = HttpHelper.sendGetReq(uri);
    }

    @Then("Response code should be {int}")
    public void checkResponseCode(Integer expectedStatusCode) {
        Assert.assertTrue("Response code should be " + expectedStatusCode +
                        ", instead got " + response.getStatusCode()
                , expectedStatusCode.equals(response.getStatusCode()));
    }

    @And("Print users")
    public void printUsers() {
        JsonPath jsonPath = response.jsonPath();
        int numOfUsers = jsonPath.getInt("data.size()");

        System.out.println("List of users: ");
        for(int i=0;i<numOfUsers;i++) {
            System.out.printf(
                    """
                    %3s | %10s | %10s | %25s | %25s
                            """,
                    jsonPath.getInt("data["+i+"].id"),
                    jsonPath.getString("data["+i+"].first_name"),
                    jsonPath.getString("data["+i+"].last_name"),
                    jsonPath.getString("data["+i+"].email"),
                    jsonPath.getString("data["+i+"].avatar")
                    );
        }
    }

    @When("I send POST request to {string} with {string} and {string}")
    public void sendPostReqWithParams(String uri, String email, String password) {
        JSONObject reqParams = new JSONObject();

        if(!email.trim().equalsIgnoreCase("no email passed"))
            reqParams.put("email", email);

        if(!password.trim().equalsIgnoreCase("no password passed"))
            reqParams.put("password", password);

        response = HttpHelper.sendPostReq(uri, reqParams);
    }

    @And("Message should be {string}")
    public void messageShouldBe(String expectedMessage) {
        Assert.assertEquals(expectedMessage, response.jsonPath().get("error").toString());
    }

    @When("I send DELETE request to {string}\\/{int}")
    public void sendDeleteReq(String uri, Integer userId) {
        response = HttpHelper.sendDeleteReq(uri, userId);
    }

    @When("I send GET request with {string} delay to {string}")
    public void iSendGETRequestWithDelayTo(String delay, String uri) {
        String uriWithDelay = uri + "?delay=" + Integer.parseInt(delay.substring(0, delay.length() - 1));
        sendGetReq(uriWithDelay);
    }

    @And("Check if response took {string}")
    public void checkResponseTime(String delay) {
        Assert.assertTrue("Response time should be greater than or equal to " + delay,
                response.getTimeIn(TimeUnit.SECONDS) >= Integer.parseInt(delay.substring(0, delay.length() - 1)));
    }
}
