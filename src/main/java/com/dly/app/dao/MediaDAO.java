package com.dly.app.dao;

import java.util.List;

import com.dly.app.pojo.City;
import com.dly.app.pojo.Collect;
import com.dly.app.pojo.County;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.Image;
import com.dly.app.pojo.Moudle;

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
	 * 获取市
	 * @return
	 */
	public List<City> getCitys();
	/**
	 * 获取区县
	 * @param cityId
	 * @return
	 */
	public List<County> getCountysByCityId(Integer cityId);
	
	public int addImage(Image image);
	public List<Image> getImages();
	public int deleteImageById(String id);
	public Image getImageById(String id);
	
	/**
	 * 获取该用户是否收藏了这个文章
	 * @param collect
	 * @return
	 */
	public List<Collect>  getCollectByUserIdAndGroupId(Collect collect);
	public List<Image> getCarousel();
	 
}
