В AbstractPage реализован метод screen который делает скриншот.

Полученный снимок сохраняется в папке `tests/report/screen`.

Файл также включается в отчет Allure.

Метод screen может принимать параметр WabElement, который позволяет сделать снимок только необходимого компонента (модальное окно, панель, элемент и т.п.)

Файлы группируются по полному имени класса теста (создается папка) и по имени тестового метода (создается папка). 

Например
> tests/report/screen/polyakova_tests_selenium_CustomerTest/testCreateCustomer

В директории создаются файлы скринов, имя файла формируется "номер строки в коде"_"имя метода", если была цепочка вызовов она вся будет отображена в имени файла
> tests/report/screen/polyakova_tests_selenium_CustomerTest/testCreateCustomer/55_loginRO_30_openRO_39.png

Таким образом если мы откроем класс CustomerTest, то на 55 строке мы увидим вызов метода loginRO, если провалиться в него, то на 30 строке будет вызов openRO, если провалиться в него, то на 39 строчке будет вызов метода screen    

Это позволяет быстро находить ближайший скрин к необходимому шагу в коде.

По завершению тесто делается скрин `9999_afterTest.png`, в независимости от успешности самого теста.