package org.toolup.archi.io.mxgraph;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestArchiToMxGraphConverter{
	
	private static final File tempDir = new File("./target/TestArchiToMxGraphConverter");
	
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
	public void testConvert() {
		try {
			File archiFile = new File(getClass().getClassLoader().getResource("POC_4.8.1.archimate").toURI());
			MxGraphConverter.toMxGraphFiles(archiFile.getAbsolutePath()
					, tempDir.getAbsolutePath()
					, true);
			Assert.assertTrue(new File(tempDir, "POC_ARCHI_4.8.1_All_elements.xml").exists());
			Assert.assertTrue(new File(tempDir, "POC_ARCHI_4.8.1_Hello_the_world.xml").exists());
			
			FileUtils.writeByteArrayToFile(new File(tempDir, "all.png")
					, MxGraphConverter.toMxGraphImg(archiFile.getAbsolutePath(),  "All elements"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
