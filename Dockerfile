FROM php:7.4-apache

LABEL maintainer = "Jack Ruszkowski"

#Copy our public folder to the working directory 

COPY app /srv/app

COPY docker/apache/vhost.conf /etc/apache2/sites-available/000-default.conf