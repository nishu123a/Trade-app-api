#!/bin/sh
set -e

CERT="/etc/letsencrypt/live/api.tradeapp.nishusinha.in/fullchain.pem"
CONF="/etc/nginx/conf.d/tradeapp.conf"

if [ -f "$CERT" ]; then
    cp /etc/nginx/templates/tradeapp.production.conf "$CONF"
else
    cp /etc/nginx/templates/tradeapp.bootstrap.conf "$CONF"
fi

exec "$@"
