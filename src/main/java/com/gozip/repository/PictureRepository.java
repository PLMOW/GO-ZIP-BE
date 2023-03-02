package com.gozip.repository;

import com.gozip.entity.Picture;
import com.gozip.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findPicturesByPostId(Long id);

    List<Picture> deleteAllByPost_Id(Long id);

    List<Picture> findAllByPost_Id(Long id);

}