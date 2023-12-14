package rock.and.roll.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import rock.and.roll.entities.Like;
import rock.and.roll.entities.Post;
import rock.and.roll.entities.User;
import rock.and.roll.requests.LikeCreateRequest;
import rock.and.roll.responses.LikeResponse;
import rock.and.roll.repositories.LikeRepository;

@Service
@Lazy
public class LikeService {

	private LikeRepository likeRepository;
	private UserService userService;
	private PostService postService;

	
	
	public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
		super();
		this.likeRepository = likeRepository;
		this.userService = userService;
		this.postService = postService;
	}

	

	public Like createOneLike(LikeCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if (user != null && post != null) {
			Like likeToSave = new Like();
			likeToSave.setId(request.getId());
			likeToSave.setPost(post);
			likeToSave.setUser(user);
			return likeRepository.save(likeToSave);
		} else
			return null;
	}

	public Like getOneLikeById(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}

	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);

	}

	public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
		List<Like> list;
		if (userId.isPresent() && postId.isPresent()) {
			list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (userId.isPresent()) {
			list = likeRepository.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			list = likeRepository.findByPostId(postId.get());
		} else
			list = likeRepository.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

}
