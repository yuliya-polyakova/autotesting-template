Для запуска эмулятора в Docker достаточно выполнить
```
docker run --privileged -d -p 4723:4723 -p 5554:5554 -p 5555:5555 -p 6080:6080 -e DEVICE="Samsung Galaxy S8" -e APPIUM=true --name android-container budtmo/docker-android-x86-11.0
```
Там-же будет запущен Appium

