package org.example.DSMSModule;


/**
* DSMSModule/DSMSOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from org/example/DSMS_CORBA.idl
* Monday, February 24, 2025 4:01:57 PM EST
*/

public interface DSMSOperations 
{

  // Admin roles
  String addShare (String shareID, String shareType, int capacity);
  String removeShare (String shareID, String shareType);
  String listShareAvailability (String shareType);

  // Buyer roles
  String purchaseShare (String buyerID, String shareID, String shareType, int shareCount);
  String getShares (String buyerID);
  String sellShare (String buyerID, String shareID, int shareCount);
  String swapShares (String buyerID, String oldShareID, String oldShareType, String newShareID, String newShareType);
} // interface DSMSOperations
