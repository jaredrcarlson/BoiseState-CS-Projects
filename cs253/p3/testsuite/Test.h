/*
 * test.h
 *
 *  Created on: Feb 23, 2016
 *      Author: Jared Carlson <jaredcarlson@u.boisestate.edu>
 */

#ifndef __TEST_H_
#define __TEST_H_

struct test {
	char name[100];
    char desc[100];
    int id;
};

struct test * newTest(int id);
void setName(struct test*, char[]);
void setDesc(struct test*, char[]);
void setId(struct test*, int);

#endif /* __TEST_H_ */
