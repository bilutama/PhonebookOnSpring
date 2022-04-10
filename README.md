# README #

Проект - телефонная книга, реализованная на технологиях:

Клиентская часть: HTML, CSS, JS, Vue.js, Bootstrap
Серверная часть: spring-framework
Связь БД и Java: hibernate
БД: MySQL установленная на компьютере, схема phonebook, логин root, пароль Admin123!
Сборка: spring + maven
Запуск: spring + tomcat

Функции проекта:
- отображение существующих контактов
- добавление новых контактов
- удаление контактов (без удаления записей из БД)
- изменение параметра "важность" существующих контактов

Что нужно добавить в проект:
- Таблицу звонков + API для получения звонков по контакту

Запуск проекта
- Run PhoneBookSpringApplication - команда собирает проект и запускает PhonebookSpringApplication main
- Debug PhoneBookSpringApplication - команда собирает проект в режиме дебаг и запускает PhoneBookSpringApplication main

Отладка серверной части:
1) запуск проекта в режиме дебаг
2) использование Postman

Отладка клиентской части:
1) запуск в Chrome
2) инструменты разработчика

Реализованные методы API:
GET {host}/phoneBook/rcp/api/v1/getContacts - получения списка контактов
GET {host}/phoneBook/rcp/api/v1/getContacts/term - получения списка контактов содержащих term в имени, фамилии или номере телефона

POST {host}//phoneBook/rcp/api/v1/addContact - добавление нового контакта в формате
Тело запроса:
{
 "firstName": "Имя",
 "lastName": "Фамилия",
 "phone": "Телефон"
}
