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
// SOURCE FILE NAME: InstallTester.c
//
// SAMPLE: Sample DB2 Installer client
//
// OUTPUT FILE: None
*****************************************************************************
//
// For more information on the sample programs, see the README file.
//
****************************************************************************/
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sys/types.h>
#include <regex.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "InstallTester.h"

FILE* installerInputStream = NULL;
FILE* installerOutputStream = NULL;
regex_t compiledRegex;

int main(int argc, char** argv)
{
  pid_t pid;
  int rc;
  int clientToInstaller[2];
  int installerToClient[2];
  char message[I_MSG_LEN_SZ];
  char messageToProcess[I_MSG_LEN_SZ];
  char returnMessage[I_MSG_LEN_SZ];
  MESSAGE messageStruct;
  char regex[] = "^[[:space:]]*[[:upper:]][[:upper:]]*[[:space:]][[:space:]]*[[:upper:]][[:upper:]]*[[:space:]][[:space:]]*[[:upper:]][[:upper:]]*.*";

  if (argc < 2)
  {
    fprintf(stdout,
            "Syntax: ./InstallTester <location of db2setup> -r <location of response file>\n");
    return 0;
  }

  if (pipe(clientToInstaller))
  {
    fprintf(stderr, "Pipe failed\n");
    exit(1);
  }

  if (pipe(installerToClient))
  {
    fprintf(stderr, "Pipe failed\n");
    exit(1);
  }

  if ((pid = fork()) == -1)
  {
    fprintf(stderr, "Fork failed\n");
    exit(1);
  }

  if (pid) /* client */
  {
    installerInputStream = fdopen(clientToInstaller[1], "w");
    installerOutputStream = fdopen(installerToClient[0], "r");
    close(clientToInstaller[0]);
    close(installerToClient[1]);
    setvbuf(installerInputStream, NULL, _IOLBF, 0);
    initMsgStruct(&messageStruct);

    if (regcomp(&compiledRegex, regex, REG_NOSUB))
    {
      fprintf(stderr, "regcomp failed");
    }

    do
    {
      if (waitpid(pid, &rc, WNOHANG))
      {
        if (WIFEXITED(rc))
        {
          break;
        }
        break;
      }

      if (!getMessage(installerOutputStream, message, I_MSG_LEN_SZ))
      {
        continue;
      }
      strncpy(messageToProcess, message, I_MSG_LEN_SZ);
      splitMessage(messageToProcess, I_MSG_LEN_SZ, &messageStruct);
      processMessage(&messageStruct);
    } while (strstr(message, "MESSAGE INFO INSTALLEND") == NULL);

    destroyMsgStruct(&messageStruct);
    regfree(&compiledRegex);

    waitpid(pid, &rc, 0);
    if (WIFEXITED(rc))
    {
      int exitStatus = WEXITSTATUS(rc);
      getReturnMessage(exitStatus, returnMessage, I_MSG_LEN_SZ);
      printf(returnMessage);
    }
  }
  else /* installer */
  {
    dup2(clientToInstaller[0], fileno(stdin));
    dup2(installerToClient[1], fileno(stdout));
    close(clientToInstaller[1]);
    close(installerToClient[0]);

    if (execv(argv[1], argv+1) == -1)
    {
      fprintf(stderr, "execl failed");
      exit(1);
    }
  }
  return 0;
}

/**
 * Split the message into a MESSAGE struct.
 * Fills the provided MESSAGE struct.
 * Returns void.
 */
void splitMessage(char* message, int messageSize, MESSAGE* messageStruct)
{
  char* token = NULL;
  char header[I_MSG_LEN_SZ]; /* temporary storage for the entire header */

  resetMsgStruct(messageStruct);

  strncpy(messageStruct->message, message, I_MSG_LEN_SZ);

  token = strtok(message, "::");
  strncpy(header, token, I_MSG_LEN_SZ);

  while (NULL != (token = strtok(NULL, "::")))
  {
    addMessageField(token, messageStruct);
  }

  splitHeader(header, messageStruct);
}

/**
 * Split the header into the message struct.
 * Return void.
 */
void splitHeader(char* header, MESSAGE* messageStruct)
{
  char* token =  NULL;
  int index = 0;

  token = strtok(header, " ");
  trimAndCopy(token, messageStruct->header[index++], HEADER_LENGTH);

  while (NULL != (token = strtok(NULL, " ")))
  {
    trimAndCopy(token, messageStruct->header[index++], HEADER_LENGTH);
  }
}

