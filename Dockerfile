FROM centos:latest
RUN yum update -y
RUN yum install httpd httpd-tools -y
RUN yum install epel-release -y \
    && yum update -y \
    && yum install htop -y \
    && yum install vim -y
WORKDIR /var/www/html
ADD . /var/www/html
EXPOSE 80
ENTRYPOINT ["/usr/sbin/httpd"]
CMD ["-D", "FOREGROUND"]

