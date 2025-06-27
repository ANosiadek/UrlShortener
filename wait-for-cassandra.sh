#!/bin/bash
hostport=$1
shift
until nc -z ${hostport//:/ }; do
  echo "Waiting for $hostport to be available..."
  sleep 2
done
echo "$hostport is available!"
exec "$@"