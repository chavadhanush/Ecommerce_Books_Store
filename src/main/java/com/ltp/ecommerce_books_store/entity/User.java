package com.ltp.ecommerce_books_store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Username can't be empty")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password can't be empty")
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Book> books;

    @ManyToMany
    @JoinTable(
            name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> allbooks = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Discussion> discussions = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();
}
