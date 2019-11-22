FROM ubuntu
RUN apt-get update -y
RUN apt-get install -y apache2
EXPOSE 80
CMD ["apache2ctl", "-D", "FOREGROUND"]
