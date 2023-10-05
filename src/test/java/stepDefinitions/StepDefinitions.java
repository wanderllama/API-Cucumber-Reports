package stepDefinitions;

import enums.HttpOperation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.RestAssured;
import util.TestContext;

public class StepDefinitions {

    private static final Logger LOG = LogManager.getLogger(StepDefinitions.class);

    @Given("a {} request is prepared for {} uri and {} endpoint")
    public void aRequestIsPreparedForUriAndEndpoint(HttpOperation requestType, String uri, String endpoint) {
        TestContext.getRA().initBase(uri);
        TestContext.getRA().init(endpoint, requestType);
        LOG.info("Created " + requestType.toString() + " Request Specification using " + uri + " URI and " + endpoint +" endpoint");
    }

    @When("the {} request is sent")
    public void theRequestIsSent(String arg0) {
    }

    @Then("the response will have {} code")
    public void theResponseWillHaveCode(String arg0) {
    }

    @And("the response {} will match")
    public void theResponseWillMatch(String arg0) {
    }

    @And("the {} parameter key and {} parameter value was added to the request")
    public void theParameterKeyAndParameterValueWasAddedToTheRequest(String key, String value) {
        TestContext.getRA().setQueryParam(key, value);
    }
}
