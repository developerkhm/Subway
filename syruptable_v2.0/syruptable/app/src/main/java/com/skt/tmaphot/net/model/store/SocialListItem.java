package com.skt.tmaphot.net.model.store;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class SocialListItem implements Parcelable {

	@SerializedName("link")
	@JsonField(name ="link")
	private String link;

	@SerializedName("postdate")
	@JsonField(name ="postdate")
	private String postdate;

	@SerializedName("description")
	@JsonField(name ="description")
	private String description;

	@SerializedName("title")
	@JsonField(name ="title")
	private String title;

	@SerializedName("bloggerlink")
	@JsonField(name ="bloggerlink")
	private String bloggerlink;

	@SerializedName("bloggername")
	@JsonField(name ="bloggername")
	private String bloggername;

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setPostdate(String postdate){
		this.postdate = postdate;
	}

	public String getPostdate(){
		return postdate;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBloggerlink(String bloggerlink){
		this.bloggerlink = bloggerlink;
	}

	public String getBloggerlink(){
		return bloggerlink;
	}

	public void setBloggername(String bloggername){
		this.bloggername = bloggername;
	}

	public String getBloggername(){
		return bloggername;
	}

	@Override
 	public String toString(){
		return 
			"SocialListItem{" + 
			"link = '" + link + '\'' + 
			",postdate = '" + postdate + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			",bloggerlink = '" + bloggerlink + '\'' + 
			",bloggername = '" + bloggername + '\'' + 
			"}";
		}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.link);
		dest.writeString(this.postdate);
		dest.writeString(this.description);
		dest.writeString(this.title);
		dest.writeString(this.bloggerlink);
		dest.writeString(this.bloggername);
	}

	public SocialListItem() {
	}

	protected SocialListItem(Parcel in) {
		this.link = in.readString();
		this.postdate = in.readString();
		this.description = in.readString();
		this.title = in.readString();
		this.bloggerlink = in.readString();
		this.bloggername = in.readString();
	}

	public static final Parcelable.Creator<SocialListItem> CREATOR = new Parcelable.Creator<SocialListItem>() {
		@Override
		public SocialListItem createFromParcel(Parcel source) {
			return new SocialListItem(source);
		}

		@Override
		public SocialListItem[] newArray(int size) {
			return new SocialListItem[size];
		}
	};
}