### Запуск тестов локально
1. Открыть терминал из текущей директории (test) и прописать команды `./bootstrap_ios_tests.sh` и `./bootstrap_android_tests.sh` (их нужно прописывать при каждом изменении в сэмпле, так как они производят сборку приложения)
2. Открыть текущую директорию (test) в IDEA или Android Studio.
3. Перед запуском тестов прописать нужную платформу (ios/android) в переменную defaultPlatform в файле test/src/test/kotlin/support/PlatformDependant.kt
4. Запустить нужный тест через функционал среды:
![test](https://jing.yandex-team.ru/files/stefjen/test.png)

### Создание нового теста
1. Создать новый класс в директории test/src/test/kotlin/smoke_tests, унаследоваться от BaseTest
2. Добавить тест в файл test/src/test/resources/testng.xml в Suite 1 или Suite 2 (лучше всего распределять равномерно, чтобы тесты отрабатывали быстрее) в `<classes>`:<br />
`<class name="smoke_tests.<Название_теста>" />`
3. Написать в новом классе метод с тестом, указать атрибут @Test