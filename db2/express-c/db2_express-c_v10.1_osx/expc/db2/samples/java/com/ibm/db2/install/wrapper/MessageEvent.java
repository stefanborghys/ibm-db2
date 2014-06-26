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
// SOURCE FILE NAME: MessageEvent.java
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

import java.util.*;

/**
 * This is the event class that is used when MESSAGE messages are received.
 *
 * @version %I%, %G%
 */
public class MessageEvent extends StreamEvent {

  // messages groups
  public static final int INFO = 1;
  public static final int QUERY = 2;
  public static final int ERROR = 3;
  public static final int WARNING = 4;

  // info message types
  public static final int INSTALLSTART = 101;
  public static final int INSTALLEND = 102;
  public static final int UNDOSTART = 103;
  public static final int UNDOEND = 104;

  // query message types
  public static final int CDPATH = 201;

  // error message types
  public static final int CDNOTFOUND = 301;
  public static final int GENERAL = 302;

  private String mFeedback;
  private boolean bFeedbackSet;

  /**
   * Construct a message event object.
   *
   * @param source           the object that is generating this event object
   * @param receivedMessage  the message received from the installer
   */
  public MessageEvent(Object source, String receivedMessage) {
    super(source, receivedMessage);
    mFeedback = null;
    bFeedbackSet = false;
  }

  /**
   * Set the user feedback for the installer.
   * This is used when the installer performs a query.
   *
   * @param feedback         the feedback from the user
   */
  public void setFeedback(String feedback) {
    if (!bFeedbackSet) {
      mFeedback = feedback;
      bFeedbackSet = true;
    }
  }

  /**
   * Get the user feedback.
   */
  public String getFeedback() {
    return mFeedback;
  }

  /**
   * Check if the event object has feedback.
   *
   * @return true if there is feedback, false otherwise
   */
  public boolean hasFeedback() {
    return bFeedbackSet;
  }

  /**
   * Store the group of the message as an integer.
   *
   * @param group       the string containing the message group
   */
  protected void setGroup(String group) {
    if (group.equals("INFO")) {
      mMsgGroup = MessageEvent.INFO;
    } else if (group.equals("QUERY")) {
      mMsgGroup = MessageEvent.QUERY;
    } else if (group.equals("ERROR")) {
      mMsgGroup = MessageEvent.ERROR;
    } else if (group.equals("WARNING")) {
      mMsgGroup = MessageEvent.WARNING;
    } else {
      mMsgGroup = -1;
    }
  }

  /**
   * Store the type of the message as an integer.
   *
   * @param type         the string containing the message group
   */
  protected void setType(String type) {
    if (type.equals("INSTALLSTART")) {
      mMsgType = MessageEvent.INSTALLSTART;
    } else if (type.equals("INSTALLEND")) {
      mMsgType = MessageEvent.INSTALLEND;
    } else if (type.equals("CDPATH")) {
      mMsgType = MessageEvent.CDPATH;
    } else if (type.equals("CDNOTFOUND")) {
      mMsgType = MessageEvent.CDNOTFOUND;
    } else if (type.equals("GENERAL")) {
      mMsgType = MessageEvent.GENERAL;
    } else if (type.equals("UNDOSTART")) {
      mMsgType = MessageEvent.UNDOSTART;
    } else if (type.equals("UNDOEND")) {
      mMsgType = MessageEvent.UNDOEND;
    } else {
      mMsgType = -1;
    }
  }
}
