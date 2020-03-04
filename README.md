# Vehicle-Application
Author: Akash Akki
Github link: https://github.com/Akash-Akki/Vehicle-Application

Instructions to Run Vehicle Application
  

  Run Jar File VehicleApplication-0.0.1-SNAPSHOT using below command

      java -jar VehicleApplication-0.0.1-SNAPSHOT.jar com.mitchell_international.VehicleApplication.VehicleApplication
	  
	  Or 
	  
	  
	  
  
    
  
  Below are the input and output when ran through Postman
  
  1. 
       a. GET /vehicles  https://localhost:8080/vehicles
  
    Sample Output:
	[
     {
        "id": 1,
        "year": 2017,
        "make": "MerecdesBenz",
        "model": "SClass"
    },
    {
        "id": 2,
        "year": 2019,
        "make": "Audi",
        "model": "Q7"
    },
    {
        "id": 3,
        "year": 2020,
        "make": "Toyota",
        "model": "Etios"
    }
]

  b.  GET /vehicles https://localhost:8080/vehicles?make=MerecdesBenz
    Sample Output
	  [
    {
        "id": 1,
        "year": 2017,
        "make": "MerecdesBenz",
        "model": "SClass"
    }
]

  c.  GET /vehicles https://localhost:8080/vehicles?model=SClass
     
	 Sample Ouput
       [
    {
        "id": 1,
        "year": 2017,
        "make": "MerecdesBenz",
        "model": "SClass"
    }
]

2.   GET /vehicles https://localhost:8080/vehicles/1
   Sample Output
   {
    "id": 1,
    "year": 2017,
    "make": "MerecdesBenz",
    "model": "SClass"
}

3. POST/vehicles  http://localhost:8080/vehicles  
   Id is Auto generated
   Sample Input:
   {
   
    
    "year": 2010,
    "model": "Etios",
    "make": "toyota"
   }
   
   
    Sample Output:
	{
   
     "id":4
    "year": 2010,
    "model": "Etios",
    "make": "toyota"
   }

4.   
    PUT /vehicles http://localhost:8080/vehicles

   Sample Input:
	{
   
     "id":2
    "year": 2010,
    "model": "Etios",
    "make": "toyota"
   }
    
	Sample Output
	  Updated Json
	{
   
     "id":2
    "year": 2010,
    "model": "Etios",
    "make": "toyota"
   }
   
 
  
5. DELETE /vehicles  http://localhost:8080/vehicles/1

   Sample Ouput :No Content
