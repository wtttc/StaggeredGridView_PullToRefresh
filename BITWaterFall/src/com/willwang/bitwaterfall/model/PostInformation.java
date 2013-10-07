package com.willwang.bitwaterfall.model;

import java.sql.Timestamp;

public class PostInformation {
	private int postId;
	private int postHeight;
	private String postImageUri;
	private String postTitle;
	private Timestamp postTimestamp;
	private String postLocation;
	private String postHost;

	public String getPostHost() {
		return postHost;
	}

	public void setPostHost(String postHost) {
		this.postHost = postHost;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public Timestamp getPostTimestamp() {
		return postTimestamp;
	}

	public void setPostTimestamp(Timestamp postTimestamp) {
		this.postTimestamp = postTimestamp;
	}

	public String getPostLocation() {
		return postLocation;
	}

	public void setPostLocation(String postLocation) {
		this.postLocation = postLocation;
	}

	public String getPostImageUri() {
		return postImageUri;
	}

	public void setPostImageUri(String postImageUri) {
		this.postImageUri = postImageUri;
	}

	public int getPostHeight() {
		return postHeight;
	}

	public void setPostHeight(int postHeight) {
		this.postHeight = postHeight;
	}

}
