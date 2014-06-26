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
// SOURCE FILE NAME: StreamCommunicator.java
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
package com.ibm.db2.install.wrapper;

import java.io.*;
import java.util.*;

/**
 * This class is used to communicate to the URE via streams.
 *
 * @version %I%, %G%
 */
public class StreamCommunicator {

  private Vector mProgressListeners;
  private Vector mMessageListeners;
  private Process mInstallProcess;
  private BufferedReader mInstallProcessReader;
  private BufferedWriter mInstallProcessWriter;
  private ObserverNotifier mObserverNotifier;
  private boolean bSubProcessTerminated;

  /**
   * Construct a StreamCommunicator object.
   *
   * @param toExecute the full command to execute a DB2 response file install
   */
  public StreamCommunicator(String toExecute) {
    mProgressListeners = new Vector();
    mMessageListeners = new Vector();

    try {
      mInstallProcess = Runtime.getRuntime().exec(toExecute);
      mInstallProcessReader = new BufferedReader(new InputStreamReader(mInstallProcess.getInputStream()));
      mInstallProcessWriter = new BufferedWriter(new OutputStreamWriter(mInstallProcess.getOutputStream()));
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    bSubProcessTerminated = false;
    mObserverNotifier = new ObserverNotifier();
    mObserverNotifier.start();
  }

  /**
   * Add a progress listener.
   *
   * @param pl the progress listener to add
   */
  public void addProgressListener(ProgressListener pl) {
    mProgressListeners.addElement(pl);
  }

  /**
   * Add a message listener.
   *
   * @param ml the message listener to add
   */
  public void addMessageListener(MessageListener ml) {
    mMessageListeners.addElement(ml);
  }

  /**
   * Waits on the install process until it exits, then returns
   * the return/exit code.
   */
  public int waitAndGetReturnValue() {
    int rc = 0;

    try {
      mInstallProcess.waitFor();
      rc = mInstallProcess.exitValue();
      bSubProcessTerminated = true;
    } catch(InterruptedException ie) {
      System.err.println("Process interrupted");
    } catch(IllegalThreadStateException itse) {
      System.err.println("Process still running, unable to get exit value");
    }
    return rc;
  }

  /**
   * Notify the appropriate listeners when a message is received.
   *
   * @param message the message
   * @exception UnknownProgressException if an unknown PROGRESS message is received
   * @exception UnknownMessageException  if an unknown MESSAGE message is received
   */
  private void notifyListeners(String message)
    throws UnknownProgressException, UnknownMessageException {
    String eventType = getEventType(message);

    if (eventType.equals("PROGRESS")) {
      notifyProgressListeners(message);
    } else if (eventType.equals("MESSAGE")) {
      notifyMessageListeners(message);
    }
  }

  /**
   * Notify the progress listeners.
   *
   * @param message the message
   * @exception UnknownProgressException if an unknown PROGRESS message is received
   */
  private void notifyProgressListeners(String message)
    throws UnknownProgressException {
    ProgressListener progressListener = null;
    ProgressEvent progressEvent = null;
    Vector tempListeners = null;

    progressEvent = new ProgressEvent(this, message);
    tempListeners = (Vector) mProgressListeners.clone();

    for (int i = 0; i < tempListeners.size(); i++) {
      progressListener = (ProgressListener) tempListeners.elementAt(i);

      switch(progressEvent.getMessageGroup()) {
      case ProgressEvent.TASK:
        progressListener.progressTaskReceived(progressEvent);
        break;
      case ProgressEvent.INFO:
        progressListener.progressInfoReceived(progressEvent);
        break;
      default:
        throw new UnknownProgressException("Unknown progress message received");
      }
    }
  }

  /**
   * Notify the message listeners.
   *
   * @param message the message
   * @exception UnknownMessageException if an unknown MESSAGE message is received
   */
  private void notifyMessageListeners(String message)
    throws UnknownMessageException {
    MessageListener messageListener = null;
    MessageEvent messageEvent = null;
    Vector tempListeners = null;

    messageEvent = new MessageEvent(this, message);
    tempListeners = (Vector) mProgressListeners.clone();

    for (int i = 0; i < tempListeners.size(); i++) {
      messageListener = (MessageListener) tempListeners.elementAt(i);

      switch(messageEvent.getMessageGroup()) {
      case MessageEvent.INFO:
        messageListener.messageInfoReceived(messageEvent);
        break;
      case MessageEvent.QUERY:
        messageListener.messageQueryReceived(messageEvent);
        if (messageEvent.getMessageGroup() == MessageEvent.QUERY) {
          sendResponse(messageEvent);
        }
        break;
      case MessageEvent.ERROR:
        messageListener.messageErrorReceived(messageEvent);
        break;
      case MessageEvent.WARNING:
        messageListener.messageWarningReceived(messageEvent);
        break;
      default:
        throw new UnknownMessageException("Unknown URE message received");
      }
    }
  }

  /**
   * Get the message family from the header.
   *
   * @param message the message
   * @return the message family
   */
  private String getEventType(String message) {
    String[] messageTokens = message.split("::", 2);
    String[] headerTokens = messageTokens[0].split(" ", 2);
    return headerTokens[0].trim();
  }

  /**
   * Get the next message from the installer.
   *
   * @return the message
   */
  private String getMessage() {
    String message = null;

    try {
      if (mInstallProcessReader.ready()) {
        message = mInstallProcessReader.readLine();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return message;
  }

  /**
   * Send a message to the installer.
   *
   * @param message the message to send
   */
  private void sendMessage(String message) {
    try {
      mInstallProcessWriter.write(message, 0, message.length());
      mInstallProcessWriter.newLine();
      mInstallProcessWriter.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Send the response corresponding to the installer's query
   * when it is ready.
   *
   * @param me the message event
   */
  private void sendResponse(MessageEvent me) {
    String feedback = null;

    while (!me.hasFeedback()) {
      try {
        Thread.sleep(1);
      } catch(InterruptedException ioe) {
        ioe.printStackTrace();
      }
    }

    feedback = me.getFeedback();

    if (feedback != null) {
      sendMessage("RESPONSE :: " + me.getFeedback());
    } else {
      sendMessage("ABORT");
    }
  }

  /**
   * Validate the format of the message by checking its header.
   * Note: It does not check if the header keywords themselves are valid.
   *       It only checks the format.
   *
   * @param message the message
   * @return true if the message format is valid, false otherwise
   */
  private boolean validHeader(String message) {
    String regex = "^[A-Z]+\\s+[A-Z]+\\s+[A-Z]+.*";
    return message.matches(regex);
  }

  /**
   * This class loops in a separate thread and continuously checks
   * for new messages.  When new messages arrive, the listeners are
   * notified.
   */
  class ObserverNotifier extends Thread {

    /**
     * Run the notifier.
     */
    public void run() {
      blockAndNotify();
    }

    /**
     * Block until a message is received.
     * Notify listeners when a message is received.
     * The loop will exit when the "MESSAGE INFO INSTALLEND" message
     * is received.
     */
    private void blockAndNotify() {
      String message = null;
      try {
        while (!bSubProcessTerminated) {
          message = getMessage();
          if (message != null && validHeader(message)) {
            try {
              notifyListeners(message);
            } catch(UnknownProgressException upe) {
              // nop
            } catch(UnknownMessageException ume) {
              // nop
            }
            if (message.equals("MESSAGE INFO INSTALLEND")) {
              Thread.sleep(50);
              return;
            }
          }
        }
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }
    }
  }
}
