#!/bin/sh

body="<!DOCTYPE html><html><head><title>This is the title</title></head><body>Time is now: $(date).</body></html>"

length=$(expr length "$body")

cat <<EOF
HTTP/1.1 200 OK
Content-Length: $length
Content-Type: text/html
Connection: Closed

$body
EOF
