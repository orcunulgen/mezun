package com.orcun.mezun.model.enums;


public enum UploadedFileDirectory{
	
	PROFILE_PICTURE_PATH("/uploads/profile_picture"),
	EVENT_POSTER_PATH("/uploads/event_poster"),
	ANNOUNCEMENT_POSTER_PATH("/uploads/announcement_poster"),
	CERTIFICATES_PATH("/uploads/certificate"),
	ALBUM_PHOTO_PATH("/uploads/album_photo"),
	TRANSCRIPT_PATH("/uploads/transcript"),
	CLASSICAL_CV_PATH("/uploads/classical_cv");
	
	private String path;
	
	private UploadedFileDirectory(String path) {
		this.path=path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	

}
