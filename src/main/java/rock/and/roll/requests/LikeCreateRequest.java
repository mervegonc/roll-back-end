package rock.and.roll.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
	Long id;
	Long userId;
	Long postId;
}
