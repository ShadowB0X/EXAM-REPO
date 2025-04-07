### INFO README SER BEDRE UD INDE I ET PROGRAMMERINGSSPROG ##

## UPDATE-FYI = ## 1. README File l.41-50 er ikke baseret på mit programs routes

Her kommer et gemmemgang af programmet

🗃️ JPA & DAO-lag

Projektet bruger JPA (med Hibernate) til at mappe Java-entiteter som SkiLesson og Instructor direkte til databasen via @Entity.

Alle CRUD-operationer håndteres gennem DAO-klasser, som implementerer et generisk IDAO<T> interface med metoder som getAll(), getById(), create(), update() og delete().

DAO’erne bruger EntityManager fra en fælles HibernateConfig, og konverterer mellem DTO og Entity.

Eksempel på route:
PUT /Ski/1/instructor/2 → opdaterer en lesson med ny instructor via ISkiLessonInstructorDAO.

Fordelene er klar arkitektur, genbrugelig kode og adskillelse af ansvar.

3.1 - 3.3.5 her opretter vi routes, impl en dao med interfaces, endpoints

🧩 REST Service with Javalin

Projektet anvender Javalin som web-framework til at opbygge et RESTful API. Javalin er et letvægtsframework til Java og Kotlin, som gør det nemt at definere HTTP-ruter og håndtere request/response-logik.

🧱 Struktur

REST-servicen er bygget op med en lagdelt arkitektur:

Controller-lag: Håndterer request/response (fx SkiLessonController)
DAO-lag: Håndterer databaseadgang via JPA og Hibernate (SkiLessonDAOImp)
DTO-lag: Bruges til at udveksle data mellem lagene og klienten (SkiLessonDTO)
Routes: Samler alle HTTP-endpoints ét sted (SkiLessonRoutes, RouteDefinitions)
Main: Starter serveren og binder routes (ApplicationConfig, Main.java)
🔗 Eksempler på endpoints

HTTP-metode	Endpoint	Funktion
GET	/api/Ski	Henter alle ski lessons
GET	/api/Ski/{id}	Henter én ski lesson
POST	/api/Ski	Opretter en ny ski lesson
PUT	/api/Ski/{id}	Opdaterer en eksisterende lesson
DELETE	/api/Ski/{id}	Sletter en ski lesson
PUT	/api/Ski/{lessonId}/instructor/{instructorId}	Tilføjer en instruktør til lesson
GET	/api/Ski/by-instructor/{instructorId}	Henter lessons for én instruktør
🧠 Hvorfor Javalin?

Enkel konfiguration
Hurtig opsætning af ruter med ctx
Integration med JPA/Hibernate via DAO-lag
God til eksamensprojekter, hvor man vil fokusere på arkitektur og funktionalitet
TESTS

Fejl jeg fik fra start, var port 7002, skulle sættes til 7070.
og der var et ekstra /. Fixet.
TEST 1 - GET

GET http://localhost:7070/api/Ski

HTTP/1.1 200 OK Date: Mon, 07 Apr 2025 09:56:25 GMT Content-Type: application/json Content-Length: 564

{ "data": [ { "id": 1, "name": "Beginner Basics", "price": 300.0, "latitude": 45.123, "longitude": 12.345, "starttime": [ 2025, 1, 10, 9, 0 ], "endtime": [ 2025, 1, 10, 11, 0 ], "level": "BEGINNER", "instructorId": 1 }, { "id": 2, "name": "Advanced Turns", "price": 450.0, "latitude": 45.456, "longitude": 12.678, "starttime": [ 2025, 1, 11, 14, 0 ], "endtime": [ 2025, 1, 11, 16, 0 ], "level": "ADVANCED", "instructorId": 2 }, { "id": 3, "name": "Intermediate Flow", "price": 400.0, "latitude": 45.222, "longitude": 12.999, "starttime": [ 2025, 1, 12, 12, 0 ], "endtime": [ 2025, 1, 12, 14, 0 ], "level": "INTERMEDIATE", "instructorId": 1 } ], "status": "success" } Response file saved.

2025-04-07T115625.200.json
Response code: 200 (OK); Time: 346ms (346 ms); Content length: 564 bytes (564 B)

TEST 2 - POST

POST http://localhost:7070/api/Ski

HTTP/1.1 201 Created Date: Mon, 07 Apr 2025 09:59:38 GMT Content-Type: application/json Content-Length: 50

{ "status": "created", "lesson": "Backcountry Basics" } Response file saved.

2025-04-07T115938.201.json
Response code: 201 (Created); Time: 114ms (114 ms); Content length: 50 bytes (50 B)

TEST 3 - GET

GET http://localhost:7070/api/Ski/1

HTTP/1.1 200 OK Date: Mon, 07 Apr 2025 10:01:10 GMT Content-Type: application/json Content-Length: 203

{ "data": { "id": 1, "name": "Beginner Basics", "price": 300.0, "latitude": 45.123, "longitude": 12.345, "starttime": [ 2025, 1, 10, 9, 0 ], "endtime": [ 2025, 1, 10, 11, 0 ], "level": "BEGINNER", "instructorId": 1 }, "status": "success" } Response file saved.

2025-04-07T120110.200.json
Response code: 200 (OK); Time: 41ms (41 ms); Content length: 203 bytes (203 B)

TEST 4 - PUT

PUT http://localhost:7070/api/Ski/1

HTTP/1.1 200 OK Date: Mon, 07 Apr 2025 10:01:44 GMT Content-Type: application/json Content-Length: 27

