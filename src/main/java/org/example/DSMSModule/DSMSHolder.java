package org.example.DSMSModule;

/**
* DSMSModule/DSMSHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from org/example/DSMS_CORBA.idl
* Monday, February 24, 2025 4:01:57 PM EST
*/

public final class DSMSHolder implements org.omg.CORBA.portable.Streamable
{
  public DSMS value = null;

  public DSMSHolder ()
  {
  }

  public DSMSHolder (DSMS initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DSMSHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DSMSHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DSMSHelper.type ();
  }

}
