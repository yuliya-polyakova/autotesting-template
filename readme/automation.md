Test Automation

1. Before writing a test, you need to read
   1. Examples are located `tests-demo/src/test/java/polyakova/test/tests/demo`
      1. An example of using Allure annotations `tests-demo/src/test/java/polyakova/test/tests/demo/TestAllure.java`
      2. An example of using environment variables `tests-demo/src/test/java/polyakova/test/tests/demo/TestEnvironmentVariables.java`
         1. Variable values are stored in `tests-demo/src/test/resources/environment_dev.properties`
      3. An example of user interface testing using selenium and the PageObject pattern `tests-demo/src/test/java/polyakova/test/tests/demo/selenium/TestSelenium.java`

[//]: # ( 2. Available tests and page descriptions `tests/src/test/java/polyakova/test/tests/selenium`)
2. We take the testcase specified in the ticket as the basis for the test, it must be imported into idea
   1. Select or create a class corresponding to the required Suite (an annotation over the class `@DisplayName("Name of the Suite")`)
      1. Class name `<Name suite>Test`
   2. Create a method for the test case
      1. Name `test<Name testcase>`
      2. Add the `@Test` annotation above the method