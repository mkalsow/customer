package com.meins.customer.mongo;

import java.io.IOException;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.google.common.collect.Lists;
import com.meins.customer.db.model.CustomerModel;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;

/**
 * Simple utility class to drop all collections and maintain an embedded mongo
 * instance
 * 
 */
public final class MongoUtility {
	public static void drop(MongoTemplate template) {
		for (Class<?> clazz : Lists.<Class<?>> newArrayList(CustomerModel.class)) {
			try {
				if (template.collectionExists(clazz)) {
					template.dropCollection(clazz);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static MongodExecutable mongodExe;

	private static MongodProcess mongod;

	public static void startEmbeddedMongo() throws IOException {
		MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
		mongodExe = runtime.prepare(new MongodConfig(Version.V2_1_1, 27017, Network.localhostIsIPv6()));
		mongod = mongodExe.start();
	}

	public static void stopEmbeddedMongo() {
		if (mongod == null) {
			return;
		}
		mongod.stop();
		mongodExe.cleanup();
	}
}
