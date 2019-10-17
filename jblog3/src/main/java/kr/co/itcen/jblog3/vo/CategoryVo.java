package kr.co.itcen.jblog3.vo;

public class CategoryVo {
	private Long categoryNo;
	private String name;
	private String contents;
	private String regDate;
	private String blogId;
	
	public Long getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(Long categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	@Override
	public String toString() {
		return "CategoryVo [categoryNo=" + categoryNo + ", name=" + name + ", contents=" + contents + ", regDate="
				+ regDate + ", blogId=" + blogId + "]";
	}
	
	
}
