package com.ruyCorp.dot.service;

import com.ruyCorp.dot.repository.TagRepository;
import com.ruyCorp.dot.repository.UserRepository;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.Tag.TagAlreadyExistsException;
import com.ruyCorp.dot.service.exception.Tag.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final UserService userService;

  @Autowired
  public TagService(TagRepository tagRepository, UserService userService) {
    this.tagRepository = tagRepository;
    this.userService = userService;
  }

  public void tagExists(Integer tagId) {
    Optional<Tag> tag = this.tagRepository.findById(tagId);
    if(tag.isPresent()) {
      throw new TagAlreadyExistsException();
    }
  }

  public Tag getTagById(Integer tagId) throws TagNotFoundException {
    return this.tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
  }

  public Tag tagIsCreatedByUser(Integer tagId, String username) throws NoPermissionException, TagNotFoundException {
    Tag tag = this.getTagById(tagId);
    User user = this.userService.findByUsername(username);
    if (!Objects.equals(tag.getUser().getId(), user.getId())) {
      throw new NoPermissionException();
    }
    return tag;
  }

}
