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
	
	private List<Message> message_list;	// holds current messages appearing in the table
	
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
			this.message_list.add(post);
			
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
    		setMessageList(message_list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message_list;
	}
	
	public void updateMessageTitle(int id, String title) {}
	
	public void updateMessageText(int id, String message) {}
	
	public void updateMessage(int id, String title, String message) {
		Message post;
		
		post = new Message();
		post.setId(id);
		post.setTitle(title);
        post.setMessage(message);
        
        String result;
		try {
			result = bulletin_board.updateMessage(post);
			System.out.println("[client] Posting message is: " + result);
			
			for (int i=0; i < this.message_list.size(); i++) {
				if (this.message_list.get(i).getId() == id) {
					this.message_list.get(i).setTitle(title);
					this.message_list.get(i).setMessage(message);
				}
			}
			//TODO find message from list and update fields...
			//			this.message_list.add(post);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}

	public void setMessageList(List<Message> message_list) {
		this.message_list = message_list;
	}
	
	public void addMessage(Message message) {
		this.message_list.add(message);
	}

	public Message getLocalMessage(int index){
		return message_list.get(index);
	}
	
	/* View messages from the last fetch to the server */
	public List<Message> getLocalMessageList() {
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
