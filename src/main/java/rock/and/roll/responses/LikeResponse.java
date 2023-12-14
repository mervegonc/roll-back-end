package rock.and.roll.responses;

import lombok.Data;
import rock.and.roll.entities.Like;

@Data
public class LikeResponse {
	Long id;
	Long userId;
	Long postId;
	
	public LikeResponse(Like entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.postId = entity.getPost().getId();
		}
}
