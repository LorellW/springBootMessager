package com.github.lorellw.letscode.controllers;

import com.github.lorellw.letscode.entiites.Message;
import com.github.lorellw.letscode.entiites.User;
import com.github.lorellw.letscode.repositories.MessageRepository;
import com.github.lorellw.letscode.services.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MessageController {

    private final MessageRepository messageRepository;
    private final MessageService messageService;
    @Value("${upload.path}")
    private String uploadPath;

    public MessageController(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/root")
    public String root(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Message> page = messageService.messageList(pageable,filter);

        model.addAttribute("page", page);
        model.addAttribute("url","/root");
        model.addAttribute("filter", filter);
        return "root";
    }

    @PostMapping("/root")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable)
    throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            messageRepository.save(message);
        }
        Page<Message> page = messageService.messageList(pageable,"");
        model.addAttribute("url", "/root");
        model.addAttribute("page", page);
        return "root";
    }

    private void saveFile(@Valid Message message,@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable User author,
                               @RequestParam(required = false) Message message,
                               Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){

        Page<Message> page = messageService.messageListForUser(pageable, author);
        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount",author.getSubscriptions().size());
        model.addAttribute("subscribersCount",author.getSubscribers().size());
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("url", "/user-messages/" + author.getId());
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                 @PathVariable Integer user,
                                 @RequestParam("id") Message message,
                                 @RequestParam("text") String text,
                                 @RequestParam("tag") String tag,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        if (message.getAuthor().equals(currentUser)){
            if (StringUtils.hasLength(text)){
                message.setText(text);
            }
            if (StringUtils.hasLength(tag)){
                message.setTag(tag);
            }

            saveFile(message,file);

            messageRepository.save(message);
        }
        return "redirect:/user-messages/" + user;
    }

}
