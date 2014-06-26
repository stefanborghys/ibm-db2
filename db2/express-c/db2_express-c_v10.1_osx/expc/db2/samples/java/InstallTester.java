//***************************************************************************
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
//***************************************************************************
//
// SOURCE FILE NAME: InstallTester.java
//
// SAMPLE: Sample DB2 Installer client
//
//         This sample has 8 classes:
//         1. InstallTester      - The sample installer
//         2. StreamCommunicator - The class that listens to the stream and
//                                 generates the stream events
//         3. StreamEvent        - The base class for stream events
//         4. MessageEvent       - Event class for MESSAGE messages
//         5. ProgressEvent      - Event class for PROGRESS messages
//         6. ObserverNotifier   - Hidden class that handles loops to receive
//                                 messages
//         7. UnknownMessageException  - Exception generated when MESSAGE
//                                       messages with unknown headers are
//                                       received
//         8. UnknownProgressException - Exception generated when PROGRESS
//                                       messages with unknown headers are
//                                       received
//
// OUTPUT FILE: None
//***************************************************************************
//
// For more information on the sample programs, see the README file.
//
//***************************************************************************

import com.ibm.db2.install.wrapper.*;
import java.io.*;
import java.util.*;

/**
 * This class is used to demonstrate the use of the installer's machine
 * stream outputs.  The installer's progress and general messages are
 * received and processed by the sample installer.
 *
 * @version %I%, %G%
 */
public class InstallTester implements ProgressListener, MessageListener {

  StreamCommunicator mStreamCommunicator;
  BufferedReader mUserInputReader;

  /**
   * Construct the sample installer.
   */

  public InstallTester() {
    mStreamCommunicator = null;
    mUserInputReader = new BufferedReader(
                                          new InputStreamReader(System.in)
                                         );
  }

  /**
   * Run the sample install.
   *
   * @param toExecute the full command to run a DB2 response file install
   */
  public void runTest(String toExecute) {
    int rc = 0;;
    String rcMessage = null;

    mStreamCommunicator = new StreamCommunicator(toExecute);
    mStreamCommunicator.addProgressListener(this);
    mStreamCommunicator.addMessageListener(this);
    rc = mStreamCommunicator.waitAndGetReturnValue();
    rcMessage = getReturnMessage(rc);
    System.out.println(rcMessage);
  }

  /**
   * Process PROGRESS TASK messages that are received.
   *
   * @param progressEvent ProgressEvent received from ProgressListener
   */
  public void progressTaskReceived(ProgressEvent progressEvent) {
    switch(progressEvent.getMessageType()) {
    case ProgressEvent.START:
      System.out.println("Message: " + progressEvent.getStreamMessage());
      System.out.println("Purpose: Received when a new task starts");
      System.out.println("Task type: " +
                         (String)progressEvent.getMessage().elementAt(0));
      System.out.println("Estimated task time: " +
                         (String)progressEvent.getMessage().elementAt(1));
      System.out.println("Task  ID: " +
                         (String)progressEvent.getMessage().elementAt(2));
      System.out.println("Task description: " +
                         (String)progressEvent.getMessage().elementAt(3) +
                         "\n");
      break;
    case ProgressEvent.END:
      System.out.println("Message: " + progressEvent.getStreamMessage());
      System.out.println("Purpose: Received when a new task starts");
      System.out.println("Task type: " +
                         (String)progressEvent.getMessage().elementAt(0));
      System.out.println("Task  ID: " +
                         (String)progressEvent.getMessage().elementAt(1));
      System.out.println("Task description: " +
                         (String)progressEvent.getMessage().elementAt(2) +
                         "\n");
      break;
    default:
      System.err.println("Unknown progress task event received");
      break;
    }
  }

