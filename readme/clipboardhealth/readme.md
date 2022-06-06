Instruction for run test task

```
git clone https://github.com/yuliya-polyakova/autotesting-template.git
cd autotesting-template
mvn -Dtest=ClipboardHealthTest -DfailIfNoTests=false test
cd tests-demo 
mvn allure:serve
```

Test for <a href="https://github.com/ClipboardHealth/vanilla/blob/main/docs/assignment.md">task</a> is located in `tests-demo/src/test/java/polyakova/test/demo/clipboardhealth`