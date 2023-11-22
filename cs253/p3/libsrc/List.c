#include <stdio.h>
#include <stdlib.h>
#include "List.h"

// Provided
struct list * createList(int (*equals)(const void *,const void *),
                         char * (*toString)(const void *),
                         void (*freeObject)(void *)) {
    struct list *list;
    list = (struct list *) malloc(sizeof(struct list));
    list->size = 0;
    list->head = NULL;
    list->tail = NULL;
    list->equals = equals;
    list->toString = toString;
    list->freeObject = freeObject;
    return list;
}

void freeList(struct list *list) {
	if (!list) {
		return;
	}
	else {
		struct node* currNode = list->head;
		struct node* nextNode;
		while(currNode) {
			nextNode = currNode->next;
			freeNode(currNode,list->freeObject);
			currNode = nextNode;
		}
		free(list);
	}
}

int getSize(const struct list *list) {
	if(!list) {
		return 0; // A NULL list has size of Zero
    }
    else {
    	return list->size;
    }
}

int isEmpty(const struct list *list) {
	if(!list) {
		return 1;
	}
	else {
		return list->size == 0;
	}
}

// Provided
void addAtFront(struct list *list, struct node *node) {
    if (!list || !node) {
    	return;
    }
    else {
    	if(isEmpty(list)) {
    		list->head = list->tail = node;
    	}
    	else {
    		node->next = list->head;
    		list->head->prev = node;
    		list->head = node;
    	}
    	list->size++;
    }
}

void addAtRear(struct list *list, struct node *node) {
	if (!list|| !node) {
		return;
	}
	else {
		if(isEmpty(list)) {
			list->head = list->tail = node;
		}
		else {
			node->prev = list->tail;
			list->tail->next = node;
			list->tail = node;
		}
		list->size++;
	}
}

struct node* removeFront(struct list *list) {
	if (!list || isEmpty(list)) {
		return NULL;
	}
	else {
		struct node* frontNode = list->head;
		if (!frontNode->next) {
			list->head = list->tail = NULL;
		}
		else {
			list->head = frontNode->next;
			list->head->prev = NULL;
			frontNode->next = NULL;
		}
		list->size--;
		return frontNode;
	}
}

struct node* removeRear(struct list *list) {
	if (!list || isEmpty(list)) {
		return NULL;
	}
	else {
		struct node* rearNode = list->tail;
		if (!rearNode->prev) {
			list->head = list->tail = NULL;
		}
		else {
			list->tail = rearNode->prev;
			list->tail->next = NULL;
			rearNode->prev = NULL;
		}
		list->size--;
		return rearNode;
	}
}

struct node* removeNode(struct list *list, struct node *node) {
    if (!node || isEmpty(list)) {
    	return NULL;
    }
    else {
    	if(node == list->head) {
    		return removeFront(list);
    	}
    	else if(node == list->tail) {
    		return removeRear(list);
    	}
    	else {
    		node->prev->next = node->next;
    		node->next->prev = node->prev;
    		node->prev = node->next = NULL;
    		list->size--;
    		return node;
    	}
    }
}

struct node* search(const struct list *list, const void *obj) {
	if (!list) {
		return NULL;
	}
	else {
		struct node* currentNode = list->head;
		while(currentNode != NULL) {
			if((list->equals(currentNode->obj,obj))) {
				return currentNode;
			}
			currentNode = currentNode->next;
		}
		return NULL;
	}
}

void reverseList(struct list *list) {
	if (list && !isEmpty(list)) {
		struct node* currNode = list->tail;
		list->head = currNode;
		struct node* oldPrev = currNode->prev;
		currNode->prev = NULL;
		while(oldPrev) {
			struct node* oldPrevPrev = oldPrev->prev;
			currNode->next = oldPrev;
			oldPrev->prev = currNode;
			currNode = oldPrev;
			oldPrev = oldPrevPrev;
		}
		currNode->next = NULL;
		list->tail = currNode;
	}
}

void printList(const struct list *list) {
    if (!list) {
    	printf("List is NULL\n");
    	return; //list was null!!
    }
    else if(list->size == 0) {
    	printf("EMPTY\n");
    	return; //list is EMPTY
    }
    int count = 0;
    char *output;
    struct node *temp = list->head;
    while (temp) {
        output = list->toString(temp->obj);
        printf(" %s -->",output);
        free(output);
        temp = temp->next;
        count++;
        if ((count % 6) == 0)
            printf("\n");
    }
    printf(" NULL \n");
}
