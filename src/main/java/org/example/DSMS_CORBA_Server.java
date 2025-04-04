package org.example;

import org.example.DSMSModule.DSMS;
import org.example.DSMSModule.DSMSHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class DSMS_CORBA_Server {

    public static void main(String args[]) {
        try{
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant
            DSMSImpl nykServer = new DSMSImpl("NYK", "localhost", 9876, 9976);
            DSMSImpl lonServer = new DSMSImpl("LON", "localhost", 9877, 9977);
            DSMSImpl tokServer = new DSMSImpl("TOK", "localhost", 9878, 9978);

            // get object reference from the servant
            org.omg.CORBA.Object nykRef = rootpoa.servant_to_reference(nykServer);
            DSMS nykHref = DSMSHelper.narrow(nykRef);

            org.omg.CORBA.Object lonRef = rootpoa.servant_to_reference(lonServer);
            DSMS lonHref = DSMSHelper.narrow(lonRef);

            org.omg.CORBA.Object tokRef = rootpoa.servant_to_reference(tokServer);
            DSMS tokHref = DSMSHelper.narrow(tokRef);

            org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent nykPath[] = ncRef.to_name( "NYK" );
            ncRef.rebind(nykPath, nykHref);
            NameComponent lonPath[] = ncRef.to_name( "LON" );
            ncRef.rebind(lonPath, lonHref);
            NameComponent tokPath[] = ncRef.to_name( "TOK" );
            ncRef.rebind(tokPath, tokHref);

            // Start the server's UDP listening thread
            new Thread(nykServer).start();
            new Thread(lonServer).start();
            new Thread(tokServer).start();

            System.out.println("Servers ready and waiting ...");

            // wait for invocations from clients
            orb.run();
        }

        catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("Exiting ...");

    }
}
