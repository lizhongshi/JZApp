package com.dly.app.dao;

import java.util.List;

import com.dly.app.pojo.Collect;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.Image;
import com.dly.app.pojo.Moudle;
import com.dly.app.pojo.Region;

public interface MediaDAO {
	public List<Moudle> getMoudles();
	public List<Group> getGroups(Group group);
	public List<Group> getGroupsByMoudleId(Group group);
	public List<Group> getGroupByGroupId(String groupId);
	/**
	 * 搜索文章标题
	 * @param text
	 * @return
	 */
	public List<Group> search(String text);
	/**
	 * 用户添加收藏
	 * @param collect
	 * @return
	 */
	public int userAddCollect(Collect collect);
	/**
	 * 获取用户收藏
	 * @param userId
	 * @return
	 */
	public List<Collect> getCollectByUserId(Collect collect);
	
	/**
	 * 上传
	 * @param image
	 * @return
	 */
	
	public List<Region> getRegion();
	
	public int addImage(Image image);
	public List<Image> getImages();
	public int deleteImageById(String id);
	public Image getImageById(String id);
	
	
	
}
