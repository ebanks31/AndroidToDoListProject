package com.example.android.rssfeedlist;

public class Item {

	private String title;
	private int position;
	private String category;
	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public Item(String title,int position, String category)
	{
		this.title=title;
		this.position=position;
		this.category=category;
	}
}
