package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty:target/cucumber/cucumber.txt",
				"html:target/cucumber/report",
				"json:target/cucumber/cucumber.json",
				"utils.MyTestListener"
		}
		,features= {"src/test/resources/features"}
		,glue = {"stepDefinitions"}
		//,dryRun = true
		,monochrome = true
		,snippets = SnippetType.CAMELCASE
		,tags = "@j"
		//,publish = true
		)
public class TestRunner {

}