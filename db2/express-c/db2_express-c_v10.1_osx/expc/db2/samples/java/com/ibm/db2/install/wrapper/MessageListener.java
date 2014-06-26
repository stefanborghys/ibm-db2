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
// SOURCE FILE NAME: MessageListener.java
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

/**
 * This is the interface for message listeners.
 * It is used for MESSAGE messages.
 *
 * @version %I%, %G%
 */
public interface MessageListener {

  /**
   * Process MESSAGE INFO messages that are received.
   *
   * @param messageEvent       MessageEvent received from MessageListener
   */
  public void messageInfoReceived(MessageEvent me);

  /**
   * Process MESSAGE QUERY messages that are received.
   *
   * @param messageEvent       MessageEvent received from MessageListener
   */
  public void messageQueryReceived(MessageEvent me);

  /**
   * Process MESSAGE ERROR messages that are received.
   *
   * @param messageEvent       MessageEvent received from MessageListener
   */
  public void messageErrorReceived(MessageEvent me);

  /**
   * Process MESSAGE WARNING messages that are received.
   *
   * @param messageEvent      MessageEvent received from MessageListener
   */
  public void messageWarningReceived(MessageEvent me);
}
