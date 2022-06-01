Настройка Jenkins для работы с Allure TestOps

Инструкция на английском
https://docs.qameta.io/allure-testops/integrations/ctl/install-plugin/

1. Скачать плагин https://qameta.github.io/distributions/#allure-testops-jenkins
2. Установить плагин в Jenkins
   1. Меню "Manage Jenkins" - "Manage Plugins". ![jenkins-manage-plugins](https://docs.qameta.io/allure-testops/images/jenkins-manage-plugins.png)
   2. Вкладка "Advanced", выбрать скачанный ранее плагин: "Choose File" - "allure-jenkins.hpi" ![jenkins-upload-plug-in](https://docs.qameta.io/allure-testops/images/jenkins-upload-plug-in.png)
   3. Нажмите кнопку "Upload"
3. Настроить доступ к Allure TestOps
   1. Меню "Manage Jenkins" - "Configure System"
   2. Создать настройку к серверу "Allure" - "Allure Servers" - "Add Allure Server" ![jenkins-allure-server-list](https://docs.qameta.io/allure-testops/images/jenkins-allure-server-list.png)
   3. Идентификатор любой, например "allure-testops" 
   4. Endpoint - "https://allure-testops.site.name/"
   5. Авторизация "Credentials" - "Add" - "Jenkins" ![jenkins-credentials-add](https://docs.qameta.io/allure-testops/images/jenkins-credentials-add.png)
      1. Domain - без изменений "Global credentials"
      2. Kind - "Secret text"
      3. Scope - без изменений "Global" 
      4. Secret - "ключ полученный в allure"
      5. ID - "allure-token"
   6. Сохранить настройки