/**
 * Process the message.
 * Return void.
 */
void processMessage(MESSAGE* messageStruct)
{
  unsigned int i = 0;
  int processed = 0; /* 0 == unprocessed, 1 == processed */
  char* messageEvent = messageStruct->header[HEADER_EVENT];

  for (i = 0; i < sizeof(messageList) / sizeof(messageList[0]); i++)
  {
    if (strcmp(messageEvent, messageList[i].token) == 0)
    {
      (messageList[i].fnc)(messageStruct);
      processed = 1;
    }
  }

  if (!processed)
  {
    printf("Unknown message type received\n");
  }
}

/**
 * Process the progress message received from the installer.
 * Return void.
 */
void processProgressGroup(MESSAGE* messageStruct)
{
  unsigned int i = 0;
  int processed = 0; /* 0 == unprocessed, 1 == processed */
  char* progressGroup = messageStruct->header[HEADER_GROUP];

  for (i = 0;
       i < sizeof(progressMessageList) / sizeof(progressMessageList[0]);
       i++)
  {
    if (strcmp(progressGroup, progressMessageList[i].token) == 0)
    {
      (progressMessageList[i].fnc)(messageStruct);
      processed = 1;
    }
  }

  if (!processed)
  {
    printf("Unknown process group\n");
  }
}

/**
 * Process the progress task received.
 * Return void.
 */
void processProgressTask(MESSAGE* messageStruct)
{
  char* progressTask = messageStruct->header[HEADER_TYPE];

  if ((NULL != progressTask) && strlen(progressTask) > 0)
  {
    if (strcmp(progressTask, "START") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when a new task starts\n");
      printf("Task type: %s\n", messageStruct->messages[0]);
      printf("Estimated task time: %s second(s)\n",
             messageStruct->messages[1]);
      printf("Task ID: %s\n", messageStruct->messages[2]);
      printf("Task description: %s\n\n", messageStruct->messages[3]);
    }
    else if (strcmp(progressTask, "END") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when a task ends\n");
      printf("Task type: %s\n", messageStruct->messages[0]);
      printf("Task ID: %s\n", messageStruct->messages[1]);
      printf("Task description: %s\n\n", messageStruct->messages[2]);
    }
    else
    {
      printf("Unknown progress task received");
    }

  }
  else
  {
    printf("Unknown progress task received");
  }
}

/**
 * Process the progress info received.
 * Return void.
 */
void processProgressInfo(MESSAGE* messageStruct)
{
  char* progressInfo = messageStruct->header[HEADER_TYPE];

  if ((NULL != progressInfo) && strlen(progressInfo) > 0)
  {
    if (strcmp(progressInfo, "TASKCOUNT") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received at the beginning of the DB2 installation.\n");
      printf("         The total number of tasks to be performed.\n");
      printf("Number of tasks: %s\n\n", messageStruct->messages[0]);
    }
    else if (strcmp(progressInfo, "TASKTIMES") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received at the beginning of the DB2 installation.\n");
      printf("         The estimated task times in seconds for each tasks.\n");
      printf("         Arranged in the order to be performed.\n");
      processTaskTimes(messageStruct->messages, messageStruct->messageCount);
    }
    else if (strcmp(progressInfo, "TASKIDS") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received at the beginning of the DB2 installation.\n");
      printf("         The task ids for all the tasks to be performed.\n");
      printf("         The task IDs can be used to map each tasks to custom messages.\n");
      processTaskIDs(messageStruct->messages, messageStruct->messageCount);
    }
    else
    {
      printf("%s\n", progressInfo);
      printf("Unknown progress info\n");
    }
  }
  else
  {
    printf("%s\n", progressInfo);
    printf("Unknown progress info\n");
  }
}

/**
 * Process the task times.
 * Return void.
 */
void processTaskTimes(char** times, int count)
{
  int i = 0;
  int totalTime = 0;

  for (i = 0; i < count; i++)
  {
    totalTime += atoi(times[i]);
  }

  printf("Total of %d tasks for %d seconds.\n\n", count, totalTime);
}

/**
 * Process the task ids.
 * Return void.
 */
void processTaskIDs(char** ids, int count)
{
  int i = 0;
  printf("Ids: ");
  for (i = 0; i < count; i++)
  {
    printf("%s ", ids[i]);
  }
  printf("\n\n");
}

/**
 * Process the install message received from the installer.
 * Return void.
 */
