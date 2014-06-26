/****************************************************************************
// Licensed Materials - Property of IBM
//
// Governed under the terms of the International
// License Agreement for Non-Warranted Sample Code.
//
// (C) COPYRIGHT International Business Machines Corp. 1997 - 2006
// All Rights Reserved.
//
// US Government Users Restricted Rights - Use, duplication or
// disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
*****************************************************************************
//
// SOURCE FILE NAME: InstallTester.h
//
// SAMPLE: Sample DB2 Installer client
//
// OUTPUT FILE: None
*****************************************************************************
//
// For more information on the sample programs, see the README file.
//
****************************************************************************/
#ifndef __INSTALLTESTER_H
#define __INSTALLTESTER_H

#define HEADER_COUNT 3
#define HEADER_LENGTH 50
#define I_MSG_LEN_SZ 1024

#define HEADER_EVENT 0
#define HEADER_GROUP 1
#define HEADER_TYPE 2

typedef struct
{
  char message[I_MSG_LEN_SZ];

  char header[HEADER_COUNT][HEADER_LENGTH];
  char** messages;
  int messageCount;
  int maxMessageCount;
  int maxMessageLength;
} MESSAGE;

int getMessage(FILE* inStream, char* message, int maxLen);
void getResponse(FILE* inStream, char* response, int maxLen);
void splitMessage(char* message, int messageSize, MESSAGE* messageStruct);
void splitHeader(char* header, MESSAGE* messageStruct);
void processMessage(MESSAGE* messageStruct);
void processProgressGroup(MESSAGE* messageStruct);
void processProgressTask(MESSAGE* messageStruct);
void processProgressInfo(MESSAGE* messageStruct);
void processTaskTimes(char** times, int count);
void processTaskIDs(char** ids, int count);
void processInstallGroup(MESSAGE* messageStruct);
void processInstallInfo(MESSAGE* messageStruct);
void processInstallQuery(MESSAGE* messageStruct);
void processInstallError(MESSAGE* messageStruct);
void processInstallWarning(MESSAGE* messageStruct);
void getReturnMessage(int rc, char* rcMessage, size_t len);
void addMessageField(char* token, MESSAGE* messageStruct);
void getLine(FILE* inStream, char* line, int maxLen);
int trimAndCopy(char* source, char* destination, size_t destSize);
void initMsgStruct(MESSAGE* messageStruct);
void resetMsgStruct(MESSAGE* messageStruct);
void destroyMsgStruct(MESSAGE* messageStruct);
void addMessageToStruct(char* message, MESSAGE* messageStruct);
int validateMessageFormat(char* message, regex_t* compiledRegex);

typedef struct
{
  char const* token;
  void (*fnc)(MESSAGE*);
} HeaderList;

HeaderList messageList[] =
{
  {"PROGRESS", processProgressGroup},
  {"MESSAGE", processInstallGroup}
};

HeaderList progressMessageList[] =
{
  {"TASK", processProgressTask},
  {"INFO", processProgressInfo}
};

HeaderList installMessageList[] =
{
  {"INFO", processInstallInfo},
  {"QUERY", processInstallQuery},
  {"ERROR", processInstallError},
  {"WARNING", processInstallWarning}
};

#endif
