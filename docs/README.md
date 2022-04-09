## Инструкция по запуску тестов :

### *Должно быть настроено и запущено :*
####  *1 Intellij IDEA ;*
####  *2 Docker .*

### *Запуск SUT :*
 1. *Склонировать проект [Diplom](https://github.com/Azize87/Diplom.git) и открыть его в Intellij IDEA;*
 2. *В терминале Intellij IDEA разворачиваем необходимый контейнер командой `docker-compose up` (настройки для запуска контейнера прописаны в `docker-compose.yml`).*
 3. Запуск SUT с поддержкой БД:

 *MySQL: в новом окне терминала запустить SUT командой `java -jar artifacts/aqa-shop.jar --spring.profiles.active=mysql`*
 
*PostgreSQL: в новом окне терминала запустить SUT командой `java -jar artifacts/aqa-shop.jar --spring.profiles.active=postgresql`*

### *Запуск тестов :*
 Для БД MySQL: в новом окне терминала запустить тесты командой `./gradlew test -DdbUrl=jdbc:mysql://localhost:3306/db`
 
 Для БД PostgreSQL: в новом окне терминала запустить тесты командой `./gradlew test -DdbUrl=jdbc:postgresql://localhost:5432/db`

