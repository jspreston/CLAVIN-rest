#!/bin/sh
curl -s \
     --data @resolve_test_data.json \
     --header "Content-Type: application/json" \
     http://localhost:9090/api/v0/resolve
