package com.mgnt.events;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mgnt.events.constants.Profiles;

@SpringBootTest
@ActiveProfiles(Profiles.TEST)
class EventsApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