  /**
   * Process PROGRESS INFO messages that are received.
   *
   * @param progressEvent ProgressEvent received from ProgressListener
   */
  public void progressInfoReceived(ProgressEvent progressEvent) {
    switch(progressEvent.getMessageType()) {
    case ProgressEvent.TASKCOUNT:
      System.out.println("Message: " + progressEvent.getStreamMessage());
      System.out.println("Purpose: Received at the beginning of the DB2 installation.");
      System.out.println("         The total number of tasks to be performed.");
      System.out.println("Number of tasks: " +
                         Integer.parseInt((String)progressEvent.getMessage().elementAt(0)) + "\n");
      break;
    case ProgressEvent.TASKTIMES:
      System.out.println("Message: " + progressEvent.getStreamMessage());
      System.out.println("Purpose: Received at the beginning of the DB2 installation.");
      System.out.println("         The estimated task times in seconds for each tasks.");
      System.out.println("         Arranged in the order to be performed.");
      processTaskTimes(progressEvent.getMessage());
      break;
    case ProgressEvent.TASKIDS:
      System.out.println("Message: " + progressEvent.getStreamMessage());
      System.out.println("Purpose: Received at the beginning of the DB2 installation.");
      System.out.println("         The task ids for all the tasks to be performed.");
      System.out.println("         The task IDs can be used to map each tasks to custom messages.");
      processTaskIds(progressEvent.getMessage());
      break;
    default:
      System.err.println("Unknown progress info received");
      break;
    }
  }

  /**
   * Process MESSAGE INFO messages that are received.
   *
   * @param messageEvent MessageEvent received from MessageListener
   */
  public void messageInfoReceived(MessageEvent messageEvent) {
    switch(messageEvent.getMessageType()) {
    case MessageEvent.INSTALLSTART:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received at the beginning of the DB2 installation.");
      System.out.println("         Can be used to display a notification to the user until the");
      System.out.println("         actual installation begins.");
      System.out.println("           ie. a splash screen\n");
      break;
    case MessageEvent.INSTALLEND:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received at the end of the DB2 installation.");
      System.out.println("         Can be used to notify the client that the installation");
      System.out.println("         is finished.");
      System.out.println("DB2 Installation is finished.  Log file location is " +
                         (String)messageEvent.getMessage().elementAt(0) +
                         ".\n");
      break;
    case MessageEvent.UNDOSTART:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received when the installation is being undone.");
      System.out.println("         Can be used to notify the start of the undoing process.\n");
      break;
    case MessageEvent.UNDOEND:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received when the installation is finished being undone");
      System.out.println("         Can be used to notify the end of the undoing process.\n");
      break;
    default:
      System.err.println("Unknown message info received");
      break;
    }
  }

  /**
   * Process MESSAGE QUERY messages that are received.
   *
   * @param messageEvent MessageEvent received from MessageListener
   */
  public void messageQueryReceived(MessageEvent messageEvent) {
    switch(messageEvent.getMessageType()) {
    case MessageEvent.CDPATH:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received when the installer prompts for a new CD location.");
      System.out.println("CD Volume Label: " + (String)messageEvent.getMessage().elementAt(0));
      System.out.println("Default path: " + (String)messageEvent.getMessage().elementAt(1));
      queryNextCD(messageEvent);
      break;
    default:
      System.out.println("Unknown message query received");
      break;
    }
  }

  /**
   * Process MESSAGE ERROR messages that are received.
   *
   * @param messageEvent MessageEvent received from MessageListener
   */
  public void messageErrorReceived(MessageEvent messageEvent) {
    switch(messageEvent.getMessageType()) {
    case MessageEvent.CDNOTFOUND:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received when the path received on a CD prompt was not valid.");
      System.out.println("Error: CD path invalid.\n");
      break;
    case MessageEvent.GENERAL:
      System.out.println("Message: " + messageEvent.getStreamMessage());
      System.out.println("Purpose: Received when a fatal error has occurred.");
      System.out.println("Error: " + messageEvent.getMessage());
      break;
    default:
      System.err.println("Unknown message error received");
      break;
    }
  }

  /**
   * Process MESSAGE WARNING messages that are received.
   *
   * @param messageEvent MessageEvent received from MessageListener
   */
  public void messageWarningReceived(MessageEvent messageEvent) {
    switch(messageEvent.getMessageType()) {
    default:
      System.err.println("Unknown message warning received");
      break;
    }
  }

  /**
   * Print out the task ids received from the installer.
   *
   * @param ids IDs of tasks to be performed by the installer
   */
  private void processTaskIds(Vector ids) {
    System.out.print("Ids: ");
    for (int i = 0; i < ids.size(); i++) {
      System.out.print(Integer.parseInt((String)ids.elementAt(i)) + " ");
    }
    System.out.println("\n");
  }

