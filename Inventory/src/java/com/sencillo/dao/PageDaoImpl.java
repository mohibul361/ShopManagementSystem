package com.sencillo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.Page;

@Repository
public class PageDaoImpl implements PageDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Page getPage(Integer id)
	{
		return (Page) sessionFactory.getCurrentSession().get(Page.class, id);
	}

	@Override
	public void Save(Page page)
	{
		this.getCurrentSession().save(page);
	}

	@Override
	public void Edit(Page page)
	{
		this.getCurrentSession().update(page);
	}

	@Override
	public List<Page> getPageList()
	{
		@SuppressWarnings("unchecked")
		List<Page> list = sessionFactory.getCurrentSession().createQuery("from Page").list();
		return list;
	}

	@Override
	public List<Page> getParentPageList()
	{
		@SuppressWarnings("unchecked")
		List<Page> list = sessionFactory.getCurrentSession().createQuery("from Page where parentPage.id is null").list();
		return list;
	}

	public List<Integer> getPageIdListByRole(Integer roleId)
	{
		@SuppressWarnings("unchecked")
		List<Integer> pageIdList = this.sessionFactory.getCurrentSession().createSQLQuery("select pages_Id from role_page where role_id = " + roleId).list();
		return pageIdList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Page, List<Page>> getPageListMap()
	{
		Map<Page, List<Page>> pageMap = new HashMap<Page, List<Page>>();

		List<Page> parentPageList = this.sessionFactory.getCurrentSession().createQuery("from Page where parentPage.id is null ").list();
		for (Page parentPage : parentPageList)
		{
			List<Page> pageList = this.sessionFactory.getCurrentSession().createQuery("from Page where parentPage.id = :parentPageId")
					.setParameter("parentPageId", parentPage.getId()).list();
			System.out.println("page List " + pageList);
			if (!pageList.isEmpty())
			{
				pageMap.put(parentPage, pageList);
			}
		}

		return pageMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Page, List<Page>> getPageListMapByRole(Integer roleId)
	{

		Map<Page, List<Page>> pageMap = new HashMap<Page, List<Page>>();

		List<Integer> pageIdList = this.sessionFactory.getCurrentSession().createSQLQuery("select pages_Id from role_page where role_id = " + roleId).list();
		List<Integer> parentPageIdList = this.sessionFactory.getCurrentSession()
				.createSQLQuery("select distinct p.parent_page_id from role_page rp join page p on rp.pages_Id=p.Id where rp.role_id= " + roleId).list();

		for (Integer parentId : parentPageIdList)
		{
			Page parentPage = getPage(parentId);
			List<Page> childPageList = this.sessionFactory.getCurrentSession().createQuery("from Page where parentPage.id = :parentPageId")
					.setParameter("parentPageId", parentPage.getId()).list();

			List<Page> pagePerRole = new ArrayList<Page>();
			for (Integer childPageId : pageIdList)
			{
				for (Page childPage : childPageList)
				{
					if (childPage.getId().equals(childPageId))
					{
						pagePerRole.add(childPage);
					}
				}
			}
			pageMap.put(parentPage, pagePerRole);

		}

		return pageMap;
	}
	
	@Override
	public void saveRolePage(Integer roleId, List<Integer> pageIds)
	{
		Query q = this.sessionFactory.getCurrentSession().createSQLQuery("delete from role_page where role_id = "+roleId);
		q.executeUpdate();
		
		for(Integer pageId : pageIds)
		{
			Query query = this.sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO role_page (role_id, pages_id) VALUES (:roleId, :pageId)");
			query.setParameter("roleId", roleId);
			query.setParameter("pageId", pageId);
			query.executeUpdate();
		}
	}

}
