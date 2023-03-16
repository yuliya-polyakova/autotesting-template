AbstractPage implements a screen method that takes a screenshot.

The resulting snapshot is saved in the `tests/report/screen` folder.

The file is also included in the Allure report.

The screen method can take a WabElement parameter, which allows you to take a screenshot of only the necessary component (modal window, panel, element, etc.)

Files are grouped by the full name of the test class (a folder is created) and by the name of the test method (a folder is created).

for example
> tests/report/screen/polyakova_test_tests_selenium_CustomerTest/testCreateCustomer

Screenshot files are created in the directory, the file name is formed by "line number in the code" _ "method name", if there was a chain of calls, it will all be displayed in the file name
> tests/report/screen/polyakova_test_tests_selenium_CustomerTest/testCreateCustomer/55_loginRO_30_openRO_39.png

thus, if we open the CustomerTest class, then on line 55 we will see a call to the loginRO method, if we fall into it, then on line 30 there will be a call to openRO, if we fall into it, then on line 39 there will be a call to the screen method

This allows you to quickly find the closest screen to the required step in the code.

Upon completion of the test, a screen `9999_afterTest.png` is made, regardless of the success of the test itself.