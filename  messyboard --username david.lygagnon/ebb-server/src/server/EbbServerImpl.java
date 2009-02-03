/*
 * This file is a part of the RMI Plugin for Eclipse tutorials.
 * Copyright (C) 2002-7 Genady Beryozkin
 */
package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


import common.EbbServerInterface;
import common.Message;

/**
 * This is a simulation of a printer server. Note that in real
 * application, server level synchronization may be needed.
 * 
 * @author Genady Beryozkin, rmi-info@genady.net
 */
public class EbbServerImpl extends UnicastRemoteObject implements EbbServerInterface {

	int i;
	
    /**
     * The electronic bulletin board name
     */
    private String name;

    private DatabaseImpl database;
    
    /**
	 * Constant serialVersionUID is needed for serialization interoperability
	 * if this file is compiled with different compilers.
	 */
	private static final long serialVersionUID = 5885886202424414094L;

	/** 
	 * Default constructor that only copies the printer's name. 
	 * The super constructor also exports the remote object.
	 */
    public EbbServerImpl(String name) throws RemoteException {
    	this.name = name;
    	i =0;
    	database = new DatabaseImpl();
    	database.startConnection();
    	database.dropTable();
    	database.createTable();
    }
    
    public static void main(String[] args) {
    	
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ebb", new EbbServerImpl("ebb"));

     } catch (RemoteException e) {
            System.err.println("Something wrong happended on the remote end");
            e.printStackTrace();
            System.exit(-1); // can't just return, rmi threads may not exit
        }
        System.out.println("The electronic bulletin board server is ready");
    }
    
	@Override
	public String postMessage(Message message) throws RemoteException, Exception {
		
		System.out.println("[Sever] Message posted " + message.getMessage());
		
		database.postMessage(message.getTitle(), message.getMessage(), message.getOwner());
		
		return "successful";
	}

	@Override
	public List<Message> viewMessages() throws RemoteException, Exception {
		return database.viewMessages();		
	}
	
}
