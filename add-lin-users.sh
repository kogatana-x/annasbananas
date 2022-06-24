#!/bin/bash

sed '1d' ad-users.csv

while IFS=, read -r user pass; do
	su -c "useradd $user -s /bin/bash -m"
	echo "$user:$pass" | chpasswd
  echo "Added user: $user ."
done < ad-users.csv
