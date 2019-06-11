# SpringMicroservicesInAction
root repo for the Spring Microservices In Action book by John Carnell / published by Manning 


This repo contains the services developed in the course of the book. Some notable differences are the use of Gradle instead of Maven as build tool, and the way the services are organized:
- each services is it's own project, with its own settings.gradle and is meant to be imported in Idea as a standalone project
- they are not setup as a multi project build as microservices should not have direct dependencies on other services, but at the same time I want to include the docker-compose file to bring up the whole cluster. In a real world application, each service would have its own repo. For the sake of this course, they will all live in this repo, but as standalone services
