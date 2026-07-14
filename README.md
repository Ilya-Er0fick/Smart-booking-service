# Сервис расписаний и встреч

Этот проект представляет собой календарь с возможностью распределения сотрудников банка для встреч с клиентами, 
ориентируясь на свободное время, занятость и район работы сотрудников.

## Используемый стек технологий
 - Java 21.01.11
 - Java Swing (графический интерфейс)
 - PostgreSQL 15+ (БД сотрудников)
 - JDBC (подключение к БД)

## Установка
1. Клонируйте репозиторий:
```
   git clone https://github.com/Ilya-Er0fick/Smart-booking-service.git
   cd Smart-booking-service
```
2. Создайте таблицу в PostgreSQL и заполните тестовыми данными:
```
CREATE TABLE example_data_base (
    "ID"         INTEGER PRIMARY KEY,
    "Lastname"   TEXT,
    "Firstname"  TEXT,
    "Midlename"  TEXT,
    "Rating"     INTEGER,
    "District"   TEXT,
    "MaxClients" INTEGER
);
```
Тестовые данные находятся в databse/example-data-base.csv
3. Укажите параметры подключения в src/timetable/readinfDataBase.java:
```
String url      = "jdbc:postgresql://localhost:5432/postgres";
String username = "postgres";
String password = "ваш_пароль";
```
4. Скомпилируйте проект и запустите src/Main.java.

## Функционал
### Функционал для клиента
1. Выбор района из списка доступных
2. Визуальный календарь с навигацией по месяцам
3. Выбор свободного часа ( с 9 до 18 )
4. Автоматическое назначение лучшего сотрудника по рейтингу
5. Отмена бронирования из списка активных встреч

### Функционал для сотрудника
1. Входпо выбору имени из списка + пароль (по умолчанию == ID)
2. Просмотр персонального расписания встреч
3.  Отмена любой встречи из своего раписания
4. Счётчик встреч и общая статистика по системе

## Структура проекта

```
smart-booking-service/
|--src/
|   |-- Main.java
|   |-- gui/
|   | |-- window/
|   |   |   |-- starWindow.java          # Стартовое окно выбора роли
|   |   |   |-- windowOpen.java          # Окно клиента (календарь бронирования)
|   |   |   --- windowOpenAdmin.java     # Окно сотрудника (расписание + отмена)
|   |   |--visualcalendar/
|   |       |-- generationCalendar.java  # Логика сетки календаря
|   --- timetable/
|       |-- Employee.java                # Модель сотрудника из БД
|       |-- Booking.java                 # Модель одного бронирования
|       |-- BookingStorage.java          # Хранилище бронирований (Singleton)
|       |-- algorithmTimetable.java      # Алгоритмы фильтрации и сортировки
|       |-- dateTimetable.java           # Вспомогательные методы дат
|       |-- readingDataBase.java         # Чтение из PostgreSQL
|-- database/
    |--example-data-base.csv           # Тестовые данные сотрудников
```

## Построение базы данных сотрудников

### Таблица `example_data_base`

| Поле | Тип | Описание |
|------|-----|----------|
| ID | INTEGER | Уникальный идентификатор сотрудника |
| Lastname | TEXT | Фамилия |
| Firstname | TEXT | Имя |
| Midlename | TEXT | Отчество |
| Rating | INTEGER | Рейтинг (0–100) |
| District | TEXT | Район работы |
| MaxClients | INTEGER | Максимальное число клиентов |