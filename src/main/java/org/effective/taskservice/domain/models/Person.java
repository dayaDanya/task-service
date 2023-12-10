package org.effective.taskservice.domain.models;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author dayaDanya
 * Класс описывающий пользователя
 */
@Entity
@Table(name="person")
public class Person {
    /**
     * id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    /**
     * имя пользователя
     */
    @Column(name="username")
    private String username;
    /**
     * пароль
     */
    @Column(name="password")
    private String password;
    /**
     * задачи созданные пользователем
     */
    @OneToMany(mappedBy = "author")
    @Column(name="created_tasks")
    private List<Task> createdTasks;
    /**
     * задачи выполняемые пользователем
     */
    @OneToMany(mappedBy = "performer")
    @Column(name="performed_tasks")
    private List<Task> performedTasks;

    public Person(String username, String password, List<Task> createdTasks, List<Task> performedTasks) {
        this.username = username;
        this.password = password;
        this.createdTasks = createdTasks;
        this.performedTasks = performedTasks;
    }
}
