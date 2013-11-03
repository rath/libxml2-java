DIR=src/main/c
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_17.jdk/Contents/Home
CFLAGS=$(shell xml2-config --cflags) -I$(DIR) -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin -g
LDFLAGS=$(shell xml2-config --libs)
TARGET=libxml2java.jnilib

all:
	gcc $(CFLAGS) $(LDFLAGS) -dynamiclib -o $(TARGET) $(DIR)/libxml.cpp $(DIR)/document.cpp $(DIR)/node.cpp $(DIR)/utils.cpp
