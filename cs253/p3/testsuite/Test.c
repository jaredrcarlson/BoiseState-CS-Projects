/*
 * test.c
 *
 *  Created on: Feb 23, 2016
 *      Author: Jared Carlson <jaredcarlson@u.boisestate.edu>
 */

#include <stdio.h>
#include <stdlib.h>

#include "Test.h"

struct test * newTest(int id) {
    struct test *test;
    test = (struct test *) malloc(sizeof(struct test));
    setId(test,id);
    return test;
}

void setName(struct test* test, char name[]) {
	sprintf(test->name,name);
}

void setDesc(struct test* test, char desc[]) {
	sprintf(test->desc,desc);
}

void setId(struct test* test, int id) {
	test->id = id;
}


