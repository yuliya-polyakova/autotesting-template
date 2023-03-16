1. Установить Android Studio
   > https://developer.android.com/studio
   1. Запустить Android Studio
   2. Добавить эмулятор
      1. More Actions ![More Actions](img/AndroidStudioMoreActions.png)
      2. Virtual Device Manager
      3. Create device ![Create Device](img/AndroidStudioCreateDevice.png)
      4. Pixel 3 ![Pixel 3](img/AndroidStudioCreateDevicePixel3.png)
      5. Android 11 ![Android 11](img/AndroidStudioCreateDeviceAndroid11.png)
         1. Download
         2. Next
         3. Finish
   3. Запустить эмулятор ![DeviceRun](img/AndroidStudioCreateDeviceRun.png)
2. Установить Appium
   > https://github.com/appium/appium-desktop/releases
   1. Запустить Appium Server ![Appium Server GUI](img/AppiumServerGUI.png)
3. Установить утилиту для работы с элементами UI на мобильном устройстве
   > https://github.com/appium/appium-inspector/releases
   1. Настроить ![AppiumInspectorSettings](img/AppiumInspectorSettings.png)
   > {
   "platformName": "Android",
   "deviceName": "Pixel 3",
   "udid": "emulator-5554"
   }
   2. Start Session