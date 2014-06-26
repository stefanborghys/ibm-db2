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
// SOURCE FILE NAME: StreamEvent.java
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
 * This is the base abstract class for event objects.
 * It handles the splitting of messages.
 *
 * @version %I%, %G%
 */
abstract class StreamEvent extends EventObject {

  protected String mReceivedMessage;
  protected int mMsgGroup;
  protected int mMsgType;
  protected Vector mHeader;
  protected Vector mMessage;


  /**
   * Construct a stream event object.
   *
   * @param source           the object that is generating this event object
   * @param receivedMessage  the message received from the installer
   */
  public StreamEvent(Object source, String receivedMessage) {
    super(source);
    mReceivedMessage = receivedMessage;
    mHeader = new Vector();
    mMessage = new Vector();
    splitMessage();
  }

  /**
   * Get the message that was received.
   *
   * @return the received message
   */
  public String getStreamMessage() {
    return mReceivedMessage;
  }

  /**
   * Get the message group.
   *
   * @return the message group
   */
  public int getMessageGroup() {
    return mMsgGroup;
  }

  /**
   * Get the message type.
   *
   * @return the message type
   */
  public int getMessageType() {
    return mMsgType;
  }

  /**
   * Get the message header.
   *
   * @return the message header
   */
  public Vector getHeader() {
    return mHeader;
  }

  /**
   * Get the message.
   *
   * @return the message
   */
  public Vector getMessage() {
    return mMessage;
  }

  /**
   * Split the message into its separate components.
   * This function will split the header as well.
   */
  protected void splitMessage() {
    String[] headerAndMessage = mReceivedMessage.trim().split("::", 2);

    splitHeader(headerAndMessage[0]);

    if (headerAndMessage.length > 1) {
      splitData(headerAndMessage[1]);
    }

    setGroup( (String) mHeader.elementAt(1) );
    setType( (String) mHeader.elementAt(2) );
  }

  /**
   * Split the header into its three parts.
   *
   * @param headerString       the entire header
   */
  protected void splitHeader(String headerString) {
    String[] headerTokens = headerString.split(" ");

    for (int i = 0; i < headerTokens.length; i++) {
      mHeader.addElement(headerTokens[i].trim());
    }
  }

  /**
   * Split the data fields and store them in the data field vector.
   *
   * @param messageString      the data fields
   */
  protected void splitData(String messageString) {
    String[] messageTokens = messageString.split("::");

    for (int i = 0; i < messageTokens.length; i++) {
      mMessage.addElement(messageTokens[i].trim());
    }
  }

  /**
   * Store the group of the message as an integer.
   *
   * @param group       the string containing the message group
   */
  protected abstract void setGroup(String group);

  /**
   * Store the type of the message as an integer.
   *
   * @param type         the string containing the message group
   */
  protected abstract void setType(String type);

}
