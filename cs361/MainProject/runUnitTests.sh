#!/bin/bash
BLD_DIR="unit-tests_$(date +%Y-%m-%d_%H-%M-%S)"

javac MainProject1.java

mkdir -p ${BLD_DIR}

rm -f ./unit-test_*~

for file in ./unit-test_* 

do
    in="${BLD_DIR}/${file}-input.txt"
    exp="${BLD_DIR}/${file}-expected.txt"
    act="${BLD_DIR}/${file}-actual.txt"
    report="${BLD_DIR}/${file}-REPORT.txt"
    line1=$(awk 'NR==1 {print; exit}' $file)
	echo $line1 > $in
    awk -F "\"*,\"*" '{if (NR!=1) print $1}' $file >> $in
    awk -F "\"*,\"*" '{if (NR!=1) print $2}' $file > $exp
    java MainProject1 $in > $act
    java MainProject1 $in 1 > $report
    cmp --silent ${act} ${exp} && echo "[${file}]: PASSED" || (echo "[${file}]: FAILED" && java MainProject1 $in 1)
done
