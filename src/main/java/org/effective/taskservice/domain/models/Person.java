package org.effective.taskservice.domain.models;

import jakarta.persistence.*;
import liquibase.ui.UIService;
import lombok.*;
import org.effective.taskservice.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author dayaDanya
 * Класс описывающий пользователя
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "entities", name="person")
public class Person implements Serializable {

    //TODO настроить каскадирование
    /**
     * id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    /**
     * имя пользователя
     */
    @Column(name="username")
    private String username;
    /**
     * электронная почта
     */
    @Column(name="email")
    private String email;
    /**
     * пароль
     */
    @Column(name="password")
    private String password;
    /**
     * ADMIN/USER
     */
    @Enumerated(EnumType.STRING)
    private Role role;
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



}