{ "id": 1, "status": "updated" } Response file saved.

2025-04-07T120144.200.json
Response code: 200 (OK); Time: 121ms (121 ms); Content length: 27 bytes (27 B)

TEST 5 - DELETE

DELETE http://localhost:7070/api/Ski/1

HTTP/1.1 204 No Content Date: Mon, 07 Apr 2025 10:02:53 GMT Content-Type: application/json;charset=utf-8

) ###Lukker den lige her. så den ikke siger, at den skal lukkes

Response code: 204 (No Content); Time: 51ms (51 ms); Content length: 0 bytes (0 B)

TEST 6 - PUT

PUT http://localhost:7070/api/Ski/2/instructor/1

HTTP/1.1 200 OK Date: Mon, 07 Apr 2025 10:03:57 GMT Content-Type: application/json Content-Length: 42

{ "status": "Instructor added to SkiLesson" } Response file saved.

2025-04-07T120357.200.json
Response code: 200 (OK); Time: 47ms (47 ms); Content length: 42 bytes (42 B)

TEST 7 - GET

GET http://localhost:7070/api/Ski/by-instructor/1

HTTP/1.1 200 OK Date: Mon, 07 Apr 2025 10:04:09 GMT Content-Type: application/json Content-Length: 571

{ "lessons": [ { "id": 2, "name": "Advanced Turns", "price": 450.0, "latitude": 45.456, "longitude": 12.678, "starttime": [ 2025, 1, 11, 14, 0 ], "endtime": [ 2025, 1, 11, 16, 0 ], "level": "ADVANCED", "instructorId": 1 }, { "id": 3, "name": "Intermediate Flow", "price": 400.0, "latitude": 45.222, "longitude": 12.999, "starttime": [ 2025, 1, 12, 12, 0 ], "endtime": [ 2025, 1, 12, 14, 0 ], "level": "INTERMEDIATE", "instructorId": 1 }, { "id": 4, "name": "Backcountry Basics", "price": 400.0, "latitude": 45.123, "longitude": 12.321, "starttime": [ 2025, 1, 20, 10, 0 ], "endtime": [ 2025, 1, 20, 12, 0 ], "level": "BEGINNER", "instructorId": 1 } ], "status": "success" } Response file saved.

2025-04-07T120409.200.json
Response code: 200 (OK); Time: 104ms (104 ms); Content length: 571 bytes (571 B)

TEST 8-9 er non eksisterende

❌ REST Error Handling

Fejl håndteres globalt i Javalin ved brug af en ApiException klasse.
Hvis en ski lesson ikke findes (ved fx GET eller DELETE), returneres en struktureret JSON-fejl med statuskode og besked.

Eksempel:

{
  "error": "Ski lesson ikke fundet"
}

Jeg kunne specifickere præcis hvad der er galt, men det har jeg ikke rigtig tid til. og vil gå videre til andet emne. 
Eksempel'
"error": "Ski lesson not found with ID: 99",
  "status": 404
så det er mere præcist fejlhåndteringer. 
🔁 Streams og Aggregation

Java Streams anvendes til at filtrere og aggregere ski lessons på backend.

🧠 Filtrering: Ski lessons efter niveau

Endpoint:
GET /api/Ski/level/BEGINNER
Returnerer alle ski lessons hvor niveauet matcher fx BEGINNER, INTERMEDIATE osv.

💰 Aggregation: Total pris pr. instruktør

Endpoint:
GET /api/Ski/instructor-price-sum
Returnerer en liste med hver instruktørs ID og den samlede pris for deres ski lessons:

[
  {
    "instructorId": 1,
    "totalPrice": 1400.0
  },
  {
    "instructorId": 2,
    "totalPrice": 2100.0
  }
]
Det var hvad jeg havde forventet at få udfra dette, men det endte med

{
"error": "Kunne ikke hente ski lesson"
}
da jeg ikke havde meget tid rykket jeg videre. prøvet debug men menu er for lang og har for kort tid til at fixe det.

GET ski lessons by level (Beginner)

GET http://localhost:7070/api/Ski/level/beginner Accept: application/json GET http://localhost:7070/api/Ski/level/beginner

HTTP/1.1 200 OK Date: Mon, 07 Apr 2025 11:31:50 GMT Content-Type: application/json Content-Length: 213

{ "status": "success", "filtered": [ { "id": 4, "name": "Backcountry Basics", "price": 400.0, "latitude": 45.123, "longitude": 12.321, "starttime": [ 2025, 1, 20, 10, 0 ], "endtime": [ 2025, 1, 20, 12, 0 ], "level": "BEGINNER", "instructorId": 1 } ] } Response file saved.

2025-04-07T133150.200.json
Response code: 200 (OK); Time: 28ms (28 ms); Content length: 213 bytes (213 B)

GET total instruction duration for a level

GET http://localhost:7070/api/Ski/instructions/intermediate/total Accept: application/json GET http://localhost:7070/api/Ski/instructions/intermediate/total

HTTP/1.1 500 Server Error Date: Mon, 07 Apr 2025 11:32:00 GMT Content-Type: application/json Content-Length: 40

{ "error": "Failed to calculate duration" } Response file saved.

2025-04-07T133201.500.json
Response code: 500 (Server Error); Time: 95ms (95 ms); Content length: 40 bytes (40 B) Fejler. har nogle håndteringer der er forkert. men ingen tid til at ændre det.

lavet TEST, men mangler tid, fixet lidt, men der er stadig fejl.
