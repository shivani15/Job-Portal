# Job Portal

Basic application service to support the following functionalites:
* Creating a new Company
* Searching for Companies using name
* Creating a new Job Opening
* Listing Existing Job Opening
* Filtering Job Openings based on company name, country, city, title and seniority level.

## Tech Stack
* Service - Java, mvn, Spring boot, Hibernate & JPA, Power Mockito & junit for unit testing
* Database - Postgress

## Setup

* cd in the root directory
* Run the following command to build the docker for Application and Databade(postgres)
    ```
    make build
    ```
  This will setup the docker with all the dependencies for running the service. 
* Run the following command to load the inital data in the database##
    ```
    make bootstrap-data
    ```
  This will load the initial data from 'database⁩/⁨bootstrap_data/job_portal_db_dump.dump' prepared from the local database populated using the 'Company Data for Backend Assignment - data_assignment.xlsx' and 'database⁩/⁨bootstrap_data/companybootstrap.py'
  
* Run the following command to run the server
    ```
    make run
    ```
  The above command will run the docker stack with Application and Postgres.\
Application Service is available on port 9090 on host machine mapped to 8080 on docker.\
Postgress Service is available on port 9999 on host machine mapped to 5432 on docker.

* Run the following command to stop the docker stack
    ```
    make stop
    ```
## Resources
* Use 'Postman Collection/Job_portal.postman_collection.json' for basic api calls using postman 