void processInstallGroup(MESSAGE* messageStruct)
{
  unsigned int i = 0;
  int processed = 0; /* 0 == unprocessed, 1 == processed */
  char* installGroup = messageStruct->header[HEADER_GROUP];

  for (i = 0;
       i < sizeof(installMessageList) / sizeof(installMessageList[0]);
       i++)
  {
    if (strcmp(installGroup, installMessageList[i].token) == 0)
    {
      (installMessageList[i].fnc)(messageStruct);
      processed = 1;
    }
  }

  if (!processed)
  {
    printf("Unknown install group\n");
  }
}

/**
 * Process install info.
 * Return void.
 */
void processInstallInfo(MESSAGE* messageStruct)
{
  char* infoType = messageStruct->header[HEADER_TYPE];

  if ((NULL != infoType) && strlen(infoType) > 0)
  {
    if (strcmp(infoType, "INSTALLSTART") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received at the beginning of the DB2 installation.\n");
      printf("         Can be used to display a notification to the user until the\n");
      printf("         actual installation begins.\n");
      printf("           ie. a splash screen\n\n");
    }
    else if (strcmp(infoType, "INSTALLEND") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received at the end of the DB2 installation.\n");
      printf("         Can be used to notify the client that the installation\n");
      printf("         is finished.\n");
      printf("DB2 Installation is finished.  Log file location is %s.\n\n",
             messageStruct->messages[0]);
    }
    else if (strcmp(infoType, "UNDOSTART") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when the installation is being undone.\n");
      printf("         Can be used to notify the start of the undoing process.\n\n");
    }
    else if (strcmp(infoType, "UNDOEND") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when the installation is finished being undone\n");
      printf("         Can be used to notify the end of the undoing process.\n\n");
    }
    else
    {
      printf("Unknown install info\n");
    }
  }
  else
  {
    printf("Unknown install info\n");
  }
}

/**
 * Process install queries.
 * Query the user, and send the user's response to the installer.
 * Return void.
 */
void processInstallQuery(MESSAGE* messageStruct)
{
  char response[I_MSG_LEN_SZ];
  char* queryType = messageStruct->header[HEADER_TYPE];

  if ((queryType != NULL) && strlen(queryType) > 0)
  {
    if (strcmp(queryType, "CDPATH") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when the installer prompts for a new CD location.\n");
      printf("CD Volume Label: %s\n", messageStruct->messages[0]);
      printf("Default path: %s\n", messageStruct->messages[1]);
      printf("Please enter the path to the disk labelled %s\n[%s]: ",
             messageStruct->messages[0],
             messageStruct->messages[1]);
      fflush(stdout);
      getResponse(stdin, response, I_MSG_LEN_SZ);
      printf("\n");

      if (strncmp(response, "abort", 5) == 0)
      {
        fprintf(installerInputStream, "ABORT\n");
      }
      else if (strlen(response) == 0)
      {
        fprintf(installerInputStream, "RESPONSE :: %s\n",
                messageStruct->messages[1]);
      }
      else
      {
        fprintf(installerInputStream, "RESPONSE :: %s\n", response);
      }
    }
    else
    {
      printf("Unknown install query\n");
    }
  }
  else
  {
    printf("Unknown install query\n");
  }
}

/**
 * Process install errors.
 * Output the appropriate error message.
 * Return void.
 */
void processInstallError(MESSAGE* messageStruct)
{
  char* errorType = messageStruct->header[HEADER_TYPE];

  if ((NULL != errorType) && strlen(errorType) > 0)
  {
    if (strcmp(errorType, "CDNOTFOUND") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when the path received on a CD prompt was not valid.\n");
      printf("Error: CD path invalid.\n\n");
    }
    else if (strcmp(errorType, "GENERAL") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when a fatal error has occurred.\n");
      printf("Error: %s\n\n", messageStruct->messages[0]);
    }
    else
    {
      printf("Unknown error received.\n");
    }
  }
  else
  {
    printf("Unknown error received.\n");
  }
}

/**
 * Process install warnings.
 * Output the appropriate message.
 * Return void.
 */
void processInstallWarning(MESSAGE* messageStruct)
{
  char* warningType = messageStruct->header[HEADER_TYPE];

  if ((NULL != warningType) && strlen(warningType) > 0)
  {
    if (strcmp(warningType, "GENERAL") == 0)
    {
      printf("Message: %s\n", messageStruct->message);
      printf("Purpose: Received when a non-fatal error has occurred.\n");
      printf("Warning: %s\n", messageStruct->messages[0]);
    }
    else
    {
      printf("Unknown warning received.\n");
    }
  }
  else
  {
    printf("Unknown warning received.\n");
  }
}

