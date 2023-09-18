package com.practice.project.chess.repository;

import com.practice.project.chess.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    List<Move> findByPlayers_IdContaining(long id);

    // TODO: split up in find move and find playermove
    Optional<Move> findByNumberAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(int number, int xFrom,
                                                                                            int xTo, int yFrom, int yTo);

}
