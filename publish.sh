#!/bin/bash
module=(
"database"
)

for element in ${module[*]}
do
  echo $element;
  ./gradlew -Dshd=1 -Pshp=1 :$element:clean
  ./gradlew -Dshd=1 -Pshp=1 :$element:build
  ./gradlew -Dshd=1 -Pshp=1 :$element:uploadArchives
done
