#!/bin/bash

### Check for valid command line argument
if [ "$1" = "" ]; then
    echo "usage: $0 <output file>"
    echo "   output file - the file to save the grades in"
    exit 0;
fi


### Definitions, Variables
GRADE_OUTFILE=$1
WF_INFILE=./sample-input.txt
WF_OUTFILE=./wf_output.tmp
WF_USAGE_MESSAGE=./wf_usage_message.tmp
MSG_GRADE_PASS="HW5:PASS"
MSG_GRADE_FAIL="HW5:FAIL"
MSG_WF_OPTION1_FAIL="Reason: The command \"wf --self-organized-list <textfile>\", did not redirect output."
MSG_WF_OPTION2_FAIL="Reason: The command \"wf --std-list <textfile>\", did not redirect output."
MSG_OUTFILE_CONTENTS_FAIL="Reason: wf output file contained the wf usage message - Did you supply proper arguments for wf?"
TEMP_OUTFILE=./outfile.tmp
GREP_RESULT=./grep_result.tmp
local FAIL_CODE=0


### Build Executable
make


### Run Executable (input) (output-redirect) - Check exit status 
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib

# Execute wf option 1
wf --self-organized-list $WF_INFILE > $WF_OUTFILE
# (Check 1) - Make sure output file was created
if [ ! -e $WF_OUTFILE ]; then
    # option 1 did NOT create the output file
    FAIL_CODE=1
else
    # make copy of output file (used for Check 2)
    cp $WF_OUTFILE $TEMP_OUTFILE
fi

# Execute wf option 2
wf --std-list $WF_INFILE >> $WF_OUTFILE
# (Check 2) - Make sure output file has more content appended to it
cmp -s $WF_OUTFILE $TEMP_OUTFILE && FAIL_CODE=2


### Verify that the output file does not contain the wf usage message
wf "invalid_argument" > $WF_USAGE_MESSAGE

# (Check 3) - Using grep to check if the wf output file contains the usage message 
grep -F -f $WF_USAGE_MESSAGE $WF_OUTFILE > $GREP_RESULT 
if [ -s $GREP_RESULT ]
then
    # grep result file is NOT empty - output file contains usage message
    FAIL_CODE=3
fi


### Cleanup
make clean


### Display and Write Results
echo "-----------------------------------------------------------------------------------"
echo " Results have been saved to file: $GRADE_OUTFILE"
echo "-----------------------------------------------------------------------------------"
if [ $FAIL_CODE ]; then
    echo $MSG_GRADE_FAIL | tee $GRADE_OUTFILE
fi
case "$FAIL_CODE" in
    1)
    echo $MSG_WF_OPTION1_FAIL | tee -a $GRADE_OUTFILE
    ;;
    2)
    echo $MSG_WF_OPTION2_FAIL | tee -a $GRADE_OUTFILE
    ;;
    3)
    echo $MSG_OUTFILE_CONTENTS_FAIL | tee -a $GRADE_OUTFILE
    ;;
    *)
    echo $MSG_GRADE_PASS | tee $GRADE_OUTFILE
    ;;
esac
