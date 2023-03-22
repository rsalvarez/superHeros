docker stop superhero
docker rm -fv superhero
docker run -d -it -p 8080:8080 --name superhero superhero
