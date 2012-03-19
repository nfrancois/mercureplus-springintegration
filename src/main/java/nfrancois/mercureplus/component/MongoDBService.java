package nfrancois.mercureplus.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * Gère la connexion à MongoDB 
 */
@Repository
public class MongoDBService {
	
	private static final Log LOG = LogFactory.getLog(MongoDBService.class);

	/** Quand pas de date disponible */
	private static final DateTime NEVER_BE_SYNCH = (new LocalDateTime(1900, 1, 1, 0, 0, 0, 0)).toDateTime();
	/** En cas d'échec de récupération de date, date future pour ne jamais synchroniser */
	private static final DateTime DONT_SYNCH = (new LocalDateTime(2100, 1, 1, 0, 0, 0, 0)).toDateTime();

	private static final String LAST_SYNCHRO_COLLECTION = "lastsynchros";

	@Value("${mongodb.host}")
	private String host;
	@Value("${mongodb.port}")
	private int port;
	@Value("${mongodb.user}")
	private String user;
	@Value("${mongodb.password}")
	private char[] password;
	@Value("${mongodb.db}")
	private String database;

	/**
	 * Récupère la dernière date de synchronisation
	 * @return
	 */
	public DateTime getLastSyncho() {
		Mongo mongo = null;
		try {
			mongo = new Mongo(host, port);
			DB db = mongo.getDB(database);
			boolean authenticate = db.authenticate(user, password);
			if (!authenticate) {
				return DONT_SYNCH;
			}
			DBCollection lastSynchos = db.getCollection(LAST_SYNCHRO_COLLECTION);
			DBCursor cursor = lastSynchos.find().sort(new BasicDBObject("updated", -1)).limit(1);
			if (cursor.hasNext()) {
				String updated = (String) cursor.next().get("updated");
				return new DateTime(updated);
			} else {
				return NEVER_BE_SYNCH;
			}
		} catch (Exception e) {
			LOG.error("getLastSyncho failed", e);
			return DONT_SYNCH;
		} finally {
			if(mongo != null){
				mongo.close();
			}
		}
	}
	
	/**
	 * Met à jour la synchro.
	 * @param synchoDate
	 * @param size
	 * @return 
	 */
	public boolean updateSyncho(DateTime synchoDate, int size) {
		Mongo mongo = null;
		try {
			mongo = new Mongo(host, port);
			DB db = mongo.getDB(database);
			boolean authenticate = db.authenticate(user, password);
			if (!authenticate) {
				return false;
			}
			DBCollection lastSynchos = db.getCollection(LAST_SYNCHRO_COLLECTION);
			DBObject newSyncho = new BasicDBObject();
			newSyncho.put("updated", synchoDate.toString());
			newSyncho.put("size", size);
			lastSynchos.insert(newSyncho);
			return true;
		} catch (Exception e) {
			LOG.error("updateSyncho failed", e);
			return false;
		} finally {
			if(mongo != null){
				mongo.close();
			}
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
