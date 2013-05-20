package com.orcun.mezun.model.post;



import com.orcun.mezun.model.PostHistory;

public class PostTypePhoto implements Post{

	private PhotoPostHistory photoPostHistory;
	
	
	public PostTypePhoto(){
		if(this.photoPostHistory==null){
			photoPostHistory=new PhotoPostHistory();
		}
	}

	public PhotoPostHistory getPhotoPostHistory() {
		return photoPostHistory;
	}

	public void setPhotoPostHistory(PhotoPostHistory photoPostHistory) {
		this.photoPostHistory = photoPostHistory;
	}

	public PhotoPostHistory getPhotoPost() {
		return getPhotoPostHistory();
	}

	public PostHistory getTextTypePost() {
		
		return photoPostHistory.getPostHistory();
	}

	public EventPostHistory getEventPost() {
		return null;
	}

	public AnnouncementPostHistory getAnnouncementPost() {
		return null;
	}

}
