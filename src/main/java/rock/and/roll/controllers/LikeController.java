package rock.and.roll.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rock.and.roll.entities.Like;
import rock.and.roll.requests.LikeCreateRequest;
import rock.and.roll.responses.LikeResponse;
import rock.and.roll.services.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	private LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}
	
	@GetMapping
	public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, 
			@RequestParam Optional<Long> postId) {
		return likeService.getAllLikesWithParam(userId, postId);
	}
	
	@PostMapping
	public Like createOneLike(@RequestBody LikeCreateRequest request) {
		return likeService.createOneLike(request);
	}
	
	@GetMapping("/{likeId}")
	public Like getOneLike(@PathVariable Long likeId) {
		return likeService.getOneLikeById(likeId);
	}
	
	@DeleteMapping("/{likeId}")
	public void deleteOneLike(@PathVariable Long likeId) {
		likeService.deleteOneLikeById(likeId);
	}
}
