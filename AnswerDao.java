package com.four.qa.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.four.qa.daoBase.BaseDao;
import com.four.qa.model.Answer;
import com.four.qa.model.FSAnswer;
import com.four.qa.model.Topic;


/**
 * @author mabing
 * @date 2016-6-24
 */
public class AnswerDao extends BaseDao<Answer, Integer>{

	public AnswerDao() {
		super(Answer.class);
	}
	
	/**
	 * @author mabing
	 * 2016-6-24 23:23
	 * 通过答案id获得答案
	 * @param id
	 * @return
	 */
	public Answer getByID(int id)
	{
		return super.get(id);
		
	}
	
	/**
	 * 通过问题id找到答案
	 * 2016-6-24 16:35
	 * @author mabing
	 * @param qid
	 * @return
	 */
	public List<Answer> getByQID(int qid) {
		return super.findBy("ID", true,
				Restrictions.sqlRestriction("id in (select id from answer where qid = '" + qid + "')"));
	}
	
	/**
	 * 获得父答案下的所有子答案
	 * 2016-6-24 16:39
	 * @author mabing
	 * @param fid
	 * @return
	 */
	public List<Answer> getByFID(int fid) {
		return super.findBy("ID", true,
				Restrictions.sqlRestriction("id in (select sid from fsanswer where fid = '" + fid + "')"));
	}
	
	/**
	 * @author mabing
	 * 2016-6-25 11:35
	 * 通过关键字查找答案
	 * @param key
	 * @return
	 */
	public List<Answer> getByKey(String key)
	{
		return super.findLike("ascontent", key, "ID", true);
	}
	
	/**
	 * @author mabing
	 * 2016-6-24 17:01
	 * 保存问题的答案
	 * @param answer
	 */
	public Answer answerQS(Answer answer)
	{
		if(answer!=null)
		{
		   super.save(answer);
		   return answer;
		}
		return null;
	}
	
	/**
	 * @author mabing
	 * 2016-6-24 23:48
	 * 保存答案的子答案
	 * @param answer
	 * @param fid
	 * @return
	 */
	public Answer answerAS(Answer answer,int fid)
	{
		AnswerDao answerdao = null;
		if(answer!=null)
		{
		   super.save(answer);
		   FSAnswer fsanswer = new FSAnswer();
		   Answer fanswer = answerdao.getByID(fid);
		   fsanswer.setFID(fanswer);
		   fsanswer.setSID(answer);
		   return answer;
		}
		return null;
	}
}
