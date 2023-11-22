#include "hidapi.h"
#include "common.hpp"
#include <wchar.h>
#include <locale.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <boost/asio.hpp>

using boost::asio::ip::tcp;

struct hid_device_ {
    const char *handle;
    char host[MAX_HOST_NAME];
    char port[MAX_PORT];
	boost::asio::io_service* io_service;
    tcp::socket* socket;
    unsigned char res[MESSAGE];
    int len;
};


/**
 * Sets the prompt for griffin.
 * Uses the environment variable GRIFFIN_PORT and GRIFFIN_HOST
 */
static void set_port_and_host(hid_device *dev)
{
    char *port = getenv("GRIFFIN_PORT");
    if (port && isdigit(*port)) {
        strcpy(dev->port,port);
    }else{
        char p[MAX_PORT];
        sprintf(p,"%d",PORT);
        strcpy(dev->port,p);
    }
    char *host = getenv("GRIFFIN_HOST");
    if(host){
        strcpy(dev->host,host);
    }else{
        strcpy(dev->host,DEFAULT_HOST);
    }
}

static void connect_to_emulator(hid_device *dev)
{
	dev->io_service = new boost::asio::io_service;
    tcp::resolver resolver(*(dev->io_service));
    tcp::resolver::query query(tcp::v4(),dev->host,dev->port);
    tcp::resolver::iterator iterator = resolver.resolve(query);
    dev->socket = new tcp::socket(*(dev->io_service));
	
	try{
		 boost::asio::connect(*(dev->socket),iterator);
	}catch(  std::exception &e){
		std::cerr<< e.what() << std::endl;
		exit(TRUE);
	}
   
}

static int send_data(hid_device *dev, const unsigned char *buff, int length)
{
	try{
		boost::asio::write(*(dev->socket),boost::asio::buffer(buff,length));
	}catch(  std::exception &e){
		std::cerr<< e.what() << std::endl;
		exit(TRUE);
	}
    return length;
}

static int recv_data(hid_device *dev, unsigned char *buff, int length)
{
	
	size_t bytes_recv =0;
	try{
		bytes_recv = boost::asio::read(*(dev->socket),boost::asio::buffer(buff,length));;
	}catch(  std::exception &e){
		std::cerr<< e.what() << std::endl;
		exit(TRUE);
	}
    if (bytes_recv == -1) {
        perror("Receive data failed (is the emulator running?)");
        exit(TRUE);
    }
    return bytes_recv;
}

static hid_device *new_hid_device(void)
{
    hid_device* dev = (hid_device*)calloc(1, sizeof(hid_device));
    dev->handle = "griffin";
    set_port_and_host(dev);
    connect_to_emulator(dev);
    return dev;
}

static void free_hid_device(hid_device *dev)
{
    if (!dev) {
        return;
    }
    delete (dev->socket);
    free(dev);
}

int hid_init(void)
{
    return FALSE;
}

int hid_exit(void)
{
    return FALSE;
}

struct hid_device_info *hid_enumerate(unsigned short vendor_id, unsigned short product_id)
{
    struct hid_device_info* root = (hid_device_info*) malloc(sizeof(struct hid_device_info));
    root->path = (char*) malloc(sizeof(char) * strlen("virtual")+1);
    strcpy(root->path,"virtual");
    root->vendor_id = 0x2354;
    root->product_id = 0x1111;
    root->serial_number = (wchar_t*) malloc(sizeof(wchar_t) * wcslen(L"SS")+1);
    wcscpy(root->serial_number,L"SS");
    root->release_number = 1;
    root->manufacturer_string = (wchar_t*) malloc(sizeof(wchar_t) * wcslen(L"boisestate")+1);
    wcscpy(root->manufacturer_string,L"boisestate");
    root->product_string = (wchar_t*) malloc(sizeof(wchar_t) * wcslen(L"griffin")+1);
    wcscpy(root->product_string,L"griffin");
    root->usage_page = 1;
    root->usage_page = 1;
    root->interface_number = 1;
    root->next = NULL;
    return root;
}

void hid_free_enumeration(struct hid_device_info *devs)
{
    struct hid_device_info *d = devs;
    while (d) {
        struct hid_device_info *next = d->next;
        free(d->path);
        free(d->serial_number);
        free(d->manufacturer_string);
        free(d->product_string);
        free(d);
        d = next;
    }
}

hid_device* hid_open(unsigned short vendor_id, unsigned short product_id, const wchar_t * serial_number)
{
    return new_hid_device();
}

hid_device* hid_open_path(const char *path)
{
    return new_hid_device();
}

int hid_write(hid_device * dev, const unsigned char *data, size_t length)
{
    //when ever we write we will read back the response 
    //which the client will get when they call hid_read
    int len = 0;

    //send over the request
    len = send_data(dev,data,length);

    //get the response
    memset(dev->res,0,MESSAGE);
    dev->len = recv_data(dev,dev->res,length);
    
    return len;
}
int hid_read_timeout(hid_device * dev, unsigned char *data, size_t length, int milliseconds)
{
    memcpy(data, dev->res,length);
    return dev->len;
}

int hid_read(hid_device * dev, unsigned char *data, size_t length)
{
    return hid_read_timeout(dev, data, length, 0);
}

int hid_set_nonblocking(hid_device * dev, int nonblock)
{
    return -1;
}

int hid_send_feature_report(hid_device * dev, const unsigned char *data, size_t length)
{
    return length;
}

int hid_get_feature_report(hid_device * dev, unsigned char *data, size_t length)
{
    return length;
}


void hid_close(hid_device * dev)
{
    free_hid_device(dev);
}

int hid_get_manufacturer_string(hid_device * dev, wchar_t * string, size_t maxlen)
{
    return FALSE;
}

int  hid_get_product_string(hid_device * dev, wchar_t * string, size_t maxlen)
{
    return FALSE;
}

int  hid_get_serial_number_string(hid_device * dev, wchar_t * string, size_t maxlen)
{
    return FALSE;
}

int  hid_get_indexed_string(hid_device * dev, int string_index, wchar_t * string, size_t maxlen)
{
    return FALSE;
}


const wchar_t* hid_error(hid_device * dev)
{
    return NULL;
}