  /**
   * Print out the estimated task times received from the installer.
   *
   * @param times Estimated task times of the tasks to be performed by the installer
   */
  private void processTaskTimes(Vector times) {
    int totalTime = 0;

    for (int i = 0; i < times.size(); i++) {
      totalTime += Integer.parseInt((String)times.elementAt(i));
    }
    System.out.println("Total of "
                       + times.size()
                       + " tasks for "
                       + totalTime
                       + " minutes\n"
                      );
  }

  /**
   * Perform the query for the required CD.
   *
   * @param messageEvent MessageEvent received from MessageListener
   */
  private void queryNextCD(MessageEvent messageEvent) {
    Vector messageData = messageEvent.getMessage();
    String input = null;
    String defaultPath = (String)messageData.elementAt(1);

    System.out.print("Enter path to the CD labeled '"
                     + (String)messageData.elementAt(0)
                     + "' \n["
                     + defaultPath
                     + "]: "
                    );
    System.out.println("");
    input = getUserInput();
    if (input.trim().equals("")) {
      messageEvent.setFeedback(defaultPath);
    } else if (input.trim().equals("a") || input.trim().equals("abort")) {
      messageEvent.setFeedback(null);
    } else {
      messageEvent.setFeedback(input);
    }
  }

  /**
   * Get the message that maps to the return code.
   *
   * @param rc the return code
   *
   * @return the message String
   */
  private String getReturnMessage(int rc) {
    String rcMessage = null;

    switch(rc) {
    case 0:
      rcMessage = "The installation has completed successfully.";
      break;
    case 1:
      rcMessage = "Warning: The installation has completed with a minor error.";
      break;
    case 67:
      rcMessage = "Error: A fatal error occurred during the installation.  Please refer to the log file.";
      break;
    case 3010:
      rcMessage = "The installation was successful.  However, a reboot is required to complete the installation.";
      break;
    case 3:
      rcMessage = "Error: The path was not found.";
      break;
    case 5:
      rcMessage = "Error: Access was denied.";
      break;
    case 10:
      rcMessage = "Error: An environment error occurred.";
      break;
    case 13:
      rcMessage = "Error: The data is invalid.";
      break;
    case 87:
      rcMessage = "Error: Invalid response file keyword.";
      break;
    case 66:
      rcMessage = "Error: The installation was cancelled by the user.";
      break;
    case 74:
      rcMessage = "Error: The configuration data is corrupt.  Contact your support personnel.";
      break;
    case 76:
      rcMessage = "Error: The installation source for this product is not available.  Verify that the source exists and that you can access it.";
      break;
    case 82:
      rcMessage = "Error: Another installation is already in progress.  Complete that installation first before proceeding with the installation.";
      break;
    case 86:
      rcMessage = "Error: There was an error opening the installation log file.  Verify that the specified log file location exists and that it is writable.";
      break;
    case 96:
      rcMessage = "Error: The Temp folder is either full or inaccessible.  Verify that the Temp folder exists and that you can write to it.";
      break;
    case 97:
      rcMessage = "Error: This installation package is not supported on this platform.";
      break;
    case 102:
      rcMessage = "Error: Another version of this product is already installed.  Installation of this version cannot continue.";
      break;
    case 103:
      rcMessage = "Error: Invalid command line argument.";
      break;
    case 143:
      rcMessage = "Error: The system does not have enough free space to continue with the installation.";
      break;
    default:
      rcMessage = "Error: No return code received.";
      break;
    }
    return rcMessage;
  }

  /**
   * Get user input from the command line.
   *
   * @return a String containing the user's input
   */
  private String getUserInput() {
    try {
      return mUserInputReader.readLine();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return null;
  }

  /**
   * Run the sample install.
   *
   * @param args arguments for the sample installer
   */
  public static void main(String[] args) {
    String toExecute = "";
    InstallTester installTester = null;

    if (args.length < 1) {
      System.err.println("Syntax: java InstallTester <location of db2setup> -r <location of response file>");
      return;
    }

    for (int i = 0; i < args.length; i++) {
      toExecute += args[i] + " ";
    }

    installTester = new InstallTester();
    System.out.println("Waiting for DB2 install to begin...");
    installTester.runTest(toExecute);
  }
}



