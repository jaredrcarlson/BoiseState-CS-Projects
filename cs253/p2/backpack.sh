#!/bin/bash
if [ "$1" = "" ];then
  echo "usage: $0 <output file>"
  echo "   output file - the file to save the grades in"
  exit 0;
fi
dest="$1"

#Clean up everything
make clean

#Generate the students assignment
make

#Make sure the program built
if [ ! -e "homework" ];then
  echo "HW2: FAIL reason: Did not build" >> $dest
  exit 1 # fail the build
fi

#Students program executable name
EXE=homework

#Backpacks program executable name
TESTER=grader

#Data file with all test values
TESTDATA=data

#Generate the expected file
while IFS=' ' read -r temp
do
    ./$TESTER  $temp >> expected
done < $TESTDATA;

#Run the students file with the same data
while IFS=' ' read -r temp
do
    ./$EXE $temp  >> actual
done < $TESTDATA;

#Compare the results. If the program is not correct
#diff will return a non-zero value that will cause
#the build to fail.
diff -q  expected actual

if [ $? -eq 0 ];then
    echo "diff ok"
else
    echo "HW2: FAIL" >> $dest
    exit 1
fi

ERROR=$({ valgrind --leak-check=full --quiet ./homework -i 1234 > /dev/null; } 2>&1)
if [[ ! -z "$ERROR" ]]
then
    echo "HW2: FAIL - valgrind" >> $dest
else
    echo "HW2: PASS" >> $dest
fi


make clean
rm -f expected
rm -f actual
