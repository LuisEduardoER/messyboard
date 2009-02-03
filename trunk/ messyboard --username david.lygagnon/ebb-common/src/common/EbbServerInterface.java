/*
 * This file is a part of the RMI Plugin for Eclipse tutorials.
 * Copyright (C) 2002-7 Genady Beryozkin
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This is the generic remote electronic bulletin board interface. 
 * It has methods to submit post, view. update and delete messages. 
 */
public interface EbbServerInterface extends Remote {
	
	/**
	 * Posts a message to the electronic bulletin board
	 * 
	 * @param msg
	 * @return
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public String postMessage(Message message) throws RemoteException, Exception;
	
	public List<Message> viewMessages() throws RemoteException, Exception;
}