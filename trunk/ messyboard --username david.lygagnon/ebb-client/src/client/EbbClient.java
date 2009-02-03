package client;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import common.EbbServerInterface;
import common.Message;

public class EbbClient {

	private EbbServerInterface bulletin_board;
	
	public EbbClient(String ip_address) {
        System.setSecurityManager(new RMISecurityManager());
        try {
            Registry registry = LocateRegistry.getRegistry(ip_address);
            bulletin_board = (EbbServerInterface) registry.lookup("ebb");
         } catch (Exception e) {
             // Something wrong here
             e.printStackTrace();
         }
	}
	
	//TODO :SPECIFY OWNER OF MESSAGE
	public void postMessage(String title, String message) {
		Message post;
		
		post = new Message();
		post.setTitle(title);
        post.setMessage(message);
        post.setOwner("jack");
        
        String result;
		try {
			result = bulletin_board.postMessage(post);
			System.out.println("[client] Posting message is: " + result);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public List<Message> viewMessages() {
		List<Message> message_list = new ArrayList<Message>();
    	
		try {
    		message_list = bulletin_board.viewMessages();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message_list;
	}
	
	
	/*
	public static void main(String[] args) {
    	List<Message> message_list = new ArrayList<Message>();
    	
    	
        if (args.length != 1) {
            System.out.println("Usage: java " + EbbClient.class + " <registry name>");
            return;
        }
        
        System.setSecurityManager(new RMISecurityManager());
        
        try {
           Registry registry = LocateRegistry.getRegistry(args[0]);
           EbbServerInterface bulletin_board = (EbbServerInterface) registry.lookup("ebb");
          
           Message posting = new Message();
           posting.setMessage("New message as object");
           posting.setOwner("jack");
           
           String result =  bulletin_board.postMessage(posting);
           System.out.println("[client] Posting message is: " + result);
           
           message_list = bulletin_board.viewMessages();
           for(int i =0; i<message_list.size(); i++) {
               System.out.println(message_list.get(i).getMessage() + "DATE: " + message_list.get(i).getDate());
           }
           
        } catch (Exception e) {
            // Something wrong here
            e.printStackTrace();
        }
        
    }
    */
}
