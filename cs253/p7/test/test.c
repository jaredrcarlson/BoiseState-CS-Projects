#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include "../include/ring.h"

#define NUMTHREADS 4
#define NUMLOGS 10
#define NUMERRORCODES 10

char *log_messages[NUMERRORCODES] = {"Success: Code 0","Error: Code 1","Error: Code 2","Error: Code 3",
                                     "Error: Code 4","Error: Code 5","Error: Code 6",
                                     "Error: Code 7","Error: Code 8","Error: Code 9"};
void *threadExec();
pthread_t threads [NUMTHREADS];
int r,i;

int main(void) {
    signal(SIGALRM,checkpoint_handler);
    alarm(alarm_interval);
    init_buffer();

    for(i=0; i<NUMTHREADS; i++) {
        pthread_create(&threads[i],NULL,threadExec,NULL);
        printf("TID: %lu - Created Thread [%u]\n",pthread_self(),i);
    }
    
    //printf("TID: %lu - Sleeping for %d seconds to let signals finish\n",pthread_self(),alarm_interval +1);
    sleep(alarm_interval +1);
     
    for(i=0; i<NUMTHREADS; i++) {
        pthread_join(threads[i],NULL);
        printf("TID: %lu - Terminated Thread [%u]\n",pthread_self(),i);
    }

    exit(EXIT_SUCCESS);
}

void *threadExec() {
    printf("Entering TID: %lu\n\n",pthread_self());
    int i;
    char message[MAX_STRING_LENGTH];
    for(i=0; i<NUMLOGS; i++) {
        r = rand() % NUMERRORCODES;
        snprintf(message,sizeof(message),"TID: %lu - %s",pthread_self(),log_messages[r]);
        log_msg(message);
    }
    printf("Exiting TID: %lu\n\n",pthread_self());
    pthread_exit(NULL);
}