/**
 * Get the message that maps to the return code.
 * snprintf is replaced by sprintf because it did
 * not exist on the testing platform.
 *
 * Return the message.
 */
void getReturnMessage(int rc, char* rcMessage, size_t len)
{
  memset(rcMessage, 0, len);

  switch (rc)
  {
  case 0:
    sprintf(rcMessage,
            "The installation has completed successfully\n");
    break;
  case 1:
    sprintf(rcMessage,
            "Warning: The installation has completed with a minor error.\n");
    break;
  case 67:
    sprintf(rcMessage,
            "Error: A fatal error occurred during the installation.  Please refer to the log file.\n");
    break;
  case 3010:
    sprintf(rcMessage,
            "The installation was successful.  However, a reboot is required to complete the installation.\n");
    break;
  case 3:
    sprintf(rcMessage,
            "Error: The path was not found.\n");
    break;
  case 5:
    sprintf(rcMessage,
            "Error: Access was denied.\n");
    break;
  case 10:
    sprintf(rcMessage,
            "Error: An environment error occurred.\n");
    break;
  case 13:
    sprintf(rcMessage,
            "Error: The data is invalid.\n");
    break;
  case 87:
    sprintf(rcMessage,
            "Error: Invalid response file keyword.\n");
    break;
  case 66:
    sprintf(rcMessage,
            "Error: The installation was cancelled by the user.\n");
    break;
  case 74:
    sprintf(rcMessage,
            "Error: The configuration data is corrupt.  Contact your support personnel.\n");
    break;
  case 76:
    sprintf(rcMessage,
            "Error: The installation source for this product is not available.  Verify that the source exists and that you can access it.\n");
    break;
  case 82:
    sprintf(rcMessage,
            "Error: Another installation is already in progress.  Complete that installation first before proceeding with the installation.\n");
    break;
  case 86:
    sprintf(rcMessage,
            "Error: There was an error opening the installation log file.  Verify that the specified log file location exists and that it is writable.\n");
    break;
  case 96:
    sprintf(rcMessage,
            "Error: The Temp folder is either full or inaccessible.  Verify that the Temp folder exists and that you can write to it.\n");
    break;
  case 97:
    sprintf(rcMessage,
            "Error: This installation package is not supported on this platform.\n");
    break;
  case 102:
    sprintf(rcMessage,
            "Error: Another version of this product is already installed.  Installation of this version cannot continue.\n");
    break;
  case 103:
    sprintf(rcMessage,
            "Error: Invalid command line argument.\n");
    break;
  case 143:
    sprintf(rcMessage,
            "Error: The system does not have enough free space to continue with the installation.\n");
    break;
  default:
    sprintf(rcMessage,
            "Error: No return code received.\n");
    break;
  }
}

/**
 * Add the message field data to the message struct.
 * Allocate memory as deemed necessary.
 * Return void.
 */
void addMessageField(char* token, MESSAGE* messageStruct)
{
  int i = 0;
  /* if all the buffers in the message struct are full,
   allocate more memory */
  if (messageStruct->messageCount >= messageStruct->maxMessageCount)
  {
    messageStruct->maxMessageCount *= 2;
    messageStruct->messages =
      (char **)realloc(messageStruct->messages,
                       sizeof(char *) * messageStruct->maxMessageCount);

    /* allocate memory for the new char buffers */
    for (i = messageStruct->maxMessageCount/2;
         i < messageStruct->maxMessageCount;
         i++)
    {
      messageStruct->messages[i] =
        (char *)malloc(sizeof(char) * messageStruct->maxMessageLength);
    }
  }

  trimAndCopy(token, messageStruct->messages[messageStruct->messageCount++],
              messageStruct->maxMessageLength);
}

/**
 * Wait on the file stream until a new message is received.
 * Fill the line buffer with the received message.
 * Return void.
 */
int getMessage(FILE* inStream, char* message, int maxLen)
{
  int validMessageReceived = 0; /* 0 == not received, 1 == received */
  char* untrimmedMessage = NULL;
  char* newlineLoc =  NULL;

  if (NULL == (untrimmedMessage = (char *) malloc(sizeof(char) * maxLen)))
  {
    fprintf(stderr, "malloc failed\n");
    goto exit;
  }

  if (fgets(untrimmedMessage, maxLen, inStream) != NULL)
  {
    newlineLoc = strstr(untrimmedMessage, "\n");
    if (newlineLoc !=  NULL)
    {
      *newlineLoc = '\0';
    }
    trimAndCopy(untrimmedMessage, message, maxLen);
  }

  if (validateMessageFormat(message, &compiledRegex))
  {
    validMessageReceived = 1;
  }

exit:
  if (untrimmedMessage)
  {
    free(untrimmedMessage);
  }
  return validMessageReceived;
}

