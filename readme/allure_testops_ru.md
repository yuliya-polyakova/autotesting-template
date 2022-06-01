# Установить Allure Testops

Устанавливается через docker-compose 

Инструкция по установке на английском по ссылке https://docs.qameta.io/allure-testops/getstarted/docker-compose

Кратко:
- скачиваем https://docs.qameta.io/allure-testops/dist/allure-testops.zip
- распаковываем
- выполнить 
  > openssl rand -base64 16
  - в windows openssl идет в поставке с git (git\usr\bin\openssl.exe)
  - результат например
  > vT5vyGpKuTFOIB5z3DHHvQ==
- необходимо вписать в распакованный файл .env, в значение 
  > JWT_SECRET
- Необходимо залогиниться в dockerhub:
  > docker login --username qametaaccess
    - затем ввести пароль
- в директории выполнить 
  > docker-compose pull
  > 
  > docker-compose up -d
- ждем инициализации
- приложение должно быть доступно по адресу ENDPOINT= http://localhost:8080

Приложение будет хранить описание тестов и результаты прогонов тестов
 

