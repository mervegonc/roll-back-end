package rock.and.roll.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rock.and.roll.entities.Post;
import rock.and.roll.requests.PostCreateRequest;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findByUserId(Long userId);

	

}
