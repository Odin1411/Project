#! usr/bin/bash
gunzip overlaps.m4.gz #unzip
awk '{if (($7- $6 > 1000) && ($11 -$10 > 1000 )) print $1}' overlaps.m4 | wc -l
#counts edges in unziped file
head -10 overlaps.m4 > file0.txt
head -100 > file1.txt
head -1000 > file2.txt
#to create test files