package util;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.empty;
import java.io.File;
import java.util.List;
import java.util.Map;

import enums.HttpOperation;
import enums.ValidatorOperation;

import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import stepDefinitions.StepDefinitions;
import utils.ConfigurationReader;

import static org.hamcrest.Matchers.anyOf;

public class RestAssured {

    private static final Logger LOG = LogManager.getLogger(RestAssured.class);

    RequestSpecification reqSpec;
    HttpOperation requestType;
    String endpoint;
    Response resp;


    public void initBase(String uri) {        
        try {
            io.restassured.RestAssured.baseURI = ConfigurationReader.getProperty(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqSpec = given();
        LOG.info("base URI set to " + uri);
    }

    public void init(String endpoint, HttpOperation requestType) {
        this.endpoint = endpoint;
        this.requestType = requestType;
        basePath = endpoint;
        reqSpec = given();
        LOG.info("endpoint set to " + endpoint + " and request method type set to " + requestType);
    }

    public void setHeader(Map<String, String> headers) {
        headers.forEach((k, v) -> reqSpec.header(k, v));
        LOG.info("headers added to request specification \n" + headers);
    }

    public void setHeader(String head, String val) {
        reqSpec.header(head, val);
        LOG.info(head.toUpperCase() + ":" + val + " header added to request specification");
    }

    public void setBody(JsonObject body) {
        reqSpec.body(body);
        LOG.info("body added to request specification");
    }

    public void setFormParam(String key, String val) {
        reqSpec.formParam(key, val);
        LOG.info("form parameter added to request specification");
    }

    public void setQueryParam(String key, String val) {
        reqSpec.queryParam(key, val);
        LOG.info("?" + key + "=" + val + "query parameter added to request specification");
    }

    public String callIt() {
        switch (requestType.toString()) {
            case "GET":
                resp = reqSpec.get();
                LOG.info("GET request added to request specification");
                break;
            case "POST":
                resp = reqSpec.post();
                LOG.info("POST request added to request specification");
                break;
            case "PATCH":
                resp = reqSpec.patch();
                LOG.info("PATCH request added to request specification");
                break;
            case "PUT":
                resp = reqSpec.put();
                LOG.info("PUT request added to request specification");
                break;
            case "DELETE":
                resp = reqSpec.delete();
                LOG.info("DELETE request added to request specification");
                break;
            default:
                LOG.info("invalid HTTP method");
        }
        return resp.asString();
    }

    public RestAssured assertIt(String key, Object val, ValidatorOperation operation) {
        switch (operation.toString()) {
            case "EQUALS":
                resp.then().body(key, equalTo(val));
                break;
            case "KEY_PRESENTS":
                resp.then().body(key, hasKey(key));
                break;
            case "HAS_ALL":
                break;
            case "NOT_EQUALS":
                resp.then().body(key, not(equalTo(val)));
                break;
            case "EMPTY":
                resp.then().body(key, empty());
                break;
            case "NOT_EMPTY":
                resp.then().body(key, not(emptyArray()));
                break;
            case "NOT_NULL":
                resp.then().body(key, notNullValue());
                break;
            case "HAS_STRING":
                resp.then().body(key, containsString((String)val));
                break;
            case "SIZE":
                resp.then().body(key, hasSize((int)val));
                break;
        }
        return this;
    }

    public void assertIt(List<List<Object>> data) {
        for (List<Object> li : data) {
            switch (li.get(2).toString()) {
                case "EQUALS":
                    resp.then().body(((String) li.get(0)), equalTo((String) li.get(1)));
                    break;
                case "KEY_PRESENTS":
                    resp.then().body(((String) li.get(0)), hasKey((String) li.get(1)));
                    break;
                case "HAS_ALL":
                    break;
            }
        }
    }

    public RestAssured assertStatusCode(int code) {
        resp.then().statusCode(code);
        return this;
    }

    public String extractString(String path) { return resp.jsonPath().getString(path);}

    public int extractInt(String path) { return resp.jsonPath().getInt(path);}

    public List<?> extractList(String path) { return resp.jsonPath().getList(path);}

    public Object extractIt(String path) { return resp.jsonPath().get(path); }

    public String extractHeader(String header_name) { return resp.header(header_name);}

    public String getResponseString() { return resp.asString();}

    public void fileUpload(String key, String path, String mime) {
        reqSpec.multiPart(key, new File(path), mime);
    }

    public void multiPartString(String key, String input) { reqSpec.multiPart(key,input);}

    public void printResp() { resp.print();}

    public String getCookieValue(String cookieName) { return resp.getCookie(cookieName); }

    public RestAssured assertIt(int code, int optionalCode) {
        resp.then().statusCode(anyOf(equalTo(code),equalTo(optionalCode)));
        return this;
    }

    public void setRedirection(boolean followRedirects) {
        reqSpec.when().redirects().follow(followRedirects);
    }

    public void ListResponseHeaders()
    {
        // Get all the headers. Return value is of type Headers.
        Headers allHeaders = resp.headers();
        // Iterate over all the Headers
        for(Header header : allHeaders)
        {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }
    }

    public int getStatusCode() { return resp.getStatusCode(); }

    public Headers getAllHeaders() {return resp.getHeaders();}
}