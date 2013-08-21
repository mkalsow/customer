package com.meins.customer.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.meins.customer.db.MongoConfig;
import com.meins.customer.db.model.CustomerModel;
import com.meins.customer.mongo.MongoUtility;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoConfig.class, DaoConfig.class }, loader = AnnotationConfigContextLoader.class)
public class CustomerDaoTest {

	@Autowired
	private DaoConfig daoConfig;

	@Autowired
	private MongoConfig mongoConfig;

	private CustomerDao customerDao;

	@BeforeClass
	public static void beforeClass() throws IOException {
		MongoUtility.startEmbeddedMongo();
	}

	@AfterClass
	public static void afterClass() {
		MongoUtility.stopEmbeddedMongo();
	}

	@Before
	public void before() throws Exception {
		MongoUtility.drop(mongoConfig.mongoTemplate());

		customerDao = daoConfig.getCustomerDao();
	}

	@Test
	public void testPersistence() {
		System.out.println("Creating an Customer...");
		CustomerModel horst = new CustomerModel();
		horst.setForename("horst");
		horst.setLastname("brot");
		customerDao.save(horst);
		assertNotNull("customer created, must now have an id", horst.getId());
		System.out.println("customer created with Id [" + horst.getId() + "]");

		ObjectId customerId = horst.getId();
		System.out.println("Finding customer with Id [" + customerId + "]");
		CustomerModel fetched = customerDao.find(new ObjectId(customerId.toString()));
		System.out.println("customer found:" + fetched);

		assertEquals("Persisted customer must be same as fetched customer", horst, fetched);
		assertEquals("customer should have firstname horst", fetched.getForename(), "horst");

		// Update
		System.out.println("Adding a new firstname to the customer...");
		fetched.setForename("hans");
		customerDao.save(fetched);
		System.out.println("new firstname added to customer..fetching again..");
		fetched = customerDao.find(customerId);
		assertEquals("customer should have firstname hans", fetched.getForename(), "hans");
		System.out.println("customer fetched after update has correct firstname");
	}

}
