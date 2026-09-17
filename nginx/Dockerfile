FROM nginx:latest

RUN rm /etc/nginx/conf.d/default.conf

COPY ./nginx/conf/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/conf/templates/tradeapp.bootstrap.conf /etc/nginx/templates/tradeapp.bootstrap.conf
COPY ./nginx/conf/templates/tradeapp.production.conf /etc/nginx/templates/tradeapp.production.conf
COPY ./nginx/docker-entrypoint.sh /docker-entrypoint.sh

RUN mkdir -p /var/www/certbot /etc/letsencrypt/live /etc/letsencrypt/archive \
    && chmod +x /docker-entrypoint.sh

EXPOSE 80 443

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
