FROM centos:centos7
RUN yum update -y
RUN yum install httpd -y
RUN yum systemctl start httpd
WORKDIR /var/www/html
ADD . /var/www/html
EXPOSE 80
ENTRYPOINT ["/usr/sbin/httpd"]
CMD ["-D", "FOREGROUND"]

