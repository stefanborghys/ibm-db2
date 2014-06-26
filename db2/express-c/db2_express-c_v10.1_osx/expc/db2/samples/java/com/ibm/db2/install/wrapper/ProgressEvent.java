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
// SOURCE FILE NAME: ProgressEvent.java
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
 * This is the event class that is used when PROGRESS messages are received.
 *
 * @version %I%, %G%
 */
public class ProgressEvent extends StreamEvent {
  // progress groups
  public static final int TASK = 1;
  public static final int INFO = 2;

  // task progress types
  public static final int START = 101;
  public static final int PERCENT = 102;
  public static final int END = 103;

  // info progress types
  public static final int TASKCOUNT = 201;
  public static final int TASKTIMES = 202;
  public static final int TASKIDS = 203;


  /**
   * Construct a progress event object.
   *
   * @param source           the object that is generating this event object
   * @param receivedMessage  the message received from the installer
   */
  public ProgressEvent(Object source, String receivedMessage) {
    super(source, receivedMessage);
  }

  /**
   * Store the group of the message as an integer.
   *
   * @param group       the string containing the message group
   */
  protected void setGroup(String group) {
    if (group.equals("TASK")) {
      mMsgGroup = ProgressEvent.TASK;
    } else if (group.equals("INFO")) {
      mMsgGroup = ProgressEvent.INFO;
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
    if (type.equals("START")) {
      mMsgType = ProgressEvent.START;
    } else if (type.equals("PERCENT")) {
      mMsgType = ProgressEvent.PERCENT;
    } else if (type.equals("END")) {
      mMsgType = ProgressEvent.END;
    } else if (type.equals("TASKCOUNT")) {
      mMsgType = ProgressEvent.TASKCOUNT;
    } else if (type.equals("TASKTIMES")) {
      mMsgType = ProgressEvent.TASKTIMES;
    } else if (type.equals("TASKIDS")) {
      mMsgType = ProgressEvent.TASKIDS;
    } else {
      mMsgType = -1;
    }
  }
}
