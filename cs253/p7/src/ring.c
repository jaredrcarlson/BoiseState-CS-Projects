#include <string.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h> 
#include <pthread.h>
#include "ring.h"

static struct {
    int curr;
    pthread_mutex_t mutex;
    char log[MAX_LOG_ENTRY][MAX_STRING_LENGTH];
} buff;

void init_buffer() {
    printf("TID: %lu - Initializing the ring buffer\n",pthread_self());
    pthread_mutex_init(&(buff.mutex),NULL);
    printf("TID: %lu - Buffer Mutex has been initialized\n",pthread_self());
    int i;
    for(i =0; i<MAX_LOG_ENTRY; i++) {
        buff.log[i][0]='\0';
    }
}

void log_msg(char *entry) {
    pthread_mutex_lock(&(buff.mutex));
    printf("TID: %lu - Mutex: Locked\n",pthread_self());
    printf("TID: %lu - Adding log entry into buffer\n",pthread_self());
    int idx = buff.curr % MAX_LOG_ENTRY;
    strncpy(buff.log[idx],entry,MAX_STRING_LENGTH);
    buff.log[idx][MAX_STRING_LENGTH-1]='\0';
    buff.curr++;
    pthread_mutex_unlock(&(buff.mutex));
    printf("TID: %lu - Mutex: Unlocked\n",pthread_self());
}

static void dump_buffer() {
    FILE *fout = fopen(log_name, "w+");
    int i;
    for(i =0; i<MAX_LOG_ENTRY; i++) {
        fprintf(fout,"log %d: %s\n",i, buff.log[i]);
    }
    fclose(fout);
    printf("TID: %lu - Buffer has been written to logfile\n",pthread_self());
}

void checkpoint_handler(int signal) {
    pthread_mutex_lock(&(buff.mutex));
    printf("TID: %lu - Mutex: Locked\n",pthread_self());
    printf("TID: %lu - Dumping Buffer\n",pthread_self());
    dump_buffer();
    alarm(alarm_interval);
    pthread_mutex_unlock(&(buff.mutex));
    printf("TID: %lu - Mutex: Unlocked\n",pthread_self());
}
