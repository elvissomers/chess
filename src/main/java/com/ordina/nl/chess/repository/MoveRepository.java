package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    List<Move> findByPlayers_IdContaining(long id);

    Optional<Move> findByNumberAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(int number, int xFrom,
                                                                                            int xTo, int yFrom, int yTo);

}
