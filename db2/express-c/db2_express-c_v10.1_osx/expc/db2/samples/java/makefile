#############################################################################
# Licensed Materials - Property of IBM
#
# Governed under the terms of the International
# License Agreement for Non-Warranted Sample Code.
#
# (C) COPYRIGHT International Business Machines Corp. 2006
# All Rights Reserved.
#
# US Government Users Restricted Rights - Use, duplication or
# disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
#############################################################################
#
# MAKEFILE for C sample installer on Linux
#
# Enter one of the following commands 
#
#   make all               - Builds all supplied sample programs
#   make clean             - Erases intermediate files
#   make cleanall          - Erases all files produced in the build process
#                            except the original source files
#
# The makefile contains the following sections:
#    1 -- VARIABLES
#    2 -- MAKE CATEGORIES
#    3 -- COMMANDS TO MAKE INDIVIDUAL SAMPLES
#
#
#############################################################################
#                  1 --  VARIABLES                                     
#############################################################################

CC=javac

COPY=cp
ERASE=rm -f

CREATE_JAR_FLAGS = -cf

WRAPPER_PATH = com/ibm/db2/install/wrapper
WRAPPER_SOURCES = $(wildcard $(WRAPPER_PATH)/*.java)
WRAPPER_CLASSES := $(patsubst %.java, %.class, $(WRAPPER_SOURCES))

WRAPPER_JAR=wrapper.jar

#############################################################################
#  Generic rule to make a class from a java source file
#############################################################################

.SUFFIXES : .class .java

.java.class :
	${CC} $<

#############################################################################
#  2 -- MAKE CATEGORIES
#              2a - make all
#              2b - make clean
#              2c - make cleanall
#############################################################################

#****************************************************************************
#                  2a - make all
#****************************************************************************

all : \
        InstallTester Wrapper $(WRAPPER_JAR)

#****************************************************************************
#                  2b - make clean
#****************************************************************************

clean : 

#****************************************************************************
#                  2c - make cleanall
#****************************************************************************

cleanall : \
	clean
	$(ERASE) *.class
	$(ERASE) $(WRAPPER_PATH)/*.class

#############################################################################
#  3 -- COMMANDS TO MAKE INDIVIDUAL SAMPLES
#                  3a -  sample installer
#############################################################################

#****************************************************************************
#                  3a - sample installer
#****************************************************************************

InstallTester : InstallTester.class

Wrapper: $(WRAPPER_CLASSES)

$(WRAPPER_JAR): $(WRAPPER_CLASSES)
	$(ERASE) $(WRAPPER_JAR)
	jar $(CREATE_JAR_FLAGS) $(WRAPPER_JAR) $(WRAPPER_CLASSES)
	
