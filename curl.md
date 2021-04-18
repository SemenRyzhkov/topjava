filter by date: 
http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=&endDate=2020-01-30&endTime=
filter by time:
http://localhost:8080/topjava/rest/meals/filter?endTime=14:00&startDate=&endDate=&startTime=09:00
filter by date and time:
http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=09:00&endDate=2020-01-30&endTime=14:00
get all meals:
http://localhost:8080/topjava/rest/meals
create meal:
POST http://localhost:8080/topjava/rest/meals
   {
        "dateTime": "2021-01-31T13:00:00",
        "description": "Обед",
        "calories": 1000
    }
    
update meal:
PUT http://localhost:8080/topjava/rest/meals/100011
   {
        "dateTime": "2021-01-31T13:00:00",
        "description": "Обед11",
        "calories": 1000
    }
delete meal:
DELETE http://localhost:8080/topjava/rest/meals/100011