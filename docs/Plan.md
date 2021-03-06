   # План автоматизации:
____________________________________________________________________________________________

## Перечень автоматизированных сценариев:

#### Предусловия:
Запустить docker-контейнер с установленными MySQL(PostgreSQL) и Node.js, далее запустить файл aqa-shop.jar.


### *Happy path* 

### *1 Покупка тура  дебетовой картой. Поля "Владелец", "Срок действия", "CVC/CVV" заполнены валидными данными*.
*Ожидаемый результат* - покупка успешно произведена, вывод системного сообщения "Успешно!" Сохранение в СУБД информации о способе и успешности проведенного платежа.

*Валидные данные* :
* *Имя и фамилия владельца :*

  - буквами латинского алфавита. Минимальное количество - 2 буквы, максимальное - у каждого банка устанавливается свой максимум;
  - буквами латинского алфавита с двойной фамилие через дефис (KULIKOV-KOSTYUSHKO);
  - возможно написание символа точки при вынужденном сокращении (F. KULIKOV-KOSTYUSHKO);

  
* *Номер действующей карты из БД*


* *Срок действия карты :*

   - поле "месяц": от 1 до 12ти, но не ранее текущего месяца текущего года;
   - поле "год" : в формате XX, где XX - последние две цифры года, но не ранее текущего. (Срок действия каждым банком устанавливается индивидуально).
 


* *Поле "CVC/CVV" :*
   
   - состоит из трех цифр.
 
   
### *2 Покупка тура : выдача кредита по данным банковской карты. Поля "Владелец", "Срок действия", "CVC/CVV" заполнены валидными данными*. 

##### Ожидаемый результат и валидные данные идентичны со сценарием *"Покупка тура дебетовой картой"*. 


### *Sad path*

### *1 Покупка тура  дебетовой картой. Поля "Владелец", "Срок действия", "CVC/CVV" заполнены невалидными данными*.

*Ожидаемый результат* - покупка не производится, вывод системного сообщения об ошибке, которое указывает, какие поля были заполнены не верно. Сохранение в СУБД информации не происходит.

*Невалидные данные :*
* *Имя и фамилия владельца :*

  - буквами латинского алфавита. Количество символов - 1 буква;
  - буквами на кириллице;
  - спецсимволы(-,=,+,! и т.п.);
  - оставить поле не заполненным.

  
* *Номер карты из БД :*

   - невалидный формат номера карты (количество цифр меньше необходимого);
   - использование спецсимволов;
   - буквы алфавита на латыни;
   - буквы алфавита на кириллице;
   - оставить поле не заполненным.


* *Срок действия карты:*

  - ввод неверного формата в поле год (Х - одна цифра);
  - истекший срок действия;
  - использование спецсимволов;
  - буквы латинского алфавита;
  - буквы алфавита на кириллице;
  - оставить поле не заполненным.


* *Поле "месяц":* :

   - неверный формат, использование 1 цифры ;
   - оставить поле не заполненым.


* *Поле "CVC/CVV" :*

  - ввод неверного формата (2 цифры, 4 цифры) ;
  - ввод неверного формата (использование спецсимволов) ;
  - ввод неверного формата (буквы латинского алфавита) ;
  - ввод неверного формата (бувы алфавита на кириллице) ;
  - оставить поле не заполненным.

### *2 Покупка тура : выдача кредита по данным банковской карты. Поля "Владелец", "Срок действия", "CVC/CVV" заполнены невалидными данными*.

##### Ожидаемый результат и невалидные данные идентичны со сценарием *"Покупка тура дебетовой картой"*.



### *3 Отпрвка пустой формы*.




## Используемые инструменты :
* #### *IntelliJ IDEA* - интегрированная среда разработки программного обеспечения для многих языков программирования ;
* #### *Java 11* - объектно-ориентированный язык программирования для написания автотестов ;
* #### *JUnit5* - библиотека для модульного тестирования программного обеспечения на языке Java ;
* #### *Docker* - ПО для автоматизации развёртывания БД ;
* #### *MySQL, PostgreSQL* - свободная реляционная и свободно-объектно-реляционная система управления БД ;
* #### *Gradle* - система автоматической сборки ;
* #### *Faker* - библиотека, для генерации  случайных данных;
* #### * Selenide* - автоматизированная система тестирования ПО;
* #### *Allure* - это фреймворк для построения понятных и красивых отчётов автотестов.



## Риски :

* *Проблемы с подключением и настройкой тестируемого окружения и СУБД* ;
* *Неверная оценка трудозатрат* ;
* *Возможность возникновения трудностей с поиском локаторов на странице* ;


## Интервальная оценка с учётом рисков (в часах):

* Подготовительная работа - 8 часов ;
* Написание автотестов – 30 часов ;
* Подготовка отчетов 8 часов ;


## План сдачи работ:

* План автоматизации - 24.03.2022
* Авто - тесты 03.04.2022
* Написание отчетов о тестировании - 10.04.2022.








