#!/bin/bash
echo ""; echo "---Starting Function Ptr Example Program---"; echo ""

exe=hello
prog=example-fptr.c
if [ -a "$exe" ];then
	/bin/rm -f $exe
fi
gcc -Wall -Werror $prog -o $exe && ./$exe
/bin/rm -f $exe

echo ""; echo "---Ending Function Ptr Example Program---"; echo ""
