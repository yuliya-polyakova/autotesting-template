package polyakova.tests.tools.selenium;

/**
 * Field type
 *
 * @author Iuliia Poliakova
 */
public enum SeleniumFieldType {
    input {
        @Override
        public String fillTemplate(String findByType, String findByValue) {
            String methodName = getMethodName(findByValue);
            return "    @FindBy(" + findByType + " = \"" + findByValue + "\")\n" +
                    "    private WebElement " + findByValue + "Input;\n" +
                    "\n" +
                    "    @Step\n" +
                    "    public void set" + methodName + "(String " + findByValue + "){\n" +
                    "        " + findByValue + "Input.clear();\n" +
                    "        " + findByValue + "Input.sendKeys(" + findByValue + ");\n" +
                    "    }";
        }
    },
    checkbox {
        @Override
        public String fillTemplate(String findByType, String findByValue) {
            String methodName = getMethodName(findByValue);
            return "    @FindBy(" + findByType + " = \"" + findByValue + "\")\n" +
                    "    private WebElement " + findByValue + "Checkbox;\n" +
                    "\n" +
                    "    @Step\n" +
                    "    public void set" + methodName + "(boolean " + findByValue + ") {\n" +
                    "        if (" + findByValue + "Checkbox.isSelected() != " + findByValue + ") {\n" +
                    "            " + findByValue + "Checkbox.click();\n" +
                    "        }\n" +
                    "    }";
        }
    },
    button {
        @Override
        public String fillTemplate(String findByType, String findByValue) {
            String methodName = getMethodName(findByValue);
            return "    @FindBy(" + findByType + " = \"" + findByValue + "\")\n" +
                    "    private WebElement " + findByValue + "Button;\n" +
                    "\n" +
                    "    @Step\n" +
                    "    public void click" + methodName + "() {\n" +
                    "        " + findByValue + "Button.click();\n" +
                    "        //TODO return new somePage();\n" +
                    "    }";
        }
    };

    public abstract String fillTemplate(String findByType, String findByValue);

    public String getMethodName(String findByValue) {
        String methodName;
        if (findByValue.length() > 1) {
            methodName = findByValue.substring(0, 1).toUpperCase() + findByValue.substring(1);
        } else {
            methodName = "";
        }
        return methodName;
    }
}
