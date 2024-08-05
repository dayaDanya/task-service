package org.effective.taskservice.config;

import org.effective.taskservice.util.mappers.CommentMapper;
import org.effective.taskservice.util.mappers.TaskMapper;
import org.effective.taskservice.util.mappers.TaskOutMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public TaskOutMapper taskOutMapper(){
        return Mappers.getMapper(TaskOutMapper.class);
    }
    @Bean
    public TaskMapper taskMapper(){
        return Mappers.getMapper(TaskMapper.class);
    }
    @Bean
    public CommentMapper commentMapper(){
        return Mappers.getMapper(CommentMapper.class);
    }
}
