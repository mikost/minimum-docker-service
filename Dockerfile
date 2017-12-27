FROM alpine:3.7

COPY script.sh /
RUN chmod +x /script.sh

CMD nc -lk -p 4321 -e /script.sh
