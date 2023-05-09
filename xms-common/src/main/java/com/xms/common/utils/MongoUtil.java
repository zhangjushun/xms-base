package com.xms.common.utils;

import com.xms.common.constant.MongoDBConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * mongodb数据库工具类
 * 
 * @Title MongoUtil.java
 * @Description
 * @author monkey
 * @date 2018年9月28日
 */
@Component
public class MongoUtil {

	@Autowired
	private  MongoTemplate mongoTemplate;

	// 插入
	public  void insert(String collectionName, Document o) {
		mongoTemplate.insert(o, collectionName);
	}

	// 普通删除
	public  DeleteResult delete(Query query, String collectionName) {
		return mongoTemplate.remove(query, collectionName);
	}

	// 根据id删除
	public  DeleteResult deleteById(String _id, String collectionName) {
		Query query = Query.query(Criteria.where("_id").is(_id));
		return mongoTemplate.remove(query, collectionName);
	}

	// 统计条数
	public  long count(Query query, String collectionName) {
		return mongoTemplate.count(query, collectionName);
	}

	// 普通查询
	public  List<Document> find(String collectionName, Query query, Sort sort) {
		if (sort != null) {
			query.with(sort);
		}
		return mongoTemplate.find(query, Document.class, collectionName);
	}

	// 根据id查询
	public  Document findById(String id, String collectionName) {
		Query query = Query.query(Criteria.where("_id").is(id));
		return findOne(query, collectionName);
	}
	
	// 根据assetid查询
	public  Document findByAssetId(String assetId, String collectionName) {
		Query query = Query.query(Criteria.where("assetId").is(assetId));
		return findOne(query, collectionName);
	}


	// 查询一条
	public  Document findOne(Query query, String collectionName) {
		return mongoTemplate.findOne(query, Document.class, collectionName);
	}

	// 分页查询
	public  List<Document> findPage(String collectionName, Query query, int start, int pageSize, Sort sort) {
		if (sort != null) {
			query.with(sort);
		}
		query.skip(start).limit(pageSize);
		List<Document> list = mongoTemplate.find(query, Document.class, collectionName);
		return list;
	}

	public  UpdateResult updateSet(Query query, Map<String, Object> valueMap, String collectionName) {
		if (valueMap == null || valueMap.isEmpty()) {
			return null;
		}
		Update update = new Update();
		for (String key : valueMap.keySet()) {
			update.set(key, valueMap.get(key));
		}
		return mongoTemplate.updateMulti(query, update, collectionName);

	}

	public  UpdateResult updateSetById(String _id, Map<String, Object> valueMap, String collectionName) {
		Query query = Query.query(Criteria.where("_id").is(_id));
		return updateSet(query, valueMap, collectionName);

	}

	public  MongoDatabase getdb() {
		return mongoTemplate.getDb();
	}

	public  MongoCollection<Document> getCollection(String collectionName) {
		return mongoTemplate.getCollection(collectionName);
	}

	public Map<String, Integer> getGroupbyField(String elementtype, Date from, Date to){
		Map<String, Integer> map = new HashMap<>();
		List<Bson> list = new ArrayList<>();
		//1:按照elementtype分类查询
		//String newElementtype = "$" + elementtype;
		BasicDBObject type = new BasicDBObject();
		type.append("elementtype",elementtype);
		BasicDBObject Query = new BasicDBObject("$match",type);
		list.add(Query);
		//2:按照时间范围查询
		BasicDBObject createtime = new BasicDBObject("createtime",new BasicDBObject("$gte",from).append("$lt",to));
		BasicDBObject createtimeQuery = new BasicDBObject("$match",createtime);
		list.add(createtimeQuery);
        //3:按照$provider分组查询并统计
		BasicDBObject _id = new BasicDBObject("_id", "$provider");
		_id.append("value", new BasicDBObject("$sum", 1));
		BasicDBObject group = new BasicDBObject("$group", _id);
		list.add(group);


		BasicDBObject result = new BasicDBObject();
		result.append("_id", 0);
		result.append("name", "$_id");
		result.append("value", "$value");
		BasicDBObject project = new BasicDBObject("$project", result);
		list.add(project);
		//4：执行查询
		AggregateIterable<Document> iterable = mongoTemplate.getCollection(MongoDBConstant.DB_NAME_TOBOSTATUS).aggregate(list);
		MongoCursor<Document> set = iterable.iterator();
		while (set.hasNext()) {
			Document mapDocument = set.next();
			String mapkey = null;
			Integer mapValue = null;
			for (String key : mapDocument.keySet()) {
				if(key.equals("name")){
					mapkey = (String) mapDocument.get(key);
				}else if(key.equals("value")){
					mapValue = (Integer) mapDocument.get(key);
				}
			}

			map.put(mapkey,mapValue);
		}
		return map;
	}
}
