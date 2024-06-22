package com.example.contactmanager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.contactmanager.DTO.UserDTO;
import com.example.contactmanager.Message.Message;
import com.example.contactmanager.Repository.RoleRepository;
import com.example.contactmanager.Repositry.ContactRepo;
import com.example.contactmanager.Security.TbConstants;
import com.example.contactmanager.Service.UserService;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.model.Role;
import com.example.contactmanager.model.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoleRepository roleRepositry;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ContactRepo cr;

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/login")
  public String login(Model model) {
    User user1 = new User();
    model.addAttribute("user", user1);
    return "userlogin";
  }

  @RequestMapping("/register")
  public String showRegister(Model model) {
    User user1 = new User();
    model.addAttribute("user", user1);
    return "userregistration";
  }

  @PostMapping("/register")
  public String userRegister(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model,
      @RequestParam(value = "check", defaultValue = "false") boolean check) {
    try {
      System.out.println(user.getName());
      // if (userAlreadyRegistered(user.getEmail(), result)) {
      // return "userlogin";
      // }
      if (result.hasErrors()) {
        model.addAttribute("user", user);
        return "userregistration";
      }

      // Role roleUser = roleRepositry.findById(TbConstants.USER_ROLE)
      // .orElseThrow(() -> new IllegalStateException("User role not found"));

      Optional<Role> userRole = roleRepositry.findById(TbConstants.USER_ROLE);

      // Create a set to store user roles
      Set<Role> userRoles = new HashSet<>();

      // If the user role is present in the database, add it to the set
      if (userRole.isPresent() && userRole != null) {

        userRoles.add(userRole.get());
        System.out.println("inside userrole");

      } else {
        // If the user role is not present, create it and save it to the database
        Role user1 = new Role();
        user1.setId(TbConstants.USER_ROLE);
        user1.setRname("ROLE_USER");

        Role admin = new Role();
        admin.setId(TbConstants.ADMIN_ROLE);
        admin.setRname("ROLE_ADMIN");

        roleRepositry.save(admin);
        roleRepositry.save(user1);

        // Add the newly created roles to the set
        userRoles.add(admin);
        userRoles.add(user1);
      }

      // Set<Role> roles = new HashSet<>();
      // roles.add(roleUser);

      User newUser = new User();
      newUser.setName(user.getName());
      newUser.setEmail(user.getEmail());
      newUser.setAbout(user.getAbout());
      newUser.setPassword(passwordEncoder.encode(user.getPassword()));
      newUser.setEnable(true);
      newUser.setRoleId(userRoles);
      // newUser.setImage("image.png");

      // Save user data
      User savedUser = userService.savedata(newUser);
      model.addAttribute("user", savedUser);
      System.out.println(savedUser.getName());

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "userregistration";
  }

  private boolean userAlreadyRegistered(String email, BindingResult result) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'userAlreadyRegistered'");
  }

  @GetMapping("/contact")
  public String getMethodName(Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    return "user_dashboard";
  }

  @GetMapping("/add-contact")
  public String addContact(Model m) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String username = authentication.getName();

    Optional<User> userOptional = userService.findByEmail(username);

    m.addAttribute("contact", new Contact());

    return "add_contact";
  }

  @GetMapping("/add_contact")
  public String addCon(Model m, HttpSession session) {

    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();

    // String username = authentication.getName();

    // Optional<User> userOptional = userService.findByEmail(username);

    m.addAttribute("contact", new Contact());

    Message message = (Message) session.getAttribute("message");

    if (message != null) {
      m.addAttribute("message", message.getContent());
      m.addAttribute("type", message.getType());
      // Remove message from session after displaying it
      session.removeAttribute("message");
    }

    return "add_contact";
  }

  @PostMapping("/process-contact")
  public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result,
      @RequestParam("pimage") MultipartFile file, Model m, HttpSession session) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      String username = authentication.getName();

      Optional<User> userOptional = userService.findByEmail(username);

      User user = userOptional.get();
      if (file.isEmpty()) {
        contact.setCimage("pexels-marcelodias-2010877.jpg");
      } else {
        contact.setCimage(file.getOriginalFilename());
        File saveFile = new ClassPathResource("static/img").getFile();
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("image is uploaded");
      }

      cr.save(contact);

      user.getContacts().add(contact);
      contact.setUser(user);
      // System.out.println(contact.getUser());
      User use = userService.savedata(user);
      m.addAttribute("user", use);

      m.addAttribute("contact", contact);

      System.out.println(use);

      session.setAttribute("message", new Message(" your contact is added successfully", "success"));

    } catch (Exception e) {

      e.printStackTrace();
      session.setAttribute("message", new Message(" Something Went Wrong!!", "danger"));

      // return "redirect:/add_contact?error";

    }
    // return "redirect:/add_contact?success";
    return "redirect:/add_contact";
  }

  // @GetMapping("/show_contact/{Page}")
  // public String showcontact(Model model,@PathVariable("Page") int page) {

  // Authentication authentication =
  // SecurityContextHolder.getContext().getAuthentication();

  // String username = authentication.getName();

  // Optional<User> userOptional = userService.findByEmail(username);

  // User user = userOptional.get();

  // PageRequest p = PageRequest.of(page, 5);

  // // List <Contact> id=user.getContacts();

  // // Iterator<Contact> iterator = id.iterator();
  // // System.err.println("print element");
  // // while(iterator.hasNext()){
  // // System.out.println(iterator);
  // // }
  // // model.addAttribute("contacts",iterator );

  // model.addAttribute("contacts", cr.findByUser(user, p));

  // System.out.println(cr.findByUser(user, p));
  // System.out.println( cr.findByUser(user, p).getTotalPages());
  // model.addAttribute("page", page);

  // model.addAttribute("total", cr.findByUser(user, p).getTotalPages());

  // List<Integer> numbers = IntStream.rangeClosed(1, cr.findByUser(user,
  // p).getTotalPages()).boxed()
  // .collect(Collectors.toList());

  // model.addAttribute("numbers", numbers);

  // return "show_contact";
  // }

  @GetMapping("/show_contact/{page}")
  public String showcontact(Model model, @PathVariable("page") int page) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Optional<User> userOptional = userService.findByEmail(username);
    if (userOptional.isEmpty()) {
      return "redirect:/error";
    }

    User user = userOptional.get();
    PageRequest p = PageRequest.of(page, 5);

    var contactsPage = cr.findAllContactsByUserId(user.getId(), p);

    if (contactsPage.hasContent()) {
      model.addAttribute("contacts", contactsPage.getContent());
    } else {
      model.addAttribute("contacts", List.of());
    }

    System.out.println("Contacts: " + contactsPage.getContent());
    System.out.println("Total Pages: " + contactsPage.getTotalPages());

    model.addAttribute("page", page);
    model.addAttribute("total", contactsPage.getTotalPages());

    List<Integer> numbers = IntStream.rangeClosed(1, contactsPage.getTotalPages())
        .boxed()
        .collect(Collectors.toList());

    model.addAttribute("numbers", numbers);

    return "show_contact";
  }

  @GetMapping("/contact/{cid}")
  public String contact(Model model, @PathVariable("cid") ObjectId s) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String username = authentication.getName();

    Optional<User> userOptional = userService.findByEmail(username);

    User user = userOptional.get();

    Optional<Contact> contactOptional = cr.findById(s);
    ObjectId name = user.getId();
    Contact contact = contactOptional.get();
    ObjectId sid = contact.getUser().getId();
    System.out.println(contact.getUser());

    if (sid.equals(name))
      model.addAttribute("contact", contact);

    return "contact_detail";

  }
}