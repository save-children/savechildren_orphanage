package com.supung.orphanage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;

@SpringBootTest
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class OrphanageApplicationTests {

	@Test
	void contextLoads() {
	}

}
