#!/bin/bash
if [ "$1" = "" ];then
  echo "usage: $0 <output file>"
  echo "   output file - the file to save the grades in"
  exit 0;
fi
dest="$1"

#Generate the students assignment
make

#Make sure the program built
if [ ! -e "hello" ];then
  echo "HW4: FAIL reason: Did not build" >> $dest
  exit 1 # fail the build
fi

make clean
if [ "$?" != 0 ];then
        #We failed
        echo "HW4: FAIL make clean" >> $dest
        exit 0
fi


make run
if [ "$?" != 0 ];then
        #We failed
        echo "HW4: FAIL make run" >> $dest
        exit 0
fi

make clean
echo "HW4: PASS" >> $dest
