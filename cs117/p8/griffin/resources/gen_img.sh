#!/bin/bash
rm *.bmp
cp ../finch.bmp finch.bmp
for d in `seq 0 360`
do
    echo "converting $d"
    convert finch.bmp -background black -distort SRT -$d finch_$d.bmp
done