/**
 * Wait on the file stream until a new message is received.
 * Fill the line buffer with the received message.
 * Return void.
 */
void getResponse(FILE* inStream, char* response, int maxLen)
{
  char* untrimmedResponse = NULL;
  char* newlineLoc = NULL;
  if (NULL == (untrimmedResponse = (char *) malloc(sizeof(char) * maxLen)))
  {
    fprintf(stderr, "malloc failed\n");
    goto exit;
  }

  if (fgets(untrimmedResponse, maxLen, inStream) != NULL)
  {
    newlineLoc = strstr(untrimmedResponse, "\n");
    if (newlineLoc !=  NULL)
    {
      *newlineLoc = '\0';
    }
    trimAndCopy(untrimmedResponse, response, maxLen);
  }

exit:
  if (untrimmedResponse)
  {
    free(untrimmedResponse);
  }
}

/**
 * Initialize the message struct.
 * All messages that are processed are split-up into
 * this struct.
 * Memory for the buffers is allocated here.
 */
void initMsgStruct(MESSAGE* messageStruct)
{
  int i = 0;
  messageStruct->messageCount = 0;
  messageStruct->maxMessageCount = 2;
  messageStruct->maxMessageLength = I_MSG_LEN_SZ;

  for (i = 0; i < HEADER_COUNT; i++)
  {
    memset(messageStruct->header[i], 0, HEADER_LENGTH);
  }

  if (NULL == (messageStruct->messages =
               (char **) malloc(sizeof(char *) * messageStruct->maxMessageCount)))
  {
    fprintf(stderr, "malloc failed\n");
  }

  for (i = 0; i < messageStruct->maxMessageCount; i++)
  {
    if (NULL == (messageStruct->messages[i] =
                 (char *) malloc(sizeof(char) * messageStruct->maxMessageLength)))
    {
      fprintf(stderr, "malloc failed\n");
    }
    memset(messageStruct->messages[i], 0, messageStruct->maxMessageLength);
  }
}

/**
 * Reset the message struct.
 * Clears all the buffers, and sets the message count
 * to zero.
 */
void resetMsgStruct(MESSAGE* messageStruct)
{
  int i = 0;
  messageStruct->messageCount = 0;

  for (i = 0; i < HEADER_COUNT; i++)
  {
    memset(messageStruct->header[i], 0, HEADER_LENGTH);
  }

  for (i = 0; i < messageStruct->maxMessageCount; i++)
  {
    memset(messageStruct->messages[i], 0, messageStruct->maxMessageLength);
  }
}

/**
 * Validate the format of the message.
 * Return 1 if it matches.
 * Otherwise, return 0;
 */
int validateMessageFormat(char* message, regex_t* pRegex)
{
  int match = 0;

  if (!regexec(pRegex, message, 0, NULL, 0))
  {
    match = 1;
  }

  return match;
}

/**
 * Destroy the message struct.
 * Deallocates memory for the buffers.
 */
void destroyMsgStruct(MESSAGE* messageStruct)
{
  int i = 0;
  for (i = 0; i < messageStruct->maxMessageCount; i++)
  {
    free(messageStruct->messages[i]);
  }
  free(messageStruct->messages);
}

/**
 * Remove leading and trailing whitespace from source.
 * Copy the trimmed string to the destination buffer.
 * Return the length of the new string on success.
 * Otherwise return -1.
 */
int trimAndCopy(char* source, char* destination, size_t destSize)
{
  unsigned int sourceSize;
  int newSize;
  char* firstChar;
  char* lastChar;

  sourceSize = strlen(source);

  if (sourceSize > destSize)
  {
    return -1;
  }

  firstChar = source;
  lastChar = source + sourceSize - 1;

  while (firstChar <= lastChar &&
         ( *firstChar == ' ' || *firstChar == '\t' ))
  {
    firstChar++;
  }

  while (lastChar >= firstChar &&
         ( *lastChar == ' ' || *lastChar == '\t' ))
  {
    lastChar--;
  }

  newSize = lastChar - firstChar + 1;
  strncpy(destination, firstChar, newSize);
  destination[newSize] = '\0';

  return newSize;
}
