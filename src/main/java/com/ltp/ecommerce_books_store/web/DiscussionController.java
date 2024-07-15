package com.ltp.ecommerce_books_store.web;

import com.ltp.ecommerce_books_store.entity.Comment;
import com.ltp.ecommerce_books_store.entity.Discussion;
import com.ltp.ecommerce_books_store.entity.User;
import com.ltp.ecommerce_books_store.service.CommentService;
import com.ltp.ecommerce_books_store.service.DiscussionService;
import com.ltp.ecommerce_books_store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public DiscussionController(DiscussionService discussionService, UserService userService, CommentService commentService) {
        this.discussionService = discussionService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getAllDiscussions(Model model) {
        model.addAttribute("discussions", discussionService.findAll());
        return "discussion-list";
    }

    @GetMapping("/new")
    public String showCreateDiscussionForm(Model model) {
        model.addAttribute("discussion", new Discussion());
        return "discussion-form";
    }

    @PostMapping("/new")
    public String createDiscussion(@ModelAttribute("discussion") @Valid Discussion discussion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "discussion-form";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        discussion.setUser(user);
        discussion.setCreationDate(LocalDateTime.now());

        discussionService.saveDiscussion(discussion);

        return "redirect:/discussions";
    }

    @GetMapping("/{id}")
    public String getDiscussion(@PathVariable Long id, Model model) {
        Discussion discussion = discussionService.findById(id);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comment", new Comment());
        return "discussion-details";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @ModelAttribute("comment") @Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/discussions/" + id;
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Discussion discussion = discussionService.findById(id);

        comment.setUser(user);
        comment.setDiscussion(discussion);
        comment.setCommentDate(LocalDateTime.now());

        commentService.saveComment(comment);

        return "redirect:/discussions/" + id;
    }
}
