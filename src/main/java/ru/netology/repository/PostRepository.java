package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
  private final Map<Long, Post> postsMap = new ConcurrentHashMap<>();
  private final AtomicLong idCounter;

  public PostRepository() {
    idCounter = new AtomicLong(0);
  }

  public List<Post> all() {
    return new ArrayList<>(postsMap.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(postsMap.get(id));
  }

  public Post save(Post post) {
    if (post.getId()==0 && postsMap.size()==0){
      idCounter.getAndIncrement();
      postsMap.put(idCounter.get(),post);
    }
    if (post.getId()==0 && postsMap.size() > 0 || post.getId() > postsMap.size()){
      idCounter.getAndIncrement();
      postsMap.put(idCounter.get(),post);
    }
    if (postsMap.containsKey(post.getId())){
      postsMap.replace(post.getId(),post);
    }
    return post;
  }

  public void removeById(long id) {
    postsMap.remove(id);
  }
}
