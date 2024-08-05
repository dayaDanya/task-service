package org.effective.taskservice;

import org.effective.taskservice.repositories.TaskRepo;
import org.effective.taskservice.services.implementations.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TaskServiceImplApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private TaskServiceImpl taskService;

	@SpyBean
	@Autowired
	private TaskRepo taskRepo;

	@Test
	void findById_cachingCorrectly(){
		final long taskId = 3L;
		taskService.findById(taskId);
		taskService.findById(taskId);

		verify(taskRepo, times(1)).findById(taskId);
	}

}
