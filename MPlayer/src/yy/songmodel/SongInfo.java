package yy.songmodel;

import java.io.Serializable;

public class SongInfo implements Serializable{
	private String id;
	private String name;
	private String size;
	private String singer;
	private String lrcName = "s2.lrc";
	private String lrcSize;
	public String getLrcName() {
		return "s2.lrc";
	}
	public void setLrcName(String lrcName) {
		this.lrcName = lrcName;
	}
	public String getLrcSize() {
		return lrcSize;
	}
	public void setLrcSize(String lrcSize) {
		this.lrcSize = lrcSize;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public SongInfo(String id, String name, String size, String singer,
			String lrcName, String lrcSize) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.singer = singer;
		this.lrcName = lrcName;
		this.lrcSize = lrcSize;
	}
	@Override
	public String toString() {
		return "SongInfo [id=" + id + ", name=" + name + ", size=" + size
				+ ", singer=" + singer + ", lrcName=" + lrcName + ", lrcSize="
				+ lrcSize + "]";
	}
	public SongInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
