package com.egzosn.infrastructure.params.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页列表，保存了分页内容，当前页，每页记录数，总记录数和页数
 * @author Linjie
 *
 * @param <T>
 */
public class Page<T> {

	private int page = 1; //  当前页
	
	private int rows = 10; // 每页记录数
	
	private int count = 1; // 页数

	private int pages = 1; // 页数
	
	private long total = 0; // 所有记录数
	
	private List<T> content; // 当前页内容
	
	/**
	 * 获取当前页
	 * @return
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * 设置当前页
	 * @param page
	 */
	public void setPage(int page) {
		if (page <= 0){
			page = 1;
		}
		this.page = page;
	}
	
	/**
	 * 获取每页记录数
	 * @return
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * 设置每页记录数
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 设置记录数
	 * @param total
	 */
	public void setTotal(long total) {
		this.total = total;
		// 根据总的记录数生成分页
     	setCount();
	}

	/**
	 * 获取当前页内容
	 * @return
	 */
	public List<T> getContent() {
		return content;
	}
	
	/**
	 * 设置当前页内容
	 * @param content
	 */
	public void setContent(List<T> content) {
		this.content = content;
	}

	public void setCount(){
		if (total >  0) {
			this.count = (int)  Math.ceil((float)total / rows );
		}
	}

	public int getPages() {
		return pages;
	}

	public void setPages() {
		if (total >  0) {
			this.count = (int)  Math.ceil((float)total / rows );
		}
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public Page() {
		content = new ArrayList<T>();
	}

	public Page(int page, int rows, long total, List<T> content) {
		this.page = page;
		this.rows = rows;
		this.total = total;
		this.content = content;
		setCount();
		setPages();
	}

}
