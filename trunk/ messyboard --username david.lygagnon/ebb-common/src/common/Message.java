package common;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int id;
		
		private String title;
		private String message;
		private String owner;
		private Date date;
		
		public void setMessage(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
		public String getOwner() {
			return owner;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getDate() {
			return date;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getTitle() {
			return title;
		}
		
		
}
