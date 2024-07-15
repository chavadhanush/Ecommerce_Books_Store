package com.ltp.ecommerce_books_store.web;

import com.ltp.ecommerce_books_store.entity.Book;
import com.ltp.ecommerce_books_store.entity.User;
import com.ltp.ecommerce_books_store.repository.UserRepository;
import com.ltp.ecommerce_books_store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/id/{id}/books")
    public String getUserBooksById(@PathVariable Long id, Model model) {
            User user = userService.getUserById(id);
            model.addAttribute("books", user.getBooks());
        return "user-books";
    }

    @GetMapping("/username/{username}/books")
    public String getUserBooksByUsername(@PathVariable String username, Model model) {
        Set<Book> userBooks = userService.getUserBooks(username);
        if (userBooks.isEmpty()) {
            return "redirect:/not-found";
        }
        model.addAttribute("books", userBooks);
        return "user-books";
    }

    @PostMapping("/login")
    public String processLogin() {
        return "redirect:/books";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }
}
