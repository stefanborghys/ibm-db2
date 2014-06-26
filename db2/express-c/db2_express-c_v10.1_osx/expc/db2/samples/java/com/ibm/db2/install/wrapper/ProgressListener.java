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
// SOURCE FILE NAME: ProgressListener.java
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
 * This is the interface for progress listeners.
 * It is used for PROGRESS messages.
 *
 * @version %I%, %G%
 */
public interface ProgressListener {

  /**
   * Process PROGRESS TASK messages that are received.
   *
   * @param progressEvent       ProgressEvent received from ProgressListener
   */
  public void progressTaskReceived(ProgressEvent pe);

  /**
   * Process PROGRESS INFO messages that are received.
   *
   * @param progressEvent       ProgressEvent received from ProgressListener
   */
  public void progressInfoReceived(ProgressEvent pe);
}
