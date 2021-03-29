package org.toolup.archi.io.mxgraph;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.IArchimateObject;
import org.toolup.archi.io.archi.ArchiIO;

public class TestArchiIO {
	

	private static Logger logger = LoggerFactory.getLogger(TestArchiIO.class);

	private static final File tempDir = new File("./target/TestArchiIO");
	
	private static final String fileName = "POC_4.8.1.archimate";
	
	@Before
	public void before() throws IOException {
		tempDir.mkdirs();
		FileUtils.cleanDirectory(tempDir);
	}
	
	@After
	public void after() throws IOException {
		FileUtils.cleanDirectory(tempDir);
	}
	
	@Test
	public void testWriteEquals() {
		try {
			ArchimateModel model = ArchiIO.read(new File(getClass().getClassLoader().getResource(fileName).toURI()).getAbsolutePath());
			
			File outputFile = new File(tempDir,  fileName + "_writetest.xml");
			ArchiIO.write(model, outputFile.getAbsolutePath());
			ArchimateModel model2 = ArchiIO.read(outputFile.getAbsolutePath());
			Assert.assertEquals(model, model2);
			
			List<IArchimateObject> listElems = model.listArchimateElements();
			List<IArchimateObject> listElems2 = model2.listArchimateElements();
			
			
			Assert.assertEquals(listElems.size(), listElems2.size());
			
			for (IArchimateObject elem : listElems) {
				Optional<IArchimateObject> op = listElems2.stream().filter(e -> e.getId() != null && e.getId().equals(elem.getId())).findFirst();
				
				if(!op.isPresent()) logger.error("absent elem {}", elem.getId());
				Assert.assertTrue("absent elem " + elem.getId(), op.isPresent());
				if(!op.get().equals(elem)) logger.error("not equal elem {}", elem.getId());
				Assert.assertEquals("not equal elem " + elem.getId(), op.get(), elem);
			}
			
			Assert.assertEquals(listElems, listElems2);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testReadEquals() {
		try {
			ArchimateModel model = ArchiIO.read(new File(getClass().getClassLoader().getResource(fileName).toURI()).getAbsolutePath());
			ArchimateModel model2 = ArchiIO.read(new File(getClass().getClassLoader().getResource(fileName).toURI()).getAbsolutePath());
			Assert.assertEquals(model, model2);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testRead() {
		try {
			ArchimateModel model = ArchiIO.read(new File(getClass().getClassLoader().getResource(fileName).toURI()).getAbsolutePath());

			Assert.assertEquals("POC ARCHI 4.8.1", model.getName());
			Assert.assertEquals("4bfe0da2-1a9d-4d97-acf0-4c474e2fe37a", model.getId());
			Assert.assertEquals("4.6.0", model.getVersion());
			Assert.assertEquals("705ab1fc-db87-43e9-8caa-5f7ef31304c2", model.getFolderStrategy().getId());
			Assert.assertEquals("4a7127c4-6891-42b4-9843-5a9988fe594b", model.getFolderBusiness().getId());
			Assert.assertEquals("6f11497c-e086-4270-b448-946f7473df31", model.getFolderApplication().getId());
			Assert.assertEquals("264f08be-7730-4b84-9eff-c9105c53a42e", model.getFolderTechnology().getId());
			Assert.assertEquals("ee694935-43aa-4388-a533-65ace326a8f3", model.getFolderMotivation().getId());
			Assert.assertEquals("aa8e8f40-dd91-4100-88bc-ee221ed91913", model.getFolderImplementation().getId());
			Assert.assertEquals("70cc95cb-feb4-4b0c-ac58-c61600939d2f", model.getFolderOther().getId());
			Assert.assertEquals("8e415996-ebaf-4911-a3b7-37fb32162658", model.getFolderRelation().getId());
			Assert.assertEquals("aca387ef-533c-4ea9-a983-9b890eb1f31f", model.getFolderViews().getId());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
